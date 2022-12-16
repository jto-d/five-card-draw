import java.awt.image.BufferedImage;

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

    public BufferedImage getImage() {
        return image;
    }

    public int getRank() {
        return rank;
    }

    public char getSuit() {
        return suit;
    }

    public int compareTo(Card c) {
        return rank - c.getRank();
    }

    public String toString() {
        return rank + "" + suit;
    }

    public static void main(String[] args) {

    }
}
