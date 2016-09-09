package org.core.display;

import java.awt.Image;
import java.awt.geom.Point2D;

import org.gui.Loader;

public class Semaphore {

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
	private Point2D.Double position;
	private boolean visible;

	public Semaphore() {
		state = SemaphoreState.START;
		this.position = new Point2D.Double(650f, 10f);
		visible = true;
	}

	public Point2D.Double getPosition() { return position; }
	public void setState(SemaphoreState state) { this.state = state; }

	public Image getImage() {
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
	
	public void start(Runnable runnable) {
		try {
			Thread.sleep(1500);
			setState(SemaphoreState.RED);
			runnable.run();
			Thread.sleep(1500);
			setState(SemaphoreState.ORANGE);
			runnable.run();
			Thread.sleep(1500);
			setState(SemaphoreState.GREEN);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
