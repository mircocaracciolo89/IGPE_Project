package org.core.still_object.racetrack;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import org.core.GameManager;
import org.gui.Loader;

public class Malus extends RacetrackElement {

	public Malus(double x, double y) {
		super(x, y);
		image = Loader.imgTree1;
		height = image.getHeight(null);
		width = image.getWidth(null);
	}

	public void behavior(GameManager gameManager) { }
	
}