import java.util.Random;

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
	
	/*
	 * some space for final modifiers
	 * 
	 * Enemy 123 = new Enemy (enemylvl, category);
	 * 
	 */ 

	
	
	Enemy(int enemylvl, int enemycategory){   //enemylvl = current lvl , category = 1,2,3,4
		
		this.enemylvl = enemylvl;
		switch(enemycategory){
		case 1: setEnemydmg(15);    	//bomber class
				setEnemyelement(0);
				setEnemylife(12);
				setNumberguns(0);
				setDropchance(60);  //can drop consumables
				setEnemyxp(1);
				setEnemycash(10);									
				break;
				
		case 2: setEnemydmg(10);			// fire class
				setEnemyelement(0);
				setEnemylife(20);
				setNumberguns(1);
				setDropchance(50);  //can drop items
				setEnemyxp(2);
				setEnemycash(20);					
				break;	
				
		case 3: setEnemydmg(20);			//special class 
				setEnemyelement(1);
				setEnemylife(50);
				setNumberguns(4);
				setDropchance(30);  // always drop items
				setEnemyxp(4);
				setEnemycash(30);					
				break;	
				
		case 4: setEnemydmg(30);			//bozz class
				setEnemyelement(2);
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
		
		public void setEnemyelement(int eVal){  //type of enemy resistances 1=laser 2=ice 3=acid 4=emp , e.g. bozz = 14 or 41 acid laser  
			Random rnd = new Random();
			rnd.setSeed(System.currentTimeMillis());
				
			switch (eVal){
			case 1: eVal = Math.abs(rnd.nextInt())%4+1;
					enemyelement = eVal;
					break;
			case 2: eVal = Math.abs(rnd.nextInt())%4+1;
					enemyelement = eVal;
					if (eVal == 4){
						eVal = 0;
					}
					enemyelement = enemyelement+(eVal+1)*10;
					break;
			default: break;		
			}
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
