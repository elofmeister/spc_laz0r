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

import gui.MenuCursor;
import gui.Window;

public class MenuManager implements KeyListener {

	public final static int LOADING		= 0;
	public final static int MENU		= 1;
	public final static int NEW_GAME	= 2;
	public final static int LOAD_GAME	= 3;
	public final static int EXTRA		= 4;
	public final static int LEVEL		= 5;
		
	private Window window;
	private Ships shp;
	private List<Bullet> bullets;
	private List<Level> levels;
	private int activeLvl;
	private int loading;

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
	
	public MenuManager(Window window, Ships shp, List<Bullet> bullets, List<Level> levels, int activeLvl) {
		this.window = window;
		this.shp = shp;
		this.bullets = bullets;
		this.levels = levels;
		this.activeLvl = activeLvl;
		this.window.getFrame().addKeyListener(this);
	}
	
	private BufferedImage getLoading() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/loading"+loading+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getMenu() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/menu"+menuPos+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getNewGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/choose"+menuPos+menuPos2+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getLoadGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/load"+menuPos+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getSaveGame() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/save"+menuPos+".png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getExtra() {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/extra1.png"));
		} catch (IOException e) {
		}
		return retval;
	}
	
	private BufferedImage getLevel() {
		int[] rgbBackground = new int[16*64*9*64];
		levels.get(activeLvl).getSubimage().getRGB(0, 0, 16*64, 9*64, rgbBackground, 0, 16*64);

		final int TRANSPARANCY 	= 16777215;
		final int BLACK			= -16777216;
		
		if (!bullets.isEmpty()) {
			for (int n = 0; n < bullets.size(); n++) {
				int[] rgbBullet = new int[64*64];
				bullets.get(n).getImage().getRGB(0, 0, 64, 64, rgbBullet, 0, 64);
				if (bullets.get(n).move(20)) {
					for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
						for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
							if (rgbBullet[i*TileSet.TILE_WIDTH+j]!=BLACK && bullets.size()>n) {
								rgbBackground[(bullets.get(n).getCoordinates().getY()+i)*16*64 + bullets.get(n).getCoordinates().getX() + j] = rgbBullet[i*TileSet.TILE_WIDTH+j];
							}
						}
					}
				} else {
					bullets.remove(n--);
				}				
			}
		}
		int[] rgbShip = new int[64*64];
		shp.getImage().getRGB(0, 0, 64, 64, rgbShip, 0, 64);
		for (int i = 0; i < TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < TileSet.TILE_WIDTH; j++) {
				if (rgbShip[i*TileSet.TILE_WIDTH+j]!=TRANSPARANCY) {
					rgbBackground[(shp.getCoordinates().getY()+i)*16*64 + shp.getCoordinates().getX() + j] = rgbShip[i*TileSet.TILE_WIDTH+j];
				}
			}
		}
		BufferedImage subimg = new BufferedImage(16*64, 9*64, BufferedImage.TYPE_INT_ARGB);
		subimg.setRGB(0, 0, 16*64, 9*64, rgbBackground, 0, 16*64);
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
						menuPos = 5;
						break;
					case 3:
						menuPos = 6;
						break;
					case 4:
						menuPos = 7;
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
						menuPos2 = 3;
						break;
					case Ships.RUMPLER:
						menuPos2 = 3;
						break;
					case Ships.GLASSCANNON:
						menuPos2 = 3;
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
						menuPos = 5;
						break;
					case 3:
						menuPos = 6;
						break;
					case 4:
						menuPos = 7;
						break;

					default:
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
						menuPos = 1;
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
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
						shp.setshipclass(Ships.STANDARDO);
						activeMenu = LEVEL;
						menuPos = 1;
						break;
					case Ships.RUMPLER:
						shp.setshipclass(Ships.RUMPLER);
						activeMenu = LEVEL;
						menuPos = 1;
						break;
					case Ships.GLASSCANNON:
						shp.setshipclass(Ships.GLASSCANNON);
						activeMenu = LEVEL;
						menuPos = 1;
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
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
						activeMenu = LEVEL;
						menuPos = 1;
						break;
					case 6:
						activeMenu = LEVEL;
						menuPos = 1;
						break;
					case 7:
						activeMenu = LEVEL;
						menuPos = 1;
						break;

					default:
						break;
					}
					break;
				case KEY_ESC:
				case KEY_BACKSPACE:
					activeMenu = MENU;
					break;

				default:
					break;
				}
			}
			else if (getActiveMenu() == LEVEL) {
				switch (e.getKeyCode()) {
				case KEY_ESC:
					activeMenu = MENU;
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
		// TODO Auto-generated method stub
		
	}
}
