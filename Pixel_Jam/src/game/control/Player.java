package game.control;

import game.model.Board;
import game.model.Bullet;
import game.model.GameColors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
public class Player implements KeyListener{

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
	private double speed = 0;
	private static final int MAX_BULLET_HOLD_TIME = 2;

	private static final boolean DEBUG = true;

	private boolean turnLeft, turnRight, firing, fired;

	public Player(Point startPos, char left, char right, char fire) {
		position = startPos;
		this.left = left;
		this.right = right;
		this.fire = fire;
	}

	public void turnLeft() {
		rotation = (rotation > ROTATION_MIN) ? rotation - 1 : ROTATION_MIN;
	}

	public void turnRight() {
		rotation = (rotation < ROTATION_MAX) ? rotation + 1 : ROTATION_MAX;
	}

	public void fire() {

		speed = (speed < MAX_BULLET_HOLD_TIME) ? speed : MAX_BULLET_HOLD_TIME;

		// Create new bullet
		bullet = new Bullet(position.x + Board.tileSize / 2, position.y
				+ Board.tileSize / 2, rotation, speed);

		speed = 0;
	}

	public void increaseFireSpeed() {
		speed += 0.1;
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

		double rotationRequired = Math.toRadians(rotation + 90);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		image = op.filter(image, null);

		return image;
	}

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	public final char getLeft() {
		return left;
	}

	public final char getRight() {
		return right;
	}

	public final char getFire() {
		return fire;
	}

	public static void main(String args[]) {
		Player player = new Player(new Point(0, 0), 'a', 'd', 'w');
		System.out.println(player.getImage().toString());
	}

	public void moveBullet(Board board) {

		if (bullet != null)
			bullet.moveFrame(board);
	}

	public void drawBullet(Graphics g) {

		if (bullet != null) {

			// System.out.println("Drawing Bullet");
			bullet.draw(g);
		}
	}

	public void draw(Graphics g) {

		int x = position.x;
		int y = position.y;

		int w = Board.tileSize;
		int h = Board.tileSize;

		g.setColor(GameColors.PLAYER);
		g.fillOval(x, y, w, h);

		int x2 = (int)(w/2 * Math.cos(rotation / 180 * Math.PI));
		int y2 = (int)(h/2 * Math.sin(rotation / 180 * Math.PI));

		g.setColor(GameColors.PLAYERFACE);
		g.drawLine(x + w / 2, y + h / 2, x + w / 2 + x2, y + h / 2 + y2);

	}

	public void playerTick() {
		if (turnLeft)
			turnLeft();
		else if (turnRight)
			turnRight();
		else if (firing) {
			increaseFireSpeed();
			firing = false;
		}
		else if (fired) {
			fire();
			fired = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		char key = e.getKeyChar();

		if (key == left)
			turnLeft = true;
		else if (key == right)
			turnRight = true;
		else if (key == fire)
			firing = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();

		turnLeft = turnRight = false;

		if (key == fire) {
			fired = true;
		}
	}
}
