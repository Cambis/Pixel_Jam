package game.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Teleports the bullet to another pad, each pad has a pointer to the other
 *
 * @author bryerscame
 *
 */
public class TeleportPad implements Tile {

	private TeleportPad reciever;
	private boolean isSender = false;
	private int x,y;

	private double time;

	public TeleportPad(boolean isSender, int x, int y, int tileSize) {
		this.isSender = isSender;
		this.x = x + tileSize/2;
		this.y = y + tileSize/2;
	}


	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		if(isSender){
		g.setColor(Color.white);
		}else{
			g.setColor(Color.RED);
		}
		g.fillOval(x * size, y * size, size, size);
		time += 0.01;
		int c = (int) (255 * Math.pow(Math.sin(time), 2));
		g.setColor(new Color(255-c,c,c));
		g.fillOval((int) ((x + 0.25) * size), (int) ((y + 0.25) * size),
				size / 2, size / 2);
		return g;
	}

	public void setReciver(TeleportPad reciever) {
		this.reciever = reciever;
	}

	public Point getPos() {
		return new Point(x,y);
	}

	public TeleportPad getReceiver() {
		return reciever;
	}

	public boolean isSender() {
		return isSender;
	}

	@Override
	public void effect(Bullet b) {
		// TODO Auto-generated method stub
		if(isSender&&reciever!=null){
			b.setPos(reciever.getPos().x, reciever.getPos().y);
		}
	}

}
