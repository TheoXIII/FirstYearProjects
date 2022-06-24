import java.util.Arrays;

public class HeapSorter extends BinaryHeapTree {

    public int[] heapSort() {
        while (n!=0) {
            swap(1,n); //Move highest priority element to the end.
            n--;
            bubbleDown(1); //Put new root element in the correct place.
        }
        return heap;
    }

    public HeapSorter(int[] load) {
        super(load.length, load);
    }
}
