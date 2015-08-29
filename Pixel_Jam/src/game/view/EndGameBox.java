package game.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class EndGameBox extends JDialog {

	private JButton next, tryAgain;

	public EndGameBox() {

		setTitle("Game Over");

		setLayout(new FlowLayout());

		next = new JButton("Next");
		tryAgain = new JButton("Try Again");

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(next);
		panel.add(tryAgain);

		setResizable(false);
		setVisible(false);
		add(panel);
		pack();
	}

	public void addNextListener(ActionListener a) {
		next.addActionListener(a);
	}

	public void addTryAgainListener(ActionListener q) {
		tryAgain.addActionListener(q);
	}

	public static void main(String args[]) {
		new EndGameBox().setVisible(true);
	}
}
