package org.core.still_object.environment;

import java.awt.Image;

import org.core.GameManager;

public class Stone extends EnvironmentElement {

	public Stone(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		height = image.getHeight(null);
		width = image.getWidth(null);
		//updateVertex();
	}

	public void behavior(GameManager gameManager) { }

}