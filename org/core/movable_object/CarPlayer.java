package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.GameManager.BorderArea;
import org.core.movable_object.Vehicle.VehicleStateOnRacetrack;
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
		steeringAngle = 7.5d;
		orientation_inDegrees = startOrientation;
		fixOrientation_inDegrees();
		updateDirection();
		direction = getDirection();
		maxSpeed = 32d;
		initialAcceleration = 3.5d;
	}

	public static Point2D.Double 	getTranslation() { return translation; }

	public Line2D.Double	getLeftLineOnEnvironment() 	{ return new Line2D.Double(vertexLeftBack.x + Math.abs(translation.x), vertexLeftBack.y + Math.abs(translation.y), vertexLeftFront.x + Math.abs(translation.x), vertexLeftFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getRightLineOnEnvironment() { return new Line2D.Double(vertexRightBack.x + Math.abs(translation.x), vertexRightBack.y + Math.abs(translation.y), vertexRightFront.x + Math.abs(translation.x), vertexRightFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getFrontLineOnEnvironment() { return new Line2D.Double(vertexLeftFront.x + Math.abs(translation.x), vertexLeftFront.y + Math.abs(translation.y), vertexRightFront.x + Math.abs(translation.x), vertexRightFront.y + Math.abs(translation.y)); }
	public Line2D.Double 	getBackLineOnEnvironment() 	{ return new Line2D.Double(vertexRightBack.x + Math.abs(translation.x), vertexRightBack.y + Math.abs(translation.y), vertexLeftBack.x + Math.abs(translation.x), vertexLeftBack.y + Math.abs(translation.y)); }

	//	public Line2D.Double	getLeftLine() 	{ return getLeftLineOnEnvironment(); }
	//	public Line2D.Double 	getRightLine() 	{ return getRightLineOnEnvironment(); }
	//	public Line2D.Double 	getFrontLine() 	{ return getFrontLineOnEnvironment(); }
	//	public Line2D.Double 	getBackLine() 	{ return getBackLineOnEnvironment(); }

	public Point2D.Double getPositionOnEnvironment() { 
		positionOnEnvironment.x = position.x + Math.abs(translation.x);
		positionOnEnvironment.y = position.y + Math.abs(translation.y);
		return positionOnEnvironment;
	}

	//	public Point2D.Double getPosition() {
	//		return getPositionOnEnvironment();
	//	}

	public GeneralPath getPath() {
		GeneralPath path = new GeneralPath(getFrontLineOnEnvironment());
		path.append(getLeftLineOnEnvironment(), true);
		path.append(getBackLineOnEnvironment(), true);
		path.append(getRightLineOnEnvironment(), true);
		path.closePath();
		return path;
	}


	public void 	setTranslation(Point2D.Double t) 	{ translation = t; }
	public void 	setTranslationX(Double x) 			{ translation.x = x; }
	public void 	setTranslationY(Double y) 			{ translation.y = y; }

	/******************************************************************************************************************/

	public void drawGameObject(Graphics2D g2d) {
		g2d.translate(position.x, position.y);
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(getImage(),AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}

	/******************************************************************************************************************/

	protected void updateSteeringForwards() {

		switch (onSteering) {

		case LEFT:
			orientation_inDegrees -= braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		
		case RIGHT:
			orientation_inDegrees += braking ? getSteeringAngleInBraking() : steeringAngle;
			
			break;
		
		case UNDEFINED:
		default:
			break;
		}

	}

	protected void updateSteeringBackwards() {

		switch (onSteering) {
		
		case LEFT:
			orientation_inDegrees += braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		
		case RIGHT:
			orientation_inDegrees -= braking ? getSteeringAngleInBraking() : steeringAngle;
			break;
		
		case UNDEFINED:
		default:
			break;
		}
		
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
		fixOrientation_inDegrees();

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
				if (currentSpeed < getActualMaxSpeed())
					currentSpeed += getPercentageAccelerationIncreaseForwardsMarch();
				else
					currentSpeed = getActualMaxSpeed();
			} else {
				currentSpeed = 2d;
				updatePositionBackwards();
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
				currentSpeed = 1d;
				updatePositionForwards();
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

			if (GameManager.intersectBorder(this) != Border.UNDEFINED) {
				currentSpeed = 1d;
				updatePositionBackwards();
			}

			if (currentSpeed > getActualInitialAcceleration())
				currentSpeed -= getPercentageAccelerationIncreaseForwardsMarch();
			else {
				this.state = VehicleState.STOP;
				currentSpeed = 0d;
			}

			break;

		case DECELERATION_BACKWARD:
			inDeceleraition();
			updateSteeringBackwards();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderBackwards();
			else
				updatePositionBackwards();

			if (GameManager.intersectBorder(this) != Border.UNDEFINED) {
				currentSpeed = 1d;
				updatePositionForwards();
			}

			if (currentSpeed > getActualInitialAcceleration()) 
				currentSpeed -= getPercentageAccelerationIncreaseBackwardsMarch();
			else {
				this.state = VehicleState.STOP;
				currentSpeed = 0d;
			}

			break;
		case STOP:
		default:
			break;
		}

	}

}
