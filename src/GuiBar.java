

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GuiBar {

	private BufferedImage tileset;
	private int[] rgbLifepoints = new int[4 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];
	private int[] rgbExperience = new int[4 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];

	public GuiBar() {
		try {
			tileset = ImageIO.read(GuiBar.class.getResource("tiles/gui.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLife(1);
		setXP(0);
	}
	
	public BufferedImage getImage() {
		BufferedImage retval = tileset.getSubimage(0, 8 * TileSet.TILE_HEIGHT, Game.WINDOW_WIDTH, TileSet.TILE_HEIGHT);
		// LIFEPOINTS
		retval.setRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbLifepoints, 0, 4 * TileSet.TILE_WIDTH);
		retval.setRGB(12 * TileSet.TILE_WIDTH, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbExperience, 0, 4 * TileSet.TILE_WIDTH);

		return retval;
	}
	
	public void setLife(float lifedifference) {
		if (lifedifference < 0) {
			lifedifference = 0;
		}
		int offset = 8 - (int) (lifedifference / (float) 0.125);
		tileset.getSubimage(0, offset * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbLifepoints, 0, 4 * TileSet.TILE_WIDTH);
	}
	
	public void setXP(float percentage) {
		if (percentage > 100) {
			percentage = 100;
		}
		int offset = (int) (percentage / (float) 12.5);
		tileset.getSubimage(12 * TileSet.TILE_WIDTH, offset * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbExperience, 0, 4 * TileSet.TILE_WIDTH);
	}	
}
