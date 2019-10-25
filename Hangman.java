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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JTextField textInputBox; 
	private JButton newGameButton;
	private String message = "welcome to hangman";
	private HangmanGame game;
	
	public Hangman() {
		game = new HangmanGame(6);
		//setPreferredSize(new Dimension(500,500));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.GREEN);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		imagePanel = new ImagePanel();
		add(imagePanel);
		wordLabel = new JLabel(game.getGuessString());
		wordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		add(wordLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(new JLabel("Guess letter (press enter to submit)"));
		textInputBox = new JTextField(5);
		textInputBox.addActionListener(new InputListener());
		add(textInputBox);
		add(Box.createRigidArea(new Dimension(0,10)));
		newGameButton = new JButton("new game");
		newGameButton.addActionListener(new NewGameButtonListener());
		newGameButton.setEnabled(false);
		add(newGameButton);
	}
	
	private class InputListener implements ActionListener {
		// called when user presses enter key in text input box
		public void actionPerformed(ActionEvent e) {
			if (game.isGameOver()) {
				message = "game is over. Start a new game to play again";
				repaint();
				return;
			}
			String userInput = textInputBox.getText();
			textInputBox.setText("");
			// check to make sure text is valid
			
			//System.out.println("user pressed enter");
			game.makeGuess('d');
			if (game.isGameOver()) {
				if (game.hasWon()) {
					message = "congratulations, you win!";
				} else {
					message = "sorry, you lose. The word was: " + game.getWordToGuess() + "\n press new game to play again";
				}
				textInputBox.setEnabled(false);
				newGameButton.setEnabled(true);
			}
			repaint();
		}
	}
	
	private class NewGameButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!game.isGameOver()) {
				message = "you still have to finish this game first";
				repaint();
				return;
			}
			game = new HangmanGame(6);
			wordLabel.setText(game.getGuessString());
			newGameButton.setEnabled(false);
			textInputBox.setEnabled(true);
			textInputBox.setText("");
		}
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
			
			// draw gallows
			int numIncorrectGuesses = game.getNumIncorrectGuesses();
			if (numIncorrectGuesses >= 1) {
				// draw head
			}
			if (numIncorrectGuesses >= 2) {
				// draw torso
			}
			if (numIncorrectGuesses >= 3) {
				// draw left arm
			}
			if (numIncorrectGuesses >= 4) {
				// draw right arm
			}
			if (numIncorrectGuesses >= 5) {
				// draw left leg
			}
			if (numIncorrectGuesses == 6) {
				// draw right leg
			}
			
			// draw message at bottom of screen
			g.drawString(message,20,200);
		}
	}
}