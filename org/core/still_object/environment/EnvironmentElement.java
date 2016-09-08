package core.still_object.environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import core.movable_object.CarPlayer;
import core.still_object.StillObject;
import gui.panels.GamePanel;

public abstract class EnvironmentElement implements StillObject {
	
	protected Image image;
	private Point2D.Double position;
	
	public EnvironmentElement(double x, double y) {
		position = new Point2D.Double(x * GamePanel.SCALE, y * GamePanel.SCALE);
	}	
	
	public Point2D.Double getPosition() {
		return position;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void drawElement(Graphics2D g2d) {
		g2d.drawImage(image, (int) position.x, (int) position.y, getWidth(), getHeight(), null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}

	public int getWidth() {
		return image.getWidth(null);
	}
	
}
