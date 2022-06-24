public class UseQueue {
    public static void main(String[] args) throws QueueFullException, QueueEmptyException {
        Queue queue = new ArrayQueue(5);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        queue.enqueue(6);
        queue.enqueue(7);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        Queue queue2 = new LinkedListQueue();
        queue2.enqueue(1);
        queue2.enqueue(2);
        queue2.enqueue(3);
        queue2.enqueue(4);
        queue2.enqueue(5);
        System.out.println(queue2.dequeue());
        System.out.println(queue2.dequeue());
        queue2.enqueue(6);
        queue2.enqueue(7);
        System.out.println(queue2.dequeue());
        System.out.println(queue2.dequeue());
        System.out.println(queue2.dequeue());
        System.out.println(queue2.dequeue());
        System.out.println(queue2.dequeue());
    }
}
