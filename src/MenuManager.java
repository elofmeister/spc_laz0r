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
import util.SaveReader;
import util.SaveWriter;

public class MenuManager implements KeyListener {

	public final static int LOADING		= 0;
	public final static int MENU		= 1;
	public final static int NEW_GAME	= 2;
	public final static int LOAD_GAME	= 3;
	public final static int SAVE_GAME	= 4;
	public final static int EXTRA		= 5;
	public final static int LEVEL		= 6;
	public final static int SKILL		= 7;
		
	private Game game;
	private Window window;
	private Ships shp;
	private Player player;
	private List<Bullet> bullets;
	private List<Level> levels;
	private List<Enemy> enemies;
	private List<Explosion> explosions;
	private List<Chest> chests;
	private GuiBar guiBar = new GuiBar();
	private int loading;
	private CollisionDetector shopDetector = new CollisionDetector();
	private Shop shop;
	private FontManager font = new FontManager();

	private boolean waveIncomming = false;
	private long waveIncommingTimestamp = System.currentTimeMillis();
	private int waveIncommingCnt = 0;
	
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
	
	public void toggleWave() {
		waveIncomming = true;
		waveIncommingCnt = 0;
		waveIncommingTimestamp = System.currentTimeMillis();
	}
	
	public MenuManager(Game game, Window window, Player player, Ships shp, List<Bullet> bullets, List<Level> levels, List<Enemy> enemies, List<Explosion> explosions, List<Chest> chests) {
		this.game = game;
		this.window = window;
		this.player = player;
		this.shp = shp;
		this.bullets = bullets;
		this.enemies = enemies;
		this.levels = levels;
		this.explosions = explosions;
		this.chests = chests;
		this.window.getFrame().addKeyListener(this);
		ConfigReader cr = new ConfigReader("config.json");
		fullscreen = cr.isFullscreen();
		sound = cr.isSound();
		background = (int) cr.getBackground();
		shop = new Shop(player, shp, game);
		this.window.getFrame().addKeyListener(shop);
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
	
	private BufferedImage getSkill() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/skill" + menuPos + ".png"));
		} catch (IOException e) {
		}
		int[] rgbBackground = new int[Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH * Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT];
		retval.getRGB(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, rgbBackground, 0, Game.WINDOW_WIDTH);
		
