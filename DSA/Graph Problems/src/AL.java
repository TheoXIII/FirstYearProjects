import java.util.LinkedList;
import java.util.List;

public class AL {
    public static boolean isStronglyConnected(Node[] nodes) {
        for (Node t: nodes) {
            if (t.getNumEdges() != nodes.length-1)
                return false;
        }
        return true;
    }

    public static boolean isEdge(Node[] nodes,int i, int j) {
        for (int k=0; k<nodes[i].getNumEdges(); k++) {
            if (nodes[i].getEdge(k).getN().getId() == j)
                return true;
        }
        return false;
    }

    public static boolean isWeaklyConnected(Node[] nodes) {
        for (int i=0; i<nodes.length; i++) {
            for (int j=i; j<nodes.length; j++) {
                if (!(isEdge(nodes,i,j) || isEdge(nodes,j,i)))
                    return false;
            }
        }
        return true;

    }

    /*public static boolean isWeaklyConnected(Node[] nodes) {
        for (int i=0; i<nodes.length; i++) {
            int j=i;
            boolean found = false;
            while (!found)
                for (int k=0; k<nodes[i].getNumEdges(); k++) {
                    if (nodes[i].getEdge(k).getN().getId() == j)
                        found = true;
                }
                for (int k=0; k<nodes[j].getNumEdges(); k++) {
                    if (nodes[j].getEdge(k).getN().getId() == i)
                        found = true;
                }
                if (++j == nodes.length)
                    return false;
            }
        }
        return true;

    }*/
}
