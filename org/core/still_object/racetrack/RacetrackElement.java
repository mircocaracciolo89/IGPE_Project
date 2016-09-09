package org.core.still_object.racetrack;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import org.core.still_object.StillObject;
import org.gui.panels.GamePanel;

public abstract class RacetrackElement implements StillObject {
	
	protected Image 	image;
	protected Integer 	height;
	protected Integer 	width;
	
	private Point2D.Double position;
	
	public RacetrackElement(double x, double y) {
		position = new Point2D.Double(x * GamePanel.SCALE, y * GamePanel.SCALE);
	}	
	
	public Image getImage() { return image; }
	public int getHeight() { return image.getHeight(null); }
	public int getWidth() { return image.getWidth(null); }

	public Point2D.Double getPosition() { return position; }
	
	public void drawElement(Graphics2D g2d) {
		g2d.drawImage(image, (int) position.x, (int) position.y, getWidth(), getHeight(), null);
	}
	
}
