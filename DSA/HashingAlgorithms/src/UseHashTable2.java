import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UseHashTable2 {
    public static void main(String[] args) {
        HashTable table1 = new ChainedHashTable(8);
        HashTable table2 = new DoubleHashTable(3);
        boolean works1 = true;
        boolean works2 = true;
        try {
            File words = new File("/home/twp/afnom/fuzz_words.txt");
            Scanner reader = new Scanner(words);
            Scanner reader2 = new Scanner(words);
            Scanner reader3 = new Scanner(words);
            for (int i=0; i<100; i++) {
                String data = reader.nextLine();
                table1.insert(data);
                table2.insert(data);
            }
            for (int i=0; i<100; i++) {
                String data =reader2.nextLine();
                if (i%2 == 0) {
                    table1.delete(data);
                    table2.delete(data);
                }
            }
            for (int i=0; i<100; i++) {
                String data = reader3.nextLine();
                System.out.println(table1.lookUp(data));
                System.out.println(table2.lookUp(data));
            }
            for (int i=0; i<100; i++) {
                String data = reader.nextLine();
                table1.insert(data);
                table2.insert(data);
            }
            for (int i=0; i<100; i++) {
                String data = reader3.nextLine();
                if (!(table1.lookUp(data)))
                    works1 = false;
                if (!(table2.lookUp(data)))
                    works2 = false;
            }
            System.out.println(works1);
            System.out.println(works2);
        } catch (FileNotFoundException | ValueInHashTableException | ValueNotInHashTableException e) {
            e.printStackTrace();
        }
    }
}
