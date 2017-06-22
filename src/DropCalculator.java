import java.util.Random;

// drop event call --> new DropCalculator(enemydropchance, activelvl, enemycash),

public class DropCalculator {
	
	private int boxrarity = 0;
	
	public static final int WEAPON = 1;
	public static final int SHIELD = 2;
	public static final int HEAL = 3;
	public static final int CONSUMESHIELD = 4;
	public static final int BOMB = 5;
	public static final int TOWNPORTAL = 6;
	
	public static final int NORMAL_RARITY = 1;
	public static final int ENCHANTED_RARITY = 2;
	public static final int GOLD_RARITY = 3;
	public static final int UNIQUE_RARITY = 4;
	
	
	DropCalculator(int thisdropchance, int thisactivelevel, int thiscash){
		
		int dropchance = thisdropchance;  
		int consumetype = 0;
		
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		int retval = Math.abs(rnd.nextInt())%100;			//main probability
									
				
			dropchance -= thisactivelevel;
//dropchance -= 0; // Hardcoremode = 5
				
				if (retval >= dropchance){					//drop cash only values > 7;
					retval -= dropchance;
					new Item("",0,0,thiscash);   
				}
				
				if (retval >= dropchance){					//drop consumables
					retval -= dropchance;
					consumetype = Math.abs(rnd.nextInt())%4;
					switch (consumetype){					
					case 0: new Item("",0,0,HEAL); 
							break;
					case 1: new Item("",0,0,CONSUMESHIELD); 
							break;
					case 2: new Item("",0,0,BOMB); 
							break;
					case 3:	new Item("",0,0,TOWNPORTAL); 
							break;					
					}
				
				}
				
				if (retval >= dropchance){
					
					int category = Math.abs(rnd.nextInt())%2+1;  //1 = weapon , 2 = shield
										
					while (retval >= 0){  						 //max boxrarity = 7
					retval -= dropchance;
					boxrarity++;					
					}					
					boxrarity--;	
	
				
				
					switch (boxrarity){
					case 1: 							
						new ItemCalculator(NORMAL_RARITY,category);	 		
				
// set box 1
							break;
					case 2: new ItemCalculator(ENCHANTED_RARITY, category);   
				
// set box 2
							break;
					case 3:	new ItemCalculator(GOLD_RARITY,category);    		
// set box 3
							break;	
					default: 	
						
					while(boxrarity > 0){
// set box 3						
							int additem	= Math.abs(rnd.nextInt())%4;
							category	= Math.abs(rnd.nextInt())%2+1;
							
						switch (additem){
							case 0: new ItemCalculator(NORMAL_RARITY, category); 
									boxrarity--;
								  	break;
							case 1: new ItemCalculator(ENCHANTED_RARITY, category); 
									boxrarity -= ENCHANTED_RARITY;
									break;
							case 2: new ItemCalculator(GOLD_RARITY, category); // 
									boxrarity -= GOLD_RARITY;
									break;
							case 3:	new ItemCalculator(UNIQUE_RARITY, category);  //
									boxrarity -= UNIQUE_RARITY;
									break;
							
					}
				}
				
					
			}
		}
				
	}	
				
}

