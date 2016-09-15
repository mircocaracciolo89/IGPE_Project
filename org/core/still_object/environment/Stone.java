package org.core.still_object.environment;

import java.awt.Image;

import org.core.GameManager;
import org.core.movable_object.MovableObject;
import org.core.movable_object.Vehicle;
import org.core.movable_object.Vehicle.VehicleStateOnRacetrack;

public class Stone extends EnvironmentElement {

	public Stone(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		height = image.getHeight(null);
		width = image.getWidth(null);
		setVertex();
	}

	public void behavior(Vehicle vehicle) {
//		vehicle.setCollision(getBorder(vehicle));
//		System.out.println(getBorder(vehicle));
	}

}