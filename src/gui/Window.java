package gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window {

	// Main-Window
	private JFrame frame;
	
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
	
	public Window(String title) {
		util.ConfigReader config = new util.ConfigReader("config.json");
		frame = new JFrame(title);
		frame.setUndecorated(config.isFullscreen());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(comp);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
	    gs.setFullScreenWindow(frame);
	    frame.validate();
	    if (!config.isFullscreen()) {
	    	frame.setResizable(false);
		    frame.setSize((int)config.getWidth(), (int)config.getHeight());
			frame.setLocationRelativeTo(null);
		}
	    setBlankCursor();
	    Random rnd = new Random();
	    rnd.setSeed(System.currentTimeMillis());
	    int num = Math.abs(rnd.nextInt()%3)+1;
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
