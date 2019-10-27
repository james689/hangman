import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class HangmanGUI extends JPanel {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Hangman");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		HangmanGUI content = new HangmanGUI();
		window.setContentPane(content);
		window.pack();
		window.setVisible(true);
	}
	
	// --------------------------------------
	
	private JLabel wordLabel; // displays the word the player is trying to guess
	private JLabel guessesMadeLabel; // displays all guesses player has made
	private JTextField textInputBox; 
	private JButton newGameButton;
	private String message = "welcome to hangman";
	private HangmanGame game;
	
	public HangmanGUI() {
		game = new HangmanGame(6); // start the player in a new game when GUI is created
		
		//setBackground(Color.GREEN);
		setLayout(null); // panel does not use a layout manager, all components are positioned manually
		setPreferredSize(new Dimension(320, 500));
		
		// create components
		ImagePanel imagePanel = new ImagePanel();
		wordLabel = new JLabel(game.getGuessString());
		wordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		//wordLabel.setOpaque(true);
		//wordLabel.setBackground(Color.BLUE);
		guessesMadeLabel = new JLabel("Guesses made: ");
		textInputBox = new JTextField(5);
		textInputBox.addActionListener(new TextInputBoxListener());
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameButtonListener());
		newGameButton.setEnabled(false);
		// set bounds
		imagePanel.setBounds(10,10,300,300);
		wordLabel.setBounds(10, 310, 300, 50);
		guessesMadeLabel.setBounds(10, 350, 300, 20);
		textInputBox.setBounds(10,380,100,20);
		newGameButton.setBounds(10,420,100,50);
		// add components to panel
		add(imagePanel);
		add(wordLabel);
		add(guessesMadeLabel);
		//add(new JLabel("Guess letter (press enter to submit)"));
		add(textInputBox);
		add(newGameButton);
	}
	
	// listener for the textInputBox
	private class TextInputBoxListener implements ActionListener {
		// called when user presses enter key in the text input box
		public void actionPerformed(ActionEvent e) {
			// don't let the player make guesses if the game is over. This situation should
			// not be able to occur anyway because the textInputBox is disabled when
			// the game is over.
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
			
			// make the guess and update state of wordLabel
			game.makeGuess(playerGuess.charAt(0));
			wordLabel.setText(game.getGuessString());
			guessesMadeLabel.setText("Guesses made: " + game.getGuessesMade());
			message = "";
			repaint();
			
			if (game.isGameOver()) {
				doGameOver();
			}
		}
	}
	
	// listener for the newGameButton
	private class NewGameButtonListener implements ActionListener {
		// called when user clicks on the new game button
		public void actionPerformed(ActionEvent e) {
			// don't let the player start a new game if there is already a game in progress.
			// This situation should not be able to occur anyway because the newGameButton is disabled when
			// the game is in progress.
			if (!game.isGameOver()) {
				message = "you still have to finish this game first";
				repaint();
				return;
			}
			doNewGame();
		}
	}
	
	// set up the GUI ready for a new game
	private void doNewGame() {
		game = new HangmanGame(6);
		wordLabel.setText(game.getGuessString());
		guessesMadeLabel.setText("Guesses made: ");
		newGameButton.setEnabled(false);
		textInputBox.setEnabled(true);
		textInputBox.setText("");
		message = "";
		repaint();
	}
	
	private void doGameOver() {
		if (game.hasWon()) {
			message = "congratulations, you win!";
		} else {
			message = "sorry, you lose. The word was: " + game.getWordToGuess();
		}
		textInputBox.setEnabled(false);
		newGameButton.setEnabled(true);
		repaint();
	}
	
	// this nested panel draws the hangman image and message
	// to the screen.
	private class ImagePanel extends JPanel {
		
		public ImagePanel() {
			//setBackground(Color.RED);
			setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
			drawGallows(g2);
			drawHangman(g2, game.getIncorrectGuessesMade());
			// draw message at bottom of screen
			g.drawString(message,10,280);
		}
		
		private void drawGallows(Graphics2D g2) {
			g2.setStroke(new BasicStroke(10));
			g2.drawLine(50,250,250,250);
			g2.drawLine(200,250,200,50);
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(200,50,100,50);
			g2.drawLine(100,50,100,70);
		}
		
		private void drawHangman(Graphics2D g, int numIncorrectGuesses) {
			g.setStroke(new BasicStroke(2));
			if (numIncorrectGuesses >= 1) {
				g.drawOval(90,70,20,20); // draw head
			}
			if (numIncorrectGuesses >= 2) {
				g.drawLine(100,90,100,130); // draw torso
			}
			if (numIncorrectGuesses >= 3) {
				g.drawLine(100,110,80,85); // draw left arm
			}
			if (numIncorrectGuesses >= 4) {
				g.drawLine(100,110,120,85); // draw right arm
			}
			if (numIncorrectGuesses >= 5) {
				g.drawLine(100,130,80,150); // draw left leg
			}
			if (numIncorrectGuesses == 6) {
				g.drawLine(100,130,120,150); // draw right leg
			}
		}
	}
}