		// SMALL
		int number;
		int size = FontManager.SMALL;
		int[] skills = {player.getAgl(), player.getCritdmg(), player.getCritprb(), player.getLaser(), player.getIce(), player.getAcid(), player.getEmp()};
		// LAST NUMBER
		for (int n = 0; n < 7; n++) {
			number = skills[n]%10;
			int[] rgbNum = new int[font.getNumber(size, number).getWidth() * font.getNumber(size, number).getHeight()];
			font.getNumber(size, number).getRGB(0, 0, font.getNumber(size, number).getWidth(), font.getNumber(size, number).getHeight(), rgbNum, 0, font.getNumber(size, number).getWidth());
			for (int i = 0; i < font.getNumber(size, number).getHeight(); i++) {
				for (int j = 0; j < font.getNumber(size, number).getWidth(); j++) {
					rgbBackground[(i + 231 + (int) (n * 28.5)) * Game.WINDOW_WIDTH + j + 576] = rgbNum[i * font.getNumber(size, number).getWidth() + j];
				}
			}
		}
		// MIDDLE NUMBER
		for (int n = 0; n < 7; n++) {
			if (skills[n] > 9) {
				number = (skills[n]-skills[n]%10)/10%10;
				int[] rgbNum = new int[font.getNumber(size, number).getWidth() * font.getNumber(size, number).getHeight()];
				font.getNumber(size, number).getRGB(0, 0, font.getNumber(size, number).getWidth(), font.getNumber(size, number).getHeight(), rgbNum, 0, font.getNumber(size, number).getWidth());
				for (int i = 0; i < font.getNumber(size, number).getHeight(); i++) {
					for (int j = 0; j < font.getNumber(size, number).getWidth(); j++) {
						rgbBackground[(i + 231 + (int) (n * 28.5)) * Game.WINDOW_WIDTH + j + 576 - font.getNumber(size, number).getWidth()] = rgbNum[i * font.getNumber(size, number).getWidth() + j];
					}
				}		
			}			
		}
		// LAST NUMBER
		for (int n = 0; n < 7; n++) {
			if (skills[n] > 99) {
				number = (skills[n]-10*(skills[n]-skills[n]%10)/10%10-skills[n]%10)/100;
				int[] rgbNum = new int[font.getNumber(size, number).getWidth() * font.getNumber(size, number).getHeight()];
				font.getNumber(size, number).getRGB(0, 0, font.getNumber(size, number).getWidth(), font.getNumber(size, number).getHeight(), rgbNum, 0, font.getNumber(size, number).getWidth());
				for (int i = 0; i < font.getNumber(size, number).getHeight(); i++) {
					for (int j = 0; j < font.getNumber(size, number).getWidth(); j++) {
						rgbBackground[(i + 231 + (int) (n * 28.5)) * Game.WINDOW_WIDTH + j + 576 - 2 * font.getNumber(size, number).getWidth()] = rgbNum[i * font.getNumber(size, number).getWidth() + j];
					}
				}
			}					
		}
		// CASH
		number = player.getCash();
		if (number > 999999999) {
			number = 999999999;
		}
		for (int n = 0; n < 9; n++) {
			if (n > 0 && number == 0) {
				break;
			}
			int[] rgbNum = new int[font.getNumber(size, number%10).getWidth() * font.getNumber(size, number%10).getHeight()];
			font.getNumber(size, number%10).getRGB(0, 0, font.getNumber(size, number%10).getWidth(), font.getNumber(size, number%10).getHeight(), rgbNum, 0, font.getNumber(size, number%10).getWidth());
			for (int i = 0; i < font.getNumber(size, number%10).getHeight(); i++) {
				for (int j = 0; j < font.getNumber(size, number%10).getWidth(); j++) {
					rgbBackground[(i + 145) * Game.WINDOW_WIDTH + j + 912 - n * font.getNumber(size, number%10).getWidth()] = rgbNum[i * font.getNumber(size, number%10).getWidth() + j];
				}
			}
			number-=number%10;
			number/=10;
		}
		// CASH
		number = player.getLvl();
		for (int n = 0; n < 3; n++) {
			if (n > 0 && number == 0) {
				break;
			}
			int[] rgbNum = new int[font.getNumber(size, number%10).getWidth() * font.getNumber(size, number%10).getHeight()];
			font.getNumber(size, number%10).getRGB(0, 0, font.getNumber(size, number%10).getWidth(), font.getNumber(size, number%10).getHeight(), rgbNum, 0, font.getNumber(size, number%10).getWidth());
			for (int i = 0; i < font.getNumber(size, number%10).getHeight(); i++) {
				for (int j = 0; j < font.getNumber(size, number%10).getWidth(); j++) {
					rgbBackground[(i + 145) * Game.WINDOW_WIDTH + j + 647 - n * font.getNumber(size, number%10).getWidth()] = rgbNum[i * font.getNumber(size, number%10).getWidth() + j];
				}
			}
			number-=number%10;
			number/=10;
		}
		// SKILLPTS
		number = player.getSkillpts();
		size = FontManager.BIG;
		for (int n = 0; n < 3; n++) {
			if (n > 0 && number == 0) {
				break;
			}
			int[] rgbNum = new int[font.getNumber(size, number%10).getWidth() * font.getNumber(size, number%10).getHeight()];
			font.getNumber(size, number%10).getRGB(0, 0, font.getNumber(size, number%10).getWidth(), font.getNumber(size, number%10).getHeight(), rgbNum, 0, font.getNumber(size, number%10).getWidth());
			for (int i = 0; i < font.getNumber(size, number%10).getHeight(); i++) {
				for (int j = 0; j < font.getNumber(size, number%10).getWidth(); j++) {
					rgbBackground[(i + 288) * Game.WINDOW_WIDTH + j + 190 - n * font.getNumber(size, number%10).getWidth()] = rgbNum[i * font.getNumber(size, number%10).getWidth() + j];
				}
			}
			number-=number%10;
			number/=10;
		}
		// PLAYER NAME
		int[] rgbNum = new int[font.getPlayer(game.getPlayerNumber()).getWidth() * font.getPlayer(game.getPlayerNumber()).getHeight()];
		font.getPlayer(game.getPlayerNumber()).getRGB(0, 0, font.getPlayer(game.getPlayerNumber()).getWidth(), font.getPlayer(game.getPlayerNumber()).getHeight(), rgbNum, 0, font.getPlayer(game.getPlayerNumber()).getWidth());
		for (int i = 0; i < font.getPlayer(game.getPlayerNumber()).getHeight(); i++) {
			for (int j = 0; j < font.getPlayer(game.getPlayerNumber()).getWidth(); j++) {
				rgbBackground[(i + 145) * Game.WINDOW_WIDTH + j + 194] = rgbNum[i * font.getPlayer(game.getPlayerNumber()).getWidth() + j];
			}
		}
	
