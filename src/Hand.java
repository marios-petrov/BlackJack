import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : cards) {
            int cardValue = card.getNumericValue();
            if (cardValue == 1) { // Ace
                aceCount++;
            }
            value += cardValue;
        }

        // Adjust for Aces
        while (aceCount > 0 && value + 10 <= 21) {
            value += 10;
            aceCount--;
        }

        return value;
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public void clearHand() {
        cards.clear();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
