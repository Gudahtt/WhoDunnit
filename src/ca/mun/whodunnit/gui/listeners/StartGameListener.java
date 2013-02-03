package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.gui.MainFrame;
import ca.mun.whodunnit.gui.NewGamePanel;

public class StartGameListener implements ActionListener {
	private MainFrame mainFrame;
	private ClueMediator mediator;

	public StartGameListener(MainFrame mainFrame, ClueMediator mediator) {
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
