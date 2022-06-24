public interface Queue {
    public void enqueue(int e) throws QueueFullException;
    public int dequeue() throws QueueEmptyException;
    public int peek() throws QueueEmptyException;
}
