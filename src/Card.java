public class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    // Method to get the numeric value of the card for scoring
    public int getNumericValue() {
        return switch (value) {
            case "Ace" -> 1;
            case "Jack", "Queen", "King" -> 10;
            default -> Integer.parseInt(value);
        };
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
