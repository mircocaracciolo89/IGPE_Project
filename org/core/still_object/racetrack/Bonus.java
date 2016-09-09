package org.core.still_object.racetrack;

import org.core.GameManager;
import org.gui.Loader;


public class Bonus extends RacetrackElement {
	
	public Bonus(double x, double y) {
		super(x, y);
		image = Loader.imgTree1;
		height = image.getHeight(null);
		width = image.getWidth(null);
	}

	public void behavior(GameManager gameManager) { }

}
