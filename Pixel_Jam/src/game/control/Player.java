package game.control;

import game.model.Board;
import game.model.Bullet;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
				"/game/assets/images/Playerv3.png");

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	public final int getX() {
		return position.x;
	}

	public final int getY() {
		return position.y;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (DEBUG)
			System.out.println(e.getKeyChar());

		if (e.getKeyChar() == left || e.getKeyChar() == right
				|| e.getKeyChar() == fire) {
			start = System.currentTimeMillis();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (DEBUG)
			System.out.println(e.getKeyChar());

		// Left key press
		if (e.getKeyChar() == left) {

			end = System.currentTimeMillis();

			// Calculate difference
			double difference = end - start;

			// Change the rotation, must be < MAX
			rotation = (rotation < ROTATION_MAX) ? rotation + difference
					: ROTATION_MAX;
		}

		// Right key press
		else if (e.getKeyChar() == right) {

			end = System.currentTimeMillis();

			// Calculate difference
			double difference = end - start;

			// Change the rotation, must be > MIN
			rotation = (rotation > ROTATION_MIN) ? rotation - difference
					: ROTATION_MIN;
		}

		// Fire the bullet!
		else if (e.getKeyChar() == fire) {

			end = System.currentTimeMillis();

			// Calculate difference
			double difference = end - start;

			// Create new bullet
			bullet = new Bullet(position.x, position.y, rotation, difference);
		}
	}

	public static void main(String args[]) {
		Player player = new Player(new Point(0, 0), 'a', 'd', 'w');
		System.out.println(player.getLookAngle());
	}

	public void moveBullet(Board board) {

		bullet.moveFrame(board);
	}
}
