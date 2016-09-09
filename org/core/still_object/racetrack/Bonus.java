package org.core.still_object.racetrack;

import org.core.GameManager;
import org.gui.Loader;


public class Bonus extends RacetrackElement {
	
	public Bonus(double x, double y) {
		super(x, y);
		image = Loader.imgTree1;
	}

	public void behavior(GameManager gameManager) { }

}
