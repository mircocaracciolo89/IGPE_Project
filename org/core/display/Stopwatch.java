package org.core.display;
import java.util.Timer;
import java.util.TimerTask;

import org.core.GameManager;

public class Stopwatch {

	GameManager gameManager;

	static float interval;
	static Timer timer;
	static boolean start;
	static int delay;
	static int period;
	static int decreaseValue;

	public Stopwatch(GameManager gameManager) {
		this.gameManager = gameManager;

		timer = new Timer();
		interval = gameManager.getTime();
		delay = 0;
		period = 10;
		decreaseValue = 1;
	}

	private static float 	getDecreaseInterval()  { return ( ((float) period) / 1000f) * decreaseValue; }
	public void 			setDecreaseValue(int value) 	{ decreaseValue = value; }
	public void				increaseInterval()				{ interval += 5f; }

	public void startTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				gameManager.setTime(setInterval());
			}

		}, delay, period);
	}

	private static final float setInterval() {
		if (interval <= getDecreaseInterval()) {
			timer.cancel();
			return 0f;
		}
		else
			return interval -= getDecreaseInterval();
	}
}