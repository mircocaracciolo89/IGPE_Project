package org.core.still_object.racetrack;

import org.core.movable_object.MovableObject;
import org.core.movable_object.Vehicle;
import org.core.movable_object.Vehicle.VehicleStateOnRacetrack;
import org.gui.Loader;

public class Bonus extends RacetrackElement {
	
	public Bonus(double x, double y, double orientation) {
		super(x, y);
		this.image = Loader.imgBonus;
		height = this.image.getHeight(null);
		width = this.image.getWidth(null);
		orientation_inDegrees = orientation;
		setVertex();
	}

	public void behavior(Vehicle vehicle) { 
		vehicle.setStateOnRacetrack(VehicleStateOnRacetrack.ON_BONUS);
	}

}
