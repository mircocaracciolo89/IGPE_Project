package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.core.GameManager;
import org.core.GameManager.Border;

public abstract class Vehicle extends MovableObject {

	/******************* ENUM **********************************************************************************************/

	public enum VehicleState { 
		STOP(0x0), ACCELERATION_FORWARD(0x1), ACCELERATION_BACKWARD(0x2), DECELERATION_FORWARD(0x3), DECELERATION_BACKWARD(0x4);
		private int format;
		VehicleState (int format) {
			this.format = format;
		}

		public static VehicleState fromInteger(int format) throws IllegalArgumentException {
			for (VehicleState dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static VehicleState getDefault() {
			return STOP;
		}
	}

	public enum OnSteering { 
		UNDEFINED(0x0), LEFT(0x1), RIGHT(0x2);
		private int format;
		OnSteering (int format) {
			this.format = format;
		}

		public static OnSteering fromInteger(int format) throws IllegalArgumentException {
			for (OnSteering dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static OnSteering getDefault() {
			return UNDEFINED;
		}
	}

	public enum VehicleStateOnRacetrack { 
		UNDEFINED(0x0), ON_BONUS(0x1), ON_MALUS(0x2), OFF_TRACK(0X3);
		private int format;
		VehicleStateOnRacetrack (int format) {
			this.format = format;
		}

		public static VehicleStateOnRacetrack fromInteger(int format) throws IllegalArgumentException {
			for (VehicleStateOnRacetrack dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static VehicleStateOnRacetrack getDefault() {
			return UNDEFINED;
		}
	}

	public enum Direction {
		UNDEFINED(0x0), NORTH(0x1), SOUTH(0x2), EAST(0x3), WEST(0x4), NE(0x5), NW(0x6), SE(0x7), SW(0x8);
		private int format;
		Direction (int format) {
			this.format = format;
		}

		public static Direction fromInteger(int format) throws IllegalArgumentException {
			for (Direction dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static Direction getDefault() {
			return UNDEFINED;
		}

	}

	public enum DegreesRange {
		PART_0(0x0), PART_1(0x1), PART_2(0x2), PART_3(0x3), PART_4(0x4), PART_5(0x5), PART_6(0x6), PART_7(0x7);
		private int format;
		DegreesRange (int format) {
			this.format = format;
		}

		public static DegreesRange fromInteger(int format) throws IllegalArgumentException {
			for (DegreesRange dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static DegreesRange getDefault() {
			return PART_0;
		}

	}

	/******************* VARIABLES **********************************************************************************************/

	protected Image 			image;
	protected Integer 			height;
	protected Integer 			width;

	protected Point2D.Double 	position;
	protected Double 			orientation_inDegrees;

	protected Double 			mass;
	protected Double 			maxSpeed;
	protected Double 			initialAcceleration;
	protected Double 			currentSpeed;

	protected Integer			gearsNumber;
	protected Integer			currentGear;

	protected Double 			steeringAngle;

	protected Point2D.Double 	vertexRightBack;
	protected Point2D.Double 	vertexLeftBack;
	protected Point2D.Double 	vertexRightFront;
	protected Point2D.Double 	vertexLeftFront;

	protected VehicleState 				state;
	protected VehicleStateOnRacetrack	stateOnRacetrack;
	protected OnSteering 				onSteering;
	protected Direction 				direction;
	protected Boolean 					braking;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public Vehicle(double x, double y) {
		
		position = new Point2D.Double(x, y);

		orientation_inDegrees = mass = maxSpeed = initialAcceleration = currentSpeed = steeringAngle = null;
		gearsNumber = null;
		currentGear = 1;
		currentSpeed = 0d;

		vertexRightBack = new Point2D.Double();
		vertexLeftBack = new Point2D.Double();
		vertexRightFront = new Point2D.Double();
		vertexLeftFront = new Point2D.Double();

		state = VehicleState.STOP;
		onSteering = OnSteering.UNDEFINED;
		stateOnRacetrack = VehicleStateOnRacetrack.UNDEFINED;
		braking = false;

	}

	/******************* GETTERS **********************************************************************************************/

	public Image 			getImage() 		{ return image; }
	public int 				getHeight() 	{ return height; }
	public int 				getWidth() 		{ return width; }

	public Point2D.Double 	getPosition() 				{ return position; }
	public Double 			getOrientation_inDegrees() 	{ return orientation_inDegrees;	}
	public Double 			getOrientation_inRadian() 	{ return ((orientation_inDegrees/180) * Math.PI);	}
	public Point2D.Double	getVersors() 				{ return new Point2D.Double(Math.cos(getOrientation_inRadian()), Math.sin(getOrientation_inRadian())); }

	public Double 			getMass() 						{ return mass; }
	public Double 			getMaxSpeed() 					{ return maxSpeed; }
	public Double 			getActualMaxSpeed() 			{ 
		switch (stateOnRacetrack) {
		case ON_BONUS:
			return maxSpeed + (0.3d * maxSpeed);
		case ON_MALUS:
			return maxSpeed - (0.3d * maxSpeed);
		case OFF_TRACK:
			return maxSpeed - (0.5d * maxSpeed);
		case UNDEFINED:
		default:
			return maxSpeed; 
		}
	}
	public Double 			getMaxSpeedBackwardsMarch() 	{ return getActualMaxSpeed() - (getActualMaxSpeed() * 0.8d); }
	public Double 			getInitialAcceleration() 		{ return initialAcceleration; }
	public Double 			getActualInitialAcceleration() 	{ return initialAcceleration/mass; }
	public Double 			getCurrentSpeed() 				{ return currentSpeed; } 
	public Double 			getActualPercentageSpeed() 		{ return ((currentSpeed * 100) / getActualMaxSpeed()); }
	public Double 			getPercentageSpeedOf(double v)	{ return (getActualMaxSpeed() * (v / 100d)); }

	public Double 			getDeltaX()			{ return (currentSpeed * getVersors().x); }
	public Double 			getDeltaY()			{ return (currentSpeed * getVersors().y); }
	public Double 			getAbsDeltaX()		{ return (currentSpeed * Math.abs(getVersors().x)); }
	public Double 			getAbsDeltaY()		{ return (currentSpeed * Math.abs(getVersors().y)); }

	public Integer 			getCurrentGear() 	{ return currentGear; } 
	public Integer 			getGearsNumber() 	{ return gearsNumber; } 

	public Double 			getSteeringAngle() 			{ return steeringAngle;	}
	public Double 			getSteeringAngleInBraking() { return steeringAngle + (steeringAngle * 0.3d);	}

	public Double			getPercentageAccelerationIncreaseForwardsMarch()	{ return (((getActualMaxSpeed() * getActualInitialAcceleration())/(1000d * (currentGear * 3))) * currentSpeed); }
	public Double			getPercentageAccelerationIncreaseBackwardsMarch()	{ return (((getActualMaxSpeed() * getActualInitialAcceleration())/800d) * currentSpeed); }
	public Double			getPercentageAccelerationDecrease()					{ 
		double divisor = braking ? 200d : 300d;
		return (((getActualMaxSpeed() * getActualInitialAcceleration())/(divisor * (currentGear * 3))) * currentSpeed);
	}

	public VehicleState 			getVehicleState() 		{ return state; }
	public VehicleStateOnRacetrack 	getStateOnRacetrack() 	{ return stateOnRacetrack; }
	public OnSteering 				getOnSteering() 		{ return onSteering; }
	public Direction				getDirection()			{ return direction; }
	public DegreesRange				getDegreesRange(double orientation_inDegrees)		{

		if (orientation_inDegrees < 45d && orientation_inDegrees >= 0d)
			return DegreesRange.PART_0;
		if (orientation_inDegrees < 90d && orientation_inDegrees >= 45)
			return DegreesRange.PART_1;
		if (orientation_inDegrees < 135d && orientation_inDegrees >= 90d)
			return DegreesRange.PART_2;
		if (orientation_inDegrees < 180d && orientation_inDegrees >= 135d)
			return DegreesRange.PART_3;
		if (orientation_inDegrees < (-135d) && orientation_inDegrees >= (-180d))
			return DegreesRange.PART_4;
		if (orientation_inDegrees < (-90d) && orientation_inDegrees >= (-135d))
			return DegreesRange.PART_5;
		if (orientation_inDegrees < (-45d) && orientation_inDegrees >= (-90d))
			return DegreesRange.PART_6;
		if (orientation_inDegrees < 0d && orientation_inDegrees >= (-45d))
			return DegreesRange.PART_7;

		return null;
	}

	public Boolean			isForwardsMarch() 	{ return (state == VehicleState.ACCELERATION_FORWARD || state == VehicleState.DECELERATION_FORWARD); }
	public Boolean			isBackwardsMarch() 	{ return (state == VehicleState.ACCELERATION_BACKWARD || state == VehicleState.DECELERATION_BACKWARD); }
	public Boolean			isBraking() 		{ return braking; }

	public Point2D.Double	getVertexLeftBack()		{ return vertexLeftBack; }
	public Point2D.Double	getVertexRightBack()	{ return vertexRightBack; }
	public Point2D.Double	getVertexLeftFront()	{ return vertexLeftFront; }
	public Point2D.Double	getVertexRightFront()	{ return vertexRightFront; }

	public Line2D.Double	getLeftLine() 	{ return new Line2D.Double(vertexLeftBack.x, vertexLeftBack.y, vertexLeftFront.x, vertexLeftFront.y); }
	public Line2D.Double 	getRightLine() 	{ return new Line2D.Double(vertexRightBack.x, vertexRightBack.y, vertexRightFront.x, vertexRightFront.y); }
	public Line2D.Double 	getFrontLine() 	{ return new Line2D.Double(vertexLeftFront.x, vertexLeftFront.y, vertexRightFront.x, vertexRightFront.y); }
	public Line2D.Double 	getBackLine() 	{ return new Line2D.Double(vertexRightBack.x, vertexRightBack.y, vertexLeftBack.x, vertexLeftBack.y); }

	/******************* SETTERS **********************************************************************************************/

	public void 	setPosition(Point2D.Double p) 		{ position = p; }
	public void 	setPositionX(Double x) 				{ position.x = x; }
	public void 	setPositionY(Double y) 				{ position.y = y; }

	public void 	setMass(Double m) 					{ mass = m; }

	public void 	setMaxSpeed(Double m) 				{ maxSpeed = m; }
	public void		setInitialAcceleration(Double a)	{ initialAcceleration = a; }
	public void 	setCurrentSpeed(Double c) 			{ currentSpeed = c; }

	public void 	setGear(Integer g)	{ currentGear = g; }
	public void 	setHigherGear() 	{ currentGear++; }
	public void 	setLowerGear() 		{ currentGear--; }

	public void 	setSteeringAngle(Double f)	{ steeringAngle = f; }

	public void 	setVehicleState(VehicleState state) 				{ this.state = state; }
	public void 	setOnSteering(OnSteering onSteering) 				{ this.onSteering = onSteering; }
	public void 	setStateOnRacetrack(VehicleStateOnRacetrack value)	{ this.stateOnRacetrack = value; }
	public void 	setBraking(boolean value) 							{ braking = value; }

	/******************* SERVICE METHODS **********************************************************************************************/

	protected void updatePositionXForwards(){ position.x += getDeltaX(); }
	protected void updatePositionYForwards(){ position.y += getDeltaY(); }

	protected void updatePositionXBackwards(){ position.x -= getDeltaX(); }
	protected void updatePositionYBackwards(){ position.y -= getDeltaY(); }

	public void fixOrientation_inDegrees() 	{

			if (orientation_inDegrees < 0d && orientation_inDegrees < (-180d))
				orientation_inDegrees += 360d;
			else if (orientation_inDegrees > 0d && orientation_inDegrees > 180d)
				orientation_inDegrees -= 360d;
			
	}
	
	public void fixCurrentSpeedInAccelerationForwards() {
		if (GameManager.intersectBorder(this) == Border.UNDEFINED) {
			if (currentSpeed < getActualMaxSpeed())
				currentSpeed += getPercentageAccelerationIncreaseForwardsMarch();
			else
				currentSpeed = getActualMaxSpeed();
		} else {
			currentSpeed = 2d;
			updatePositionBackwards();
		}
	}

	public void updateDirection() {
		if(getVersors().x == 1 && getVersors().y == 0)
			this.direction = Direction.EAST;
		else if(getVersors().x > 0 && getVersors().y > 0)
			this.direction = Direction.SE;
		else if(getVersors().x == 0 && getVersors().y == 1)
			this.direction = Direction.SOUTH;
		else if(getVersors().x < 0 && getVersors().y > 0)
			this.direction = Direction.SW;
		else if(getVersors().x == -1 && getVersors().y == 0)
			this.direction = Direction.WEST;
		else if(getVersors().x < 0 && getVersors().y < 0)
			this.direction = Direction.NW;
		else if(getVersors().x == 0 && getVersors().y == -1)
			this.direction = Direction.NORTH;
		else if(getVersors().x > 0 && getVersors().y < 0)
			this.direction = Direction.NE;
		else
			this.direction = Direction.UNDEFINED;
	}

	protected void updateVertex() {

		double 	halfWidth = getWidth()/2, halfHeight = getHeight()/2,

				cosWidth = halfWidth * Math.cos(getOrientation_inRadian()), sinHeight = halfHeight * Math.sin(getOrientation_inRadian()),
				sinWidth = halfWidth * Math.sin(getOrientation_inRadian()), cosHeight = halfHeight * Math.cos(getOrientation_inRadian()),

				x11 = -cosWidth - sinHeight,
				y11 = sinWidth - cosHeight,

				x21 = cosWidth - sinHeight,
				y21 = -sinWidth - cosHeight, 

				x31 = -cosWidth + sinHeight,
				y31 = sinWidth + cosHeight,

				x41 = cosWidth + sinHeight,
				y41 = -sinWidth + cosHeight;


		vertexRightBack.x = position.x + x11; 
		vertexRightBack.y = position.y - y11;

		vertexLeftBack.x = position.x - x21;
		vertexLeftBack.y = position.y + y21;

		vertexRightFront.x = position.x - x31;
		vertexRightFront.y = position.y + y31;

		vertexLeftFront.x = position.x + x41;
		vertexLeftFront.y = position.y - y41;

	}


	protected void updateGears() {

		if (currentSpeed < getPercentageSpeedOf(20d)) {
			setGear(1);
		}
		if (getPercentageSpeedOf(20d) <= currentSpeed && currentSpeed < getPercentageSpeedOf(40d)) {
			setGear(2);
		}
		if (getPercentageSpeedOf(40d) <= currentSpeed && currentSpeed < getPercentageSpeedOf(70d)) {
			setGear(3);
		}
		if (getPercentageSpeedOf(70d) <= currentSpeed && currentSpeed < getPercentageSpeedOf(95d)) {
			setGear(4);
		}
		if (getPercentageSpeedOf(95d) <= currentSpeed) {
			setGear(5);
		}

	}

	/******************************************************************************************************************/

	protected void updatePositionForwards() {

		switch (GameManager.intersectBorder(this)) {

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


		switch (GameManager.intersectBorder(this)) {

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

	/******************************************************************************************************************/

	protected void inAcceleration() {

		if (currentSpeed <= 2d)
			currentSpeed = getActualInitialAcceleration();

		if (braking)
			currentSpeed -= getPercentageAccelerationDecrease();

	}

	/******************************************************************************************************************/

	protected void inDeceleraition() {

		if (braking)
			currentSpeed -= getPercentageAccelerationDecrease();

		if (currentSpeed <= getActualInitialAcceleration()) {
			this.state = VehicleState.STOP;
			currentSpeed = 0d;
		}
	}
	
	

}