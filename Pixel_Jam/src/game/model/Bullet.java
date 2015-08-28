package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	private final int RADIUS = 5;

	private int x;
	private int y;

	private double vx;
	private double vy;

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

	public void moveFrame(Board board) {

		x += vx;
		y += vy;

		//board.checkCollisions(this);
	}

	public double getSpeed() {

		return Math.sqrt(vx*vx + vy*vy);
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

		vx = speed * Math.cos(direction);
		vy = speed * Math.sin(direction);
	}

	public void draw(Graphics g) {

		Color firstColor = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(firstColor);
	}
}
