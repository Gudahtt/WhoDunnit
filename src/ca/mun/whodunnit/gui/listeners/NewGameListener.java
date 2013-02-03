package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.mun.whodunnit.gui.MainFrame;

public class NewGameListener implements ActionListener {
	private MainFrame mainFrame;

	public NewGameListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainFrame.showNewGamePanel();
	}

}
