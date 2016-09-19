package org.core.still_object.environment;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.core.still_object.Element;

public abstract class EnvironmentElement extends Element {
	
	/******************* CONSTRUCTOR **********************************************************************************************/
	
	public EnvironmentElement(double x, double y) {
		super(x, y);
	}
	
	/******************* SETTERS **********************************************************************************************/

	public void setVertex() {
		double a = Math.sqrt((((double) getWidth())/2d * ((double) getWidth())/2d) + (((double) getHeight())/2d * ((double) getHeight())/2d));
		double sin_gamma = (((double) getHeight())/2d) / a;
		double gamma = Math.asin(sin_gamma);

		double ax = a * Math.cos(gamma);
		double ay = a * Math.sin(gamma);

		vertexNorthWest = new Point2D.Double((position.x - ax), (position.y - ay));
		vertexNorthEast = new Point2D.Double((position.x + ax), (position.y - ay));
		vertexSouthWest = new Point2D.Double((position.x - ax), (position.y + ay));
		vertexSouthEast = new Point2D.Double((position.x + ax), (position.y + ay));
	}

	/******************* SERVICE METHODS **********************************************************************************************/

	public void drawGameObject(Graphics2D g2d) {
		g2d.translate(position.x, position.y);
		g2d.drawImage(image, AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}
}
