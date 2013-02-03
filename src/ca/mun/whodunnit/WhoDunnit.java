package ca.mun.whodunnit;

import javax.swing.SwingUtilities;

import ca.mun.whodunnit.gui.MainFrame;
import ca.mun.whodunnit.logic.WhoDunnitController;

public class WhoDunnit {
	private static WhoDunnitController clueController;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		clueController = new WhoDunnitController();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame(clueController.getMediator());
			}
		});
	}
}
