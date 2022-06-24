import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    public static int[] run(NodeA[] matrix, int v) {
        PriorityQueue<NodeA> q = new PriorityQueue<>();
        matrix[v].setD(0); //d is distance,
        q.addAll(Arrays.asList(matrix));
        NodeA next;
        int end;
        while (true) {
            next = q.remove();
            if (next.getH() == 0) {
                end = next.getId();
                break;
            }
            for (int i=0; i < next.getNumEdges(); i++) {
                if (next.getD() + next.getEdge(i).getWeight() < next.getEdge(i).getN().getD()) { //Create path to that node if that would make the path to the node shorter than the current node.
                    next.getEdge(i).getN().setD(next.getD() + next.getEdge(i).getWeight());
                    next.getEdge(i).getN().setP(next);
                }
            }
        }
        List<NodeA> order = new LinkedList<>();
        NodeA prev = matrix[end];
        order.add(prev);
        while (prev.getId() != v) {
            prev = prev.getP();
            order.add(0,prev);
        }
        int[] returnArr = new int[order.size()];
        for(int i=0; i<returnArr.length; i++)
            returnArr[i] = order.get(i).getId();
        return returnArr;
    }
}
