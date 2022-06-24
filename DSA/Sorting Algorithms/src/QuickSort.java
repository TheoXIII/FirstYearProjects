public class QuickSort {
    private static void swap(int i, int j, int[] a) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int partition_stable(int[] a, int left, int right) { //Unstable, saves memory.
        int p = a[left];
        int[] tmpl = new int[right-left];
        int[] tmpr = new int[right-left];
        int ptrl = 0;
        int ptrr = 0;
        for (int i=left+1; i<=right; i++) {
            if (a[i] <= p)
                tmpl[ptrl++] = a[i];
            else
                tmpr[ptrr++]= a[i];
        }
        a[left+ptrl] = p;
        System.arraycopy(tmpl,0,a,left,ptrl);
        System.arraycopy(tmpr,0,a,left+ptrl+1,ptrr);
        return left+ptrl;

    }

    private static int partition(int[] a, int left, int right) { //Unstable, saves memory.
        int p = a[left];
        int l = left+1;
        int r = right;
        while (l <= r) {
            if (a[l] < p)
                swap(l, l++ - 1, a);
            else
                swap(l,r--,a);
        }
        return l-1;
    }

    private static void sortRun(int[] a, int left, int right) {
        if (left < right) {
            int p = partition_stable(a,left,right);
            sortRun(a,left,p-1);
            sortRun(a,p+1,right);
        }
    }

    public static void sort(int[] a) {
        sortRun(a,0,a.length-1);
    }
}
