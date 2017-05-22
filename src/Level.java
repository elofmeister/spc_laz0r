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
	
	private int[][] base_level = {
			{31, 60, 51, 71, 71, 31, 63, 71, 71, 96, 63, 43, 71, 52, 71, 71,},
			{31, 42, 94, 71, 43, 71, 97, 71, 71, 71, 51, 71, 71, 40, 63, 51,},
			{63, 71, 00, 02, 02, 02, 02, 02, 02, 02, 01, 71, 42, 71, 63, 71,},
			{30, 33, 03, 20, 21, 20, 22, 20, 23, 20, 13, 94, 71, 71, 71, 40,},
			{71, 71, 03, 20, 20, 20, 20, 20, 20, 20, 13, 31, 71, 73, 71, 42,}, 
			{61, 33, 03, 20, 20, 20, 20, 20, 20, 20, 13, 63, 71, 71, 71, 30,},
			{51, 71, 03, 20, 20, 20, 20, 20, 20, 20, 13, 95, 52, 71, 42, 71,},
			{71, 71, 10, 12, 12, 12, 12, 12, 12, 12, 11, 60, 41, 71, 71, 61,},
			{52, 71, 60, 98, 43, 97, 71, 63, 62, 98, 71, 43, 33, 71, 60, 71,}
	};
	
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
		int[] bgTiles = {
				94, 95, 96, 97, 98, 99,															// 
				30, 31, 32, 33, 40, 41, 42, 43, 50, 51, 52, 53, 60, 61, 62, 63, 70,				// variation of stars
				30, 31, 32, 33, 40, 41, 42, 43, 50, 51, 52, 53, 60, 61, 62, 63, 70,				// variation of stars
				71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71,	// black tile
				71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71	// black tile
		};
		int[][] planets = {
				// 1. parameter .. starting index
				// 2. parameter .. length
				// 3. parameter .. height
				{ 4,  3,  3},
				{ 7,  3,  3},
				{34,  3,  3},
				{37,  3,  3},
				{64,  3,  3},
				{67,  3,  3},
				{80,  4,  2}
		};
		int[][] retval = new int[this.levelHgt][this.levelWdt]; 
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		// fill array with background tiles
		for (int h = 0; h < this.levelHgt; h++) {
			for (int w = 0; w < this.levelWdt; w++) {
				retval[h][w] = bgTiles[Math.abs(rnd.nextInt()%bgTiles.length)];		
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
			this.levelArray = this.base_level;
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
