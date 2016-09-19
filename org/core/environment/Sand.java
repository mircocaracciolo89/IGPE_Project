package org.core.environment;

import org.core.GameManager;
import org.core.environment.racetrack.Racetrack;
import org.core.movable_object.CarPlayer;
import org.core.still_object.environment.EnvironmentElement;
import org.gui.Loader;
import org.gui.panels.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sand implements Environment {

	/******************* VARIABLES **********************************************************************************************/

	private Image image;
	private final List<EnvironmentElement> elements;
	private Racetrack racetrack;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public Sand(File racetrackFile) throws IOException {
		image = Loader.imgBackgroundEnvironment;
		elements = new ArrayList<EnvironmentElement>();
		racetrack = new Racetrack(racetrackFile);
	}
	
	/******************* GETTERS **********************************************************************************************/

	public Image getImageBackground() { return image; }
	public List<EnvironmentElement> getElements() { return elements; }
	public Racetrack getRacetrack() { return racetrack; }

	/******************* SERVICE METHODS **********************************************************************************************/
	
	public void drawEnvironment(Graphics2D g2d) {
		g2d.drawImage(image, (int) CarPlayer.getTranslation().x, (int) CarPlayer.getTranslation().y, GameManager.WIDTH * GamePanel.SCALE, GameManager.HEIGHT * GamePanel.SCALE, null);
		
		g2d.setStroke(GamePanel.stroke);
		g2d.setColor(Color.ORANGE);
		g2d.translate(CarPlayer.getTranslation().x, CarPlayer.getTranslation().y);

		g2d.draw(getRacetrack().getPathOut());
		g2d.draw(getRacetrack().getPathIn());

	}
}
