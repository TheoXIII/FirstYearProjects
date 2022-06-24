import java.util.Arrays;

public class RunPrimsAL {
    public static void main(String[] args) {
        Node[] matrix = new Node[6];
        for (int i=0; i<matrix.length; i++) {
            matrix[i] = new Node(i);
        }
        matrix[0].setEdges(new Edge[]{new Edge(matrix[1], 3), new Edge(matrix[2], 1), new Edge(matrix[3],7)});
        matrix[1].setEdges(new Edge[]{new Edge(matrix[4],2)});
        matrix[2].setEdges(new Edge[]{new Edge(matrix[1], 1), new Edge(matrix[3],5), new Edge(matrix[4],1)});
        matrix[3].setEdges(new Edge[]{new Edge(matrix[4],1), new Edge(matrix[5],2)});
        matrix[4].setEdges(new Edge[]{new Edge(matrix[3], 3), new Edge(matrix[5],6)});
        matrix[5].setEdges(new Edge[]{});
        System.out.println(Arrays.toString(PrimsAL.run(matrix)));
    }
}

