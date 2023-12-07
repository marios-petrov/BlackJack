public class DealerTurnState implements GameState {
    private BlackjackGame game;

    public DealerTurnState(BlackjackGame game) {
        this.game = game;
    }

    @Override
    public void playerHit() {
        // No action, as it's dealer's turn
    }

    @Override
    public void playerStand() {
        // No action, as it's dealer's turn
    }

}
