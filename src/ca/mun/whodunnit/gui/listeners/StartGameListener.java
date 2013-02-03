package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.gui.MainFrame;
import ca.mun.whodunnit.gui.NewGamePanel;

public class StartGameListener implements ActionListener {
	private MainFrame mainFrame;
	private WhoDunnitMediator mediator;

	public StartGameListener(MainFrame mainFrame, WhoDunnitMediator mediator) {
		this.mainFrame = mainFrame;
		this.mediator = mediator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingWorker<Void, Void> startGameWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				mediator.startGame(NewGamePanel.getPlayers());
				return null;
			}
		};

		startGameWorker.execute();

		mainFrame.showGamePanel();
	}
}
