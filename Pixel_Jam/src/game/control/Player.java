package game.control;

import game.model.Board;
import game.model.Bullet;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;

/**
 * Represents a player on screen.
 *
 * @author bryerscame
 *
 */
public class Player implements KeyListener {

	// Player's position
	private Point position;

	// Current angle the player is facing
	private double rotation;
	private static final double ROTATION_MIN = 0;
	private static final double ROTATION_MAX = 180;

	// Keys used to control player movement
	private final char left, right, fire;

	private long start, end;

	private Bullet bullet;
	private double bulletHoldTime = 0;
	private static final int MAX_BULLET_HOLD_TIME = 5;

	private static final boolean DEBUG = true;

	public Player(Point startPos, char left, char right, char fire) {
		position = startPos;
		this.left = left;
		this.right = right;
		this.fire = fire;
	}

	public final double getLookAngle() {
		return rotation;
	}

	public BufferedImage getImage() {
		java.net.URL imageURL = getClass().getResource(
				"/game/assets/Playerv3.png");

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (DEBUG)
			System.out.println(e.getKeyChar());

		char key = e.getKeyChar();

		/**
		 * ROTATION_MIN < rotation < ROTATION_MAX
		 */

		if (key == left) {
			rotation = (rotation > ROTATION_MIN) ? rotation - 1 : ROTATION_MIN;
		} else if (key == right) {
			rotation = (rotation < ROTATION_MAX) ? rotation + 1 : ROTATION_MAX;
		} else if (key == fire) {
			bulletHoldTime = (bulletHoldTime < MAX_BULLET_HOLD_TIME) ? bulletHoldTime + 1
					: MAX_BULLET_HOLD_TIME;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		// Fire the bullet!
		if (e.getKeyChar() == fire) {

			// Create new bullet
			bullet = new Bullet(position.x, position.y, rotation,
					bulletHoldTime);

			bulletHoldTime = 0;
		}
	}

	public static void main(String args[]) {
		Player player = new Player(new Point(0, 0), 'a', 'd', 'w');
		System.out.println(player.getImage().toString());
	}

	public void moveBullet(Board board) {

		bullet.moveFrame(board);
	}
}
