import java.awt.image.BufferedImage;
import java.util.*;

public class Chest {

	private Coordinates coordinates;
	private List<Item> items = new ArrayList<Item>();
	private BufferedImage image;
	private int chestNumber = 1;
	
	public Chest(BufferedImage image, Coordinates coordinates) {
		this.coordinates = coordinates;
		this.image = image;
		this.chestNumber = chestNumber;
	}
	
	public BufferedImage getImage() {
		return image.getSubimage((chestNumber-1)*TileSet.TILE_WIDTH, 7 * TileSet.TILE_HEIGHT, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
	}
	
	public void addItemToChest(Item item) {
		items.add(item);
	}
	
	public boolean isInView() {
		boolean bretval = false;
		if (
				coordinates.getX() >= 0 && 
				coordinates.getY() >= 0 && 
				coordinates.getX() < Game.WINDOW_WIDTH - TileSet.TILE_WIDTH &&
				coordinates.getY() < Game.WINDOW_HEIGHT - TileSet.TILE_HEIGHT
			) {
			bretval = true;
		}
		return bretval;
	}
	
	public boolean isInRange(Coordinates shipCoordinates) {
		return new CollisionDetector().isInRange(shipCoordinates, coordinates);
	}
	
	public void moveLeft() {
		coordinates.setX(coordinates.getX()-1);
	}
	
	public void moveRight() {
		coordinates.setX(coordinates.getX()+1);
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public Item getNextItem() {
		Item retval = null;
		if (!items.isEmpty()) {
			retval = items.get(0);
			items.remove(0);
		}
		return retval;
	}
	
	public boolean isEmpty() {
		boolean bretval = false;
		if (items.isEmpty()) {
			bretval = true;
		}
		return bretval;
	}
	
	public void setRarity(int rarity) {
		chestNumber = rarity;
	}
}
