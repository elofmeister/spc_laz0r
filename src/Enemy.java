import java.awt.image.BufferedImage;
import java.util.Random;

// e.g. Enemy badguy = new Enemy (enemylvl, category, currentlvl);

public class Enemy {
	
	private long id;
	private int enemylvl;
	private int enemydmg;
	private int enemycategory;
	private int enemyelement;
	private int enemylife;
	private int numberguns;	
	private int dropchance;
	private int enemyxp;
	private int enemycash;
	private long enemycurse_timestamp = System.currentTimeMillis();
	private long enemycurse_speed = 0;
	private Level level;
	private Coordinates coordinates;
	private Movement movement;
	private BufferedImage[] image = new BufferedImage[4];
	private Random rnd;
	
	public static final int ENEMYSPEED = 10;
	public static final int ENEMY_CURSE_DURATION = 5000;
	public static final int SPAWN_DISTANCE = 90;
	
	public static final int BOMBER_CLASS = 1;
	public static final int FIRE_CLASS = 2;
	public static final int SPECIAL_CLASS = 3;
	public static final int BOZZ_CLASS = 4;
	
	public static final int LASER_RESISTANCE = 1;
	public static final int ICE_RESISTANCE = 2;
	public static final int ACID_RESISTANCE = 3;
	public static final int EMP_RESISTANCE = 4;

	/*
	 * some space for final modifiers
	 * 
	 * 
	 * 
	 */ 

	
	
	public Enemy(int enemycategory, Level level, BufferedImage image, Random rnd){   //enemylvl = current lvl , category = 1,2,3,4
		this.rnd = rnd;
		this.id = rnd.nextLong();
		int x = 0, y = 0;
		while(!(
				(
						(x < -TileSet.TILE_WIDTH && y < -TileSet.TILE_HEIGHT) ||
						(x > Game.WINDOW_WIDTH && y < -TileSet.TILE_HEIGHT) ||
						(x < -TileSet.TILE_WIDTH && y > Game.WINDOW_HEIGHT) ||
						(x > Game.WINDOW_WIDTH && y > Game.WINDOW_HEIGHT)
				)
						&&
				(
						(x > -SPAWN_DISTANCE - TileSet.TILE_WIDTH && y > -SPAWN_DISTANCE - TileSet.TILE_HEIGHT) &&
						(x < Game.WINDOW_WIDTH + SPAWN_DISTANCE && y > -SPAWN_DISTANCE - TileSet.TILE_HEIGHT) &&
						(x > -SPAWN_DISTANCE - TileSet.TILE_WIDTH && y < Game.WINDOW_HEIGHT + SPAWN_DISTANCE)
				)			
				)) {
			x = rnd.nextInt() % (Game.WINDOW_WIDTH + SPAWN_DISTANCE);
			y = rnd.nextInt() % (Game.WINDOW_HEIGHT + SPAWN_DISTANCE);
		}
		coordinates = new Coordinates(x, y);
		movement = new Movement(coordinates, Math.abs(rnd.nextInt())%3);
		this.level = level;
		switch(enemycategory){
		case BOMBER_CLASS:
				setEnemydmg(15);    	
				setEnemyelement();
				setEnemylvl();
				setEnemylife(12);
				setNumberguns(0);
				setDropchance(60);  //can drop consumables
				setEnemyxp(1);
				setEnemycash(10);									
				break;
				
		case FIRE_CLASS: 
				setEnemydmg(10);			
				setEnemyelement();
				setEnemylvl();
				setEnemylife(20);
				setNumberguns(1);
				setDropchance(50);  //can drop items
				setEnemyxp(2);
				setEnemycash(20);					
				break;	
				
		case SPECIAL_CLASS: 
				setEnemydmg(20);			
				setEnemyelement();
				setEnemylvl();
				setEnemylife(50);
				setNumberguns(4);
				setDropchance(30);  // always drop items
				setEnemyxp(4);
				setEnemycash(30);					
				break;	
				
		case BOZZ_CLASS: 
				setEnemydmg(30);			//bozz class
				setEnemyelement();
				setEnemylvl();
				setEnemylife(100);
				setNumberguns(10);
				setDropchance(25);  // always drop items
				setEnemyxp(10);
				setEnemycash(50);
				break;				
		}
		int xOffset = 0, yOffset = 0;
		switch (getEnemyelement()) {
		case LASER_RESISTANCE:
			xOffset = 0;
			yOffset = 0;
			break;
		case ICE_RESISTANCE:
			xOffset = 0;
			yOffset = 4 * TileSet.TILE_HEIGHT;	
			break;
		case ACID_RESISTANCE:
			xOffset = 4 * TileSet.TILE_WIDTH;
			yOffset = 0;
			break;
		case EMP_RESISTANCE:
			xOffset = 4 * TileSet.TILE_WIDTH;
			yOffset = 4 * TileSet.TILE_HEIGHT;
			break;

		default:
			break;
		}
		for (int i = 0; i < this.image.length; i++) {
			this.image[i] = image.getSubimage(i * TileSet.TILE_WIDTH + xOffset, (enemycategory - BOMBER_CLASS) * TileSet.TILE_HEIGHT + yOffset, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);	
		}
	} 
	//all needed get constructors

