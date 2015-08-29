package game.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Teleports the bullet to another pad, each pad has a pointer to the other
 *
 * @author bryerscame
 *
 */
public class TeleportPad implements Tile {

	private TeleportPad reciever;

	public TeleportPad() {

	}

	public TeleportPad(TeleportPad reciever) {
		this.reciever = reciever;
	}

	@Override
	public Graphics2D draw(int x, int y, int size, Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval(x * size, y * size, size, size);
		g.setColor(Color.BLACK);
		g.drawOval((int) ((x + 0.25) * size), (int) ((y + 0.25) * size),
				size / 2, size / 2);
		return g;
	}

	public void setReciver(TeleportPad reciever) {
		this.reciever = reciever;
	}

	public TeleportPad getReceiver() {
		return reciever;
	}

	@Override
	public void effect(Bullet b) {
		// TODO Auto-generated method stub

	}

}
