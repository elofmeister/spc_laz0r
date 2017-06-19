
public class Enemy {
	
	private String enemyname;
	private int enemylvl;
	private int enemydmg;
	private int enemycategory;
	private int enemyelement;
	private int enemylife;
	private int numberguns;	
	private double dropchance;
	private int enemyxp;
	private int enemycash;
	
	/*
	 * some space for final modifiers
	 * 
	 * 
	 * 
	 */

	
	
	Enemy(String this.enemyname, int this.enemylvl, int this.enemydmg, int this.enemycategory, int this.enemyelement, int this.enemylife, int this.numberguns, double this.dropchance , int this.enemyxp ,int this.enemycash){
		
	
		enemyname  =  this.enemyname;
		enemylvl  =  this.enemylvl;
		enemydmg  = this.enemydmg;
		enemycategory  =  this.enemycategory;
		enemyelement  =  this.enemyelement;
		enemylife  =  this.enemylife;
		numberguns  =  this.numberguns;	
		dropchance  =  this.dropchance;
		enemyxp  =  this.enemyxp; 
		enemycash  =  this.enemycash;
	} 
	//all needed get constructors
		public String getEnemyname(){
			return enemyname;
		}
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
		public void setEnemyname(String eVal){
			enemyname = eVal;
		}
	
		public void setEnemylvl(int eVal){
			//this.Lvl+waveNumber+rand()%2; values between 1-100
			enemylvl = eVal;
		}
		
		public void setEnemydmg(int eVal){		//damage per bullet/contact
			enemydmg = enemydmg+eVal*enemylvl;
		}
		
		public void setEnemycategory(int eVal){
			enemycategory = eVal;			
			switch(eVal){
			case 1: setEnemydmg(enemylife);    	//bomber class
					setEnemyelement(0);
					setEnemylife(10);
					setNumberguns(0);
					setDropchance(0.05);  //new 60
					setEnemyxp(1);
					setEnemycash(1);									
					break;
					
			case 2: setEnemydmg(10);			// fire class
					setEnemyelement(0);
					setEnemylife(10);
					setNumberguns(1);
					setDropchance(0.1);  //new 50
					setEnemyxp(2);
					setEnemycash(2);					
					break;	
					
			case 3: setEnemydmg(50);			//special class 
					setEnemyelement(1);
					setEnemylife(50);
					setNumberguns(4);
					setDropchance(0.3);  // new 30
					setEnemyxp(4);
					setEnemycash(10);					
					break;	
					
			case 4: setEnemydmg(100);			//bozz class
					setEnemyelement(2);
					setEnemylife(100);
					setNumberguns(10);
					setDropchance(0.5);  // new 25
					setEnemyxp(10);
					setEnemycash(50);
					break;				
			}
			
		}
		
		public void setEnemyelement(int eVal){  //number of enemy resistances
			enemyelement = enemyelement+eVal;
		}
		
		public void setEnemylife(int eVal){
			if (eVal >= 0){				  //starting life	
				enemylife *= eVal;
				enemylife += eVal;
			}
			else{
				enemylife += eVal;  //damage calculation
			}
		}
				 
		public void setNumberguns(int eVal){
			numberguns += eVal;
		}
		
		public void setDropchance(double eVal){  //values between 0.0000 - 1.0000
			dropchance += eVal;
		}
		
		public void setEnemyxp(int eVal){
			enemyxp = eVal;
			enemyxp = enemyxp*enemylvl*eVal;
		}
		
		public void setEnemycash(int eVal){
			enemycash = enemycash+enemylvl*eVal;
		}
		
}
