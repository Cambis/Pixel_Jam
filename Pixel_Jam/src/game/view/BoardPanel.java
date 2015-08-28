package game.view;

import game.control.Player;
import game.model.Board;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Draws the board itself
 *
 * @author bryerscame
 *
 */
public class BoardPanel extends JPanel {

	public BoardPanel() {

	}

	public void paintComponent(Graphics g) {
		Image image = new Board("res/test1.txt").draw();
		g.drawImage(image, 0, 0, null);
	}

	public static void main(String args[]) {
		BoardPanel board = new BoardPanel();
		JFrame frame = new JFrame();
		frame.add(board);
		frame.setVisible(true);
	}
}
