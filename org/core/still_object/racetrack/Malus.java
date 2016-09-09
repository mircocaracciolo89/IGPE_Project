package org.core.still_object.racetrack;

import org.core.GameManager;
import org.gui.Loader;

public class Malus extends RacetrackElement {

	public Malus(double x, double y, double orientation) {
		super(x, y);
		image = Loader.imgMalus;
		height = image.getHeight(null);
		width = image.getWidth(null);
		orientation_inDegrees = orientation;
		setVertex();
	}

	public void behavior(GameManager gameManager) { }
	
}