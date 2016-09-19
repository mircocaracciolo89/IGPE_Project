package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.environment.racetrack.IntelligencePoint;
import org.gui.Loader;
import org.gui.panels.GamePanel;

public class CarComputer extends Vehicle {

	/******************* VARIABLES **********************************************************************************************/

	private List<IntelligencePoint> intelligencePoints;
	private int indexOfIntelligencePoint;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public CarComputer(Point2D.Double startPoint, double startOrientation) {
		super(startPoint.x * GamePanel.SCALE, startPoint.y * GamePanel.SCALE);

		int rand = (int) (Math.random()*4);

		switch (rand) {
		case 0:
			image = Loader.imgRedCar;
			break;
		case 1:
			image = Loader.imgYellowCar;
			break;
		case 2:
			image = Loader.imgGreenCar;
			break;
		case 3:
			image = Loader.imgBlueCar;
			break;

		default:
			image = Loader.imgBlueCar;
			break;
		}

		height = image.getHeight(null);
		width = image.getWidth(null);

		border = Border.UNDEFINED;
		mass = 1d;

		orientation_inDegrees = startOrientation;
		fixOrientation_inDegrees();
		maxSpeed = 12d + (16d - 12d) * new Random().nextDouble();
		initialAcceleration = 2.2d + (2.5d - 2.2d) * new Random().nextDouble();

		intelligencePoints = GameManager.getEnvironment().getRacetrack().getIntelligencePoints();
		indexOfIntelligencePoint = 1;

		state = VehicleState.ACCELERATION_FORWARD;
	}

	/******************* SERVICE METHODS **********************************************************************************************/

