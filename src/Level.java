import java.awt.Graphics;
import java.util.Random;


public class Level {
	private static final int CONTINUE_OFFSET 		= 10;
	private static final int DEFAULT_WIDTH 			= 100;
	private static final int DEFAULT_HEIGHT 		= 9;
	private static final int PLANET_DICE 			= 16;
	private static final int PLANET_DICE_PERCENTAGE = 30;
	private static final int TILESET_WIDTH 			= 10;
	@SuppressWarnings("unused")
	private static final int TILESET_HEIGHT 		= 10;
	
	private int levelCnt;
	@SuppressWarnings("unused")
	private int levelNxt;					// level needed to continue
	private int[][] levelArray;				// defines the positioning of tiles
	private int levelWdt = DEFAULT_WIDTH;	// default width of standard level
	private int levelHgt = DEFAULT_HEIGHT;	// default height of all levels
	
	private int viewPos = 0;					// level position (in pixel)

	private TileSet tileset = null;
	
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
						retval[y+h][x+w] = planets[num][0] + h*TILESET_WIDTH + w;
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
		this.levelNxt = levelCnt * CONTINUE_OFFSET;
		this.tileset = tileset;
		if ( this.levelCnt == 0 ) {
			this.levelArray = new CSV("src/csv/base_level.csv").getIntegerArray();
			this.levelWdt = this.levelArray[0].length;
		} else {
			this.levelArray = createNewLevel();
		}
	}

	public int getWidth() {
		return levelWdt;
	}

	public int getHeight() {
		return levelHgt;
	}
	
	public void renderMap(Graphics g) {
		int startTile = viewPos/TileSet.TILE_WIDTH;
	    for(int tileY = 0; tileY < levelHgt; tileY++){
	    	for(int tileX = startTile; tileX < levelWdt; tileX++){
	    		tileset.renderTile(g, levelArray[tileY][tileX], tileX * TileSet.TILE_WIDTH - viewPos, tileY * TileSet.TILE_HEIGHT);
	    	}
	    }
	}
}
