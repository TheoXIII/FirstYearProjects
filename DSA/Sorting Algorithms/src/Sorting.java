import java.util.Arrays;

public class Sorting {

    public static void swap(int i, int j, int[] a) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void bubbleSort(int[] a) {
        for (int i=0; i < a.length; i++) {
            for (int j=0; j < i; j++) {
                if (a[j] > a[j+1]) {
                    swap(j,j+1,a);
                }
            }
        }
    }

    public static void insertionSort(int[] a) {
        for (int i=1; i < a.length; i++) {
            int j = i;
            int t = a[j];
            while (j > 0 && t < a[j-1]) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = t;
        }
    }

    public static void selectionSort(int[] a) {
        for (int i=0; i < a.length; i++) {
            int min = i;
            for (int j=i+1; j < a.length; j++) {
                if (a[j] < a[min])
                    min = j;
            }
            swap(i,min,a);
        }
    }

    public static void heapSort(int[] a) {
        HeapSorter sorter = new HeapSorter(a);
        System.arraycopy(sorter.heapSort(),1,a,0,a.length);
    }

    public static void heapSort2(int[] a) {
        new HeapSorter2(a);
    }

    public static void pigeonholeSort(int[] a) { //a must contain elements from 0 to n-1
        int[] tmp = new int[a.length];
        for (int i : a) tmp[i] = i;
        System.arraycopy(tmp,0,a,0,a.length);
    }

    public static void pigeonholeSortInPlace(int[] a) { //a must contain elements from 0 to n-1
        for (int i=0; i < a.length; i++)
            while (a[i] != i)
                swap(a[i],i,a);
    }

    public static void main(String[] args) {
        int[] a = {5,12,6,3,11,8,4};
        QuickSort.sort(a);
        System.out.println(Arrays.toString(a));
        int[] b = {7,3,4,1,6,2,5,0};
        pigeonholeSortInPlace(b);
        System.out.println(Arrays.toString(b));
        int[] c = {927,3712,9798,7542,3028,2142,2734,6876,3579,9662};
        RadixSort.sort(c);
        System.out.println(Arrays.toString(c));
        MergeSort.sort(b);
        int[] d = {5,12,6,3,11,8,4};
        heapSort2(d);
        System.out.println(Arrays.toString(d));
    }
}
