package game.model;

import game.view.BoardPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class TrophyTile implements Tile {

	double time = 0;

	private boolean enabled = true;

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {

		time += 0.1;

		double widthScale = Math.sin(time);

		double rx = 255 * Math.sin(time);
		double gx = rx;
		double bx = 100 * Math.cos(time);

		g.setColor(new Color((int)rx, (int)gx, (int)bx));
		g.fillOval(x + Board.tileSize/2, y + Board.tileSize/2, (int) (Board.tileSize/2 * widthScale), Board.tileSize);

		return g;
	}

	@Override
	public void effect(Bullet b) {
		b.getPlayer().addTrophy();
		enabled = false;
	}

}
