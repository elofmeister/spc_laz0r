package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import init.FileManager;

public class ConfigReader {

	private boolean fullscreen;
	private long height;
	private long width;
	
	public ConfigReader(String file) {
		JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(FileManager.getConfigPath() + "/" + file));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resolution = (JSONObject) jsonObject.get("resolution");

            fullscreen = (Boolean) resolution.get("fullscreen");
            height = (Long) resolution.get("height");
            width = (Long) resolution.get("width");

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (org.json.simple.parser.ParseException e) {
            System.err.println(e.getMessage());
		}
	}

	public long getHeight() {
		return height;
	}

	public long getWidth() {
		return width;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
}
