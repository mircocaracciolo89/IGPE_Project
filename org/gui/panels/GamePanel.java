package org.gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

import org.core.GameManager;
import org.core.environment.Environment;
import org.core.movable_object.CarPlayer;
import org.core.movable_object.Vehicle;
import org.gui.Loader;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int SCALE = 4;
	
	public static final Font font = new Font("Arial", Font.BOLD, 14);
	public static final BasicStroke stroke = new BasicStroke(20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	public static final BasicStroke stroke1 = new BasicStroke(25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{25}, 0);
	public static final BasicStroke stroke2 = new BasicStroke(25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{25}, 25);

	private GameManager gameManager;

	private CarPlayer carPlayer;
	private List<Vehicle> vehicles;
	private Environment environment;

	private Runnable runnable;

	/******************************** COSTRUCTOR **********************************************************************************/
	
	public GamePanel(GameManager gameManager) throws IOException {

		this.gameManager = gameManager;
		carPlayer = GameManager.getCarPlayer();
		vehicles = GameManager.getVehicles();
		environment = GameManager.getEnvironment();

		runnable = new Runnable() { public void run() { repaint(); } };

		setPreferredSize(new Dimension(GameManager.WIDTH, GameManager.HEIGHT));

	}

	/******************************************************************************************************************/

	public GameManager getGameManager() { return gameManager; }


	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		AffineTransform oldXform = g2d.getTransform();
		
		// ENVIRONMENT

		environment.drawEnvironment(g2d);

		// CAR

		g2d.setTransform(oldXform);
		carPlayer.drawGameObject(g2d);

		// VEHICLES
		
		for (Vehicle vehicle : vehicles) {
			g2d.setTransform(oldXform);
			vehicle.drawGameObject(g2d);
		}
		
		// INFO

		g2d.setTransform(oldXform);
		gameManager.drawInfo(g2d);

		// PAUSE

//		if(GameManager.paused) {
//			g2d.setTransform(oldXform);
//			g2d.drawImage(Loader.imgPauseImage, 0, 0, GameManager.WIDTH, GameManager.HEIGHT, null);
//		}
//		
		// SEMAPHORE
		
		if (gameManager.getSemaphore().isVisible()) {
			g2d.drawImage(gameManager.getSemaphore().getImage(), 600, 5, null);
		}
		
		g2d.dispose();
	}

	/******************************************************************************************************************/

	public void loadGame() {
		start();
	}

	public void start() {
		gameManager.start(runnable);
	}

}