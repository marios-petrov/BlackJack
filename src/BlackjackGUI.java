import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BlackjackGUI implements GameStateObserver {
    private JFrame frame;
    private BackgroundPanel mainPanel; // Custom panel for the background
    private JPanel playerPanel, dealerPanel, controlPanel;
    private JLabel statusLabel;
    private JLabel playerTotalLabel;
    private JLabel dealerTotalLabel;
    private JButton hitButton, standButton, playAgainButton;
    private BlackjackGame game;

    public BlackjackGUI() {
        game = new BlackjackGame();
        game.addObserver(this); // Register GUI as an observer
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new BackgroundPanel("Background.jpg");
        mainPanel.setLayout(new BorderLayout());

        playerPanel = new JPanel(new FlowLayout());
        dealerPanel = new JPanel(new FlowLayout());
        playerPanel.setOpaque(false);
        dealerPanel.setOpaque(false);

        // Initialize and customize the total value labels
        playerTotalLabel = new JLabel();
        dealerTotalLabel = new JLabel();
        playerTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dealerTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerTotalLabel.setForeground(Color.WHITE);
        dealerTotalLabel.setForeground(Color.WHITE);

        updateHandsDisplay(); // Initial update

        JPanel paddedPlayerPanel = new JPanel(new BorderLayout());
        paddedPlayerPanel.add(playerPanel, BorderLayout.NORTH);
        paddedPlayerPanel.setOpaque(false);
        paddedPlayerPanel.setBorder(BorderFactory.createEmptyBorder(400, 0, 0, 0));

        controlPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        statusLabel = new JLabel("Welcome to Blackjack!");

        hitButton.addActionListener(e -> {
            game.playerHit();
        });

        standButton.addActionListener(e -> {
            game.playerStand();
        });

        playAgainButton.addActionListener(e -> restartGame());

        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        controlPanel.add(playAgainButton);
        controlPanel.add(statusLabel);

        mainPanel.add(dealerPanel, BorderLayout.NORTH);
        mainPanel.add(paddedPlayerPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void onGameStateChanged() {
        updateHandsDisplay();
        updateGameStatus();
    }


    private void updateHandsDisplay() {
        playerPanel.removeAll();
        dealerPanel.removeAll();

        // Player label
        JLabel playerLabel = new JLabel("Player");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerLabel.setForeground(Color.WHITE);
        playerPanel.add(playerLabel);

        // Dealer label
        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dealerLabel.setForeground(Color.WHITE);
        dealerPanel.add(dealerLabel);

        // Display player's cards
        for (Card card : game.getPlayerHand().getCards()) {
            playerPanel.add(new JLabel(getCardImage(card)));
        }

        // Display dealer's cards
        List<Card> dealerCards = game.getDealerHand().getCards();
        boolean isDealerSecondCardHidden = game.getCurrentState() instanceof PlayerTurnState && dealerCards.size() > 1;

        if (dealerCards.size() > 0) {
            // Always show the first dealer card
            dealerPanel.add(new JLabel(getCardImage(dealerCards.get(0))));

            // Show the second card as hidden if game is in the initial state
            if (isDealerSecondCardHidden) {
                dealerPanel.add(new JLabel(getHiddenCardImage())); // Hidden card image
            } else {
                // Show all dealer cards if not in the initial state
                for (int i = 1; i < dealerCards.size(); i++) {
                    dealerPanel.add(new JLabel(getCardImage(dealerCards.get(i))));
                }
            }
        }

        // Update the hand value labels
        playerTotalLabel.setText("Total: " + game.getPlayerHand().getHandValue());

        // Update dealer total label based on whether the second card is hidden
        int dealerTotal = isDealerSecondCardHidden ? dealerCards.get(0).getNumericValue() : game.getDealerHand().getHandValue();
        dealerTotalLabel.setText("Total: " + dealerTotal);

        playerPanel.add(playerTotalLabel);
        dealerPanel.add(dealerTotalLabel);

        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    private ImageIcon getCardImage(Card card) {
        String fileName = "Cardpics/" + card.getValue().toLowerCase() + "_of_" + card.getSuit().toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(fileName);

        // Resize the image
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(180, 250, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private ImageIcon getHiddenCardImage() {
        String fileName = "Cardpics/card_back.png"; // Replace with your card back image path
        ImageIcon icon = new ImageIcon(fileName);
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(180, 250, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void updateGameStatus() {
        String status = game.getGameStatus();
        statusLabel.setText(status);

        // Check if the game has ended due to a win, tie, or bust
        if (status.contains("Wins") || status.equals("Tie Game") || status.contains("Busts")) {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            playAgainButton.setVisible(true);
        }
    }

    private void restartGame() {
        game.startNewGame();
        updateHandsDisplay();
        statusLabel.setText("Welcome to Blackjack!");

        // Reset button states
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BlackjackGUI();
            }
        });
    }

    // Custom panel class for the background
    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(String fileName) {
            backgroundImage = new ImageIcon(fileName).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
