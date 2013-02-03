package ca.mun.whodunnit.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.gui.listeners.AccusationSubmitListener;
import ca.mun.whodunnit.model.Card;

public class AccusationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6558322288515197744L;

	public AccusationFrame(ClueMediator mediator, final GamePanel gamePanel) {
		super();
		this.setAlwaysOnTop(true);
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel room = new JPanel();
		JPanel suspect = new JPanel();
		JPanel weapon = new JPanel();
		JPanel control = new JPanel();

		room.setPreferredSize(new Dimension(10, 10));
		suspect.setPreferredSize(new Dimension(10, 10));
		weapon.setPreferredSize(new Dimension(10, 10));
		control.setPreferredSize(new Dimension(10, 10));

		Border loweredetched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);

		TitledBorder roomTitle = BorderFactory.createTitledBorder(
				loweredetched, "Room");
		TitledBorder suspectTitle = BorderFactory.createTitledBorder(
				loweredetched, "Suspect");
		TitledBorder weaponTitle = BorderFactory.createTitledBorder(
				loweredetched, "Weapon");

		roomTitle.setTitleJustification(TitledBorder.LEFT);
		suspectTitle.setTitleJustification(TitledBorder.LEFT);
		weaponTitle.setTitleJustification(TitledBorder.LEFT);

		room.setBorder(roomTitle);
		suspect.setBorder(suspectTitle);
		weapon.setBorder(weaponTitle);

		JComboBox roomList = new JComboBox(Card.getCardArray(Card.Type.ROOM));
		JComboBox suspectList = new JComboBox(
				Card.getCardArray(Card.Type.SUSPECT));
		JComboBox weaponList = new JComboBox(
				Card.getCardArray(Card.Type.WEAPON));

		roomList.setSelectedIndex(0);
		suspectList.setSelectedIndex(0);
		weaponList.setSelectedIndex(0);

		room.add(roomList);
		suspect.add(suspectList);
		weapon.add(weaponList);

		JButton cancel = new JButton("Cancel");
		JButton submit = new JButton("Submit");

		submit.addActionListener(new AccusationSubmitListener(roomList,
				suspectList, weaponList, this, mediator));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.getAccusation().setEnabled(true);
				dispose();
			}
		});

		control.add(cancel);
		control.add(submit);

		getContentPane().add(room);
		getContentPane().add(suspect);
		getContentPane().add(weapon);
		getContentPane().add(control);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(200, 300));
		setResizable(false);
		setVisible(true);

		pack();
	}
}
