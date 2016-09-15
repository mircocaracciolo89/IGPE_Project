package org.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.core.GameManager;
import org.core.movable_object.Vehicle.OnSteering;
import org.core.movable_object.Vehicle.VehicleState;
import org.gui.panels.GamePanel;

public class KeyDetected extends KeyAdapter {

//	GamePanel gamePanel;
//	MainFrame mainFrame;

	public KeyDetected() {
//		this.gamePanel = gamePanel;
	}

	public void keyPressed(final KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_E:
			System.exit(0);
			break;

		case KeyEvent.VK_P:
			GameManager.paused = !GameManager.paused;
			break;

		case KeyEvent.VK_UP:

			if (GameManager.getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_BACKWARD || GameManager.getCarPlayer().getVehicleState() == VehicleState.DECELERATION_BACKWARD)
				GameManager.getCarPlayer().setBraking(true);
			else
				GameManager.getCarPlayer().setVehicleState(VehicleState.ACCELERATION_FORWARD);
			break;

		case KeyEvent.VK_DOWN:

			if (GameManager.getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_FORWARD || GameManager.getCarPlayer().getVehicleState() == VehicleState.DECELERATION_FORWARD)
				GameManager.getCarPlayer().setBraking(true);
			else
				GameManager.getCarPlayer().setVehicleState(VehicleState.ACCELERATION_BACKWARD);
			break;

		case KeyEvent.VK_LEFT:
			GameManager.getCarPlayer().setOnSteering(OnSteering.LEFT);

			break;

		case KeyEvent.VK_RIGHT:
			GameManager.getCarPlayer().setOnSteering(OnSteering.RIGHT);

			break;

		case KeyEvent.VK_SPACE:
			GameManager.getCarPlayer().setBraking(true);

			break;

		}

	}

	public void keyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:

			if (GameManager.getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_FORWARD)
				GameManager.getCarPlayer().setVehicleState(VehicleState.DECELERATION_FORWARD);

			GameManager.getCarPlayer().setBraking(false);
			break;

		case KeyEvent.VK_DOWN:
			if (GameManager.getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_BACKWARD)
				GameManager.getCarPlayer().setVehicleState(VehicleState.DECELERATION_BACKWARD);

			GameManager.getCarPlayer().setBraking(false);

			break;

		case KeyEvent.VK_LEFT:
			GameManager.getCarPlayer().setOnSteering(OnSteering.getDefault());

			break;

		case KeyEvent.VK_RIGHT:
			GameManager.getCarPlayer().setOnSteering(OnSteering.getDefault());

			break;

		case KeyEvent.VK_SPACE:
			GameManager.getCarPlayer().setBraking(false);

			break;

		}
	}
}
