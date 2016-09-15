package org.core.movable_object;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.core.GameObject;

public abstract class MovableObject implements GameObject {

	public abstract Double 			getOrientation_inDegrees();
	public abstract Double 			getOrientation_inRadian();
	public abstract Point2D.Double	getVersors();

	public abstract Line2D.Double	getLeftLine();
	public abstract Line2D.Double 	getRightLine();
	public abstract Line2D.Double 	getFrontLine();
	public abstract Line2D.Double 	getBackLine();
	
	public GeneralPath getPath() {
		GeneralPath path = new GeneralPath(getFrontLine());
		path.append(getLeftLine(), true);
		path.append(getBackLine(), true);
		path.append(getRightLine(), true);
		path.closePath();
		return path;
	}
	
	public abstract void update(GameManager gameManager);
	
}
