package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProvider {

	//	private static BufferedImage environmentSand;
	private static BufferedImage environmentHerb;
	//	
	//	private static BufferedImage environmentElementStone1;
	//	private static BufferedImage environmentElementStone2;
	//	
	//	private static BufferedImage environmentElementTree1;
	//	private static BufferedImage environmentElementTree2;
	//	
	//	private static BufferedImage racetrackElementBonus;
	//	private static BufferedImage racetrackElementMalus;
	//	
	private static BufferedImage car;
	//	
	private static BufferedImage greenSemaphore;
	private static BufferedImage orangeSemaphore;
	private static BufferedImage redSemaphore;

	static {
		try {
			//			environmentSand = ImageIO.read(new File("images"+File.separator+"sand.png"));
			environmentHerb = ImageIO.read(new File("img"+File.separator+"herb.jpg"));
			//			
			//			environmentElementStone1 = ImageIO.read(new File("images"+File.separator+"stone1.png"));
			//			environmentElementStone2 = ImageIO.read(new File("images"+File.separator+"stone2.png"));
			//			
			//			environmentElementTree1 = ImageIO.read(new File("images"+File.separator+"tree1.png"));
			//			environmentElementTree2 = ImageIO.read(new File("images"+File.separator+"tree2.png"));
			//			
			//			racetrackElementBonus = ImageIO.read(new File("images"+File.separator+"bonus.png"));
			//			racetrackElementMalus = ImageIO.read(new File("images"+File.separator+"malus.png"));
			//			
			car = ImageIO.read(new File("img"+File.separator+"car.jpg"));
			//			
			greenSemaphore = ImageIO.read(new File("img"+File.separator+"car.jpg"));
			orangeSemaphore = ImageIO.read(new File("img"+File.separator+"car.jpg"));
			redSemaphore = ImageIO.read(new File("img"+File.separator+"car.jpg"));
			//			

		}catch(final IOException e) {
			e.printStackTrace();
		}

	}

	//	public static BufferedImage getEnvironmentSand() {
	//		return environmentSand;
	//	}
	//
	public static BufferedImage getEnvironmentHerb() {
		return environmentHerb;
	}
	//
	//	public static BufferedImage getEnvironmentElementStone1() {
	//		return environmentElementStone1;
	//	}
	//
	//	public static BufferedImage getEnvironmentElementStone2() {
	//		return environmentElementStone2;
	//	}
	//
	//	public static BufferedImage getEnvironmentElementTree1() {
	//		return environmentElementTree1;
	//	}
	//
	//	public static BufferedImage getEnvironmentElementTree2() {
	//		return environmentElementTree2;
	//	}
	//
	//	public static BufferedImage getRacetrackElementBonus() {
	//		return racetrackElementBonus;
	//	}
	//
	//	public static BufferedImage getRacetrackElementMalus() {
	//		return racetrackElementMalus;
	//	}
	//	
	public static BufferedImage getCar() {
		return car;
	}
	//
	public static BufferedImage getGreenSemaphore() {
		return greenSemaphore;
	}

	public static BufferedImage getOrangeSemaphore() {
		return orangeSemaphore;
	}

	public static BufferedImage getRedSemaphore() {
		return redSemaphore;
	}
}
