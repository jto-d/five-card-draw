import java.util.ArrayList;

public class Player {

    private String name;
    private int chips;
    private ArrayList<Card> cards;
    public int potCommitment;

    public Player(String name, int chips) {
        this.chips = chips;
        this.name = name;
        cards = new ArrayList<>();
        potCommitment = 0;
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public void bet(int amount) {
        potCommitment += amount;
        chips -= amount;
    }

    public void drawCard(Card c) {
        cards.add(c);
    }

    public void removeCard(int index) {
        cards.remove(index);
    }

    public void clearCards() {
        cards.clear();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void winPot(int winnings) {
        chips += winnings;
    }


    public Hand getHand() {
        Card[] c = new Card[5];
        Hand h = new Hand(cards.toArray(c));
        return h;
    }

    public static void main(String[] args) {

    }
}
