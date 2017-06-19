package init;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.*;

public class ConfigJSON {
	// DEFAULT RESOLUTION
	private final int DEFAULT_WIDTH = 1024;
	private final int DEFAULT_HEIGHT = 576;
	private final boolean DEFAULT_FULLSCREEN = true;
	private final boolean DEFAULT_SOUND = true;
	private final int DEFAULT_BACKGROUND = 1;
	
	@SuppressWarnings("unchecked")
	public ConfigJSON() {
		 JSONObject obj = new JSONObject();
		 JSONObject resolution = new JSONObject();
		 resolution.put("fullscreen", DEFAULT_FULLSCREEN);
		 resolution.put("width", DEFAULT_WIDTH);
		 resolution.put("height", DEFAULT_HEIGHT);
	     obj.put("resolution", resolution);
	     obj.put("sound", DEFAULT_SOUND);
	     obj.put("background", DEFAULT_BACKGROUND);
	     
	     try (FileWriter file = new FileWriter(FileManager.getConfigPath() + "/config.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}
}
