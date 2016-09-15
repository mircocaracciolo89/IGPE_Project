package org.core.still_object.environment;

import java.awt.Image;

import org.core.GameManager;

public class Tree extends EnvironmentElement {

	public Tree(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		height = image.getHeight(null);
		width = image.getWidth(null);
		setVertex();
	}

	public void behavior(GameManager gameManager) { }

}