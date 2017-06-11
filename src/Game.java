import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Game implements Runnable, KeyListener {
	private static final int 	FPS 					= 60;
	private static final long 	LOOP_TIME 				= 1000 / FPS;
	public  static final int 	WINDOW_WIDTH_TILE_NUM 	= 16;
	public  static final int 	WINDOW_HEIGHT_TILE_NUM 	= 9;
	public  static final int 	WINDOW_WIDTH 			= WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH;
	public  static final int 	WINDOW_HEIGHT 			= WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT;
	private static final String GAME_TITLE 				= "spc_laz0r";
	
	private int levelCnt = 0;	// number of generated levels
	private int activeLvl = 1;
	private List<Level> levels = new ArrayList<Level>();	// list of all generated levels
	private gui.Window mainWindow;
	private String bgDir = "right";
	
	public static void main(String[] arg) {
	    new Thread(new Game()).start();	// calling run method 
	}

	public void run() {
		long timestamp;
	    long oldTimestamp;
	    int loadingPercentage = 0;
		mainWindow = new gui.Window("Loading " + (loadingPercentage+=5) + "%");
		mainWindow.getFrame().addKeyListener(this);
		new init.FileManager();
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset.png",10,10)));	// generating first (base) level
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset.png",10,10)));	// generating lvl 1
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset1.png",10,10)));	// generating lvl 2
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset2.png",10,10)));	// generating lvl 3
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset3.png",10,10)));	// generating lvl 4
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset4.png",10,10)));	// generating lvl 5
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset5.png",10,10)));	// generating lvl 6
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset6.png",10,10)));	// generating lvl 7
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset7.png",10,10)));	// generating lvl 8
		mainWindow.changeTitle("Loading " + (loadingPercentage++) + "%");
		int speed = 100;
		while(loadingPercentage <= 100) {
			timestamp = System.currentTimeMillis();
			while (timestamp + speed > System.currentTimeMillis());
				mainWindow.changeTitle("Loading " + loadingPercentage++ + "%");
				speed--;
		}
		timestamp = System.currentTimeMillis();
		while (timestamp + 1000 > System.currentTimeMillis());

		long teststamp1 = System.currentTimeMillis() , teststamp2 = teststamp1;
		mainWindow.changeTitle(GAME_TITLE);
		
		
		while(true) {
			oldTimestamp = System.currentTimeMillis();
		    update();
		    timestamp = System.currentTimeMillis();
		    if ( timestamp - oldTimestamp > LOOP_TIME ) {
		        continue;
		    }
		    render();
		    timestamp = System.currentTimeMillis();
		    if ( timestamp - oldTimestamp <= LOOP_TIME ) {
		        try {
		        	Thread.sleep(LOOP_TIME - (timestamp-oldTimestamp) );
		        } catch (InterruptedException e) {
		        	System.err.println(e.getMessage());
		        }
		    }
		    
		    teststamp2 = System.currentTimeMillis();
		    
		    if ( teststamp2 - teststamp1 >= 1 ) {
		    	if ( bgDir == "right" ) {
		    		levels.get(activeLvl).moveRight();
		    	} else {
		    		levels.get(activeLvl).moveLeft();
		    	}
		    	teststamp1 = teststamp2;
		    	
		    }
		    
		}
	}
	
	private void update() {
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void render() {
		mainWindow.changeImage(levels.get(activeLvl).getSubimage());
	}


	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == 65) {
			bgDir = "left";
		} else if( e.getKeyCode() == 68) {
			bgDir = "right";
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
