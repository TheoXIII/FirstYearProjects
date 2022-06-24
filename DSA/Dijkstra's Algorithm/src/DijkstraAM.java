import java.util.LinkedList;
import java.util.List;

public class DijkstraAM {
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

    public static int[] run(int[][] matrix, int v, int end) {
        int[] d = new int[matrix.length];
        int[] p = new int[matrix.length];
        for(int i=0;i<p.length;i++) {
            d[i] = Integer.MAX_VALUE;
            p[i] = i;
        }
        boolean[] f = new boolean[matrix.length];
        d[v] = 0;
        int w;
        while (isUnfinished(f)) {
            w = getW(d,f);
            f[w] = true;
            int[] us = getNeighbours(w,matrix);
            for(int u: us) {
                if(d[w] + matrix[w][u] < d[u]) {
                    d[u] = d[w] + matrix[w][u];
                    p[u] = w;
                }
            }
        }
        List<Integer> order = new LinkedList<>();
        int prev = end;
        order.add(end);
        while (prev != v) {
            prev = p[prev];
            order.add(0,prev);
        }
        Object[] objectArr = order.toArray();
        int[] returnArr = new int[objectArr.length];
        for(int i=0;i<objectArr.length;i++) {
            returnArr[i] = (int) objectArr[i];
        }
        return returnArr;
    }
}
