public class GameOverState implements GameState {
    private BlackjackGame game;

    public GameOverState(BlackjackGame game) {
        this.game = game;
    }

    @Override
    public void playerHit() {
        // No action, game is over
    }

    @Override
    public void playerStand() {
        // No action, game is over
    }
}
