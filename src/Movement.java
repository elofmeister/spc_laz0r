import java.util.Random;

public class Movement {
	// MOVEMENTS
	public static final int 	RANDOM_MOVE 			= 0;
	public static final int 	ZIGZAG_MOVE 			= 1;
	public static final int 	CIRCLE_MOVE 			= 2;
	
	// BORDER
	private static final int 	BORDER_MIN_X 			= 0;
	private static final int 	BORDER_MAX_X 			= (Game.WINDOW_WIDTH_TILE_NUM - 1) * TileSet.TILE_WIDTH;
	private static final int 	BORDER_MIN_Y 			= 0;
	private static final int 	BORDER_MAX_Y 			= (Game.WINDOW_HEIGHT_TILE_NUM - 1) * TileSet.TILE_HEIGHT;

	// DIRECTIONS
	private	static final int 	NUMBER_OF_DIRECTIONS 	= 8;
	private static final int 	DIRECTION_E 			= 0;
	private static final int 	DIRECTION_SE 			= 1;
	private static final int 	DIRECTION_S 			= 2;
	private static final int 	DIRECTION_SW 			= 3;
	private static final int 	DIRECTION_W 			= 4;
	private static final int 	DIRECTION_NW 			= 5;
	private static final int 	DIRECTION_N 			= 6;
	private static final int 	DIRECTION_NE 			= 7;
	
	private Coordinates 		coordinates				= null;
	private int 				movementDef				= 0;
	private Random 				rnd 					= new Random();
	private long 				moveCnt					= 0;
	private int 				direction 				= rnd.nextInt() % NUMBER_OF_DIRECTIONS;
	private int 				movement_offset 		= 1;
	
	/**
	 * This function checks if the input coordinates are inside the window borders.
	 * @param x	= x-coordinate
	 * @param y = y-coordinate
	 * @return true, if the coordinates are inside the window borders, else false
	 */
	private boolean checkBorder(int x, int y) {
		boolean bretval = false;
		if (x >= BORDER_MIN_X && x <= BORDER_MAX_X && y >= BORDER_MIN_Y && y <= BORDER_MAX_Y) {
			bretval = true;
		}
		return bretval;
	}

	// MOVE FUNCTIONS FOR SPECIFIC DIRECTIONS
	private void moveNorth() {
		if (coordinates.getY() > BORDER_MAX_Y * 0.1) {
			coordinates.setY(coordinates.getY() - 1);
		}
	}
	
	private void moveSouth() {
		if (coordinates.getY() < BORDER_MAX_Y * 0.9) {
			coordinates.setY(coordinates.getY() + 1);
		}
	}
	
	private void moveWest() {
		if (coordinates.getX() > BORDER_MAX_X * 0.1) {
			coordinates.setX(coordinates.getX() - 1);
		}
	}
	
	private void moveEast() {
		if (coordinates.getX() < BORDER_MAX_X * 0.9) {
			coordinates.setX(coordinates.getX() + 1);
		}
	}
	
	// CHECK FUNCTIONS FOR SPECIFIC DIRECTIONS
	/**
	 * This functions defines an area, the ship can't move east.
	 * @return true, if the ship can move east, else false
	 */
	private boolean checkEast() {
		boolean bretval = false;
		if (coordinates.getX() < BORDER_MAX_X * 2 / 3) {
			bretval = true;
		}
		return bretval;
	}
	
	/**
	 * This functions defines an area, the ship can't move south.
	 * @return true, if the ship can move south, else false
	 */
	private boolean checkSouth() {
		boolean bretval = false;
		if (coordinates.getY() < BORDER_MAX_X * 2 / 3) {
			bretval = true;
		}
		return bretval;
	}
	
	/**
	 * This functions defines an area, the ship can't move west.
	 * @return true, if the ship can move west, else false
	 */
	private boolean checkWest() {
		boolean bretval = false;
		if (coordinates.getX() > BORDER_MAX_X / 3) {
			bretval = true;
		}
		return bretval;
	}
	
	/**
	 * This functions defines an area, the ship can't move north.
	 * @return true, if the ship can move north, else false
	 */
	private boolean checkNorth() {
		boolean bretval = false;
		if (coordinates.getY() > BORDER_MAX_X / 3) {
			bretval = true;
		}
		return bretval;
	}
	
	// CHANGE DIRECTION RANDOMLY
	private void changeDirection() {
		boolean temp = true;
		while(temp) {
			direction = rnd.nextInt() % NUMBER_OF_DIRECTIONS;
			switch (direction) {
			case DIRECTION_E:
				if (checkEast()) {
					temp = false;
				}
				break;
				
			case DIRECTION_SE:
				if (checkEast() && checkSouth()) {
					temp = false;
				}
				break;
			
			case DIRECTION_S:
				if (checkSouth()) {
					temp = false;
				}
				break;
				
			case DIRECTION_SW:
				if (checkSouth() && checkWest()) {
					temp = false;
				}
				break;
			
			case DIRECTION_W:
				if (checkWest()) {
					temp = false;
				}
				break;
			
			case DIRECTION_NW:
				if (checkNorth() && checkWest()) {
					temp = false;
				}
				break;
				
			case DIRECTION_N:
				if (checkNorth()) {
					temp = false;
				}
				break;
				
			case DIRECTION_NE:
				if (checkNorth() && checkWest()) {
					temp = false;
				}
				break;

			default:
				break;
			}
		}	
	}
	
