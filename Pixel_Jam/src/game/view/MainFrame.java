package game.view;

import game.control.Player;
import game.model.Board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Holds all of the GUI elements
 *
 * @author bryerscame
 *
 */
public class MainFrame extends JFrame implements KeyListener {

	public static int BOARD_HEIGHT, BOARD_WIDTH;

	private JMenuBar menuBar;
	private JMenu menu;

	// Displays the board
	private BoardPanel boardPanel;
	private Board board;

	private EndGameBox endGame;

	// Displays the current rule
	private JLabel rule;
	private JLabel player1Score, player2Score;

	private Timer timer;

	public MainFrame() {

		setLayout(new BorderLayout());
		setJMenuBar(createMenu());

		board = new Board("res/test3.txt");
		boardPanel = new BoardPanel(board);
		add(boardPanel, BorderLayout.CENTER);


		board.setParentPanel(boardPanel);

		setSize(new Dimension(board.getXSize(), board.getYSize() + 50));

		setUpTimer();

		player1Score = new JLabel(String.valueOf(board.getPlayers().get(0).getScore()));
		player1Score.setHorizontalAlignment(JLabel.CENTER);
		player2Score = new JLabel(String.valueOf(board.getPlayers().get(1).getScore()));
		player2Score.setHorizontalAlignment(JLabel.CENTER);
		JPanel playerPanel = new JPanel(new GridLayout());
		playerPanel.setSize(board.getXSize(), 50);
		playerPanel.add(player1Score);
		playerPanel.add(player2Score);

		add(playerPanel, BorderLayout.SOUTH);

		// Make sure we can sees it!
		setVisible(true);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(board.getPlayers().get(0));
		addKeyListener(board.getPlayers().get(1));
		repaint();
	}

	private JMenuBar createMenu() {

		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("File"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainFrame main = new MainFrame();

			}

		});

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		menu.add(newGame);
		menu.add(quit);

		return menuBar;
	}

	private void setUpTimer() {

		endGame = new EndGameBox();
		endGame.setLocationRelativeTo(boardPanel);
		endGame.setVisible(false);
		endGame.addTryAgainListener(tryAgainListener);

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (boardPanel.gameOver()) {
					endGame.setVisible(true);
					timer.stop();
				}
			}

		});

		timer.start();
	}
	public static void main(String args[]) {
		new MainFrame();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		// System.out.println("In here");

		for (Player p : board.getPlayers()) {
			if (key == p.getLeft()) {
				p.turnLeft();
			} else if (key == p.getRight()) {
				p.turnRight();
			} else if (key == p.getFire()) {
				p.updateFireSpeed();
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();

		for (Player p : board.getPlayers())
			if (key == p.getFire()) {
				p.fire();
			}
	}

	private ActionListener tryAgainListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			endGame.dispose();
			new MainFrame();
		}

	};
}
