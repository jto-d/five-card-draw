import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* Implements the GUI using Java Swing and acts as the control
for flow of the program
 */
public class GUI implements ActionListener {

    private Node currPlayer;
    private Deck deck;
    private ArrayList<Node> notInPot;
    private Node firstPlayer;
    private ArrayList<Player> listPlayers;
    private Players players;


    private int potsize;
    private int bet;
    private int currentBet;
    private int phase;

    private JLabel balance;
    private JLabel player;
    private JLabel pot;

    private JTextField swap;

    private JFrame frame;
    private JLayeredPane container;

    private JPanel table;
    private JPanel cards;
    private JPanel buttons;

    // creates a gui using the deck, list of players, and circular linked list
    public GUI(Deck deck, ArrayList<Player> listPlayers, Players players) throws IOException {

        potsize = 0;
        bet = 10;
        currentBet = 0;
        phase = 1;
        this.listPlayers = listPlayers;
        this.players = players;

        notInPot = new ArrayList<>();

        this.deck = deck;

        // create the frame
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Five Card Draw");
        frame.setSize(612, 512);
//        frame.pack();


        // initialize panels
        container = new JLayeredPane();
        container.setBounds(0, 0, 612, 512);


        table = new JPanel();
        cards = new JPanel();
        buttons = new JPanel();

        // text field for swap cards
        swap = new JTextField();
        swap.setBounds(275, 0, 50, 50);
        container.add(swap, 1, 0);

        // submit button for swap cards
        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        submit.setActionCommand("Submit");
        submit.setPreferredSize(new Dimension(100, 50));
        submit.setBounds(330, 0, 100, 50);
        container.add(submit, 1, 0);


        // add buttons for "poker moves"
        String[] actions = {"Fold", "Check/Call", "Bet/Raise"};

        for (String action : actions) {
            JButton button = new JButton(action);
            button.addActionListener(this);
            button.setActionCommand(action);
            button.setPreferredSize(new Dimension(100, 50));
            buttons.add(button);
        }

        // create and add background image
        BufferedImage TABLE_IMAGE = ImageIO.read(new File("table.jpeg"));
        JLabel tableImage = new JLabel(new ImageIcon(TABLE_IMAGE));
        tableImage.setBounds(0, 0, 612, 408);
        container.add(tableImage, 0, 0);

        // display pot
        pot = new JLabel();
        pot.setForeground(Color.WHITE);
        pot.setText("Pot: $");
        pot.setBounds(275, 100, 100, 50);
        container.add(pot, 1, 0);


        // display balance
        balance = new JLabel();
        balance.setText("Balance: $");
        balance.setBounds(50, 412, 100, 50);
        container.add(balance, 1, 0);

        // display player name
        player = new JLabel("Name Here");
        player.setForeground(Color.WHITE);
        player.setBounds(285, 360, 100, 50);
        container.add(player, 2, 0);


        buttons.setBounds(250, 412, 400, 52);

        // display the five cards

        cards.setBounds(100, 250, 400, 120);

        container.add(buttons);
//        frame.add(table);

        container.add(cards, 2, 0);

        frame.add(container);
//        frame.pack();
        frame.setVisible(true);
        reset();

    }

    public ArrayList<Node> getNotInPot() {
        return notInPot;
    }

    // returns the current phase for testing
    public int getPhase() {
        return phase;
    }

    // returns the current player node for testing
    public Node getCurrPlayer() {
        return currPlayer;
    }

    // returns the potsize for testing
    public int getPotsize() {
        return potsize;
    }

    // focus a player on the gui
    // e.g. load Player 1's name, balance, and cards
    public void focusPlayer(Node node) {
        if (firstPlayer == null) {
            firstPlayer = node;
        }
        currPlayer = node;
        Player p = currPlayer.player;
        setBalance(p);
        player.setText(p.getName());
        displayCards(p);
    }

    // add a player's bet to the pot
    public void addPot(int potsize) {
        this.potsize += potsize;
        pot.setText("Pot: $" + this.potsize);
    }

