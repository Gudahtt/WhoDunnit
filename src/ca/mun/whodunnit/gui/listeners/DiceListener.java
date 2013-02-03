package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.gui.GamePanel;

public class DiceListener implements ActionListener {
	private WhoDunnitMediator mediator;
	private GamePanel gamePanel;

	public DiceListener(WhoDunnitMediator mediator, GamePanel gamePanel) {
		this.mediator = mediator;
		this.gamePanel = gamePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingWorker<Void, Void> dieWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				mediator.rollDie();
				return null;
			}

		};

		dieWorker.execute();
		gamePanel.drawPathSquares();
		gamePanel.revalidate();
		gamePanel.repaint();
	}
}
