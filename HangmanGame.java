import java.util.HashSet;
import java.util.Set;

// This class represents the hangman game logic
public class HangmanGame {
	
	private String wordToGuess; // the word the player is trying to guess
	private int numIncorrectGuesses; // number of incorrect guesses player has made
	private int numIncorrectGuessesAllowed; // number of incorrect guesses player is allowed to make before its game over
	private Set<Character> guessesMade; // all guesses made so far
	
	public HangmanGame(int numIncorrectGuessesAllowed) {
		this.numIncorrectGuessesAllowed = numIncorrectGuessesAllowed;
		wordToGuess = "hippopotemous"; // in reality random word will be picked from dictionary here
		numIncorrectGuesses = 0;
		guessesMade = new HashSet<>();
	}
	
	public boolean hasWon() {
		for (int i = 0; i < wordToGuess.length(); i++) {
			char c = wordToGuess.charAt(i);
			if (!guessesMade.contains(c)) {
				return false;
			}
		}
		return true;
	}
	
	public int getNumIncorrectGuesses() {
		return numIncorrectGuesses;
	}
	
	public boolean hasAlreadyGuessedCharacter(char c) {
		return guessesMade.contains(c);
	}
	
	public String getWordToGuess() {
		return wordToGuess;
	}
	
	public void makeGuess(char c) {
		/*if (isGameOver()) {
			return;
		}
		guessesMade.add(c);*/
		numIncorrectGuesses++;
	}
	
	public String getGuessString() {
		StringBuilder sb = new StringBuilder(wordToGuess);
		for (int i = 0; i < wordToGuess.length(); i++) {
			char c = wordToGuess.charAt(i);
			if (!guessesMade.contains(c)) {
				sb.setCharAt(i, '-');
			}
		}
		return sb.toString();
	}
	
	public boolean isGameOver() {
		return hasWon() || (numIncorrectGuesses == numIncorrectGuessesAllowed);
	}
}