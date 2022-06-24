import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable implements HashTable {
    private int n;
    private ArrayList<LinkedList<String>> a;
    private int size = 0;

    private void enlarge() { //Insert all elements into new hash table.
        n = 2*n;
        ArrayList<LinkedList<String>> oldArray = a;
        a = new ArrayList<>(n);
        for (int i=0; i<n; i++)
            a.add(new LinkedList<>());
        size = 0;
        for (LinkedList<String> l: oldArray)
            for (String s: l)
                try {
                    insert(s);
                } catch (ValueInHashTableException ignored) {}
    }

    @Override
    public void insert(String key) throws ValueInHashTableException {
        LinkedList<String> list = a.get(hashFunction(key));
        if (list.contains(key))
            throw new ValueInHashTableException();
        list.addFirst(key);
        size++;
        if ((float) size/(float) n > MAX_LOAD_FACTOR)
            enlarge();
    }

    @Override
    public void delete(String key) throws ValueNotInHashTableException {
        LinkedList<String> list = a.get(hashFunction(key));
        for (int i=0; i<list.size(); i++)
            if(list.get(i).equals(key)) {
                list.remove(i);
                size--;
                return;
            }
        throw new ValueNotInHashTableException();
    }

    @Override
    public boolean lookUp(String key) {
        LinkedList<String> list = a.get(hashFunction(key));
        for (String s: list)
            if(s.equals(key))
                return true;
        return false;
    }

    private int hashFunction(String key) {
        int total = 0;
        for (char c: key.toCharArray())
            total+=c;
        return total % n;
    }

    public ChainedHashTable(int size) {
        a = new ArrayList<>(size);
        for (int i=0; i<size; i++)
            a.add(new LinkedList<>());
        n = size;
    }
}
