package org.core.display;
import java.util.Timer;
import java.util.TimerTask;

import org.core.GameManager;

public class Stopwatch {

	/******************* VARIABLES **********************************************************************************************/

	GameManager gameManager;
	
	static float interval;
	static Timer timer;
	static boolean start;
	static int delay;
	static int period;
	static int decreaseValue;

	/******************* CONSTRUCTOR **********************************************************************************************/

	public Stopwatch(GameManager gameManager) {
		this.gameManager = gameManager;

		timer = new Timer();
		interval = gameManager.getTime();
		delay = 0;
		period = 10;
		decreaseValue = 1;
	}
	/******************* GETTERS **********************************************************************************************/

	private static float 	getDecreaseInterval()  { return ( ((float) period) / 1000f) * decreaseValue; }

	/******************* SETTERS **********************************************************************************************/

	private static final float setInterval() {
		if (interval <= getDecreaseInterval()) {
			timer.cancel();
			return 0f;
		}
		else
			return interval -= getDecreaseInterval();
	}
	
	public void setDecreaseValue(int value) 	{ decreaseValue = value; }
	
	/******************* SERVICE METHODS **********************************************************************************************/

	public void	increaseInterval()				{ interval += 5f; }
	
	public void startTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				gameManager.setTime(setInterval());
			}

		}, delay, period);
	}
}