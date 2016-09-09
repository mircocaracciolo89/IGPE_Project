package org.gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.core.GameManager;
import org.gui.Loader;
import org.gui.MainFrame;

public class SelectRacetrackPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Image img = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Giovanni\\workspaceIGPE\\Progetto_IGPE_\\img\\sfondo.jpg");

	public SelectRacetrackPanel(MainFrame mainFrame) {
		setLayout(null);
		
		ImageIcon icona = new ImageIcon( Loader.imgItaRacetrack);
		ImageIcon icona2 = new ImageIcon( Loader.imgBraRacetrack);
		ImageIcon icona3 = new ImageIcon(  Loader.imgButtonBack);
		ImageIcon icona4 = new ImageIcon( Loader.imgButtonBack2);

		JButton buttonRacetrack1 = new JButton(icona);
		JButton buttonRacetrack2 = new JButton(icona2);
		JButton backButton = new JButton(icona3);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		backButton.setIcon(icona3);
		backButton.setRolloverIcon(icona4);
		backButton.setPressedIcon(icona4);
		
		add(buttonRacetrack1);
		add(buttonRacetrack2);
		add(backButton);
		
		
		buttonRacetrack1.setBounds(80, 175, 500, 500); 
		buttonRacetrack2.setBounds(855, 175, 500, 500); 
		backButton.setBounds(10, 10, 100, 30);
		
		setPreferredSize(new Dimension(GameManager.WIDTH-1,GameManager.HEIGHT-1));
		
		buttonRacetrack1.addActionListener(e -> mainFrame.selectCar("Japan"));
		backButton.addActionListener( e -> mainFrame.goMenuPanel());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	//	g.drawImage(img, 0, 0, (int)d.getWidth(), (int)d.getHeight(), null);
		g.drawImage(Loader.imgBackgroundMenu,0,0,null);


	}
	
}
