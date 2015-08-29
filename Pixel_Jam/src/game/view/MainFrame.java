package game.view;

import game.control.Player;
import game.model.Board;
import game.model.RuleType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

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

	public static final int TARGET_SCORE = 100;
	public static final int TROPHY_SCORE = 20;

	// Levels
	private final String[] levels = new String[] { "CallumLvl.txt", "camerons_level.txt", "Lohit.txt" };

	// Top menu bar
	private JMenuBar menuBar;
	private JMenu menu;

	// Displays the board
	private BoardPanel boardPanel;
	private Board board;

	// This pops up after a game is won
	private EndGameBox endGame;

	// Current rule
	private RuleType rule;

	// Displays the current rule
	private JLabel ruleLabel;

	// Displays player scores
	private JLabel player1Score, player2Score;

	// Used to check for game over and update player scores
	private Timer timer;

	public MainFrame() {
		init();
	}

	private void init() {

		setLayout(new BorderLayout());
		setJMenuBar(createMenu());

		// Add board
		changeBoard("CallumLvl.txt", null);
//		board = new Board("res/CallumLvl.txt");
//		boardPanel = new BoardPanel(board);
//		add(boardPanel, BorderLayout.CENTER);
//		board.setParentPanel(boardPanel);
//		setSize(new Dimension(board.getXSize(), board.getYSize() + 100));

		// Add rule label
		JPanel rulePanel = new JPanel();
		ruleLabel = new JLabel("DICKS", JLabel.CENTER);
		rulePanel.add(ruleLabel);
		add(rulePanel, BorderLayout.NORTH);

		// Set up player scores
		player1Score = new JLabel(String.valueOf(board.getPlayers().get(0)
				.getScore()));
		player1Score.setHorizontalAlignment(JLabel.CENTER);
		player2Score = new JLabel(String.valueOf(board.getPlayers().get(1)
				.getScore()));
		player2Score.setHorizontalAlignment(JLabel.CENTER);
		JPanel playerPanel = new JPanel(new GridLayout());

		playerPanel.setSize(board.getXSize(), 50);
		playerPanel.add(player1Score);
		playerPanel.add(player2Score);
		add(playerPanel, BorderLayout.SOUTH);

		// Set up the timer (updates score and checks for game over).
		setUpTimer();

		// Make sure we can sees it!
		setVisible(true);

		this.setLocationRelativeTo(null);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(board.getPlayers().get(0));
		addKeyListener(board.getPlayers().get(1));
		repaint();
	}

	/**
	 * Change board and rule, called when user presses next
	 *
	 * @param board
	 * @param rule
	 */
	private void changeBoard(String level, RuleType rule) {

//		this.board = new Board("res/" + level);
//		boardPanel.setBoard(board);
//		this.rule = rule;

		this.board = new Board("res/" + level);
		boardPanel = new BoardPanel(board);
		add(boardPanel, BorderLayout.CENTER);
		board.setParentPanel(boardPanel);
		setSize(new Dimension(board.getXSize(), board.getYSize() + 100));

		this.rule = rule;

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

	/**
	 * Updates player score
	 */
	private void updateScore() {
		Player player1 = board.getPlayers().get(0);
		Player player2 = board.getPlayers().get(1);

		player1Score.setText(String.valueOf(player1.getScore()));
		player2Score.setText(String.valueOf(player2.getScore()));
	}

	private void setUpTimer() {

		endGame = new EndGameBox();
		endGame.setLocationRelativeTo(boardPanel);
		endGame.setVisible(false);
		endGame.addTryAgainListener(tryAgainListener);
		endGame.addNextListener(nextListener);

		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				updateScore();

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
			dispose();
			new MainFrame();
		}

	};

	private ActionListener nextListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			endGame.dispose();
			int levelNumber = randomNumber(0, levels.length);
			int ruleNumber = randomNumber(0, RuleType.values().length);

			changeBoard(levels[levelNumber], RuleType.values()[ruleNumber]);
		}

	};

	/**
	 * Gets a random number between a max and a min
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
