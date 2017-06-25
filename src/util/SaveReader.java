package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import init.FileManager;

public class SaveReader {
	
	public final static int SAVE_1 = 1;
	public final static int SAVE_2 = 2;
	public final static int SAVE_3 = 3;

	// FILENAME
	private String filename;
	
	// LEVEL DATA
	private long levelProgress;
	
	// PLAYER DATA
	private String name;
	private long xp;
	private long oldxp;
	private long lvl;
	private long skillpts;
	private long agl; 
	private long critprb;
	private long critdmg;
	private long laser;
	private long acid; 
	private long ice;
	private long emp;
	private long cash;
	
	// SHIP DATA
	private long shipclass;
	private long healthpotions;
	private long shields;
	private long bombs;
	private long portals;
	
	public SaveReader(int savegame) {
		filename = "savegame"+savegame;
	
		JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(FileManager.getSavePath() + "/" + filename + ".json"));

            JSONObject jsonObject = (JSONObject) obj;
            
            JSONObject level = (JSONObject) jsonObject.get("level");
            levelProgress = (Long) level.get("levelProgress");
            
            JSONObject player = (JSONObject) jsonObject.get("player");
            name = (String) player.get("name");
    		xp = (Long) player.get("xp");
    		oldxp = (Long) player.get("oldxp");
    		lvl = (Long) player.get("lvl");
    		skillpts = (Long) player.get("skillpts");
    		agl = (Long) player.get("agl");
    		critprb = (Long) player.get("critprb");
    		critdmg = (Long) player.get("critdmg");
    		laser  = (Long) player.get("laser");
    		acid  = (Long) player.get("acid");
    		ice  = (Long) player.get("ice");
    		emp  = (Long) player.get("emp");
    		cash  = (Long) player.get("cash");
    		
    		JSONObject ship = (JSONObject) jsonObject.get("ship");
    		shipclass  = (Long) ship.get("shipclass");
    		healthpotions  = (Long) ship.get("healthpotions");
    		shields  = (Long) ship.get("shields");
    		bombs  = (Long) ship.get("bombs");
    		portals  = (Long) ship.get("portals");

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (org.json.simple.parser.ParseException e) {
            System.err.println(e.getMessage());
		}
	}

	public static int getSave1() {
		return SAVE_1;
	}

	public static int getSave2() {
		return SAVE_2;
	}

	public static int getSave3() {
		return SAVE_3;
	}

	public String getFilename() {
		return filename;
	}

	public int getLevelProgress() {
		return (int) levelProgress;
	}

	public String getName() {
		return name;
	}

	public int getXp() {
		return (int) xp;
	}

	public int getOldxp() {
		return (int) oldxp;
	}

	public int getLvl() {
		return (int) lvl;
	}

	public int getSkillpts() {
		return (int) skillpts;
	}

	public int getAgl() {
		return (int) agl;
	}

	public int getCritprb() {
		return (int) critprb;
	}

	public int getCritdmg() {
		return (int) critdmg;
	}

	public int getLaser() {
		return (int) laser;
	}

	public int getAcid() {
		return (int) acid;
	}

	public int getIce() {
		return (int) ice;
	}

	public int getEmp() {
		return (int) emp;
	}

	public int getCash() {
		return (int) cash;
	}

	public int getShipclass() {
		return (int) shipclass;
	}

	public int getHealthpotions() {
		return (int) healthpotions;
	}

	public int getShields() {
		return (int) shields;
	}

	public int getBombs() {
		return (int) bombs;
	}

	public int getPortals() {
		return (int) portals;
	}
}
