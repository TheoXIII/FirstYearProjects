public class AM {
    public static boolean isStronglyConnected(int[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                if(matrix[i][j] == Integer.MAX_VALUE)
                    return false;
            }
        }
        return true;
    }

    public static boolean isWeaklyConnected(int[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for (int j=i; j<matrix.length; j++) {
                if(matrix[i][j] == Integer.MAX_VALUE && matrix[j][i] == Integer.MAX_VALUE)
                    return false;
            }
        }
        return true;
    }
}
