package org.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

public interface GameObject {
	
	public Image getImage();
	public int getHeight();
	public int getWidth();
	public Point2D.Double getPosition();
	public void drawGameObject(Graphics2D g2d);
}
