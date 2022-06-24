import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {
    private int d; private Node p;
    private int id;
    private List<Edge> edges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Node getP() {
        return p;
    }

    public void setP(Node p) {
        this.p = p;
    }

    public int compareTo(Node n) {
        return compare(this,n);
    }

    public int compare(Node n1, Node n2) {
        return n1.getD() - n2.getD();
    }

    public int getNumEdges() {
        return edges.size();
    }

    public Edge getEdge(int i) {
        return edges.get(i);
    }

    public void setEdges(Edge[] edges) {
        this.edges = new LinkedList<Edge>();
        this.edges.addAll(Arrays.asList(edges));
    }

    public Node(int id) {
        setId(id);
        setD(Integer.MAX_VALUE);
        setP(this);
    }
}
