package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.model.Card;

public class AccusationSubmitListener implements ActionListener {
	private JComboBox roomList;
	private JComboBox suspectList;
	private JComboBox weaponList;
	private JFrame frame;
	private ClueMediator mediator;

	public AccusationSubmitListener(JComboBox roomList, JComboBox suspectList,
			JComboBox weaponList, JFrame frame, ClueMediator mediator) {
		this.roomList = roomList;
		this.suspectList = suspectList;
		this.weaponList = weaponList;
		this.frame = frame;
		this.mediator = mediator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Card selectedRoom = (Card) roomList.getSelectedItem();
		final Card selectedSuspect = (Card) suspectList.getSelectedItem();
		final Card selectedWeapon = (Card) weaponList.getSelectedItem();

		SwingWorker<Void, Void> accusationWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				mediator.makeAccusation(selectedRoom, selectedSuspect,
						selectedWeapon);
				return null;
			}

		};

		accusationWorker.execute();
		frame.dispose();
	}
}
