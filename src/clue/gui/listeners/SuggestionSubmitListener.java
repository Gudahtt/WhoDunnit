package clue.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import clue.api.ClueMediator;
import clue.model.Card;

//submit button listener

public class SuggestionSubmitListener implements ActionListener {
	private JComboBox suspectList;
	private JComboBox weaponList;
	private JFrame frame;
	private ClueMediator mediator;

	public SuggestionSubmitListener(JComboBox suspectList,
			JComboBox weaponList, JFrame frame, ClueMediator mediator) {
		this.suspectList = suspectList;
		this.weaponList = weaponList;
		this.frame = frame;
		this.mediator = mediator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Card selectedSuspect = (Card) suspectList.getSelectedItem();
		final Card selectedWeapon = (Card) weaponList.getSelectedItem();

		SwingWorker<Void, Void> suggestionWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				mediator.makeSuggestion(selectedSuspect, selectedWeapon);
				return null;
			}

		};

		suggestionWorker.execute();
		frame.dispose();
	}
}
