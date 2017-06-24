import java.util.Random;

public class DamageCalculator {
	
	public static final int PLAYER_IS_TARGET = 1;
	public static final int ENEMY_IS_TARGET = 2;
	
	
	public static final int LASER = 1;  			//damage type
	public static final int ICE = 2;
	public static final int ACID = 3;
	public static final int EMP = 4;
	public static final int BOMB = 5;
	
	public static final int LASER_DMG = -3;  		//damage per second
	public static final int ICE_DMG = -2;
	public static final int ACID_DMG = -1;
	public static final int EMP_DMG = 0;
	
	public static final int BOMB_DMG = -100;
	
	public static final int LASER_SLOW = 0;			//shipspeed reduction (pixel per move)	
	public static final int ICE_SLOW = -4;
	public static final int ACID_SLOW = -6;
	public static final int EMP_SLOW = -8;
	
	private int damage;
	private int elementchance;
	private double crtitchance;
	
	public DamageCalculator(long id, Player player, Enemy enemy, Ships shp){  
		
		int target = ENEMY_IS_TARGET;
		if (id == Player.ID) {
			target = PLAYER_IS_TARGET;
		}
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		elementchance = Math.abs(rnd.nextInt())%100;	
	
		switch (target){
		case PLAYER_IS_TARGET:
			shp.setlife(-enemy.getEnemydmg()); // bullet damage
			
	    if(shp.getcurse() == 0){
			switch (enemy.getEnemyelement()){
			case LASER: 
						if(elementchance>player.getLaser()){
							shp.setcurse(LASER);
							shp.setlife(LASER_DMG);
							shp.setshipspeed(LASER_SLOW);												
						}
						break;
						
			case ICE: 
					if(elementchance>player.getLaser()){
						shp.setshipspeed(ICE);
						shp.setlife(ICE_DMG);
						shp.setshipspeed(ICE_SLOW);	
					}
					break;
				
			case ACID: 
					if(elementchance>player.getLaser()){
						shp.setcurse(ACID);
						shp.setlife(ACID_DMG);
						shp.setshipspeed(ACID_SLOW);
					}
			       	break;
				
			case EMP:
					if(elementchance>player.getLaser()){
							
						shp.setcurse(EMP);
						shp.setlife(EMP_DMG);
						shp.setshipspeed(EMP_SLOW);						
					}
					
			       	break;
				}
		    }
	    	shp.setcursetimestamp();
			break;

			
		case ENEMY_IS_TARGET:
			enemy.setEnemylife(-shp.getdmg());
			
		
			crtitchance = Math.abs(rnd.nextDouble());
			if (crtitchance<=player.getCritprb()){
				damage = (int) Math.round(shp.getdmg()*player.getCritdmg());
				enemy.setEnemylife(-damage);
			}	
			
			switch(enemy.getEnemyelement()){
			case LASER: enemy.setEnemylife(-(player.getIce()+player.getAcid()+player.getEmp()));
						if(elementchance<player.getIce()){
							enemy.setEnemycursespeed(ICE_SLOW);												
						}
						else if(elementchance<player.getAcid()){
							enemy.setEnemycursespeed(ACID_SLOW);	
						}
						else if(elementchance<player.getEmp()){
							enemy.setEnemycursespeed(EMP_SLOW);	
						}									
						break;
						
			case ICE:   enemy.setEnemylife(-(player.getLaser()+player.getAcid()+player.getEmp()));
						if(elementchance<player.getLaser()){
							enemy.setEnemycursespeed(LASER_SLOW);												
						}
						else if(elementchance<player.getAcid()){
							enemy.setEnemycursespeed(ACID_SLOW);	
						}
						else if(elementchance<player.getEmp()){
							enemy.setEnemycursespeed(EMP_SLOW);	
						}									
						break;
						
			case ACID:  enemy.setEnemylife(-(player.getLaser()+player.getIce()+player.getEmp())); 
						if(elementchance<player.getIce()){
							enemy.setEnemycursespeed(ICE_SLOW);												
						}
						else if(elementchance<player.getLaser()){
							enemy.setEnemycursespeed(LASER_SLOW);	
						}
						else if(elementchance<player.getEmp()){
							enemy.setEnemycursespeed(EMP_SLOW);	
						}									
						break;
						
			case EMP:  enemy.setEnemylife(-(player.getLaser()+player.getIce()+player.getAcid()));
						if(elementchance>player.getIce()){
							enemy.setEnemycursespeed(ICE_SLOW);												
						}
						else if(elementchance>player.getAcid()){
							enemy.setEnemycursespeed(ACID_SLOW);	
						}
						else if(elementchance>player.getLaser()){
							enemy.setEnemycursespeed(LASER_SLOW);	
						}									
						break;
				}
			enemy.setEnemycurse();
			break;
			
		case BOMB:
			enemy.setEnemylife(player.getLvl()*BOMB_DMG);
		}
	}	
}
