package game.model;

import java.awt.Color;
import java.awt.Graphics2D;

public class SlowPad implements Tile {

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		g.setColor(GameColors.SLOWPAD);
		g.fillOval(x * size, y * size, size, size);
		g.setColor(Color.white);
		g.drawOval((int)((x+0.25) * size), (int)((y+0.25) * size), size/2, size/2);
		return g;
	}

	@Override
	public void effect(Bullet b) {
		b.setSpeed(0.1);
	}



}
