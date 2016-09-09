package org.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.core.GameManager;
import org.core.movable_object.Vehicle.OnSteering;
import org.core.movable_object.Vehicle.VehicleState;
import org.gui.panels.GamePanel;

public class KeyDetected extends KeyAdapter {

	GamePanel gamePanel;
	MainFrame mainFrame;

	public KeyDetected(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
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

			if (gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_BACKWARD || gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.DECELERATION_BACKWARD)
				break;			
			else
				gamePanel.getGameManager().getCarPlayer().setVehicleState(VehicleState.ACCELERATION_FORWARD);

			break;

		case KeyEvent.VK_DOWN:

			if (gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_FORWARD || gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.DECELERATION_FORWARD)
				break;
			else 
				gamePanel.getGameManager().getCarPlayer().setVehicleState(VehicleState.ACCELERATION_BACKWARD);

			break;

		case KeyEvent.VK_LEFT:
			gamePanel.getGameManager().getCarPlayer().setOnSteering(OnSteering.LEFT);

			break;

		case KeyEvent.VK_RIGHT:
			gamePanel.getGameManager().getCarPlayer().setOnSteering(OnSteering.RIGHT);

			break;

		case KeyEvent.VK_SPACE:
			gamePanel.getGameManager().getCarPlayer().setBraking(true);

			break;

		}

	}

	public void keyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:

			if (gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_FORWARD)
				gamePanel.getGameManager().getCarPlayer().setVehicleState(VehicleState.DECELERATION_FORWARD);

			break;

		case KeyEvent.VK_DOWN:
			if (gamePanel.getGameManager().getCarPlayer().getVehicleState() == VehicleState.ACCELERATION_BACKWARD)
				gamePanel.getGameManager().getCarPlayer().setVehicleState(VehicleState.DECELERATION_BACKWARD);

			break;

		case KeyEvent.VK_LEFT:
			gamePanel.getGameManager().getCarPlayer().setOnSteering(OnSteering.getDefault());

			break;

		case KeyEvent.VK_RIGHT:
			gamePanel.getGameManager().getCarPlayer().setOnSteering(OnSteering.getDefault());

			break;

		case KeyEvent.VK_SPACE:
			gamePanel.getGameManager().getCarPlayer().setBraking(false);

			break;

		}
	}
}
