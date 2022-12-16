import java.io.IOException;
import java.util.ArrayList;

public class Game {

    public static void main(String[] args) throws IOException {
// create constant variables
        int STARTING_CHIPS = 1000;
        int ANTE = 5;

        // initialize players
        int numPlayers = 4;

        ArrayList<Player> tempPlayers = new ArrayList<>();


        for (int i = 1; i <= numPlayers; i++)
            tempPlayers.add(new Player("Player " + i, i * 1000));

        // implements a circularly linked list
        Players players = new Players(tempPlayers);


        // create deck
        Deck deck = new Deck();
        deck.initializeDeck();

        GUI gui = new GUI(deck);


//        while (players.size() > 1) {

        int pot = 0;

        // shuffle deck
        for (Player player : tempPlayers) {
            player.clearCards();
            player.bet(ANTE);
            pot += ANTE;
        }
        deck.shuffleDeck();

        // put in the antes and draw cards


        for (int i = 0; i < 5; i++) {
            for (Player player : tempPlayers) {
                player.drawCard(deck.getCard());
            }
        }

        int index = 0;
        gui.focusPlayer(players.start);
        gui.addPot(pot);

        // deal cards
//        }
    }
}
