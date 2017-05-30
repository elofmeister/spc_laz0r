
public class Enemy {
	
	private String ENEMYNAME;
	private int ENEMYLVL;
	private int ENEMYDMG;
	private int ENEMYCATEGORY;
	private int ENEMYELEMENT;
	private int ENEMYLIFE;
	private int NUMBERGUNS;	
	private double DROPCHANCE;
	private int ENEMYXP;
	private int ENEMYCASH;

	
	
	Enemy(String activeENEMYNAME,int activeENEMYLVL, int activeENEMYDMG, int activeENEMYCATEGORY, int activeENEMYELEMENT, int activeENEMYLIFE, int activeNUMBERGUNS, double activeDROPCHANCE , int activeENEMYXP ,int activeENEMYCASH){
		
	
		ENEMYNAME = activeENEMYNAME;
		ENEMYLVL = activeENEMYLVL;
		ENEMYDMG =activeENEMYDMG;
		ENEMYCATEGORY = activeENEMYCATEGORY;
		ENEMYELEMENT = activeENEMYELEMENT;
		ENEMYLIFE = activeENEMYLIFE;
		NUMBERGUNS = activeNUMBERGUNS;	
		DROPCHANCE = activeDROPCHANCE;
		ENEMYXP = activeENEMYXP; 
		ENEMYCASH = activeENEMYCASH;
	} 
	//all needed get constructors
		public String getENEMYNAME(){
			return ENEMYNAME;
		}
		public int getENEMYLVL(){
			return ENEMYLVL;
		}
		 
		public int getENEMYDMG(){
			return ENEMYDMG;
		}
		
		public int getENEMYCATEGORY(){
			return ENEMYCATEGORY;
		}
		
		public int getENEMYELEMENT(){
			return ENEMYELEMENT;
		}
		
		public int getENEMYLIFE(){
			return ENEMYLIFE;
		}
			 
		public int getNUMBERGUNS(){
			return NUMBERGUNS;
		}
		
		public double getDROPCHANCE(){
			return DROPCHANCE;
		}
		
		public int getENEMYXP(){
			return ENEMYXP;
		}
		
		public int getENEMYCASH(){
			return ENEMYCASH;
		}
		
		
		
		//all needed set constructors
		public void setENEMYNAME(String eVal){
			ENEMYNAME=eVal;
		}
	
		public void setENEMYLVL(int eVal){
			//activeLvl+waveNumber+rand()%2; values between 1-100
			ENEMYLVL=eVal;
		}
		
		public void setENEMYDMG(int eVal){		//damage per bullet/contact
			ENEMYDMG=ENEMYDMG+eVal*ENEMYLVL;
		}
		
		public void setENEMYCATEGORY(int eVal){
			ENEMYCATEGORY=eVal;			
			switch(eVal){
			case 1: setENEMYDMG(ENEMYLIFE);    	//bomber class
					setENEMYELEMENT(0);
					setENEMYLIFE(10);
					setNUMBERGUNS(0);
					setDROPCHANCE(0.05);
					setENEMYXP(1);
					setENEMYCASH(1);									
					break;
					
			case 2: setENEMYDMG(10);			// fire class
					setENEMYELEMENT(0);
					setENEMYLIFE(10);
					setNUMBERGUNS(1);
					setDROPCHANCE(0.1);
					setENEMYXP(2);
					setENEMYCASH(2);					
					break;	
					
			case 3: setENEMYDMG(50);			//special class 
					setENEMYELEMENT(1);
					setENEMYLIFE(50);
					setNUMBERGUNS(4);
					setDROPCHANCE(0.3);
					setENEMYXP(4);
					setENEMYCASH(10);					
					break;	
					
			case 4: setENEMYDMG(100);			//bozz class
					setENEMYELEMENT(2);
					setENEMYLIFE(100);
					setNUMBERGUNS(10);
					setDROPCHANCE(0.5);
					setENEMYXP(10);
					setENEMYCASH(50);
					break;				
			}
			
		}
		
		public void setENEMYELEMENT(int eVal){  //number of enemy resistances
			ENEMYELEMENT=ENEMYELEMENT+eVal;
		}
		
		public void setENEMYLIFE(int eVal){
			if (eVal>=0){				  //starting life	
				ENEMYLIFE=ENEMYLVL*eVal;
				ENEMYLIFE=ENEMYLIFE+eVal;
			}
			else{
				ENEMYLIFE=ENEMYLIFE+eVal;  //damage calculation
			}
		}
				 
		public void setNUMBERGUNS(int eVal){
			NUMBERGUNS=NUMBERGUNS+eVal;
		}
		
		public void setDROPCHANCE(double eVal){
			DROPCHANCE=DROPCHANCE+eVal;
		}
		
		public void setENEMYXP(int eVal){
			ENEMYXP=eVal;
			ENEMYXP=ENEMYXP*ENEMYLVL*eVal;
		}
		
		public void setENEMYCASH(int eVal){
			ENEMYCASH=ENEMYCASH+ENEMYLVL*eVal;
		}
		
}
