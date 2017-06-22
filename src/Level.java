import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Level {
	private static final int CONTINUE_OFFSET 		= 10;
	private static final int DEFAULT_WIDTH 			= 100;
	private static final int DEFAULT_HEIGHT 		= 9;
	private static final int PLANET_DICE 			= 16;
	private static final int PLANET_DICE_PERCENTAGE = 30;
	

	private int levelCnt;
	private int levelNxt;					// level needed to continue
	private int[][] levelArray;				// defines the positioning of tiles
	private int levelWdt = DEFAULT_WIDTH;	// default width of standard level
	private int levelHgt = DEFAULT_HEIGHT;	// default height of all levels
	
	private int viewPos = 0;					// level position (in pixel)

	private TileSet tileset = null;
	private BufferedImage image;
	
	public boolean moveRight() {
		boolean bretval = false;
		if ( viewPos < TileSet.TILE_WIDTH * levelWdt - Game.WINDOW_WIDTH ) {
			viewPos++;
			bretval = true;
		}
		return bretval;
	}
	
	public boolean moveLeft() {
		boolean bretval = false;
		if ( viewPos > 0 ) {
			viewPos--;
			bretval = true;
		}
		return bretval;
	}
	
	private int[][] createNewLevel() {
		int[][] bgTiles = new CSV("src/csv/background.csv").getIntegerArray();
		int[][] planets = new CSV("src/csv/planets.csv").getIntegerArray();
		int[][] retval = new int[this.levelHgt][this.levelWdt]; 
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		// fill array with background tiles
		for (int h = 0; h < this.levelHgt; h++) {
			for (int w = 0; w < this.levelWdt; w++) {
				retval[h][w] = bgTiles[Math.abs(rnd.nextInt()%bgTiles.length)][0];		
			}
		}
		// generating planets
		// number of dice
		for (int i = 0; i < (int) (DEFAULT_WIDTH/PLANET_DICE); i++) {
			if ( Math.abs(rnd.nextInt()%100) < PLANET_DICE_PERCENTAGE  ) {
				int num = Math.abs(rnd.nextInt()%planets.length);									// choose planet
				int x = i*PLANET_DICE + Math.abs(rnd.nextInt()%(PLANET_DICE - planets[num][1]));	// x index of array
				int y = Math.abs(rnd.nextInt()%(DEFAULT_HEIGHT-planets[num][2])); // y index of array
				for ( int h = 0; h < planets[num][2]; h++ ) {
					for ( int w = 0; w < planets[num][1]; w++ ) {
						retval[y+h][x+w] = planets[num][0] + h*(tileset.getWidth()/TileSet.TILE_WIDTH) + w;
					}
				}
			}
		}
		// generating portal
		retval[(int) (DEFAULT_HEIGHT/2)][DEFAULT_WIDTH-2] = 73;
		return retval;			
	}
	
	public Level( int levelCnt, TileSet tileset ) {
		this.levelCnt = levelCnt;
		levelNxt = levelCnt * CONTINUE_OFFSET;
		this.tileset = tileset;
		if ( levelCnt == 0 ) {
			levelArray = new CSV("src/csv/base_level.csv").getIntegerArray();
			levelWdt = levelArray[0].length;
		} else {
			levelArray = createNewLevel();
		}
		updateImage(tileset);
	}

	public void updateImage(TileSet newTileSet) {
		tileset = newTileSet;
		image = new BufferedImage(TileSet.TILE_WIDTH * levelArray[0].length, TileSet.TILE_HEIGHT * levelArray.length, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < levelArray.length; i++) {
			for (int j = 0; j < levelArray[0].length; j++) {
				int[] rgbArray = new int[64 * 64];
				tileset.getTileset().getSubimage(levelArray[i][j]%10*64, (int)(levelArray[i][j]/10)*64, 64, 64).getRGB(0, 0, 64, 64, rgbArray, 0, 64);
				image.setRGB(j*64, i*64, 64, 64, rgbArray, 0, 64);
			}
		}
	}
	
	public void restart() {
		viewPos = 0;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getWidth() {
		return levelWdt;
	}

	public int getHeight() {
		return levelHgt;
	}
	
	public TileSet getTileSet() {
		return tileset;		
	}
	
	public int[][] getLevelArray() {
		return levelArray;		
	}
	
	public int getLevelCnt() {
		return levelCnt;
	}
	
	public int getLevelNxt() {
		return levelNxt;
	}

	public BufferedImage getSubimage() {
		return image.getSubimage(viewPos, 0, 16 * TileSet.TILE_WIDTH, 9 * TileSet.TILE_HEIGHT);
	}
}
