
package structures;

import interfaces.H_Map;
import interfaces.H_Set;
import interfaces.H_Collection;


public class H_LinkedHashMap<K, V> implements H_Map<K, V> {

    private H_Map<K, H_LinkedList<MapEntry>.ListNode> map;
    private H_LinkedList<MapEntry> list;
    
    public H_LinkedHashMap() {
        this.map = new H_HashMap<>();
        this.list = new H_LinkedList<>();
    }
    
    @Override
    public V get(Object key) {
        return map.get(key).value().getValue();
    }

    @Override
    public V put(K key, V value) {
        V previous = remove(key); 
        list.add(new MapEntry(key, value));
        map.put(key, list.getNode(list.size() - 1));
        return previous;
    }

    @Override
    public V remove(Object key) {
        if (!map.containsKey(key)) return null;
        return map.remove(key).remove().getValue();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public H_Set<K> keySet() {
        H_Set<K> keys = new H_LinkedHashSet<>();
        for (MapEntry entry : list)
            keys.add(entry.getKey());
        return keys;
    }

    @Override
    public H_Collection<V> values() {
        H_Collection<V> values = new H_ArrayList<>();
        for (MapEntry entry : list) 
            values.add(entry.getValue());
        return values;
    }
    
    @Override
    public H_Set<Entry<K, V>> entrySet() {
        H_Set<Entry<K, V>> set = new H_LinkedHashSet<>();
        for (MapEntry entry : list) 
            set.add(entry);
        return set;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }
    
    @Override
    public String toString() {
        return this.entrySet().toString();
    }

    class MapEntry implements H_Map.Entry<K, V> {
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
        H_Map<Integer,Integer> map = new H_LinkedHashMap<>();
        for (int i = 31; i >= 0; i--) {
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
