public class UseStack {
    public static void main(String[] args) throws StackFullException, StackEmptyException {
        Stack stack = new ArrayStack(10);
        stack.push(10);
        stack.push(11);
        stack.push(13);
        stack.push(14);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        Stack stack2 = new LinkedListStack();
        stack2.push(10);
        stack2.push(11);
        stack2.push(13);
        stack2.push(14);
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
    }
}
