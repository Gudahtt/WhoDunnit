package clue.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import clue.gui.CharacterPanel;
import clue.gui.NewGamePanel;

public class CharacterRadioButtonListener implements ActionListener {
	private JComboBox box;
	private CharacterPanel characterPanel; // panel containing the radio button
											// this listener
											// is attached to
	private NewGamePanel nGPanel;

	public CharacterRadioButtonListener(JComboBox box,
			CharacterPanel characterPanel, NewGamePanel nGPanel) {
		this.box = box;
		this.characterPanel = characterPanel;
		this.nGPanel = nGPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton button = (JRadioButton) e.getSource();
		if (button.getName().equals("human")) {
			box.setEnabled(false);
			characterPanel.setActivePlayer(true);
			nGPanel.setStart(); // determine if start should be enabled
		} else if (button.getName().equals("comp")) {
			box.setEnabled(true);
			characterPanel.setActivePlayer(true);
			nGPanel.setStart();
		} else if (button.getName().equals("inactive")) {
			box.setEnabled(false);
			characterPanel.setActivePlayer(false);
			nGPanel.setStart();
		}

	}
}
