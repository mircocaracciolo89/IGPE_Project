package org.core.still_object;

import org.core.GameObject;
import org.core.movable_object.Vehicle;

import java.awt.geom.Line2D;

public abstract class StillObject implements GameObject {
	
	public abstract Line2D.Double	getTopLine();
	public abstract Line2D.Double 	getRightLine();
	public abstract Line2D.Double 	getBottomLine();
	public abstract Line2D.Double 	getLeftLine();
	public abstract void behavior(Vehicle vehicle);
	
}
