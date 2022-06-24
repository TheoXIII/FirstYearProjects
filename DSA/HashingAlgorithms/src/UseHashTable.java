public class UseHashTable {
    public static void main(String[] args) throws ValueNotInHashTableException, ValueInHashTableException {
        HashTable table1 = new ChainedHashTable(8);
        table1.insert("hello");
        table1.insert("world");
        table1.insert("all");
        System.out.println(table1.lookUp("hello"));
        System.out.println(table1.lookUp("world"));
        System.out.println(table1.lookUp("all"));
        table1.delete("world");
        System.out.println(table1.lookUp("world"));

        HashTable table2 = new DoubleHashTable(3);
        table2.insert("hello");
        table2.insert("world");
        table2.insert("all");
        System.out.println(table2.lookUp("hello"));
        System.out.println(table2.lookUp("world"));
        System.out.println(table2.lookUp("all"));
        table2.delete("world");
        System.out.println(table2.lookUp("world"));
        table2.insert("world");
        System.out.println(table2.lookUp("world"));
    }
}
