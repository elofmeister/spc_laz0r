package gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import util.ConfigReader;

public class Window {

	// Main-Window
	private JFrame frame;
	private ConfigReader config = new ConfigReader("config.json");
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice gs = ge.getDefaultScreenDevice();
	private DisplayMode dispModeOld;
	
	// Background-Image
	private Component comp = new Component() {
		private static final long serialVersionUID = 2513633783220324477L;
		Image img = new ImageIcon(this.getClass().getResource("images/loading1.png")).getImage();
	    @Override
	    public void paint(Graphics g) {
	        super.paint(g);
	        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	    }
	};
	
	public void setWindowedMode() {
		gs.setDisplayMode(dispModeOld);
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(false);
		gs.setFullScreenWindow(null);
		frame.setSize((int)config.getWidth(), (int)config.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.repaint();
	}
	
	public void setFullscreenMode() {
		dispModeOld = gs.getDisplayMode();
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(true);
		gs.setFullScreenWindow(frame);
		frame.setVisible(true);
		frame.repaint();
	}
	
	public Window(String title) {
		frame = new JFrame(title);
		frame.setUndecorated(config.isFullscreen());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(comp);
	    frame.validate();
		dispModeOld = gs.getDisplayMode();
		gs.setFullScreenWindow(frame);
	    if (config.isFullscreen()) {
			setFullscreenMode();		
		} else {
			setWindowedMode();
		}
	    setBlankCursor();
	    Random rnd = new Random();
	    rnd.setSeed(System.currentTimeMillis());
	    int num = Math.abs(rnd.nextInt()%4)+1;
	    changeImage(new ImageIcon(this.getClass().getResource("images/loading"+num+".png")).getImage());
	}

	public Image PathToImage(String path) {
		return new ImageIcon(this.getClass().getResource(path)).getImage();
	}
	
	public void changeImage(Image image) {
		frame.remove(comp);
		comp = new Component() {
			private static final long serialVersionUID = 2513633783220324477L;
			Image img = image;
		    @Override
		    public void paint(Graphics g) {
		        super.paint(g);
		        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		frame.add(comp);
		frame.revalidate();
	}
	
	public void changeTitle(String newTitle) {
		frame.setTitle(newTitle);
	}
	
	public void exit() {
		frame.dispose();
	}
	
	public static void main(String[] args) {
		new Window("Test");

	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setBlankCursor() {
		frame.setCursor(new gui.BlankCursor().getCursor());
	}
	
	public void setMenuCursor() {
		frame.setCursor(new gui.MenuCursor().getCursor());
	}
}
