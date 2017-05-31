package init;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.*;

public class ConfigJSON {
	// DEFAULT RESOLUTION
	private final int DEFAULT_WIDTH = 1024;
	private final int DEFAULT_HEIGHT = 576;
	
	@SuppressWarnings("unchecked")
	public ConfigJSON() {
		 JSONObject obj = new JSONObject();
		 JSONObject resolution = new JSONObject();
		 resolution.put("width", DEFAULT_WIDTH);
		 resolution.put("height", DEFAULT_HEIGHT);
	     obj.put("resolution", resolution);
	     try (FileWriter file = new FileWriter(FileManager.getConfigPath() + "/config.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}
}
