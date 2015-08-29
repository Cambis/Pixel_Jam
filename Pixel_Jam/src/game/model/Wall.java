package game.model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall implements Tile {


	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(x * size, y * size, size, size);
		g.setColor(Color.white);
		g.drawRect(x * size, y * size, size, size);
		return g;
	}

	public HitDetection hitSegment(int x, int y){
		if(x>=y){
			if(y>=Board.tileSize-x){
				//East
				return HitDetection.EAST;
			}else{
				//North
				return HitDetection.NORTH;
			}
		}else{
			if(y>=Board.tileSize-x){
				//South
				return HitDetection.SOUTH;
			}else{
				//West
				return HitDetection.WEST;
			}
		}
	}
}

