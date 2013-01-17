package clue;

import javax.swing.SwingUtilities;

import clue.gui.MainFrame;
import clue.logic.ClueController;

/**
 * @author mjs221
 * 
 */
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
