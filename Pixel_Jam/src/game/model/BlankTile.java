package game.model;

import java.awt.Color;
import java.awt.Graphics2D;

public class BlankTile implements Tile {

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		return g;
	}

}
