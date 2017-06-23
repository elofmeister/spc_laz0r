import java.awt.image.BufferedImage;

public class Explosion {

	private static final int DURATION = 50; 
	private long timestamp = System.currentTimeMillis();
	
	private Coordinates coordinates;
	private SoundPlayer soundplayer;
	private BufferedImage image;
	private int animation;
	
	public Explosion(Coordinates coordinates, BufferedImage image, SoundPlayer sound) {
		this.coordinates = coordinates;
		this.image = image;
		sound.play();
	}
	
	public boolean isFinished() {
		boolean bretval = false;
		if (animation >= 19) {
			bretval = true;
		}
		return bretval;
	}
	
	public BufferedImage getImage() {
		BufferedImage retval;
		if (timestamp + DURATION < System.currentTimeMillis() && !isFinished()) {
			animation++;
			timestamp = System.currentTimeMillis();
		}
		if (animation < 10) {
			retval = image.getSubimage(animation * TileSet.TILE_WIDTH, 8 * TileSet.TILE_HEIGHT, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
		} else {
			retval = image.getSubimage((animation-10) * TileSet.TILE_WIDTH, 9 * TileSet.TILE_HEIGHT, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
		}
		return retval;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

}
