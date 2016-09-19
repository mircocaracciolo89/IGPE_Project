package org.gui;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JComponent;

public class Loader extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/******************* IMAGES **********************************************************************************************/
	
	public static Image imgBackgroundMenu = null;
	public static Image imgBackgroundEnvironment = null;
	
	public static Image imgButtonStart = null;
	public static Image imgButtonStartFocus = null;
	public static Image imgButtonMultiplayer = null;
	public static Image imgButtonEditor = null;
	public static Image imgButtonExit = null;
	public static Image imgButtonExitFocus = null;
	public static Image imgButtonBack= null;
	public static Image imgButtonBackFocus = null;
	
	public static Image imgItaRacetrack= null;
	public static Image imgBraRacetrack= null;
	
	public static Image imgRedCar = null;
	public static Image imgCar1Menu = null;
	public static Image imgCar2Menu = null;
	public static Image imgGreenCar = null;
	public static Image imgYellowCar = null;
	public static Image imgBlueCar = null;
	
	public static Image imgTree1 = null;
	public static Image imgTree2 = null;
	public static Image imgTree3 = null;

	public static Image imgStone1 = null;
	public static Image imgStone2 = null;
	public static Image imgStone3 = null;

	public static Image imgSemaphoreStart = null;
	public static Image imgSemaphoreRed = null;
	public static Image imgSemaphoreOrange = null;
	public static Image imgSemaphoreGreen = null;

	public static Image imgBonus = null;
	public static Image imgMalus = null;
	
	public MediaTracker mt = new MediaTracker(this);
	Toolkit tk = Toolkit.getDefaultToolkit();

	/******************* LOADER **********************************************************************************************/

	public boolean load() throws MalformedURLException {

		imgBackgroundMenu = 		tk.getImage( "img"+File.separator+"Backgrounds"+File.separator+"backgroundMenu.jpg");
		imgBackgroundEnvironment = 	tk.getImage( "img"+File.separator+"Backgrounds"+File.separator+"backgroundEnvironment.jpg");
		imgButtonStart =			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"quickStart.png");
		imgButtonStartFocus =		tk.getImage( "img"+File.separator+"Buttons"+File.separator+"QuickStartFocus.png");
		imgButtonMultiplayer =		tk.getImage( "img"+File.separator+"Buttons"+File.separator+"multiplayer.png");
		imgButtonEditor = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"editor.png");
		imgButtonExit = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"exit.png");
		imgButtonExitFocus = 		tk.getImage( "img"+File.separator+"Buttons"+File.separator+"ExitFocus.png");
		imgButtonBack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"back.png");
		imgButtonBackFocus =		tk.getImage( "img"+File.separator+"Buttons"+File.separator+"backFocus.png");
		
		imgItaRacetrack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"Italy.png");
		imgBraRacetrack = 			tk.getImage( "img"+File.separator+"Buttons"+File.separator+"Brasil.png");
		
		imgRedCar = 				tk.getImage( "img"+File.separator+"Cars"+File.separator+"carPlayer.png");
		imgCar1Menu = 				tk.getImage( "img"+File.separator+"Buttons"+File.separator+"redCar.png");
		imgCar2Menu = 				tk.getImage( "img"+File.separator+"Buttons"+File.separator+"yellowCar.png");
		imgGreenCar = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"greenCarComputer.png");
		imgYellowCar = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"yellowCarComputer.png");
		imgBlueCar = 		tk.getImage( "img"+File.separator+"Cars"+File.separator+"blueCarComputer.png");
		
		imgTree1 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree1.png");
		imgTree2 =					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree2.png");
		imgTree3 = 					tk.getImage( "img"+File.separator+"Trees"+File.separator+"tree3.png");

		imgStone1 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone1.png");
		imgStone2 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone2.png");
		imgStone3 =					tk.getImage( "img"+File.separator+"Stones"+File.separator+"stone3.png");
		
		imgSemaphoreStart = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"fullSemaphore.png");
		imgSemaphoreRed = 			tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"redSemaphore.png");
		imgSemaphoreOrange = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"yellowSemaphore.png");
		imgSemaphoreGreen = 		tk.getImage( "img"+File.separator+"Semaphore"+File.separator+"greenSemaphore.png");

		imgBonus = 					tk.getImage( "img"+File.separator+"Bonus"+File.separator+"imgBonus.png");
		imgMalus = 					tk.getImage( "img"+File.separator+"Malus"+File.separator+"imgMalus1.png");

		mt.addImage(imgBackgroundMenu, 1);
		mt.addImage(imgBackgroundEnvironment, 2);
		mt.addImage(imgButtonStart, 3);
		mt.addImage(imgButtonStartFocus, 4);
		mt.addImage(imgButtonMultiplayer, 5);
		mt.addImage(imgButtonEditor, 6);
		mt.addImage(imgButtonExit, 7);
		mt.addImage(imgButtonExitFocus, 8);
		mt.addImage(imgButtonBack, 9);
		mt.addImage(imgButtonBackFocus, 10);
		mt.addImage(imgItaRacetrack, 11);
		mt.addImage(imgBraRacetrack, 12);
		mt.addImage(imgRedCar, 13);
		mt.addImage(imgCar1Menu, 14);
		mt.addImage(imgCar2Menu, 15);
		mt.addImage(imgGreenCar, 16);
		mt.addImage(imgYellowCar, 17);
		mt.addImage(imgBlueCar, 18);
		mt.addImage(imgTree1, 19);
		mt.addImage(imgTree2, 20);
		mt.addImage(imgTree3, 21);
		mt.addImage(imgStone1, 22);
		mt.addImage(imgStone2, 23);
		mt.addImage(imgStone3, 24);
		mt.addImage(imgSemaphoreStart, 25);
		mt.addImage(imgSemaphoreRed, 26);
		mt.addImage(imgSemaphoreOrange, 27);
		mt.addImage(imgSemaphoreGreen, 28);
		mt.addImage(imgBonus, 29);
		mt.addImage(imgMalus, 30);

		for(int i = 1; i <= 30; i++)
		{
			try {
				mt.waitForID(i);
			}
			catch(Exception e){}
		}
		return true;
		
	}

}