import java.util.Random;

public class DropCalculator {
	private int boxrarity = 0;
	
	
	DropCalculator(){
		int dropchance = getDropchance();
		int consumetype = 0;
		
			
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		retval = Math.abs(rnd.nextInt())%100;	//main probability
		Random rnd = new Random();									
				System.out.println("" + retval);
				
			dropchance -= getActiveLvl();
			dropchance -= 0; // Hardcoremode = 5
				
				if (retval >= dropchance){
					System.out.println("drop cash:" + retval);
					retval -= dropchance;
					//Item x = new Item("C@$H", 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); 
					//setisCash(1);
					//setItemvalue(getEnemycash());
				}
				
				if (retval >= dropchance){
					System.out.println("drop consumable:" + retval);
					retval -= dropchance;
					consumetype = Math.abs(rnd.nextInt())%4;
					switch (consumetype){
					case 0: new Item("Healing Potion", 0, 1, 0, 0, 0, 0, 0, 3, 0); 
							break;
					case 1: new Item("Portal", 0, 1, 0, 0, 0, 0, 0, 4, 0); 
							break;
					case 2: new Item("Inincible Shield", 0, 1, 0, 0, 0, 0, 0, 5, 0 ); 
							break;
					case 3:	new Item("Bomb", 0, 1, 0, 0, 0, 0, 0, 6, 0); 
							break;					
					}
				
				}
				
				if (retval >= dropchance){
					
					int category	= Math.abs(rnd.nextInt())%2+1;
					
					while (retval >= 0){
					retval -= dropchance;
					boxrarity++;					
					}
					
					boxrarity--;	
					
				System.out.println("drop item rarity:" + boxrarity);	//max boxrarity = 7
				
					switch (boxrarity){
					case 1: new Item("NAME", 0, 0, 1, 0, 0, 1, category, 0, 0);	// normal	
					System.out.println("yougot" + normal);
							break;
					case 2: new Item("NAME", 0, 0, 1, 0, 0, 2, category, 0, 0);    // gold
					System.out.println("yougot" + gold);
							break;
					case 3:	new Item("NAME", 0, 0, 1, 0, 0, 3, category, 0, 0);    //unique
					System.out.println("yougot" + unique);
							break;	
					default: 	
						
					while(boxrarity > 0){
								
							int additem	= Math.abs(rnd.nextInt())%4;
							category	= Math.abs(rnd.nextInt())%2+1;
							
						switch (additem){
							case 0: new Item("NAME", 0, 0, 1, 0, 0, 1, category, 0, 0); // normal
							boxrarity--;
							System.out.println("yougot" + normal);
							break;
							case 1: new Item("NAME", 0, 0, 1, 0, 0, 2, category, 1, 0); // gold
							boxrarity -= 2;
							System.out.println("yougot" + gold);
							break;
							case 2: new Item("NAME", 0, 0, 1, 0, 0, 3, category, 2, 0); //unique
							boxrarity -= 3;
							System.out.println("yougot" + unique);
							break;
							case 3:	new Item("NAME", 0, 0, 1, 0, 0, 4, category, 3, 0); //set item ????
							boxrarity -= 4;
							System.out.println("yougot" + setitem);
					}
				}
				
					
			}
		}
				
				
				
	}
	
}
