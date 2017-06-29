import java.awt.image.BufferedImage;

public class FontManager {

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int BIG = 2;
	
	private BufferedImage image = new TileSet("tiles/menucharacters.png", 10, 10).getTileset();
	
	public FontManager() {
	}
	
	public BufferedImage getNumber(int size, int num) {
		return image.getSubimage(num * TileSet.TILE_WIDTH + (25 - 2 * size), size * TileSet.TILE_HEIGHT + (22 - 3 * size), TileSet.TILE_WIDTH - 2 * (25 - 2 * size), TileSet.TILE_HEIGHT - 2 * (22 - 3 * size));
	}
	
	public BufferedImage getPlayer(int num) {
		return image.getSubimage((num - 1) * 2 * TileSet.TILE_WIDTH, 3 * TileSet.TILE_HEIGHT + 20, 2 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT - 40);
	}
	
	public BufferedImage getCrit(int num) {
		return image.getSubimage(num * TileSet.TILE_WIDTH, 4 * TileSet.TILE_HEIGHT, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
	}

	public BufferedImage getWaveIncomming(int status) {
		BufferedImage retval = null;
		switch (status) {
		case 0:
		case 7:
			retval = image.getSubimage(4 * TileSet.TILE_WIDTH, 9 * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
			break;
		case 1:
		case 6:
			retval = image.getSubimage(0, 9 * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
			break;
		case 2:
		case 5:
			retval = image.getSubimage(4 * TileSet.TILE_WIDTH, 8 * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
			break;
		case 3:
		case 4:
			retval = image.getSubimage(0, 8 * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT);
			break;

		default:
			break;
		}
		return retval;
	}
}
