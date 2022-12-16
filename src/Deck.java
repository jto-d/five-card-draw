import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Deck {
    private Card[] deck;
    private int topCard;

    public Deck() {
        deck = new Card[52];
        topCard = -1;
    }

    // initialize the deck with each of the 52 cards
    public void initializeDeck() throws IOException {
        int index = 0;
        char[] suits = {'c', 'h', 'd', 's'};
        for (char suit : suits) {
            for (int rank = 2; rank < 15; rank++) {
                String filepath = "src/cardImages/" + rank + suit + ".png";
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

    public Card getCard() {
        topCard++;
        return deck[topCard];
    }

    public void burnCard() {
        topCard++;
    }

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
