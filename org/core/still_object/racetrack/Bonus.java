package org.core.still_object.racetrack;

import org.core.GameManager;
import org.gui.Loader;

public class Bonus extends RacetrackElement {
	
	public Bonus(double x, double y, double orientation) {
		super(x, y);
		image = Loader.imgBonus;
		height = image.getHeight(null);
		width = image.getWidth(null);
		orientation_inDegrees = orientation;
		setVertex();
	}

	public void behavior(GameManager gameManager) { }

}
