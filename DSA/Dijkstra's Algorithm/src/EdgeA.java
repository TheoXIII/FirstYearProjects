public class EdgeA {
    private final NodeA n;
    private final int weight;


    public NodeA getN() {
        return n;
    }

    public int getWeight() {
        return weight;
    }

    public EdgeA(NodeA n, int weight) {
        this.n = n;
        this.weight = weight;
    }

}