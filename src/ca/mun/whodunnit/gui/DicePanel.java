package ca.mun.whodunnit.gui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mun.whodunnit.model.Card;

public class DicePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7210322783158344187L;
	private ArrayList<String> diceImagePaths;
	private ArrayList<String> playerImagePaths;
	private ImageProcessor imgProc;

	JLabel dieImage;
	JLabel playerImage;

	public DicePanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		diceImagePaths = new ArrayList<String>();
		diceImagePaths.add("/resources/die_face_0.png");
		diceImagePaths.add("/resources/die_face_1.png");
		diceImagePaths.add("/resources/die_face_2.png");
		diceImagePaths.add("/resources/die_face_3.png");
		diceImagePaths.add("/resources/die_face_4.png");
		diceImagePaths.add("/resources/die_face_5.png");
		diceImagePaths.add("/resources/die_face_6.png");

		imgProc = new ImageProcessor(diceImagePaths.get(0));
		dieImage = new JLabel(imgProc.getImage());

		add(dieImage);

		playerImagePaths = new ArrayList<String>();
		playerImagePaths.add("/resources/scarlett.jpg");
		playerImagePaths.add("/resources/mustard.jpg");
		playerImagePaths.add("/resources/white.jpg");
		playerImagePaths.add("/resources/green.jpg");
		playerImagePaths.add("/resources/peacock.jpg");
		playerImagePaths.add("/resources/plum.jpg");

		imgProc = new ImageProcessor(playerImagePaths.get(0));
		playerImage = new JLabel(imgProc.getImage());

		add(playerImage);
	}

	public void loadImage(int i, Card playerCard) {
		switch (i) {
		case 0:
			imgProc.setImage(diceImagePaths.get(0));
			break;
		case 1:
			imgProc.setImage(diceImagePaths.get(1));
			break;
		case 2:
			imgProc.setImage(diceImagePaths.get(2));
			break;
		case 3:
			imgProc.setImage(diceImagePaths.get(3));
			break;
		case 4:
			imgProc.setImage(diceImagePaths.get(4));
			break;
		case 5:
			imgProc.setImage(diceImagePaths.get(5));
			break;
		case 6:
			imgProc.setImage(diceImagePaths.get(6));
			break;

		}
		dieImage.setIcon(imgProc.getImage());

		if (playerCard == Card.MISS_SCARLET) {
			imgProc.setImage(playerImagePaths.get(0));
		}
		if (playerCard == Card.COLONEL_MUSTARD) {
			imgProc.setImage(playerImagePaths.get(1));
		}
		if (playerCard == Card.MRS_WHITE) {
			imgProc.setImage(playerImagePaths.get(2));
		}
		if (playerCard == Card.MR_GREEN) {
			imgProc.setImage(playerImagePaths.get(3));
		}
		if (playerCard == Card.MRS_PEACOCK) {
			imgProc.setImage(playerImagePaths.get(4));
		}
		if (playerCard == Card.PROFESSOR_PLUM) {
			imgProc.setImage(playerImagePaths.get(5));
		}

		playerImage.setIcon(imgProc.getImage());
		revalidate();
		repaint();
	}
}
