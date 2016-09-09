package org.core.still_object.environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import org.core.still_object.StillObject;
import org.gui.panels.GamePanel;

public abstract class EnvironmentElement implements StillObject {
	
	protected Image 	image;
	protected Integer 	height;
	protected Integer 	width;
	
	private Point2D.Double position;
	
	protected Point2D.Double 	vertexNorthWest;
	protected Point2D.Double 	vertexNorthEast;
	protected Point2D.Double 	vertexSouthWest;
	protected Point2D.Double 	vertexSouthEast;
	
	public EnvironmentElement(double x, double y) {
		position = new Point2D.Double(x * GamePanel.SCALE, y * GamePanel.SCALE);
	}	
	
	public Image getImage() { return image; }
	public int getHeight() { return height; }
	public int getWidth() { return width; }

	public Point2D.Double getPosition() { return position; }
	
	public void drawElement(Graphics2D g2d) { 
		g2d.drawImage(image, (int) position.x, (int) position.y, getWidth(), getHeight(), null);
	}
	
	public void updateVertex() {
		double a = Math.sqrt((((double) getWidth())/2d * ((double) getWidth())/2d) + (((double) getHeight())/2d * ((double) getHeight())/2d));
		double sin_gamma = (((double) getHeight())/2d) / a;
		double gamma = Math.asin(sin_gamma);

		double ax = a * Math.cos(gamma);
		double ay = a * Math.sin(gamma);

		vertexNorthWest.x = (position.x - ax);
		vertexNorthWest.y = (position.y - ay);

		vertexNorthEast.x = (position.x + ax);
		vertexNorthEast.y = (position.y - ay);

		vertexSouthWest.x = (position.x - ax);
		vertexSouthWest.y = (position.y + ay);

		vertexSouthEast.x = (position.x + ax);
		vertexSouthEast.y = (position.y + ay);

	}
	
}
