package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	private final int RADIUS = 5;

	private int x;
	private int y;

	private double vx;
	private double vy;

	int numHits;

	public Bullet(int xi, int yi, double direction, double speed) {

		x = xi;
		y = yi;

		setSpeed(direction, speed);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int GetNumHits() {

		return numHits;
	}

	public void moveFrame(Board board) {

		x += vx;
		y += vy;

		System.out.println("pos:[" + x + "," + y + "]");

		HitDirection hitDirection = board.checkCollisions(this);

		if (hitDirection != null) { //if bullet made a collision

			numHits++;

			// if bullet hit top or bottom
			if (hitDirection == HitDirection.NORTH || hitDirection == HitDirection.SOUTH) {

				bounceVertical(); //flip vertical speed
			}
			else if (hitDirection == HitDirection.EAST || hitDirection == HitDirection.WEST) {

				bounceHorizontal(); //flip horizontal speed
			}
		}
	}

	public double getSpeed() {

		return Math.sqrt(vx*vx + vy*vy); //magnitude of velocity vector
	}

	public void setVelocity(double vx, double vy) {

		this.vx = vx;
		this.vy = vy;
	}

	public void bounceVertical() {

		vx = -vx;
	}

	public void bounceHorizontal() {

		vy = -vy;
	}

	public void setSpeed(double direction, double speed) {

		vx = speed * Math.cos(direction / 180 * Math.PI);
		vy = speed * Math.sin(direction / 180 * Math.PI);
	}

	public void setSpeed(double speed) {

		setSpeed(getDirection(), speed);
	}

	public double getDirection() {

		return Math.atan2(vy, vx) / Math.PI * 180;
	}

	public void draw(Graphics g) {

		Color firstColor = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(firstColor);
	}
}
