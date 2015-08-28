package game.model;

import java.awt.Graphics;

public class Bullet {

	private final int RADIUS = 5;

	private int x;
	private int y;

	private double vx;
	private double vy;

	public Bullet(int xi, int yi, double direction, double speed) {


	}

	public void moveFrame() {

		x += vx;
		y += vy;

		// check for collisions
	}

	public double getSpeed() {

		return Math.sqrt(vx*vx + vy*vy);
	}

	public void setVelocity(double vx, double vy) {

		this.vx = vx;
		this.vy = vy;
	}

	public void setSpeed(double direction, double speed) {


	}

	public void draw(Graphics g) {

		g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
	}
}
