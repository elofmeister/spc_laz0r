package init;

import java.io.File;

import util.SaveWriter;

public class FileManager {

	private final static String MAIN_DIRECTORY = ".spc_laz0r";
	private final static String SAVES = MAIN_DIRECTORY + "/saves";
	private final static String CONFIG = MAIN_DIRECTORY + "/config";
	
	public static String getConfigPath() {
		return getFile(CONFIG).getPath();
	}
	
	public static String getSavePath() {
		return getFile(SAVES).getPath();
	}
	
	private static File getFile(String path) {
		String FileFolder = null;
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("WIN")) {
		    FileFolder = System.getenv("APPDATA") + "\\" + path;
		}
		if (os.contains("MAC")) {
		    FileFolder = System.getProperty("user.home") + "/Library/Application " + "Support" + path;
		}
		if (os.contains("NUX")) {
		    FileFolder = System.getProperty("user.dir") + path;
		}
		return new File(FileFolder);
	}
	
	private void createDirectory(String path) {
		File directory = getFile(path);
		if (directory.exists() == false) {
		    directory.mkdir();
		}
	}
	
	private void createConfig(String file, String path) {
		File directory = getFile(path + "/" + file);
		if (directory.exists() == false) {
			new ConfigJSON();
		}
	}
	
	private void createSave(int savegame) {
		File directory = getFile(SAVES + "/" + "savegame" + savegame + ".json");
		if (directory.exists() == false) {
			new SaveWriter(savegame).save();
		}
	}
	
	public FileManager() {
		createDirectory(MAIN_DIRECTORY);
		createDirectory(CONFIG);
		createDirectory(SAVES);
		createConfig("config.json", CONFIG);
		createSave(1);
		createSave(2);
		createSave(3);
	}
}
