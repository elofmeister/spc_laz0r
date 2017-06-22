import java.util.Random;

// e.g. Enemy badguy = new Enemy (enemylvl, category);

public class Enemy {
	
	private int enemylvl;
	private int enemydmg;
	private int enemycategory;
	private int enemyelement;
	private int enemylife;
	private int numberguns;	
	private double dropchance;
	private int enemyxp;
	private int enemycash;
	
	
	public static final int BOMBER_CLASS = 1;
	public static final int FIRE_CLASS = 2;
	public static final int SPECIAL_CLASS = 3;
	public static final int BOZZ_CLASS = 4;
	public static final int lASER_RESISTANCE = 1;
	public static final int ICE_RESISTANCE = 2;
	public static final int ACID_RESISTANCE = 3;
	public static final int EMP_RESISTANCE = 4;

	/*
	 * some space for final modifiers
	 * 
	 * 
	 * 
	 */ 

	
	
	Enemy(int enemylvl, int enemycategory){   //enemylvl = current lvl , category = 1,2,3,4
		
		this.enemylvl = enemylvl;
		switch(enemycategory){
		case BOMBER_CLASS: 
				setEnemydmg(15);    	
				setEnemyelement();
				setEnemylife(12);
				setNumberguns(0);
				setDropchance(60);  //can drop consumables
				setEnemyxp(1);
				setEnemycash(10);									
				break;
				
		case FIRE_CLASS: 
				setEnemydmg(10);			
				setEnemyelement();
				setEnemylife(20);
				setNumberguns(1);
				setDropchance(50);  //can drop items
				setEnemyxp(2);
				setEnemycash(20);					
				break;	
				
		case SPECIAL_CLASS: 
				setEnemydmg(20);			
				setEnemyelement();
				setEnemylife(50);
				setNumberguns(4);
				setDropchance(30);  // always drop items
				setEnemyxp(4);
				setEnemycash(30);					
				break;	
				
		case BOZZ_CLASS: 
				setEnemydmg(30);			//bozz class
				setEnemyelement();
				setEnemylife(100);
				setNumberguns(10);
				setDropchance(25);  // always drop items
				setEnemyxp(10);
				setEnemycash(50);
				break;				
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
		
		public double getDropchance(){
			return dropchance;
		}
		
		public int getEnemyxp(){
			return enemyxp;
		}
		
		public int getEnemycash(){
			return enemycash;
		}
		
		
		
		//all needed set constructors

		public void setEnemylvl(int eVal){
			//Lvl+waveNumber+rand()%2; values between 1-100 <--- <--- <--- <---<--- <---<--- <---<--- <--- ???
			enemylvl = eVal;
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
			int eVal = Math.abs(rnd.nextInt())%4+1;  
					enemyelement = eVal;		
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
			enemyxp = eVal;
		}
		
		public void setEnemycash(int eVal){
			enemycash = enemycash+enemylvl*eVal;
		}
			
}
