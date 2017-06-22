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
	private static Player spieler;
	private static Enemy gegner;
	private static Ships shiff;
	public DamageCalculator(int target){  //  1 = me, 2 = enemy
		spieler.getAcid();
		spieler.getEmp();
		spieler.getIce();
		spieler.getLaser();
		spieler.getCritdmg();
		spieler.getCritprb();
		gegner.getEnemydmg();
		gegner.getEnemylife();
		gegner.getEnemyelement();
		shiff.getdmg();
		shiff.getlife();

		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		elementchance = Math.abs(rnd.nextInt())%100;	
	
		switch (target){
		case PLAYER_IS_TARGET:
			shiff.setlife(-gegner.getEnemydmg()); // bullet damage
			
	    if(shiff.getcurse() == 0){
			switch (gegner.getEnemyelement()){
			case LASER: 
						if(elementchance>spieler.getLaser()){
							shiff.setcurse(LASER);
							shiff.setlife(LASER_DMG);
							shiff.setshipspeed(LASER_SLOW);
							long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
								while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
								
									long newtimestamp = System.currentTimeMillis()/1000; 
								
										if (newtimestamp != oldtimestamp) {
											shiff.setlife(LASER_DMG);												
											oldtimestamp = newtimestamp;
										    }						
								}														
						}
						break;
						
			case ICE: 
					if(elementchance>spieler.getLaser()){
						shiff.setcurse(ICE);
						shiff.setlife(ICE_DMG);
						shiff.setshipspeed(ICE_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shiff.setlife(ICE_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}					
					}
					break;
				
			case ACID: 
					if(elementchance>spieler.getLaser()){
						shiff.setcurse(ACID);
						shiff.setlife(ACID_DMG);
						shiff.setshipspeed(ACID_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shiff.setlife(ACID_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}			
					}
			       	break;
				
			case EMP:
					if(elementchance>spieler.getLaser()){
							
						shiff.setcurse(EMP);
						shiff.setlife(EMP_DMG);
						shiff.setshipspeed(EMP_SLOW);
						long timestamp = System.currentTimeMillis()/1000, oldtimestamp = System.currentTimeMillis()/1000;							
							while (timestamp+CURSE_DURATION>System.currentTimeMillis()/1000){
							
								long newtimestamp = System.currentTimeMillis()/1000; 
							
									if (newtimestamp != oldtimestamp) {
										shiff.setlife(EMP_DMG); 		
										oldtimestamp = newtimestamp;
									    }						
							}							
					}
					
			       	break;
				}
		    }	
	    	shiff.setcurse(0);
	    	shiff.setshipspeed(0);
			break;

			
		case ENEMY_IS_TARGET:
			gegner.setEnemylife(-shiff.getdmg());
			
		
			crtitchance = Math.abs(rnd.nextDouble());
			if (crtitchance>=spieler.getCritprb()){
				damage = (int) Math.round(shiff.getdmg()*spieler.getCritdmg());
				gegner.setEnemylife(-damage);
			}
			System.out.println("critpr"+ crtitchance);
	//		gegner.setEnemylife(shiff.getdmg()+spieler.getLaser()+spieler.getIce()+spieler.getAcid()+spieler.getEmp());
			
			switch(gegner.getEnemyelement()){
			case LASER: //gegner.setEnemylife(shiff.getdmg()+spieler.getLaser()+spieler.getIce()+spieler.getAcid()+spieler.getEmp())
						break;
			case ICE: break;
			case ACID: break;
			case EMP: break;
			}
			break;
		}
		
	
	
	}
	public static void main(String[] arg){
		spieler = new Player("Horst");
		gegner = new Enemy(1,2); 
		shiff = new Ships(3);
		

	int i = gegner.getEnemylife();
	System.out.println("Leben vorher"+ i);
	gegner.setEnemyelement();
	System.out.println("course"+ shiff.getcurse());
	new DamageCalculator(2);	// calling run method 
	i = gegner.getEnemydmg();
	System.out.println("gegnerDmg: "+ i);
	i = gegner.getEnemyelement();
	System.out.println("Element:"+ i);



	i = gegner.getEnemylife();
	System.out.println("getEnemylife nacher"+ i);
	i = shiff.getlife();
	System.out.println("Life Nachher"+ i);
	
}

	
}
