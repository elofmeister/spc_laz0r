
// e.g. Player player1  =  new Player("Horst"); call from main with values

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
	
	public Player(String name) {
		
		this.name  =  name;
		this.xp  =  0;
		this.oldxp  =  0;
		this.lvl  =  1;
		this.skillpts  =  0;
		this.agl  =  0;
		this.critprb  =  0;
		this.critdmg  =  0;
		this.laser  =  0;
		this.acid  =  0;
		this.ice  =  0;
		this.emp  =  0;
		this.cash  =  0;
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
		 if (lvl < 100){
			 lvl += val;
			 setSkillpts(4);
		 }
		
	}
	
	public void setSkillpts(int val){  //max = 400
		skillpts += val;
	}
	
	public void setAgl(){		//start = 1 max = 3.5 step = 0.025  --> glasscannon can max 0,65*200 ~ 15pixels/frame
		if (agl < 3.5){
			agl += 0.025;
			setSkillpts(-1);
			}
		else setSkillpts(1);
		
	}
	
	public void setCritprb(){ //max = 0.2 step = 0.002
		 if (critprb < 0.2){
			 critprb += 0.002;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	
	public void setCritdmg(){	//max = 5x step = 0.05
		 if (critdmg < 5){
			 critdmg += 0.05;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	 
	public void setLaser(){	//Max = 100 step = 1
		 if (laser < 100){
			 laser += 1;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	
	public void setAcid(){	//Max = 100 step = 1
		if (acid < 100){
			 acid += 1;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	
	public void setIce(){	//Max = 100 step = 1
		if (ice < 100){
			 ice += 1;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	public void setEmp(){	//Max = 100 step = 1
		if (emp < 100){
			 emp += 1;
			 setSkillpts(-1);
		 }
		 else setSkillpts(1);
	}
	public void setCash(int val){	//Max =  1 000 000
		 if (cash < 1000000){
			 cash += val;
		 }
	}
	
}
	