		retval.setRGB(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, rgbBackground, 0, Game.WINDOW_WIDTH);
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
		if (activeMenu == LEVEL && game.getActiveLevel() == 0) {
			shop.setActivated(true);
			if (waveIncomming) {
				waveIncomming = false;
			}
		} else {
			shop.setActivated(false);
		}
		int[] rgbBackground = new int[Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH * Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT];
		levels.get(game.getActiveLevel()).getSubimage().getRGB(0, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH, Game.WINDOW_HEIGHT_TILE_NUM * TileSet.TILE_HEIGHT, rgbBackground, 0, Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH);

		final int TRANSPARANCY 	= 16777215;
		final int BLACK			= -16777216;
		
		int[] rgbShop = new int[Shop.SHOP_HEIGHT * Shop.SHOP_WIDTH];
		if (shop.isAtShop(Shop.SHOP_1)) {
			shop.getShopImage(Shop.SHOP_1).getRGB(0, 0, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT, rgbShop, 0, Shop.SHOP_WIDTH);
		} else if (shop.isAtShop(Shop.SHOP_2)) {
			shop.getShopImage(Shop.SHOP_2).getRGB(0, 0, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT, rgbShop, 0, Shop.SHOP_WIDTH);
		} else if (shop.isAtShop(Shop.SHOP_3)) {
			shop.getShopImage(Shop.SHOP_3).getRGB(0, 0, Shop.SHOP_WIDTH, Shop.SHOP_HEIGHT, rgbShop, 0, Shop.SHOP_WIDTH);
		}
		if (shop.isAtShop(Shop.SHOP_1) || shop.isAtShop(Shop.SHOP_2) || shop.isAtShop(Shop.SHOP_3)) {
			for (int i = 0; i < Shop.SHOP_HEIGHT; i++) {
				for (int j = 0; j < Shop.SHOP_WIDTH; j++) {
					try {
						rgbBackground[i * Game.WINDOW_WIDTH + j + Game.WINDOW_WIDTH - Shop.SHOP_WIDTH] = rgbShop[i * Shop.SHOP_WIDTH + j];
					} catch (Exception e) {
						System.err.println("ShopRenderException");
					}
				}
			}
		}		
		
