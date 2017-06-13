import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileSet {
	public static final int TILE_WIDTH 	= 64;
	public static final int TILE_HEIGHT = 64;
	public static final int TILE_OFFSET = 0;

	private String path;
	private BufferedImage tileset;
	private int width;
	private int height;

	public TileSet(String path, int width, int height) {
		this.path = path;
		this.width = width * TILE_WIDTH;
		this.height = height * TILE_HEIGHT;
		try {
			tileset = ImageIO.read(TileSet.class.getResource(path));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getPath() {
		return path;
	}

	public BufferedImage getTileset() {
		return tileset;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}