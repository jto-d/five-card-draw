import java.util.Arrays;

public class Hand {

    Card[] cards;

    public Hand(Card[] cards) {
        this.cards = cards;

        // sort the cards based on rank
        Arrays.sort(cards);
    }

    public String getRank() {

        // check for straight / flush
        boolean straight = true;
        boolean flush = true;

        for (int i = 1; i < 5; i++) {
            if (cards[i].getSuit() != cards[0].getSuit())
                flush = false;
            if (cards[i].getRank() - 1 != cards[i - 1].getRank())
                if (!(cards[i - 1].getRank() == 5 && cards[i].getRank() == 14)) {
                    straight = false;
                }

            int highVal = cards[4].getRank();
            if (highVal == 14) {
                highVal = 5;
            }

            if (i == 4)
                if (flush && straight)
                    return "Straight Flush " + highVal;
                else if (flush)
                    return "Flush " + cards[4].getRank();
                else if (straight)
                    return "Straight " + highVal;

        }

        // check for quads
        if (cards[0].getRank() == cards[3].getRank() ||
                cards[1].getRank() == cards[4].getRank())
            return "Quads " + cards[2].getRank();

        // check for full house
        if ((cards[0].getRank() == cards[2].getRank() && cards[3].getRank() == cards[4].getRank()) ||
                (cards[0].getRank() == cards[1].getRank() && cards[2].getRank() == cards[4].getRank()))
            return "Full House " + cards[2].getRank();


        // check for 3
        for (int i = 0; i < 3; i++)
            if (cards[i].getRank() == cards[i + 2].getRank())
                return "Trips " + cards[i].getRank();

        // check for 2 pair
        for (int i = 0; i < 2; i++)
            if (cards[i].getRank() == cards[i + 1].getRank() && cards[i + 2].getRank() == cards[i + 3].getRank())
                return "Two Pair " + cards[i + 3].getRank();
        if (cards[0].getRank() == cards[1].getRank() && cards[3].getRank() == cards[4].getRank())
            return "Two Pair " + cards[3].getRank();

        // check for pair
        for (int i = 0; i < 4; i++)
            if (cards[i].getRank() == cards[i + 1].getRank())
                return "Pair " + cards[i].getRank();

        // highest card
        return "High Card " + cards[4].getRank();

    }

    public static void main(String[] args) {

        Card[] c = new Card[5];

//        c[0] = new Card(2, 'c');
//        c[1] = new Card(2, 'h');
//        c[2] = new Card(8, 'c');
//        c[3] = new Card(8, 'd');
//        c[4] = new Card(14, 'c');


        Hand h = new Hand(c);

        System.out.println(h.getRank());

    }
}
