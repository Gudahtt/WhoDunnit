package ca.mun.whodunnit.logic;

import java.io.IOException;

import ca.mun.whodunnit.api.WhoDunnitMediator;
import ca.mun.whodunnit.api.EventBroker;
import ca.mun.whodunnit.model.SessionData;

public class WhoDunnitController {
	private GameController game;
	private WhoDunnitMediator mediator;
	private EventBroker broker;

	public WhoDunnitController() {
		broker = new EventBroker();
		game = new GameController(this);
		mediator = new WhoDunnitMediator(this);
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
	public WhoDunnitMediator getMediator() {
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
