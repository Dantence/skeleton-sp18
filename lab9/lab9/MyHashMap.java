package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        return (double) size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize();
        }
        if (!containsKey(key)) {
            size += 1;
        }
        buckets[hash(key)].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private void resize() {
        ArrayMap<K, V> newBuckets[] = new ArrayMap[buckets.length * 2];
        for (int i = 0; i < newBuckets.length; i += 1) {
            newBuckets[i] = new ArrayMap<>();
        }

        for(int i = 0; i < buckets.length; i += 1) {
            for (K key : buckets[i].keySet()) {
                newBuckets[i].put(key, buckets[i].get(key));
            }
        }
        buckets = newBuckets;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(int i = 0; i < size; i += 1) {
            keySet.addAll(buckets[i].keySet());
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        return buckets[hash(key)].remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (!get(key).equals(value)) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {

        private int bucketIndex;
        private Iterator<K> iterator;

        private void findFirstNotEmptyBucket() {
            int newBucketIndex = bucketIndex;
            while (newBucketIndex < buckets.length) {
                if (newBucketIndex > bucketIndex && buckets[newBucketIndex].size() > 0) {
                    iterator = buckets[newBucketIndex].iterator();
                    bucketIndex = newBucketIndex;
                    break;
                }
                newBucketIndex += 1;
            }
        }

        HashMapIterator() {
            bucketIndex = 0;
            findFirstNotEmptyBucket();
        }

        @Override
        public boolean hasNext() {
            if (iterator.hasNext()) {
                return true;
            }
            findFirstNotEmptyBucket();
            return bucketIndex < buckets.length && iterator.hasNext();
        }

        @Override
        public K next() {
            return iterator.next();
        }
    }
}
