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
import org.core.display.Semaphore.SemaphoreState;
import org.core.environment.*;
import org.core.movable_object.*;
import org.core.movable_object.Vehicle.OnSteering;
import org.gui.panels.GamePanel;

// PER LE COLLISIONI STUDIARE SEPARATING AXIS THEOREM "S.A.T."

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

	protected static Graphics g;

	public static Boolean 	paused;

	public static GameState gameState;
	private static Border 	border;

	private static Environment 	environment;

	private static CarPlayer 	carPlayer;
	private static List<Vehicle> vehicles;

	private Semaphore 		semaphore;

	private boolean 		isNewLap;
	private int 			lapCounter;
	private boolean 		running;

	private static int indexCheckpointNumber;

	private static Stopwatch stopwatch;
	private float time;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public GameManager() {
		paused = false;
		border = Border.UNDEFINED;
		gameState = GameState.UNDEFINED;

		vehicles = new ArrayList<Vehicle>();

		running = true;
		isNewLap = false;
		lapCounter = 0;
		indexCheckpointNumber = 1;
		semaphore = new Semaphore();
		stopwatch = new Stopwatch(this);
		time = 0f;
	}

	/******************* GETTERS **********************************************************************************************/

	public static Environment 		getEnvironment() 	{ return environment; }
	public static CarPlayer 		getCarPlayer() 		{ return carPlayer; }
	public static List<Vehicle> 	getVehicles() 		{ return vehicles; }

	public Semaphore 				getSemaphore() 		{ return semaphore; }

	public int 						getLapCounter() 	{ return lapCounter; }

	public static int 				getIndexCheckpointNumber() { return indexCheckpointNumber; }
	public static Stopwatch 		getStopwatch() { return stopwatch; }

	public void 					quit() 				{ running = false; }

	/******************* SETTERS **********************************************************************************************/

	public void setEnvironment(String nameFile) {
		try {
			environment = new Herb(new File("racetrackFiles"+File.separator+nameFile));
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

		if (lapCounter <= 0)
			g2d.drawString("LAP: -", 50, 50);
		else 
			g2d.drawString("LAP: " + lapCounter, 50, 50);

		g2d.drawString(String.format("TIME REMAINING: %1$.3f", Math.abs(time)), 50, 100);

		if (!isCorrectDirection())
			g2d.drawString("WRONG DIRECTION", 600, 400);
		if (!isOnTrack())
			g2d.drawString("BACK ON TRACK", 600, 400);
		if (isCorrectDirection() && intersectCheckpoint()) {
			g2d.drawString("CHECKPOINT", 600, 400);
			indexCheckpointNumber = (indexCheckpointNumber + 1) % environment.getRacetrack().getCheckpoints().size();
			if (indexCheckpointNumber == 1) isNewLap = false;
		}
		if (intersectStartLine() && !isNewLap) {
			g2d.drawString("LAP", 600, 400);
			lapCounter++;
			isNewLap = true;
		}

		if (running == false)
			g2d.drawString("GAME OVER", 600, 400);

	}	


	public boolean isCorrectDirection() {
		for (int i = 0; i < environment.getRacetrack().getRacetrackParts().size(); i++)
			if ((environment.getRacetrack().getRacetrackParts().get(i).contains(carPlayer.getPositionOnEnvironment()))
					&& (carPlayer.getDirection() != environment.getRacetrack().getDirections().get(i)[0] 
							&& carPlayer.getDirection() != environment.getRacetrack().getDirections().get(i)[1] 
									&& carPlayer.getDirection() != environment.getRacetrack().getDirections().get(i)[2]) )
				return false;
		return true;
	}


	public boolean isOnTrack() {
		if((environment.getRacetrack().getPathIn().contains(carPlayer.getPositionOnEnvironment())) || (!environment.getRacetrack().getPathOut().contains(carPlayer.getPositionOnEnvironment())))
			return false;
		return true;
	}


	/********************************* METHOS FOR INTERSECTS *********************************************************************************/

	public boolean intersectCheckpoint() {
		if(carPlayer.getLeftLineOnEnvironment().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber))
				|| carPlayer.getRightLineOnEnvironment().intersectsLine(environment.getRacetrack().getCheckpoints().get(indexCheckpointNumber)))
			return true;
		return false;
	}

	public boolean intersectStartLine() {
		if(carPlayer.getLeftLineOnEnvironment().intersectsLine(environment.getRacetrack().getStartLine())
				|| carPlayer.getRightLineOnEnvironment().intersectsLine(environment.getRacetrack().getStartLine()))
			return true;
		return false;
	}

	public static Border intersectBorder(final Vehicle vehicle) {
		border = Border.UNDEFINED;

		if(vehicle.getVertexLeftBack().x <= 0d || vehicle.getVertexLeftFront().x <= 0d 
				|| vehicle.getVertexRightBack().x <= 0d || vehicle.getVertexRightFront().x <= 0d)
			border = Border.LEFT;

		else if(vehicle.getVertexLeftBack().x >= (GameManager.WIDTH ) || vehicle.getVertexLeftFront().x >= (GameManager.WIDTH )
				|| vehicle.getVertexRightBack().x >= (GameManager.WIDTH ) || vehicle.getVertexRightFront().x >= (GameManager.WIDTH ))
			border = Border.RIGHT;

		else if(vehicle.getVertexLeftBack().y <= 0d || vehicle.getVertexLeftFront().y <= 0d 
				|| vehicle.getVertexRightBack().y <= 0d || vehicle.getVertexRightFront().y <= 0d)
			border = Border.TOP;

		else if(vehicle.getVertexLeftBack().y >= (GameManager.HEIGHT ) || vehicle.getVertexLeftFront().y >= (GameManager.HEIGHT )
				|| vehicle.getVertexRightBack().y >= (GameManager.HEIGHT ) || vehicle.getVertexRightFront().y >= (GameManager.HEIGHT ))
			border = Border.BOTTOM;

		return border;
	}

	public void intersectBetweenVehicles() {
		
	}

	public void intersectBetweenElements() {
		
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
						
						intersectBetweenVehicles();
						intersectBetweenElements();
						
						carPlayer.update(gameManager);
						for (Vehicle vehicle : vehicles) {
							vehicle.update(gameManager);
						}
						runnable.run();
						
					}

					//					if (gameManager.time <= 0f)
					//						running = false;

					if (lapCounter == 1)
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