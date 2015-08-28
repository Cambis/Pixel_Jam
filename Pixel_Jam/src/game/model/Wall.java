package game.model;

public class Wall implements Tile {

	public HitDirection hitSegment(int x, int y){
		if(x>=y){
			if(y>=Board.tileSize-x){
				//East
				return HitDirection.EAST;
			}else{
				//North
				return HitDirection.NORTH;
			}
		}else{
			if(y>=Board.tileSize-x){
				//South
				return HitDirection.SOUTH;
			}else{
				//West
				return HitDirection.WEST;
			}
		}
	}

	//private HitDirection hitDirection;
	public enum HitDirection {

		NORTH,
		SOUTH,
		EAST,
		WEST

	}
}

