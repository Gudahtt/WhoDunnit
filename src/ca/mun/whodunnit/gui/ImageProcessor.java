package ca.mun.whodunnit.gui;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageProcessor {
	private Icon icon;

	public ImageProcessor(final String path) {
		setImage(path);
	}

	public void setImage(final String path) {
		URL url = getClass().getResource(path);
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		icon = new ImageIcon(image);
	}

	public Icon getImage() {
		return icon;
	}
}