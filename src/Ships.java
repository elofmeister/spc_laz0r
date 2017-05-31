import java.util.*;
import java.math.*;

//z.B. Ships shp = new Ships(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); aufruf aus main mit übergabewerten

public class Ships{

	private String SHIPNAME;
	private int CLASS;
	private int SHIELDSLOTS[];
	private int GUNSLOTS[];
	private int INVENTORY[];
	private int BONUSSLOTS[];
	private int LIFE;
	private int DMG;	
	private double FIRESPEED;
	

	
	
	Ships(int shipCLASS,int shipSHIELDSLOTS, int shipGUNSLOTS, int shipINVENTORYSIZE, int shipBONUSSLOTS, int shipLIFE, int shipDMG, double shipFIRESPEED){
		
		switch (shipCLASS){
		case 1: SHIPNAME = "Glasskanone";
				break;
		case 2: SHIPNAME = "Rumpler";
				break;
		case 3: SHIPNAME = "Standardo";
				break;
		}
		CLASS = shipCLASS;
		SHIELDSLOTS = new int[shipSHIELDSLOTS];
		GUNSLOTS = new int[shipGUNSLOTS];
		INVENTORY = new int[shipINVENTORYSIZE];
		BONUSSLOTS = new int[shipBONUSSLOTS];
		LIFE = shipLIFE;
		DMG = shipDMG;	
		FIRESPEED= shipFIRESPEED;
	}  
	
	
	//all needed get constructors
	
	public String getSHIPNAME(){
		return SHIPNAME;
	}
	
	public int getCLASS(){
		return CLASS;
	}
	
	public int getSHIELDSLOTS(int i){
		return SHIELDSLOTS[i];
	}
	 
	public int GUNSLOTS(int i){
		return GUNSLOTS[i];
	}
	
	public int getINVENTORY(int i){
		return INVENTORY[i];
	}
	
	public int getBONUSSLOTS(int i){
		return BONUSSLOTS[i];
	}
	
	public int getLIFE(){
		return LIFE;
	}
	 
	public int getDMG(){
		return DMG;
	}
	
	public double getFIRESPEED(){
		return FIRESPEED;
	}
	
	
	//all needed set constructors
	public void setSHIPNAME(String sVal){
		SHIPNAME=sVal;
	}
	
	public void setCLASS(int sVal){
		CLASS=sVal;
		
		switch (CLASS){
		case 1: setLIFE(60);
				setDMG(10);
				setFIRESPEED(1.00);
				break;
				
		case 2: setLIFE(100);
				setDMG(6);
				setFIRESPEED(1.40);
				break;
				
		case 3: setLIFE(80);
				setDMG(8);
				setFIRESPEED(1.20);
				break;
		}
	}
	
	public void setSHIELDSLOTS(int i, int sVal){
		SHIELDSLOTS[i]=sVal;
	}
	 
	public void setGUNSLOTS(int i, int sVal){
		GUNSLOTS[i]=sVal;
	}
	
	public void setINVENTORY(int i, int sVal){
		INVENTORY[i]=sVal;
	}
	public void setBONUSSLOTS(int i, int sVal){
		BONUSSLOTS[i]=sVal;
	}
	 
	public void setLIFE(int sVal){
		LIFE=LIFE+sVal;
	}
	
	public void setDMG(int sVal){
		DMG=DMG+sVal;
	}
	
	public void setFIRESPEED(double sVal){
		FIRESPEED=FIRESPEED+sVal;
	}
	
	
	
}
	
