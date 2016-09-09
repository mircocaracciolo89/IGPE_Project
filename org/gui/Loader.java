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
	
	public static Image imgBackgroundMenu = null;
	public static Image imgBackGroundEnvironment = null;
	
	public static Image imgButtonStart = null;
	public static Image imgButtonStart2 = null;
	public static Image imgButtonMultiplayer = null;
	public static Image imgButtonEditor = null;
	public static Image imgButtonExit = null;
	public static Image imgButtonExit2 = null;
	public static Image imgButtonBack= null;
	public static Image imgButtonBack2 = null;
	
	public static Image imgItaRacetrack= null;
	public static Image imgBraRacetrack= null;
	
	public static Image imgCarPlayer = null;
	public static Image imgCar1Menu = null;
	public static Image imgCar2Menu = null;
	public static Image imgGreenCarComputer = null;
	public static Image imgYellowCarComputer = null;
	public static Image imgBlueCarComputer = null;
	
	public static Image imgTree1 = null;
	public static Image imgTree2 = null;
	public static Image imgTree3 = null;
	public static Image imgTree4 = null;
	public static Image imgTree5 = null;
	public static Image imgTree6 = null;

	public static Image imgStone1 = null;
	public static Image imgStone2 = null;
	public static Image imgStone3 = null;

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

		imgBackgroundMenu = 		tk.getImage( "img"+File.separator+"Backgrounds"+File.separator+"backgroundMenu.jpg");
		imgBackGroundEnvironment = 	tk.getImage( "img"+File.separator+"Backgrounds"+File.separator+"backgroundEnvironment.jpg");
		imgButtonStart =			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"quickStart.png");
		imgButtonStart2 =			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"QuickStartFocus.png");
		imgButtonMultiplayer =		tk.getImage( "img"+File.separator+"Buttons"+File.separator+"multiplayer.png");
		imgButtonEditor = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"editor.png");
		imgButtonExit = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"exit.png");
		imgButtonExit2 = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"ExitFocus.png");
		imgButtonBack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"back.png");
		imgButtonBack2 =			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"backFocus.png");
		
		imgItaRacetrack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"Italy.png");
		imgBraRacetrack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"Brasil.png");
		
		imgCarPlayer = 				tk.getImage( "img"+File.separator+"Cars"+File.separator+"carPlayer.png");
		imgCar1Menu = 				tk.getImage( "img"+File.separator+"Buttons"+File.separator+"redCar.png");
		imgCar2Menu = 				tk.getImage( "img"+File.separator+"Buttons"+File.separator+"yellowCar.png");
		imgGreenCarComputer = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"greenCarComputer.png");
		imgYellowCarComputer = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"yellowCarComputer.png");
		imgBlueCarComputer = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"blueCarComputer.png");
		
		imgTree1 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree1.png");
		imgTree2 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree2.png");
		imgTree3 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree3.png");
		imgTree4 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree4.png");
		imgTree5 =					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree5.png");
		imgTree6 =					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree6.png");

		imgStone1 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone1.png");
		imgStone2 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone2.png");
		imgStone3 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone3.png");
		
		imgSemaphoreStart = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"fullSemaphore.png");
		imgSemaphoreRed = 			tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"redSemaphore.png");
		imgSemaphoreOrange = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"yellowSemaphore.png");
		imgSemaphoreGreen = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"greenSemaphore.png");

		mt.addImage(imgBackgroundMenu, 1);
		mt.addImage(imgBackGroundEnvironment, 2);
		mt.addImage(imgButtonStart, 3);
		mt.addImage(imgButtonStart2, 4);
		mt.addImage(imgButtonMultiplayer, 5);
		mt.addImage(imgButtonEditor, 6);
		mt.addImage(imgButtonExit, 7);
		mt.addImage(imgButtonExit2, 8);
		mt.addImage(imgButtonBack, 9);
		mt.addImage(imgButtonBack2, 10);
		mt.addImage(imgItaRacetrack, 11);
		mt.addImage(imgBraRacetrack, 12);
		mt.addImage(imgCarPlayer, 13);
		mt.addImage(imgCar1Menu, 14);
		mt.addImage(imgCar2Menu, 15);
		mt.addImage(imgGreenCarComputer, 16);
		mt.addImage(imgYellowCarComputer, 17);
		mt.addImage(imgBlueCarComputer, 18);
		mt.addImage(imgTree1, 19);
		mt.addImage(imgTree2, 20);
		mt.addImage(imgTree3, 21);
		mt.addImage(imgTree4, 22);
		mt.addImage(imgTree5, 23);
		mt.addImage(imgTree6, 24);
		mt.addImage(imgStone1, 25);
		mt.addImage(imgStone2, 26);
		mt.addImage(imgStone3, 27);
		mt.addImage(imgSemaphoreStart, 28);
		mt.addImage(imgSemaphoreRed, 29);
		mt.addImage(imgSemaphoreOrange, 30);
		mt.addImage(imgSemaphoreGreen, 31);

		for(int i = 0; i < 31; i++)
		{
			try {
				mt.waitForID(i);
			}
			catch(Exception e){}
		}
		return true;
		
	}


}
