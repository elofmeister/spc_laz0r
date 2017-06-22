import java.util.Random;

public class DamageCalculator {
	
	public static final int PLAYER_IS_TARGET = 1;
	public static final int ENEMY_IS_TARGET = 2;
	
	public static final int CURSE_DURATION = 5;		//duration in miliseconds
	
	public static final int LASER = 1;  			//damage type
	public static final int ICE = 2;
	public static final int ACID = 3;
	public static final int EMP = 4;
	
	public static final int LASER_DMG = -3;  		//damage per second
	public static final int ICE_DMG = -2;
	public static final int ACID_DMG = -1;
	public static final int EMP_DMG = 0;
	
	public static final int LASER_SLOW = 0;			//shipspeed reduction (pixel per move)	
	public static final int ICE_SLOW = -4;
	public static final int ACID_SLOW = -6;
	public static final int EMP_SLOW = -8;
	
	private int damage;
	private int elementchance;
	private double crtitchance;
	private static Player player;
	private static Enemy gegner;
	private static Ships shp;
	public DamageCalculator(int target){  
	
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		elementchance = Math.abs(rnd.nextInt())%100;	
	
		switch (target){
		case PLAYER_IS_TARGET:
			shp.setlife(-gegner.getEnemydmg()); // bullet damage
			
	    if(shp.getcurse() == 0){
			switch (gegner.getEnemyelement()){
			case LASER: 
						if(elementchance>player.getLaser()){
							shp.setcurse(LASER);
							shp.setlife(LASER_DMG);
							shp.setshipspeed(LASER_SLOW);
							long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
								while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
								
									long newtimestamp = System.currentTimeMillis()/1000; 
								
										if (newtimestamp != oldtimestamp) {
											shp.setlife(LASER_DMG);												
											oldtimestamp = newtimestamp;
										    }						
								}														
						}
						break;
						
			case ICE: 
					if(elementchance>player.getLaser()){
						shp.setshipspeed(ICE);
						shp.setlife(ICE_DMG);
						shp.setshipspeed(ICE_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shp.setlife(ICE_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}					
					}
					break;
				
			case ACID: 
					if(elementchance>player.getLaser()){
						shp.setcurse(ACID);
						shp.setlife(ACID_DMG);
						shp.setshipspeed(ACID_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shp.setlife(ACID_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}			
					}
			       	break;
				
			case EMP:
					if(elementchance>player.getLaser()){
							
						shp.setcurse(EMP);
						shp.setlife(EMP_DMG);
						shp.setshipspeed(EMP_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shp.setlife(EMP_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}							
					}
					
			       	break;
				}
		    }	
	    	shp.setcurse(0);
	    	shp.setshipspeed(0);
			break;

			
		case ENEMY_IS_TARGET:
			gegner.setEnemylife(-shp.getdmg());
			
		
			crtitchance = Math.abs(rnd.nextDouble());
			if (crtitchance<=player.getCritprb()){
				damage = (int) Math.round(shp.getdmg()*player.getCritdmg());
				System.out.println("critchan"+ damage);
				gegner.setEnemylife(-damage);
			}
			 //bonusbullet still missing <-- <-- <-- <-- <-- <-- <-- <-- <-- <-- <-- <-- <-- <-- 
	//		gegner.setEnemylife(shp.getdmg()+player.getLaser()+player.getIce()+player.getAcid()+player.getEmp());
			
			switch(gegner.getEnemyelement()){
			case LASER: gegner.setEnemylife(-(player.getIce()+player.getAcid()+player.getEmp()));
						break;
			case ICE: gegner.setEnemylife(-(player.getLaser()+player.getAcid()+player.getEmp()));
						break;
			case ACID: gegner.setEnemylife(-(player.getLaser()+player.getIce()+player.getEmp())); 
						break;
			case EMP: gegner.setEnemylife(-(player.getLaser()+player.getIce()+player.getAcid()));
						break;
			}
			break;
		}
		
	
	
	}
//	public static void main(String[] arg){
//		Level lvl = new Level(5, new TileSet("tiles/tileset1.png",10,10));
//		player = new Player("Horst");
//		gegner = new Enemy(2,lvl); 
//		shp = new Ships(3);
//		gegner.setEnemylife(20);	
//
//}

	
}
