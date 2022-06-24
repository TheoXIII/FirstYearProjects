public class AVLTree extends BSTTree {

    private Node rightRotation(Node x) {
        System.out.println(x.val);
        System.out.println("right rotated");
        Node y = new Node(x.left.val, x.left.left, x.left.right);
        x.left = y.right;
        y.right = x;
        return new Node(y.val, y.left, y.right);
    }

    private Node leftRotation(Node y) {
        Node x = new Node(y.right.val, y.right.left, y.right.right);
        y.right = x.left;
        x.left = y;
        System.out.println("left rotated");
        return new Node(x.val, x.left, x.right);

    }

    public void insert(int v)
    {
        if (tree == null)
            tree = new Node(v, null, null);
        else
            insert(v, tree);
    }

    public int findHeight(Node n) {
        if (n == null) {
            return 0;
        }
        return Math.max(findHeight(n.left),findHeight(n.right))+1;
    }

    private void balanceNode(Node n) {
        if (Math.abs(n.getBalance()) > 1) {
            // Case L
            System.out.println("37");
            System.out.println(n.getBalance());
            System.out.println(n.left.getBalance());
            System.out.println(n.toString(0));
            if (n.getBalance() > 0) {
                if (n.left.getBalance() < 0) {
                    n.left = leftRotation(n.left);
                }
                n = rightRotation(n);
                System.out.println(n.val);
            } else { //Case R
                if (n.right.getBalance() > 0) {
                    n.right = rightRotation(n.right);
                }
                n = leftRotation(n);
            }
        }
    }

    private int insert(int v, Node ptr)
    {
        int leftHeight;
        int rightHeight;
        if (v < ptr.val)
        {
            if (ptr.left == null) {
                ptr.left = new Node(v, null, null);
                leftHeight = 1;
            } else {
                leftHeight = insert(v, ptr.left) + 1;
            }
            rightHeight = findHeight(ptr.right);
        }
        else if (v > ptr.val)
        {
            if (ptr.right == null) {
                ptr.right = new Node(v, null, null);
                rightHeight = 1;
            } else {
                rightHeight = insert(v, ptr.right);
            }
            leftHeight = findHeight(ptr.left);
        }
        else
            throw new Error("Value already in tree");
        ptr.setBalance(leftHeight,rightHeight);
        balanceNode(ptr);
        return Math.max(leftHeight,rightHeight);
    }

    public static void main(String[] args)
    {
        AVLTree tree = new AVLTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(9);
        tree.insert(30);
        tree.insert(40);
        tree.insert(25);
        tree.insert(16);
        //tree.tree.left.val = 40;
        System.out.println(tree);
        System.out.println(tree.tree.left.getBalance());
        System.out.println(tree.findHeight(tree.tree));
    }
}
