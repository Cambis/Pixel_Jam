package game.view;

import game.control.Player;
import game.model.Board;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Draws the board itself
 *
 * @author bryerscame
 *
 */
public class BoardPanel extends JPanel implements ActionListener {

	private Board board;

	private Timer timer;

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




	private void setUpTimer() {

		timer = new Timer(20, this); //tick every 20 milliseconds
	}

	//on timer tick
	@Override
	public void actionPerformed(ActionEvent e) {

		board.gameTick();
		this.repaint();
	}

}
