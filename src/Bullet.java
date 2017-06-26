import java.awt.image.BufferedImage;

import java.util.Random;

public class Bullet {

	public final static int DEFAULT_FIRESPEED = 200;
	public final static int DEFAULT_BULLETSPEED = 20;
	
	public final static int MOVE_NORTH 	= 0;
	public final static int MOVE_EAST 	= 1;
	public final static int MOVE_SOUTH 	= 2;
	public final static int MOVE_WEST 	= 3;
	
	public final static int PURPLE	= 0;
	public final static int RED		= 1;
	public final static int BLUE	= 2;
	public final static int GREEN	= 3;
	public final static int YELLOW	= 4;
	
	public final static int NORMAL_BULLET	= 0;
	public final static int MEDIUM_BULLET	= 1;
	public final static int BIG_BULLET		= 2;
	public final static int MICRO_BULLET	= 3;
	public final static int SMALL_BULLET	= 4;
	
	private Coordinates coordinates;
	private int direction;
	private BufferedImage image;
	private long id;
	private int bulletStyle;
		
	public Bullet(Coordinates start, int direction, int color, BufferedImage image, long id, int bulletStyle) {
		this.id = id;
		coordinates = new Coordinates(start.getX(), start.getY());
		this.direction = direction;
		this.bulletStyle = bulletStyle;
		int xOffset = 0;
		if (direction == MOVE_NORTH || direction == MOVE_SOUTH) {
			xOffset = 5 * TileSet.TILE_WIDTH;
		}
		int yOffset = bulletStyle*TileSet.TILE_HEIGHT;
				
		this.image = image.getSubimage(color * TileSet.TILE_WIDTH + xOffset, yOffset, 64, 64);
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
	
	public long getIdentity() {
		return id;
	}
	
	public int getDirection() {
		return direction;
	}
}
