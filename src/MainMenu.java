import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.Random;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.text.*;
import javax.swing.*;



public class MainMenu extends JFrame { 
	
    JButton[] button = new JButton[30]; 

	  
    
    public MainMenu(){
    	
    	JFrame mainFrame = new JFrame(); //create a window
    	mainFrame.setTitle("SkillMenu");

    	mainFrame.setLocationRelativeTo(null);    	
    	mainFrame.setSize(600,850);
    	
    	JPanel panel = new JPanel(new GridBagLayout());
    	panel.setBackground(Color.LIGHT_GRAY);
    	panel.setSize(500,500);
        GridBagConstraints gbc = new GridBagConstraints();
        
     	mainFrame.add(panel); // adding JPanel to the JFrame 
    	
    	
    	for(int i=0; i<=29; i++){
    		int row = 0;
    		gbc.fill = GridBagConstraints.BOTH;
    		gbc.weightx= 0.5;
    		gbc.gridx = row;
    		gbc.gridy = i;
    		button[i] = new JButton(""+i); 
       		button[i].addActionListener(new ButtonListener(button[i])); 
    		panel.add(button[i], gbc); 
    		if (i==14){row++;}
    	}
    	
    
    	
    	
    	mainFrame.setVisible(true); 
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    } 
     
     
     
    public static void main(String[] args){     
       	new MainMenu();       	
    	
    }
}






