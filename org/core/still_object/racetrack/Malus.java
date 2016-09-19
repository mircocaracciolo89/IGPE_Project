package org.core.still_object.racetrack;

import org.core.movable_object.Vehicle;
import org.core.movable_object.Vehicle.VehicleStateOnRacetrack;
import org.gui.Loader;

public class Malus extends RacetrackElement {

	/******************* CONSTRUCTOR **********************************************************************************************/
	
	public Malus(double x, double y, double orientation) {
		super(x, y);
		this.image = Loader.imgMalus;
		height = this.image.getHeight(null);
		width = this.image.getWidth(null);
		orientation_inDegrees = orientation;
		setVertex();
	}

	/******************* SERVICE METHODS **********************************************************************************************/

	public void behavior(Vehicle vehicle) {
		vehicle.setStateOnRacetrack(VehicleStateOnRacetrack.ON_MALUS);
	}
	
}