package org.core.still_object;

import org.core.GameObject;

import java.awt.geom.Line2D;

import org.core.GameManager;

public interface StillObject extends GameObject {
	
	public Line2D.Double	getTopLine();
	public Line2D.Double 	getRightLine();
	public Line2D.Double 	getBottomLine();
	public Line2D.Double 	getLeftLine();
	
	public void behavior(GameManager gameManager);
	
}
