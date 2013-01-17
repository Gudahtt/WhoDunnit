package clue.logic;

import java.io.IOException;

import clue.api.ClueMediator;
import clue.api.EventBroker;
import clue.model.SessionData;

/**
 * @author mjs221
 * 
 */
public class ClueController {
	private GameController game;
	private ClueMediator mediator;
	private EventBroker broker;

	public ClueController() {
		broker = new EventBroker();
		game = new GameController(this);
		mediator = new ClueMediator(this);
	}

	/**
	 * @return the broker
	 */
	public EventBroker getBroker() {
		return broker;
	}

	/**
	 * @return the mediator
	 */
	public ClueMediator getMediator() {
		return mediator;
	}

	/**
	 * @return the game
	 */
	public GameController getGameController() {
		return game;
	}

	public void saveGame(String saveFilePath) throws IOException {
		SessionProcessor.saveSession(saveFilePath, game.getData());
	}

	public void loadGame(String loadFilePath) throws NullPointerException,
			IOException {
		SessionData data = SessionProcessor.loadSession(loadFilePath);
		game.setData(data);
		mediator.setData(data);
		broker.notify("EventLog");
		broker.notify("UpdateTokens");
		broker.notify("UpdateDie");
		broker.notify("UpdateSaveButton");
	}
}
