import java.awt.image.BufferedImage;

//z.B. Ships shp = new Ships(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); aufruf aus main mit übergabewerten

public class Ships{

	private String shipname;
	private int shipclass;
	private int gunslots[] = new int[8];
	private int bonusslots[] = new int[4];
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

	public static final int STANDARDO = 1;
	public static final int RUMPLER = 2;
	public static final int GLASSCANNON = 3;
	
	public static final int SHIPSPEED = 12; //in pixel per move
	
	private BufferedImage[] image = new BufferedImage[10];		// all ship tiles
	private boolean spawned = false;
	private int spawnCnt = SPAWN_0;
	private int animation = MOVE_RIGHT;
	
	private long spawnTimer = System.currentTimeMillis();
	
	private Coordinates coor = new Coordinates(256, 256);
		
	public Ships(int shipclass) {
		setshipclass(shipclass);
	}  
		
	public void setshipclass(int shipclass) {
		this.shipclass = shipclass;
		switch (this.shipclass) {
		case GLASSCANNON: 
			setlife(60);
			setdmg(10);
			setfirespeed(1.00);
			shipname = "GLASSCANNON";
			break;
		case RUMPLER: 
			setlife(100);
			setdmg(6);
			setfirespeed(1.40);
			shipname = "Rumpler";
			break;
		case STANDARDO: 
			setlife(80);
			setdmg(8);
			setfirespeed(1.20);
			shipname = "Standardo";
			break;
		default: 
			break;
		}
		TileSet tileset = new TileSet("tiles/ships.png", 10, 10);
		for (int i = 0; i <= MOVE_LEFT; i++) {
			if (i <= SPAWN_3) {
				image[i] = tileset.getTileset().getSubimage(i * 64 + 64, (this.shipclass-STANDARDO) * 128 + 64, 64, 64);
			} else {
				image[i] = tileset.getTileset().getSubimage((i - MOVE_RIGHT) * 64, (this.shipclass-STANDARDO) * 128, 64, 64);
			}
		}
	}
	
	public String getshipname(){
		return shipname;
	}
	
	public int getshipclass(){
		return shipclass;
	}
	 
	public int gunslots(int i){
		return gunslots[i];
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
		 
	public void setgunslots(int i, int sVal){
		gunslots[i]=sVal;
	}
	
	public void setbonusslots(int i, int sVal){
		bonusslots[i]=sVal;
	}
	 
	public void setlife(int sVal){
		life=sVal;
	}
	
	public void setdmg(int sVal){
		dmg=sVal;
	}
	
	public void setfirespeed(double sVal){
		firespeed=+sVal;
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
			if (spawnTimer+1000<System.currentTimeMillis()) {
				spawnTimer = System.currentTimeMillis();
				retval = image[spawnCnt++];
				if (spawnCnt > SPAWN_3) {
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
	
	
}
	
