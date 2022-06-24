public interface HashTable {
    float MAX_LOAD_FACTOR = 0.75F;
    void insert(String key) throws ValueInHashTableException;
    void delete(String key) throws ValueNotInHashTableException;
    boolean lookUp(String key);
}
