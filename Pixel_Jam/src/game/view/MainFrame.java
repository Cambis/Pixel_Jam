package game.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
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

	private BoardPanel board;

	public MainFrame() {

		setLayout(new BorderLayout());
		setSize(600, 600);
		setJMenuBar(createMenu());

		// Make sure we can sees it!
		setVisible(true);
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
