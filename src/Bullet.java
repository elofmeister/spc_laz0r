import java.awt.image.BufferedImage;

public class Bullet {

	public final static int MOVE_NORTH 	= 0;
	public final static int MOVE_EAST 	= 1;
	public final static int MOVE_SOUTH 	= 2;
	public final static int MOVE_WEST 	= 3;
	
	public final static int PURPLE	= 0;
	public final static int RED		= 1;
	public final static int GREEN	= 2;
	public final static int BLUE	= 3;
	public final static int YELLOW	= 4;
	
	
	private Coordinates coordinates;
	private int direction;
	private BufferedImage image;
	
	public Bullet(Coordinates start, int direction, int color, BufferedImage image) {
		coordinates = new Coordinates(start.getX(), start.getY());
		this.direction = direction;
		int xOffset = 0;
		if (direction == MOVE_NORTH || direction == MOVE_SOUTH) {
			xOffset = 5 * TileSet.TILE_WIDTH;
		}
		this.image = image.getSubimage(color * TileSet.TILE_WIDTH + xOffset, 0, 64, 64);
	}
	
	public boolean move(int speed) {
		boolean bretval = false;
		switch (direction) {
		case MOVE_NORTH:
			if (coordinates.getY()-speed>0) {
				coordinates.setY(coordinates.getY()-speed);
				bretval = true;
			} else {
				coordinates.setY(0);
			}
			break;
		case MOVE_EAST:
			if (coordinates.getX()+speed<Game.WINDOW_WIDTH-TileSet.TILE_WIDTH) {
				coordinates.setX(coordinates.getX()+speed);
				bretval = true;
			} else {
				coordinates.setX(Game.WINDOW_WIDTH-TileSet.TILE_WIDTH);
			}
			break;
		case MOVE_SOUTH:
			if (coordinates.getY()+speed<Game.WINDOW_HEIGHT-TileSet.TILE_HEIGHT) {
				coordinates.setY(coordinates.getY()+speed);
				bretval = true;
			} else {
				coordinates.setY(Game.WINDOW_HEIGHT-TileSet.TILE_HEIGHT);
			}
			break;
		case MOVE_WEST:
			if (coordinates.getX()-speed>0) {
				coordinates.setX(coordinates.getX()-speed);
				bretval = true;
			} else {
				coordinates.setX(0);
			}
			break;

		default:
			break;
		}
		return bretval;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void dispose() {
		image.flush();
	}
}
