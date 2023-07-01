
package io.github.uhmarcel;


public interface H_Map<K,V> {
    
    public V get(Object key);
    public V put(K key, V value);
    public V remove(Object key);
    public boolean containsKey(Object key);
    public H_Set<K> keySet();
    public H_Collection<V> values();
    public int size();
    public boolean isEmpty();
    public H_Set<H_Map.Entry<K,V>> entrySet();
    
    interface Entry<K,V> {
        public K getKey();
        public V getValue();
    }

}
