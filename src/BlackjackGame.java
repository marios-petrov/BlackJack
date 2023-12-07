import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private GameState currentState;
    private String gameStatus;
    private List<GameStateObserver> observers = new ArrayList<>();

    public BlackjackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        currentState = new PlayerTurnState(this);
        startNewGame();
    }

    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.onGameStateChanged();
        }
    }

    public void startNewGame() {
        deck.shuffle();
        player.getHand().clearHand();
        dealer.getHand().clearHand();
        gameStatus = "99% of all gamblers quit right before they win BIG!";
        dealInitialCards();
        changeState(new PlayerTurnState(this)); // Start with player's turn
        notifyObservers();
    }

    private void dealInitialCards() {
        player.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
        player.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
        notifyObservers();
    }

    public void playerHit() {
        currentState.playerHit();
    }

    public void playerStand() {
        currentState.playerStand();
    }

    public void dealerPlay() {
        while (dealer.getHandValue() < 17) {
            dealer.addCardToHand(deck.dealCard());
        }
        determineWinner();
        changeState(new GameOverState(this));
        notifyObservers();
    }

    public void determineWinner() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (playerValue > 21) {
            gameStatus = "Player Busts, Dealer Wins";
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            gameStatus = "Player Wins";
        } else if (playerValue == dealerValue) {
            gameStatus = "Tie Game";
        } else {
            gameStatus = "Dealer Wins";
        }
        notifyObservers();
    }

    public void changeState(GameState newState) {
        currentState = newState;
        notifyObservers();
    }

    public Deck getDeck() {
        return deck;
    }

    public Player getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String status) {
        this.gameStatus = status;
        notifyObservers();
    }

    public Hand getPlayerHand() {
        return player.getHand();
    }

    public Hand getDealerHand() {
        return dealer.getHand();
    }

    public GameState getCurrentState() {
        return currentState;
    }

}
