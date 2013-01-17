package clue.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import clue.api.model.PlayerType;
import clue.gui.listeners.CharacterRadioButtonListener;
import clue.model.Card;

public class CharacterPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1421122752691767459L;
	private JComboBox difficulty;
	private JRadioButton humanButton, compButton, inactiveButton;
	private Card card;
	private boolean activePlayer; // is an active player or not

	public CharacterPanel(Card card, String path, NewGamePanel nGPanel) {
		this.card = card;
		activePlayer = true; // default is human
		ImageProcessor imgProc;
		JPanel rButtonsP, options, comboBoxP, imageP;
		Border loweredEtched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		ButtonGroup group;
		JLabel img;

		rButtonsP = new JPanel();
		rButtonsP.setLayout(new BoxLayout(rButtonsP, BoxLayout.Y_AXIS));
		imageP = new JPanel();
		imageP.setLayout(new BoxLayout(imageP, BoxLayout.Y_AXIS));

		comboBoxP = new JPanel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(350, 450));
		setBorder(loweredEtched);

		imgProc = new ImageProcessor(path);
		img = new JLabel(imgProc.getImage());
		imageP.add(img);

		difficulty = new JComboBox(PlayerType.getDifficultyArray());
		difficulty.setEnabled(false);

		humanButton = new JRadioButton("Human Player");
		compButton = new JRadioButton("Compuer Player");
		inactiveButton = new JRadioButton("Inactive Player");

		humanButton.setName("human");
		compButton.setName("comp");
		inactiveButton.setName("inactive");

		humanButton.addActionListener(new CharacterRadioButtonListener(
				difficulty, this, nGPanel));
		compButton.addActionListener(new CharacterRadioButtonListener(
				difficulty, this, nGPanel));
		inactiveButton.addActionListener(new CharacterRadioButtonListener(
				difficulty, this, nGPanel));

		humanButton.setSelected(true);

		group = new ButtonGroup();
		group.add(humanButton);
		group.add(compButton);
		group.add(inactiveButton);

		rButtonsP.add(humanButton);
		rButtonsP.add(compButton);
		rButtonsP.add(inactiveButton);

		comboBoxP.add(difficulty);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

		options.add(rButtonsP);
		options.add(comboBoxP);

		add(imageP);
		add(options);

	}

	public PlayerType getDifficulty() {
		return (PlayerType) difficulty.getSelectedItem();
	}

	public JRadioButton getHumanButton() {
		return humanButton;
	}

	public JRadioButton getCompButton() {
		return compButton;
	}

	public JRadioButton getInactiveButton() {
		return inactiveButton;
	}

	public Card getCard() {
		return card;
	}

	public void setActivePlayer(boolean activePlayer) {
		this.activePlayer = activePlayer;
	}

	public boolean isActivePlayer() {
		return activePlayer;
	}
}
