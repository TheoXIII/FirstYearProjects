import java.util.*;

public class RadixSortImproved {
    private static int max(int a[]) {
        int max = a[0];
        for (int i=1; i<a.length; i++)
            if (a[i] > max)
                max = a[i];
        return max;
    }

    private static void sortRun(int a[], int order) {
        List<Deque<Integer>> bins = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            bins.add(new LinkedList<>());
        }
        for (int i: a) {
            bins.get((i % (int) Math.pow(10,order+1))/(int) Math.pow(10,order)).add(i);
        }
        int ptr=0;
        for (int i = 0; i < 10; i++) {
            Deque<Integer> bin = bins.get(i);
            while (!(bin.isEmpty()))
                a[ptr++] = bin.removeFirst();
        }

    }

    public static void sort(int a[]) {
        int order = max(a)/10;
        for (int i=0; i<=order; i++) {
            sortRun(a, i);
        }
    }
}