		guiBar.setXP(new ExperienceTest(player.getLvl(), player.getoldXP(), player.getXp()).getPercentage());
		guiBar.setBonusslots(shp.getbonusslots(0), shp.getbonusslots(1), shp.getbonusslots(2), shp.getbonusslots(3));
		guiBar.setCurse(shp.getcurse());
		if (shp.getlife() < 0) {
			guiBar = new GuiBar();
			game.portToBase();
		} else {
			guiBar.setLife((float) shp.getlife() / (float) shp.getmaxlife());
		}
		int[] rgbGuiBar = new int[Game.WINDOW_WIDTH * TileSet.TILE_HEIGHT];
		guiBar.getImage().getRGB(0, 0, Game.WINDOW_WIDTH, TileSet.TILE_HEIGHT, rgbGuiBar, 0, Game.WINDOW_WIDTH);
		for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < Game.WINDOW_WIDTH; j++) {
				try {
					rgbBackground[(Game.WINDOW_HEIGHT - TileSet.TILE_HEIGHT + i) * Game.WINDOW_WIDTH + j] = rgbGuiBar[i * Game.WINDOW_WIDTH + j];
				} catch (Exception e) {
					System.err.println("GuiBarRenderException");
				}
			}
		}
		
		for (int i = 0; i < chests.size(); i++) {
			if (chests.get(i).isInRange(shp.getCoordinates())) {
				while (!chests.get(i).isEmpty()) {
					Item item = chests.get(i).getNextItem();
					if (item.getCategory() > 6) {
						player.setCash(item.getItemvalue());
					} else {
						shp.addItem(item);
					}
				}
				chests.remove(i);
				game.menuSound.play();
			}
		}
		
		if (!chests.isEmpty()) {
			for (int n = 0; n < chests.size(); n++) {
				int[] rgbChest = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
				chests.get(n).getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbChest, 0, TileSet.TILE_WIDTH);
				if (chests.get(n).isInView()) {
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbChest[i * TileSet.TILE_WIDTH + j] != BLACK && chests.size() > n) {
								try {
									rgbBackground[(chests.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + chests.get(n).getCoordinates().getX() + j] = rgbChest[i * TileSet.TILE_WIDTH + j];
								} catch (Exception e) {
									System.err.println("ChestRenderException");
								}
							}
						}
					}
				}
			}
		}
		
		if (!enemies.isEmpty()) {
			for (int n = 0; n < enemies.size(); n++) {
				enemies.get(n).move();
				if (enemies.get(n).isInView()) {
					int[] rgbEnemy = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
					enemies.get(n).getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbEnemy, 0, TileSet.TILE_WIDTH);
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbEnemy[i * TileSet.TILE_WIDTH + j] != TRANSPARANCY && enemies.size() > n) {
								try {
									rgbBackground[(enemies.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + enemies.get(n).getCoordinates().getX() + j] = rgbEnemy[i * TileSet.TILE_WIDTH + j];
								} catch (Exception e) {
									System.err.println("EnemyRenderException");
								}
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
								try {
									rgbBackground[(bullets.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + bullets.get(n).getCoordinates().getX() + j] = rgbBullet[i * TileSet.TILE_WIDTH + j];
								} catch (Exception e) {
									System.err.println("BulletRenderException");
								}
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
								try {
									rgbBackground[(explosions.get(n).getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + explosions.get(n).getCoordinates().getX() + j] = rgbExplosion[i * TileSet.TILE_WIDTH + j];
								} catch (Exception e) {
									System.err.println("ExplosionRenderException");
								}
							}
						}
					}
				} else {
					explosions.remove(n--);
				}				
			}
		}
		if (shp.getCrit().isCrit()) {
			if (shp.getCrit().getCritTimestamp() + 200 > System.currentTimeMillis()) {
				int[] rgbCrit = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
				font.getCrit(shp.getCrit().getCritCnt()).getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbCrit, 0, TileSet.TILE_WIDTH);
				for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
					for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
						if (rgbCrit[i * TileSet.TILE_WIDTH + j] != BLACK) {
							rgbBackground[(i + shp.getCrit().getCoordinates().getY()) * Game.WINDOW_WIDTH + j + shp.getCrit().getCoordinates().getX()] = rgbCrit[i * TileSet.TILE_WIDTH + j];							
						}
					}
				}
			} else {
				if (shp.getCrit().getCritCnt() >= 3) {
					shp.getCrit().disableCrit();
				} else {
					shp.getCrit().setCritTimestamp();
					shp.getCrit().setCritCnt();
				}				
			}
		}
		int[] rgbShip = new int[TileSet.TILE_HEIGHT * TileSet.TILE_WIDTH];
		shp.getImage().getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbShip, 0, TileSet.TILE_WIDTH);
		for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
				if (rgbShip[i * TileSet.TILE_WIDTH + j] != TRANSPARANCY) {
					try {
						rgbBackground[(shp.getCoordinates().getY() + i) * Game.WINDOW_WIDTH_TILE_NUM * TileSet.TILE_WIDTH + shp.getCoordinates().getX() + j] = rgbShip[i * TileSet.TILE_WIDTH + j];
					} catch (Exception e) {
						System.err.println("ShipRenderException");
					}
				}
			}
		}
		if (waveIncomming) {
			if (waveIncommingCnt > 7) {
				waveIncommingCnt = 0;
				waveIncomming = false;
			} else {
				BufferedImage img = font.getWaveIncomming(waveIncommingCnt);
				int height = img.getHeight();
				int width = img.getWidth();
				int[] rgbWaveIncomming = new int[width * height];
				img.getRGB(0, 0, width, height, rgbWaveIncomming, 0, width);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (rgbWaveIncomming[i * width + j] != BLACK) {
							try {
								rgbBackground[(3 * height + i) * Game.WINDOW_WIDTH + 6 * TileSet.TILE_WIDTH + j] = rgbWaveIncomming[i * width + j];
							} catch (Exception e) {
								System.err.println("WaveIncommingRenderException");
							}
						}
					}
				}
				if (waveIncommingTimestamp + 400 < System.currentTimeMillis()) {
					waveIncommingTimestamp = System.currentTimeMillis();
					waveIncommingCnt++;
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
		case SAVE_GAME:
			retval = getSaveGame();
			break;
		case EXTRA:
			retval = getExtra();
			break;
		case LEVEL:
			retval = getLevel();
			break;
		case SKILL:
			retval = getSkill();
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
					if (menuPos < 5) {
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case 2:
					case 3:
					case 4:
						game.menuSound.play();
						menuPos+=4;
						break;
					case 5:
						game.escSound.play();
						menuPos+=4;
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == NEW_GAME) {
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
			} else if (getActiveMenu() == LOAD_GAME) {
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
					case 5:
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
			} else if (getActiveMenu() == SAVE_GAME) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 2) {
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 6) {
						menuPos++;
					}
					break;
				case KEY_ENTER:
					switch (menuPos) {
					case 2:
					case 3:
					case 4:
						menuPos+=5;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == EXTRA) {
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
						for (int i = 0; i < levels.size(); i++) {
							levels.get(i).updateImage(new TileSet("tiles/tileset" + (int)new ConfigReader("config.json").getBackground() + ".png", 10, 10));
						}
						game.menuSound.play();
						break;
					}
					break;

				default:
					break;
				} 
			} else if (getActiveMenu() == SKILL) {
				switch (e.getKeyCode()) {
				case KEY_UP:
				case KEY_LEFT:
					if (menuPos > 2) {
						menuPos--;
					}
					break;
				case KEY_DOWN:
				case KEY_RIGHT:
					if (menuPos < 11) {
						menuPos++;
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
					case 6:						
						activeMenu = NEW_GAME;
						menuPos = 1;
						break;
					case 7:
						activeMenu = LOAD_GAME;
						menuPos = 1;
						break;
					case 8:
						activeMenu = EXTRA;
						menuPos = 0;
						break;
					case 9:
						System.exit(0);
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == NEW_GAME) {
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
						player.reset();
						shp.setshipclass(menuPos);
						activeMenu = LEVEL;
						menuPos = 1;
						game.portToBase();
						game.newSound.play();
						game.setPlayerNumber(1);
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					menuPos = 1;
					game.escSound.play();
					break;
				
				default:
					break;
				}
			} else if (getActiveMenu() == LOAD_GAME) {
				switch (e.getKeyCode()) {
				case KEY_ENTER:
					switch (menuPos) {
					case 5:
					case 6:
					case 7:
						SaveReader saveReader = new SaveReader(menuPos-4);
						game.load(saveReader.getLevelProgress());
						player.load(saveReader.getName(), saveReader.getXp(), saveReader.getOldxp(), saveReader.getLvl(), saveReader.getSkillpts(), saveReader.getAgl(), saveReader.getCritprb(), saveReader.getCritdmg(), saveReader.getLaser(), saveReader.getAcid(), saveReader.getIce(), saveReader.getEmp(), saveReader.getCash());
						shp.load(saveReader.getShipclass(), saveReader.getHealthpotions(), saveReader.getShields(), saveReader.getBombs(), saveReader.getPortals());
						game.portToBase();
						game.newSound.play();
						activeMenu = LEVEL;
						game.setPlayerNumber(menuPos-4);
						menuPos = 1;
						guiBar = new GuiBar();
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					menuPos = 1;
					game.escSound.play();
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == SAVE_GAME) {
				switch (e.getKeyCode()) {
				case KEY_ENTER:
					switch (menuPos) {
					case 7:
					case 8:
					case 9:
						SaveWriter saveWriter = new SaveWriter(menuPos-6);
						saveWriter.setLevelData(game.getLevelProgress());
						saveWriter.setPlayerData(player.getName(), player.getXp(), player.getoldXP(), player.getLvl(), player.getSkillpts(), player.getAgl(), player.getCritprb(), player.getCritdmg(), player.getLaser(), player.getAcid(), player.getIce(), player.getEmp(), player.getCash());
						saveWriter.setShipData(shp.getshipclass(), shp.getbonusslots(0), shp.getbonusslots(1), shp.getbonusslots(2), shp.getbonusslots(3));
						saveWriter.save();
						activeMenu = SKILL;
						game.setPlayerNumber(menuPos-6);
						menuPos = 1;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = SKILL;
					menuPos = 1;
					game.escSound.play();
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == EXTRA) {
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
			} else if (getActiveMenu() == LEVEL) {
				switch (e.getKeyCode()) {
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = SKILL;
					game.escSound.play();
					break;

				default:
					break;
				}
			} else if (getActiveMenu() == SKILL) {
				switch (e.getKeyCode()) {
				case KEY_ENTER:
					switch (menuPos) {
					case 2:
						if (player.getSkillpts() >= 10 && player.getAgl() < 4) {
							player.setAgl();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 3:
						if (player.getSkillpts() >= 2 && player.getCritdmg() < 50) {
							player.setCritdmg();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 4:
						if (player.getSkillpts() >= 2 && player.getCritprb() < 50) {
							player.setCritprb();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 5:
						if (player.getSkillpts() >= 1 && player.getLaser() < 100) {
							player.setLaser();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 6:
						if (player.getSkillpts() >= 1 && player.getIce() < 100) {
							player.setIce();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 7:
						if (player.getSkillpts() >= 1 && player.getAcid() < 100) {
							player.setAcid();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 8:
						if (player.getSkillpts() >= 1 && player.getEmp() < 100) {
							player.setEmp();	
							game.menuSound.play();						
						} else {
							game.escSound.play();
						}
						break;
					case 9:
						activeMenu = MENU;
						menuPos = 1;
						game.menuSound.play();
						break;
					case 10:
						activeMenu = SAVE_GAME;
						menuPos = 1;
						game.menuSound.play();
						break;
					case 11:
						activeMenu = LEVEL;
						game.menuSound.play();
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = LEVEL;
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
