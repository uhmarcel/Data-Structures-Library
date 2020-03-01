
package structures;

import interfaces.H_Map;
import interfaces.H_Set;
import interfaces.H_MultiSet;
import java.util.Iterator;


public class H_HashMultiSet<E> implements H_MultiSet<E> {

    private H_Map<E, Integer> map;
    
    @Override
    public boolean add(E object) {
        int prev = (map.containsKey(object)) ? map.get(object) : 0;
        map.put(object, prev + 1);
        return true;
    }
    
    @Override
    public int add(E object, int ocurrences) {
        if (ocurrences < 0) throw new IllegalArgumentException();
        int prev = (map.containsKey(object)) ? map.get(object) : 0;
        map.put(object, prev + ocurrences);
        return prev;
    }
    
    @Override
    public boolean remove(Object object) {
        if (!map.containsKey(object)) return false;
        int prev = map.get(object);
        if (prev == 1) 
            map.remove(object);
        else
            map.put((E) object, prev - 1);
        return true;
    }

    @Override
    public int remove(Object object, int ocurrences) {
        if (ocurrences < 0) throw new IllegalArgumentException();
        if (!map.containsKey(object)) return 0;
        int prev = map.get(object);
        if (prev <= ocurrences)
            map.remove(object);
        else
            map.put((E) object, prev - ocurrences);
        return prev;
    }

    @Override
    public int getCount(E object) {
        return map.get(object);
    }

    @Override
    public int setCount(E object, int ocurrences) {
        if (ocurrences < 0) throw new IllegalArgumentException();
        if (ocurrences == 0) return map.remove(object);
        return map.put(object, ocurrences);
    }

    @Override
    public H_Set<E> uniqueSet() {
        return map.keySet();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        this.map = new H_HashMap<>();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

}
