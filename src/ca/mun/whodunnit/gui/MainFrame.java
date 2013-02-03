package ca.mun.whodunnit.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.gui.listeners.LoadGameListener;
import ca.mun.whodunnit.gui.listeners.MainFrameListener;
import ca.mun.whodunnit.gui.listeners.QuitGameListener;
import ca.mun.whodunnit.gui.listeners.SaveGameListener;


public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8649005162354159732L;
	private JMenuItem save;
	private GamePanel gamePanel;
	private WhoDunnitMediator mediator;
	private CardLayout cardLayout;
	private final static String MENU_CARD = "Menu panel";
	private final static String GAME_CARD = "Game panel";
	private final static String NEWGAME_CARD = "New game panel";

	/**
	 * @throws HeadlessException
	 */
	public MainFrame(WhoDunnitMediator mediator) throws HeadlessException {
		this.mediator = mediator;
		gamePanel = new GamePanel(this);
		MenuPanel menuPanel = new MenuPanel(this, mediator);
		NewGamePanel newGamePanel = new NewGamePanel(this, mediator);

		// Menu bar
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem load, quit;

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");

		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		quit = new JMenuItem("Quit");

		save.setEnabled(false);

		save.addActionListener(new SaveGameListener(this, mediator));
		load.addActionListener(new LoadGameListener(this, mediator));
		quit.addActionListener(new QuitGameListener(this));

		fileMenu.add(save);
		fileMenu.add(load);
		fileMenu.add(quit);

		menuBar.add(fileMenu);

		// apple compatibility for menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("apple.awt.brushMetalLook", "true");
		// apple compatibility for file dialog
		System.setProperty("apple.awt.fileDialogForDirectories", "true");

		cardLayout = new CardLayout();

		// frame properties
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(700, 700));
		// setPreferredSize(new Dimension(700, 700));
		setTitle("Clue");
		setResizable(true);
		setVisible(true);
		setLayout(cardLayout);
		setJMenuBar(menuBar);
		addWindowListener(new MainFrameListener(this));

		// add the main panels
		add(menuPanel, MENU_CARD);
		add(gamePanel, GAME_CARD);
		add(newGamePanel, NEWGAME_CARD);
		pack();
	}

	public WhoDunnitMediator getMediator() {
		return mediator;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public JMenuItem getSaveButton() {
		return save;
	}

	public void showMenuPanel() {
		cardLayout.show(this.getContentPane(), MENU_CARD);
	}

	public void showGamePanel() {
		cardLayout.show(this.getContentPane(), GAME_CARD);
	}

	public void showNewGamePanel() {
		cardLayout.show(this.getContentPane(), NEWGAME_CARD);
	}
}
