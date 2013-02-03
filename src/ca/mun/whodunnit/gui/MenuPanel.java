package ca.mun.whodunnit.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.gui.listeners.LoadGameListener;
import ca.mun.whodunnit.gui.listeners.NewGameListener;

public class MenuPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8049644036188782142L;

	public MenuPanel(MainFrame mainFrame, ClueMediator mediator) {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(new BoxLayout(imgPanel, BoxLayout.X_AXIS));
		ImageProcessor imgProc = new ImageProcessor(
				"/resources/clue-board-game-box-cover-01.jpg");
		JLabel image = new JLabel(imgProc.getImage());

		imgPanel.add(image);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton newGameButton = new JButton("New Game"), loadGameButton = new JButton(
				"Load Game");

		newGameButton.addActionListener(new NewGameListener(mainFrame));
		loadGameButton.addActionListener(new LoadGameListener(mainFrame,
				mediator));

		buttonPanel.add(newGameButton);
		buttonPanel.add(loadGameButton);

		add(imgPanel);
		add(buttonPanel);
	}
}
