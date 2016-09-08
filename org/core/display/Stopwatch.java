package core.display;
import java.awt.Graphics2D;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import core.GameManager;

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
		interval = 5f;
		start = true;
		delay = 0;
		period = 10;
		decreaseValue = 1;
	}
	
	private static float getDecreaseInterval() { return (((float) period) / 1000f) * decreaseValue; }
	public void setDecreaseValue(int value) { decreaseValue = value; }

	public void startTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				gameManager.setTime(setInterval());
			}

		}, delay, period);
	}

	private static final float setInterval() {
		if (interval <= getDecreaseInterval())
			timer.cancel();
		if (start) {
			start = false;
			return interval;
		}
		else
			return interval -= getDecreaseInterval();
	}
}