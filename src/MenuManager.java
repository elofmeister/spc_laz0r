import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import gui.Window;

import util.ConfigReader;
import util.ConfigWriter;

public class MenuManager implements KeyListener {

	public final static int LOADING		= 0;
	public final static int MENU		= 1;
	public final static int NEW_GAME	= 2;
	public final static int LOAD_GAME	= 3;
	public final static int EXTRA		= 4;
	public final static int LEVEL		= 5;
		
	private Game game;
	private Window window;
	private Ships shp;
	private List<Bullet> bullets;
	private List<Level> levels;
	private List<Enemy> enemies;
	private List<Explosion> explosions;
	private int loading;

	private boolean fullscreen = true;
	private boolean sound = false;
	private int background = 1;
	
	private int menuPos = 1;
	private int menuPos2 = 0;
	
	private int activeMenu = LOADING;
	
	private final int KEY_W 		= 87;
	private final int KEY_A 		= 65;
	private final int KEY_S 		= 83;
	private final int KEY_D 		= 68;
	private final int KEY_LEFT 		= 37;
	private final int KEY_UP 		= 38;
	private final int KEY_RIGHT 	= 39;
	private final int KEY_DOWN 		= 40;
	private final int KEY_ENTER		= 10;
	private final int KEY_ESC		= 27;
	private final int KEY_BACKSPACE	= 8;
	
	public MenuManager(Game game, Window window, Ships shp, List<Bullet> bullets, List<Level> levels, List<Enemy> enemies, List<Explosion> explosions) {
		this.game = game;
		this.window = window;
		this.shp = shp;
		this.bullets = bullets;
		this.enemies = enemies;
		this.levels = levels;
		this.explosions = explosions;
		this.window.getFrame().addKeyListener(this);
		ConfigReader cr = new ConfigReader("config.json");
		fullscreen = cr.isFullscreen();
		sound = cr.isSound();
		background = (int) cr.getBackground();
	}
	
