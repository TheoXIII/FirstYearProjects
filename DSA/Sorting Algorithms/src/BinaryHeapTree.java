public class BinaryHeapTree {
    final protected int MAX;
    final protected int[] heap;
    protected int n;

    protected int value(int i) {
        if (i < 1 || i > n) {
            throw new IndexOutOfBoundsException();
        }
        return heap[i];
    }
    public boolean isRoot(int i) {return i==1;}
    public int level(int i) {return (int) ( Math.log(i) / Math.log(2));}
    public int parent(int i) {return i/2;}
    public int left(int i) {return 2*i;}
    public int right(int i) {return 2*i+1;}

    public boolean isEmpty() {
        return n == 0;
    }

    protected void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int root() {
        if ( isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return heap[1];
    }

    public int lastLeaf() {
        if ( isEmpty() ) {
            throw new IndexOutOfBoundsException();
        }
        return heap[n];
    }

    public void insert(int p) {
        if (n == MAX) {
            throw new IndexOutOfBoundsException();
        }
        n = n+1;
        heap[n] = p;
        bubbleUp(n);

    }

    protected void bubbleUp(int i) {
        if ( i==1 ) return; //i is the root

        if (heap[i] > heap[parent(i)]) {
            swap(i,parent(i));
            bubbleUp(parent(i));
        }
    }

    public void deleteRoot() {
        if (n < 1) {
            throw new IndexOutOfBoundsException();
        }
        heap[1] = heap[n];
        n = n-1;
        bubbleDown(1);
    }

    public void delete(int i) {
        n-=1;
        try {
            update(i, heap[n+1]);
        } catch (IndexOutOfBoundsException e) {
            n+=1;
            throw new IndexOutOfBoundsException();
        }
    }

    public void update(int i, int priority) {
        if (n < 1) {
            throw new IndexOutOfBoundsException();
        }
        if (i < 1 || i > n)
            throw new IndexOutOfBoundsException();
        heap[i] = priority;
        bubbleUp(i);
        bubbleDown(i);
    }

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

    private void whitespace(int w) {
        for (int i=0; i < w; i++) {
            System.out.print(" ");
        }
    }

    private void heapify() {
        for (int i = n/2; i>0; i--) {
            bubbleDown(i);
        }
    }

    public void outputTree() {
        if (n == 0) {
            System.out.println("Tree empty!");
            return;
        }
        int levels = level(n)+1;
        int count=1;
        int newcount;
        for (int i=0; i < levels; i++) {
            newcount = count;
            whitespace(levels-i);
            for (int j=count; j < newcount+Math.pow(2,i); j++) {
                System.out.print(heap[j]);
                whitespace(levels-i);
                count++;
                if (count > n)
                    break;
            }
            System.out.printf("%n");

        }
    }

    public BinaryHeapTree(int MAX) {
        this.MAX = MAX;
        heap = new int[MAX+1];
        n=0;
    }

    public BinaryHeapTree(int MAX, int[] load) {
        if (load.length > MAX) {
            throw new IndexOutOfBoundsException();
        }
        this.MAX = MAX;
        heap = new int[MAX + 1];
        System.arraycopy(load, 0, heap, 1, load.length);
        n=load.length;
        heapify();
    }

}

