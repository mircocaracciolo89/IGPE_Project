package org.core.environment.racetrack;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.core.movable_object.Vehicle.OnSteering;

public class IntelligencePoint {
	
	private static final int RANGE = 300;

	private Point2D.Double point;
	Rectangle2D.Double bounds;
	private Double degree;
	
	public IntelligencePoint(double x, double y, Double degrees) {
		this.point = new Point2D.Double(x, y);
		bounds = new Rectangle2D.Double(x - RANGE/2, y - RANGE/2, RANGE, RANGE);
		this.degree = degrees;
	}

	public Point2D.Double 		getPoint() { return point; }
	public Rectangle2D.Double 	getBounds() { return bounds; }
	public Double 				getDegree() {	return degree; }

}
