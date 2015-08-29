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
	
			double widthScale = .3 + .5 * Math.sin(time);
	
			double rx = 50 + 200 * Math.pow(Math.sin(time/2), 2);
			double gx = rx;
			double bx = 255 * Math.max(0, -Math.cos(time-1));
	
			g.setColor(new Color((int)rx, (int)gx, (int)(bx)));
			g.fillOval((int) (x*size + size/2 - widthScale*size/2), y*size + size/2, (int) (size * widthScale), size);
	
	//		g.setColor(new Color((int)rx/2, (int)gx/2, (int)bx/2));
	//		g.drawOval((int) (x*size + size/2 - widthScale*size/4), y*size + size/2, (int) (size/2 * widthScale), size/2);
		}
			
		return g;
	}

	@Override
	public void effect(Bullet b) {
		if(enabled){
			b.getPlayer().addTrophy();
			System.out.println("YOU GOT A MOTHER FUCKING TROPHY!!");
		}
		enabled = false;
	}

}
