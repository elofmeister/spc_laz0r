import java.awt.image.BufferedImage;
import java.util.*;

import javafx.scene.image.Image;

public class CollisionDetector {

	private long lastCollisionWith;
	private int enemy;
	
	public CollisionDetector() {
	}

	public boolean isCollide(Bullet bullet, Ships ship, List<Enemy> enemies) {
		boolean bretval = false;
		int bulletXmin, bulletXmax, bulletYmin, bulletYmax;
		int targetXmin, targetXmax, targetYmin, targetYmax;

		if (bullet.getDirection() == Bullet.MOVE_NORTH || bullet.getDirection() == Bullet.MOVE_SOUTH) {
			bulletXmin = bullet.getCoordinates().getX() + 32;
			bulletXmax = bullet.getCoordinates().getX() + 40;
			bulletYmin = bullet.getCoordinates().getY() + 16;
			bulletYmax = bullet.getCoordinates().getY() + 48;
		} else {
			bulletXmin = bullet.getCoordinates().getX() + 20;
			bulletXmax = bullet.getCoordinates().getX() + 52;
			bulletYmin = bullet.getCoordinates().getY() + 27;
			bulletYmax = bullet.getCoordinates().getY() + 35;
		}
		if (bullet.getIdentity() != Player.ID) {
			/*
			 * Collision with ship
			 */
			targetXmin = ship.getCoordinates().getX();
			targetXmax = ship.getCoordinates().getX() + TileSet.TILE_WIDTH;
			targetYmin = ship.getCoordinates().getY();
			targetYmax = ship.getCoordinates().getY() + TileSet.TILE_HEIGHT;
			if ((
					(targetXmin < bulletXmin && bulletXmin < targetXmax) ||
					(targetXmin < bulletXmax && bulletXmax < targetXmax)
				)
					&&
				(
					(targetYmin < bulletYmin && bulletYmin < targetYmax) ||
					(targetYmin < bulletYmax && bulletYmax < targetYmax)
				)) {
				bretval = true;
				lastCollisionWith = Player.ID;
				for (int i = 0; i < enemies.size(); i++) {
					if (enemies.get(i).getIdentity() == bullet.getIdentity()) {
						enemy = i;
						break;
					}
				}
			}
		} else {
			/*
			 * Collision with enemies
			 */
			for (int i = 0; i < enemies.size(); i++) {
				targetXmin = enemies.get(i).getCoordinates().getX();
				targetXmax = enemies.get(i).getCoordinates().getX() + TileSet.TILE_WIDTH;
				targetYmin = enemies.get(i).getCoordinates().getY();
				targetYmax = enemies.get(i).getCoordinates().getY() + TileSet.TILE_HEIGHT;
				if ((
						(targetXmin < bulletXmin && bulletXmin < targetXmax) ||
						(targetXmin < bulletXmax && bulletXmax < targetXmax)
					)
						&&
					(
						(targetYmin < bulletYmin && bulletYmin < targetYmax) ||
						(targetYmin < bulletYmax && bulletYmax < targetYmax)
					)) {
					bretval = true;
					lastCollisionWith = enemies.get(i).getIdentity();
					enemy = i;
					break;
				}
			}
		}
		return bretval;
	}
	
	public long getLastCollsion() {
		return lastCollisionWith;
	}
	
	public int getEnemy() {
		return enemy;
	}
}
