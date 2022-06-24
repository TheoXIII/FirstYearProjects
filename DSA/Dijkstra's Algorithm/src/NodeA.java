import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NodeA implements Comparable<NodeA> {
    private final int h;

    protected int d; protected NodeA p;
    protected int id;
    private List<EdgeA> edges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getD() {
        if (d == -1)
            return Integer.MAX_VALUE;
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public NodeA getP() {
        return p;
    }

    public void setP(NodeA p) {
        this.p = p;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return getH() + getD();
    }

    public int compareTo(NodeA n) {
        if (d == -1)
            return 1;
        return compare(this,n);
    }

    public int compare(NodeA n1, NodeA n2) {return n1.getF() - n2.getF();}

    public int getNumEdges() {
        return edges.size();
    }

    public EdgeA getEdge(int i) {
        return edges.get(i);
    }

    public void setEdges(EdgeA[] edges) {
        this.edges = new LinkedList<>();
        this.edges.addAll(Arrays.asList(edges));
    }

    public NodeA(int id, int h) {
        setId(id);
        setD(-1);
        setP(this);
        this.h = h;
    }
}
