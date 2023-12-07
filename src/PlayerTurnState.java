public class PlayerTurnState implements GameState {
    private BlackjackGame game;

    public PlayerTurnState(BlackjackGame game) {
        this.game = game;
    }

    @Override
    public void playerHit() {
        // Logic when player hits
        game.getPlayer().addCardToHand(game.getDeck().dealCard());
        if (game.getPlayer().getHandValue() > 21) {
            game.setGameStatus("Player Busts");
            game.changeState(new GameOverState(game));
        }
        game.notifyObservers();
    }

    @Override
    public void playerStand() {
        // Logic when player stands
        game.setGameStatus("Player Stands");
        game.changeState(new DealerTurnState(game));
        game.dealerPlay();
    }
}
