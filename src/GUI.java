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

public class GUI implements ActionListener {

    private Node currPlayer;
    private Deck deck;
    private ArrayList<Node> notInPot;
    private Node firstPlayer;


    private int potsize;
    private int bet;
    private int currentBet;

    private JLabel balance;
    private JLabel player;
    private JLabel pot;

    private JTextField swap;

    private JFrame frame;
    private JLayeredPane container;

    private JPanel table;
    private JPanel cards;
    private JPanel buttons;

    public GUI(Deck deck) throws IOException {

        potsize = 0;
        bet = 10;
        currentBet = 0;

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
        swap.setBounds(275, 0, 100, 50);
        container.add(swap, 1, 0);

        // submit button for swap cards
        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        submit.setActionCommand("Submit");
        submit.setPreferredSize(new Dimension(100, 50));


        // add buttons for "poker moves"
        String[] actions = {"Fold", "Check/Call", "Bet/Raise"};

        for (String action : actions) {
            JButton button = new JButton(action);
            button.addActionListener(this);
            button.setActionCommand(action);
            button.setPreferredSize(new Dimension(100, 50));
            buttons.add(button);
        }

        BufferedImage TABLE_IMAGE = ImageIO.read(new File("src/table.jpeg"));
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

    }

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

    public void addPot(int potsize) {
        this.potsize += potsize;
        pot.setText("Pot: $" + this.potsize);
    }

    public void displayCards(Player p) {
        ArrayList<Card> c = p.getCards();
        for (Card card : c) {
            BufferedImage initImage = card.getImage();
            int w = initImage.getWidth();
            int h = initImage.getHeight();

            double scale = 0.15;

            // Create a new image of the proper size
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

    public void switchPlayer() {
        cards.removeAll();

        if (currPlayer.next == firstPlayer) {
            System.out.println("Next Phase");
        }

        while (notInPot.contains(currPlayer.next)) {
            currPlayer = currPlayer.next;
        }

        focusPlayer(currPlayer.next);

    }

    public void setBalance(Player p) {
        balance.setText("Balance: $" + p.getChips());
    }

    public void swapCards(String s) {
        for (int i = 0; i < s.length(); i++) {
            currPlayer.getPlayer().removeCard(Integer.parseInt(s.substring(i, i + 1)));
        }
        for (int i = 0; i < s.length(); i++) {
            currPlayer.getPlayer().drawCard(deck.getCard());
        }
    }

    public void fold() {
        notInPot.add(currPlayer);
    }

    public void call() {
        currPlayer.player.bet(currentBet);
        addPot(currentBet);
    }

    public void bet() {
        firstPlayer = currPlayer;
        currentBet = bet;
        currPlayer.player.bet(bet);
        addPot(bet);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Fold")) {
            fold();
            switchPlayer();
        } else if (e.getActionCommand().equals("Check/Call")) {
            call();
            switchPlayer();
        } else if (e.getActionCommand().equals("Bet/Raise")) {
            bet();
            switchPlayer();
        } else if (e.getActionCommand().equals("Submit")) {
            String cardsToSwap = swap.getText();
            swapCards(cardsToSwap);
        }
    }

    public static void main(String[] args) throws IOException {


    }

}
