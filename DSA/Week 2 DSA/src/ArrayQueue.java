public class ArrayQueue implements Queue {
    private final int[] array;
    private final int MAXSIZE;
    private int size = 0;
    private int ptr = 0;

    public void enqueue(int e) throws QueueFullException {
        if (size == MAXSIZE)
            throw new QueueFullException();
        array[(ptr+size++) % MAXSIZE] = e;
    }

    public int dequeue() throws QueueEmptyException {
        if (size == 0)
            throw new QueueEmptyException();
        size--;
        int val = array[ptr];
        ptr = (ptr+1)%MAXSIZE;
        return val;
    }

    public int peek() throws QueueEmptyException {
        if (size == 0)
            throw new QueueEmptyException();
        return array[ptr];
    }

    public ArrayQueue(int MAXSIZE) {
        this.MAXSIZE = MAXSIZE;
        array = new int[MAXSIZE];
    }
}
