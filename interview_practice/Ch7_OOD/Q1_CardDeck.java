package coding_interview;

import java.util.ArrayList;
import java.util.Random;

// Classes put in 1 file for readability
public class Card {
	private int val;
	private String suit;
	
	public Card(int val, String suit) {
		this.val = val;
		this.suit = suit;
	}
}

public class Deck<T extends Card> {
	ArrayList<T> cardsLeft;
	ArrayList<T> cardsUsed;
	Random rand;
	
	public Deck(ArrayList<T> deck) {
		cardsLeft = deck;
		cardsUsed = new ArrayList<>();
		rand = new Random();
	}
	
	public void reset() {
		cardsLeft.addAll(cardsUsed);
	}
	
	public T draw() {
		if (cardsLeft.size() > 0) {
			T card = cardsLeft.remove(rand.nextInt(cardsLeft.size()));
			cardsUsed.add(card);
			
			return card;
		}
		
		return null;
	}
	
	public ArrayList<T> draw(int amt) {
		if (amt <= 0 || amt > cardsLeft.size()) {
			return null;
		}
		
		ArrayList<T> cardsDrawn = new ArrayList<>();
		
		for (int i = 0; i < amt; i++) {
			cardsDrawn.add(draw());
		}
		
		return cardsDrawn;
	}
	
	public int numCardsUsed() {
		return cardsUsed.size();
	}
	
	public int numCardsLeft() {
		return cardsLeft.size();
	}
}
