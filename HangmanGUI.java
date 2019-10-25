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

public class HangmanGUI extends JPanel {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Hangman");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HangmanGUI content = new HangmanGUI();
		window.setContentPane(content);
		window.pack();
		window.setVisible(true);
	}
	
	// --------------------------------------
	
	private JLabel wordLabel; // displays the word the player is trying to guess
	private JTextField textInputBox; 
	private JButton newGameButton;
	private String message = "welcome to hangman";
	private HangmanGame game;
	
	public HangmanGUI() {
		game = new HangmanGame(6); // start the player in a new game when GUI is created
		
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.GREEN);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		wordLabel = new JLabel(game.getGuessString());
		wordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		
		textInputBox = new JTextField(5);
		textInputBox.addActionListener(new TextInputBoxListener());
		
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameButtonListener());
		newGameButton.setEnabled(false);
		
		add(new ImagePanel());
		add(wordLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(new JLabel("Guess letter (press enter to submit)"));
		add(textInputBox);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(newGameButton);
	}
	
	// listener for the textInputBox
	private class TextInputBoxListener implements ActionListener {
		// called when user presses enter key in the text input box
		public void actionPerformed(ActionEvent e) {
			// don't let the player make guesses if the game is over. This should
			// not be able to occur anyway because the textInputBox is disabled when
			// the game is over but just in case.
			if (game.isGameOver()) {
				message = "game is over. Start a new game to play again";
				repaint();
				return;
			}
			
			// get the player's guess
			String playerGuess = textInputBox.getText();
			textInputBox.setText("");
			// check to make sure text is valid e.g. only one char entered, not a digit etc
			if (game.hasGuessedAlready(playerGuess.charAt(0))) {
				message = "you have already guessed this letter";
				repaint();
				return;
			}
			
			game.makeGuess(playerGuess.charAt(0));
			wordLabel.setText(game.getGuessString());
			message = "";
			if (game.isGameOver()) {
				if (game.hasWon()) {
					message = "congratulations, you win!";
				} else {
					message = "sorry, you lose. The word was: " + game.getWordToGuess();
				}
				textInputBox.setEnabled(false);
				newGameButton.setEnabled(true);
			}
			repaint();
		}
	}
	
	// listener for the newGameButton
	private class NewGameButtonListener implements ActionListener {
		// called when user clicks on the new game button
		public void actionPerformed(ActionEvent e) {
			// don't let the player start a new game if there is already a game in progress.
			// This should not be able to occur anyway because the newGameButton is disabled when
			// the game is in progress but just in case.
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
			message = "";
			repaint();
		}
	}
	
	// this nested panel draws the hangman image and message
	// to the screen.
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
			int numIncorrectGuesses = game.getIncorrectGuessesMade();
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
			
			g.drawString("" + numIncorrectGuesses, 20, 100);
			
			// draw message at bottom of screen
			g.drawString(message,20,200);
		}
	}
}