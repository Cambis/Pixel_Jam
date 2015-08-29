package game.view;

import game.control.Player;
import game.model.Board;

import java.awt.BorderLayout;
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

/**
 * Holds all of the GUI elements
 * @author bryerscame
 *
 */
public class MainFrame extends JFrame implements KeyListener{

	public static int BOARD_HEIGHT, BOARD_WIDTH;

	private JMenuBar menuBar;
	private JMenu menu;

	// Displays the board
	private BoardPanel boardPanel;
	private Board board;

	// Displays the current rule
	private JLabel rule;

	private int fireSpeed = 0;

	public MainFrame() {

		setLayout(new BorderLayout());
		setSize(600, 600);
		setJMenuBar(createMenu());

		board = new Board("res/test3.txt");
		boardPanel = new BoardPanel(board);
		add(boardPanel, BorderLayout.CENTER);

		// Make sure we can sees it!
		setVisible(true);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		repaint();
	}

	private JMenuBar createMenu() {

		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("File"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame();
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


	public static void main(String args[]) {
		new MainFrame();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		//System.out.println("In here");

		for (Player p : board.getPlayers()) {
			if (key == p.getLeft())
				p.turnLeft();
			else if (key == p.getRight())
				p.turnRight();
			else if (key == p.getFire()) {
				p.increaseFireSpeed();
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
}
