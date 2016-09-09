package org.core.environment.racetrack;

import java.awt.geom.Point2D;

import org.core.movable_object.Vehicle.OnSteering;

public class IntelligencePoint {

	private Point2D.Double point;
	private OnSteering steering;
	private Double nextDegree;
	
	public IntelligencePoint(Point2D.Double point, OnSteering steering, Double nextDegrees) {
		this.point = point;
		this.steering = steering;
		this.nextDegree = nextDegrees;
	}

	public Point2D.Double 	getPoint() { return point; }
	public OnSteering	 	getSteering() { return steering; }
	public Double 			getNextDegree() {	return nextDegree; }

	

}
