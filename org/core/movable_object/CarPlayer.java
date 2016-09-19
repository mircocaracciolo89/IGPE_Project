package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.GameManager.BorderArea;

public class CarPlayer extends Vehicle {

	/******************* VARIABLES **********************************************************************************************/

	protected static Point2D.Double translation;
	protected Point2D.Double positionOnEnvironment;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public CarPlayer(Point2D.Double startPoint, Point2D.Double startTranslation, Double startOrientation, Image img) {
		super(startPoint.x, startPoint.y);

		//		translation = new Point2D.Double(0d, 0d);
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
		//		maxSpeed = 20d;
		//		initialAcceleration = 3d;
		maxSpeed = 25d + (27d - 25d) * new Random().nextDouble();
		initialAcceleration = 3.9d + (4.5d - 3.9d) * new Random().nextDouble();		
	}

	/******************* GETTERS **********************************************************************************************/

	public static Point2D.Double 	getTranslation() { return translation; }

	public Point2D.Double	getVertexLeftFrontOnEnvironment() 	{ return new Point2D.Double( (vertexLeftFront.x + Math.abs(translation.x)), (vertexLeftFront.y + Math.abs(translation.y))); }
	public Point2D.Double	getVertexLeftBackOnEnvironment() 	{ return new Point2D.Double( (vertexLeftBack.x + Math.abs(translation.x)), (vertexLeftBack.y + Math.abs(translation.y))); }
	public Point2D.Double	getVertexRightFrontOnEnvironment() 	{ return new Point2D.Double( (vertexRightFront.x + Math.abs(translation.x)), (vertexRightFront.y + Math.abs(translation.y))); }
	public Point2D.Double	getVertexRightBackOnEnvironment() 	{ return new Point2D.Double( (vertexRightBack.x + Math.abs(translation.x)), (vertexRightBack.y + Math.abs(translation.y))); }


	public Line2D.Double	getLeftLineOnEnvironment() 	{ return new Line2D.Double(getVertexLeftBackOnEnvironment().x, getVertexLeftBackOnEnvironment().y, getVertexLeftFrontOnEnvironment().x, getVertexLeftFrontOnEnvironment().y); }
	public Line2D.Double 	getRightLineOnEnvironment() { return new Line2D.Double(getVertexRightBackOnEnvironment().x, getVertexRightBackOnEnvironment().y, getVertexRightFrontOnEnvironment().x, getVertexRightFrontOnEnvironment().y); }
	public Line2D.Double 	getFrontLineOnEnvironment() { return new Line2D.Double(getVertexLeftFrontOnEnvironment().x, getVertexLeftFrontOnEnvironment().y, getVertexRightFrontOnEnvironment().x, getVertexRightFrontOnEnvironment().y); }
	public Line2D.Double 	getBackLineOnEnvironment() 	{ return new Line2D.Double(getVertexRightBackOnEnvironment().x, getVertexRightBackOnEnvironment().y, getVertexLeftBackOnEnvironment().x, getVertexLeftBackOnEnvironment().y); }

	public Point2D.Double getPositionOnEnvironment() { 
		positionOnEnvironment.x = position.x + Math.abs(translation.x);
		positionOnEnvironment.y = position.y + Math.abs(translation.y);
		return positionOnEnvironment;
	}

	/******************* SETTERS **********************************************************************************************/

	public void 	setTranslation(Point2D.Double t) 	{ translation = t; }
	public void 	setTranslationX(Double x) 			{ translation.x = x; }
	public void 	setTranslationY(Double y) 			{ translation.y = y; }

	/******************* SERVICE METHODS **********************************************************************************************/

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
		//System.out.println(orientation_inDegrees);
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

	public void updateVehicleOnBorderAreaForwards() {

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

	public void updateVehicleOnBorderAreaBackwards () {

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

	protected void increaseTranslationX()	{ translation.x += getAbsDeltaX(); }

	protected void increaseTranslationY()	{ translation.y += getAbsDeltaY(); }

	protected void decreaseTranslationX()	{ translation.x -= getAbsDeltaX(); }

	protected void decreaseTranslationY()   { translation.y -= getAbsDeltaY(); }

	/******************************************************************************************************************/

	public void update(GameManager gameManager) {

		//		maxPosition = null;
		updateVertex();
		updateDirection();
		updateStateOnRacetrack();
		fixOrientation_inDegrees();
		GameManager.intersect(this);
		GameManager.intersectBetweenVehicles(this);

		switch (this.state) {
		case ACCELERATION_FORWARD:
			inAcceleration();
			updateSteeringForwards();
			updateGears();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderAreaForwards();
			else 
				updatePositionForwards(null);

			fixCurrentSpeedInAccelerationForwards();

			break;

		case ACCELERATION_BACKWARD:
			inAcceleration();
			updateSteeringBackwards();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderAreaBackwards();
			else
				updatePositionBackwards(null);

			fixCurrentSpeedInAccelerationBackwards();

			//			if (maxPosition == null) {
			//				if (currentSpeed < getMaxSpeedBackwardsMarch())
			//					currentSpeed += getPercentageAccelerationBackwards();
			//				else
			//					currentSpeed = getMaxSpeedBackwardsMarch();
			//			}
			//			else {
			//				maxPosition = null;
			//			}

			//			if (GameManager.intersectBorder(this) == Border.UNDEFINED) {
			//				if (currentSpeed < getMaxSpeedBackwardsMarch())
			//					currentSpeed += getPercentageAccelerationBackwards();
			//				else
			//					currentSpeed = getMaxSpeedBackwardsMarch();
			//			} else {
			//				currentSpeed = 1d;
			//				updatePositionForwards(null);
			//			}

			break;

		case DECELERATION_FORWARD:
			inDeceleraition();
			updateSteeringForwards();
			updateGears();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderAreaForwards();
			else
				updatePositionForwards(null);

			//			if (GameManager.intersectBorder(this) != Border.UNDEFINED) {
			//				currentSpeed = 1d;
			//				updatePositionBackwards(null);
			//			}

			if (currentSpeed > getActualInitialAcceleration())
				currentSpeed -= getPercentageAccelerationForwards();
			else {
				this.state = VehicleState.STOP;
				currentSpeed = 0d;
			}

			break;

		case DECELERATION_BACKWARD:
			inDeceleraition();
			updateSteeringBackwards();

			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
				updateVehicleOnBorderAreaBackwards();
			else
				updatePositionBackwards(null);

			//			if (GameManager.intersectBorder(this) != Border.UNDEFINED) {
			//				currentSpeed = 1d;
			//				updatePositionForwards(null);
			//			}

			if (currentSpeed > getActualInitialAcceleration()) 
				currentSpeed -= getPercentageAccelerationBackwards();
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