	// MOVE FUNCTION FOR ACTUAL DIRECTION
	private void moveDirection() {
		switch (direction) {
		case DIRECTION_E:
			moveEast();
			break;
			
		case DIRECTION_SE:
			moveEast();
			moveSouth();
			break;
		
		case DIRECTION_S:
			moveSouth();
			break;
			
		case DIRECTION_SW:
			moveSouth();
			moveWest();
			break;
		
		case DIRECTION_W:
			moveWest();
			break;
		
		case DIRECTION_NW:
			moveWest();
			moveNorth();
			break;
			
		case DIRECTION_N:
			moveNorth();
			break;
			
		case DIRECTION_NE:
			moveNorth();
			moveEast();
			break;

		default:
			break;
		}
	}
	
	// RANDOM MOVEMENT
	private void randomMove() {
		final int LENGTH_BASE 	= 20;
		final int LENGTH_OFFSET = 20;
		if (moveCnt % movement_offset == 0) {
			movement_offset = Math.abs(rnd.nextInt() % LENGTH_BASE) + LENGTH_OFFSET;
			changeDirection();
		}
	}
	
	// ZIGZAG MOVEMENT
	private void zigzagMove() {
		final int NUMBER_OF_CHANGES = 6;
		if ((int)(moveCnt / BORDER_MAX_X)%2 == 0) {
			if ((int)(coordinates.getX()/(int)(BORDER_MAX_X / NUMBER_OF_CHANGES)) % 2 == 0) {
				direction = DIRECTION_SE;
			} else {
				direction = DIRECTION_NE;
			}
		} else {
			if ((int)(coordinates.getX()/(int)(BORDER_MAX_X / NUMBER_OF_CHANGES)) % 2 == 0) {
				direction = DIRECTION_NW;
			} else {
				direction = DIRECTION_SW;
			}
		}
		
	}
	
	//CIRCLE MOVEMENT
	private void circleMove() {
		final int CIRCLE_MULT = 2;
		switch((int)(moveCnt % (NUMBER_OF_DIRECTIONS * CIRCLE_MULT * TileSet.TILE_WIDTH)) / (CIRCLE_MULT * TileSet.TILE_WIDTH)) {
		case 0:
			direction = DIRECTION_NE;
			break;

		case 1:
			direction = DIRECTION_E;
			break;

		case 2:
			direction = DIRECTION_SE;
			break;

		case 3:
			direction = DIRECTION_S;
			break;

		case 4:
			direction = DIRECTION_SW;
			break;

		case 5:
			direction = DIRECTION_W;
			break;

		case 6:
			direction = DIRECTION_NW;
			break;

		case 7:
			direction = DIRECTION_N;
			break;

		default:
			break;
		}
	}
	
	private void testPrint() {
		for (int i = 0; i < coordinates.getY() / TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < BORDER_MAX_X / TileSet.TILE_WIDTH; j++) {
				System.out.print('.');
			}
			System.out.println();
		}
		for (int i = 0; i < coordinates.getX() / TileSet.TILE_WIDTH; i++) {
			System.out.print('.');
		}
		if (coordinates.getX() >= 0 && coordinates.getY() >= 0) {
			System.out.print('x');
		}
		for (int i = coordinates.getX() / TileSet.TILE_WIDTH + 1; i < BORDER_MAX_X / TileSet.TILE_WIDTH; i++) {
			System.out.print('.');
		}
		System.out.println();
		for (int i = coordinates.getY() / TileSet.TILE_HEIGHT + 1; i < BORDER_MAX_Y / TileSet.TILE_HEIGHT; i++) {
			for (int j = 0; j < BORDER_MAX_X / TileSet.TILE_WIDTH; j++) {
				System.out.print('.');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void move() {
		testPrint();
		if (checkBorder(coordinates.getX(), coordinates.getY())) {
			switch (movementDef) {
			case RANDOM_MOVE:
				randomMove();
				break;

			case ZIGZAG_MOVE:
				zigzagMove();
				break;
				
			case CIRCLE_MOVE:
				circleMove();
				break;
				
			default:
				break;
			}
			moveDirection();
			moveCnt++;
		} else {
			if (coordinates.getX() < BORDER_MIN_X) {
				coordinates.setX(coordinates.getX() + 1);
			} else if (coordinates.getX() > BORDER_MAX_X) {
				coordinates.setX(coordinates.getX() - 1);
			}
			if (coordinates.getY() < BORDER_MIN_Y) {
				coordinates.setY(coordinates.getY() + 1);
			} else if (coordinates.getY() > BORDER_MAX_Y) {
				coordinates.setY(coordinates.getY() - 1);
			}
		}
	}
	
	public Movement(Coordinates coordinates, int movementDef) {
		this.coordinates = coordinates;
		this.movementDef = movementDef;
		rnd.setSeed(System.currentTimeMillis());
	}
	
	public static void main(String[] arg) {
	    Movement movement = new Movement(new Coordinates(-100, 300), Movement.CIRCLE_MOVE);
	    long time1 = System.currentTimeMillis();
	    long time2 = time1;
	    while(true) {
	    	time2 = System.currentTimeMillis();
	    	if (time2-time1 > 10) {
				time1 = time2;
				movement.move();
			}
	    }
	}
}
