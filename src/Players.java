import java.util.ArrayList;

public class Players {

    public Node start;
    private Node curr;


    public Players(ArrayList<Player> p) {
        start = null;
        curr = null;

        for (Player player : p) {
            addNode(player);
        }
    }

    public void addNode(Player p) {
        Node node = new Node(p);

        if (start == null) {
            start = node;
        } else {
            curr.next = node;
        }
        curr = node;
        curr.next = start;

    }

    public String toString() {
        Node node = start;
        String str = "";

        if (start != null) {
            do {
                str += node.player.getName() + ", ";
                node = node.next;
            } while (node != start);
        }

        return str;
    }


    public static void main(String[] args) {
        int STARTING_CHIPS = 1000;
        int BIG_BLIND = STARTING_CHIPS / 10;
        int SMALL_BLIND = BIG_BLIND / 2;

        // initialize players
        int numPlayers = 4;

        ArrayList<Player> players = new ArrayList<>();


        for (int i = 1; i <= numPlayers; i++)
            players.add(new Player("Player " + i, STARTING_CHIPS));

        Players p = new Players(players);

        System.out.println(p);
    }
}
