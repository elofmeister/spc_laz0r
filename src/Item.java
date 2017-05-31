
public class Item {
	
	private String ITEMNAME;
	private boolean ISCASH;
	private boolean CONSUMEABLE;
	private int ITEMVALUE;
	private int ITEMLVL;
	private int QUALITY;
	private int RARITY;
	private int CATEGORY;
	private int NUMBERBUFFS;	
	private int FIRETYPE;

	
	
	Item(String thisITEMNAME, boolean thisISCASH, boolean thisCONSUMEABLE, int thisITEMVALUE, int thisITEMLVL, int thisQUALITY, int thisRARITY, int thisCATEGORY, int thisNUMBERBUFFS, int thisFIRETYPE){
		
		ITEMNAME = thisITEMNAME;
		ISCASH = thisISCASH;
		CONSUMEABLE = thisCONSUMEABLE;
		ITEMVALUE = thisITEMVALUE;
		ITEMLVL = thisITEMLVL;
		QUALITY = thisQUALITY;
		RARITY = thisRARITY;
		CATEGORY = thisCATEGORY;
		NUMBERBUFFS = thisNUMBERBUFFS;	
		FIRETYPE = thisFIRETYPE;
	} 
	
	//all needed get constructors
	public String getITEMNAME(){
		return ITEMNAME;
	}	
	public boolean getISCASH(){
		return ISCASH;
	}
	public boolean getCONSUMEABLE(){
		return CONSUMEABLE;
	}
	
	public int getITEMVALUE(){
		return ITEMVALUE;
	}
	
	public int getITEMLVL(){
		return ITEMLVL;
	}
	
	public int getQUALITY(){
		return QUALITY;
	}
	
	public int getRARITY(){
		return RARITY;
	}
		 
	public int getCATEGORY(){
		return CATEGORY;
	}
	
	public int getNUMBERBUFFS(){
		return NUMBERBUFFS;
	}
	
	public int getFIRETYPE(){
		return FIRETYPE;
	}
	
	//all needed set constructors
	public void setITEMNAME(String iVal){
		ITEMNAME=iVal;
	}
	public void setISCASH(boolean iVal){
		ISCASH=iVal;
	}
	
	public void setCONSUMEABLE(boolean iVal){
		CONSUMEABLE=iVal;
	}
	
	public void setITEMVALUE(int iVal){
		ITEMVALUE=iVal;
	} 
	
	public void setITEMLVL(int iVal){
		ITEMLVL=iVal;
	}
	
	public void setAGL(int iVal){
		QUALITY=iVal;
	}
	
	public void setRARITY(int iVal){
		RARITY=iVal;
	}
	
	public void setCATEGORY(int iVal){
		CATEGORY=iVal;
	}
	 
	public void setNUMBERBUFFS(int iVal){
		NUMBERBUFFS=iVal;
	}
	
	public void setFIRETYPE(int iVal){
		FIRETYPE=iVal;
	}
		
}

