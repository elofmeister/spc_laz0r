import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	private JFrame frame;	// window frame
	private Canvas canvas;	// canvas for drawing level tiles

	public Window(String title, int width, int height){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frame.setVisible(true);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public JFrame getFrame(){
		return frame;
	}
}