package game.control;

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

	// Keys used to control player movement
	private char left, right;

	private long start, end;

	private static final boolean DEBUG = true;

	public Player(Point startPos, char left, char right) {
		position = startPos;
		this.left = left;
		this.right = right;
	}

	public final double getLookAngle() {
		return rotation;
	}

	public BufferedImage getImage() {
		java.net.URL imageURL = getClass().getResource("/game/assets/images/Playerv3.png");

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (DEBUG)
			System.out.println(e.getKeyChar());

		if (e.getKeyChar() == left || e.getKeyChar() == right) {
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

			// Change the rotation, must be < 360
			rotation = (rotation < 360) ? rotation + difference : 360;
		}

		// Right key press
		else if (e.getKeyChar() == right) {

			end = System.currentTimeMillis();

			// Calculate difference
			double difference = end - start;

			// Change the rotation, must be > 0
			rotation = (rotation > 0) ? rotation - difference : 0;
		}
	}

	public static void main(String args[]) {
		new Player(new Point(0, 0), 'A', 'D');
	}
}
