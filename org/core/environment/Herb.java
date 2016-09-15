package org.core.environment;

import org.core.GameManager;
import org.core.environment.racetrack.IntelligencePoint;
import org.core.environment.racetrack.Racetrack;
import org.core.movable_object.CarComputer;
import org.core.movable_object.CarPlayer;
import org.core.still_object.StillObject;
import org.core.still_object.environment.EnvironmentElement;
import org.gui.Loader;
import org.gui.panels.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Herb implements Environment {

	private Image imageBackgroud;
	private final List<EnvironmentElement> elements;
	private Racetrack racetrack;
	BufferedImage bufferedImageEnvironment;

	public Herb(File racetrackFile) throws IOException {
		imageBackgroud = Loader.imgBackGroundEnvironment;
		elements = new ArrayList<EnvironmentElement>();
		racetrack = new Racetrack(racetrackFile);
		createImageEnvironmet();
	}

	public Image getImageBackground() { return imageBackgroud; }
	public List<EnvironmentElement> getElements() { return elements; }
	public Racetrack getRacetrack() { return racetrack; }

	public void createImageEnvironmet() {

		bufferedImageEnvironment = new BufferedImage(GameManager.WIDTH * GamePanel.SCALE, GameManager.HEIGHT * GamePanel.SCALE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImageEnvironment.createGraphics();
		AffineTransform oldXform = g2d.getTransform();

		// BACKGROUND

		g2d.drawImage(imageBackgroud, 0, 0, GameManager.WIDTH * GamePanel.SCALE, GameManager.HEIGHT * GamePanel.SCALE, null);

		// RACETRACK

		g2d.setStroke(GamePanel.stroke);
		g2d.setColor(Color.GRAY);

		for (double i = 0d; i < GameManager.WIDTH * GamePanel.SCALE; i+=5)
			for (double j = 0d; j < GameManager.HEIGHT * GamePanel.SCALE; j+=5)
				if(!((racetrack.getPathIn().contains(i, j)) || (!racetrack.getPathOut().contains(i, j))))
					g2d.fillRect((int)i, (int)j, 5, 5);

		g2d.draw(racetrack.getPathOut());
		g2d.draw(racetrack.getPathIn());

		// START LINE

		Line2D.Double startLine = racetrack.getStartLine();

		g2d.setStroke(GamePanel.stroke1);
		g2d.setColor(Color.BLACK);
		g2d.drawLine((int) startLine.x1 -25, (int) startLine.y1, (int) startLine.x2 -25, (int) startLine.y2);

		g2d.setStroke(GamePanel.stroke2);
		g2d.setColor(Color.WHITE);
		g2d.drawLine((int) startLine.x1 -25, (int) startLine.y1, (int) startLine.x2 -25, (int) startLine.y2);

		g2d.setStroke(GamePanel.stroke2);
		g2d.setColor(Color.BLACK);
		g2d.draw(startLine);

		g2d.setStroke(GamePanel.stroke1);
		g2d.setColor(Color.WHITE);
		g2d.draw(startLine);

		g2d.setStroke(GamePanel.stroke1);
		g2d.setColor(Color.BLACK);
		g2d.drawLine((int) startLine.x1 +25, (int) startLine.y1, (int) startLine.x2 +25, (int) startLine.y2);

		g2d.setStroke(GamePanel.stroke2);
		g2d.setColor(Color.WHITE);
		g2d.drawLine((int) startLine.x1 +25, (int) startLine.y1, (int) startLine.x2 +25, (int) startLine.y2);

		// STILL OBJECTS

		g2d.setStroke(GamePanel.stroke);
		for (StillObject item : racetrack.getStillObjects()) {
			g2d.setTransform(oldXform);
			item.drawGameObject(g2d);
		}
		
		// INTELLIGENCE POINTS
		
		for (IntelligencePoint intPoint : Racetrack.getIntelligencePoints()) {
			g2d.setTransform(oldXform);
			g2d.drawString(""+Racetrack.getIntelligencePoints().indexOf(intPoint), (int) intPoint.getPoint().getX(), (int) intPoint.getPoint().getY());
			g2d.drawString(""+intPoint.getDegree(), (int) intPoint.getPoint().getX()+10, (int) intPoint.getPoint().getY()+10);
			g2d.draw(intPoint.getBounds());
			
//			CarComputer car = new CarComputer(new Point2D.Double(intPoint.getPoint().getX(), intPoint.getPoint().getY()), intPoint.getDegree());
//			car.drawGameObject(g2d);
			
		}
		
		

		//		try {
		//			ImageIO.write(bi, "PNG", new File("img"+File.separator+"imageEnvironment.png"));
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

	}

	public void drawEnvironment(Graphics2D g2d) {

		g2d.drawImage(bufferedImageEnvironment, null, (int) CarPlayer.getTranslation().x, (int) CarPlayer.getTranslation().y);

		// CHECKPOINTS

		g2d.setStroke(GamePanel.stroke);
		g2d.translate(CarPlayer.getTranslation().x, CarPlayer.getTranslation().y);

		for (Line2D.Double line2d : racetrack.getCheckpoints()) {
			if (racetrack.getCheckpoints().indexOf(line2d) == GameManager.getIndexCheckpointNumber())
				g2d.setColor(Color.RED);
			else g2d.setColor(Color.YELLOW);
			g2d.draw(line2d);
		}
		
//		for (StillObject item : racetrack.getStillObjects())
//			item.drawElement(g2d);

		// STILL OBJECTS

				for (StillObject item : racetrack.getStillObjects())
					item.drawGameObject(g2d);

	}
}
