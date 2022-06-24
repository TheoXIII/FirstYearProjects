import java.util.LinkedList;
import java.util.List;

public class PrimsAM {
    private static boolean isUnfinished(boolean[] f) {
        for (boolean w: f) {
            if (!w)
                return true;
        }
        return false;
    }

    private static int getW(int[] d, boolean[] f) {
        int min = -1;
        int minD = Integer.MAX_VALUE;
        for(int i=0; i<d.length;i++) {
            if(d[i] < minD && !f[i]) {
                minD = d[i];
                min = i;
            }
        }
        return min;
    }

    private static int[] getNeighbours(int w, int[][] matrix) {
        List<Integer> neighbours = new LinkedList<Integer>();
        for (int i=0; i<matrix[w].length; i++) {
            if(!(i==w) && matrix[w][i] != Integer.MAX_VALUE)
                neighbours.add(i);
        }
        Object[] objectArr = neighbours.toArray();
        int[] returnArr = new int[objectArr.length];
        for(int i=0;i<objectArr.length;i++) {
            returnArr[i] = (int) objectArr[i];
        }
        return returnArr;
    }

    public static String[] run(int[][] matrix) {
        int[] d = new int[matrix.length];
        int[] p = new int[matrix.length];
        for(int i=0;i<p.length;i++) {
            d[i] = Integer.MAX_VALUE;
            p[i] = i;
        }
        boolean[] f = new boolean[matrix.length];
        d[0] = 0;
        int w;
        while (isUnfinished(f)) {
            w = getW(d,f);
            f[w] = true;
            int[] us = getNeighbours(w,matrix);
            for(int u: us) {
                if(matrix[w][u] < d[u]) {
                    d[u] = matrix[w][u];
                    p[u] = w;
                }
            }
        }
        String[] edges = new String[matrix.length-1];
        for (int i=1; i<matrix.length; i++) {
            edges[i-1] = p[i] + "," + i;
        }
        return edges;
    }
}
