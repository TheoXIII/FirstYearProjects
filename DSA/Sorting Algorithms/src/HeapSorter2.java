public class HeapSorter2 {
    final protected int[] heap;
    protected int n;

    protected void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int left(int i) {return 2*i;}
    public int right(int i) {return 2*i+1;}

    protected void bubbleDown(int i) {
        if (left(i) > n) //no children
            return;
        else if (right(i) > n) //only left child
            if (heap[i] < heap[left(i)])
                swap(i,left(i));
            else
                return;
        else if (heap[left(i)] > heap[right(i)] && heap[i] < heap[left(i)]) { //two children
            swap(i, left(i));
            bubbleDown(left(i));
        }
        else if (heap[i] < heap[right(i)]) {
            swap(i,right(i));
            bubbleDown(right(i));
        }
    }

    private void heapify() {
        for (int i = n/2; i>0; i--) {
            bubbleDown(i);
        }
    }

    public HeapSorter2(int[] load) {
        this.n = load.length;
        heap = new int[n+1];
        System.arraycopy(load, 0, heap, 1, load.length);
        heapify();
        while (n!=0) {
            swap(1,n); //Move highest priority element to the end.
            n--;
            bubbleDown(1); //Put new root element in the correct place.
        }
        System.arraycopy(heap, 1, load, 0, load.length);
    }
}
