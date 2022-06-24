import java.util.Arrays;

public class RunAStar {
    public static void main(String[] args) {
        NodeA[] matrix = new NodeA[6];
        for (int i=0; i<5; i++) {
            matrix[i] = new NodeA(i,10);
        }
        matrix[5] = new NodeA(5,0);
        matrix[0].setEdges(new EdgeA[]{new EdgeA(matrix[1], 3), new EdgeA(matrix[2], 1), new EdgeA(matrix[3],7)});
        matrix[1].setEdges(new EdgeA[]{new EdgeA(matrix[4],2)});
        matrix[2].setEdges(new EdgeA[]{new EdgeA(matrix[1], 1), new EdgeA(matrix[3],5), new EdgeA(matrix[4],1)});
        matrix[3].setEdges(new EdgeA[]{new EdgeA(matrix[4],1), new EdgeA(matrix[5],2)});
        matrix[4].setEdges(new EdgeA[]{new EdgeA(matrix[3], 3), new EdgeA(matrix[5],6)});
        matrix[5].setEdges(new EdgeA[]{});
        System.out.println(Arrays.toString(AStar.run(matrix, 0)));
    }
}
