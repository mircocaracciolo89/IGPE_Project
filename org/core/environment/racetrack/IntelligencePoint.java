package org.core.environment.racetrack;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.gui.panels.GamePanel;

public class IntelligencePoint {

	/******************* VARIABLES **********************************************************************************************/

	private static final int RANGE = 75;

	private Point2D.Double point;
	Rectangle2D.Double bounds;
	private Double degree;
	private Integer nextIndexPoint;
	
	/******************* CONSTRUCTOR **********************************************************************************************/

	public IntelligencePoint(double x, double y, Double degrees, int nextIndexPoint) {
		this.point = new Point2D.Double(x, y);
		bounds = new Rectangle2D.Double(x - RANGE * GamePanel.SCALE/2, y - RANGE * GamePanel.SCALE/2, RANGE * GamePanel.SCALE, RANGE * GamePanel.SCALE);
		this.degree = degrees;
		this.nextIndexPoint = nextIndexPoint;
	}

	/******************* GETTERS **********************************************************************************************/

	public Point2D.Double 		getPoint() { return point; }
	public Rectangle2D.Double 	getBounds() { return bounds; }
	public Double 				getDegree() {	return degree; }
	public Integer 				getNextIndexPoint() { return nextIndexPoint; }
	
	public Double 			getOrientation_inRadian() 	{ return ((degree/180) * Math.PI);	}
	public Point2D.Double	getVersors() 				{ return new Point2D.Double(Math.cos(getOrientation_inRadian()), Math.sin(getOrientation_inRadian())); }
}