	public void drawGameObject(Graphics2D g2d) {
		g2d.translate(position.x + CarPlayer.getTranslation().x, position.y + CarPlayer.getTranslation().y);
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(getImage(),AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}

	/******************************************************************************************************************/

	private void updateSteering(Double value, Double nextDegree) {

		onSteering = OnSteering.UNDEFINED;

		DegreesRange nextRange = getDegreesRange(nextDegree);

		if (getDegreesRange(orientation_inDegrees) == getDegreesRange(nextDegree)) {
			onSteering = orientation_inDegrees > nextDegree ? OnSteering.LEFT : OnSteering.RIGHT;
		}
		else
			switch (getDegreesRange(orientation_inDegrees)) {

			case PART_0:
				switch (nextRange) {
				case PART_4:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_5:
				case PART_6:
				case PART_7:
					onSteering = OnSteering.LEFT;
					break;
				case PART_1:
				case PART_2:
				case PART_3:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_1:
				switch (nextRange) {
				case PART_5:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_0:
				case PART_6:
				case PART_7:
					onSteering = OnSteering.LEFT;
					break;
				case PART_2:
				case PART_3:
				case PART_4:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_2:
				switch (nextRange) {
				case PART_6:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_1:
				case PART_0:
				case PART_7:
					onSteering = OnSteering.LEFT;
					break;
				case PART_3:
				case PART_4:
				case PART_5:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_3:
				switch (nextRange) {
				case PART_7:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_4:
				case PART_5:
				case PART_6:
					onSteering = OnSteering.RIGHT;
					break;
				case PART_0:
				case PART_1:
				case PART_2:
					onSteering = OnSteering.LEFT;
					break;
				default:
					break;
				}
				break;

			case PART_4:
				switch (nextRange) {
				case PART_0:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_1:
				case PART_2:
				case PART_3:
					onSteering = OnSteering.LEFT;
					break;
				case PART_5:
				case PART_6:
				case PART_7:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_5:
				switch (nextRange) {
				case PART_1:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_2:
				case PART_3:
				case PART_4:
					onSteering = OnSteering.LEFT;
					break;
				case PART_0:
				case PART_6:
				case PART_7:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_6:
				switch (nextRange) {
				case PART_2:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_3:
				case PART_4:
				case PART_5:
					onSteering = OnSteering.LEFT;
					break;
				case PART_0:
				case PART_1:
				case PART_7:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			case PART_7:
				switch (nextRange) {
				case PART_3:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_4:
				case PART_5:
				case PART_6:
					onSteering = OnSteering.LEFT;
					break;
				case PART_0:
				case PART_1:
				case PART_2:
					onSteering = OnSteering.RIGHT;
					break;
				default:
					break;
				}
				break;

			default:
				break;
			}

		double tmpDegree = 0d;

		switch (onSteering) {
		case RIGHT:
			tmpDegree = (orientation_inDegrees + value) % 359d;
			if (tmpDegree < 0d)
				tmpDegree += 360d;

			if ((getDegreesRange(tmpDegree) != DegreesRange.PART_0) && (nextRange == DegreesRange.PART_0)
					|| ((tmpDegree <= nextDegree) && (nextRange != DegreesRange.PART_0)) ) {
				orientation_inDegrees = tmpDegree;
			} else
				orientation_inDegrees += (nextDegree - orientation_inDegrees);
			break;

		case LEFT:
			tmpDegree = (orientation_inDegrees - value) % 359d;
			if (tmpDegree < 0d)
				tmpDegree += 360d;

			if ((getDegreesRange(tmpDegree) != DegreesRange.PART_7) && (nextRange == DegreesRange.PART_7)
					|| ((tmpDegree >= nextDegree) && (nextRange != DegreesRange.PART_7)) ) {
				orientation_inDegrees = tmpDegree;
			} else
				orientation_inDegrees -= (orientation_inDegrees - nextDegree);
			break;

		case UNDEFINED:
		default:
			break;
		}

	}


	/******************************************************************************************************************/

	public void update(GameManager gameManager) {

		updateStateOnRacetrack();
		updateVertex();
		updateDirection();
		fixOrientation_inDegrees();
		GameManager.intersect(this);
		GameManager.intersectBetweenVehicles(this);

		IntelligencePoint nextIntelligencePoint = intelligencePoints.get(indexOfIntelligencePoint);


		for (IntelligencePoint intelligencePoint : intelligencePoints) {
			//			if ((intelligencePoint.getBounds().contains(position))) {

			if ((intelligencePoint.getBounds().contains(vertexLeftBack))
					|| (intelligencePoint.getBounds().contains(vertexRightBack))
					|| (intelligencePoint.getBounds().contains(vertexLeftFront))
					|| (intelligencePoint.getBounds().contains(vertexRightFront)) ) {

				indexOfIntelligencePoint = intelligencePoint.getNextIndexPoint();
			}
		}

		//		}

		//		IntelligencePoint nextIntelligencePoint = intelligencePoints.get(indexOfIntelligencePoint);

		//		Double distance = Math.sqrt(Math.pow((nextIntelligencePoint.getPoint().x - position.x), 2) + Math.pow((nextIntelligencePoint.getPoint().y - position.y), 2));
		Double distance = nextIntelligencePoint.getPoint().distance(position);

		//		System.out.println("distance "+distance);
		//		System.out.println("lib distance "+nextIntelligencePoint.getPoint().distance(position));

		Double iterations = distance/currentSpeed;
		Double value = orientation_inDegrees/iterations;


		//		if ((nextIntelligencePoint.getBounds().contains(vertexLeftBack))
		//				|| (nextIntelligencePoint.getBounds().contains(vertexRightBack))
		//				|| (nextIntelligencePoint.getBounds().contains(vertexLeftFront))
		//				|| (nextIntelligencePoint.getBounds().contains(vertexRightFront)) ) {
		//
		//			indexOfIntelligencePoint = (indexOfIntelligencePoint + 1) % intelligencePoints.size();
		//			//			System.out.println(this.toString()+" index: "+indexOfIntelligencePoint);
		//		}



		//		
		//		
		//		
		//		Double moduleA = Math.sqrt( Math.pow(position.x, 2) +  Math.pow(position.y, 2));
		//		Double moduleB = Math.sqrt( Math.pow(nextIntelligencePoint.getPoint().x, 2) +  Math.pow(nextIntelligencePoint.getPoint().y, 2));
		//
		//		Double a = (position.x * getVersors().x) + (position.y * getVersors().y);
		//		Double b = (nextIntelligencePoint.getPoint().x * nextIntelligencePoint.getVersors().x) + (nextIntelligencePoint.getPoint().y * nextIntelligencePoint.getVersors().y);
		//		
		//		Double cosAlpha = (a * b) / (moduleA * moduleB);
		////		Double alpha = Math.acos(cosAlpha);
		//		Double alpha = Math.atan( (nextIntelligencePoint.getPoint().x - position.x) / (nextIntelligencePoint.getPoint().y - position.y));

		//		System.out.println(alpha);

		//		orientation_inDegrees = alpha;

		if (nextIntelligencePoint.getDegree() != orientation_inDegrees)
			updateSteering(value, nextIntelligencePoint.getDegree());
		//		fixOrientation_inDegrees();

		//		System.out.println(orientation_inDegrees);

		switch (this.state) {
		case ACCELERATION_FORWARD:
			inAcceleration();
			updateGears();

			updatePositionForwards(null);

			fixCurrentSpeedInAccelerationForwards();

			break;

		case STOP:
		default:
			break;
		}

	}

}