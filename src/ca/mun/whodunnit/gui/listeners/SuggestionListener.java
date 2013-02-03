package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.gui.GamePanel;
import ca.mun.whodunnit.gui.SuggestionFrame;

public class SuggestionListener implements ActionListener {
	private WhoDunnitMediator mediator;
	private GamePanel gamePanel;

	public SuggestionListener(WhoDunnitMediator mediator, GamePanel gamePanel) {
		this.mediator = mediator;
		this.gamePanel = gamePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((JButton) e.getSource()).setEnabled(false);
		new SuggestionFrame(mediator, gamePanel);

	}

}
