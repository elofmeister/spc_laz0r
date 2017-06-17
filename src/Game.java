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
	private gui.Window mainWindow = new gui.Window(GAME_TITLE);
	private String direction = CAMERA_DIRECTION_RIGHT;
	@SuppressWarnings("unused")
	private Player player = new Player("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	private Ships shp = new Ships(Ships.GLASSKANONE, 0, 1, 0, 0, 0, 0, 0);
	private BufferedImage bulletTileset = new TileSet("tiles/bulletsitems.png", 10, 10).getTileset();
	private List<Bullet> bullets = new List<Bullet>() {
		int size = 0;
		long timestamp = System.currentTimeMillis();
		Bullet[] array = null;
		
		@Override
		public <T> T[] toArray(T[] a) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Bullet> subList(int fromIndex, int toIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int size() {
			return size;
		}
		
		@Override
		public Bullet set(int index, Bullet element) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Bullet remove(int index) {
			if (!isEmpty() && index < size && index >= 0) {
				array[index].dispose();
				array[index] = null;
				Bullet[] tmp = array;
				System.out.println("Bullet "+size+" removed.");
				array = new Bullet[--size];
				for (int i = 0, j = 0; i < tmp.length; i++) {
					if (tmp[i] != null) {
						array[j++] = tmp[i];
					}
				}
			}
			return null;
		}
		
		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public ListIterator<Bullet> listIterator(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListIterator<Bullet> listIterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int lastIndexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Iterator<Bullet> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isEmpty() {
			boolean bretval = false;
			if (size == 0) {
				bretval = true;
			}
			return bretval;
		}
		
		@Override
		public int indexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Bullet get(int index) {
			Bullet retval = null;
			if (!isEmpty() && index < size && index >= 0) {
				retval = array[index];
			}
			return retval;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean contains(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void clear() {
			array = null;
			size = 0;			
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends Bullet> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean addAll(Collection<? extends Bullet> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void add(int index, Bullet element) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean add(Bullet e) {
			boolean bretval = false;
			if (timestamp+200<System.currentTimeMillis()) {
				Bullet[] tmp = null;
				if (!isEmpty()) {
					tmp = array;
				}
				array = new Bullet[size+1];
				if (!isEmpty()) {
					for (int i = 0; i < tmp.length; i++) {
						array[i] = tmp[i];
					}
				}
				array[size++] = e;
				System.out.println("Bullet "+size+" added.");
				timestamp = System.currentTimeMillis();
				bretval = true;
			}
			return bretval;
		}
	};
	private MenuManager menuManager = new MenuManager(mainWindow, shp, bullets, levels, activeLvl);
	
	public static void main(String[] arg) {
	    new Thread(new Game()).start();	// calling run method 
	}

	public void run() {
		//new init.SaveJSON("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0).save("save");
		long timestamp;
	    long oldTimestamp;
		menuManager.setActiveMenu(MenuManager.LOADING);
		new init.FileManager();
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset.png",10,10)));	// generating first (base) level
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset.png",10,10)));	// generating lvl 1
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset1.png",10,10)));	// generating lvl 2
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset2.png",10,10)));	// generating lvl 3
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset3.png",10,10)));	// generating lvl 4
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset4.png",10,10)));	// generating lvl 5
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset5.png",10,10)));	// generating lvl 6
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset6.png",10,10)));	// generating lvl 7
		levels.add(new Level(levelCnt++, new TileSet("tiles/tileset7.png",10,10)));	// generating lvl 8
		menuManager.setActiveMenu(MenuManager.MENU);
		mainWindow.getFrame().addKeyListener(this);
		while(true) {	
			oldTimestamp = System.currentTimeMillis();
		    update();
		    timestamp = System.currentTimeMillis();
		    if ( timestamp - oldTimestamp > LOOP_TIME ) {
		        continue;
		    }
		    handleKeys();
		    mainWindow.changeImage(menuManager.get());
		    timestamp = System.currentTimeMillis();
		    if ( timestamp - oldTimestamp <= LOOP_TIME ) {
		        try {
		        	Thread.sleep(LOOP_TIME - (timestamp-oldTimestamp) );
		        } catch (InterruptedException e) {
		        	System.err.println(e.getMessage());
		        }
		    }
	    	if ( direction == CAMERA_DIRECTION_RIGHT ) {
	    		levels.get(activeLvl).moveRight();
	    	} else {
	    		levels.get(activeLvl).moveLeft();
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
	
	private void handleKeys() {
		if (key_a) {
			if (!(shp.getCoordinates().getX()-5<0)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()-5);
			} else {
				shp.getCoordinates().setX(0);
				direction = CAMERA_DIRECTION_LEFT;
			}			
			shp.setAnimation(Ships.MOVE_LEFT);
		}
		if (key_d) {
			if (!(shp.getCoordinates().getX()+5>WINDOW_WIDTH-TileSet.TILE_WIDTH)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()+5);
			} else {
				shp.getCoordinates().setX(WINDOW_WIDTH-TileSet.TILE_WIDTH);
				direction = CAMERA_DIRECTION_RIGHT;
			}
			shp.setAnimation(Ships.MOVE_RIGHT);
		}
		if (key_w) {
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
		}
		if (key_s) {
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
		}
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		if (key_right) {
			bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_EAST, Math.abs(rnd.nextInt()%(Bullet.YELLOW+1)), bulletTileset));
		}
		if (key_left) {
			bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_WEST, Math.abs(rnd.nextInt()%(Bullet.YELLOW+1)), bulletTileset));
		}
		if (key_up) {
			bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_NORTH, Math.abs(rnd.nextInt()%(Bullet.YELLOW+1)), bulletTileset));
		}
		if (key_down) {
			bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_SOUTH, Math.abs(rnd.nextInt()%(Bullet.YELLOW+1)), bulletTileset));
		}
		if (!key_w) {
			if (shp.getAnimation() == Ships.MOVE_LEFT_UP) {
				shp.setAnimation(Ships.MOVE_LEFT);
			} else if (shp.getAnimation() == Ships.MOVE_RIGHT_UP) {
				shp.setAnimation(Ships.MOVE_RIGHT);
			}
		}
		if (!key_s) {
			if (shp.getAnimation() == Ships.MOVE_LEFT_DOWN) {
				shp.setAnimation(Ships.MOVE_LEFT);
			} else if (shp.getAnimation() == Ships.MOVE_RIGHT_DOWN) {
				shp.setAnimation(Ships.MOVE_RIGHT);
			}
		}
	}

	boolean key_w 		= false;
	boolean key_a 		= false;
	boolean key_s 		= false;
	boolean key_d 		= false;
	boolean key_left 	= false;
	boolean key_up 		= false;
	boolean key_right 	= false;
	boolean key_down 	= false;
	final int KEY_W 	= 87;
	final int KEY_A 	= 65;
	final int KEY_S 	= 83;
	final int KEY_D 	= 68;
	final int KEY_LEFT 	= 37;
	final int KEY_UP 	= 38;
	final int KEY_RIGHT = 39;
	final int KEY_DOWN 	= 40;
	
	public void keyPressed(KeyEvent e) {
		if (menuManager.getActiveMenu()==MenuManager.LEVEL) {
			switch (e.getKeyCode()) {
			case KEY_W:
				key_w = true;
				break;
			case KEY_A:
				key_a = true;
				break;
			case KEY_S:
				key_s = true;
				break;
			case KEY_D:
				key_d = true;
				break;
			case KEY_LEFT:
				key_left = true;
				break;
			case KEY_UP:
				key_up = true;
				break;
			case KEY_RIGHT:	
				key_right = true;
				break;
			case KEY_DOWN:
				key_down = true;
				break;
			case 77:
				int menu = menuManager.getActiveMenu();
				if (menu < MenuManager.LEVEL) {
					menu++;
				} else {
					menu = MenuManager.LOADING;
				}
				menuManager.setActiveMenu(menu);
				break;
			default:
				break;
			}
		}
		switch (e.getKeyCode()) {
		case 77:
			int menu = menuManager.getActiveMenu();
			if (menu < MenuManager.LEVEL) {
				menu++;
			} else {
				menu = MenuManager.LOADING;
			}
			menuManager.setActiveMenu(menu);
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e) {	
		switch (e.getKeyCode()) {
		case KEY_W:
			key_w = false;
			break;
		case KEY_A:
			key_a = false;
			break;
		case KEY_S:
			key_s = false;
			break;
		case KEY_D:
			key_d = false;
			break;
		case KEY_LEFT:
			key_left = false;
			break;
		case KEY_UP:
			key_up = false;
			break;
		case KEY_RIGHT:	
			key_right = false;
			break;
		case KEY_DOWN:
			key_down = false;
			break;
		default:
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
