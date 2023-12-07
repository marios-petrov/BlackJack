import java.util.*;

public class Player implements Participant {
    private Hand hand;

    public Player() {
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

    // Additional methods specific to Player can be added here
}

