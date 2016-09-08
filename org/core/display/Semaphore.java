package core.display;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import core.GameManager;
import core.GameManager.GameState;
import gui.ImageProvider;
import gui.Loader;
import gui.panels.GamePanel;


public class Semaphore {//implements Runnable {

	public enum SemaphoreState {
		UNDEFINED(0x0), START(0x1), RED(0x2), ORANGE(0x3), GREEN(0x4);
		private int format;
		SemaphoreState (int format) {
			this.format = format;
		}

		public static SemaphoreState fromInteger(int format) throws IllegalArgumentException {
			for (SemaphoreState dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static SemaphoreState getDefault() {
			return UNDEFINED;
		}

	}

	private SemaphoreState state;
	private Image image;
	public static final int HEIGHT = 15;
	public static final int WIDTH = 15;

	private boolean visible;

	private Point2D.Double position;

	public Semaphore() {
		state = SemaphoreState.START;
		this.position = new Point2D.Double(650f, 10f);
		visible = true;
	}

	public Point2D.Double getPosition() { return position; }
	public void setState(SemaphoreState state) { this.state = state; }

	public Image getImage() {
		System.out.println(state);
		switch (this.state) {
		case START:
			image = Loader.imgSemaphoreStart;
			break;
		case RED:
			image = Loader.imgSemaphoreRed;
			break;
		case ORANGE:
			image = Loader.imgSemaphoreOrange;
			break;
		case GREEN:
			image = Loader.imgSemaphoreGreen;
			break;
		case UNDEFINED:
			image = null;
			break;
		default:
			break;
		}
		return image;
	}

	public boolean isVisible() { return visible; }
	public void setVisible(boolean visible) { this.visible = visible; }
//
//	public void run() {
//		try {
//			Thread.sleep(1000);
//			state = State.RED;
//			Thread.sleep(1000);
//			state = State.ORANGE;
//			Thread.sleep(1000);
//			state = State.GREEN;
//			//			GameManager.gameState = GameState.RUN;
//			//			Thread.sleep(2500);
//			visible = false;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//	}
}
