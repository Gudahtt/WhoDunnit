package ca.mun.whodunnit;

import javax.swing.SwingUtilities;

import ca.mun.whodunnit.gui.MainFrame;
import ca.mun.whodunnit.logic.ClueController;

public class Clue {
	private static ClueController clueController;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		clueController = new ClueController();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame(clueController.getMediator());
			}
		});
	}
}
