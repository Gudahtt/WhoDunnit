package ca.mun.whodunnit.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.Notebook;
import ca.mun.whodunnit.model.Player;

/**
 * notebook GUI, creates buttons and text areas to write in. Grabs notebook
 * through game session and loops through it setting the buttons to the
 * appropriate states. As of now does not get textarea text since it is not in
 * the notebook object on application side
 * 
 */

public class NotebookPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7675103272542596352L;
	private HashMap<ButtonGroup, ArrayList<JCheckBox>> buttonGroups;
	private JTextArea wNotes;
	private ArrayList<JTextArea> textLabels;

	public NotebookPanel(ClueMediator mediator) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel notebook = new JPanel(new GridLayout(22, 4));
		this.textLabels = new ArrayList<JTextArea>();

		wNotes = new JTextArea();
		JScrollPane playerNotesScroller = new JScrollPane(wNotes,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		notebook.add(new JTextArea("Yes"));
		notebook.add(new JTextArea("No"));
		notebook.add(new JTextArea("Maybe"));
		notebook.add(new JTextArea("Card"));

		ArrayList<Card> rooms = Card.getTypes(Card.Type.ROOM);
		ArrayList<Card> suspects = Card.getTypes(Card.Type.SUSPECT);
		ArrayList<Card> weapons = Card.getTypes(Card.Type.WEAPON);
		buttonGroups = new HashMap<ButtonGroup, ArrayList<JCheckBox>>();
		JCheckBox yes;
		JCheckBox no;
		JCheckBox maybe;
		DeselectableButtonGroup buttonGroup;
		JTextArea textArea;

		for (int i = 0; i < suspects.size(); ++i) {
			yes = new JCheckBox();
			no = new JCheckBox();
			maybe = new JCheckBox();
			buttonGroup = new DeselectableButtonGroup();
			buttonGroup.add(yes);
			buttonGroup.add(no);
			buttonGroup.add(maybe);

			ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();

			boxes.add(yes);
			boxes.add(no);
			boxes.add(maybe);
			boxes.add(new JCheckBox(suspects.get(i).toString()));

			buttonGroups.put(buttonGroup, boxes);

			textArea = new JTextArea(suspects.get(i).toString());
			textLabels.add(textArea);

			notebook.add(yes);
			notebook.add(no);
			notebook.add(maybe);
			notebook.add(textArea);

		}
		for (int i = 0; i < weapons.size(); ++i) {
			yes = new JCheckBox();
			no = new JCheckBox();
			maybe = new JCheckBox();
			buttonGroup = new DeselectableButtonGroup();
			buttonGroup.add(yes);
			buttonGroup.add(no);
			buttonGroup.add(maybe);

			ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();

			boxes.add(yes);
			boxes.add(no);
			boxes.add(maybe);
			boxes.add(new JCheckBox(weapons.get(i).toString()));

			buttonGroups.put(buttonGroup, boxes);

			textArea = new JTextArea(weapons.get(i).toString());
			textLabels.add(textArea);

			notebook.add(yes);
			notebook.add(no);
			notebook.add(maybe);
			notebook.add(textArea);
		}

		for (int i = 0; i < rooms.size(); ++i) {
			yes = new JCheckBox();
			no = new JCheckBox();
			maybe = new JCheckBox();
			buttonGroup = new DeselectableButtonGroup();
			buttonGroup.add(yes);
			buttonGroup.add(no);
			buttonGroup.add(maybe);
			ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();

			boxes.add(yes);
			boxes.add(no);
			boxes.add(maybe);
			boxes.add(new JCheckBox(rooms.get(i).toString()));

			buttonGroups.put(buttonGroup, boxes);

			textArea = new JTextArea(rooms.get(i).toString());
			textLabels.add(textArea);

			notebook.add(yes);
			notebook.add(no);
			notebook.add(maybe);
			notebook.add(textArea);
		}

		add(notebook);
		add(playerNotesScroller);

	}

	public HashMap<Card, String> getStates() {
		HashMap<Card, String> states = new HashMap<Card, String>();

		for (ButtonGroup b : buttonGroups.keySet()) {
			boolean[] buttonSelected = new boolean[3];

			buttonSelected[0] = false;
			buttonSelected[1] = false;
			buttonSelected[2] = false;

			if (buttonGroups.get(b).get(0).isSelected())
				states.put(Card.getCard(buttonGroups.get(b).get(3).getText()),
						"Yes");
			else if (buttonGroups.get(b).get(1).isSelected())
				states.put(Card.getCard(buttonGroups.get(b).get(3).getText()),
						"No");
			else if (buttonGroups.get(b).get(2).isSelected())
				states.put(Card.getCard(buttonGroups.get(b).get(3).getText()),
						"Maybe");
			else
				states.put(Card.getCard(buttonGroups.get(b).get(3).getText()),
						"Unchecked");

		}
		return states;
	}

	public String getText() {
		return wNotes.getText();
	}

	public void populateNotebook(Player p) {
		Notebook noteBook = p.getNotebook();
		HashMap<Card, String> states = noteBook.getStateList();
		ArrayList<Card> hand = p.getHand();

		ButtonGroup[] groups = buttonGroups.keySet()
				.toArray(new ButtonGroup[0]);

		for (ButtonGroup g : groups) {
			g.clearSelection();

			JCheckBox yes = this.buttonGroups.get(g).get(0);
			JCheckBox no = this.buttonGroups.get(g).get(1);
			JCheckBox maybe = this.buttonGroups.get(g).get(2);

			String title = this.buttonGroups.get(g).get(3).getText();
			JTextArea textLabel = null;
			for (JTextArea t : textLabels) {
				if (title.equals(t.getText())) {
					textLabel = t;
					break;
				}
			}

			for (Card c : hand) {
				if (c.toString().equals(title)) {
					textLabel.setBackground(Color.CYAN);
				}
			}

			String state = states.get(Card.getCard(title));

			if (state.equals("Maybe")) {
				maybe.setSelected(true);

			}
			if (state.equals("No")) {
				no.setSelected(true);

			}
			if (state.equals("Yes")) {
				yes.setSelected(true);

			}

		}

		wNotes.setText(noteBook.getText());
	}

	public void clearNotebook() {
		for (ButtonGroup group : this.buttonGroups.keySet()) {
			for (JCheckBox box : this.buttonGroups.get(group)) {
				box.setSelected(false);
			}
		}
		for (JTextArea j : textLabels) {
			j.setBackground(null);
		}

		wNotes.setText("");
		revalidate();
		repaint();
	}

	public class DeselectableButtonGroup extends ButtonGroup {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5624636837019652550L;

		public void setSelected(ButtonModel model, boolean selected) {

			if (selected) {

				super.setSelected(model, selected);

			} else {

				clearSelection();
			}
		}
	}
}
