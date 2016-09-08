package core.still_object.racetrack;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import core.GameManager;
import gui.Loader;

public class Malus extends RacetrackElement {

	public Malus(double x, double y) {
		super(x, y);
		image = Loader.imgTree;
	}

	public void behavior(GameManager gameManager) { }
	
}