import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.util.*;

import gui.Window;
import util.ConfigReader;

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
	
	public SoundPlayer bombSound = new SoundPlayer("sounds/bomb.wav");
	public SoundPlayer dmgSound = new SoundPlayer("sounds/dmg.wav");
	public SoundPlayer escSound = new SoundPlayer("sounds/esc.wav");
	public SoundPlayer explosionSound = new SoundPlayer("sounds/explosion.wav");
	public SoundPlayer fireSound = new SoundPlayer("sounds/fire.wav");
	public SoundPlayer healSound = new SoundPlayer("sounds/heal.wav");
	public SoundPlayer menuSound = new SoundPlayer("sounds/menu.wav");
	public SoundPlayer newSound = new SoundPlayer("sounds/new.wav");
	public SoundPlayer pewSound = new SoundPlayer("sounds/pew.wav");
	public SoundPlayer tpSound = new SoundPlayer("sounds/tp.wav");
	
	private int levelCnt = 0;	// number of generated levels
	private int activeLvl = 0;
	private int playerNumber = 1;
	private int levelProgress = 0;
	private List<Level> levels = new ArrayList<Level>();	// list of all generated levels
	private Window mainWindow;
	private String direction = CAMERA_DIRECTION_RIGHT;
	@SuppressWarnings("unused")
	private Player player = new Player("Horst");
	private Ships shp = new Ships(Ships.STANDARDO);
	private BufferedImage bulletTileset = new TileSet("tiles/bulletsitems.png", 10, 10).getTileset();
	private BufferedImage enemiesTileset = new TileSet("tiles/enemys.png", 10, 10).getTileset();
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Explosion> explosions = new ArrayList<Explosion>();
	private List<Chest> chests = new ArrayList<Chest>();
	private long bulletTimer = System.currentTimeMillis();
	private long enemyTimer = System.currentTimeMillis();
	
	private MenuManager menuManager;
	private CollisionDetector collisionDetector = new CollisionDetector();
	
	public static void main(String[] arg) {
	    new Thread(new Game()).start();	// calling run method 
	}
		
	public void run() {
		//new init.SaveJSON("Horst", 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0).save("save");
		long timestamp;
	    long oldTimestamp;
		new init.FileManager();
		player.setCash(300);
		readSoundConfig();
		mainWindow = new Window(GAME_TITLE);
		for (int i = 0; i <= 10; i++) {
			levels.add(new Level(levelCnt++, new TileSet("tiles/tileset"+(int)new ConfigReader("config.json").getBackground()+".png",10,10)));
		}
		menuManager = new MenuManager(this, mainWindow, player, shp, bullets, levels, enemies, explosions, chests);
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
	    		if (!levels.get(activeLvl).isEndReached()) {
	    			for (Chest chest : chests) {
						chest.moveLeft();
					}
				}
	    	} else {
	    		levels.get(activeLvl).moveLeft();
	    		if (!levels.get(activeLvl).isBeginReached()) {
	    			for (Chest chest : chests) {
						chest.moveRight();
					}
				}
	    	}
	    	if ( levels.get(activeLvl).isEndReached() && 
    				shp.getCoordinates().getX()>=12.5*TileSet.TILE_WIDTH && 
    				shp.getCoordinates().getX()<=13.5*TileSet.TILE_WIDTH && 
    				shp.getCoordinates().getY()>=3.5*TileSet.TILE_HEIGHT && 
    				shp.getCoordinates().getY()<=4.5*TileSet.TILE_HEIGHT
    			) {
    			nextLevel();
    		}
	    	if (activeLvl>0) {
	    		shp.prooveItemTimer();	
				if (levels.get(activeLvl).toggleWave()) {
					Random rnd = new Random();
					rnd.setSeed(System.currentTimeMillis());
					levels.get(activeLvl).addWave();
					for (int i = 0; i < levels.get(activeLvl).getEnemyAmount(); i++) {
						enemies.add(new Enemy(Math.abs(rnd.nextInt()%2)+1, levels.get(activeLvl), enemiesTileset, rnd));
					}
					if (levels.get(activeLvl).getWaveCnt() == (int) (levels.get(activeLvl).getWaveAmount()/2)) {
						enemies.add(new Enemy(Enemy.SPECIAL_CLASS, levels.get(activeLvl), enemiesTileset, rnd));
					}
					if (levels.get(activeLvl).getWaveCnt() == levels.get(activeLvl).getWaveAmount()) {
						enemies.add(new Enemy(Enemy.BOZZ_CLASS, levels.get(activeLvl), enemiesTileset, rnd));
					}
				}
				for (int i = 0; i < enemies.size(); i++) {
					if (enemyTimer + Bullet.DEFAULT_FIRESPEED * 2 < System.currentTimeMillis() && enemies.get(i).isInView()) {
						bullets.add(new Bullet(enemies.get(i).getCoordinates(), enemies.get(i).getDirection(), enemies.get(i).getEnemyelement(), bulletTileset, enemies.get(i).getIdentity()));
					}
				}
				if (enemyTimer + Bullet.DEFAULT_FIRESPEED * 2 < System.currentTimeMillis()) {
					enemyTimer = System.currentTimeMillis();
				}
				for (int i = 0; i < bullets.size(); i++) {
					if (collisionDetector.isCollide(bullets.get(i), shp, enemies)) {
						try {
							new DamageCalculator(collisionDetector.getLastCollsion(), player, enemies.get(collisionDetector.getEnemy()), shp, fireSound);
						} catch (Exception e) {
							System.err.println("DamageCalculatorException");
						}
						bullets.remove(i--);
						if (shp.getlife() > 0 && collisionDetector.getLastCollsion() != Player.ID && enemies.get(collisionDetector.getEnemy()).getEnemylife() <= 0) {
							/*
							 * Enemy killed
							 */
							player.setXp(enemies.get(collisionDetector.getEnemy()).getEnemyxp());
							explosions.add(new Explosion(enemies.get(collisionDetector.getEnemy()).getCoordinates(), bulletTileset, explosionSound));
							chests.add(new DropCalculator(new Chest(bulletTileset, enemies.get(collisionDetector.getEnemy()).getCoordinates()), enemies.get(collisionDetector.getEnemy()).getDropchance(), activeLvl, enemies.get(collisionDetector.getEnemy()).getEnemycash()).getChest());
							enemies.remove(collisionDetector.getEnemy());
							System.out.println("Level: "+player.getLvl()+" XP: "+player.getXp() + " OldXP: " + player.getoldXP() +" ("+new ExperienceTest(player.getLvl(), player.getoldXP(), player.getXp()).getPercentage()+"%)");
						}
						if (collisionDetector.getLastCollsion() == Player.ID) {
							dmgSound.play();
						}
					}
				}
			}
		}
	}
	
	public void load(int levelProgress) {
		this.levelProgress = levelProgress;
	}
	
	public void nextLevel() {
		if (activeLvl == 0 && levelProgress > activeLvl + 1) {
			levels.get(activeLvl).restart();
			tpSound.play();
			shp.respawn();
			bullets.clear();
			enemies.clear();
			explosions.clear();
			chests.clear();
			activeLvl = levelProgress;
			direction = "right";
		} else if (activeLvl<levels.size()-1) {
			levels.get(activeLvl).restart();
			tpSound.play();
			shp.respawn();
			bullets.clear();
			enemies.clear();
			explosions.clear();
			chests.clear();
			activeLvl++;
			if (activeLvl > levelProgress) {
				levelProgress = activeLvl;
			}
			direction = "right";
		} else {
			portToBase();
		}
	}
	
	public void portToBase() {
		levels.get(activeLvl).restart();
		shp.respawn();
		tpSound.play();
		bullets.clear();
		enemies.clear();
		explosions.clear();
		chests.clear();
		activeLvl = 0;
		direction = "right";
		shp.setlife(0);
	}
	
	private void update() {
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void readSoundConfig() {
		boolean isEnabled = new ConfigReader("config.json").isSound();
		bombSound.setEnabled(isEnabled);
		dmgSound.setEnabled(isEnabled);
		escSound.setEnabled(isEnabled);
		explosionSound.setEnabled(isEnabled);
		fireSound.setEnabled(isEnabled);
		healSound.setEnabled(isEnabled);
		menuSound.setEnabled(isEnabled);
		newSound.setEnabled(isEnabled);
		pewSound.setEnabled(isEnabled);
		tpSound.setEnabled(isEnabled);
	}
	
	private void handleKeys() {
		if (key_a) {
			if (!(shp.getCoordinates().getX()-shp.getshipspeed()<0)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()-shp.getshipspeed());
			} else {
				shp.getCoordinates().setX(0);
				direction = CAMERA_DIRECTION_LEFT;
			}			
			shp.setAnimation(Ships.MOVE_LEFT);
		}
		if (key_d) {
			if (!(shp.getCoordinates().getX()+shp.getshipspeed()>WINDOW_WIDTH-TileSet.TILE_WIDTH)) {
				shp.getCoordinates().setX(shp.getCoordinates().getX()+shp.getshipspeed());
			} else {
				shp.getCoordinates().setX(WINDOW_WIDTH-TileSet.TILE_WIDTH);
				direction = CAMERA_DIRECTION_RIGHT;
			}
			shp.setAnimation(Ships.MOVE_RIGHT);
		}
		if (key_w) {
			if (!(shp.getCoordinates().getY()-shp.getshipspeed()<0)) {
				shp.getCoordinates().setY(shp.getCoordinates().getY()-shp.getshipspeed());
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
			if (!(shp.getCoordinates().getY()+shp.getshipspeed()>WINDOW_HEIGHT-TileSet.TILE_HEIGHT)) {
				shp.getCoordinates().setY(shp.getCoordinates().getY()+shp.getshipspeed());
			} else {
				shp.getCoordinates().setY(WINDOW_HEIGHT-TileSet.TILE_HEIGHT);
			}
			if (shp.getAnimation() == Ships.MOVE_LEFT || shp.getAnimation() == Ships.MOVE_LEFT_UP || shp.getAnimation() == Ships.MOVE_LEFT_DOWN) {
				shp.setAnimation(Ships.MOVE_LEFT_DOWN);
			} else {
				shp.setAnimation(Ships.MOVE_RIGHT_DOWN);
			}
		}
		if (key_right) {
			if (bulletTimer+Bullet.DEFAULT_FIRESPEED*shp.getfirespeed()<System.currentTimeMillis()) {
				bulletTimer = System.currentTimeMillis();
				pewSound.play();
				bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_EAST, player.getColor(), bulletTileset, Player.ID));	
			}
		}
		if (key_left) {
			if (bulletTimer+Bullet.DEFAULT_FIRESPEED*shp.getfirespeed()<System.currentTimeMillis()) {
				bulletTimer = System.currentTimeMillis();
				pewSound.play();
				bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_WEST, player.getColor(), bulletTileset, Player.ID));	
			}
		}
		if (key_up) {
			if (bulletTimer+Bullet.DEFAULT_FIRESPEED*shp.getfirespeed()<System.currentTimeMillis()) {
				bulletTimer = System.currentTimeMillis();
				pewSound.play();
				bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_NORTH, player.getColor(), bulletTileset, Player.ID));	
			}
		}
		if (key_down) {
			if (bulletTimer+Bullet.DEFAULT_FIRESPEED*shp.getfirespeed()<System.currentTimeMillis()) {
				bulletTimer = System.currentTimeMillis();
				pewSound.play();
				bullets.add(new Bullet(shp.getCoordinates(), Bullet.MOVE_SOUTH, player.getColor(), bulletTileset, Player.ID));	
			}
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
	boolean key_1    	= false;
	boolean key_2     	= false;
	boolean key_3     	= false;
	boolean key_4     	= false;
	final int KEY_W 	= 87;
	final int KEY_A 	= 65;
	final int KEY_S 	= 83;
	final int KEY_D 	= 68;
	final int KEY_LEFT 	= 37;
	final int KEY_UP 	= 38;
	final int KEY_RIGHT = 39;
	final int KEY_DOWN 	= 40;
	final int KEY_1     = 49;
	final int KEY_2     = 50;
	final int KEY_3     = 51;
	final int KEY_4     = 52;
	
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
			case KEY_1:
				if (!key_1) {
					if (shp.getbonusslots(0)>=1){
						shp.setlife(1);
						shp.setbonusslots(0, shp.getbonusslots(0)-1);
						healSound.play();
					}
				}
				key_1 = true;
				break;
			case KEY_2:
				if (!key_2) {
					if (shp.getbonusslots(1)>=1){
						shp.setinvincible();
						shp.setbonusslots(1, shp.getbonusslots(1)-1);
					}
				}				
				key_2 = true;
				break;
			case KEY_3:
				if (!key_3) {
					if (shp.getbonusslots(2)>=1){
						for (int i = 0; i < enemies.size(); i++) {
							if (enemies.get(i).isInView()) {			
								new DamageCalculator(enemies.get(i).getIdentity(), player, enemies.get(i), shp, fireSound);
								   if(enemies.get(i).getEnemylife() <= 0){
									   player.setXp(enemies.get(i).getEnemyxp());   					   				
									   explosions.add(new Explosion(enemies.get(i).getCoordinates(), bulletTileset, explosionSound));
									   enemies.remove(enemies.get(i--));
								   }
							}
						}
						shp.setbonusslots(2, shp.getbonusslots(2)-1);
						bombSound.play();
					}
				}
				key_3 = true;
				break;
			case KEY_4:
				if (!key_4) {
					if (shp.getbonusslots(3)>=1){
						portToBase();
						shp.setbonusslots(3, shp.getbonusslots(3)-1);
					}	
				}
				key_4 = true;
				break;
			default:
				break;
			}
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
		case KEY_1:
			key_1 = false;
			break;
		case KEY_2:
			key_2 = false;
			break;
		case KEY_3:
			key_3 = false;
			break;
		case KEY_4:
			key_4 = false;
			break;
		default:
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public int getActiveLevel() {
		return activeLvl;
	}
	
	public int getLevelProgress() {
		return levelProgress;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public void setPlayerNumber(int num) {
		playerNumber = num;
	}
}
