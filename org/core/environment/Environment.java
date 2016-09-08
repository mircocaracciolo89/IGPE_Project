package core.environment;

import core.environment.racetrack.Racetrack;
import core.still_object.environment.EnvironmentElement;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

public interface Environment {
	public Image getImageBackground();
	public List<EnvironmentElement> getElements();
	public Racetrack getRacetrack();
 
	public void drawEnvironment(Graphics2D g2d);
}
