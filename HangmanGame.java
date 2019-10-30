import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

// This class represents the hangman game logic
public class HangmanGame {
	
	private String wordToGuess; 			// the word the player has to guess 
	private int numIncorrectGuessesMade; 	// number of incorrect guesses player has made
	private int numIncorrectGuessesAllowed; // number of incorrect guesses player is allowed to make before its game over. This
											// value can be set in the constructor, to make the game easier or more difficult.
	private Set<Character> guessesMade; 	// all guesses made so far
	
	private static final List<String> WORDS_LIST = getWordsList(); // the list of words the wordToGuess will be randomly chosen from
	
	// ----- public interface -------------
	
	public HangmanGame(int numIncorrectGuessesAllowed) {
		this.numIncorrectGuessesAllowed = numIncorrectGuessesAllowed;
        wordToGuess = WORDS_LIST.get(new Random().nextInt(WORDS_LIST.size())); 
		numIncorrectGuessesMade = 0;
		guessesMade = new HashSet<>();
	}
	
	// if every letter in wordToGuess is contained within the guessesMade set
	// the player has won.
	public boolean hasWon() {
		for (int i = 0; i < wordToGuess.length(); i++) {
			char letter = wordToGuess.charAt(i);
			if (!guessesMade.contains(letter)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isGameOver() {
		return hasWon() || (numIncorrectGuessesMade > numIncorrectGuessesAllowed);
	}
	
	public int getNumIncorrectGuessesMade() {
		return numIncorrectGuessesMade;
	}
	
	public boolean hasGuessedAlready(char letter) {
		return guessesMade.contains(letter);
	}
	
	public String getWordToGuess() {
		return wordToGuess;
	}
	
	public List<Character> getGuessesMade() {
		List<Character> guessesMadeList = new ArrayList<>();
		guessesMadeList.addAll(guessesMade);
		return guessesMadeList;
	}
	
	public void makeGuess(char letter) {
		// don't allow the guess if the game is over or
		// the letter has already been guessed. The client class
		// should prevent this from happening anyway but just in case.
		if (isGameOver() || hasGuessedAlready(letter)) {
			return;
		}
		
		guessesMade.add(letter);
		if (wordToGuess.indexOf(letter) == -1) { // letter is not in wordToGuess
			numIncorrectGuessesMade++;
		}
	}
	
	// convenience method for client class. Returns the wordToGuess with letters
	// the player has not guessed yet replaced with '-'
	public String getGuessString() {
		StringBuilder sb = new StringBuilder(wordToGuess);
		for (int i = 0; i < wordToGuess.length(); i++) {
			char letter = wordToGuess.charAt(i);
			if (!guessesMade.contains(letter)) {
				sb.setCharAt(i, '-');
			}
		}
		return sb.toString();
	}
	
	// ----- private interface -------------
	
	// load the WORDS_LIST from a file
	private static List<String> getWordsList() {
		List<String> words = new ArrayList<String>();
		File file = new File("words_alpha.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				words.add(line);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return words;
	}
}