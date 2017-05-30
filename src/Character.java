import java.util.*;
import java.math.*;

// z.B. Character plr = new Character("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); aufruf aus main mit übergabewerten

public class Character{

	private String NAME;
	private int XP;
	private int OLDXP;
	private int LVL;
	private int SKILLPTS;
	private double AGL;
	private double CRITPRB;
	private double CRITDMG;
	private int LASER;
	private int ACID;
	private int ICE;
	private int EMP;
	private int CASH;
	
	
	Character(String meNAME,int meXP, int meLVL, int meSKILLPTS, double meAGL, double meCRITPRB, double meCRITDMG, int meLASER, int meACID , int meICE, int meEMP, int meCASH){
		
		 NAME = meNAME;
		 XP = meXP;
		 OLDXP = meXP;
		 LVL = meLVL;
		 SKILLPTS = meSKILLPTS;
		 AGL = meAGL;
	     CRITPRB = meCRITPRB;
		 CRITDMG = meCRITDMG;
		 LASER = meLASER;
		 ACID = meACID;
		 ICE = meICE;
		 EMP = meEMP;
		 CASH = meCASH;
	}  
	
	
	//all needed get constructors
	public String getNAME(){
		return NAME;
	}
	public int getXP(){
		return XP;
	}
	 
	public int getLVL(){
		return LVL;
	}
	
	public int getSKILLPTS(){
		return SKILLPTS;
	}
	
	public double getAGL(){
		return AGL;
	}
	
	public double getCRITPRB(){
		return CRITPRB;
	}
	
	public double getCRITDMG(){
		return CRITDMG;
	}
	 
	public int getLASER(){
		return LASER;
	}
	
	public int getACID(){
		return ACID;
	}
	
	public int getICE(){
		return ICE;
	}
	public int getEMP(){
		return EMP;
	}
	
	public int getCASH(){
		return CASH;
	}
	
	//all needed set constructors
	public void setNAME(String Val){
		 NAME=Val;
	}
	public void setXP(int Val){  //Level Calculating formula
	     XP=XP+Val;
	     
	    if(LVL<=10){				//low Level formula
	    	while(XP>=LVL*LVL*2){
	    		setLVL(1);
		    	OLDXP=XP;
	    	}
	    }
	    if(XP>=200){				//mid level formula
	    if(LVL>=11 && LVL<=50 ){	
	     while(XP>=OLDXP*1.1){
	    	 setLVL(1);
	    	 OLDXP=XP;
	     }
	    }
	    if(LVL>=51 && LVL<=100 ){ 	//high level formula
	    	while(XP>=OLDXP*1.05){
		    	 setLVL(1);
		    	 OLDXP=XP;
		     }
	     }
	    }
	 }
	 
	public void setLVL(int Val){		//per 1 level = 4 skillpoints 
		 LVL=LVL+Val;
		 setSKILLPTS(4);
	}
	
	public void setSKILLPTS(int Val){  //max=400
		SKILLPTS=SKILLPTS+Val;
	}
	
	public void setAGL(double Val){		//start=1 max=3.5 step=0.025  
		 AGL=AGL+Val;
	}
	
	public void setCRITPRB(double Val){ //max=0.2 step=0.002
		 CRITPRB=CRITPRB+Val;
	}
	
	public void setCRITDMG(double Val){	//max=5x step=0.05
		 CRITDMG=CRITDMG+Val;
	}
	 
	public void setLASER(int Val){	//Max=100
		 LASER=LASER+Val;
	}
	
	public void setACID(int Val){	//Max=100
		 ACID=ACID+Val;
	}
	
	public void setICE(int Val){	//Max=100
		 ICE=ICE+Val;
	}
	public void setEMP(int Val){	//Max=100
		 EMP=EMP+Val;
	}
	public void setCASH(int Val){	//Max= 1 000 000
		 CASH=CASH+Val;
	}
	
}
	
