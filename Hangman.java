import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Box;

public class Hangman extends JPanel {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Hangman");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Hangman content = new Hangman();
		window.setContentPane(content);
		window.pack();
		window.setVisible(true);
	}
	
	// --------------------------------------
	
	private JPanel imagePanel; // displays the hangman image on screen
	private JLabel wordLabel; // displays the word user is trying to guess
	
	public Hangman() {
		//setPreferredSize(new Dimension(500,500));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.GREEN);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		imagePanel = new ImagePanel();
		add(imagePanel);
		wordLabel = new JLabel("_ _ a_ _ b _ _ e");
		wordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(wordLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(new JLabel("Enter Guess"));
		add(new JTextField(5));
		add(Box.createRigidArea(new Dimension(0,10)));
		add(new JButton("new game"));
	}
	
	private class ImagePanel extends JPanel {
		
		public ImagePanel() {
			setPreferredSize(new Dimension(300,300));
			setBackground(Color.RED);
			setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// decide what image to draw based on the outer class's current state
		}
	}
}