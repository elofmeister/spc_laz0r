
public class Item {
	
// e.g.	Item x = new Item("C@$H", 0, 1, 0, 50, 0, 0, 0, 0, 0, 0, 0 ,0); called from dropcalculator
	
	private String itemname;
	private int iscash;
	private int isconsumeable;
	private int itemvalue;
	private int itemlvl;
	private int quality;
	private int rarity;
	private int category;
	private int numberbuffs;	
	private int firetype;

	/*
	 * some space for final modifiers
	 * 
	 * 
	 * 
	 */
	
	
	Item(String this.itemname, int this.iscash, int this.isconsumeable, int this.itemvalue, int this.itemlvl, int this.quality, int this.rarity, int this.category, int this.numberbuffs, int this.firetype){
		
		itemname  =  this.itemname;
		iscash  =  this.iscash;
		isconsumeable  =  this.isconsumeable;
		itemvalue  =  this.itemvalue;
		itemlvl  =  this.itemlvl;
		quality  =  this.quality;
		rarity  =  this.rarity;
		category  =  this.category;
		numberbuffs  =  this.numberbuffs;	
		firetype  =  this.firetype;
	} 
	
	//all needed get constructors
	public String getItemname(){
		return itemname;
	}	
	public int getIscash(){
		return iscash;
	}
	public int getIsconsumeable(){
		return isconsumeable;
	}
	
	public int getItemvalue(){
		return itemvalue;
	}
	
	public int getItemlvl(){
		return itemlvl;
	}
	
	public int getQuality(){
		return quality;
	}
	
	public int getRarity(){
		return rarity;
	}
		 
	public int getCategory(){
		return category;
	}
	
	public int getNumberbuffs(){
		return numberbuffs;
	}
	
	public int getFiretype(){
		return firetype;
	}
	
	//all needed set constructors
	public void setItemname(String iVal){
		itemname = iVal;
	}
	
	public void setIscash(int iVal){
		iscash = iVal;
//		if (iscash){
//			setItemvalue(getEnemcash);
//		}
//		
	}
	
	public void setIsconsumeable(int iVal){
		isconsumeable = iVal;
	}
	
	public void setItemvalue(int iVal){
		itemvalue = iVal;
	} 
	
	public void setItemlvl(int iVal){
		itemlvl = iVal;
	}
	
	
	public void setRarity(int iVal){
		rarity = iVal;
	}
	
	public void setCategory(int iVal){
		category = iVal; //3 = heal; 4=tp , 5=bomb, 6=consumeshield  1 = weapon 2=shield
	}
	 
	public void setNumberbuffs(int iVal){
		numberbuffs = iVal;
	}
	
	public void setFiretype(int iVal){
		firetype = iVal;
	}
		
}

