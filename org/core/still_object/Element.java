package org.core.still_object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import org.core.GameManager;
import org.gui.panels.GamePanel;

public abstract class Element implements StillObject {

	protected Image 	image;
	protected Integer 	height;
	protected Integer 	width;

	protected Point2D.Double position;

	protected Point2D.Double 	vertexNorthWest;
	protected Point2D.Double 	vertexNorthEast;
	protected Point2D.Double 	vertexSouthWest;
	protected Point2D.Double 	vertexSouthEast;

	public Element(double x, double y) {
		position = new Point2D.Double(x * GamePanel.SCALE, y * GamePanel.SCALE);
	}

	public Image getImage() { return image; }
	public int getHeight() { return image.getHeight(null); }
	public int getWidth() { return image.getWidth(null); }

	public Point2D.Double getPosition() { return position; }

	public Line2D.Double getTopLine() { return new Line2D.Double(vertexNorthWest.x, vertexNorthWest.y, vertexNorthEast.x, vertexNorthEast.y); }
	public Line2D.Double getRightLine() { return new Line2D.Double(vertexNorthEast.x, vertexNorthEast.y, vertexSouthEast.x, vertexSouthEast.y); }
	public Line2D.Double getBottomLine() { return new Line2D.Double(vertexSouthWest.x, vertexSouthWest.y, vertexSouthEast.x, vertexSouthEast.y); }
	public Line2D.Double getLeftLine() { return new Line2D.Double(vertexNorthWest.x, vertexNorthWest.y, vertexSouthWest.x, vertexSouthWest.y); }

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
}
