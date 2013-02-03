package ca.mun.whodunnit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.border.MatteBorder;

import ca.mun.whodunnit.api.ClueMediator;
import ca.mun.whodunnit.api.Subscriber;
import ca.mun.whodunnit.api.model.Direction;
import ca.mun.whodunnit.api.model.SquareType;
import ca.mun.whodunnit.gui.listeners.AccusationListener;
import ca.mun.whodunnit.gui.listeners.DiceListener;
import ca.mun.whodunnit.gui.listeners.MovementListener;
import ca.mun.whodunnit.gui.listeners.SelectListener;
import ca.mun.whodunnit.gui.listeners.SuggestionListener;
import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.ComputerPlayer;
import ca.mun.whodunnit.model.Player;
import ca.mun.whodunnit.model.Position;
import ca.mun.whodunnit.model.Turn;
import ca.mun.whodunnit.model.Card.Type;

public class GamePanel extends JPanel implements Subscriber {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1960462598652164047L;
	private ClueMediator mediator;
	private JButton suggestion, accusation, die, turn, undoMovement;
	private JPanel gameBoard, controlPanel;
	private DicePanel dPanel;
	private EventLog eventLog;
	private NotebookPanel nPanel;
	private JFrame disproveFrame;
	private MainFrame mainFrame;
	private JTabbedPane tabbedControlPane;
	private ArrayList<GameSquare> pathSquares; // highlighted squares

