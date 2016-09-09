package org.core.still_object.environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.gui.Loader;

public class Tree extends EnvironmentElement {

	public Tree(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		height = image.getHeight(null);
		width = image.getWidth(null);
	}

	public void behavior(GameManager gameManager) { }

}