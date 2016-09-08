package core.still_object.environment;

import gui.Loader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import core.GameManager;

public class Stone extends EnvironmentElement {

	public Stone(double x, double y, Image image) {
		super(x, y);
		this.image = image;
	}

	public void behavior(GameManager gameManager) { }


//	public void updateVertex() {
//		double a = Math.sqrt((((double) getWidth())/2d * ((double) getWidth())/2d) + (((double) getHeight())/2d * ((double) getHeight())/2d));
//		double sin_gamma = (((double) getHeight())/2d) / a;
//		double gamma = Math.asin(sin_gamma);
//
//		double ax = a * Math.cos(gamma);
//		double ay = a * Math.sin(gamma);
//
//		System.out.println("AX:\t\t"+ax);
//		System.out.println("AY:\t\t"+ay);
//
//		vertexLeftBack.x = (position.x - ax);
//		vertexLeftBack.y = (position.y - ay);
//		System.out.println("vertexLeftBack:\t"+vertexLeftBack);
//
//		vertexLeftFront.x = (position.x + ax);
//		vertexLeftFront.y = (position.y - ay);
//		System.out.println("vertexLeftFront:\t"+vertexLeftFront);
//
//		vertexRightBack.x = (position.x - ax);
//		vertexRightBack.y = (position.y + ay);
//		System.out.println("vertexRightBack:\t"+vertexRightBack);
//
//		vertexRightFront.x = (position.x + ax);
//		vertexRightFront.y = (position.y + ay);
//		System.out.println("vertexRightFront:\t"+vertexRightFront);
//
//	}
}