	public GamePanel(MainFrame mainFrame) {
		super(new BorderLayout());
		pathSquares = new ArrayList<GameSquare>();
		mediator = mainFrame.getMediator();
		this.mainFrame = mainFrame;

		mediator.registerSubscriber("UpdateTokens", this);
		mediator.registerSubscriber("Disprove", this);
		mediator.registerSubscriber("StartGame", this);
		mediator.registerSubscriber("EndTurn", this);
		mediator.registerSubscriber("UpdateDie", this);
		mediator.registerSubscriber("StartTurn", this);
		mediator.registerSubscriber("UpdatePaths", this);
		mediator.registerSubscriber("UpdateSuggestion", this);
		mediator.registerSubscriber("UpdateRetractMovement", this);
		mediator.registerSubscriber("EndGame", this);
		mediator.registerSubscriber("UpdateSaveButton", this);

		gameBoard = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GameSquare gSquare;
		SquareType roomType = null;
		Color tokenColor = null;

		// adding gamesquares to grid
		c.weightx = 0.05;
		c.weighty = 0.05;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 24; j++) {
				c.gridwidth = 1;
				tokenColor = null;
				roomType = mediator.checkIsRoom(new Position(j, i));
				gSquare = new GameSquare(roomType, tokenColor, j + i * 24, null);
				gSquare.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
				gSquare.addMouseListener(new MovementListener(this, mediator));
				c.fill = GridBagConstraints.BOTH;
				c.gridx = j;
				c.gridy = i;
				switch (j + 24 * i) {
				case 50:
					gSquare.setText("Kitchen");
					c.gridwidth = 2;
					break;
				case 83:
					gSquare.setText("Ballroom");
					c.gridwidth = 2;
					break;
				case 68:
					gSquare.setText("Conservatory");
					c.gridwidth = 3;
					break;
				case 267:
					gSquare.setText("DiningRoom");
					c.gridwidth = 3;
					break;
				case 554:
					gSquare.setText("Lounge");
					c.gridwidth = 2;
					break;
				case 563:
					gSquare.setText("Hall");
					c.gridwidth = 2;
					break;
				case 547:
					gSquare.setText("Study");
					c.gridwidth = 2;
					break;
				case 236:
					gSquare.setText("BillardRoom");
					c.gridwidth = 3;
					break;
				case 380:
					gSquare.setText("Library");
					c.gridwidth = 2;
					break;

				}
				gameBoard.add(gSquare, c);

			}
		}

		// adding room borders
		for (int i = 0; i < 24 * 25; i++) {
			setGameSquareBorder(i, gameBoard);
		}

		// making grid
		for (int i = 0; i < 24 * 25; i++) {
			gSquare = (GameSquare) gameBoard.getComponent(i);
			MatteBorder m = (MatteBorder) gSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.left += 1;
			in.bottom += 1;
			gSquare.setBorder(new MatteBorder(in, Color.BLACK));

		}

		// Placing Doorways
		HashMap<Position, Direction> entrances = mediator.getEntrances();
		for (Position p : entrances.keySet()) {
			Direction direction = entrances.get(p);
			int x = p.getX();
			int y = p.getY();
			int gridIndex = x + 24 * y;
			GameSquare currentSquare = (GameSquare) gameBoard
					.getComponent(gridIndex);

			if (direction == Direction.NORTH) {
				MatteBorder m = (MatteBorder) currentSquare.getBorder();
				Insets in = m.getBorderInsets();
				in.top = 0;
				currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
			}
			if (direction == Direction.SOUTH) {
				MatteBorder m = (MatteBorder) currentSquare.getBorder();
				Insets in = m.getBorderInsets();
				in.bottom = 1;
				currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
			}
			if (direction == Direction.WEST) {
				MatteBorder m = (MatteBorder) currentSquare.getBorder();
				Insets in = m.getBorderInsets();
				in.left = 1;
				currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
			}
			if (direction == Direction.EAST) {
				MatteBorder m = (MatteBorder) currentSquare.getBorder();
				Insets in = m.getBorderInsets();
				in.right = 0;
				currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
			}

		}

		JScrollPane gameScroller = new JScrollPane(gameBoard);

		controlPanel = new JPanel();
		controlPanel
				.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

		suggestion = new JButton("Make a Suggestion");
		accusation = new JButton("Make an Accusation");
		die = new JButton("Roll Die");
		undoMovement = new JButton("Undo Movement");
		turn = new JButton("Start Turn");

		dPanel = new DicePanel();

		suggestion.addActionListener(new SuggestionListener(mediator, this));
		accusation.addActionListener(new AccusationListener(mediator, this));
		die.addActionListener(new DiceListener(mediator, this));

		undoMovement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> retractMovementWorker = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						mediator.retractMovement();
						return null;
					}
				};

				retractMovementWorker.execute();
			}
		});

		turn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ((JButton) e.getSource()).getText();
				if (text.equals("Start Turn")) {
					SwingWorker<Void, Void> startWorker = new SwingWorker<Void, Void>() {

						@Override
						protected Void doInBackground() throws Exception {
							mediator.startTurn();
							return null;
						}
					};

					startWorker.execute();
				} else {
					SwingWorker<Void, Void> endWorker = new SwingWorker<Void, Void>() {

						@Override
						protected Void doInBackground() throws Exception {
							mediator.endTurn();
							return null;
						}
					};

					mediator.storeNotebook(nPanel.getStates(), nPanel.getText());
					endWorker.execute();
					tabbedControlPane.setSelectedIndex(0);
				}
			}
		});

		tabbedControlPane = new JTabbedPane();

		suggestion.setEnabled(false);
		accusation.setEnabled(false);
		die.setEnabled(false);
		undoMovement.setEnabled(false);

		controlPanel.add(dPanel);
		controlPanel.add(suggestion);
		controlPanel.add(accusation);
		controlPanel.add(die);
		controlPanel.add(undoMovement);
		controlPanel.add(turn);

		eventLog = new EventLog(mediator, 5, 5);
		eventLog.setEditable(false);
		JScrollPane eventScroller = new JScrollPane(eventLog);

		nPanel = new NotebookPanel(mediator);
		JScrollPane nScroller = new JScrollPane(nPanel);
		tabbedControlPane.add("Controls", controlPanel);
		tabbedControlPane.add("Notebook", nScroller);

		tabbedControlPane.setMinimumSize(new Dimension(200, 500));
		// tabbedControlPane.setPreferredSize(new Dimension(200, 500));
		gameScroller.setMinimumSize(new Dimension(500, 500));
		eventScroller.setMinimumSize(new Dimension(500, 100));

		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());

		JSplitPane vSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				gameScroller, eventScroller);
		vSplitPane.setContinuousLayout(true);

		rightPanel.add(vSplitPane);
		leftPanel.add(tabbedControlPane);

		JSplitPane hSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPanel, rightPanel);
		hSplitPane.setContinuousLayout(true);

		add(hSplitPane, BorderLayout.CENTER);
	}

	/**
	 * @return the suggestion
	 */
	public JButton getSuggestion() {
		return suggestion;
	}

	/**
	 * @return the accusation
	 */
	public JButton getAccusation() {
		return accusation;
	}

	public void setGameSquareBorder(int i, JPanel gameBoard) {

		GameSquare southSquare;
		GameSquare eastSquare;
		GameSquare currentSquare = (GameSquare) gameBoard.getComponent(i);
		SquareType currentSquareType = currentSquare.getType();
		edgeBorder(i, currentSquare);

		if (i == 599) // last square
		{
			MatteBorder m = (MatteBorder) currentSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.right = 1;
			in.bottom = 1;
			currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
		} else if (576 <= i && i <= 598) // bottom row except last square
		{

			eastSquare = (GameSquare) gameBoard.getComponent(i + 1);

			if (currentSquareType == SquareType.HALLWAY) {
				if (eastSquare.getType() != currentSquareType) {
					MatteBorder m = (MatteBorder) eastSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.left = 1;
					eastSquare.setBorder(new MatteBorder(in, Color.BLACK));
				}
			} else {
				if (eastSquare.getType() != currentSquareType) {
					MatteBorder m = (MatteBorder) currentSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.right = 1;
					currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
				}
			}
		}

		else // everything else
		{
			eastSquare = (GameSquare) gameBoard.getComponent(i + 1);
			southSquare = (GameSquare) gameBoard.getComponent(i + 24);

			if (currentSquareType == SquareType.HALLWAY) {
				if (eastSquare.getType() != currentSquareType) {

					MatteBorder m = (MatteBorder) eastSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.left = 1;
					eastSquare.setBorder(new MatteBorder(in, Color.BLACK));

				}
				if (southSquare.getType() != currentSquareType) {
					MatteBorder m = (MatteBorder) southSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.top = 1;
					southSquare.setBorder(new MatteBorder(in, Color.BLACK));
				}
			} else {
				if (eastSquare.getType() != currentSquareType) {
					MatteBorder m = (MatteBorder) currentSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.right = 1;
					currentSquare.setBorder(new MatteBorder(in, Color.BLACK));

				}
				if (southSquare.getType() != currentSquareType) {
					MatteBorder m = (MatteBorder) currentSquare.getBorder();
					Insets in = m.getBorderInsets();
					in.bottom = 1;
					currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
				}
			}
		}
	}

	public void edgeBorder(int i, GameSquare currentSquare) {
		if (0 <= i && i <= 23) // top
		{
			MatteBorder m = (MatteBorder) currentSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.top = 1;
			currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
		}
		if (576 <= i && i <= 599) // bottom
		{
			MatteBorder m = (MatteBorder) currentSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.bottom = 1;
			currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
		}
		if (i % 24 == 0) {
			MatteBorder m = (MatteBorder) currentSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.left = 1;
			currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
		}
		if (i % 24 == 23) {
			MatteBorder m = (MatteBorder) currentSquare.getBorder();
			Insets in = m.getBorderInsets();
			in.right = 1;
			currentSquare.setBorder(new MatteBorder(in, Color.BLACK));
		}
	}

	@Override
	public void notify(String event) {
		if (event.equals("UpdateTokens")) {
			Color tokenColor = null;

			for (int i = 0; i < 600; i++) {
				GameSquare square = (GameSquare) this.gameBoard.getComponent(i);
				if (square.getTokenColor() == Color.GRAY) {
					square.setText("");
				}
				square.setTokenColor(tokenColor);
			}

			for (Card character : Card.getTypes(Type.SUSPECT)) {
				Position playerPos = mediator.getTokenPosition(character);

				int x = playerPos.getX();
				int y = playerPos.getY();
				int gridIndex = x + 24 * y;

				GameSquare playerSquare = (GameSquare) gameBoard
						.getComponent(gridIndex);

				if (character == Card.COLONEL_MUSTARD) {
					tokenColor = Color.YELLOW;
				}
				if (character == Card.MISS_SCARLET) {
					tokenColor = Color.RED;
				}
				if (character == Card.MR_GREEN) {
					tokenColor = Color.GREEN;
				}
				if (character == Card.MRS_PEACOCK) {
					tokenColor = Color.BLUE;
				}
				if (character == Card.MRS_WHITE) {
					tokenColor = Color.WHITE;
				}
				if (character == Card.PROFESSOR_PLUM) {
					tokenColor = Color.MAGENTA;
				}

				playerSquare.setTokenColor(tokenColor);
			}

			for (Card weapon : Card.getTypes(Type.WEAPON)) {
				Position weaponPos = mediator.getTokenPosition(weapon);

				int x = weaponPos.getX();
				int y = weaponPos.getY();
				int gridIndex = x + 24 * y;

				GameSquare weaponSquare = (GameSquare) gameBoard
						.getComponent(gridIndex);

				weaponSquare.setTokenColor(Color.GRAY);
				weaponSquare.setText(weapon.toString().substring(0, 2));
			}

			if (mediator.getCurrentTurn().getPlayer().getAlive()) {
				this.drawPathSquares();
			}

		}
		if (event.equals("Disprove")) {
			Turn currentTurn = mediator.getCurrentTurn();
			Player currentPlayer = currentTurn.getPlayer();
			Player disprovingPlayer = currentTurn.getDisprovingPlayer();

			if (currentPlayer.isHuman() && disprovingPlayer.isHuman()) {
				humanDisprovingHuman(disprovingPlayer);
			} else if (!currentPlayer.isHuman() && disprovingPlayer.isHuman()) {
				humanDisprovingComputer(disprovingPlayer);
			} else {
				computerDisproving(disprovingPlayer);
			}

		} else if (event.equals("StartGame")) {
			this.dPanel.loadImage(0, mediator.getCurrentTurn().getPlayer()
					.getCharacter());
			this.tabbedControlPane.setEnabledAt(1, false);
		} else if (event.equals("EndTurn")) {
			this.nPanel.clearNotebook();
			drawPathSquares(); // erase any remaining squares
			Card currentCharacter = mediator.getCurrentTurn().getPlayer()
					.getCharacter();
			this.dPanel.loadImage(0, currentCharacter);
			this.suggestion.setEnabled(false);
			this.accusation.setEnabled(false);
			this.die.setEnabled(false);
			this.undoMovement.setEnabled(false);
			this.turn.setText("Start Turn");
			tabbedControlPane.setEnabledAt(1, false);

			if (!mediator.getCurrentTurn().getPlayer().isHuman()) {
				turn.setEnabled(false);
			} else {
				turn.setEnabled(true);
			}

		} else if (event.equals("UpdateDie")) {
			Turn currentTurn = mediator.getCurrentTurn();
			this.dPanel.loadImage(currentTurn.getRemainingSteps(), currentTurn
					.getPlayer().getCharacter());
			die.setEnabled(mediator.canRollDie());
		} else if (event.equals("UpdatePaths")) {
			this.drawPathSquares();
		} else if (event.equals("StartTurn")) {
			if (mediator.getCurrentTurn().getPlayer().isHuman()) {
				this.accusation.setEnabled(true);
				this.die.setEnabled(true);
				this.turn.setText("End Turn");
				this.tabbedControlPane.setEnabledAt(1, true);
			} else {
				this.accusation.setEnabled(false);
				this.die.setEnabled(false);
				this.turn.setText("End Turn");
			}
			this.nPanel.populateNotebook(mediator.getCurrentTurn().getPlayer());

		} else if (event.equals("UpdateSuggestion")) {
			if (mediator.getCurrentTurn().getPlayer().isHuman()
					&& mediator.canSuggest()) {
				this.suggestion.setEnabled(true);
			} else {
				this.suggestion.setEnabled(false);
			}
		} else if (event.equals("UpdateRetractMovement")) {
			if (mediator.canRetractMovement() && mediator.checkIsHuman()) {
				this.undoMovement.setEnabled(true);
			} else {
				this.undoMovement.setEnabled(false);
			}
		} else if (event.equals("Highlight")) {
			this.drawPathSquares();
		} else if (event.equals("EndGame")) {
			this.endGame();
		} else if (event.equals("UpdateSaveButton")) {
			if (mediator.getCurrentTurn().getPlayer().isHuman()
					&& turn.getText().equals("Start Turn"))
				mainFrame.getSaveButton().setEnabled(true);
			else
				mainFrame.getSaveButton().setEnabled(false);
		}
	}

	public void endDisproveSuggestion(Player disprovingPlayer, Card selectedCard) {
		// remove list of disproving players cards
		if (disprovingPlayer.isHuman()) {
			disproveFrame.dispose();
		}
		Turn currentTurn = mediator.getCurrentTurn();
		SwingWorker<Void, Void> endDisproveWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				mediator.endDisproveSuggestion();
				return null;
			}

		};

		tabbedControlPane.setSelectedIndex(0);

		if (currentTurn.getPlayer().isHuman()) {
			// reset back to current players notebook
			nPanel.clearNotebook();
			nPanel.populateNotebook(currentTurn.getPlayer());

			// display popup with disproving players selected card and reset
			// disproving card to null for loop condition
			String promptChangeString = disprovingPlayer.getCharacter()
					.toString()
					+ " has selected a card.  Ensure that everyone but "
					+ currentTurn.getPlayer().getCharacter().toString()
					+ " has looked away";

			JOptionPane.showMessageDialog(this, promptChangeString);
			JOptionPane.showMessageDialog(this, disprovingPlayer.getCharacter()
					.toString() + " has shown you " + selectedCard.toString());

			endDisproveWorker.execute();

		} else {
			currentTurn.getPlayer().getNotebook()
					.changeState(selectedCard, "No");
			nPanel.clearNotebook();
			nPanel.populateNotebook(currentTurn.getPlayer());
			endDisproveWorker.execute();
		}
	}

	public ArrayList<GameSquare> getPathSquares() {

		ArrayList<Position> pathSquaresPositions = mediator.getPathPositions();
		ArrayList<GameSquare> pathSquares = new ArrayList<GameSquare>();
		for (Position p : pathSquaresPositions) {
			int x = p.getX();
			int y = p.getY();
			int gridIndex = x + 24 * y;
			pathSquares.add((GameSquare) gameBoard.getComponent(gridIndex));
		}

		return pathSquares;
	}

	public void drawPathSquares() {
		for (GameSquare g : pathSquares) // unhighlight old path
		{
			g.setPath(false);
		}

		pathSquares = getPathSquares();
		for (GameSquare g : pathSquares) // highlight new path
		{
			g.setPath(true);
		}

		gameBoard.revalidate();
		gameBoard.repaint();

	}

	public GameSquare get(int gridIndex) {
		return (GameSquare) gameBoard.getComponent(gridIndex);
	}

	public void humanDisprovingHuman(Player disprovingPlayer) {
		Turn currentTurn = mediator.getCurrentTurn();

		JOptionPane.showMessageDialog(gameBoard, disprovingPlayer
				.getCharacter().toString()
				+ " can disprove you.  Ensure everyone but "
				+ disprovingPlayer.getCharacter().toString()
				+ " has looked away");

		mediator.storeNotebook(nPanel.getStates(), nPanel.getText());

		nPanel.clearNotebook();
		nPanel.populateNotebook(disprovingPlayer);
		tabbedControlPane.setSelectedIndex(1);

		// show disproving player his hand and ask him to select a card to
		// show current player
		disproveFrame = new JFrame("Select a Card");
		// disproveFrame.setLayout(new GridLayout(0,1));
		JPanel disprovePanel = new JPanel(new GridLayout(0, 1));
		JCheckBox box;
		ButtonGroup checkboxGroup = new ButtonGroup();
		ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();

		ArrayList<Card> disprovingCards = currentTurn.getDisprovingCards();
		ArrayList<Card> disprovingPlayerHand = disprovingPlayer.getHand();
		for (Card c : disprovingPlayerHand) {
			box = new JCheckBox(c.toString());
			if (!disprovingCards.contains(c)) {
				box.setEnabled(false);
			}

			checkboxGroup.add(box);
			disprovePanel.add(box);
			checkboxList.add(box);
		}

		JButton selectButton = new JButton("Select Card");
		selectButton.addActionListener(new SelectListener(checkboxList,
				disprovingPlayer, this));

		disprovePanel.add(selectButton);
		disproveFrame.getContentPane().add(disprovePanel);

		disproveFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		disproveFrame.setResizable(true);
		disproveFrame.setMinimumSize(new Dimension(400, 400));
		disproveFrame.setVisible(true);
		disproveFrame.setAlwaysOnTop(true);
	}

	public void humanDisprovingComputer(Player disprovingPlayer) {
		Turn currentTurn = mediator.getCurrentTurn();

		mediator.storeNotebook(nPanel.getStates(), nPanel.getText());

		nPanel.clearNotebook();
		nPanel.populateNotebook(disprovingPlayer);

		String player = mediator.getCurrentTurn().getPlayer().getCharacter()
				.toString();

		JOptionPane.showMessageDialog(gameBoard, disprovingPlayer
				.getCharacter().toString()
				+ " can disprove "
				+ player
				+ ".  Ensure everyone but "
				+ disprovingPlayer.getCharacter().toString()
				+ " has looked away.");

		tabbedControlPane.setSelectedIndex(1);

		// show disproving player his hand and ask him to select a card to
		// show current player
		disproveFrame = new JFrame("Select a Card");
		// disproveFrame.setLayout(new GridLayout(0,1));
		JPanel disprovePanel = new JPanel(new GridLayout(0, 1));
		JCheckBox box;
		ButtonGroup checkboxGroup = new ButtonGroup();
		ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();

		ArrayList<Card> disprovingCards = currentTurn.getDisprovingCards();
		ArrayList<Card> disprovingPlayerHand = disprovingPlayer.getHand();
		for (Card c : disprovingPlayerHand) {
			box = new JCheckBox(c.toString());
			if (!disprovingCards.contains(c)) {
				box.setEnabled(false);
			}

			checkboxGroup.add(box);
			disprovePanel.add(box);
			checkboxList.add(box);
		}

		JButton selectButton = new JButton("Select Card");
		selectButton.addActionListener(new SelectListener(checkboxList,
				disprovingPlayer, this));

		disprovePanel.add(selectButton);
		disproveFrame.getContentPane().add(disprovePanel);

		disproveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disproveFrame.setResizable(true);
		disproveFrame.setMinimumSize(new Dimension(400, 400));
		disproveFrame.setVisible(true);
		disproveFrame.setAlwaysOnTop(true);
	}

	public void computerDisproving(Player disprovingPlayer) {
		Turn currentTurn = mediator.getCurrentTurn();
		ComputerPlayer disprovingComputerPlayer = (ComputerPlayer) disprovingPlayer;
		if (currentTurn.getPlayer().isHuman()) {
			JOptionPane.showMessageDialog(gameBoard, disprovingPlayer
					.getCharacter().toString() + " can disprove you.");
		} else {
			JOptionPane.showMessageDialog(gameBoard, disprovingPlayer
					.getCharacter().toString()
					+ " can disprove "
					+ currentTurn.getPlayer().getCharacter().toString());
		}

		mediator.storeNotebook(nPanel.getStates(), nPanel.getText());

		Card selectedCard = mediator
				.getDisprovingCard(disprovingComputerPlayer);
		this.endDisproveSuggestion(disprovingPlayer, selectedCard);
	}

	public void endGame() {
		this.accusation.setEnabled(false);
		this.suggestion.setEnabled(false);
		this.die.setEnabled(false);
		this.tabbedControlPane.setEnabledAt(1, false);

		mediator.endGame();
		
		String winner = mediator.getWinner().toString();
		
		JOptionPane.showMessageDialog(gameBoard, "The game has ended. " + winner + " is the winner!");
		
		mainFrame.showMenuPanel();

		Color tokenColor = null;

		for (int i = 0; i < 600; i++) {
			GameSquare square = (GameSquare) this.gameBoard.getComponent(i);
			square.setTokenColor(tokenColor);
		}
	}

}
