package init;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class SaveJSON {

	private String name;
	private int xp;
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
	
	public SaveJSON(String name,int xp, int lvl, int skillpts, double agl, double critprb, double critdmg, int laser, int acid , int ice, int emp, int cash) {
		this.name  =  name;
		this.xp  =  xp;
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
	public SaveJSON() {
	}
	
	@SuppressWarnings("unchecked")
	public void save(String filename) {
		JSONObject obj = new JSONObject();
		JSONObject character = new JSONObject();
		character.put("name", name);
		character.put("xp", xp);
		character.put("lvl", lvl);
		character.put("skillpts", skillpts);
		character.put("agl", agl);
		character.put("critprb", critprb);
		character.put("critdmg", critdmg);
		character.put("laser", laser);
		character.put("acid", acid);
		character.put("ice", ice);
		character.put("emp", emp);
		character.put("cash", cash);
		obj.put("character", character);
		try (FileWriter file = new FileWriter(FileManager.getSavePath() + "/"+filename+".json")) {
		      file.write(obj.toJSONString());
		      file.flush();
		} catch (IOException e) {
		      System.err.println(e.getMessage());
		}
	}
}
