package org.core.movable_object;

import java.awt.Image;
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
		UNDEFINED(0x0), ON_BONUS(0x1), ON_MALUS(0x2), OFF_TRACK(0x3), ON_TRACK(0x4), WRONG_DIRECTION(0x5);
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

	public enum Crash {
		UNDEFINED(0x0), LEFT_LINE_WITH_LEFT_LINE(0x1), FRONT_LINE_WITH_LEFT_LINE(0x2), RIGHT_LINE_WITH_LEFT_LINE(0x3), BACK_LINE_WITH_LEFT_LINE(0x4)
		,				LEFT_LINE_WITH_RIGHT_LINE(0x5), FRONT_LINE_WITH_RIGHT_LINE(0x6), RIGHT_LINE_WITH_RIGHT_LINE(0x7), BACK_LINE_WITH_RIGHT_LINE(0x8)
		,				LEFT_LINE_WITH_FRONT_LINE(0x9), FRONT_LINE_WITH_FRONT_LINE(0x10), RIGHT_LINE_WITH_FRONT_LINE(0X11), BACK_LINE_WITH_FRONT_LINE(0x12)
		,				LEFT_LINE_WITH_BACK_LINE(0x13), FRONT_LINE_WITH_BACK_LINE(0x14), RIGHT_LINE_WITH_BACK_LINE(0x15), BACK_LINE_WITH_BACK_LINE(0x16);
		private int format;
		Crash (int format) {
			this.format = format;
		}

		public static Crash fromInteger(int format) throws IllegalArgumentException {
			for (Crash dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static Crash getDefault() {
			return Crash.UNDEFINED;
		}

	}

	/******************* VARIABLES **********************************************************************************************/

	protected Image 			image;
	protected Integer 			height;
	protected Integer 			width;

	protected Point2D.Double 	position;
	protected Point2D.Double 	maxPosition;
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
	protected Border 					border;
	protected Boolean 					braking;

	protected Boolean	isNewLap;
	protected Integer	lapCounter;
	protected Integer	ranking;
	protected Integer 	indexCheckpointNumber;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public Vehicle(double x, double y) {

		position = new Point2D.Double(x, y);
		maxPosition = null;
		
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
		border = Border.UNDEFINED;
		braking = false;

		isNewLap = false;
		lapCounter = 0;
		indexCheckpointNumber = 1;
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
		case ON_TRACK:
		case WRONG_DIRECTION:
		case UNDEFINED:
		default:
			return maxSpeed; 
		}
	}
	public Double 	getMaxSpeedBackwardsMarch() 	{ return getActualMaxSpeed() - (getActualMaxSpeed() * 0.8d); }
	public Double 	getInitialAcceleration() 		{ return initialAcceleration; }
	public Double 	getActualInitialAcceleration() 	{ return initialAcceleration/mass; }
	public Double 	getCurrentSpeed() 				{ return currentSpeed; } 
	public Double 	getActualPercentageSpeed() 		{ return ((currentSpeed * 100) / getActualMaxSpeed()); }
	public Double 	getPercentageSpeedOf(double v)	{ return (getActualMaxSpeed() * (v / 100d)); }

	public Double 	getDeltaX()			{ return (currentSpeed * getVersors().x); }
	public Double 	getDeltaY()			{ return (currentSpeed * getVersors().y); }
	public Double 	getAbsDeltaX()		{ return (currentSpeed * Math.abs(getVersors().x)); }
	public Double 	getAbsDeltaY()		{ return (currentSpeed * Math.abs(getVersors().y)); }

	public Integer 	getCurrentGear() 	{ return currentGear; } 
	public Integer 	getGearsNumber() 	{ return gearsNumber; } 

	public Double 	getSteeringAngle() 			{ return steeringAngle;	}
	public Double 	getSteeringAngleInBraking() { return steeringAngle + (steeringAngle * 0.3d);	}

	public Double	getPercentageAccelerationForwards()		{ return (((getActualMaxSpeed() * getActualInitialAcceleration())/(1000d * (currentGear * 3))) * currentSpeed); }
	public Double	getPercentageAccelerationBackwards()	{ return (((getActualMaxSpeed() * getActualInitialAcceleration())/800d) * currentSpeed); }
	public Double	getPercentageDeceleration()				{ 
		double divisor = braking ? 200d : 300d;
		return (((getActualMaxSpeed() * getActualInitialAcceleration())/(divisor * (currentGear * 3))) * currentSpeed);
	}

	public VehicleState 			getVehicleState() 		{ return state; }
	public VehicleStateOnRacetrack 	getStateOnRacetrack() 	{ return stateOnRacetrack; }
	public OnSteering 				getOnSteering() 		{ return onSteering; }
	public Direction				getDirection()			{ return direction; }
	public DegreesRange				getDegreesRange(double orientation_inDegrees) {

		if (orientation_inDegrees < 45d && orientation_inDegrees >= 0d)
			return DegreesRange.PART_0;
		else if (orientation_inDegrees < 90d && orientation_inDegrees >= 45)
			return DegreesRange.PART_1;
		else if (orientation_inDegrees < 135d && orientation_inDegrees >= 90d)
			return DegreesRange.PART_2;
		else if (orientation_inDegrees < 180d && orientation_inDegrees >= 135d)
			return DegreesRange.PART_3;
		else if (orientation_inDegrees < 225d && orientation_inDegrees >= 180d)
			return DegreesRange.PART_4;
		else if (orientation_inDegrees < 270d && orientation_inDegrees >= 225d)
			return DegreesRange.PART_5;
		else if (orientation_inDegrees < 315d && orientation_inDegrees >= 270d)
			return DegreesRange.PART_6;
		else if (orientation_inDegrees < 360d && orientation_inDegrees >= 315d)
			return DegreesRange.PART_7;

		return null;
	}

	public Crash getCrash(Vehicle vehicle) {

		if (this instanceof CarPlayer) {

			if (((CarPlayer) this).getFrontLineOnEnvironment().intersectsLine(vehicle.getFrontLine()))
				return Crash.FRONT_LINE_WITH_FRONT_LINE;
			else if (((CarPlayer) this).getFrontLineOnEnvironment().intersectsLine(vehicle.getBackLine()))
				return Crash.FRONT_LINE_WITH_BACK_LINE;
			else if (((CarPlayer) this).getFrontLineOnEnvironment().intersectsLine(vehicle.getLeftLine()))
				return Crash.FRONT_LINE_WITH_LEFT_LINE;
			else if (((CarPlayer) this).getFrontLineOnEnvironment().intersectsLine(vehicle.getRightLine()))
				return Crash.FRONT_LINE_WITH_RIGHT_LINE;

			else if (((CarPlayer) this).getBackLineOnEnvironment().intersectsLine(vehicle.getFrontLine()))
				return Crash.BACK_LINE_WITH_FRONT_LINE;
			else if (((CarPlayer) this).getBackLineOnEnvironment().intersectsLine(vehicle.getBackLine()))
				return Crash.BACK_LINE_WITH_BACK_LINE;
			else if (((CarPlayer) this).getBackLineOnEnvironment().intersectsLine(vehicle.getLeftLine()))
				return Crash.BACK_LINE_WITH_LEFT_LINE;
			else if (((CarPlayer) this).getBackLineOnEnvironment().intersectsLine(vehicle.getRightLine()))
				return Crash.BACK_LINE_WITH_RIGHT_LINE;

			else if (((CarPlayer) this).getLeftLineOnEnvironment().intersectsLine(vehicle.getFrontLine()))
				return Crash.LEFT_LINE_WITH_FRONT_LINE;
			else if (((CarPlayer) this).getLeftLineOnEnvironment().intersectsLine(vehicle.getBackLine()))
				return Crash.LEFT_LINE_WITH_BACK_LINE;
			else if (((CarPlayer) this).getLeftLineOnEnvironment().intersectsLine(vehicle.getLeftLine()))
				return Crash.LEFT_LINE_WITH_LEFT_LINE;
			else if (((CarPlayer) this).getLeftLineOnEnvironment().intersectsLine(vehicle.getRightLine()))
				return Crash.LEFT_LINE_WITH_RIGHT_LINE;

			else if (((CarPlayer) this).getRightLineOnEnvironment().intersectsLine(vehicle.getFrontLine()))
				return Crash.RIGHT_LINE_WITH_FRONT_LINE;
			else if (((CarPlayer) this).getRightLineOnEnvironment().intersectsLine(vehicle.getBackLine()))
				return Crash.RIGHT_LINE_WITH_BACK_LINE;
			else if (((CarPlayer) this).getRightLineOnEnvironment().intersectsLine(vehicle.getLeftLine()))
				return Crash.RIGHT_LINE_WITH_LEFT_LINE;
			else if (((CarPlayer) this).getRightLineOnEnvironment().intersectsLine(vehicle.getRightLine()))
				return Crash.RIGHT_LINE_WITH_RIGHT_LINE;

		} else {

			if (this.getFrontLine().intersectsLine(vehicle.getFrontLine()))
				return Crash.FRONT_LINE_WITH_FRONT_LINE;
			else if (this.getFrontLine().intersectsLine(vehicle.getBackLine()))
				return Crash.FRONT_LINE_WITH_BACK_LINE;
			else if (this.getFrontLine().intersectsLine(vehicle.getLeftLine()))
				return Crash.FRONT_LINE_WITH_LEFT_LINE;
			else if (this.getFrontLine().intersectsLine(vehicle.getRightLine()))
				return Crash.FRONT_LINE_WITH_RIGHT_LINE;

			else if (this.getBackLine().intersectsLine(vehicle.getFrontLine()))
				return Crash.BACK_LINE_WITH_FRONT_LINE;
			else if (this.getBackLine().intersectsLine(vehicle.getBackLine()))
				return Crash.BACK_LINE_WITH_BACK_LINE;
			else if (this.getBackLine().intersectsLine(vehicle.getLeftLine()))
				return Crash.BACK_LINE_WITH_LEFT_LINE;
			else if (this.getBackLine().intersectsLine(vehicle.getRightLine()))
				return Crash.BACK_LINE_WITH_RIGHT_LINE;

			else if (this.getLeftLine().intersectsLine(vehicle.getFrontLine()))
				return Crash.LEFT_LINE_WITH_FRONT_LINE;
			else if (this.getLeftLine().intersectsLine(vehicle.getBackLine()))
				return Crash.LEFT_LINE_WITH_BACK_LINE;
			else if (this.getLeftLine().intersectsLine(vehicle.getLeftLine()))
				return Crash.LEFT_LINE_WITH_LEFT_LINE;
			else if (this.getLeftLine().intersectsLine(vehicle.getRightLine()))
				return Crash.LEFT_LINE_WITH_RIGHT_LINE;

			else if (this.getRightLine().intersectsLine(vehicle.getFrontLine()))
				return Crash.RIGHT_LINE_WITH_FRONT_LINE;
			else if (this.getRightLine().intersectsLine(vehicle.getBackLine()))
				return Crash.RIGHT_LINE_WITH_BACK_LINE;
			else if (this.getRightLine().intersectsLine(vehicle.getLeftLine()))
				return Crash.RIGHT_LINE_WITH_LEFT_LINE;
			else if (this.getRightLine().intersectsLine(vehicle.getRightLine()))
				return Crash.RIGHT_LINE_WITH_RIGHT_LINE;

		}

		return Crash.UNDEFINED;
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

	public Boolean 			isNewLap() 					{ return isNewLap; }
	public Integer 			getLapCounter() 			{ return lapCounter; }
	public Integer 			getRanking() 				{ return ranking; }
	public Integer 			getIndexCheckpointNumber()  { return indexCheckpointNumber; }

	/******************* SETTERS **********************************************************************************************/

	public void 	setPosition(Point2D.Double p) 		{ 
		setPositionX(p.x); 
		setPositionY(p.y); 

		//		if (this instanceof CarPlayer ) {
		//			if (GameManager.getActualBorderArea() != BorderArea.UNDEFINED)
		//				((CarPlayer) this).updateVehicleOnBorderForwards();
		//			else 
		//				updatePositionForwards();
		//		}

	}
	public void 	setPositionX(Double x) 						{ position.x = x; }
	public void 	setPositionY(Double y) 						{ position.y = y; }
	public void 	setMaxPosition(Point2D.Double maxPosition) 	{ this.maxPosition = maxPosition; }

	public void 	setMass(Double m) 					{ mass = m; }
	public void 	setMaxSpeed(Double m) 				{ maxSpeed = m; }
	public void		setInitialAcceleration(Double a)	{ initialAcceleration = a; }
	public void 	setCurrentSpeed(Double c) 			{ currentSpeed = c; }
	public void 	setOrientation_inDegrees(Double o) 	{ orientation_inDegrees = o; }

	public void 	setGear(Integer g)	{ currentGear = g; }

	public void 	setSteeringAngle(Double f)	{ steeringAngle = f; }

	public void 	setVehicleState(VehicleState state) 				{ this.state = state; }
	public void 	setOnSteering(OnSteering onSteering) 				{ this.onSteering = onSteering; }
	public void 	setStateOnRacetrack(VehicleStateOnRacetrack value)	{ this.stateOnRacetrack = value; }
	public void 	setBorder(Border border) 							{ this.border = border; }
	public void 	setBraking(boolean value) 							{ braking = value; }

	//	public void 	setIsNewLap(Boolean isNewLap) 							{ this.isNewLap = isNewLap; }
	//	public void 	setLapCounter(Integer lapCounter) 						{ this.lapCounter = lapCounter; }
	//	public void 	setRanking(Integer ranking) 							{ this.ranking = ranking; }
	//	public void 	setIndexCheckpointNumber(Integer indexCheckpointNumber) { this.indexCheckpointNumber = indexCheckpointNumber; }

	/******************* SERVICE METHODS **********************************************************************************************/

	public void updateStateOnRacetrack() {

		if (GameManager.isCorrectDirection(this)) {

			if( GameManager.intersectCheckpoint(this)) {
				indexCheckpointNumber = (indexCheckpointNumber + 1) % GameManager.getEnvironment().getRacetrack().getCheckpoints().size();
				if (indexCheckpointNumber == 1) isNewLap = false;
				if (stateOnRacetrack != VehicleStateOnRacetrack.UNDEFINED)
					stateOnRacetrack = VehicleStateOnRacetrack.UNDEFINED;
			}

			if (stateOnRacetrack == VehicleStateOnRacetrack.WRONG_DIRECTION)
				stateOnRacetrack = VehicleStateOnRacetrack.UNDEFINED;

		} else
			stateOnRacetrack = VehicleStateOnRacetrack.WRONG_DIRECTION;

		if (GameManager.intersectStartLine(this) && !isNewLap) {
			lapCounter++;
			isNewLap = true;
		}

		if (!GameManager.isOnTrack(this))
			stateOnRacetrack = VehicleStateOnRacetrack.OFF_TRACK;
		else {
			if (stateOnRacetrack == VehicleStateOnRacetrack.OFF_TRACK)
				stateOnRacetrack = VehicleStateOnRacetrack.ON_TRACK;
		}

	}

	public void updatePositionXForwards() { 

		if (maxPosition == null)
			position.x += getDeltaX();

		else {

			System.out.println("maxPosition.x "+maxPosition.x);

			switch (getDirection()) {
			case EAST:
			case NE:
			case SE:
				if ((position.x + getDeltaX()) <= maxPosition.x)
					position.x += getDeltaX();
				else {
					currentSpeed = 2d;
					position.x -= (getDeltaX() * 2);
				}
				break;

			case WEST:
			case NW:
			case SW:
				if ((position.x + getDeltaX()) >= maxPosition.x)
					position.x += getDeltaX();
				else {
					currentSpeed = 2d;
					position.x -= (getDeltaX() * 2);
				}
				break;

			default:
				break;
			}

		}

	}
	public void updatePositionYForwards(){ 
		if (maxPosition == null)
			position.y += getDeltaY();

		else {
			System.out.println("maxPosition.y "+maxPosition.y);

			switch (getDirection()) {
			case NORTH:
			case NE:
			case NW:
				if ((position.y + getDeltaY()) > maxPosition.y)
					position.y += getDeltaY();
				else {
					currentSpeed = 2d;
					position.y -= (getDeltaY() * 2);
				}
				break;

			case SOUTH:
			case SE:
			case SW:
				if ((position.y + getDeltaY()) < maxPosition.y)
					position.y += getDeltaY();
				else {
					currentSpeed = 2d;
					position.y -= (getDeltaY() * 2);
				}
				break;

			default:
				break;
			}

		}
	}

	public void updatePositionXBackwards(){ 
		if (maxPosition == null)
			position.x -= getDeltaX();
		else {
			System.out.println("maxPosition.x "+maxPosition.x);

			switch (getDirection()) {
			case EAST:
			case NE:
			case SE:
				if ((position.x - getDeltaX()) > maxPosition.x)
					position.x -= getDeltaX();
				else {
					currentSpeed = 2d;
					position.x += (getDeltaX() * 2);
				}
				break;

			case WEST:
			case NW:
			case SW:
				if ((position.x - getDeltaX()) < maxPosition.x)
					position.x -= getDeltaX();
				else {
					currentSpeed = 2d;
					position.x += (getDeltaX() * 2);
				}
				break;

			default:
				break;
			}

		}
	}
	public void updatePositionYBackwards() { 
		if (maxPosition == null)
			position.y -= getDeltaY();
		else {
			System.out.println("maxPosition.y "+maxPosition.y);

			switch (getDirection()) {
			case NORTH:
			case NE:
			case NW:
				if ((position.y - getDeltaY()) < maxPosition.y)
					position.y -= getDeltaY();
				else {
					currentSpeed = 2d;
					position.y += (getDeltaY() * 2);
				}
				break;

			case SOUTH:
			case SE:
			case SW:
				if ((position.y - getDeltaY()) > maxPosition.y)
					position.y -= getDeltaY();
				else {
					currentSpeed = 2d;
					position.y += (getDeltaY() * 2);
				}
				break;

			default:
				break;
			}

		}
	}

	public void fixOrientation_inDegrees() 	{
		orientation_inDegrees = orientation_inDegrees % 359d;
		if (orientation_inDegrees < 0d)
			orientation_inDegrees += 360;
	}

	public void fixCurrentSpeedInAccelerationForwards() {
		//		if (GameManager.intersectBorder(this) == Border.UNDEFINED) {
		if (maxPosition == null) {
			if (currentSpeed < getActualMaxSpeed())
				currentSpeed += getPercentageAccelerationForwards();
			else
				currentSpeed = getActualMaxSpeed();
		}
		else {
			maxPosition = null;
		}
	}

	public void fixCurrentSpeedInAccelerationBackwards() {
		if (maxPosition == null) {
			if (currentSpeed < getMaxSpeedBackwardsMarch())
				currentSpeed += getPercentageAccelerationBackwards();
			else
				currentSpeed = getMaxSpeedBackwardsMarch();
		}
		else {
			maxPosition = null;
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

	public void updatePositionForwards(Border border) {

		updatePositionXForwards();
		updatePositionYForwards();

		//		switch ((border == null) ? GameManager.intersectBorder(this) : border) {
		//		//		switch (border) {
		//
		//		case UNDEFINED:
		//			updatePositionXForwards();
		//			updatePositionYForwards();
		//			break;
		//
		//		case TOP:
		//			updatePositionXForwards();
		//
		//			switch (getDirection()) {
		//			case SOUTH:
		//			case SE:
		//			case SW:
		//				updatePositionYForwards();
		//				break;
		//			default:
		//				break;
		//			}
		//			break;
		//
		//		case RIGHT:
		//			updatePositionYForwards();
		//
		//			switch (getDirection()) {
		//			case WEST:
		//			case NW:
		//			case SW:
		//				updatePositionXForwards();
		//				break;
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		case BOTTOM:
		//			updatePositionXForwards();
		//
		//			switch (getDirection()) {
		//			case NORTH:
		//			case NW:
		//			case NE:
		//				updatePositionYForwards();
		//				break;
		//
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		case LEFT:
		//			updatePositionYForwards();
		//
		//			switch (getDirection()) {
		//			case EAST:
		//			case NE:
		//			case SE:
		//				updatePositionXForwards();
		//				break;
		//
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		default:
		//			break;
		//		}

	}

	public void updatePositionBackwards(Border border) {

		updatePositionXBackwards();
		updatePositionYBackwards();

		//		switch ((border == null) ? GameManager.intersectBorder(this) : border) {
		//
		//		case UNDEFINED:
		//			updatePositionXBackwards();
		//			updatePositionYBackwards();
		//			break;
		//
		//		case BOTTOM:
		//			updatePositionXBackwards();
		//
		//			switch (getDirection()) {
		//			case SOUTH:
		//			case SE:
		//			case SW:
		//				updatePositionYBackwards();
		//				break;
		//			default:
		//				break;
		//			}
		//			break;
		//
		//		case LEFT:
		//			updatePositionYBackwards();
		//
		//			switch (getDirection()) {
		//			case WEST:
		//			case NW:
		//			case SW:
		//				updatePositionXBackwards();
		//				break;
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		case TOP:
		//			updatePositionXBackwards();
		//
		//			switch (getDirection()) {
		//			case NORTH:
		//			case NW:
		//			case NE:
		//				updatePositionYBackwards();
		//				break;
		//
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		case RIGHT:
		//			updatePositionYBackwards();
		//
		//			switch (getDirection()) {
		//			case EAST:
		//			case NE:
		//			case SE:
		//				updatePositionXBackwards();
		//				break;
		//
		//			default:
		//				break;
		//			}
		//
		//			break;
		//
		//		default:
		//			break;
		//		}
	}

	/******************************************************************************************************************/

	protected void inAcceleration() {

		if (currentSpeed <= 2d)
			currentSpeed = getActualInitialAcceleration();

		if (braking)
			currentSpeed -= getPercentageDeceleration();

	}

	/******************************************************************************************************************/

	protected void inDeceleraition() {

		if (braking)
			currentSpeed -= getPercentageDeceleration();

		if (currentSpeed <= getActualInitialAcceleration()) {
			this.state = VehicleState.STOP;
			currentSpeed = 0d;
		}
	}

}