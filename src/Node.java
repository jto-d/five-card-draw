// acts as a node in the circular linked list of players
// each node points to another node, as well as contains
// a Player value
public class Node {

    public Player player;

    public Node next;

    // Node constructor
    public Node(Player p) {
        player = p;
        next = null;
    }

    // returns the Player value
    public Player getPlayer() {
        return player;
    }

}
