package gui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class BlankCursor {

	private BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	private Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "blank cursor");
	
	public BlankCursor() {
	}
	
	public Cursor getCursor() {
		return cursor;
	}
}
