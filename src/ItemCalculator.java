import java.util.Random;

public class ItemCalculator {

	String qualitynames[]={"broken ", "damaged ", "normal ", "good ", "perfect "};
	String raritynames[]={"Alien-", "Human-", "space ", "tuned ", "overclocked ","enchanted ", "pimped ", "rare ", "ultra " , "mega ", "unique "};
	String weaponnames[]={"Blaster", "Dizzler", "Gun", "Biggun", "Photonbouncer", "Plasmagun", "Damager", "Projectileemitter", "LAZOR", "Phaser"};
	String shieldnames[]={"Protector", "Quantumshield", "Forcefield", "Shizzler", "Bigshield"};	
	
	public static final int ITEMTIMER = 20000;	// in ms
	public static final int ITEMSTATS = 10;

	private String name = "";
	private int timer = 0;
	private int stat = 0;
	private int itemtype;
	
	ItemCalculator(int rarity, int itemtype){
					
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());	
		
		int qualitycase = Math.abs(rnd.nextInt());
		this.itemtype = itemtype;
		
		
		switch(qualitycase){  						// = duration, sets timer
		case 0: name = name.concat(qualitynames[0]);
				timer = ITEMTIMER;
				break;			
		case 1: name = name.concat(qualitynames[1]); 
				timer = 2*ITEMTIMER;
				break;
		case 2: name = name.concat(qualitynames[2]);
				timer = 3*ITEMTIMER;
				break;
		case 3: name = name.concat(qualitynames[3]);
				timer = 4*ITEMTIMER;
				break;
		case 4: name = name.concat(qualitynames[4]);
				timer = 5*ITEMTIMER;
				break;
		default: break;
		}
		
		int rand =  Math.abs(rnd.nextInt()%3);
		rand = Math.abs(rnd.nextInt())%3;		

		switch(rarity){  							// = damage or life, sets stats
		case 1: name = name.concat(raritynames[rand]);
				stat = ITEMSTATS;
				break;			
		case 2: name = name.concat(raritynames[rand+3]);
				stat = 2*ITEMSTATS;
				break;
		case 3: name = name.concat(raritynames[rand+6]);
				stat = 3*ITEMSTATS;
				break;
		case 4: name = name.concat(raritynames[10]); 
				stat = 4*ITEMSTATS;
				break;	
		default: break;
		}
		
		switch(itemtype){
		case 1: rand = Math.abs(rnd.nextInt())%10;
				name = name.concat(weaponnames[rand]); 
				break;
				
		case 2: rand = Math.abs(rnd.nextInt())%5;
				name = name.concat(shieldnames[rand]);			
				break;
		default: break;
		}	
	}
	
	public Item getItem() {
		return new Item(name, timer, stat, itemtype);
	}

}