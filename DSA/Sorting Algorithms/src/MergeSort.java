public class MergeSort {
    private static void merge(int[] a, int left, int mid, int right) {
        int[] tmp = new int[right-left+1];
        int lPtr = left;
        int rPtr = mid;
        for (int i=0; i <= right-left; i++) {
            if (lPtr > mid) {
                tmp[i] = a[rPtr++];
            } else if (rPtr > right) {
                tmp[i] = a[lPtr++];
            }
            else if (a[lPtr] <= a[rPtr]) {
                tmp[i] = a[lPtr++];
            } else {
                tmp[i] = a[rPtr++];
            }
        }
        System.arraycopy(tmp,0,a,left,right-left+1);

    }

    private static void sortRun(int[] a, int left, int right) {
        if (left < right) {
            int mid = (left+right)/2;
            sortRun(a,left,mid);
            sortRun(a,mid+1,right);
            merge(a,left,mid+1,right);
        }
    }

    public static void sort(int[] a) {
        sortRun(a, 0, a.length-1);
    }
}
