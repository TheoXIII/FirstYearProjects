public class SampleQAnswer {
    public static void main(String[] args) {
        /*BTree tree = new BTree(3,3,5,20);
        tree.add(10);
        tree.add(25);
        tree.add(15);
        tree.add(35);
        tree.add(40);
        tree.add(30);
        tree.add(50);
        tree.add(55);
        tree.add(45);
        tree.add(60);*/
        BTree tree = new BTree(3,3, new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60});
        tree.printAll();
        tree.add(33);
        tree.printAll();
    }
}
