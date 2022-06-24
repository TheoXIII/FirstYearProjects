public interface Stack {
    public void push(int item) throws StackFullException;
    public int pop() throws StackEmptyException;
    public int peek() throws StackEmptyException;
    public void printStack();
}
