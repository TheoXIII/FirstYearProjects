import java.util.LinkedList;

public class LinkedListStack implements Stack {
    private final LinkedList<Integer> list = new LinkedList<>();
    private int size = 0;

    public void push(int item) {
        list.add(item);
        size++;
    }

    public int pop() throws StackEmptyException {
        if (size == 0)
            throw new StackEmptyException();
        return list.get(--size);
    }

    public int peek() throws StackEmptyException {
        if (size == 0)
            throw new StackEmptyException();
        return list.get(size-1);
    }

    public void printStack() {
        for (int e: list)
            System.out.printf("%d ",e);
        System.out.printf("%n");
    }
}