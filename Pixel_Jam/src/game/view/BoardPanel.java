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

	private Board board;

	public BoardPanel(Board board) {
		this.board = board;
	}

	public void paintComponent(Graphics g) {
		Image image = board.draw();
		g.drawImage(image, 0, 0, null);
	}

	public static void main(String args[]) {

		BoardPanel board = new BoardPanel(new Board("res/test1.txt"));
		JFrame frame = new JFrame();
		frame.add(board);
		frame.setVisible(true);
	}
}
