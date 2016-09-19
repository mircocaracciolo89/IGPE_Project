package org.gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.core.GameManager;
import org.gui.Loader;
import org.gui.MainFrame;

public class SelectCarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/******************* CONSTRUCTOR **********************************************************************************************/
	
	public SelectCarPanel(MainFrame mainFrame) throws IOException {
		setLayout(null);
		
		ImageIcon icona = new ImageIcon( Loader.imgCar1Menu);
		ImageIcon icona2 = new ImageIcon( Loader.imgCar2Menu);
		ImageIcon icona3 = new ImageIcon( Loader.imgButtonBack);
		ImageIcon icona4 = new ImageIcon( Loader.imgButtonBackFocus);

		JButton buttonCar1 = new JButton(icona);
		JButton buttonCar2 = new JButton(icona2);
		JButton backButton = new JButton(icona3);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		backButton.setIcon(icona3);
		backButton.setRolloverIcon(icona4);
		backButton.setPressedIcon(icona4);
		
		add(buttonCar1);
		add(buttonCar2);
		add(backButton);
		
		buttonCar1.setBounds(80, 175, 500, 500); 
		buttonCar2.setBounds(855, 175, 500, 500); 
		backButton.setBounds(10, 10, 100, 30);
		
		setPreferredSize(new Dimension(GameManager.WIDTH-2,GameManager.HEIGHT-2));
		
		buttonCar1.addActionListener(e -> mainFrame.startGame(Loader.imgRedCar));
		buttonCar2.addActionListener(e -> mainFrame.startGame(Loader.imgYellowCar));
		backButton.addActionListener( e -> mainFrame.goSelectRacetrack());
	}
	
	/******************* SERVICE METHODS **********************************************************************************************/

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Loader.imgBackgroundMenu, 0, 0, null);
	}
	
}
