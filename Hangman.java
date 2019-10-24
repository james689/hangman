import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

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
	
	public Hangman() {
		setPreferredSize(new Dimension(500,500));
	}
}