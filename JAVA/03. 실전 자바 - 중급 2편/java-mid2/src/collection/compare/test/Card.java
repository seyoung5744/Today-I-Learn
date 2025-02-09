package collection.compare.test;

public class Card implements Comparable<Card> {

    private final Suit suit;
    private final int rank;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int compareTo(Card anothorCard) {
        if (this.rank == anothorCard.rank) {
            this.suit.compareTo(anothorCard.suit);
        }
        return Integer.compare(this.rank, anothorCard.rank);
    }

    @Override
    public String toString() {
        return rank + "(" + suit.getIcon() + ")";
    }
}
