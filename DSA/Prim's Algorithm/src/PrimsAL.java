import java.util.Arrays;
import java.util.PriorityQueue;

public class PrimsAL {

    public static String[] run(Node[] matrix) {
        PriorityQueue<Node> q = new PriorityQueue<>();
        matrix[0].setD(0);
        q.addAll(Arrays.asList(matrix));
        Node next;
        while (q.size() != 0) {
            next = q.remove();
            for (int i=0; i < next.getNumEdges(); i++) {
                if (next.getEdge(i).getWeight() < next.getEdge(i).getN().getD()) {
                    next.getEdge(i).getN().setD(next.getEdge(i).getWeight());
                    next.getEdge(i).getN().setP(next);
                }
            }
        }
        String[] edges = new String[matrix.length-1];
        for (int i=1; i<matrix.length; i++) {
            edges[i-1] = matrix[i].getP().getId() + "," + (matrix[i].getId()) ;
        }
        return edges;
    }
}

