import java.awt.image.BufferedImage;

//z.B. Ships shp = new Ships(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); aufruf aus main mit übergabewerten

public class Ships{

	private String shipname;
	private int shipclass;
	private int shieldslots[];
	private int gunslots[];
	private int inventory[];
	private int bonusslots[];
	private int life;
	private int dmg;	
	private double firespeed;

	public static final int SPAWN_0 = 0;
	public static final int SPAWN_1 = 1;
	public static final int SPAWN_2 = 2;
	public static final int SPAWN_3 = 3;
	public static final int MOVE_RIGHT = 4;
	public static final int MOVE_RIGHT_UP = 5;
	public static final int MOVE_RIGHT_DOWN = 6;
	public static final int MOVE_LEFT_DOWN = 7;
	public static final int MOVE_LEFT_UP = 8;
	public static final int MOVE_LEFT = 9;

	public static final int GLASSKANONE = 1;
	public static final int RUMPLER = 2;
	public static final int STANDARDO = 3;
	
	private BufferedImage[] image = new BufferedImage[10];		// all ship tiles
	private boolean spawned = false;
	private int spawnCnt = 0;
	private int animation = SPAWN_0;
	
	private Coordinates coor = new Coordinates(256, 256);
		
	public Ships(int shipclass,int shieldslots, int gunslots, int inventory, int bonusslots, int life, int dmg, double firespeed) {
		this.shipclass = shipclass;
		switch (this.shipclass) {
		case GLASSKANONE: 
			shipname = "Glasskanone";
			break;
		case RUMPLER: 
			shipname = "Rumpler";
			break;
		case STANDARDO: 
			shipname = "Standardo";
			break;
		default: 
			break;
		}
		this.shieldslots = new int[shieldslots];
		this.gunslots = new int[gunslots];
		this.inventory = new int[inventory];
		this.bonusslots = new int[bonusslots];
		this.life = life;
		this.dmg = dmg;	
		this.firespeed= firespeed;
		TileSet tileset = new TileSet("tiles/ships.png", 10, 10);
		for (int i = 0; i <= MOVE_LEFT; i++) {
			if (i <= SPAWN_3) {
				image[i] = tileset.getTileset().getSubimage(i * 64 + 64, (this.shipclass-GLASSKANONE) * 64 + 64, 64, 64);
			} else {
				image[i] = tileset.getTileset().getSubimage((i - MOVE_RIGHT) * 64, (this.shipclass-GLASSKANONE) * 64, 64, 64);
			}
		}
	}  
	
	
	//all needed get constructors
	
	public String getshipname(){
		return shipname;
	}
	
	public int getshipclass(){
		return shipclass;
	}
	
	public int getshieldslots(int i){
		return shieldslots[i];
	}
	 
	public int gunslots(int i){
		return gunslots[i];
	}
	
	public int getinventory(int i){
		return inventory[i];
	}
	
	public int getbonusslots(int i){
		return bonusslots[i];
	}
	
	public int getlife(){
		return life;
	}
	 
	public int getdmg(){
		return dmg;
	}
	
	public double getfirespeed(){
		return firespeed;
	}
	
	
	//all needed set constructors
	public void setshipname(String sVal){
		shipname=sVal;
	}
	
	public void setshipclass(int sVal){
		shipclass=sVal;
		
		switch (shipclass){
		case 1: setlife(60);
				setdmg(10);
				setfirespeed(1.00);
				break;
				
		case 2: setlife(100);
				setdmg(6);
				setfirespeed(1.40);
				break;
				
		case 3: setlife(80);
				setdmg(8);
				setfirespeed(1.20);
				break;
		}
	}
	
	public void setshieldslots(int i, int sVal){
		shieldslots[i]=sVal;
	}
	 
	public void setgunslots(int i, int sVal){
		gunslots[i]=sVal;
	}
	
	public void setinventory(int i, int sVal){
		inventory[i]=sVal;
	}
	public void setbonusslots(int i, int sVal){
		bonusslots[i]=sVal;
	}
	 
	public void setlife(int sVal){
		life=life+sVal;
	}
	
	public void setdmg(int sVal){
		dmg=dmg+sVal;
	}
	
	public void setfirespeed(double sVal){
		firespeed=firespeed+sVal;
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
			}
		}
		else {
			retval = image[spawnCnt++];
			if (spawnCnt > SPAWN_3) {
				spawned = true;
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
	
	
}
	