		public int getEnemylvl(){
			return enemylvl;
		}
		 
		public int getEnemydmg(){
			return enemydmg;
		}
		
		public int getEnemycategory(){
			return enemycategory;
		}
		
		public int getEnemyelement(){
			return enemyelement;
		}
		
		public int getEnemylife(){
			return enemylife;
		}
			 
		public int getNumberguns(){
			return numberguns;
		}
		
		public int getDropchance(){
			return dropchance;
		}
		
		public int getEnemyxp(){
			return enemyxp;
		}
		
		public int getEnemycash(){
			return enemycash;
		}
		
		public int getEnemyspeed(){
			int retval = ENEMYSPEED;
			if (enemycurse_timestamp + ENEMY_CURSE_DURATION > System.currentTimeMillis()) {
				retval+=enemycurse_speed;
			}
			return retval; 
		}
		
		public BufferedImage getImage() {
			return image[movement.getDirection()];
		}
		
		//all needed set constructors

		public void setEnemylvl(){
			enemylvl = Math.abs(rnd.nextInt())%4 + (level.getLevelCnt()-1) * 10 + level.getWaveCnt(); //+ maybe level.getWavesamount()			
		}
		
		public void setEnemydmg(int eVal){		//damage per bullet/contact
			enemydmg = eVal;
		}
		
		public void setEnemycategory(int eVal){
			enemycategory = eVal;			
			
			
		}
		
		public void setEnemyelement(){  //type of enemy resistances 1=laser 2=ice 3=acid 4=emp , e.g. bozz = 14 or 41 acid laser  
			Random rnd = new Random();
			rnd.setSeed(System.currentTimeMillis()); 
			enemyelement = Math.abs(rnd.nextInt())%4+1;		
		}
		
		public void setEnemylife(int eVal){
			if (eVal >= 0){			//starting life	
				enemylife += eVal*enemylvl;
			}
			else{
				enemylife += eVal;  //damage calculation
			}
		}
				 
		public void setNumberguns(int eVal){
			numberguns += eVal;
		}
		
		public void setDropchance(double eVal){  
			dropchance += eVal;
		}
		
		public void setEnemyxp(int eVal){
			enemyxp = eVal*enemylvl;
		}
		
		public void setEnemycash(int eVal){
			enemycash = enemycash+enemylvl*eVal;
		}
		
		public void setEnemycurse() {
			enemycurse_timestamp = System.currentTimeMillis();
		}
		
		public void setEnemycursespeed(int eVal){
			enemycurse_speed = eVal;
		}
		
		public void move() {
			movement.move(getEnemyspeed());
		}
		
		public Coordinates getCoordinates() {
			return coordinates;
		}
		
		public boolean isInView() {
			boolean bretval = false;
			if (
					coordinates.getX() >= 0 && 
					coordinates.getY() >= 0 && 
					coordinates.getX() < Game.WINDOW_WIDTH - TileSet.TILE_WIDTH &&
					coordinates.getY() < Game.WINDOW_HEIGHT - TileSet.TILE_HEIGHT
				) {
				bretval = true;
			}
			return bretval;
		}
		
		public long getIdentity() {
			return id;
		}
		
		public int getDirection() {
			return movement.getDirection();
		}
}
