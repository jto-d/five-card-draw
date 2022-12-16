import java.awt.image.BufferedImage;


/* Card object that has a rank, suit, and image associated
 with it. Implements the comparable interface in order to
 compare card ranks in order to make a hand
*/
public class Card implements Comparable<Card> {
    private int rank;
    private char suit;
    private BufferedImage image;

    // create a card object with a rank and suit
    public Card(int rank, char suit, BufferedImage image) {
        this.rank = rank;
        this.suit = suit;
        this.image = image;
    }

    // create a card object without an image (used for testing)
    public Card(int rank, char suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // return the image
    public BufferedImage getImage() {
        return image;
    }

    // return the rank
    public int getRank() {
        return rank;
    }

    // return the suit
    public char getSuit() {
        return suit;
    }

    // compares the ranks of the current card and another
    public int compareTo(Card c) {
        return rank - c.getRank();
    }

    // returns a String containing the card's rank and suit
    public String toString() {
        return rank + "" + suit;
    }

    public static void main(String[] args) {
        Card c = new Card(6, 'c');
        System.out.println(c.getRank());
        System.out.println(c.getSuit());
    }
}
