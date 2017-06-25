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

}
