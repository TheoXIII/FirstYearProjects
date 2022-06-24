public class ArrayStack implements Stack {
    private final int[] array;
    private final int MAXSIZE;
    private int size = 0;

    public void push(int item) throws StackFullException {
        if (size == MAXSIZE)
            throw new StackFullException();
        array[size++] = item;
    }

    public int pop() throws StackEmptyException {
        if (size == 0)
            throw new StackEmptyException();
        return array[--size];
    }

    public int peek() throws StackEmptyException {
        if (size == 0)
            throw new StackEmptyException();
        return array[size-1];
    }

    public void printStack() {
        for (int e: array)
            System.out.printf("%d ",e);
        System.out.printf("%n");
    }

    public ArrayStack(int MAXSIZE) {
        this.MAXSIZE = MAXSIZE;
        this.array = new int[MAXSIZE];
    }
}
