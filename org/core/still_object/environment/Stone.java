package org.core.still_object.environment;

import java.awt.Image;

import org.core.movable_object.Vehicle;

public class Stone extends EnvironmentElement {

	/******************* CONSTRUCTOR **********************************************************************************************/

	public Stone(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		height = image.getHeight(null);
		width = image.getWidth(null);
		setVertex();
	}

	/******************* SERVICE METHODS **********************************************************************************************/

	public void behavior(Vehicle vehicle) {
	}

}