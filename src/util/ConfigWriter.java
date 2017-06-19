package util;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import init.FileManager;

public class ConfigWriter {
	
	private final int DEFAULT_WIDTH = 1024;
	private final int DEFAULT_HEIGHT = 576;

	public ConfigWriter(boolean fullscreen, boolean sound, int background) {
		 JSONObject obj = new JSONObject();
		 JSONObject resolution = new JSONObject();
		 resolution.put("fullscreen", fullscreen);
		 resolution.put("width", DEFAULT_WIDTH);
		 resolution.put("height", DEFAULT_HEIGHT);
	     obj.put("resolution", resolution);
	     obj.put("sound", sound);
	     obj.put("background", background);
	     
	     try (FileWriter file = new FileWriter(FileManager.getConfigPath() + "/config.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}

}
