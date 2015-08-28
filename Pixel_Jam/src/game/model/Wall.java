package game.model;

public class Wall implements Tile {

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

