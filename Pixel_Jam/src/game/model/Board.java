package game.model;

import game.control.Player;
import game.view.BoardPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

	private Tile tiles[][];
	private List<Player> players = new ArrayList<Player>();
	private int xSize;
	private int ySize;
	public static final int tileSize = 16;

	private BoardPanel parentPanel;

	public Board(String filename) {

		try {
			Scanner s = new Scanner(new File(filename));
			xSize = s.nextInt();
			ySize = s.nextInt();
			tiles = new Tile[xSize*2][ySize];
			int xPos = 0;
			int yPos = 0;
			while (s.hasNext()) {
				if (s.hasNextInt()) {
					int i = s.nextInt();
					if (i == 0) {
						tiles[xPos][yPos] = new BlankTile();
						tiles[xSize+(xSize-xPos)-1][yPos] = new BlankTile();
					} else if (i == 1) {
						tiles[xPos][yPos] = new Wall();
						tiles[xSize+(xSize-xPos)-1][yPos] = new Wall();
					} else {
						System.out.println("Error loading file (invalid int - "
								+ i + ", xPos = " + xPos + ", yPox = " + yPos
								+ ")");
					}
					// Else square is a player or target
				} else {
					String tok = s.next();
					// Checks string was 1 char
					if (tok.length() != 1) {
						s.close();
						throw new IOException("Invalid string");
					}
					// gets char
					char c = tok.charAt(0);
					if (c == 'T') {
						tiles[xPos][yPos] = new Target();
						tiles[xSize+(xSize-xPos)-1][yPos] = new Target();
					} else if (c == 'P') {
						// Create player at this point
						players.add(new Player(new Point(xPos*tileSize, yPos*tileSize), 'd', 'a', 'w'));
						players.add(new Player(new Point((xSize+(xSize-xPos)-1)*tileSize, yPos*tileSize), 'l', 'j', 'i'));
						tiles[xPos][yPos] = new BlankTile();
					}
				}
				//Updates x and y
				xPos++;
				if(xPos>=xSize){
					xPos=0;
					yPos++;
				}
			}
		} catch (IOException e) {
		}
	}

	public void setParentPanel(BoardPanel panel) {

		parentPanel = panel;
	}

	public HitDetection checkCollisions(Bullet b) {
		if(b.getX()<0 || b.getY()<0 || b.getX()>xSize*2*tileSize || b.getY()>ySize*tileSize){
			System.out.println("Out of screen");
			return null;
		}
		int xTile = b.getX() / tileSize;
		int yTile = b.getY() / tileSize;
		if(tiles[xTile][yTile] instanceof Wall){
			int xPos = b.getX() - xTile;
			int yPos = b.getY() - yTile;
			System.out.println("IN WALL!!!");
			return ((Wall)tiles[xTile][yTile]).hitSegment(xPos, yPos);
		}

		int right = b.getX()+b.RADIUS;
		int left = b.getX()-b.RADIUS;
		int bot = b.getY()+b.RADIUS;
		int top = b.getY()-b.RADIUS;

		if(tiles[xTile][yTile] instanceof Target) {

			parentPanel.endGame();
			return HitDetection.TARGET;
		}

		if(top<(yTile)*tileSize){
			//Colliding with tile above
			//Check Top-Left
//			if(left<(xTile)*tileSize
//					&& (tiles[xTile-1][yTile-1] instanceof Wall)){
//				if(dist(left, top, xTile*tileSize, yTile*tileSize)<=b.RADIUS){
//					System.out.println("NW");
//					return HitDetection.NORTH_WEST;
//				}
//			}
//			else if(right>(xTile+1)*tileSize
//					&& (tiles[xTile+1][yTile-1] instanceof Wall)){
//				//Colliding with tile right
//				if(dist(right, top, (xTile+1)*tileSize, yTile*tileSize)<=b.RADIUS){
//					System.out.println("NE");
//					return HitDetection.NORTH_EAST;
//				}
//			}
			if(tiles[xTile][yTile-1] instanceof Wall){
				System.out.println("N");
				return HitDetection.NORTH;
			}
		}
		else if(bot>(yTile+1)*tileSize){
			//Colliding with tile below
//			if(left<(xTile)*tileSize
//					&& (tiles[xTile-1][yTile+1] instanceof Wall)){
//				if(dist(left, bot, xTile*tileSize, (yTile+1)*tileSize)<=b.RADIUS){
//					System.out.println("Sw");
//					return HitDetection.SOUTH_WEST;
//				}
//			}
//			else if(right>(xTile+1)*tileSize
//					&& (tiles[xTile+1][yTile+1] instanceof Wall)){
//				//Colliding with tile right
//				if(dist(right, bot, (xTile+1)*tileSize, (yTile+1)*tileSize)<=b.RADIUS){
//					System.out.println("SE");
//					return HitDetection.SOUTH_EAST;
//				}
//			}
			if(tiles[xTile][yTile+1] instanceof Wall){
				System.out.println("S");
				return HitDetection.SOUTH;
			}
		}
		if(left<(xTile)*tileSize
				&& (tiles[xTile-1][yTile] instanceof Wall)){
			//Colliding with tile left
			System.out.println("W");
			return HitDetection.WEST;
		}
		else if(right>(xTile+1)*tileSize
				&& (tiles[xTile+1][yTile] instanceof Wall)){
			//Colliding with tile right
			System.out.println("E");
			return HitDetection.EAST;
		}
		return null;
	}

	/**
	 * Helper method:
	 * returns distance between two points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private double dist(int x1, int y1, int x2, int y2){
		int distX = Math.abs(x1-x2);
		int distY = Math.abs(y1-y2);
		return Math.sqrt(distX*distX+distY*distY);
	}

	public int getTileEdge(double x, double y, HitDetection dir){
		int xTile = (int)x / tileSize;
		int yTile = (int)y / tileSize;
		if(tiles[xTile][yTile] instanceof Wall){
			switch(dir){
			case NORTH:
				yTile++;
			case SOUTH:
				yTile--;
			case WEST:
				xTile++;
			case EAST:
				xTile--;
			}
		}
		switch(dir){
		case NORTH:
			return yTile*tileSize;
		case SOUTH:
			return (yTile+1)*tileSize;
		case WEST:
			return xTile*tileSize;
		case EAST:
			return (xTile+1)*tileSize;
		}
		return -1;

	}

	public List<Player> getPlayers(){
		return players;
	}

	public BufferedImage draw() {
		BufferedImage boardReturn = new BufferedImage(tileSize * (xSize*2), tileSize * ySize,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) boardReturn.getGraphics();
		g.fillRect(0,0,tileSize * (xSize*2), tileSize * ySize);
		for (int x = 0; x < xSize*2; x++) {
			for (int y = 0; y < ySize; y++) {
				if (tiles[x][y] instanceof Wall) {
					g.setColor(Color.black);
					g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
					g.setColor(Color.WHITE);
					g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}else if (tiles[x][y] instanceof Target) {
					g.setColor(Color.red);
					g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
					g.setColor(Color.WHITE);
					g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}
				for(Player p: players){
					if(p.getX()/tileSize==x && p.getY()/tileSize==y){
						//g.drawImage(p.getImage(), x*tileSize, y*tileSize, tileSize, tileSize, null);

						p.draw(g);
					}

					p.drawBullet(g);
				}
			}
		}
		return boardReturn;
	}

	public void gameTick() {

		for (Player player : players) {
			player.playerTick();
			player.moveBullet(this);
		}
	}

	public final int getXSize() {
		return tileSize * (xSize * 2);
	}

	public final int getYSize() {
		return tileSize * ySize;
	}

	public enum Direction{
		NORTH,SOUTH,EAST,WEST;
	}
}
