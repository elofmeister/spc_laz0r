import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

// e.g. Player player1  =  new Player("Horst"); call from main with values

public class Player {

	public static final long ID = 1;
	private String name;
	private int xp;
	private int oldxp;
	private int lvl;
	private int skillpts;
	private int agl;
	private int critprb;
	private int critdmg;
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
		reset();
	}  
	
	public void load(String name, int xp, int oldxp, int lvl, int skillpts, int agl, int critprb, int critdmg, int laser, int acid, int ice, int emp, int cash){
		this.name  =  name;
		this.xp  =  xp;
		this.oldxp  = oldxp;
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
	
	public void reset() {
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
	
	public int getAgl(){
		return agl;
	}
	
	public int getCritprb(){
		return critprb;
	}
	
	public int getCritdmg(){
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
	public int getoldXP(){
		return oldxp;
	}
	
	public int getColor(){
		int retval = Bullet.PURPLE;
		int maxval = Math.max( getLaser(), Math.max( getIce(), Math.max( getAcid(), getEmp() ) ) );
		if (maxval!=0) {
			if(maxval==getLaser()){
				retval = Bullet.RED;
			}
			if(maxval==getIce()){
				retval = Bullet.BLUE;
			}
			if(maxval==getAcid()){
				retval = Bullet.GREEN;
			}
			if(maxval==getEmp()){
				retval = Bullet.YELLOW;
			}
		}
		return retval;
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
	
	public void setAgl(){		//start = 1 max =4 step = 1 --> glasscannon can max  ~ 16pixels/frame
		if (agl < 4 && skillpts >= 10){
			agl += 1;
			setSkillpts(-10);
			}
		else setSkillpts(10);
		
	}
	
	public void setCritprb(){ //max = 50% step = 1
		 if (critprb < 50){
			 critprb += 1;
			 setSkillpts(-2);
		 }
		 else setSkillpts(2);
	}
	
	public void setCritdmg(){	//max = 50x step = 1
		 if (critdmg < 50){
			 critdmg += 1;
			 setSkillpts(-2);
		 }
		 else setSkillpts(2);
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
	public void setoldXP(int oldXP) {
		oldxp = oldXP;
	}

}
	
