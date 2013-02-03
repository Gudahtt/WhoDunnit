package ca.mun.whodunnit.gui.listeners;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.gui.MainFrame;

public class LoadGameListener implements ActionListener {
	private MainFrame mainFrame;
	private ClueMediator mediator;

	public LoadGameListener(MainFrame mainFrame, ClueMediator mediator) {
		this.mediator = mediator;
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileDialog dialog = new FileDialog(mainFrame, "Load Game",
				FileDialog.LOAD);
		dialog.setFile("*.clue");
		dialog.setVisible(true);
		final String loadFileDir = dialog.getDirectory();
		final String loadFileName = dialog.getFile();

		if (!(loadFileName == null)) {
			final String loadFilePath = loadFileDir + loadFileName;

			SwingWorker<Void, Void> loadWorker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					mediator.loadGame(loadFilePath);
					return null;
				}

			};

			try {
				loadWorker.execute();
			} catch (NullPointerException exception) {
				System.err.println("the load file is null");
			}

			// show the game panel
			mainFrame.showGamePanel();
		}
	}
}
