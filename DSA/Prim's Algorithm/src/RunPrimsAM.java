import java.util.Arrays;

public class RunPrimsAM {
    public static void main(String[] args) {
        int[][] matrix = {{0,3,1,7,-1,-1},
                {-1,0,-1,-1,2,-1},
                {-1,1,0,5,1,-1},
                {-1,-1,-1,0,1,2},
                {-1,-1,-1,3,0,6},
                {-1,-1,-1,-1,-1,0}};
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix.length; j++) {
                if (matrix[i][j] == -1)
                    matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        System.out.println(Arrays.toString(PrimsAM.run(matrix)));
    }
}
