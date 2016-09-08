package core.still_object.racetrack;

import core.GameManager;
import gui.Loader;


public class Bonus extends RacetrackElement {
	
	public Bonus(double x, double y) {
		super(x, y);
		image = Loader.imgTree;
	}

	public void behavior(GameManager gameManager) { }

}
