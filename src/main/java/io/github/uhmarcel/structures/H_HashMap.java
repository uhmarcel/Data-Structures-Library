package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Map;
import io.github.uhmarcel.H_Set;
import io.github.uhmarcel.H_Collection;
import io.github.uhmarcel.H_List;


public class H_HashMap<K,V> implements H_Map<K,V> {
    
    private static final int DEFAULT_SIZE = 16;
    private H_List<MapEntry>[] map; 
    private int size;
    
    public H_HashMap() {
        this.map = new H_LinkedList[DEFAULT_SIZE];
        this.size = 0;
        for (int i = 0; i < map.length; i++) {
            map[i] = new H_LinkedList<>();
        }
    }

    @Override
    public V get(Object o) {
        K key = (K) o;
        int index = key.hashCode() % map.length;
        return findValue(index, key);
    }

    @Override
    public V put(K key, V value) {
        if (loadFactor() > 0.75) grow();
        V prev = remove(key);
        int index = key.hashCode() % map.length;
        map[index].add(new MapEntry(key, value));
        size++;
        return prev;
    }
    
    @Override
    public V remove(Object o) {
        K key = (K) o;
        int index = key.hashCode() % map.length;
        V removed = findValue(index, key);
        if (removed != null) {
            map[index].remove(new MapEntry(key, null));
            size--;
            if (loadFactor() < 0.25) shrink();
        }
        return removed;
    }

    @Override
    public boolean containsKey(Object o) {
        K key = (K) o;
        int index = key.hashCode() % map.length;
        MapEntry entry = new MapEntry(key, null);
        return map[index].contains(entry);
    }

    @Override
    public H_Set<K> keySet() {
        H_Set<K> keys = new H_HashSet<>();
        for (int i = 0; i < map.length; i++) {
            for (MapEntry entry : map[i]) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    @Override
    public H_Collection<V> values() {
        H_Collection<V> values = new H_ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (MapEntry entry : map[i]) {
                values.add(entry.getValue());
            }
        }
        return values;
    }
    
    @Override
    public H_Set<H_Map.Entry<K,V>> entrySet() {
        H_Set<Entry<K,V>> set = new H_HashSet<>();
        for (int i = 0; i < map.length; i++) {
            for (MapEntry entry : map[i]) {
                set.add(entry);
            }
        }
        return set;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public String toString() {
        return this.entrySet().toString();
    }
    
    private V findValue(int listIndex, K key) {
        for (MapEntry entry : map[listIndex]) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    private double loadFactor() {
        return (double) size / (double) map.length;
    }
    
    private void rehash(H_List<MapEntry>[] prevMap) {
        for (int i = 0; i < prevMap.length; i++) {
            for (MapEntry entry : prevMap[i]) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    private void grow() {
        int n = map.length;
        if (n == Integer.MAX_VALUE) return;
        H_List<MapEntry>[] prevMap = this.map;
        int capacity = (n < Integer.MAX_VALUE / 2) ? n * 2 : Integer.MAX_VALUE;
        this.map = new H_LinkedList[capacity];
        this.size = 0;
        for (int i = 0; i < map.length; i++) {
            map[i] = new H_LinkedList<>();
        }
        rehash(prevMap);
    }
    
    private void shrink() {
        int n = map.length;
        if (n <= DEFAULT_SIZE) return;
        H_List<MapEntry>[] prevMap = this.map;
        int capacity = Math.max(n / 2, DEFAULT_SIZE);
        this.map = new H_LinkedList[capacity];
        this.size = 0;
        for (int i = 0; i < map.length; i++) {
            map[i] = new H_LinkedList<>();
        }
        rehash(prevMap);
    }    
    
    class MapEntry implements H_Map.Entry<K,V> {
        private final K key;
        private final V value;
        
        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public int hashCode() {
            return key.hashCode();
        }
        
        public boolean equals(Object o) {
            MapEntry entry = (MapEntry) o;
            return key.equals(entry.getKey());
        }
        
        public String toString() {
            return key + "=" + value;
        }
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_HashMap<Integer,Integer> map = new H_HashMap<>();
        for (int i = 0; i < 32; i++) {
            map.put(i, 2);
            map.put(i, i);
            System.out.println(map.loadFactor());
        }
        
        for (int i = 12; i < 24; i++) {
            map.put(i, 27);
            System.out.println(map.loadFactor());
        }
        
        System.out.println(map);
        System.out.println(map.get(12));
        System.out.println(map.get(2));
        System.out.println(map.containsKey(31));
        System.out.println(map.containsKey(32));
        System.out.println(map.keySet());
        System.out.println(map.keySet().size());
        System.out.println(map.values());
        System.out.println(map.values().size());
        System.out.println(map.size());
        
        for (int i = 0; i < 32; i++) {
            map.remove(i);
            System.out.println(map);
            System.out.println(map.loadFactor());
        }
    }

}
