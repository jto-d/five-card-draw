import java.util.ArrayList;

public class Player {

    private String name;
    private int chips;
    private ArrayList<Card> cards;

    public Player(String name, int chips) {
        this.chips = chips;
        this.name = name;
        cards = new ArrayList<>();
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public int bet(int amount) {
        chips -= amount;
        return amount;
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


    public String getHand() {
        Card[] c = new Card[5];
        Hand h = new Hand(cards.toArray(c));
        return h.getRank();
    }

    public static void main(String[] args) {

    }
}
