package ca.mun.whodunnit.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.api.model.PlayerType;
import ca.mun.whodunnit.gui.listeners.StartGameListener;
import ca.mun.whodunnit.model.Card;

public class NewGamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8897532607080053849L;
	private static ArrayList<CharacterPanel> characterPanels;
	private JButton start;

	public NewGamePanel(MainFrame mainFrame, WhoDunnitMediator mediator) {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// First three characters.
		JPanel characterLineOne = new JPanel();
		characterLineOne.setLayout(new BoxLayout(characterLineOne,
				BoxLayout.X_AXIS));

		// Last three characters.
		JPanel characterLineTwo = new JPanel();
		characterLineTwo.setLayout(new BoxLayout(characterLineTwo,
				BoxLayout.X_AXIS));

		// Start Button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		final String scarlettPath = "/resources/scarlett.jpg";
		final String mustardPath = "/resources/mustard.jpg";
		final String whitePath = "/resources/white.jpg";
		final String greenPath = "/resources/green.jpg";
		final String peacockPath = "/resources/peacock.jpg";
		final String plumPath = "/resources/plum.jpg";

		CharacterPanel scarlett = new CharacterPanel(Card.MISS_SCARLET,
				scarlettPath, this);
		CharacterPanel mustard = new CharacterPanel(Card.COLONEL_MUSTARD,
				mustardPath, this);
		CharacterPanel white = new CharacterPanel(Card.MRS_WHITE, whitePath,
				this);
		CharacterPanel green = new CharacterPanel(Card.MR_GREEN, greenPath,
				this);
		CharacterPanel peacock = new CharacterPanel(Card.MRS_PEACOCK,
				peacockPath, this);
		CharacterPanel plum = new CharacterPanel(Card.PROFESSOR_PLUM, plumPath,
				this);

		characterPanels = new ArrayList<CharacterPanel>();

		characterPanels.add(scarlett);
		characterPanels.add(mustard);
		characterPanels.add(white);
		characterPanels.add(green);
		characterPanels.add(peacock);
		characterPanels.add(plum);

		characterLineOne.add(scarlett);
		characterLineOne.add(mustard);
		characterLineOne.add(white);

		characterLineTwo.add(green);
		characterLineTwo.add(peacock);
		characterLineTwo.add(plum);

		start = new JButton("Start Game");
		start.addActionListener(new StartGameListener(mainFrame, mediator));
		add(start);
		buttonPanel.add(start);

		add(characterLineOne);
		add(characterLineTwo);
		add(buttonPanel);
	}

	public static HashMap<Card, PlayerType> getPlayers() {
		HashMap<Card, PlayerType> players = new HashMap<Card, PlayerType>();

		for (CharacterPanel p : characterPanels) {
			if (p.getHumanButton().isSelected()) {
				players.put(p.getCard(), PlayerType.HUMAN);
			} else if (p.getCompButton().isSelected()) {
				players.put(p.getCard(), p.getDifficulty());
			}
		}
		return players;
	}

	public void setStart() {
		int numberOfActivePlayers = 0;
		for (CharacterPanel p : characterPanels) {
			if (p.isActivePlayer()) {
				numberOfActivePlayers++;
			}
		}
		if (numberOfActivePlayers > 2) {
			start.setEnabled(true);
		} else
			start.setEnabled(false);
	}

}
