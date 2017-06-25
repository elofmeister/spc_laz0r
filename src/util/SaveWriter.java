package util;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import init.FileManager;

public class SaveWriter {

	public final static int SAVE_1 = 1;
	public final static int SAVE_2 = 2;
	public final static int SAVE_3 = 3;

	// FILENAME
	private String filename;
	
	// LEVEL DATA
	private int levelProgress;
	
	// PLAYER DATA
	private String name = "Horst";
	private int xp = 0;
	private int oldxp = 0;
	private int lvl = 1;
	private int skillpts;
	private int agl; 
	private int critprb;
	private int critdmg;
	private int laser;
	private int acid; 
	private int ice;
	private int emp;
	private int cash;
	
	// SHIP DATA
	private int shipclass = 1;
	private int healthpotions;
	private int shields;
	private int bombs;
	private int portals;
	
	public SaveWriter(int savegame) {
		filename = "savegame"+savegame;
	}
	
	public void setLevelData(int levelProgress) {
		this.levelProgress = levelProgress;
	}
	
	public void setPlayerData(String name, int xp, int oldxp, int lvl, int skillpts, int agl, int critprb, int critdmg, int laser, int acid, int ice, int emp, int cash) {
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
	
	public void setShipData(int shipclass, int healthpotions, int shields, int bombs, int portals) {
		this.shipclass = shipclass;
		this.healthpotions = healthpotions;
		this.shields = shields;
		this.bombs = bombs;
		this.portals = portals;
	}
	
	@SuppressWarnings("unchecked")
	public void save() {
		JSONObject obj = new JSONObject();
		
		// LEVEL DATA
		JSONObject level = new JSONObject();
		level.put("levelProgress", levelProgress);
		obj.put("level", level);
		
		// PLAYER DATA
		JSONObject player = new JSONObject();
		player.put("name", name);
		player.put("xp", xp);
		player.put("oldxp", oldxp);
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
		
		// SHIP DATA
		JSONObject ship = new JSONObject();
		ship.put("shipclass", shipclass);
		ship.put("healthpotions", healthpotions);
		ship.put("shields", shields);
		ship.put("bombs", bombs);
		ship.put("portals", portals);
		obj.put("ship", ship);
		
		try (FileWriter file = new FileWriter(FileManager.getSavePath() + "/"+filename+".json")) {
		      file.write(obj.toJSONString());
		      file.flush();
		} catch (IOException e) {
		      System.err.println(e.getMessage());
		}
	}
}
