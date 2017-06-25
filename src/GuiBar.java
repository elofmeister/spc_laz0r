

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GuiBar {

	private BufferedImage tileset;
	private int[] rgbLifepoints = new int[4 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];
	private int[] rgbExperience = new int[4 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];
	private int[] rgbBonusslots = new int[4 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];
	private int[] rgbCurse		= new int[2 * TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];

	public GuiBar() {
		try {
			tileset = ImageIO.read(GuiBar.class.getResource("tiles/gui.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLife(1);
		setXP(0);
		setBonusslots(0, 0, 0, 0);
		setCurse(0);
	}
	
	public BufferedImage getImage() {
		BufferedImage retval = tileset.getSubimage(0, 8 * TileSet.TILE_HEIGHT, Game.WINDOW_WIDTH, TileSet.TILE_HEIGHT);
		// LIFEPOINTS
		retval.setRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbLifepoints, 0, 4 * TileSet.TILE_WIDTH);
		retval.setRGB(5 * TileSet.TILE_WIDTH, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbBonusslots, 0, 4 * TileSet.TILE_WIDTH);
		retval.setRGB(10 * TileSet.TILE_WIDTH, 0, 2 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbCurse, 0, 2 * TileSet.TILE_WIDTH);
		retval.setRGB(12 * TileSet.TILE_WIDTH, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbExperience, 0, 4 * TileSet.TILE_WIDTH);

		return retval;
	}
	
	public void setLife(float lifedifference) {
		if (lifedifference < 0) {
			lifedifference = 0;
		}
		int offset = 8 - (int) (lifedifference / (float) 0.125);
		if (offset < 0 || offset > 8) {
			offset = 8;
		}
		try {
			tileset.getSubimage(0, offset * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbLifepoints, 0, 4 * TileSet.TILE_WIDTH);
		} catch (Exception e) {
			System.err.println("GuiBarSetLifeException "+offset);
		}
	}
	
	public void setXP(float percentage) {
		if (percentage > 100) {
			percentage = 100;
		}
		int offset = (int) (percentage / (float) 12.5);
		tileset.getSubimage(12 * TileSet.TILE_WIDTH, offset * TileSet.TILE_HEIGHT, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, 4 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbExperience, 0, 4 * TileSet.TILE_WIDTH);
	}
	
	public void setBonusslots(int slot1, int slot2, int slot3, int slot4) {
		int[] slot = {slot1, slot2, slot3, slot4};
		for (int i = 0; i < 4; i++) {
			int[] rgbSlot = new int[TileSet.TILE_WIDTH * TileSet.TILE_HEIGHT];
			tileset.getSubimage((5 + i) * TileSet.TILE_WIDTH, slot[i] * TileSet.TILE_HEIGHT, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbSlot, 0, TileSet.TILE_WIDTH);
			for (int y = 0; y < TileSet.TILE_HEIGHT; y++) {
				for (int x = 0; x < TileSet.TILE_WIDTH; x++) {
					rgbBonusslots[y * 4 * TileSet.TILE_WIDTH + x + i * TileSet.TILE_WIDTH] = rgbSlot[y * TileSet.TILE_WIDTH + x];
				}
			}
		}
	}
	
	public void setCurse(int curse) {
		tileset.getSubimage(10 * TileSet.TILE_WIDTH, curse * TileSet.TILE_HEIGHT, 2 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT).getRGB(0, 0, 2 * TileSet.TILE_WIDTH, TileSet.TILE_HEIGHT, rgbCurse, 0, 2 * TileSet.TILE_WIDTH);
	}
}
