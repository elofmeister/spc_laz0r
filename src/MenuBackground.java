import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//@SuppressWarnings("unused")
public class MenuBackground extends JFrame {
		
	MenuBackground() {
    add(new ContentPanel());
    setSize(1024, 576);
    
   
  }

  public static void main(String[] args) {
	  MenuBackground bgframe = new MenuBackground();
	  bgframe.setLocationRelativeTo(null);
	  bgframe.setVisible(true);
	  
//	  JPanel f = new JPanel();
//	  f.setSize( 200, 200 );
//	  f.setLayout(new FlowLayout());
	//  f.setLocation(100, 100);
	 //f.setOpaque( true );
//	  JButton b = new JButton("ISCH bin Knopf");
//	  bgframe.add(b);
//	  JButton c = new JButton("ISCH bin Knopssf");
//	  f.add(c);
//	  JButton d = new JButton("ISCHsdf bin Knopssf");
//	  f.add(d);
//	  bgframe.add(f);
//	  f.setVisible(true);
	  
  }

}

//@SuppressWarnings("unused")
class ContentPanel extends JPanel {
  Image bgimage = null;

  ContentPanel() {
    MediaTracker mt = new MediaTracker(this);
    bgimage = Toolkit.getDefaultToolkit().getImage("C://Users/Rechenoperator/Desktop/Studium/2. Semester/Java projekt/tiles/loading.png");
    mt.addImage(bgimage, 0);
    try {
      mt.waitForAll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgimage,0,0,getWidth(), getHeight(),this);
  }
}

