package clue.gui;

import javax.swing.JTextArea;

import clue.api.ClueMediator;
import clue.api.Subscriber;

public class EventLog extends JTextArea implements Subscriber {
	/**
	 * 
	 */
	private static final long serialVersionUID = 500347484926295571L;
	private ClueMediator mediator;

	public EventLog(ClueMediator mediator, int rows, int col) {
		super(rows, col);
		this.mediator = mediator;
		mediator.registerSubscriber("EventLog", this);
	}

	@Override
	public void notify(String event) {
		this.setText(mediator.getEventLog());
	}
}
