import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import gui.Window;

public class MenuManager {

	public final static int LOADING		= 0;
	public final static int MENU		= 1;
	public final static int EXTRA		= 2;
	public final static int LEVEL		= 3;
	
	private Window window;
	private Ships shp;
	private List<Bullet> bullets;
	private List<Level> levels;
	private int activeLvl;
	private int loading;
	
	private int activeMenu = LOADING;
	
	public MenuManager(Window window, Ships shp, List<Bullet> bullets, List<Level> levels, int activeLvl) {
		this.window = window;
		this.shp = shp;
		this.bullets = bullets;
		this.levels = levels;
		this.activeLvl = activeLvl;
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
			retval = ImageIO.read(MenuManager.class.getResource("gui/images/menu_1.png"));
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
			window.setBlankCursor();
			Random rnd = new Random();
		    rnd.setSeed(System.currentTimeMillis());
		    loading = Math.abs(rnd.nextInt()%3)+1;
			break;
		case MENU:
			window.setMenuCursor();
			break;
		case EXTRA:
			window.setMenuCursor();
			break;
		case LEVEL:
			window.setBlankCursor();
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
}
