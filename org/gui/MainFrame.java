package org.gui;

import org.gui.panels.GamePanel;
import org.gui.panels.MenuPanel;
import org.gui.panels.SelectCarPanel;
import org.gui.panels.SelectRacetrackPanel;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.core.GameManager;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Loader loader;

	private GameManager gameManager;
	private GamePanel gamePanel;
	private static MenuPanel menuPanel;
	private SelectRacetrackPanel selectRacetrackPanel;
	private SelectCarPanel selectCarPanel;

	public MainFrame() throws IOException {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				showExitConfirm();
			}
		});

		gameManager = new GameManager();
		menuPanel = new MenuPanel(this);

		setContentPane(menuPanel);
//		setUndecorated(true);
		pack();

	}

	protected void showExitConfirm() {
		int option = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?","Exit",JOptionPane.YES_NO_CANCEL_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			setContentPane(menuPanel);
		}
	}

	private void start() 
	{
		gamePanel.start();
	}

	public void selectRacetrack()
	{
		selectRacetrackPanel = new SelectRacetrackPanel(this);

		SwingUtilities.invokeLater(() -> {
			setContentPane(selectRacetrackPanel);
			setLocationRelativeTo(null);
			pack();
			//			start();
		});
	}

	public void startGame(Image img)
	{

		gameManager.setVehicles(GameManager.getEnvironment().getRacetrack().getStartPointCarPlayer(), GameManager.getEnvironment().getRacetrack().getStartTranslationCarPlayer(), GameManager.getEnvironment().getRacetrack().getStartOrientationCarPlayer(), img);
		try {
			gamePanel = new GamePanel(gameManager);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			setContentPane(gamePanel);
			setLocationRelativeTo(null);

			gamePanel.requestFocusInWindow();
			gamePanel.addKeyListener(new KeyDetected());

			pack();
			start();

		});
	}

	public void goMenuPanel()
	{
		SwingUtilities.invokeLater(() -> {
			setContentPane(menuPanel);
			setLocationRelativeTo(null);
			pack();
		});
	}

	public void selectCar(String nameFile)
	{
		gameManager.setEnvironment(nameFile);

		try {
			selectCarPanel = new SelectCarPanel(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			setContentPane(selectCarPanel);
			setLocationRelativeTo(null);
			pack();
		});
	}

	public void goSelectRacetrack()
	{
		SwingUtilities.invokeLater(() -> {
			setContentPane(selectRacetrackPanel);
			setLocationRelativeTo(null);
			pack();
		});
	}

	public void setupNetworkGame() {
		//		JDialog dialog = new JDialog(this);
		//		dialog.setModal(true);
		//		dialog.setContentPane(new ConnectionPanel(this, dialog));
		//		dialog.pack();
		//		dialog.setLocationRelativeTo(this);
		//		dialog.setVisible(true);
		//
		//		SwingUtilities.invokeLater(() -> setContentPane(new JLabel(
		//				"Please wait...")));
	}

	public static void main(String[] args) throws IOException {

		loader = new Loader();
		if(loader.load()) {
			MainFrame mainFrame = new MainFrame();
			mainFrame.setVisible(true);
		}

	}

}
