
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.Random;

import javax.imageio.ImageIO;

public class Level {
	private static final int CONTINUE_OFFSET 		= 10;
	private static final int DEFAULT_WIDTH 			= 100;
	private static final int DEFAULT_HEIGHT 		= 9;
	private static final int PLANET_DICE 			= 16;
	private static final int PLANET_DICE_PERCENTAGE = 50;

	private static final int WAVES					= 10;		// First level starts with 10 waves
	private static final int WAVES_OFFSET			= 0;		// Each new level
	
	private static final int ENEMIES_PER_WAVE		= 6;		// First level starts with 6 enemies
	private static final float ENEMIES_OFFSET		= (float) 0.5;		// Each new level 
	
	private int waveAmount;
	private int waveCnt;
	private int levelCnt;
	private int levelNxt;					// level needed to continue
	private int[][] levelArray;				// defines the positioning of tiles
	private int levelWdt = DEFAULT_WIDTH;	// default width of standard level
	private int levelHgt = DEFAULT_HEIGHT;	// default height of all levels
	
	private int viewPos = 0;					// level position (in pixel)

	private TileSet tileset = null;
	private BufferedImage image;
	
	public boolean isEndReached() {
		boolean bretval = false;
		if (viewPos >= TileSet.TILE_WIDTH * levelWdt - Game.WINDOW_WIDTH) {
			bretval = true;
		}
		return bretval;
	}
	
	public boolean isBeginReached() {
		boolean bretval = false;
		if (viewPos == 0) {
			bretval = true;
		}
		return bretval;
	}
	
	public void moveRight() {
		if (!isEndReached()) {
			viewPos++;
		}
	}
	
	public void moveLeft() {
		if (!isBeginReached()) {
			viewPos--;
		}
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
		// generate planets
		for (int i = 0; i < (int) (DEFAULT_WIDTH/PLANET_DICE); i++) {
			if ( Math.abs(rnd.nextInt()%100) < PLANET_DICE_PERCENTAGE  ) {
				int num = Math.abs(rnd.nextInt()%planets.length);
				int x = i*PLANET_DICE + Math.abs(rnd.nextInt()%(PLANET_DICE - planets[num][1]));
				int y = Math.abs(rnd.nextInt()%(DEFAULT_HEIGHT-planets[num][2]));
				for ( int h = 0; h < planets[num][2]; h++ ) {
					for ( int w = 0; w < planets[num][1]; w++ ) {
						retval[y+h][x+w] = planets[num][0] + h*(tileset.getWidth()/TileSet.TILE_WIDTH) + w;
					}
				}
			}
		}
		// generate portal
		retval[(int) (DEFAULT_HEIGHT/2)][DEFAULT_WIDTH-3] = 73;
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
			waveAmount = WAVES + WAVES_OFFSET * (levelCnt-1);
			levelArray = createNewLevel();
		}
		updateImage(tileset);
	}

	public boolean toggleWave() {
		boolean bretval = false;
		int waveDistance = (DEFAULT_WIDTH*TileSet.TILE_WIDTH)/waveAmount;
		if (waveCnt>0) {
			int loadingProgress = (int)(100*((float)((float)viewPos-((float)waveCnt-1)*(float)waveDistance)/((float)waveCnt*(float)waveDistance-((float)waveCnt-1)*(float)waveDistance)));
			String loadingBar = "";
			String space = " ";
			String load = "-";
			for (int i = 0; i < loadingProgress; i++) {
				loadingBar+=load;
			}
			for (int i = 0; i < 100-loadingProgress; i++) {
				loadingBar+=space;				
			}
			//System.out.println("Loading new wave "+loadingBar+" ("+loadingProgress+"%). Wave "+waveCnt+" of "+ waveAmount+".");
		}
		if (viewPos>waveCnt*waveDistance && waveCnt<waveAmount) {
			bretval = true;
		}
		return bretval;
	}
	
	public void addWave() {
		waveCnt++;
	}
	
	public int getWaveAmount() {
		return waveAmount;
	}
	
	public int getEnemyAmount() {
		return ENEMIES_PER_WAVE + (int) ((levelCnt - 1) * ENEMIES_OFFSET);
	}
	
	public int getWaveCnt() {
		return waveCnt;
	}
	
	public int getViewPos() {
		return viewPos;
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
		waveCnt = 0;
	}
	
	public void setWaveCnt(int waveCnt) {
		this.waveCnt = waveCnt;
	}
	
	public void setViewPos(int viewPos) {
		this.viewPos = viewPos;
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
