import java.util.*;

public class Dealer implements Participant {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    @Override
    public Hand getHand() {
        return hand;
    }

    @Override
    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    @Override
    public int getHandValue() {
        return hand.getHandValue();
    }

    @Override
    public List<Card> getCards() {
        return hand.getCards();
    }
    // Additional methods specific to Dealer can be added here
}