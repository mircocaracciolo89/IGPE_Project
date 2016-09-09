package org.gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.core.GameManager;
import org.gui.Loader;
import org.gui.MainFrame;

public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Image img;
	

	public MenuPanel(MainFrame mainFrame) throws IOException {
		
		
		ImageIcon icona = new ImageIcon( Loader.imgButtonStart);
		ImageIcon icona2 = new ImageIcon( Loader.imgButtonStart2);
		JButton quickStart = new JButton(icona);
		quickStart.setOpaque(false);
		quickStart.setContentAreaFilled(false);
		quickStart.setBorderPainted(false);
		quickStart.setFocusPainted(false);
		quickStart.setIcon(icona);
		quickStart.setRolloverIcon(icona2);
		quickStart.setPressedIcon(icona2);
		
		
		ImageIcon icona3 = new ImageIcon( Loader.imgEditor);
		JButton editorButton = new JButton(icona3);
		editorButton.setOpaque(false);
		editorButton.setContentAreaFilled(false);
		editorButton.setBorderPainted(false);
		editorButton.setFocusPainted(false);
		editorButton.setIcon(icona3);
		
		ImageIcon icona5 = new ImageIcon( Loader.imgMultiplayer);
		JButton multiplayerButton = new JButton(icona5);
		multiplayerButton.setOpaque(false);
		multiplayerButton.setContentAreaFilled(false);
		multiplayerButton.setBorderPainted(false);
		multiplayerButton.setFocusPainted(false);
		multiplayerButton.setIcon(icona5);

		ImageIcon icona7 = new ImageIcon(  Loader.imgButtonExit);
		ImageIcon icona8 = new ImageIcon(  Loader.imgButtonExit2);
		JButton exitButton = new JButton(icona7);
		exitButton.setOpaque(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.setIcon(icona7);
		exitButton.setRolloverIcon(icona8);
		exitButton.setPressedIcon(icona8);
		
		
		quickStart.addActionListener(e -> mainFrame.selectRacetrack());
		
		multiplayerButton.addActionListener(e -> mainFrame.setupNetworkGame());
		
		exitButton.addActionListener(e -> System.exit(0));
		
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(GameManager.WIDTH,GameManager.HEIGHT));
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.insets = new Insets(50, 50, 50, 50);
		constraints.gridx = 0;
		constraints.gridy = 0;

		add(quickStart,constraints);
		constraints.gridy = 1;
		add(editorButton, constraints);
		constraints.gridy = 2;
		add(multiplayerButton,constraints);
		constraints.gridy = 3; 
		add(exitButton, constraints);
		constraints.gridy = 4;
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Loader.imgBackground,0,0,null);
	}
}
