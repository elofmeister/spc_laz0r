import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileSet {
	public static final int TILE_WIDTH 	= 64;
	public static final int TILE_HEIGHT = 64;
	public static final int TILE_OFFSET = 0;
	
	private BufferedImage[] tiles;

	/** 
	 * @details	This constructor splits the TileSet into single tiles.
	 * 
	 * @param	tilesSet	buffered image of whole TileSet
	 * @param	tiles		buffered image array of all single tiles
	 */
	public TileSet(String path, int sizeX, int sizeY) {
		tiles = new BufferedImage[sizeX * sizeY];
		BufferedImage tileSet;
		try {
			tileSet = ImageIO.read(TileSet.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		for ( int y = 0, i = 0; y < sizeY; y++ ) {
			for ( int x = 0; x < sizeX; x++ ) {
				tiles[i++] = tileSet.getSubimage(x * (TILE_WIDTH + TILE_OFFSET), y * (TILE_HEIGHT + TILE_OFFSET), TILE_WIDTH, TILE_HEIGHT);
			}
		}
	}
	
	public void renderTile(Graphics g, int tileNum, int x, int y) {
		g.drawImage(tiles[tileNum], x, y, TILE_WIDTH, TILE_HEIGHT, null);
	}
}