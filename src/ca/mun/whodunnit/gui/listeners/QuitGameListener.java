package ca.mun.whodunnit.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ca.mun.whodunnit.gui.MainFrame;

public class QuitGameListener implements ActionListener {
	private MainFrame mainFrame;

	public QuitGameListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JOptionPane quitOptionPane = new JOptionPane(
				"If you want to save your game, do so before quitting \n"
						+ "\n Do you really want to quit?",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

		final JDialog dialog = new JDialog(mainFrame, "Quit", true);
		dialog.setContentPane(quitOptionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		quitOptionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();

				if (quitOptionPane.isVisible()
						&& (e.getSource() == quitOptionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {

					dialog.setVisible(false);
				}
			}
		});
		dialog.pack();
		dialog.setVisible(true);

		int quitValue = ((Integer) quitOptionPane.getValue()).intValue();
		if (quitValue == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (quitValue == JOptionPane.NO_OPTION) {
			dialog.dispose();
		}
	}
}
