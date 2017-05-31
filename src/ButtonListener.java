import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonListener implements ActionListener{ 
	Character plr = new Character("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);   
    JButton button;
	 public ButtonListener(JButton button){ 
        this.button = button; 
    } 
    public void actionPerformed(ActionEvent e) { 
        if(e.getSource() == button){ 
        	int characterStat = 1;        	
             System.out.println("Button '" + ((JButton)e.getSource()).getText() + "' geklickt." );
             String text = ((JButton)e.getSource()).getText(); 
             int buttonNumber = Integer.parseInt(text);
             System.out.println("Buttonnummer" + buttonNumber);
            
             	 
           switch (buttonNumber){
	            case 0: plr.setXP(1);
         				System.out.println("xp" + plr.getXP());
	            		break;	     
	            case 1: plr.setXP(10);
 				System.out.println("xp" + plr.getXP());	
	            
	        	        break;
			    case 2:  plr.setNAME("Schas");
 		plr.setACID(buttonNumber);
 		plr.setICE(buttonNumber);
 		plr.setXP(100);
 		plr.setLASER(buttonNumber);
 		plr.setNAME("Test");
 		
			             break;
			    case 3:  characterStat = 1;			    	
			             break;
			    case 4:           break;
			    case 5: 			             break;
			    case 6:			             break;
			    case 7:  characterStat = 34;
			    			             break;
			    case 8:  characterStat = 12;
			             break;
			    case 9:  characterStat = 321;
			             break;
			    case 10: characterStat = 34;
			             break;
			    case 11: characterStat = 324;
			             break;
			    case 12: characterStat = 234;
			             break;
			    default: characterStat = 45;
			             break;
             }
        System.out.println("NAME:"+plr.getNAME()+" XP:"+plr.getXP()+" LVL:"+plr.getLVL()+" PTS:"+plr.getSKILLPTS()+" AGL:"+plr.getAGL()+" CRITP:"+plr.getCRITPRB()+" CRITX:"+plr.getCRITDMG()+" LZR:"+plr.getLASER()+" ACID:"+plr.getACID()+" ICE:"+plr.getICE()+" EMP:"+plr.getEMP()+" CASH:"+plr.getCASH());                     	

    }
}
}    
