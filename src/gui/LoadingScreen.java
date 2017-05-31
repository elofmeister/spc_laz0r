package gui;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LoadingScreen {

	JFrame frame = new JFrame("Loading");
	
	public LoadingScreen() {
		util.ConfigReader config = new util.ConfigReader("config.json");
		frame.setUndecorated(config.isFullscreen());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(new Component() {
			private static final long serialVersionUID = 2513633783220324477L;
			Image img = new ImageIcon(this.getClass().getResource("images/loading.png")).getImage();
		    @Override
		    public void paint(Graphics g) {
		        super.paint(g);
		        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		    }
		});

	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
	    gs.setFullScreenWindow(frame);
	    frame.validate();
	    if (!config.isFullscreen()) {
	    	frame.setResizable(false);
		    frame.setSize((int)config.getWidth(), (int)config.getHeight());
			frame.setLocationRelativeTo(null);
		}
	}
	
	public void exit() {
		frame.dispose();
	}
}
