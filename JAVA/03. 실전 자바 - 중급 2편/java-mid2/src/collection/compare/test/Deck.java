package collection.compare.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        init();
        shuffle();
    }

    public void init() {
        for (int rank = 1; rank <= 13; rank++) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.removeFirst();
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards + '}';
    }
}
