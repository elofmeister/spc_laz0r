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
		JSONObject player = new JSONObject();
		player.put("name", name);
		player.put("xp", xp);
		player.put("lvl", lvl);
		player.put("skillpts", skillpts);
		player.put("agl", agl);
		player.put("critprb", critprb);
		player.put("critdmg", critdmg);
		player.put("laser", laser);
		player.put("acid", acid);
		player.put("ice", ice);
		player.put("emp", emp);
		player.put("cash", cash);
		obj.put("player", player);
		try (FileWriter file = new FileWriter(FileManager.getSavePath() + "/"+filename+".json")) {
		      file.write(obj.toJSONString());
		      file.flush();
		} catch (IOException e) {
		      System.err.println(e.getMessage());
		}
	}
}
