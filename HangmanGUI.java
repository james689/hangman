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
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;

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
	
	private JLabel wordLabel; 			// displays the word the player is trying to guess
	private JLabel guessesMadeLabel; 	// displays all guesses player has made
	private JTextField textInputBox; 	// where the player enters their guesses
	private JButton newGameButton;		// starts a new game when clicked
	
	private String message = "Welcome to hangman";
	private HangmanGame game;
	private static final int NUM_INCORRECT_GUESSES_ALLOWED = 9;
	
	// constructor creates the GUI components and lays them out
	public HangmanGUI() {
		game = new HangmanGame(NUM_INCORRECT_GUESSES_ALLOWED); // start the player in a new game when GUI is created
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		ImagePanel imagePanel = new ImagePanel();
		imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		wordLabel = new JLabel(game.getGuessString());
		wordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		wordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		guessesMadeLabel = new JLabel("Guesses made: ");
		guessesMadeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JPanel enterGuessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		enterGuessPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		textInputBox = new JTextField(5);
		textInputBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		textInputBox.setMaximumSize(new Dimension(50,50));
		textInputBox.addActionListener(new TextInputBoxListener());
		JLabel enterGuessLabel = new JLabel("Enter guess: ");
		enterGuessLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		enterGuessPanel.add(enterGuessLabel);
		enterGuessPanel.add(textInputBox);
		
		newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		newGameButton.addActionListener(new NewGameButtonListener());
		newGameButton.setEnabled(false);
		
		// add components to panel
		add(imagePanel);
		add(wordLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(guessesMadeLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(enterGuessPanel);
		add(Box.createRigidArea(new Dimension(0,10)));
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
				message = "Game is over. Start a new game to play again";
				repaint();
				return;
			}
			
			// get the player's guess and perform input validation
			String playerGuess = textInputBox.getText();
			if (!inputIsValid(playerGuess)) {
				repaint();
				return;
			}
			
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
			textInputBox.setText("");
			repaint();
			
			if (game.isGameOver()) {
				doGameOver();
			}
		}
	}
	
	private boolean inputIsValid(String str) {
		if (str.length() != 1) {
			message = "enter 1 letter";
			return false;
		}
		// if here, string contains only 1 character, check to make sure it is a letter
		if (!Character.isLetter(str.charAt(0))) {
			message = "enter a letter";
			return false;
		}
		return true;
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
		game = new HangmanGame(NUM_INCORRECT_GUESSES_ALLOWED);
		wordLabel.setText(game.getGuessString());
		guessesMadeLabel.setText("Guesses made: ");
		newGameButton.setEnabled(false);
		textInputBox.setEnabled(true);
		textInputBox.setText("");
		message = "";
		repaint();
	}
	
	// this nested panel draws the hangman image and message
	// to the screen. It's size and position is set in the HangmanGUI constructor.
	private class ImagePanel extends JPanel {
		
		public ImagePanel() {
			setPreferredSize(new Dimension(300,300));
			setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
			drawHangman(g2, game.getNumIncorrectGuessesMade());
			g.drawString(message,10,280);
		}
		
		private void drawHangman(Graphics2D g, int numIncorrectGuesses) {
			if (numIncorrectGuesses >= 1) {
				g.setStroke(new BasicStroke(10));
				g.drawLine(50,250,250,250); // draw base of gallows
			}
			if (numIncorrectGuesses >= 2) {
				g.drawLine(200,250,200,50); // draw vertical beam of gallows
			}
			if (numIncorrectGuesses >= 3) {
				g.setStroke(new BasicStroke(5));
				g.drawLine(200,50,100,50); // draw horizontal top beam of gallows
			}
			if (numIncorrectGuesses >= 4) {
				g.drawLine(100,50,100,70); // draw vertical beam hangman is attached to
			}
			if (numIncorrectGuesses >= 5) {
				g.setStroke(new BasicStroke(2));
				g.drawOval(90,70,20,20); // draw hangman's head
			}
			if (numIncorrectGuesses >= 6) {
				g.drawLine(100,90,100,130); // draw torso
			}
			if (numIncorrectGuesses >= 7) {
				g.drawLine(100,110,80,85); // draw left arm
			}
			if (numIncorrectGuesses >= 8) {
				g.drawLine(100,110,120,85); // draw right arm
			}
			if (numIncorrectGuesses >= 9) {
				g.drawLine(100,130,80,150); // draw left leg
			}
			if (numIncorrectGuesses == 10) {
				g.drawLine(100,130,120,150); // draw right leg
			}
		}
	}
}