import java.awt.image.BufferedImage;
import java.util.*;

public class Ships {

	private String shipname;
	private int shipclass;
	
	private int bonusslots[] = {1, 1, 1, 1};
	private int life;
	private int maxlife;
	private int baselife;
	private int curse; //curse (1-4=Element)
	private int dmg;	
	private double firespeed;
	private List<Item> currentitems = new ArrayList<Item>();
	
	private static final int ITEM_MAX = 8;

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
	public static final int LVLUP_0 				= 17;
	public static final int LVLUP_1 				= 18;
	public static final int LVLUP_2 				= 19;
	public static final int LVLUP_3 				= 20;
	public static final int LVLUP_4 				= 21;
	public static final int LVLUP_5 				= 22;
	public static final int LVLUP_6 				= 23;
	public static final int LVLUP_7 				= 24;

	public static final int STANDARDO = 1;
	public static final int RUMPLER = 2;
	public static final int GLASSCANNON = 3;
	
	public static final int SHIPSPEED = 15; //in pixel per move
	public static final int CURSE_DURATION = 5000;		//duration in milliseconds
	public static final int SHIELD_DURATION = 10000; //consume shield last 20000 milliseconds
	
	public static final int GOOD_DAMAGE = 11;
	public static final int BIG_DAMAGE = 50;
	public static final int HUGE_DAMAGE = 100;
	
	private BufferedImage[] image = new BufferedImage[LVLUP_7 + 1];		// all ship tiles
	private boolean spawned = false;
	private boolean lvlUp = true;
	private boolean isinvincible = false;
	private int spawnCnt = SPAWN_0;
	private int lvlUpCnt = LVLUP_0;
	private int animation = MOVE_RIGHT;
	private int cursedspeed;
	private long cursetimestamp = System.currentTimeMillis();
	private long invincible_timestamp = System.currentTimeMillis();
	private long spawnTimer = System.currentTimeMillis();
	private long lvlUpTimer = System.currentTimeMillis();
	
	private Coordinates coor = new Coordinates(256, 256);
	private Crit crit = new Crit(coor);
		
	public Ships(int shipclass) {
		crit.disableCrit();
		setshipclass(shipclass);
	}  
	
	public void load(int shipclass, int healthpotions, int shields, int bombs, int portals) {
		setshipclass(shipclass);
		bonusslots[0] = healthpotions;
		bonusslots[1] = shields;
		bonusslots[2] = bombs;
		bonusslots[3] = portals;
	}
	
	public void setshipclass(int shipclass) {
		this.shipclass = shipclass;
		switch (this.shipclass) {
		case GLASSCANNON: 
			setbaselife(60);
			setdmg(10);
			setfirespeed(1.00);
			curse = 0;
			shipname = "GLASSCANNON";
			break;
		case RUMPLER: 
			setbaselife(100);
			setdmg(6);
			setfirespeed(1.40);
			curse = 0;
			shipname = "Rumpler";
			break;
		case STANDARDO: 
			setbaselife(80);
			setdmg(8);
			setfirespeed(1.20);
			curse = 0;
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
			} else if (i <= MOVE_LEFT_SHIELD) {
				image[i] = tileset.getTileset().getSubimage((i - MOVE_RIGHT_SHIELD) * 64, (this.shipclass-STANDARDO) * 3 * 64 + 128, 64, 64);
			} else if (i <= LVLUP_3) {
				image[i] = tileset.getTileset().getSubimage((i - LVLUP_0 + 6) * 64, (this.shipclass-STANDARDO) * 3 * 64, 64, 64);
			}  else {
				image[i] = tileset.getTileset().getSubimage((i - LVLUP_4 + 6) * 64, (this.shipclass-STANDARDO) * 3 * 64 + 64, 64, 64);
			}
		}
		bonusslots[0] = 1;
		bonusslots[1] = 1;
		bonusslots[2] = 1;
		bonusslots[3] = 1;
	}
	
	public void respawn() {
		coor.setX(256);
		coor.setY(256);
		spawned = false;
		spawnCnt = SPAWN_0;
		lvlUp = true;
		lvlUpCnt = LVLUP_0;
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
	
	public int getbulletstyle(){
	int retval = 0;
		if (getdmg()>=GOOD_DAMAGE){
			retval += 1;
			if (getdmg()>=BIG_DAMAGE){
				retval += 1;
					if (getdmg()>=HUGE_DAMAGE){
					retval += 1;
				}	
			}		
		}		
		return retval;
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
		if (spawned && lvlUp) {
				 if (animation <= MOVE_LEFT) {
					retval = image[animation];
					if(invincible_timestamp + SHIELD_DURATION > System.currentTimeMillis()){
						retval = image[animation+6];
					}
			}
			
		} else if (spawned) {
			if (lvlUpTimer + 300 < System.currentTimeMillis()) {
				lvlUpTimer = System.currentTimeMillis();
				retval = image[lvlUpCnt++];
				if (lvlUpCnt > LVLUP_7) {
					lvlUp = true;
				}
			} else {
				retval = image[lvlUpCnt];
			}
		} else {
			if (spawnTimer + 300 < System.currentTimeMillis()) {
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
	
	public boolean isItemSpace() {
		boolean bretval = false;
		if (currentitems.size() < ITEM_MAX) {
			bretval = true;
		}
		return bretval;
	}
	
	public void addItem(Item item) {
		if (item.getCategory() == 1) {
			if (isItemSpace()) {
				currentitems.add(item);
				dmg += item.getItemstat();
			}
		}
		if (item.getCategory() == 2) {
			if (isItemSpace()) {
				currentitems.add(item);
				maxlife += item.getItemstat();
			}
		}
		if (item.getCategory() >=3 && item.getCategory() <= 6) {
			setbonusslots(item.getCategory()-3, getbonusslots(item.getCategory()-3)+1);
		}
	}
	
	public void prooveItemTimer() {
		if (!currentitems.isEmpty()) {
			for (int i = 0; i < currentitems.size(); i++) {
				if (currentitems.get(i).getItemtimer() + currentitems.get(i).getTimestamp() < System.currentTimeMillis()) {
					if (currentitems.get(i).getCategory() == 1) {
						dmg -= currentitems.get(i).getItemstat();
					}
					if (currentitems.get(i).getCategory() == 2) {
						maxlife -= currentitems.get(i).getItemstat();
					}
					currentitems.remove(i);
				}
			}
		}
	}
	
	public void toggleLvlUpAnimation() {
		lvlUp = false;
		lvlUpCnt = LVLUP_0;
	}
	
	public void toggleCritAnimation(Coordinates coordinates) {
		crit = new Crit(coordinates);
	}
	
	public Crit getCrit() {
		return crit;
	}
}
	
