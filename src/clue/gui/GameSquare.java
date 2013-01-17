package clue.gui;

/**
 *
 * @author adamsturge991
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clue.api.model.SquareType;
import clue.model.Position;

public class GameSquare extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2502949767281395289L;
	private SquareType roomType;
	private Color tokenColor = null; // color of token in this square
	private boolean path;
	private int index; // index in the grid
	public static final Color hallwayColor = Color.LIGHT_GRAY;
	public static final Color roomColor = Color.ORANGE;
	public static final Color unusedColor = Color.BLACK; // color of unused
															// squares
	public JLabel roomLabel;

	public GameSquare(SquareType rt, Color c, int index, String text) {
		roomType = rt;
		tokenColor = c;
		path = false;
		this.index = index;
		this.roomLabel = new JLabel();
		if (text != null) {
			roomLabel.setText(text);
		}

		add(roomLabel);
		this.setPreferredSize(new Dimension(20, 20));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = this.getSize();
		if (!path) {
			if (roomType == SquareType.ROOM) {
				g.setColor(roomColor);
				g.fillRect(0, 0, d.width, d.height);
			} else if (roomType == SquareType.HALLWAY) {
				g.setColor(hallwayColor);
				g.fillRect(0, 0, d.width, d.height);
			} else {
				g.setColor(unusedColor);
				g.fillRect(0, 0, d.width, d.height);
			}
		}
		if (path) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, d.width, d.height);
		}
		if (tokenColor != null) {
			g.setColor(tokenColor);
			g.fillOval(2, 2, d.width - 4, d.height - 4);
		}

	}

	public int getIndex() {
		return index;
	}

	public SquareType getType() {
		return roomType;
	}

	public void setTokenColor(Color tokenColor) {
		this.tokenColor = tokenColor;
	}

	public void setPath(boolean b) {
		path = b;
	}

	public Position getPosition() {
		int y = index / 24;
		int x = index % 24;

		return new Position(x, y);
	}

	public void setText(String text) {
		roomLabel.setText(text);
	}

	public Color getTokenColor() {
		return tokenColor;
	}
}
