public class Node {

    public Player player;

    public Node next;

    public Node(Player p) {
        player = p;
        next = null;
    }

    public Player getPlayer() {
        return player;
    }

}
