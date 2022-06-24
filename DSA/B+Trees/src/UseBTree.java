import java.util.List;

public class UseBTree {
    public static void main(String[] args) {
        BTree tree = new BTree(11,10,5,10);
        for (int i=1; i<=1000; i++) {
            if (!(i==5 | i == 10))
                tree.add(i);
        }
        tree.printAll();
        boolean found = true;
        for (int i=1; i<=1000; i++) {
            if(!tree.isPresent(i)) {
                found = false;
                System.out.println(i);
            }
        }
        System.out.println(found);
        System.out.println(tree.isPresent(1001));
        List<Integer> nums = tree.range(600,620);
        System.out.println(nums.toString());
    }

}
