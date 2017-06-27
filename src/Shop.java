import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Shop implements KeyListener {

	public static final int SHOP_1 = 1;
	public static final int SHOP_2 = 2;
	public static final int SHOP_3 = 3;

	public static final int SHOP_HEIGHT = Game.WINDOW_HEIGHT/2 - TileSet.TILE_HEIGHT / 2;
	public static final int SHOP_WIDTH = Game.WINDOW_WIDTH/2;
	
	private Game game;
	private Player player;
	private Ships ship;
	private BufferedImage image = new TileSet("gui/images/shop.png", 16, 9).getTileset();
	private boolean activated = false;
	
	public Shop(Player player, Ships ship, Game game) {
		this.game = game;
		this.player = player;
		this.ship = ship;
	}

	private boolean keyPressed;
	private final int KEY_ENTER		= 10;
	
	public boolean isAtShop(int shopNumber) {
		boolean bretval = false;
		if (game.getActiveLevel() == 0) {
			switch (shopNumber) {
			case SHOP_1:
				if (new CollisionDetector().isInRange(ship.getCoordinates(), new Coordinates(4 * TileSet.TILE_WIDTH, 3 * TileSet.TILE_HEIGHT))) {
					bretval = true;
				}
				break;
			case SHOP_2:
				if (new CollisionDetector().isInRange(ship.getCoordinates(), new Coordinates(6 * TileSet.TILE_WIDTH, 3 * TileSet.TILE_HEIGHT))) {
					bretval = true;
				}
				break;
			case SHOP_3:
				if (new CollisionDetector().isInRange(ship.getCoordinates(), new Coordinates(8 * TileSet.TILE_WIDTH, 3 * TileSet.TILE_HEIGHT))) {
					bretval = true;
				}
				break;
	
			default:
				break;
			}
		}
		return bretval;
	}
	
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KEY_ENTER) {
			if (activated) {
				if (isAtShop(SHOP_1) && player.getCash() >= 1000 && ship.isItemSpace()) {
					Random rnd = new Random();
					rnd.setSeed(System.currentTimeMillis());
					player.setCash(-1000);
					ship.addItem(new ItemCalculator(Math.abs(rnd.nextInt())%3+1, Math.abs(rnd.nextInt())%2+1).getItem());
					game.menuSound.play();
				} else if (isAtShop(SHOP_2) && player.getCash() >= 100 && ship.getbonusslots(0) < 4) {
					player.setCash(-100);
					ship.setbonusslots(0, ship.getbonusslots(0)+1);
					game.menuSound.play();
				} else if (isAtShop(SHOP_3) && player.getCash() >= 100 && ship.getbonusslots(1) < 4) {
					player.setCash(-100);
					ship.setbonusslots(1, ship.getbonusslots(1)+1);
					game.menuSound.play();
				} else {
					game.escSound.play();
				}
			}			
			keyPressed = true;
		}	
	}
	
	public BufferedImage getShopImage(int shopNumber) {
		BufferedImage retval = null;
		switch (shopNumber) {
		case SHOP_1:
			retval = image.getSubimage(0, 0, SHOP_WIDTH, SHOP_HEIGHT);
			break;
		case SHOP_2:
			retval = image.getSubimage(SHOP_WIDTH, 0, SHOP_WIDTH, SHOP_HEIGHT);
			break;
		case SHOP_3:
			retval = image.getSubimage(0, SHOP_HEIGHT, SHOP_WIDTH, SHOP_HEIGHT);
			break;

		default:
			break;
		}
		return retval;
	}

	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KEY_ENTER) {
			keyPressed = false;
		}	
	}

	public void keyTyped(KeyEvent key) {
	}
	
	public void setActivated(boolean status) {
		activated = status;
	}

}
