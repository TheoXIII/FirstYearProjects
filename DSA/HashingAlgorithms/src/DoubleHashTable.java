public class DoubleHashTable implements HashTable{
    private int n;
    private StringPlus[] a;
    private int size = 0;

    private static class StringPlus {
        private final String s;
        private final boolean tombstone;

        public String getS() {
            return s;
        }

        public boolean isTombstone() {
            return tombstone;
        }

        public StringPlus() {
            s = null;
            tombstone = true;
        }

        public StringPlus(String s) {
            this.s = s;
            this.tombstone = false;
        }
    }

    public void enlarge() {
        n = 2*n;
        StringPlus[] oldArray = a;
        a = new StringPlus[n];
        size = 0;
        for (StringPlus s: oldArray) {
            if (s != null && !(s.isTombstone())) {
                try {
                    insert(s.getS());
                } catch (ValueInHashTableException ignored) {}
            }
        }
    }

    @Override
    public void insert(String key) throws ValueInHashTableException {
        int index = -1;
        int i = hashFunction(key,0);
        int count = 0;
        while (a[i] != null) {
            if (key.equals(a[i].getS()))
                throw new ValueInHashTableException();
            if (a[i].isTombstone() && index == -1)
                index = i;
            i = hashFunction(key,++count);
        }
        if (index != -1) {
            a[index] = new StringPlus(key);
        } else {
            a[i] = new StringPlus(key);
        }
        size++;
        if ((float) size/(float) n > MAX_LOAD_FACTOR)
            enlarge();
    }

    @Override
    public void delete(String key) throws ValueNotInHashTableException {
        int i = hashFunction(key,0);
        int count = 0;
        while (a[i] != null) {
            if (key.equals(a[i].getS())) {
                a[i] = new StringPlus(); //Create tombstone.
                size--;
                return;
            }
            i = hashFunction(key,++count);
        }
        throw new ValueNotInHashTableException();
    }

    @Override
    public boolean lookUp(String key) {
        int i = hashFunction(key,0);
        int count = 0;
        while (a[i] != null) {
            if (key.equals(a[i].getS())) {
                return true;
            }
            i = hashFunction(key,++count);
        }
        return false;
    }

    private int hashFunction(String key, int i) {
        int total = 1;
        for (char c: key.toCharArray())
            total*=c;
        int total2 = 0;
        for (char c: key.toCharArray())
            total2+=c;
        total2 = 2*total2+1;
        return (Math.abs(total)+i*total2) % n;
    }

    public DoubleHashTable(int power) {
        n = (int) Math.pow(2,power);
        a = new StringPlus[n];
    }
}
