package game.model;

import game.view.BoardPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class TrophyTile implements Tile {

	double time = 0;

	private boolean enabled = true;

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {

		time += 0.005;

		double widthScale = Math.sin(time);

		double rx = 128 + 127 * Math.sin(time);
		double gx = rx;
		double bx = 50 + 50 * Math.cos(time);

		g.setColor(new Color((int)rx, (int)gx, (int)bx));
		g.fillOval(x*size + Board.tileSize/2, y*size + size/2, (int) (size/2 * widthScale), size);

		return g;
	}

	@Override
	public void effect(Bullet b) {
		b.getPlayer().addTrophy();
		enabled = false;
	}

}
