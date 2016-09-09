package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.GameManager.BorderArea;
import org.gui.Loader;

public class CarPlayer extends Vehicle {

	protected static Point2D.Double translation;
	protected Point2D.Double positionOnEnvironment;

	public CarPlayer(Point2D.Double startPoint, Point2D.Double startTranslation, Double startOrientation, Image img) {
		super(startPoint.x, startPoint.y);

//		translation = new Point2D.Double(0d, 0d);
//		translation = new Point2D.Double(-2300d, 0d);
		translation = startTranslation;
		positionOnEnvironment = new Point2D.Double();

		image = img;
		height = image.getHeight(null);
		width = image.getWidth(null);

		mass = 1d;
		steeringAngle = 10d;
		orientation_inDegrees = startOrientation;
		updateDirection();
		direction = getDirection();
		maxSpeed = 30d;
		initialAcceleration = 5d;

	}

	public static Point2D.Double 	getTranslation() { return translation; }
	
	public Line2D.Double	getLeftLineOnEnvironment() 	{ return new Line2D.Double(vertexLeftBack.x + Math.abs(translation.x), vertexLeftBack.y + Math.abs(translation.y), vertexLeftFront.x + Math.abs(translation.x), vertexLeftFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getRightLineOnEnvironment() { return new Line2D.Double(vertexRightBack.x + Math.abs(translation.x), vertexRightBack.y + Math.abs(translation.y), vertexRightFront.x + Math.abs(translation.x), vertexRightFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getFrontLineOnEnvironment() { return new Line2D.Double(vertexLeftFront.x + Math.abs(translation.x), vertexLeftFront.y + Math.abs(translation.y), vertexRightFront.x + Math.abs(translation.x), vertexRightFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getBackLineOnEnvironment() 	{ return new Line2D.Double(vertexRightBack.x + Math.abs(translation.x), vertexRightBack.y + Math.abs(translation.y), vertexLeftBack.x + Math.abs(translation.x), vertexLeftBack.y + Math.abs(translation.y)); }

	public void 	setTranslation(Point2D.Double t) 	{ translation = t; }
	public void 	setTranslationX(Double x) 			{ translation.x = x; }
	public void 	setTranslationY(Double y) 			{ translation.y = y; }
	
	public Point2D.Double getPositionOnEnvironment() { 
		positionOnEnvironment.x = position.x + Math.abs(translation.x);
		positionOnEnvironment.y = position.y + Math.abs(translation.y);
		return positionOnEnvironment;
	}

	/******************************************************************************************************************/
	
	public void drawElement(Graphics2D g2d) {
		g2d.translate(position.x, position.y);
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(getImage(),AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}

	/******************************************************************************************************************/

	protected void updateSteeringForwards() {

		Double newOrientation = orientation_inDegrees;

		switch (onSteering) {
		case LEFT:
			newOrientation -= braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		case RIGHT:
			newOrientation += braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		case UNDEFINED:
		default:
			break;
		}

		setOrientation_inDegrees(newOrientation);

	}

	protected void updateSteeringBackwards() {

		Double newOrientation = orientation_inDegrees;

		switch (onSteering) {
		case LEFT:
			newOrientation += braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		case RIGHT:
			newOrientation -= braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		case UNDEFINED:
		default:
			break;
		}

		setOrientation_inDegrees(newOrientation);

	}
	
	/******************************************************************************************************************/

	protected void updateVehicleOnBorderForwards () {

		switch (GameManager.getActualBorderArea()) {

		case TOP:

			increaseTranslationY();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionXForwards();
			break;

		case RIGHT:
			decreaseTranslationX();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionYForwards();
			break;

		case BOTTOM:
			decreaseTranslationY();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionXForwards();
			break;

		case LEFT:
			increaseTranslationX();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionYForwards();
			break;

		case TOP_RIGHT:
			decreaseTranslationX();
			increaseTranslationY();
			break;

		case TOP_LEFT:
			increaseTranslationX();
			increaseTranslationY();
			break;

		case BOTTOM_RIGHT:
			decreaseTranslationX();
			decreaseTranslationY();
			break;

		case BOTTOM_LEFT:
			increaseTranslationX();
			decreaseTranslationY();
			break;

		default:
			break;
		}

	}

	protected void updateVehicleOnBorderBackwards () {

		switch (GameManager.getActualBorderArea()) {
		case TOP:
			increaseTranslationY();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionXBackwards();
			break;

		case RIGHT:
			decreaseTranslationX();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionYBackwards();
			break;

		case BOTTOM:
			decreaseTranslationY();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionXBackwards();
			break;

		case LEFT:
			increaseTranslationX();
			if (GameManager.intersectBorder(this) == Border.UNDEFINED)
				updatePositionYBackwards();
			break;

		case TOP_RIGHT:
			decreaseTranslationX();
			increaseTranslationY();
			break;

		case TOP_LEFT:
			increaseTranslationX();
			increaseTranslationY();
			break;

		case BOTTOM_RIGHT:
			decreaseTranslationX();
			decreaseTranslationY();
			break;

		case BOTTOM_LEFT:
			increaseTranslationX();
			decreaseTranslationY();
			break;

		default:
			break;
		}

	}

	/******************************************************************************************************************/

	protected void updatePositionForwards() {

		Border border = GameManager.intersectBorder(this);

		switch (border) {

		case UNDEFINED:
			updatePositionXForwards();
			updatePositionYForwards();
			break;

		case TOP:
			updatePositionXForwards();

			switch (getDirection()) {
			case SOUTH:
			case SE:
			case SW:
				updatePositionYForwards();
				break;
			default:
				break;
			}
			break;

		case RIGHT:
			updatePositionYForwards();

			switch (getDirection()) {
			case WEST:
			case NW:
			case SW:
				updatePositionXForwards();
				break;
			default:
				break;
			}

			break;

		case BOTTOM:
			updatePositionXForwards();

			switch (getDirection()) {
			case NORTH:
			case NW:
			case NE:
				updatePositionYForwards();
				break;

			default:
				break;
			}

			break;

		case LEFT:
			updatePositionYForwards();

			switch (getDirection()) {
			case EAST:
			case NE:
			case SE:
				updatePositionXForwards();
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}

	}

	protected void updatePositionBackwards() {

		Border border = GameManager.intersectBorder(this);

		switch (border) {

		case UNDEFINED:
			updatePositionXBackwards();
			updatePositionYBackwards();
			break;

		case BOTTOM:
			updatePositionXBackwards();

			switch (getDirection()) {
			case SOUTH:
			case SE:
			case SW:
				updatePositionYBackwards();
				break;
			default:
				break;
			}
			break;

		case LEFT:
			updatePositionYBackwards();

			switch (getDirection()) {
			case WEST:
			case NW:
			case SW:
				updatePositionXBackwards();
				break;
			default:
				break;
			}

			break;

		case TOP:
			updatePositionXBackwards();

			switch (getDirection()) {
			case NORTH:
			case NW:
			case NE:
				updatePositionYBackwards();
				break;

			default:
				break;
			}

			break;

		case RIGHT:
			updatePositionYBackwards();

			switch (getDirection()) {
			case EAST:
			case NE:
			case SE:
				updatePositionXBackwards();
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}
	}

	protected void increaseTranslationX(){
		translation.x += getAbsDeltaX();
	}

	protected void increaseTranslationY(){
		translation.y += getAbsDeltaY();
	}

	protected void decreaseTranslationX(){
		translation.x -= getAbsDeltaX();
	}

	protected void decreaseTranslationY(){
		translation.y -= getAbsDeltaY();
	}


	/******************************************************************************************************************/

	public void update(GameManager gameManager) {

		updateVertex();
		updateDirection();

		switch (this.state) {
		case ACCELERATION_FORWARD:
			inAcceleration();
			updateSteeringForwards();
			updateGears();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderForwards();
			else 
				updatePositionForwards();

			if (GameManager.intersectBorder(this) == Border.UNDEFINED) {
				if (currentSpeed < maxSpeed)
					currentSpeed += getPercentageAccelerationIncreaseForwardsMarch();
				else
					currentSpeed = maxSpeed;
			} else {
				currentSpeed = 0d;
			}

			break;

		case ACCELERATION_BACKWARD:
			inAcceleration();
			updateSteeringBackwards();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderBackwards();
			else
				updatePositionBackwards();

			if (GameManager.intersectBorder(this) == Border.UNDEFINED) {
				if (currentSpeed < getMaxSpeedBackwardsMarch())
					currentSpeed += getPercentageAccelerationIncreaseBackwardsMarch();
				else
					currentSpeed = getMaxSpeedBackwardsMarch();
			} else {
				currentSpeed = 0d;
			}
			break;

		case DECELERATION_FORWARD:
			inDeceleraition();
			updateSteeringForwards();
			updateGears();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderForwards();
			else
				updatePositionForwards();

			if (currentSpeed > getActualInitialAcceleration())
				currentSpeed -= getPercentageAccelerationIncreaseForwardsMarch();
			break;

		case DECELERATION_BACKWARD:
			inDeceleraition();
			updateSteeringBackwards();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderBackwards();
			else
				updatePositionBackwards();

			if (currentSpeed > getActualInitialAcceleration())
				currentSpeed -= getPercentageAccelerationIncreaseBackwardsMarch();
			break;
		case STOP:
		default:
			break;
		}

	}

}
