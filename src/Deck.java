import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/* creates a Deck object of the 52 cards in a standard deck of cards
the Deck will be unshuffled until the shuffleDeck() command is ran
 */
public class Deck {
    private Card[] deck;
    private int topCard;

    // create a deck object
    public Deck() {
        deck = new Card[52];
        topCard = -1;
    }

    // initialize the deck with each of the 52 cards
    // reads each of the suits, ranks, and images and throws
    // an exception if the filepath cannot be found
    public void initializeDeck() throws IOException {
        int index = 0;
        char[] suits = {'c', 'h', 'd', 's'};
        for (char suit : suits) {
            for (int rank = 2; rank < 15; rank++) {
                String filepath = "cardImages/" + rank + suit + ".png";
                Card c = new Card(rank, suit, ImageIO.read(new File(filepath)));
                deck[index] = c;
                index++;
            }
        }
    }

    // implement the Fisher-Yates shuffling algorithm to shuffle the deck
    public void shuffleDeck() {
        topCard = -1;
        for (int i = deck.length - 1; i >= 0; i--) {
            int index = (int) (Math.random() * i);
            Card temp = deck[index];
            deck[index] = deck[i];
            deck[i] = temp;
        }
    }

    // get the top card of the deck and move the "topCard" pointer to the
    // next card
    public Card getCard() {
        topCard++;
        return deck[topCard];
    }

    // print all of the cards in the deck in their current order
    public String toString() {
        String str = "";
        for (Card c : deck) {
            str += c + " ";
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        Deck d = new Deck();
        d.initializeDeck();
        System.out.println(d);
        d.shuffleDeck();
        System.out.println(d);

    }
}
