package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import ca.mun.whodunnit.gui.GamePanel;
import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.Player;

public class SelectListener implements ActionListener {
	private ArrayList<JCheckBox> checkboxList;
	private Player disprovingPlayer;
	private GamePanel gPanel;

	public SelectListener(ArrayList<JCheckBox> checkboxList,
			Player disprovingPlayer, GamePanel gPanel) {
		this.checkboxList = checkboxList;
		this.disprovingPlayer = disprovingPlayer;
		this.gPanel = gPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox selected = null;
		for (JCheckBox j : checkboxList) {
			if (j.isSelected()) {
				selected = j;
				break;
			}
		}
		try {
			String cardString = selected.getText();
			Card selectedCard = Card.getCard(cardString); // initialized to
															// random card

			gPanel.endDisproveSuggestion(disprovingPlayer, selectedCard);
		} catch (NullPointerException exception) {

		}
	}

}
