package game.model;

import game.control.Player;

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
						players.add(new Player(new Point(xPos, yPos), 'a', 'd', 'w'));
						players.add(new Player(new Point(xSize+(xSize-xPos)-1, yPos), 'j', 'l', 'i'));
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

	public HitDirection checkCollisions(Bullet b) {
		int xTile = b.getX() / tileSize;
		int yTile = b.getY() / tileSize;
		if (tiles[xTile][yTile] instanceof Wall) {
			int xPos = b.getX() - xTile;
			int yPos = b.getY() - yTile;

		}
		return null;
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
				}else if (tiles[x][y] instanceof Target) {
					g.setColor(Color.red);
					g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}
				for(Player p: players){
					if(p.getX()==x && p.getY()==y){
						g.drawImage(p.getImage(), x*tileSize, y*tileSize, tileSize, tileSize, null);
					}

					p.drawBullet(g);
				}
			}
		}
		return boardReturn;
	}

	public void gameTick() {

		for (Player player : players) {

			player.moveBullet(this);
		}
	}
}
