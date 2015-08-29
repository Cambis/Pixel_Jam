package game.model;

import java.awt.Graphics2D;

public class TrophyTile implements Tile {

	private boolean enabled = true;

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void effect(Bullet b) {
		b.getPlayer().addTrophy();
		enabled = false;
	}

}