
package structures;

import java.util.Iterator;

public class H_AssociativeList<K,V> implements H_Map<K,V> {

    private H_LinkedList<MapEntry> list;
    
    public H_AssociativeList() {
        this.list = new H_LinkedList<>();
    }
    
    @Override
    public V get(Object key) {
        for (MapEntry entry : list) {
            if (key.equals(entry.getKey())) { 
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // Ignore duplicate values to achieve O(1)
        list.addFirst(new MapEntry(key, value));
        return null;
    }

    @Override
    public V remove(Object key) {
        Iterator<MapEntry> iterator = list.iterator();
        boolean firstFound = true;
        V lastValue = null;
        
        while (iterator.hasNext()) {
            MapEntry entry = iterator.next();
            if (key.equals(entry.getKey())) {
                if (firstFound) {
                    lastValue = entry.getValue();
                    firstFound = false;
                }
                iterator.remove();
            }
        }
        return lastValue;
    }

    @Override
    public boolean containsKey(K key) {
        for (MapEntry entry : list) {
            if (key.equals(entry.getKey())) { 
                return true;
            }
        }
        return false;
    }

    @Override
    public H_Set<K> keySet() {
        H_Set<K> set = new H_HashSet<>();
        for (MapEntry entry : list) {
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public H_Collection<V> values() {
        pruneDuplicates();
        H_Collection<V> values = new H_ArrayList<>();
        for (MapEntry entry : list) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public H_Set<Entry<K, V>> entrySet() {
        H_Set<Entry<K,V>> set = new H_HashSet<>();
        for (MapEntry entry : list) {
            set.add(entry);
        }
        return set;
    }
    
    public String toString() {
        return list.toString();
    }
    
    private void pruneDuplicates() {
        H_Set<K> seen = new H_HashSet<>();
        Iterator<MapEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            MapEntry entry = iterator.next();
            if (seen.contains(entry.getKey())) {
                iterator.remove();
            } else {
                seen.add(entry.getKey());
            }
        }
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
        H_Map<Integer, Integer> map = new H_AssociativeList<>();
        for (int i = 0; i < 32; i++) {
            map.put(i, 2);
            map.put(i, i);
        }
        
        for (int i = 12; i < 24; i++) {
            map.put(i, 27);
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
        }
    }
    
}
