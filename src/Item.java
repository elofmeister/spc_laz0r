
//e.g new Item("GUN", 20, 10, 2);

public class Item {
	
	private String itemname;
	private boolean iscash;
	private boolean consumeable;
	private int itemvalue;
	private int itemtimer;
	private int itemstat;
	private int category;
	private long timestamp = System.currentTimeMillis();


	
	
	Item(String itemname, int itemtimer, int itemstat, int category){
		

		this.category = category;
		switch(category){
		case 1: setItemname(itemname);
				setItemvalue(0);	
				setItemtimer(itemtimer);
				setItemstat(itemstat);
				break;
		case 2: setItemname(itemname);
				setItemvalue(0);	
				setItemtimer(itemtimer);
				setItemstat(itemstat);
				break;
		case 3: setItemname("Healthpotion");
				setItemvalue(100);	
				setItemtimer(0);
				break;
		case 4: setItemname("Invincible Shield");
				setItemvalue(100);
				setItemtimer(5);
				break;
		case 5: setItemname("Bomb");
				setItemvalue(100);
				setItemtimer(0);
				break;
		case 6: setItemname("Townportal");
				setItemvalue(100);
				setItemtimer(0);
				break;			
		default:setItemname("C@SH");
				setItemvalue(category);
				setItemtimer(0);
				break;
		}
	} 
	
	//all needed get constructors
	public String getItemname(){
		return itemname;
	}	
	public boolean getIscash(){
		return iscash;
	}
	public boolean getConsumeable(){
		return consumeable;
	}
	
	public int getItemvalue(){
		return itemvalue;
	}
	
	public int getItemtimer(){
		return itemtimer;
	}
	
	public int getItemstat(){
		return itemstat;
	}
		 
	public int getCategory(){
		return category;
	}
	
	public boolean isDead() {
		boolean bretval = false;
		if(itemtimer == 0) {
			bretval = true;
		}
		return bretval;
	}
	
	
	//all needed set constructors
	public void setItemname(String iVal){
		itemname=iVal;
	}
	public void setIscash(boolean iVal){
		iscash=iVal;
	}
	
	public void setConsumeable(boolean iVal){
		consumeable=iVal;
	}
	
	public void setItemvalue(int iVal){
		itemvalue=iVal;
	} 
	
	public void setItemtimer(int iVal){
		itemtimer=iVal;
	}	
	
	public void setItemstat(int iVal){
		itemstat=iVal;
	}	
	
	public void setCategory(int iVal){
		category=iVal;
	}	
	
	public long getTimestamp() {
		return timestamp;
	}
		
}

