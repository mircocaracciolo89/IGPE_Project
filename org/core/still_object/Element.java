package org.core.still_object;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.movable_object.CarPlayer;
import org.core.movable_object.Vehicle;
import org.gui.panels.GamePanel;

public abstract class Element extends StillObject {

	protected Image 	image;
	protected Integer 	height;
	protected Integer 	width;

	protected Point2D.Double position;
	protected Border 		 border;

	protected Point2D.Double 	vertexNorthWest;
	protected Point2D.Double 	vertexNorthEast;
	protected Point2D.Double 	vertexSouthWest;
	protected Point2D.Double 	vertexSouthEast;

	public Element(double x, double y) {
		position = new Point2D.Double(x * GamePanel.SCALE, y * GamePanel.SCALE);
		border = Border.UNDEFINED;
	}

	public Image getImage()  { return image; }
	public int	 getHeight() { return height; }
	public int 	 getWidth()  { return width; }

	public Point2D.Double getPosition() { return position; }

	public Line2D.Double getTopLine() 		{ return new Line2D.Double(vertexNorthWest.x, vertexNorthWest.y, vertexNorthEast.x, vertexNorthEast.y); }
	public Line2D.Double getRightLine() 	{ return new Line2D.Double(vertexNorthEast.x, vertexNorthEast.y, vertexSouthEast.x, vertexSouthEast.y); }
	public Line2D.Double getBottomLine() 	{ return new Line2D.Double(vertexSouthWest.x, vertexSouthWest.y, vertexSouthEast.x, vertexSouthEast.y); }
	public Line2D.Double getLeftLine() 		{ return new Line2D.Double(vertexNorthWest.x, vertexNorthWest.y, vertexSouthWest.x, vertexSouthWest.y); }

	public void setBorder(Border border) 	{ this.border = border; }

	public Border getBorder(Vehicle vehicle) {

		if (vehicle instanceof CarPlayer) {

			if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(getTopLine()) || ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(getTopLine())
					|| ((CarPlayer) vehicle).getFrontLineOnEnvironment().intersectsLine(getTopLine()) || ((CarPlayer) vehicle).getBackLineOnEnvironment().intersectsLine(getTopLine()))
				return Border.BOTTOM;

			else if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(getBottomLine()) || ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(getBottomLine())
					|| ((CarPlayer) vehicle).getFrontLineOnEnvironment().intersectsLine(getBottomLine()) || ((CarPlayer) vehicle).getBackLineOnEnvironment().intersectsLine(getBottomLine()))
				return Border.TOP;

			else if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(getLeftLine()) || ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(getLeftLine())
					|| ((CarPlayer) vehicle).getFrontLineOnEnvironment().intersectsLine(getLeftLine()) || ((CarPlayer) vehicle).getBackLineOnEnvironment().intersectsLine(getLeftLine()))
				return Border.RIGHT;

			else if(((CarPlayer) vehicle).getLeftLineOnEnvironment().intersectsLine(getRightLine()) || ((CarPlayer) vehicle).getRightLineOnEnvironment().intersectsLine(getRightLine())
					|| ((CarPlayer) vehicle).getFrontLineOnEnvironment().intersectsLine(getRightLine()) || ((CarPlayer) vehicle).getBackLineOnEnvironment().intersectsLine(getRightLine()))
				return Border.LEFT;


		} else {

			if(vehicle.getLeftLine().intersectsLine(getTopLine()) || vehicle.getRightLine().intersectsLine(getTopLine()))
				return Border.BOTTOM;

			else if(vehicle.getLeftLine().intersectsLine(getBottomLine()) || vehicle.getRightLine().intersectsLine(getBottomLine()))
				return Border.TOP;

			else if(vehicle.getLeftLine().intersectsLine(getLeftLine()) || vehicle.getRightLine().intersectsLine(getLeftLine()))
				return Border.RIGHT;

			else if(vehicle.getLeftLine().intersectsLine(getRightLine()) || vehicle.getRightLine().intersectsLine(getRightLine()))
				return Border.LEFT;

		}

		return Border.UNDEFINED;
	}
}