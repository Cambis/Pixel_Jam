package game.model;

import game.view.BoardPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class TrophyTile implements Tile {

	double time = 0;

	private boolean enabled = true;

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {

		if(enabled) {
			time += 0.01;

			double widthScale = .5 + .5 * Math.sin(time);

			double rx = 50 + 200 * Math.pow(Math.sin(time/2), 2);
			double gx = rx;
			double bx = 255 * Math.max(0, -Math.cos(time));

			double sc = 0.8;

			double margin = (1-sc)*size/2;

			g.setColor(new Color((int)rx, (int)gx, (int)(bx)));
			g.fillOval((int) (x*size + size/2 + margin - widthScale*size/2), (int)(y*size + margin + 1), (int) (size * widthScale*sc), (int)(size*sc));

			g.setColor(Color.YELLOW.darker());
			g.drawOval((int) (x*size + size/2 + margin - widthScale*size/2), (int)(y*size + margin + 1), (int) (size * widthScale*sc), (int)(size*sc));
			g.drawOval((int) (x*size + size/2 + margin*sc - widthScale*size/4), (int) (y*size + margin + 1 + size*sc/4), (int) (size/2 * widthScale * sc), (int)(size*sc/2));
		}

		return g;
	}

	@Override
	public void effect(Bullet b) {
		if(enabled){
			b.getPlayer().addTrophy();
		}
		enabled = false;
	}

}
