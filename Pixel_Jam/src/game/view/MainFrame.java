package game.view;

import game.model.Board;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

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
public class MainFrame extends JFrame {

	private JMenuBar menuBar;
	private JMenu menu;

	// Displays the board
	private BoardPanel board;

	// Displays the current rule
	private JLabel rule;

	public MainFrame() {

		setLayout(new BorderLayout());
		setSize(600, 600);
		setJMenuBar(createMenu());

		board = new BoardPanel(new Board("res/test1.txt"));
		add(board, BorderLayout.CENTER);

		// Make sure we can sees it!
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
	}

	private JMenuBar createMenu() {

		menuBar = new JMenuBar(); // creates menu bar

		menu = new JMenu("File"); // creates first menu item
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

		menuBar.add(menu); // adds to menu bar

		JMenuItem menuItem = new JMenuItem("New Game");
		menu.add(menuItem);

		return menuBar;
	}

	public static void main(String args[]) {
		new MainFrame();
	}
}
