
// z.B. Character player1  =  new Character("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0); call from main with values

public class Player {

	private String name;
	private int xp;
	private int oldxp;
	private int lvl;
	private int skillpts;
	private double agl;
	private double critprb;
	private double critdmg;
	private int laser;
	private int acid;
	private int ice;
	private int emp;
	private int cash;
	
	/*
	 * some space for final modifiers
	 * 
	 * 
	 * 
	 */
	
	public Player(String name,int xp, int lvl, int skillpts, double agl, double critprb, double critdmg, int laser, int acid , int ice, int emp, int cash) {
		
		this.name  =  name;
		this.xp  =  xp;
		this.oldxp  =  xp;
		this.lvl  =  lvl;
		this.skillpts  =  skillpts;
		this.agl  =  agl;
		this.critprb  =  critprb;
		this.critdmg  =  critdmg;
		this.laser  =  laser;
		this.acid  =  acid;
		this.ice  =  ice;
		this.emp  =  emp;
		this.cash  =  cash;
	}  
	
	
	//all needed get constructors
	public String getName(){
		return name;
	}
	public int getXp(){
		return xp;
	}
	 
	public int getLvl(){
		return lvl;
	}
	
	public int getSkillpts(){
		return skillpts;
	}
	
	public double getAgl(){
		return agl;
	}
	
	public double getCritprb(){
		return critprb;
	}
	
	public double getCritdmg(){
		return critdmg;
	}
	 
	public int getLaser(){
		return laser;
	}
	
	public int getAcid(){
		return acid;
	}
	
	public int getIce(){
		return ice;
	}
	public int getEmp(){
		return emp;
	}
	
	public int getCash(){
		return cash;
	}
	
	//all needed set constructors
	public void setName(String val){
		 name = val;
	}
	public void setXp(int val){  //Level Calculating formula
	     xp += val;
	     
	    if(lvl <= 10){				//low Level formula
	    	while(xp >= lvl*lvl*2){
	    		setLvl(1);
		    	oldxp = xp;
	    		}
	    	}
		    if(xp >= 200){				//mid level formula
		    if(lvl >= 11 && lvl <= 50 ){	
		     while(xp >= oldxp*1.1){
		    	 setLvl(1);
		    	 oldxp = xp;
		     	}
		    }
			    if(lvl >= 51 && lvl <= 100 ){ 	//high level formula
			    	while(xp >= oldxp*1.05){
				    	 setLvl(1);
				    	 oldxp = xp;
				    }
		     	}
		    }
	 }
	 
	public void setLvl(int val){		//per 1 level  =  4 skillpoints 
		 lvl += val;
		 setSkillpts(4);
	}
	
	public void setSkillpts(int val){  //max = 400
		skillpts += val;
	}
	
	public void setAgl(double val){		//start = 1 max = 3.5 step = 0.025  
		 agl += val;
	}
	
	public void setCritprb(double val){ //max = 0.2 step = 0.002
		 critprb += val;
	}
	
	public void setCritdmg(double val){	//max = 5x step = 0.05
		 critdmg += val;
	}
	 
	public void setLaser(int val){	//Max = 100
		 laser += val;
	}
	
	public void setAcid(int val){	//Max = 100
		 acid += val;
	}
	
	public void setIce(int val){	//Max = 100
		 ice += val;
	}
	public void setEmp(int val){	//Max = 100
		 emp += val;
	}
	public void setCash(int val){	//Max =  1 000 000
		 cash += val;
	}
	
}
	
