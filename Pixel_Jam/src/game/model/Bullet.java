package game.model;

import game.control.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Bullet {

	public final int RADIUS = 5;

	private double x;
	private double y;

	private double vx;
	private double vy;

	private double initialSpeed;
	private double alpha = 0.0007;

	private Player player;

	private RuleType currentRule;

	int numHits;
	private double distance;
	private long initialTime;

	public Bullet(int xi, int yi, double direction, double speed, Player player) {

		x = xi;
		y = yi;
		this.player = player;
		initialSpeed = speed;

		initialTime = System.currentTimeMillis();

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

	public void setPos(int x, int y){
		this.x=x;
		this.y=y;
	}

	public int getNumHits() {

		return numHits;
	}

	public void moveFrame(Board board) {

		currentRule = board.getRule();

		if (currentRule == RuleType.TIME_TO_FINISH) {
			int time = getRuleValue(currentRule);
			int value = currentRule.getValue();
//			long currentTime = System.currentTimeMillis();
//			double time = (currentTime - initialTime) / 1000;
			System.out.println("TIME: " + time);
			System.out.println("VAL: " + value);

			if (time >= value)
				player.removeBullet();
		}

		x += vx;
		y += vy;

		distance += getSpeed();

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

			GameSounds.playSound("short_chirp.wav");
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

		Font font = new Font("Dialog", Font.ITALIC, Board.tileSize*4/5);

		g.setColor(GameColors.BULLET);
		g.fillOval(getX() - RADIUS, getY() - RADIUS, RADIUS * 2, RADIUS * 2);

		g.setColor(Color.BLACK);
		g.setFont(font);

		if (currentRule != RuleType.NO_RULE) {
			String s = String.valueOf(getRuleValue(currentRule));
//			FontMetrics fm = g.getFontMetrics();
//			Rectangle2D rect = fm.getStringBounds(s, g);
//			g.setColor(GameColors.BACKGROUND);
//			g.fillRect(getX(), getY() - fm.getAscent(), (int) rect.getWidth(),
//					(int) rect.getHeight());
			g.setColor(Color.WHITE);
			g.drawString(s, player.getX(), player.getY());
		}

	}

	public void drawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	}

	/**
	 * Checks if the game is over
	 * @param rule
	 * @return
	 */
	public boolean checkValidWin(RuleType rule) {

		switch (rule) {
		case NO_RULE:
			return true;
		case BOUNCES:
			return numHits >= rule.getValue();
		case DISTANCE:
			return distance/Board.tileSize >= rule.getValue();
		case TIME_ALIVE:
			return (System.currentTimeMillis()-initialTime)/1000 >= rule.getValue();
		case TROPHY:
			return player.getNumTrophies() >= rule.getValue();
		default:
			break;

		}
		return false;
	}

	private int getRuleValue(RuleType rule) {

		switch (rule) {
		case NO_RULE:
			return 0;
		case BOUNCES:
			return numHits;
		case DISTANCE:
			return (int) (distance/Board.tileSize);
		case TIME_ALIVE:
			return (int) ((System.currentTimeMillis()-initialTime)/1000);
		case TIME_TO_FINISH:
			return (int) ((System.currentTimeMillis()-initialTime)/1000);
		case TROPHY:
			return player.getNumTrophies();
		default:
			System.out.println("Unknown rule " + rule);
			break;

		}
		return 0;
	}
}
