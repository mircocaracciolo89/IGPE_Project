package org.gui;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.LinkRef;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Loader extends JComponent {

	private static final long serialVersionUID = 1L;
	
	public static Image imgBackground = null;
	public static Image imgRacetrack1 = null;
	public static Image imgRacetrack2 = null;
	public static Image imgCar1 = null;
	public static Image imgCar2 = null;
	public static Image imgHerb = null;
	public static Image imgButtonStart = null;
	public static Image imgPauseImage = null;
	public static Image imgTree = null;
	public static Image imgCar = null;
	public static Image imgMultiplayer = null;
	public static Image imgEditor = null;
	public static Image imgButtonStart2 = null;
	public static Image imgButtonExit = null;
	public static Image imgButtonExit2 = null;
	public static Image imgIta= null;
	public static Image imgBra= null;
	public static Image imgBackButton= null;
	public static Image imgBackButton2 = null;
	
	public static Image imgSemaphoreStart = null;
	public static Image imgSemaphoreRed = null;
	public static Image imgSemaphoreOrange = null;
	public static Image imgSemaphoreGreen = null;
	
	public MediaTracker mt = new MediaTracker(this);
	Toolkit tk = Toolkit.getDefaultToolkit();

	public Loader() {
		super();
	}

	public boolean load() throws MalformedURLException {
//		imgBackground = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/sfondo.jpg"));
//		imgRacetrack1 = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/Ita.png"));
//		imgRacetrack2 = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/Bra.png"));
//		imgCar = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/car.jpg"));
//		imgCar1 = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/car.png"));
//		imgCar2 = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/yellowCar2.png"));
//		imgHerb = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/herb.jpg"));
//		imgButtonStart =	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/quickStartChiaro.png"));
//		imgButtonStart2 =	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/QuickStartNuovo.png"));
//		imgPauseImage = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/pauseImage.png"));
//		imgTree = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/tree.png"));
//		imgMultiplayer =	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/multiplayerChiaro.png"));
//		imgEditor = 		tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/editorChiaro.png"));
//		imgButtonExit = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/exitChiaro.png"));
//		imgButtonExit2 = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/ExitNuovo.png"));
//		imgBra = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/Bra.png"));
//		imgIta = 			tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/Ita.png"));
//		imgBackButton = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/backButton.png"));
//		imgBackButton2 =	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/backButtonScuro.png"));
//		
//		imgSemaphoreStart = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/pieno.png"));
//		imgSemaphoreRed = 		tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/rosso.png"));
//		imgSemaphoreOrange = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/giallo.png"));
//		imgSemaphoreGreen = 	tk.getImage( new URL("https://github.com/mircocaracciolo89/IGPE_Project/blob/master/img/verde.png"));
		
		
		imgBackground = 	tk.getImage( "img"+File.separator+"sfondo.jpg");
		imgRacetrack1 = 	tk.getImage( "img"+File.separator+"Ita.png");
		imgRacetrack2 = 	tk.getImage( "img"+File.separator+"Bra.png");
		imgCar = 			tk.getImage( "img"+File.separator+"car.jpg");
		imgCar1 = 			tk.getImage( "img"+File.separator+"car.png");
		imgCar2 = 			tk.getImage( "img"+File.separator+"yellowCar2.png");
		imgHerb = 			tk.getImage( "img"+File.separator+"herb.jpg");
		imgButtonStart =	tk.getImage( "img"+File.separator+"quickStartChiaro.png");
		imgButtonStart2 =	tk.getImage( "img"+File.separator+"QuickStartNuovo.png");
		imgPauseImage = 	tk.getImage( "img"+File.separator+"pauseImage.png");
		imgTree = 			tk.getImage( "img"+File.separator+"tree.png");
		imgMultiplayer =	tk.getImage( "img"+File.separator+"multiplayerChiaro.png");
		imgEditor = 		tk.getImage( "img"+File.separator+"editorChiaro.png");
		imgButtonExit = 	tk.getImage( "img"+File.separator+"exitChiaro.png");
		imgButtonExit2 = 	tk.getImage( "img"+File.separator+"ExitNuovo.png");
		imgBra = 			tk.getImage( "img"+File.separator+"Bra.png");
		imgIta = 			tk.getImage( "img"+File.separator+"Ita.png");
		imgBackButton = 	tk.getImage( "img"+File.separator+"backButton.png");
		imgBackButton2 =	tk.getImage( "img"+File.separator+"backButtonScuro.png");
		
		imgSemaphoreStart = 	tk.getImage( "img"+File.separator+"pieno.png");
		imgSemaphoreRed = 		tk.getImage( "img"+File.separator+"rosso.png");
		imgSemaphoreOrange = 	tk.getImage( "img"+File.separator+"giallo.png");
		imgSemaphoreGreen = 	tk.getImage( "img"+File.separator+"verde.png");

		mt.addImage(imgBackground, 1);
		mt.addImage(imgRacetrack1, 2);
		mt.addImage(imgRacetrack2, 3);
		mt.addImage(imgCar1, 4);
		mt.addImage(imgCar2, 5);
		mt.addImage(imgHerb, 6);
		mt.addImage(imgButtonStart, 7);
		mt.addImage(imgPauseImage, 8);
		mt.addImage(imgTree, 9);
		mt.addImage(imgCar, 10);
		mt.addImage(imgMultiplayer, 11);
		mt.addImage(imgEditor, 12);
		mt.addImage(imgButtonStart2, 13);
		mt.addImage(imgButtonExit, 14);
		mt.addImage(imgButtonExit2, 15);
		mt.addImage(imgBra, 16);
		mt.addImage(imgIta, 17);
		mt.addImage(imgBackButton, 18);
		mt.addImage(imgBackButton2, 19);

		mt.addImage(imgSemaphoreStart, 20);
		mt.addImage(imgSemaphoreRed, 21);
		mt.addImage(imgSemaphoreOrange, 22);
		mt.addImage(imgSemaphoreGreen, 23);

		for(int i = 0; i < 23; i++)
		{
			try {
				mt.waitForID(i);
			}
			catch(Exception e){}
		}
		return true;
		
	}


}
