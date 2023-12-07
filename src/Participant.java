import java.util.*;

public interface Participant {
    Hand getHand();
    void addCardToHand(Card card);
    int getHandValue();
    List<Card> getCards();
}
