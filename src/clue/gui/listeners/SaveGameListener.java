package clue.gui.listeners;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clue.api.ClueMediator;
import clue.gui.MainFrame;

public class SaveGameListener implements ActionListener {
	private MainFrame mainFrame;
	private ClueMediator mediator;

	public SaveGameListener(MainFrame mainFrame, ClueMediator mediator) {
		this.mainFrame = mainFrame;
		this.mediator = mediator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileDialog dialog = new FileDialog(mainFrame, "Save Game",
				FileDialog.SAVE);
		dialog.setFile("*.clue");
		dialog.setVisible(true);
		String saveFileDir = dialog.getDirectory();
		String saveFileName = dialog.getFile();
		if (!(saveFileName == null)) {
			if (!(saveFileName.toLowerCase().endsWith(".clue"))) {
				saveFileName = saveFileName + ".clue";
			}
			String saveFilePath = saveFileDir + saveFileName;
			try {
				mediator.saveGame(saveFilePath);
			} catch (NullPointerException exception) {
				System.err.println("the save file is null");
			}
		}
	}
}
