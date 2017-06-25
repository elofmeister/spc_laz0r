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
	private Chest chest;
	
	
	DropCalculator(Chest chest, int thisdropchance, int thisactivelevel, int thiscash){
		this.chest = chest;
		int dropchance = thisdropchance;  
		int consumetype = 0;
		
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		int retval = Math.abs(rnd.nextInt())%100;			//main probability
									
				
			dropchance -= thisactivelevel;
//dropchance -= 0; // Hardcoremode = 5
				
				if (retval >= dropchance){					//drop cash only values > 7;
					retval -= dropchance;
					chest.addItemToChest(new Item("",0,0,thiscash));   
				}
				
				if (retval >= dropchance){					//drop consumables
					retval -= dropchance;
					consumetype = Math.abs(rnd.nextInt())%4;
					switch (consumetype){					
					case 0: 
						chest.addItemToChest(new Item("",0,0,HEAL)); 
						break;
					case 1: 
						chest.addItemToChest(new Item("",0,0,CONSUMESHIELD)); 
						break;
					case 2: 
						chest.addItemToChest(new Item("",0,0,BOMB)); 
						break;
					case 3:	
						chest.addItemToChest(new Item("",0,0,TOWNPORTAL)); 
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
						chest.addItemToChest(new ItemCalculator(NORMAL_RARITY,category).getItem());			
				
// set box 1
							break;
					case 2: chest.addItemToChest(new ItemCalculator(ENCHANTED_RARITY, category).getItem());   
				
// set box 2
							break;
					case 3:	chest.addItemToChest(new ItemCalculator(GOLD_RARITY,category).getItem());    		
// set box 3
							break;	
					default: 	
						
					while(boxrarity > 0){
// set box 3						
							int additem	= Math.abs(rnd.nextInt())%4;
							category	= Math.abs(rnd.nextInt())%2+1;
							
						switch (additem){
							case 0: chest.addItemToChest(new ItemCalculator(NORMAL_RARITY, category).getItem()); 
									boxrarity--;
									chest.setRarity(1);
								  	break;
							case 1: chest.addItemToChest(new ItemCalculator(ENCHANTED_RARITY, category).getItem()); 
									boxrarity -= ENCHANTED_RARITY;
									chest.setRarity(2);
									break;
							case 2: chest.addItemToChest(new ItemCalculator(GOLD_RARITY, category).getItem()); // 
									boxrarity -= GOLD_RARITY;
									chest.setRarity(3);
									break;
							case 3:	chest.addItemToChest(new ItemCalculator(UNIQUE_RARITY, category).getItem());  //
									boxrarity -= UNIQUE_RARITY;
									chest.setRarity(3);
									break;
							
					}
				}	
			}
		}		
	}
	public Chest getChest() {
		return chest;
	}
}

