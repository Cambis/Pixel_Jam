package game.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InitialScreen extends JPanel{

	private JButton start;
	private JFrame frame;

	public InitialScreen() {

		setLayout(new BorderLayout());
		frame = new JFrame();
		start = new JButton("Start");

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainFrame();
			}

		});

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				frame.dispose();
				new MainFrame();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		// add(start, BorderLayout.CENTER);

		frame.setSize(960, 540);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
		frame.repaint();
	}

	public void paintComponent(Graphics g) {
		java.net.URL imageURL = getClass().getResource(
				"/game/assets/titlescreenv2.png");

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		g.drawImage(image, 0, 0, null);
	}

	public static void main(String args[]) {
		new InitialScreen();
	}
}