	private BufferedImage getLoading() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/loading" + loading + ".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getMenu() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/menu" + menuPos + ".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getNewGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/choose" + menuPos + menuPos2 + ".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getLoadGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/load" + menuPos + ".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getSaveGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/save" + menuPos + ".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getExtra() {
		BufferedImage retval = null;
		try {
			int fs = 0;
			if (fullscreen) {
				fs = 1;
			}
			int s = 0;
			if (sound) {
				s = 1;
			}
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/extra"+fs+s+menuPos+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getLevel() {
		int[] rgbBackground = new int[Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH * Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT];
		levels.get(game.getActiveLevel()).getSubimage().getRGB(0, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH, Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT, rgbBackground, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH);

		final int TRANSPARANCY 	= 16777215;
		final int BLACK			= -16777216;
		
		if (!enemies.isEmpty()) {
			for (int n = 0; n < enemies.size(); n++) {
				if (enemies.get(n).isInView()) {
					int[] rgbEnemy = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
					enemies.get(n).getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbEnemy, 0, TileSet.TILE_WIDTH);
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbEnemy[i * TileSet.TILE_WIDTH + j] != TRANSPARANCY && enemies.size() > n) {
								rgbBackground[(enemies.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + enemies.get(n).getCoordinates().getX() + j] = rgbEnemy[i * TileSet.TILE_WIDTH + j];
							}
						}
					}		
				}
			}
		}		
		if (!bullets.isEmpty()) {
			for (int n = 0; n < bullets.size(); n++) {
				int[] rgbBullet = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
				bullets.get(n).getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbBullet, 0, TileSet.TILE_WIDTH);
				if (bullets.get(n).move(Bullet.DEFAULT_BULLETSPEED)) {
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbBullet[i * TileSet.TILE_WIDTH + j] != BLACK && bullets.size() > n) {
								rgbBackground[(bullets.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + bullets.get(n).getCoordinates().getX() + j] = rgbBullet[i * TileSet.TILE_WIDTH + j];
							}
						}
					}
				} else {
					bullets.remove(n--);
				}				
			}
		}
		if (!explosions.isEmpty()) {
			for (int n = 0; n < explosions.size(); n++) {
				int[] rgbExplosion = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
				explosions.get(n).getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbExplosion, 0, TileSet.TILE_WIDTH);
				if (!explosions.get(n).isFinished()) {
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbExplosion[i * TileSet.TILE_WIDTH + j] != BLACK && explosions.size() > n) {
								rgbBackground[(explosions.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + explosions.get(n).getCoordinates().getX() + j] = rgbExplosion[i * TileSet.TILE_WIDTH + j];
							}
						}
					}
				} else {
					explosions.remove(n--);
				}				
			}
		}
		int[] rgbShip = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
		shp.getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbShip, 0, TileSet.TILE_WIDTH);
		for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
				if (rgbShip[i * TileSet.TILE_WIDTH + j] != TRANSPARANCY) {
					rgbBackground[(shp.getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + shp.getCoordinates().getX() + j] = rgbShip[i * TileSet.TILE_WIDTH + j];
				}
			}
		}
		BufferedImage subimg = new BufferedImage(Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH, Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		subimg.setRGB(0, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH, Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT, rgbBackground, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH);
		return subimg;
	}
		
	public void setActiveMenu(int menu) {
		activeMenu = menu;
		switch (activeMenu) {
		case LOADING:
			Random rnd = new Random();
		    rnd.setSeed(System.currentTimeMillis());
		    loading = Math.abs(rnd.nextInt()%3)+1;
			break;

		default:
			break;
		}
	}
	
	public BufferedImage get() {
		BufferedImage retval = null;
		switch (activeMenu) {
		case LOADING:
			retval = getLoading();
			break;
		case MENU:
			retval = getMenu();
			break;
		case NEW_GAME:
			retval = getNewGame();
			break;
		case LOAD_GAME:
			retval = getLoadGame();
			break;
		case EXTRA:
			retval = getExtra();
			break;
		case LEVEL:
			retval = getLevel();
			break;

		default:
			break;
		}
		return retval;
	}
	
	public int getActiveMenu() {
		return activeMenu;
	}

	private boolean keyPressed = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keyPressed) {
			if (getActiveMenu() == MENU) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 2) {
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 4) {
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case 2:
					case 3:
					case 4:
						menuPos+=3;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == NEW_GAME) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 1) {
						menuPos2 = 2;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 3) {
						menuPos2 = 1;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case Ships.STANDARDO:
					case Ships.RUMPLER:
					case Ships.GLASSCANNON:
						menuPos2 = 3;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;
				
				default:
					break;
				}
			}
			else if (getActiveMenu() == LOAD_GAME) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 2) {
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 4) {
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case 2:
					case 3:
					case 4:
						menuPos+=3;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == EXTRA) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 1) {
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 9) {
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case 1:
						if (fullscreen) {
							fullscreen = false;
						} else {
							fullscreen = true;
						}
						new ConfigWriter(fullscreen, sound, background);
						game.menuSound.play();
						break;
					case 2:
						if (sound) {
							sound = false;
						} else {
							sound = true;
						}
						new ConfigWriter(fullscreen, sound, background);
						game.readSoundConfig();
						game.menuSound.play();
						break;

					default:
						if (menuPos >= 3 && menuPos <= 9) {
							background = menuPos-2;
						}
						new ConfigWriter(fullscreen, sound, background);
						for (int i = 1; i < levels.size(); i++) {
							levels.get(i).updateImage(new TileSet("tiles/tileset" + (int)new ConfigReader("config.json").getBackground() + ".png", 10, 10));
						}
						game.menuSound.play();
						break;
					}
					break;

				default:
					break;
				}
			}
			keyPressed = true;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (keyPressed) {
			if (getActiveMenu() == MENU) {
				switch (e.getKeyCode()) {
				case KEY_ENTER:
					switch (menuPos) {
					case 5:						
						activeMenu = NEW_GAME;
						menuPos = 1;
						break;
					case 6:
						activeMenu = LOAD_GAME;
						menuPos = 1;
						break;
					case 7:
						activeMenu = EXTRA;
						menuPos = 0;
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == NEW_GAME) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 1) {
						menuPos2 = 0;
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 3) {
						menuPos2 = 0;
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case Ships.STANDARDO:
					case Ships.RUMPLER:
					case Ships.GLASSCANNON:
						shp.setshipclass(menuPos);
						activeMenu = LEVEL;
						menuPos = 1;
						game.newGame();
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					game.escSound.play();
					break;
				
				default:
					break;
				}
			}
			else if (getActiveMenu() == LOAD_GAME) {
				switch (e.getKeyCode()) {
				case KEY_ENTER:
					switch (menuPos) {
					case 5:
					case 6:
					case 7:
						activeMenu = LEVEL;
						menuPos = 1;
						game.newGame();
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					game.escSound.play();
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == EXTRA) {
				switch (e.getKeyCode()) {
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					menuPos = 1;
					game.escSound.play();
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == LEVEL) {
				switch (e.getKeyCode()) {
				case KEY_ESC:
					activeMenu = MENU;
					game.escSound.play();
					break;

				default:
					break;
				}
			}
			keyPressed = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
