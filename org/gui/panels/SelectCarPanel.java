package gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.GameManager;
import gui.Loader;
import gui.MainFrame;

public class SelectCarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public SelectCarPanel(MainFrame mainFrame) throws IOException {
		setLayout(null);
		
		ImageIcon icona = new ImageIcon( Loader.imgCar1);
		ImageIcon icona2 = new ImageIcon( Loader.imgCar2);
		ImageIcon icona3 = new ImageIcon( Loader.imgBackButton);
		ImageIcon icona4 = new ImageIcon( Loader.imgBackButton2);

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
		
		buttonCar1.addActionListener(e -> mainFrame.startGame(Loader.imgCar));
		backButton.addActionListener( e -> mainFrame.goSelectRacetrack());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Loader.imgBackground, 0, 0, null);
	}
	
}
