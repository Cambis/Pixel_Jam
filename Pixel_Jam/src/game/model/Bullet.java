package game.model;

import game.control.Player;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	public final int RADIUS = 5;

	private double x;
	private double y;

	private double vx;
	private double vy;

	private double initialSpeed;
	private double alpha = 0.0007;

	private Player player;

	int numHits;

	public Bullet(int xi, int yi, double direction, double speed, Player player) {

		x = xi;
		y = yi;
		this.player = player;
		initialSpeed = speed;

		setSpeed(direction, initialSpeed);
	}

	public Player getPlayer() {
		return player;
	}


	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public int GetNumHits() {

		return numHits;
	}

	public void moveFrame(Board board) {

		x += vx;
		y += vy;

		double newSpeed = alpha * initialSpeed + (1-alpha) * getSpeed();

		setSpeed(newSpeed);

		board.getTile(x, y).effect(this);

		HitDetection hitDirection = board.checkCollisions(this);

		if (hitDirection != null) { //if bullet made a collision

			numHits++;

			// if bullet hit top or bottom
			switch(hitDirection){
			case NORTH: case SOUTH:
				bounceVertical(); //flip vertical speed
				if(hitDirection==HitDetection.NORTH)
					y = board.getTileEdge(x, y, hitDirection)+RADIUS;
				else
					y = board.getTileEdge(x, y, hitDirection)-RADIUS;
				break;
			case WEST: case EAST:
				bounceHorizontal(); //flip horizontal speed
				if(hitDirection==HitDetection.EAST)
					x = board.getTileEdge(x, y, hitDirection)-RADIUS;
				else
					x = board.getTileEdge(x, y, hitDirection)+RADIUS;
				break;
//			case NORTH_WEST: case NORTH_EAST: case SOUTH_EAST: case SOUTH_WEST:
//				bounceHorizontal();
//				bounceVertical();
//
//				break;
			}


		}
	}

	public double getSpeed() {

		return Math.sqrt(vx*vx + vy*vy); //magnitude of velocity vector
	}

	public double getInitialSpeed() {

		return initialSpeed; //magnitude of velocity vector
	}

	public void setVelocity(double vx, double vy) {

		this.vx = vx;
		this.vy = vy;
	}

	public void bounceVertical() {

		vy = -vy;
	}

	public void bounceHorizontal() {

		vx = -vx;
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
		g.setColor(GameColors.BULLET);
		g.fillOval(getX() - RADIUS, getY() - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(firstColor);
	}
}
