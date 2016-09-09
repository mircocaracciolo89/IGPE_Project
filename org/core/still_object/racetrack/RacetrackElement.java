package org.core.still_object.racetrack;


import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.core.still_object.Element;

public abstract class RacetrackElement extends Element {

	protected Double orientation_inDegrees;

	public RacetrackElement(double x, double y, Double orientation) {
		super(x, y);
		orientation_inDegrees = orientation;
	}

	public Double getOrientation_inDegrees() 	{ return orientation_inDegrees;	}
	public Double getOrientation_inRadian() 	{ return ((orientation_inDegrees/180) * Math.PI);	}

	public void setVertex() {
		double 	halfWidth = getWidth()/2, halfHeight = getHeight()/2,

				cosWidth = halfWidth * Math.cos(getOrientation_inRadian()), sinHeight = halfHeight * Math.sin(getOrientation_inRadian()),
				sinWidth = halfWidth * Math.sin(getOrientation_inRadian()), cosHeight = halfHeight * Math.cos(getOrientation_inRadian()),

				x11 = -cosWidth - sinHeight,
				y11 = sinWidth - cosHeight,

				x21 = cosWidth - sinHeight,
				y21 = -sinWidth - cosHeight, 

				x31 = -cosWidth + sinHeight,
				y31 = sinWidth + cosHeight,

				x41 = cosWidth + sinHeight,
				y41 = -sinWidth + cosHeight;


		vertexNorthWest = new Point2D.Double((position.x + x11), (position.y - y11));
		vertexNorthEast = new Point2D.Double((position.x - x21), (position.y + y21));
		vertexSouthWest = new Point2D.Double((position.x - x31), (position.y + y31));
		vertexSouthEast = new Point2D.Double((position.x + x41), (position.y - y41));
	}

	public void drawGameObject(Graphics2D g2d) {
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(image, (int) position.x, (int) position.y, getWidth(), getHeight(), null);
	}
}
