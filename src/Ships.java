import java.awt.image.BufferedImage;
import java.util.*;

public class Ships {

	private String shipname;
	private int shipclass;
	
	private int bonusslots[] = new int[4];
	private int life;
	private int maxlife;
	private int baselife;
	private int curse; //curse (1-4=Element)
	private int dmg;	
	private double firespeed;
	private List<Item> currentitems = new ArrayList<Item>();

	public static final int SPAWN_0 				= 0;
	public static final int SPAWN_1 				= 1;
	public static final int SPAWN_2 				= 2;
	public static final int SPAWN_3 				= 3;
	public static final int SPAWN_4 				= 4;
	public static final int MOVE_RIGHT 				= 5;
	public static final int MOVE_RIGHT_UP 			= 6;
	public static final int MOVE_RIGHT_DOWN 		= 7;
	public static final int MOVE_LEFT_DOWN 			= 8;
	public static final int MOVE_LEFT_UP 			= 9;
	public static final int MOVE_LEFT 				= 10;
	public static final int MOVE_RIGHT_SHIELD 		= 11;
	public static final int MOVE_RIGHT_UP_SHIELD 	= 12;
	public static final int MOVE_RIGHT_DOWN_SHIELD  = 13;
	public static final int MOVE_LEFT_DOWN_SHIELD  	= 14;
	public static final int MOVE_LEFT_UP_SHIELD  	= 15;
	public static final int MOVE_LEFT_SHIELD  		= 16;

	public static final int STANDARDO = 1;
	public static final int RUMPLER = 2;
	public static final int GLASSCANNON = 3;
	
	public static final int SHIPSPEED = 15; //in pixel per move
	public static final int CURSE_DURATION = 5000;		//duration in milliseconds
	public static final int SHIELD_DURATION = 5000; //consume shield last 5000 milliseconds
	
	private BufferedImage[] image = new BufferedImage[MOVE_LEFT_SHIELD + 1];		// all ship tiles
	private boolean spawned = false;
	private boolean isinvincible = false;
	private int spawnCnt = SPAWN_0;
	private int animation = MOVE_RIGHT;
	private int cursedspeed;
	private long cursetimestamp = System.currentTimeMillis();
	private long invincible_timestamp = System.currentTimeMillis();
	private long spawnTimer = System.currentTimeMillis();
	
	private Coordinates coor = new Coordinates(256, 256);
		
	public Ships(int shipclass) {
		setshipclass(shipclass);
	}  
		
	public void setshipclass(int shipclass) {
		this.shipclass = shipclass;
		switch (this.shipclass) {
		case GLASSCANNON: 
			setbaselife(60);
			setdmg(10);
			setfirespeed(1.00);
			shipname = "GLASSCANNON";
			break;
		case RUMPLER: 
			setbaselife(100);
			setdmg(6);
			setfirespeed(1.40);
			shipname = "Rumpler";
			break;
		case STANDARDO: 
			setbaselife(80);
			setdmg(8);
			setfirespeed(1.20);
			shipname = "Standardo";
			break;
		default: 
			break;
		}
		TileSet tileset = new TileSet("tiles/ships.png", 10, 10);
		for (int i = 0; i < image.length; i++) {
			if (i <= SPAWN_4) {
				image[i] = tileset.getTileset().getSubimage(i * 64, (this.shipclass-STANDARDO) * 3 * 64 + 64, 64, 64);
			} else if (i <= MOVE_LEFT) {
				image[i] = tileset.getTileset().getSubimage((i - MOVE_RIGHT) * 64, (this.shipclass-STANDARDO) * 3 * 64, 64, 64);				
			} else {
				image[i] = tileset.getTileset().getSubimage((i - MOVE_RIGHT_SHIELD) * 64, (this.shipclass-STANDARDO) * 3 * 64 + 128, 64, 64);
			}
		}
	}
	
	public void respawn() {
		coor.setX(256);
		coor.setY(256);
		spawned = false;
		spawnCnt = SPAWN_0;
		animation = MOVE_RIGHT;
		setlife(0);
		cursetimestamp = 1;
		invincible_timestamp = 1;	
	}
	
	public String getshipname(){
		return shipname;
	}
	
	public int getshipclass(){
		return shipclass;
	}
	
	public int getbonusslots(int i){
		return bonusslots[i];
	}
	
	public int getlife(){
		return life;
	}
	
	public int getmaxlife(){
		return maxlife;
	}
	
	public int getbaselife(){
		return baselife;
	} 
	 
	public int getcurse(){
		return curse;
	}
	
	public int getdmg(){
		return dmg;
	}
	
	public double getfirespeed(){
		return firespeed;
	}
	
	public int getshipspeed(){
		int retval = SHIPSPEED;
		if (cursetimestamp + CURSE_DURATION > System.currentTimeMillis()) {
			retval+=cursedspeed;
		} else {
			curse = 0;
		}
		return retval;
	}
	
	public boolean getinvincible(){
		if(invincible_timestamp + SHIELD_DURATION > System.currentTimeMillis()){
			isinvincible = true;
		}
		else {
			isinvincible = false;
		}
		return isinvincible;
	}
	
	//all needed set constructors
	public void setshipname(String sVal){
		shipname=sVal;
	}
		 
	public void setbonusslots(int i, int sVal){
		if (sVal<=4) {
			bonusslots[i]=sVal;
		}
	}
	 
	public void setlife(int sVal){
		if (sVal>=0) {
			life=maxlife;   //healing potion		
		} 
		else {
			if (getinvincible()==false){
				life+=sVal;     //damage
			}
		}
	}
	
	public void setmaxlife(int sVal){
			maxlife+=sVal;     //sval>0 shield activated	sval<0 shield deactivated
			if(life>=maxlife) setlife(1);
	}
	
	
	public void setbaselife(int sVal){
		baselife=sVal;  		//spawnlife				
		maxlife=baselife;
		setlife(baselife);
	}
	
	public void setinvincible() {
		invincible_timestamp = System.currentTimeMillis();
	}
	
	public void setdmg(int sVal){
		dmg=sVal;
	}
	
	public void setfirespeed(double sVal){
		firespeed=+sVal;
	}

	public void setshipspeed(int sVal){
		cursedspeed=sVal;
	}

	public boolean isSpawned() {
		return spawned;
	}


	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}
	
	public BufferedImage getImage() {
		BufferedImage retval = null;
		if (spawned) {
				 if (animation <= MOVE_LEFT) {
					retval = image[animation];
					if(invincible_timestamp + SHIELD_DURATION > System.currentTimeMillis()){
						retval = image[animation+6];
					}
			}
			
		}
		else {
			if (spawnTimer+300<System.currentTimeMillis()) {
				spawnTimer = System.currentTimeMillis();
				retval = image[spawnCnt++];
				if (spawnCnt > SPAWN_4) {
					spawned = true;
				}
			} else {
				retval = image[spawnCnt];
			}
		}
		return retval;
	}

	public Coordinates getCoordinates() {
		return coor;
	}

	public int getAnimation() {
		return animation;
	}
	
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	
	public void setcurse(int curse) {
		this.curse = curse;
	}
	
	public void setcursetimestamp() {
		cursetimestamp = System.currentTimeMillis();
	}
}
	
