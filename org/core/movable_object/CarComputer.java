package org.core.movable_object;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.core.GameManager;
import org.core.GameManager.Border;
import org.core.GameManager.BorderArea;
import org.core.environment.racetrack.IntelligencePoint;
import org.core.environment.racetrack.Racetrack;
import org.core.movable_object.Vehicle.VehicleState;
import org.gui.Loader;
import org.gui.panels.GamePanel;

public class CarComputer extends Vehicle {

	private List<IntelligencePoint> intelligencePoints;
	private int indexOfIntelligencePoint;

	public CarComputer(Point2D.Double startPoint, double startOrientation) {
		super(startPoint.x * GamePanel.SCALE, startPoint.y * GamePanel.SCALE);

		image = Loader.imgYellowCarComputer;
		height = image.getHeight(null);
		width = image.getWidth(null);

		mass = 1d;

		orientation_inDegrees = startOrientation;
		fixOrientation_inDegrees();
		maxSpeed = 10d;
		initialAcceleration = 5d;

		intelligencePoints = Racetrack.getIntelligencePoints();
		indexOfIntelligencePoint = 0;

		state = VehicleState.ACCELERATION_FORWARD;
	}

	/******************************************************************************************************************/

	public void drawGameObject(Graphics2D g2d) {
		g2d.translate(position.x + CarPlayer.getTranslation().x, position.y + CarPlayer.getTranslation().y);
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(getImage(),AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}

	/******************************************************************************************************************/

	private void updateSteering(Double value, Double nextDegree) {


		System.out.println("");


		if (orientation_inDegrees < 0d && nextDegree < 0d) {
			onSteering = (orientation_inDegrees > nextDegree) ? OnSteering.RIGHT : OnSteering.LEFT;
		}
		else if (orientation_inDegrees > 0d && nextDegree > 0d)  {
			onSteering = (orientation_inDegrees < nextDegree) ? OnSteering.RIGHT : OnSteering.LEFT;
		}
		else {

			DegreesRange nextRange = getDegreesRange(nextDegree);

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
				default:
					break;
				}
				break;

			case PART_1:
				switch (nextRange) {
				case PART_5:
					onSteering = OnSteering.fromInteger((int) (Math.random()*2));
					break;
				case PART_6:
				case PART_7:
					onSteering = OnSteering.LEFT;
					break;
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
				case PART_7:
					onSteering = OnSteering.LEFT;
					break;
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
					onSteering = OnSteering.LEFT;
					break;
				case PART_0:
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
					onSteering = OnSteering.LEFT;
					break;
				case PART_0:
				case PART_1:
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
		}



		switch (onSteering) {
		case RIGHT:
			//			System.out.println("RIGHT");
			if (orientation_inDegrees < nextDegree)
				orientation_inDegrees += value;
			break;

		case LEFT:
			//			System.out.println("LEFT");
			if (orientation_inDegrees > nextDegree)
				orientation_inDegrees -= value;
			break;

		case UNDEFINED:
		default:
			break;
		}

		//		fixOrientation_inDegrees();

	}


	/******************************************************************************************************************/

	public synchronized void update(GameManager gameManager) {

		updateVertex();
		updateDirection();
		fixOrientation_inDegrees();

		IntelligencePoint nextIntelligencePoint = intelligencePoints.get(indexOfIntelligencePoint);

		Double distance = Math.sqrt( ((nextIntelligencePoint.getPoint().x - position.x) * (nextIntelligencePoint.getPoint().x - position.x)) + ((nextIntelligencePoint.getPoint().y - position.y) * (nextIntelligencePoint.getPoint().y - position.y)) );
		Double iterations = distance/currentSpeed;
		Double value = orientation_inDegrees/iterations;


		//		System.out.println(this.toString()+" currentDegrees: "+orientation_inDegrees);

//		if (orientation_inDegrees != 70d)
//			updateSteering(1d, 70d);

		//		System.out.println(this.toString()+"index out: "+indexOfIntelligencePoint);


		//		System.out.println(this.toString()+" distance: "+distance);
		//		System.out.println(this.toString()+" iteration: "+iterations);
		//		System.out.println(this.toString()+" value: "+value);
		//		System.out.println(this.toString()+" nextDegrees: "+nextIntelligencePoint.getDegree());


				if (orientation_inDegrees != nextIntelligencePoint.getDegree())
					updateSteering(value, nextIntelligencePoint.getDegree());
		
				if ((nextIntelligencePoint.getBounds().contains(vertexLeftBack))
						|| (nextIntelligencePoint.getBounds().contains(vertexRightBack))
						|| (nextIntelligencePoint.getBounds().contains(vertexLeftFront))
						|| (nextIntelligencePoint.getBounds().contains(vertexRightFront)) ) {
		
					//			indexOfIntelligencePoint += (indexOfIntelligencePoint + 1) % 25;
					indexOfIntelligencePoint++;
					System.out.println(this.toString()+" index: "+indexOfIntelligencePoint);
				}



		switch (this.state) {
		case ACCELERATION_FORWARD:
			inAcceleration();

			updateGears();

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

		case STOP:
		default:
			break;
		}

	}

}
