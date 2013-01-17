package clue.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import clue.api.ClueMediator;
import clue.gui.AccusationFrame;
import clue.gui.GamePanel;

public class AccusationListener implements ActionListener {
	private ClueMediator mediator;
	private GamePanel gamePanel;

	public AccusationListener(ClueMediator mediator, GamePanel gamePanel) {
		this.mediator = mediator;
		this.gamePanel = gamePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((JButton) e.getSource()).setEnabled(false);
		new AccusationFrame(mediator, gamePanel);
	}

}
