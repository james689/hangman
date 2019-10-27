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
	private int incorrectGuessesMade; 		// number of incorrect guesses player has made
	private int incorrectGuessesAllowed; 	// number of incorrect guesses player is allowed to make before its game over. This
											// can be set in the constructor, to make the game easier or more difficult.
	private Set<Character> guessesMade; 	// all guesses made so far
	
	private static final List<String> WORDS_LIST = getWordsList();
	
	public HangmanGame(int incorrectGuessesAllowed) {
		this.incorrectGuessesAllowed = incorrectGuessesAllowed;
		System.out.println("in HangmanGame constructor. WORDS_LIST.size() = " + WORDS_LIST.size());
		Random rand = new Random(); 
        wordToGuess = WORDS_LIST.get(rand.nextInt(WORDS_LIST.size())); 
		incorrectGuessesMade = 0;
		guessesMade = new HashSet<>();
	}
	
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
	
	// if each letter in wordToGuess is contained within the guessesMade set
	// the player has won.
	public boolean hasWon() {
		for (int i = 0; i < wordToGuess.length(); i++) {
			char c = wordToGuess.charAt(i);
			if (!guessesMade.contains(c)) {
				return false;
			}
		}
		return true;
	}
	
	public int getIncorrectGuessesMade() {
		return incorrectGuessesMade;
	}
	
	public boolean hasGuessedAlready(char c) {
		return guessesMade.contains(c);
	}
	
	public String getWordToGuess() {
		return wordToGuess;
	}
	
	public List<Character> getGuessesMade() {
		List<Character> ret = new ArrayList<>();
		ret.addAll(guessesMade);
		return ret;
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
			incorrectGuessesMade++;
		}
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
		return hasWon() || (incorrectGuessesMade == incorrectGuessesAllowed);
	}
}