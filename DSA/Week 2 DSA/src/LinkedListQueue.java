import java.util.LinkedList;

public class LinkedListQueue implements Queue {
    private final LinkedList<Integer> list = new LinkedList<>();
    private int size = 0;

    @Override
    public void enqueue(int e) {
        list.add(e);
        size++;
    }

    @Override
    public int dequeue() throws QueueEmptyException {
        if (size == 0)
            throw new QueueEmptyException();
        int value = list.getFirst();
        list.removeFirst();
        size--;
        return value;
    }

    @Override
    public int peek() throws QueueEmptyException {
        if (size == 0)
            throw new QueueEmptyException();
        return list.getFirst();
    }
}
