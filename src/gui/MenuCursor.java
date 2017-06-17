package gui;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuCursor {
	
	private Image img = Toolkit.getDefaultToolkit().getImage(MenuCursor.class.getResource("images/MenuCursor.png"));
	private Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "menu cursor");
	
	public MenuCursor() {
	}

	public Cursor getCursor() {
		return cursor;
	}
}