    // display a player's card
    public void displayCards(Player p) {
        ArrayList<Card> c = p.getCards();
        for (Card card : c) {
            BufferedImage initImage = card.getImage();
            int w = initImage.getWidth();
            int h = initImage.getHeight();

            double scale = 0.15;

            // scale a new image of the correct size
            int w2 = (int) (w * scale);
            int h2 = (int) (h * scale);
            BufferedImage CARD_IMAGE = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
            AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
            AffineTransformOp scaleOp
                    = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_BILINEAR);

            scaleOp.filter(initImage, CARD_IMAGE);

            JLabel cardPic = new JLabel(new ImageIcon(CARD_IMAGE));
            cardPic.setPreferredSize(new Dimension(w2, h2));

            cards.add(cardPic);
            frame.setVisible(true);
            frame.repaint();
        }

    }

    // switch to the next active player (currently in the hand)
    public void switchPlayer() {

        cards.removeAll();

        if (notInPot.size() == 3) {
            onePlayerShowdown();
        } else {

            if (currPlayer.next == firstPlayer) {
                phase += 1;
            }

            while (notInPot.contains(currPlayer.next)) {
                currPlayer = currPlayer.next;
            }


            focusPlayer(currPlayer.next);
        }


    }

    // change the balance text for a player
    public void setBalance(Player p) {
        balance.setText("Balance: $" + p.getChips());
    }

    // facilitate "mucking" certain cards and draw new ones for the player
    public void swapCards(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            currPlayer.getPlayer().removeCard(Integer.parseInt(s.substring(i, i + 1)));
        }
        for (int i = 0; i < s.length(); i++) {
            currPlayer.getPlayer().drawCard(deck.getCard());
        }
    }

    // get rid of your cards and remove the current player from the hand
    public void fold() {
        notInPot.add(currPlayer);
    }

    // match the highest current bet
    public void call() {
        addPot(currentBet - currPlayer.player.potCommitment);
        currPlayer.player.bet(currentBet - currPlayer.player.potCommitment);

    }

    // raise the highest bet by 10
    public void bet() {
        firstPlayer = currPlayer;
        currentBet += bet;
        currPlayer.player.bet(currentBet);
        addPot(currentBet);
    }

    // faciliate "showdown" but only after action has been folded all the way
    public void onePlayerShowdown() {
        while (notInPot.contains(currPlayer)) {
            currPlayer = currPlayer.next;
        }
        currPlayer.player.winPot(potsize);
        System.out.println(currPlayer.player.getName() + " won the pot");
        currPlayer = players.start;

        System.out.println(currPlayer.player.getName() + " " + currPlayer.player.getChips());
        System.out.println(currPlayer.next.player.getName() + " " + currPlayer.next.player.getChips());
        System.out.println(currPlayer.next.next.player.getName() + " " + currPlayer.next.next.player.getChips());
        System.out.println(currPlayer.next.next.next.player.getName() + " " +
                currPlayer.next.next.next.player.getChips());
        reset();

    }

    // showdown between two players where the higher ranking hand will win all of the chips
    public void showdown() {
        Node winner = currPlayer;
        int winningHand = winner.player.getHand().getCardHand();
        int winningRank = winner.player.getHand().getRankHand();

        currPlayer = currPlayer.next;

        do {
            if (!notInPot.contains(currPlayer)) {
                int currHand = currPlayer.player.getHand().getCardHand();
                int currRank = currPlayer.player.getHand().getRankHand();
                if (currHand > winningHand ||
                        (currHand == winningHand && currRank > winningRank)) {
                    winner = currPlayer;
                    winningHand = currHand;
                    winningRank = currRank;
                }
            }
            currPlayer = currPlayer.next;
        } while (currPlayer.next != firstPlayer);

        winner.player.winPot(potsize);
        System.out.println(winner.player.getName() + " won the pot");
        currPlayer = players.start;
        System.out.println(currPlayer.player.getName() + " " + currPlayer.player.getChips());
        System.out.println(currPlayer.next.player.getName() + " " + currPlayer.next.player.getChips());
        System.out.println(currPlayer.next.next.player.getName() + " " + currPlayer.next.next.player.getChips());
        System.out.println(currPlayer.next.next.next.player.getName() + " " +
                currPlayer.next.next.next.player.getChips());
        reset();

    }

    // start a new hand
    public void reset() {

        firstPlayer = players.start;
        potsize = 0;
        if (notInPot.size() > 0)
            notInPot.clear();
        phase = 1;
        currentBet = 0;
        if (cards.getComponents().length != 0)
            cards.removeAll();


        int p = 0;
        int ANTE = 5;

        // shuffle deck
        for (Player player : listPlayers) {
            if (player.getCards().size() > 0)
                player.clearCards();
            player.bet(ANTE);
            player.potCommitment = 0;
            p += ANTE;
        }
        deck.shuffleDeck();

        for (int i = 0; i < 5; i++) {
            for (Player player : listPlayers) {
                player.drawCard(deck.getCard());
            }
        }


        focusPlayer(players.start);
        addPot(p);
    }

    /* action listeners for the buttons
    call each command for the corresponding buttons
     */

    public void actionPerformed(ActionEvent e) {
        if (phase == 1 || phase == 3) {
            if (e.getActionCommand().equals("Fold")) {
                fold();
                switchPlayer();
            } else if (e.getActionCommand().equals("Check/Call")) {
                call();
                switchPlayer();
            } else if (e.getActionCommand().equals("Bet/Raise")) {
                bet();
                switchPlayer();
            }
        } else if (e.getActionCommand().equals("Submit") && phase == 2) {
            currentBet = 0;
            String cardsToSwap = swap.getText();
            swapCards(cardsToSwap);
            switchPlayer();
            for (Player player : listPlayers) {
                player.potCommitment = 0;
            }
        } else if (phase == 4) {
            showdown();


        }
    }

    public static void main(String[] args) throws IOException {
        int STARTING_CHIPS = 1000;


        // initialize players
        int numPlayers = 4;

        ArrayList<Player> tempPlayers = new ArrayList<>();


        for (int i = 1; i <= numPlayers; i++)
            tempPlayers.add(new Player("Player " + i, STARTING_CHIPS));

        // implements a circularly linked list
        Players players = new Players(tempPlayers);


        // create deck
        Deck deck = new Deck();
        deck.initializeDeck();

        new GUI(deck, tempPlayers, players);


    }

}
