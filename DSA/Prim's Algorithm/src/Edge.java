public class Edge {
    private final Node n;
    private final int weight;


    public Node getN() {
        return n;
    }

    public int getWeight() {
        return weight;
    }

    public Edge(Node n, int weight) {
        this.n = n;
        this.weight = weight;
    }

}
