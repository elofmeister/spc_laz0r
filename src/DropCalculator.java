import java.util.Random;

// drop event call --> new DropCalculator(enemydropchance, activelvl, enemycash),

public class DropCalculator {
	private int boxrarity = 0;
	
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
					switch (consumetype){					//3=heal, 4=consumeshield, 5=bomb, 6=tp ,
					case 0: new Item("",0,0,3); 
							break;
					case 1: new Item("",0,0,4); 
							break;
					case 2: new Item("",0,0,5); 
							break;
					case 3:	new Item("",0,0,6); 
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
						new ItemCalculator(1,category);	 		// normal	
				
// set box 1
							break;
					case 2: new ItemCalculator(2, category);    // enchanted
				
// set box 2
							break;
					case 3:	new ItemCalculator(3,category);    	//gold		
// set box 3
							break;	
					default: 	
						
					while(boxrarity > 0){
// set box 3						
							int additem	= Math.abs(rnd.nextInt())%4;
							category	= Math.abs(rnd.nextInt())%2+1;
							
						switch (additem){
							case 0: new ItemCalculator(1, category); // 1 = normal 
									boxrarity--;
								  	break;
							case 1: new ItemCalculator(2, category); // 2 = enchanted
									boxrarity -= 2;
									break;
							case 2: new ItemCalculator(3, category); // 3 = gold
									boxrarity -= 3;
									break;
							case 3:	new ItemCalculator(4, category);  //4 = unique
									boxrarity -= 4;
									break;
							
					}
				}
				
					
			}
		}
				
	}	
//	public static void main(String[] arg) {
//	    new DropCalculator(15,5);	// calling run method 
//	}
				
}

