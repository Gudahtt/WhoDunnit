package clue.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import clue.api.ClueMediator;
import clue.gui.GamePanel;
import clue.gui.GameSquare;

public class MovementListener implements MouseListener {
	private GamePanel gamePanel;
	private ClueMediator mediator;

	public MovementListener(GamePanel gamePanel, ClueMediator mediator) {
		this.gamePanel = gamePanel;
		this.mediator = mediator;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (mediator.getCurrentTurn().getPlayer().isHuman()) {
			final GameSquare clickedSquare = (GameSquare) arg0.getSource();
			ArrayList<GameSquare> pathSquares = gamePanel.getPathSquares();

			if (pathSquares.contains(clickedSquare)) {
				SwingWorker<Void, Void> moveWorker = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						mediator.moveCurrentPlayer(clickedSquare.getPosition());
						return null;
					}

				};

				moveWorker.execute();
				gamePanel.revalidate();
				gamePanel.repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
