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
	
	
	DropCalculator(Chest chest, int dropchance, int activelevel, int cash){
		System.out.println("Create new Chest:");
		this.chest = chest;
		int consumetype = 0;
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		int droppoints = Math.abs(rnd.nextInt()) % 100;			//main probability
									
		dropchance -= activelevel;
				
		/*
		 * DROP CASH ONLY (values > 7)
		 */
		if (droppoints >= dropchance) {
			droppoints -= dropchance;
			chest.addItemToChest(new Item("", 0, 0, cash));   
		}
		
		/*
		 * DROP CONSUMABLES
		 */
		if (droppoints >= dropchance) {
			droppoints -= dropchance;
			consumetype = Math.abs(rnd.nextInt()) % 4;
			switch (consumetype){					
			case 0: 
				chest.addItemToChest(new Item("", 0, 0, HEAL)); 
				break;
			case 1: 
				chest.addItemToChest(new Item("", 0, 0, CONSUMESHIELD)); 
				break;
			case 2: 
				chest.addItemToChest(new Item("", 0, 0, BOMB)); 
				break;
			case 3:	
				chest.addItemToChest(new Item("", 0, 0, TOWNPORTAL)); 
				break;					
			}
		
		}
		
		System.out.println("droppoints: " + droppoints);
		System.out.println("dropchance: " + dropchance);
		if (droppoints >= dropchance) {
			int category = Math.abs(rnd.nextInt()) % 2 + 1;  //1 = weapon , 2 = shield	
			while (droppoints >= 0) {  						 //max boxrarity = 7
				droppoints -= dropchance;
				boxrarity++;					
			}					
			boxrarity--;
				
			while(boxrarity > 0) {					
				int additem	= Math.abs(rnd.nextInt()) % 4;
				category	= Math.abs(rnd.nextInt()) % 2 + 1;
				switch (additem) {
					case 0: 
						chest.addItemToChest(new ItemCalculator(NORMAL_RARITY, category).getItem()); 
						boxrarity--;
						break;
					case 1:
						chest.addItemToChest(new ItemCalculator(ENCHANTED_RARITY, category).getItem()); 
						boxrarity -= ENCHANTED_RARITY;
						if (chest.getRarity() < 2) {
							chest.setRarity(2);
						}
						break;
					case 2: 
						chest.addItemToChest(new ItemCalculator(GOLD_RARITY, category).getItem()); // 
						boxrarity -= GOLD_RARITY;
						if (chest.getRarity() < 3) {
							chest.setRarity(3);
						}
						break;
						
					case 3:
						chest.addItemToChest(new ItemCalculator(UNIQUE_RARITY, category).getItem());  //
						boxrarity -= UNIQUE_RARITY;
						if (chest.getRarity() < 3) {
							chest.setRarity(3);
						}
						break;
						
					default:
						break;
				}
			}	
		}
		System.out.println("rarity: " + chest.getRarity());
	}		
	
	public Chest getChest() {
		return chest;
	}
}

