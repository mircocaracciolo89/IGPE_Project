package org.core.still_object;

import org.core.GameObject;
import org.core.movable_object.Vehicle;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import org.core.GameManager;

public abstract class StillObject implements GameObject {
	
	public abstract Line2D.Double	getTopLine();
	public abstract Line2D.Double 	getRightLine();
	public abstract Line2D.Double 	getBottomLine();
	public abstract Line2D.Double 	getLeftLine();
	
	public GeneralPath getPath() {
		GeneralPath path = new GeneralPath(getTopLine());
		path.append(getLeftLine(), true);
		path.append(getBottomLine(), true);
		path.append(getRightLine(), true);
		path.closePath();
		return path;
	}
	
	public abstract void behavior(Vehicle vehicle);
	
}
