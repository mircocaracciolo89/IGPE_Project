package org.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.core.display.*;
import org.core.environment.*;
import org.core.movable_object.*;
import org.core.movable_object.Vehicle.VehicleStateOnRacetrack;
import org.core.movable_object.Vehicle.Crash;
import org.core.still_object.Element;
import org.core.still_object.StillObject;
import org.core.still_object.racetrack.RacetrackElement;
import org.gui.panels.GamePanel;

public class GameManager {

	/******************* ENUM **********************************************************************************************/

	public enum Border {
		UNDEFINED(0x0), TOP(0x1), RIGHT(0x2), BOTTOM(0x3), LEFT(0x4);
		private int format;
		Border (int format) {
			this.format = format;
		}

		public static Border fromInteger(int format) throws IllegalArgumentException {
			for (Border dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static Border getDefault() {
			return UNDEFINED;
		}

	}

	public enum BorderArea {
		UNDEFINED(0x0), TOP(0x1), RIGHT(0x2), BOTTOM(0x3), LEFT(0x4), TOP_RIGHT(0x5), TOP_LEFT(0x6), BOTTOM_RIGHT(0x7), BOTTOM_LEFT(0x8);
		private int format;
		BorderArea (int format) {
			this.format = format;
		}

		public static BorderArea fromInteger(int format) throws IllegalArgumentException {
			for (BorderArea dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static BorderArea getDefault() {
			return UNDEFINED;
		}

	}

	public enum GameState {
		UNDEFINED(0x0), RUN(0x1), WIN(0x2), OVER(0x3);
		private int format;
		GameState (int format) {
			this.format = format;
		}

		public static GameState fromInteger(int format) throws IllegalArgumentException {
			for (GameState dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static GameState getDefault() {
			return UNDEFINED;
		}

	}

	/******************* VARIABLES **********************************************************************************************/

	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	protected static final double WIDTH_RANGE = 600d;
	protected static final double HEIGHT_RANGE = 400d;

	protected static final float TIME = 10f;
	protected static final int LAP_NUMBER = 2;

	protected static Graphics g;

	public static Environment 	 environment;
	private static CarPlayer 	 carPlayer;
	private static List<Vehicle> vehicles;

	public static  boolean 	 paused;
	public static  GameState gameState;
	private static Border 	 border;

	private Semaphore semaphore;

	public boolean  isNewLap;
	public boolean  isOnTrack;
	public boolean  isCorrectDirection;
	public int 		lapCounter;

	private boolean running;

	public static int indexCheckpointNumber;
	private static Stopwatch stopwatch;
	private float  time;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public GameManager() {
		paused = false;
		border = Border.UNDEFINED;
		gameState = GameState.UNDEFINED;

		vehicles = new ArrayList<Vehicle>();

		isOnTrack = isCorrectDirection = true;
		isNewLap = false;
		lapCounter = 0;
		indexCheckpointNumber = 1;

		running = true;
		semaphore = new Semaphore();
		time = TIME;
		stopwatch = new Stopwatch(this);
	}

	/******************* GETTERS **********************************************************************************************/

	public static Environment 		getEnvironment() 	{ return environment; }
	public static CarPlayer 		getCarPlayer() 		{ return carPlayer; }
	public static List<Vehicle> 	getVehicles() 		{ return vehicles; }

	public Semaphore 				getSemaphore() 		{ return semaphore; }

	public int 						getLapCounter() 	{ return lapCounter; }

	public static int 				getIndexCheckpointNumber()  { return indexCheckpointNumber; }
	public static Stopwatch 		getStopwatch() 				{ return stopwatch; }
	public float 					getTime() 					{ return time; }

	/******************* SETTERS **********************************************************************************************/

	public void setEnvironment(String nameFile) {
		try {
			environment = new Grass(new File("racetrackFiles"+File.separator+nameFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setVehicles(Point2D.Double startPoint, Point2D.Double startTranslation, Double startOrientation, Image img) {
		carPlayer = new CarPlayer(startPoint, startTranslation, startOrientation, img);

		for (Map.Entry<Point2D.Double, Double> entry : environment.getRacetrack().getParamVehicles().entrySet()) {
			vehicles.add(new CarComputer(entry.getKey(), entry.getValue()));
		}

	}

	public void setTime(float time) { this.time = time; }

	/******************* SERVICE METHODS FOR ENUM BORDER **********************************************************************************************/

	public static boolean isOnBorderAreaTop() {

		if (carPlayer.isForwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case NORTH:
			case NE:
			case NW:
				if (carPlayer.getPosition().y <= HEIGHT_RANGE && CarPlayer.getTranslation().y <= 0)  
					return true;
			default:
				break;
			}
		} else if (carPlayer.isBackwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case SOUTH:
			case SE:
			case SW:
				if (carPlayer.getPosition().y <= HEIGHT_RANGE && CarPlayer.getTranslation().y <= 0)  
					return true;
			default:
				break;
			}
		}

		return false;
	}

	public static boolean isOnBorderAreaRight() {

		if (carPlayer.isForwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case EAST:
			case NE:
			case SE:
				if (GameManager.WIDTH - carPlayer.getPosition().x <= WIDTH_RANGE  && (carPlayer.getPosition().x + Math.abs(CarPlayer.getTranslation().x)) <= ((GameManager.WIDTH * GamePanel.SCALE) - WIDTH_RANGE))
					return true;
			default:
				break;
			}
		} else if (carPlayer.isBackwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case WEST:
			case NW:
			case SW:
				if (GameManager.WIDTH - carPlayer.getPosition().x <= WIDTH_RANGE  && (carPlayer.getPosition().x + Math.abs(CarPlayer.getTranslation().x)) <= ((GameManager.WIDTH * GamePanel.SCALE) - WIDTH_RANGE))
					return true;
			default:
				break;
			}
		}
		return false;
	}

	public static boolean isOnBorderAreaBottom() {

		if (carPlayer.isForwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case SOUTH:
			case SE:
			case SW:
				if (GameManager.HEIGHT - carPlayer.getPosition().y <= HEIGHT_RANGE && (carPlayer.getPosition().y + Math.abs(CarPlayer.getTranslation().y)) <= ((GameManager.HEIGHT * GamePanel.SCALE) - HEIGHT_RANGE))  
					return true;
			default:
				break;
			}
		} else if (carPlayer.isBackwardsMarch()) { 
			switch (carPlayer.getDirection()) {
			case NORTH:
			case NE:
			case NW:
				if (GameManager.HEIGHT - carPlayer.getPosition().y <= HEIGHT_RANGE && (carPlayer.getPosition().y + Math.abs(CarPlayer.getTranslation().y)) <= ((GameManager.HEIGHT * GamePanel.SCALE) - HEIGHT_RANGE))  
					return true;
			default:
				break;
			}
		}

		return false;
	}

	public static boolean isOnBorderAreaLeft() {

		if (carPlayer.isForwardsMarch()) {
			switch (carPlayer.getDirection()) {
			case WEST:
			case SW:
			case NW:
				if (carPlayer.getPosition().x <= WIDTH_RANGE && CarPlayer.getTranslation().x <= 0)
					return true;
			default:
				break;
			}
		} else if (carPlayer.isBackwardsMarch()) { 
			switch (carPlayer.getDirection()) {
			case EAST:
			case SE:
			case NE:
				if (carPlayer.getPosition().x <= WIDTH_RANGE && CarPlayer.getTranslation().x <= 0)
					return true;
			default:
				break;
			}
		}

		return false;
	}

	public static BorderArea getActualBorderArea() {

		if (isOnBorderAreaTop() && !isOnBorderAreaRight() && !isOnBorderAreaLeft())
			return BorderArea.TOP;

		else if (isOnBorderAreaRight() && !isOnBorderAreaTop() && !isOnBorderAreaBottom())
			return BorderArea.RIGHT;

		else if (isOnBorderAreaBottom() && !isOnBorderAreaRight() && !isOnBorderAreaLeft()) 
			return BorderArea.BOTTOM;

		else if (isOnBorderAreaLeft() && !isOnBorderAreaTop() && !isOnBorderAreaBottom())
			return BorderArea.LEFT;

		else if (isOnBorderAreaTop() && isOnBorderAreaRight())
			return BorderArea.TOP_RIGHT;

		else if (isOnBorderAreaTop() && isOnBorderAreaLeft())
			return BorderArea.TOP_LEFT;

		else if (isOnBorderAreaBottom() && isOnBorderAreaRight())
			return BorderArea.BOTTOM_RIGHT;

		else if (isOnBorderAreaBottom() && isOnBorderAreaLeft())
			return BorderArea.BOTTOM_LEFT;

		return BorderArea.UNDEFINED;
	}

	/********************************* METHOS FOR DRAW *********************************************************************************/

	public void drawInfo(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(GamePanel.font);
		g2d.drawString("CURRENT GEAR: " + carPlayer.getCurrentGear(), 1150, 50);
		g2d.drawString(String.format("CURRENT SPEED: %1$.0f KM/H", carPlayer.getCurrentSpeed().floatValue() * 10f) , 1150, 100);

		g2d.drawString( (lapCounter <= 0) ? "LAP: - / "+LAP_NUMBER : "LAP: "+lapCounter+" / "+LAP_NUMBER, 50, 50);


		if (!isCorrectDirection) {
			g2d.setColor(Color.RED);
			g2d.drawString("WRONG DIRECTION", 600, 400);
		}

		if (!isOnTrack) {
			g2d.setColor(Color.RED);
			g2d.drawString("BACK ON TRACK", 600, 400);
		}

		if (running == false)
			g2d.drawString("GAME OVER", 600, 400);

		g2d.drawString(String.format("TIME REMAINING: %1$.3f", Math.abs(time)), 50, 100);
	}	

	/******************************************************************************************************************/

	public static boolean isCorrectDirection(Vehicle vehicle) {
		if (vehicle instanceof CarPlayer) {

			for (int i = 0; i < environment.getRacetrack().getRacetrackParts().size(); i++) {

				if ((environment.getRacetrack().getRacetrackParts().get(i).contains(((CarPlayer) vehicle).getPositionOnEnvironment()))
						&& (vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[0] 
								&& vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[1] 
										&& vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[2]) ) {

					stopwatch.setDecreaseValue(10);
					return false;
				}

			}

			if (vehicle.getStateOnRacetrack() != VehicleStateOnRacetrack.OFF_TRACK)
				stopwatch.setDecreaseValue(1);

		} else {

			for (int i = 0; i < environment.getRacetrack().getRacetrackParts().size(); i++)
				if ((environment.getRacetrack().getRacetrackParts().get(i).contains(vehicle.getPosition()))
						&& (vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[0] 
								&& vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[1] 
										&& vehicle.getDirection() != environment.getRacetrack().getDirections().get(i)[2]) )

					return false;

		}

		return true;
	}


	/******************************************************************************************************************/

	public static boolean isOnTrack(Vehicle vehicle) {
		if (vehicle instanceof CarPlayer) { 

			if((environment.getRacetrack().getPathIn().contains(((CarPlayer) vehicle).getPositionOnEnvironment())) || (!environment.getRacetrack().getPathOut().contains(((CarPlayer) vehicle).getPositionOnEnvironment()))) {
				stopwatch.setDecreaseValue(5);
				return false;
			}
			stopwatch.setDecreaseValue(1);

		} else {

			if((environment.getRacetrack().getPathIn().contains(vehicle.getPosition())) || (!environment.getRacetrack().getPathOut().contains(vehicle.getPosition()))) {
				return false;
			}

		}

		return true;
	}


	/********************************* METHOS FOR INTERSECTS *********************************************************************************/

	public static boolean intersectCheckpoint(Vehicle vehicle) {
		if (vehicle instanceof CarPlayer) {

			if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber))
					|| ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber))) {

				stopwatch.increaseInterval();

				return true;
			}
		} else {

			if(vehicle.getLeftLine().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber))
					|| vehicle.getRightLine().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber))) {

				return true;
			}
		}

		return false;
	}

	public static boolean intersectStartLine(Vehicle vehicle) {
		if (vehicle instanceof CarPlayer) {

			if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(environment.getRacetrack().getStartLine())
					|| ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(environment.getRacetrack().getStartLine()))

				return true;

		} else { 

			if(vehicle.getLeftLine().intersectsLine(environment.getRacetrack().getStartLine())
					|| vehicle.getRightLine().intersectsLine(environment.getRacetrack().getStartLine()))

				return true;
		}

		return false;
	}

	public static Border intersectBorder(Vehicle vehicle) {
		border = Border.UNDEFINED;
		//		vehicle.setMaxPosition(null);

		if (vehicle instanceof CarPlayer) {

			if(((CarPlayer) vehicle).getVertexLeftBack().x <= 0d || ((CarPlayer) vehicle).getVertexLeftFront().x <= 0d 
					|| vehicle.getVertexRightBack().x <= 0d || vehicle.getVertexRightFront().x <= 0d)
				border = Border.LEFT;

			else if(((CarPlayer) vehicle).getVertexLeftBack().x >= (GameManager.WIDTH ) || ((CarPlayer) vehicle).getVertexLeftFront().x >= (GameManager.WIDTH )
					|| ((CarPlayer) vehicle).getVertexRightBack().x >= (GameManager.WIDTH ) || ((CarPlayer) vehicle).getVertexRightFront().x >= (GameManager.WIDTH ))
				border = Border.RIGHT;

			else if(((CarPlayer) vehicle).getVertexLeftBack().y <= 0d || ((CarPlayer) vehicle).getVertexLeftFront().y <= 0d 
					|| ((CarPlayer) vehicle).getVertexRightBack().y <= 0d || ((CarPlayer) vehicle).getVertexRightFront().y <= 0d)
				border = Border.TOP;

			else if(((CarPlayer) vehicle).getVertexLeftBack().y >= (GameManager.HEIGHT ) || ((CarPlayer) vehicle).getVertexLeftFront().y >= (GameManager.HEIGHT )
					|| ((CarPlayer) vehicle).getVertexRightBack().y >= (GameManager.HEIGHT ) || ((CarPlayer) vehicle).getVertexRightFront().y >= (GameManager.HEIGHT ))
				border = Border.BOTTOM;

		} else {

			if(vehicle.getVertexLeftBack().x <= 0d || vehicle.getVertexLeftFront().x <= 0d 
					|| vehicle.getVertexRightBack().x <= 0d || vehicle.getVertexRightFront().x <= 0d)
				border = Border.LEFT;

			else if(vehicle.getVertexLeftBack().x >= (GameManager.WIDTH * GamePanel.SCALE) || vehicle.getVertexLeftFront().x >= (GameManager.WIDTH * GamePanel.SCALE)
					|| vehicle.getVertexRightBack().x >= (GameManager.WIDTH * GamePanel.SCALE) || vehicle.getVertexRightFront().x >= (GameManager.WIDTH * GamePanel.SCALE))
				border = Border.RIGHT;

			else if(vehicle.getVertexLeftBack().y <= 0d || vehicle.getVertexLeftFront().y <= 0d 
					|| vehicle.getVertexRightBack().y <= 0d || vehicle.getVertexRightFront().y <= 0d)
				border = Border.TOP;

			else if(vehicle.getVertexLeftBack().y >= (GameManager.HEIGHT * GamePanel.SCALE) || vehicle.getVertexLeftFront().y >= (GameManager.HEIGHT * GamePanel.SCALE)
					|| vehicle.getVertexRightBack().y >= (GameManager.HEIGHT * GamePanel.SCALE) || vehicle.getVertexRightFront().y >= (GameManager.HEIGHT * GamePanel.SCALE))
				border = Border.BOTTOM;

			System.out.println("\nvertex left back "+vehicle.getVertexLeftBack().y);
			System.out.println("vertex left front "+vehicle.getVertexLeftFront().y);
			System.out.println("vertex right back "+vehicle.getVertexRightBack().y);
			System.out.println("vertex right front "+vehicle.getVertexRightFront().y);


		}

		Border b = intersectBetweenElements(vehicle);
		if (b != Border.UNDEFINED)
			border = b;

		return border;
	}

	@SuppressWarnings("static-access")
	public static void intersectBetweenVehicles(Vehicle currentVehicle) {

		for (Vehicle vehicle : vehicles) {

			if (currentVehicle instanceof CarComputer && currentVehicle.equals(vehicle))
				vehicle = GameManager.getCarPlayer();


			if (currentVehicle.getCrash(vehicle) != Crash.UNDEFINED) {

				currentVehicle.setMaxPosition(currentVehicle.getPosition());
				vehicle.setMaxPosition(currentVehicle.getPosition());

				currentVehicle.setCurrentSpeed(currentVehicle.getCurrentSpeed() * 0.8d);

				if (currentVehicle instanceof CarPlayer) {
					switch (getActualBorderArea()) {
					case TOP:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y + (vehicle.getAbsDeltaY()));
						currentVehicle.setPositionX(currentVehicle.getPosition().x + vehicle.getDeltaX() );

						break;

					case BOTTOM:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y - (vehicle.getAbsDeltaY()));
						currentVehicle.setPositionX(currentVehicle.getPosition().x + vehicle.getDeltaX() );
						break;

					case LEFT:

						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x + vehicle.getAbsDeltaX());
						currentVehicle.setPositionY(currentVehicle.getPosition().y + vehicle.getDeltaY() );
						break;

					case RIGHT:


						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x - vehicle.getAbsDeltaX());
						currentVehicle.setPositionY(currentVehicle.getPosition().y + vehicle.getDeltaY() );
						break;

					case TOP_LEFT:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y + (vehicle.getAbsDeltaY()));
						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x + vehicle.getAbsDeltaX());
						break;

					case TOP_RIGHT:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y + (vehicle.getAbsDeltaY()));
						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x - vehicle.getAbsDeltaX());
						break;

					case BOTTOM_LEFT:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y - (vehicle.getAbsDeltaY()));
						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x + vehicle.getAbsDeltaX());
						break;

					case BOTTOM_RIGHT:

						((CarPlayer)currentVehicle).setTranslationY( ((CarPlayer)currentVehicle).getTranslation().y - (vehicle.getAbsDeltaY()));
						((CarPlayer)currentVehicle).setTranslationX( ((CarPlayer)currentVehicle).getTranslation().x - vehicle.getAbsDeltaX());
						break;

					case UNDEFINED:
					default:
						currentVehicle.setPosition(new Point2D.Double( (currentVehicle.getPosition().x + vehicle.getDeltaX() ) , ( currentVehicle.getPosition().y + vehicle.getDeltaY() ) ));
						break;
					}
				}
				else
					currentVehicle.setPosition(new Point2D.Double( (currentVehicle.getPosition().x + vehicle.getDeltaX()) , ( currentVehicle.getPosition().y + vehicle.getDeltaY()) ));

				vehicle.setCurrentSpeed(vehicle.getCurrentSpeed() * 0.8d);

				if (vehicle instanceof CarPlayer) {
					switch (getActualBorderArea()) {
					case TOP:

						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y + (currentVehicle.getAbsDeltaY()));
						vehicle.setPositionX(vehicle.getPosition().x + currentVehicle.getDeltaX() );
						break;

					case BOTTOM:

						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y - (currentVehicle.getAbsDeltaY()));
						vehicle.setPositionX(vehicle.getPosition().x + currentVehicle.getDeltaX() );
						break;

					case RIGHT:

						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x - (currentVehicle.getAbsDeltaX()));
						vehicle.setPositionY(vehicle.getPosition().y + currentVehicle.getDeltaY() );
						break;

					case LEFT:

						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x + (currentVehicle.getAbsDeltaX()));
						vehicle.setPositionY(vehicle.getPosition().y + currentVehicle.getDeltaY() );
						break;

					case TOP_LEFT:

						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y + (currentVehicle.getAbsDeltaY()));
						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x + (currentVehicle.getAbsDeltaX()));
						break;

					case TOP_RIGHT:

						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x - (currentVehicle.getAbsDeltaX()));
						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y + (currentVehicle.getAbsDeltaY()));
						break;

					case BOTTOM_RIGHT:

						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x + (currentVehicle.getAbsDeltaX()));
						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y + (currentVehicle.getAbsDeltaY()));
						break;

					case BOTTOM_LEFT:

						((CarPlayer)vehicle).setTranslationX( ((CarPlayer)vehicle).getTranslation().x + (currentVehicle.getAbsDeltaX()));
						((CarPlayer)vehicle).setTranslationY( ((CarPlayer)vehicle).getTranslation().y - (currentVehicle.getAbsDeltaY()));
						break;

					case UNDEFINED:
					default:
						vehicle.setPosition(new Point2D.Double( (vehicle.getPosition().x + currentVehicle.getDeltaX()*2 ) , ( vehicle.getPosition().y + currentVehicle.getDeltaY() *2) ));
						break;
					}
				}
				else 
					vehicle.setPosition(new Point2D.Double( (vehicle.getPosition().x + currentVehicle.getDeltaX()*2 ) , ( vehicle.getPosition().y + currentVehicle.getDeltaY() *2) ));
			}
		}

	}







	public static Border intersectBetweenElements(Vehicle vehicle) {
		Border b = Border.UNDEFINED;

		for (StillObject stillObject : environment.getRacetrack().getStillObjects()) {

			b = ((Element)stillObject).getBorder(vehicle);

			if ((stillObject instanceof RacetrackElement) && b != Border.UNDEFINED) {
				stillObject.behavior(vehicle);
				return Border.UNDEFINED;
			}
			else if (b != Border.UNDEFINED)
				return b; 
		}
		return b;
	}

	public static void intersect(Vehicle vehicle) {
		if (GameManager.intersectBorder(vehicle) != Border.UNDEFINED)
			vehicle.setMaxPosition(vehicle.getPosition());
	}

	private void updateInfo() {

		indexCheckpointNumber = carPlayer.getIndexCheckpointNumber();
		lapCounter = carPlayer.getLapCounter();
		isNewLap = carPlayer.isNewLap();
		isOnTrack = carPlayer.getStateOnRacetrack() != VehicleStateOnRacetrack.OFF_TRACK;
		isCorrectDirection = carPlayer.getStateOnRacetrack() != VehicleStateOnRacetrack.WRONG_DIRECTION;

	}

	/******************************************************************************************************************/

	public void start(final Runnable runnable) { 

		final GameManager gameManager = this;
		new Thread() {
			public void run() {

				semaphore.start(runnable);
				stopwatch.startTimer();

				while (running) {
					if(GameManager.paused == false) { 

						carPlayer.update(gameManager);

						for (Vehicle vehicle : vehicles) {
							vehicle.update(gameManager);
						}

						updateInfo();

						runnable.run();

					}

					if (gameManager.time <= 0f || gameManager.lapCounter > GameManager.LAP_NUMBER)
						running = false;

					if (carPlayer.getCurrentSpeed() > ((carPlayer.getActualMaxSpeed() * 0.5d)) )
						semaphore.setVisible(false);

					try {
						Thread.sleep(35);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}

				}

			}

		}.start();
	}

	/******************************************************************************************************************/

}