import java.io.IOException;
import java.util.ArrayList;


/* Used to test features against their expected outputs (based on the rules of poker)
The expected outputs are labeled in the comments above each test
 */
public class FeatureTests {
    public static void main(String[] args) throws IOException {

        // ***TESTS FOR GUI***
        System.out.println("***TESTS FOR GUI***\n");

        // Initializes a GUI

        // Expected Output:
        // No errors thrown, board displaying the first player's hand and information

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

        GUI gui = new GUI(deck, tempPlayers, players);

        System.out.println("Success\n");


        // ***TESTS FOR DECK/CARDS***

        System.out.println("***TESTS FOR DECK/CARDS***\n");

        // Gets the rank and suit of a card

        // Expected Output:
        // "6"
        // "c"
        Card c = new Card(6, 'c');
        System.out.println(c.getRank());
        System.out.println(c.getSuit());


        // Gets the first 4 cards of an unshuffled deck

        // Expected Output:
        // "2c 3c 4c 5c "
        Deck d = new Deck();
        d.initializeDeck();
        for (int i = 0; i < 4; i++)
            System.out.print(d.getCard() + " ");
        System.out.println();

        // Gets the first ten cards of a shuffled deck 5 times

        // Expected Output:
        // All 5 should be different
        d.shuffleDeck();
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 10; i++)
                System.out.print(d.getCard() + " ");
            System.out.println();
        }

        /* Ranks the hand, outputting an int for the hand strength
        followed by a space then the "rank." A high card is hand
        strength 0. A straight flush is hand strength 8.
         */

        // Expected Output:
        /*
           "5 12" -- Queen High Flush
           "1 2" -- Pair of Twos
           "3 11" -- Three of a Kind of Jacks
           "6 10" -- Tens Full
           "0 14" -- Ace High
         */
        Card[] hand1 = {new Card(2, 'c'), new Card(4, 'c'),
                new Card(7, 'c'), new Card(8, 'c'),
                new Card(12, 'c')};
        Hand h1 = new Hand(hand1);
        System.out.println(h1.getRank());

        Card[] hand2 = {new Card(2, 'd'), new Card(4, 'c'),
                new Card(7, 'c'), new Card(8, 's'),
                new Card(2, 'c')};
        Hand h2 = new Hand(hand2);
        System.out.println(h2.getRank());

        Card[] hand3 = {new Card(11, 'd'), new Card(11, 'c'),
                new Card(7, 'c'), new Card(8, 's'),
                new Card(11, 'h')};
        Hand h3 = new Hand(hand3);
        System.out.println(h3.getRank());

        Card[] hand4 = {new Card(10, 'd'), new Card(10, 'c'),
                new Card(3, 'c'), new Card(3, 's'),
                new Card(10, 'h')};
        Hand h4 = new Hand(hand4);
        System.out.println(h4.getRank());

        Card[] hand5 = {new Card(14, 'd'), new Card(6, 'c'),
                new Card(2, 'c'), new Card(3, 's'),
                new Card(10, 'h')};
        Hand h5 = new Hand(hand5);
        System.out.println(h5.getRank());

        System.out.println();

        // ***TESTS FOR BETS***
        System.out.println("***TESTS FOR BETS***\n");

        // Tests will be carried out using the already initialized GUI object

        // Show players switch when "check/call" command given and the pot remains the same
        // (simulates the check/call button press)

        /* Expected Output:
            "Player 1 -- Pot: 20"
            "Player 2 -- Pot: 20"
            "Player 3 -- Pot: 20"
            "Player 4 -- Pot: 20"
         */
        for (int i = 0; i < 4; i++) {
            System.out.println(gui.getCurrPlayer().player.getName() + " -- Pot: " + gui.getPotsize());
            gui.call();
            gui.switchPlayer();
        }


    }
}
