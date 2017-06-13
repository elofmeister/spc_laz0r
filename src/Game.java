import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

public class Game implements Runnable, KeyListener {
	private static final int 	FPS 					= 60;
	private static final long 	LOOP_TIME 				= 1000 / FPS;
	public  static final int 	WINDOW_WIDTH_TILE_NUM 	= 16;
	public  static final int 	WINDOW_HEIGHT_TILE_NUM 	= 9;
	public  static final int 	WINDOW_WIDTH 			= WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH;
	public  static final int 	WINDOW_HEIGHT 			= WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT;
	private static final String GAME_TITLE 				= "spc_laz0r";
	
	private static final String	CAMERA_DIRECTION_RIGHT	= "right";
	private static final String	CAMERA_DIRECTION_LEFT	= "left";
	
	private int levelCnt = 0;	// number of generated levels
	private int activeLvl = 1;
	private List<Level> levels = new ArrayList<Level>();	// list of all generated levels
	private gui.Window mainWindow;
	private String direction = CAMERA_DIRECTION_RIGHT;
	@SuppressWarnings("unused")
	private Character player = new Character("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	private Ships shp = new Ships(Ships.GLASSKANONE, 0, 1, 0, 0, 0, 0, 0);
	
	public static void main(String[] arg) {
	    new Thread(new Game()).start();	// calling run method 
	}

	public void run() {
		//new init.SaveJSON("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0).save("save");
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
		    	if ( direction == CAMERA_DIRECTION_RIGHT ) {
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
		int[] rgbBackground = new int[16*64*9*64];
		levels.get(activeLvl).getSubimage().getRGB(0, 0, 16*64, 9*64, rgbBackground, 0, 16*64);
		int[] rgbShip = new int[64*64];
		shp.getImage().getRGB(0, 0, 64, 64, rgbShip, 0, 64);

		final int TRANSPARANCY = 16777215;
		for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
				if (rgbShip[i*TileSet.TILE_WIDTH+j]!=TRANSPARANCY) {
					rgbBackground[(shp.getCoordinates().getY()+i)*16*64 + shp.getCoordinates().getX() + j] = rgbShip[i*TileSet.TILE_WIDTH+j];
				}
			}
		}
		
		BufferedImage subimg = new BufferedImage(16*64, 9*64, BufferedImage.TYPE_INT_ARGB);
		subimg.setRGB(0, 0, 16*64, 9*64, rgbBackground, 0, 16*64);
		mainWindow.changeImage(subimg);
	}


	public void keyPressed(KeyEvent e) {
		final int KEY_W 	= 87;
		final int KEY_A 	= 65;
		final int KEY_S 	= 83;
		final int KEY_D 	= 68;
		final int KEY_LEFT 	= 37;
		final int KEY_UP 	= 38;
		final int KEY_RIGHT = 39;
		final int KEY_DOWN 	= 40;
		
		switch (e.getKeyCode()) {
		case KEY_W:
			if (!(shp.getCoordinates().getY()-5<0)) {
				shp.getCoordinates().setY(shp.getCoordinates().getY()-5);
			} else {
				shp.getCoordinates().setY(0);
			}			
			if (shp.getAnimation() == Ships.MOVE_LEFT || shp.getAnimation() == Ships.MOVE_LEFT_UP || shp.getAnimation() == Ships.MOVE_LEFT_DOWN) {
				shp.setAnimation(Ships.MOVE_LEFT_UP);
			} else {
				shp.setAnimation(Ships.MOVE_RIGHT_UP);
			}
			break;
		case KEY_A:
			if (!(shp.getCoordinates().getX()-5<0)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()-5);
			} else {
				shp.getCoordinates().setX(0);
				direction = CAMERA_DIRECTION_LEFT;
			}			
			shp.setAnimation(Ships.MOVE_LEFT);
			break;
		case KEY_S:
			if (!(shp.getCoordinates().getY()+5>WINDOW_HEIGHT-TileSet.TILE_HEIGHT)) {
				shp.getCoordinates().setY(shp.getCoordinates().getY()+5);
			} else {
				shp.getCoordinates().setY(WINDOW_HEIGHT-TileSet.TILE_HEIGHT);
			}
			if (shp.getAnimation() == Ships.MOVE_LEFT || shp.getAnimation() == Ships.MOVE_LEFT_UP || shp.getAnimation() == Ships.MOVE_LEFT_DOWN) {
				shp.setAnimation(Ships.MOVE_LEFT_DOWN);
			} else {
				shp.setAnimation(Ships.MOVE_RIGHT_DOWN);
			}
			break;
		case KEY_D:
			if (!(shp.getCoordinates().getX()+5>WINDOW_WIDTH-TileSet.TILE_WIDTH)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()+5);
			} else {
				shp.getCoordinates().setX(WINDOW_WIDTH-TileSet.TILE_WIDTH);
				direction = CAMERA_DIRECTION_RIGHT;
			}
			shp.setAnimation(Ships.MOVE_RIGHT);
			break;
		case KEY_LEFT:
			
			break;
		case KEY_UP:
			
			break;
		case KEY_RIGHT:
			
			break;
		case KEY_DOWN:
			
			break;

		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
