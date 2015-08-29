package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	public final int RADIUS = 5;

	private double x;
	private double y;

	private double vx;
	private double vy;

	int numHits;

	public Bullet(int xi, int yi, double direction, double speed) {

		x = xi;
		y = yi;

		setSpeed(direction, speed);
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

		HitDetection hitDirection = board.checkCollisions(this);

		if (hitDirection != null) { //if bullet made a collision

			numHits++;

			// if bullet hit top or bottom
			if (hitDirection == HitDetection.NORTH || hitDirection == HitDetection.SOUTH) {
				bounceVertical(); //flip vertical speed
				if(hitDirection==HitDetection.NORTH)
					y = board.getTileEdge(x, y, hitDirection)+RADIUS;
				else
					y = board.getTileEdge(x, y, hitDirection)-RADIUS;

			}
			else if (hitDirection == HitDetection.EAST || hitDirection == HitDetection.WEST) {

				bounceHorizontal(); //flip horizontal speed
				x = board.getTileEdge(x, y, hitDirection);
			}
//			if (hitDirection == HitDetection.NORTH) {
//
//				bounceVertical();
//				y = Math.floor(y/Board.tileSize)*board.tileSize-5;
//			}
//			else if (hitDirection == HitDetection.SOUTH) {
//
//				bounceVertical();
//				y = Math.ceil(y/Board.tileSize)*board.tileSize;
//			}
//			else if (hitDirection == HitDetection.WEST) {
//
//				bounceHorizontal();
//				x = Math.floor(x/Board.tileSize)*board.tileSize-5;
//			}
//			else if (hitDirection == HitDetection.EAST) {
//
//				bounceHorizontal();
//				x = Math.ceil(x/Board.tileSize)*board.tileSize;
//			}

			//System.out.println(hitDirection);
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
		g.setColor(Color.ORANGE);
		g.fillOval(getX() - RADIUS, getY() - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(firstColor);
	}
}
