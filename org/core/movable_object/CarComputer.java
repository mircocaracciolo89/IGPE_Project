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
import org.core.GameManager.BorderArea;
import org.gui.Loader;
import org.gui.panels.GamePanel;

public class CarComputer extends Vehicle {

	Point2D.Double point_succ;
	Double degrees_succ;

	public static final int RANGE_POINT = 50;

	public CarComputer(Point2D.Double startPoint, double startOrientation) {
		super(startPoint.x * GamePanel.SCALE, startPoint.y * GamePanel.SCALE);

		point_succ = new Point2D.Double();
		degrees_succ = null;
		
		image = Loader.imgYellowCarComputer;
		height = image.getHeight(null);
		width = image.getWidth(null);

		mass = 1d;

		orientation_inDegrees = startOrientation;
		maxSpeed = 10d;
		initialAcceleration = 5d;

	}

	/******************************************************************************************************************/

	public void drawElement(Graphics2D g2d) {
		g2d.translate(position.x + CarPlayer.getTranslation().x, position.y + CarPlayer.getTranslation().y);
		g2d.rotate(getOrientation_inRadian());
		g2d.drawImage(getImage(),AffineTransform.getTranslateInstance(-getWidth()/2, -getHeight()/2), null);
	}

	/******************************************************************************************************************/

	private void updateSteering(Double value, Double degrees_succ) {

		switch (onSteering) {
		case RIGHT:
			if (orientation_inDegrees < degrees_succ)
				orientation_inDegrees += value;
			break;

		case LEFT:
			System.out.println("ENtrat");
			System.out.println("deg in "+degrees_succ);
			System.out.println("val in "+value);

			if (orientation_inDegrees > degrees_succ)
				orientation_inDegrees -= value;
			break;

		case UNDEFINED:
		default:
			break;
		}

	}


	/******************************************************************************************************************/

	public synchronized void update(GameManager gameManager) {

		updateVertex();
		updateDirection();

		Point2D.Double a = new Point2D.Double(810d * GamePanel.SCALE, 160d * GamePanel.SCALE);
		Point2D.Double b = new Point2D.Double(920d * GamePanel.SCALE, 160d * GamePanel.SCALE);
		Point2D.Double c = new Point2D.Double(1005d * GamePanel.SCALE, 230d * GamePanel.SCALE);
		Point2D.Double d = new Point2D.Double(1110d * GamePanel.SCALE, 350d * GamePanel.SCALE);

		Map<Point2D.Double, OnSteering> map = new HashMap<>();

		map.put(a, OnSteering.LEFT);
		map.put(b, OnSteering.RIGHT);
		map.put(c, OnSteering.RIGHT);
		map.put(d, OnSteering.UNDEFINED);


		//		
		//		for (Map.Entry<Point2D.Double, OnSteering> entry : map.entrySet()) {
		//			entry.
		//		}





		if (position.x == 650d * GamePanel.SCALE  && position.y == 140d * GamePanel.SCALE) {
			this.state = VehicleState.ACCELERATION_FORWARD;
			point_succ = a;
			onSteering = OnSteering.LEFT;
			degrees_succ = 0d;
		}

		Double distance = Math.sqrt( ((point_succ.x - position.x) * (point_succ.x - position.x)) + ((point_succ.y - position.y) * (point_succ.y - position.y)) );
		Double iterations = distance/currentSpeed;
		Double value = orientation_inDegrees/iterations;

		updateSteering(value, degrees_succ);











		//		if (position.x == 810d * GamePanel.SCALE)
		//			setOrientation_inDegrees(0.98d);
		//
		//		if (position.x >= 920d * GamePanel.SCALE)
		//			setOrientation_inDegrees(30d);

		//		if (right) {
		//			setOrientation_inDegrees(orientation_inDegrees + 1d);
		//		}

		switch (this.state) {
		case ACCELERATION_FORWARD:
			if (currentSpeed == 0d)
				currentSpeed = getActualInitialAcceleration();

			updateGears();

			updatePositionXForwards();
			updatePositionYForwards();

			if (currentSpeed < maxSpeed)
				currentSpeed += getPercentageAccelerationIncreaseForwardsMarch();
			else
				currentSpeed = maxSpeed;

			inAcceleration();
			break;

			//		case ACCELERATION_BACKWARD:
			//			if (currentSpeed == 0d)
			//				currentSpeed = getActualInitialAcceleration();
			//
			//			updateSteeringBackwards();
			//
			//			if (GameManager.getActualBorder() != Border.UNDEFINED)
			//				updateVehicleOnBorderBackwards();
			//			else
			//				updatePositionBackwards();
			//
			//			if (GameManager.intersectBorders(this) == GameManager.NO_INTERSECT) {
			//				if (currentSpeed < getMaxSpeedBackwardsMarch())
			//					currentSpeed += getPercentageAccelerationIncreaseBackwardsMarch();
			//				else
			//					currentSpeed = getMaxSpeedBackwardsMarch();
			//			} else {
			//				currentSpeed = 0d;
			//			}
			//			inAcceleration();
			//			break;
			//
			//		case DECELERATION_FORWARD:
			//			updateSteeringForwards();
			//			updateGears();
			//
			//			if (GameManager.getActualBorder() != Border.UNDEFINED)
			//				updateVehicleOnBorderForwards();
			//			else
			//				updatePositionForwards();
			//
			//			if (currentSpeed > getActualInitialAcceleration())
			//				currentSpeed -= getPercentageAccelerationIncreaseForwardsMarch();
			//			inDeceleraition();
			//			break;
			//
			//		case DECELERATION_BACKWARD:
			//			if (GameManager.getActualBorder() != Border.UNDEFINED)
			//				updateVehicleOnBorderBackwards();
			//			else
			//				updatePositionBackwards();
			//
			//			if (currentSpeed > getActualInitialAcceleration())
			//				currentSpeed -= getPercentageAccelerationIncreaseBackwardsMarch();
			//			inDeceleraition();
			//			break;
		case STOP:
		default:
			break;
		}

	}

}
