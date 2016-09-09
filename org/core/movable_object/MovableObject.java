package org.core.movable_object;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.core.GameObject;

public interface MovableObject extends GameObject {

	public Double 			getOrientation_inDegrees();
	public Double 			getOrientation_inRadian();
	public Point2D.Double	getVersors();

	public Line2D.Double	getLeftLine();
	public Line2D.Double 	getRightLine();
	public Line2D.Double 	getFrontLine();
	public Line2D.Double 	getBackLine();
	
	public void update(GameManager gameManager);
	
}
