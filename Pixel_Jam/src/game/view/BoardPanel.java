package game.view;

import game.control.Player;
import game.model.Board;
import game.model.Bullet;
import game.model.RuleType;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Draws the board itself
 *
 * @author bryerscame
 *
 */
public class BoardPanel extends JPanel implements ActionListener {

	public enum BoardState {
		START, PLAY;
	}

	private BoardState state;

	private Board board;

	private Timer timer;

	private Player winner = null;

	private RuleType rule;

	public BoardPanel(Board board) {
		this.board = board;
		state = BoardState.PLAY;
		setUpTimer();
	}

	public void updateState(BoardState state) {
		this.state = state;
	}

	public void paintTitleScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, board.getXSize() * 2 * Board.tileSize, board.getYSize() * Board.tileSize);
	}

	public void paintComponent(Graphics g) {
		if (state.equals(BoardState.START))
			paintTitleScreen(g);

		else if (state.equals(BoardState.PLAY)) {
			Image image = board.draw();
			g.drawImage(image, 0, 0, null);
		}
	}

	public Player getWinner() {
		return winner;
	}

	private void setUpTimer() {

		timer = new Timer(1, this); // tick every 1 milliseconds
		timer.start();
	}
	// on timer tick
	@Override
	public void actionPerformed(ActionEvent e) {

		board.gameTick();
		this.repaint();
	}


	public void setBoard(Board board) {
		this.board = board;
	}

	public void endGame(Player p) {
		winner = p;
		timer.stop();
	}
}
