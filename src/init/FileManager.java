package init;

import java.io.File;

public class FileManager {

	private final static String MAIN_DIRECTORY = ".spc_laz0r";
	private final static String SAVES = MAIN_DIRECTORY + "/saves";
	private final static String CONFIG = MAIN_DIRECTORY + "/config";
	
	public static String getConfigPath() {
		return getFile(CONFIG).getPath();
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
	
	public FileManager() {
		createDirectory(MAIN_DIRECTORY);
		createDirectory(CONFIG);
		createDirectory(SAVES);
		createConfig("config.json", CONFIG);
	}
}
