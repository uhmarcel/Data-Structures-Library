
package structures;

import structures.interfaces.H_Map;
import structures.interfaces.H_Set;
import java.util.Iterator;


public class H_LinkedHashSet<E> implements H_Set<E> {

    private H_Map<E, H_LinkedList<E>.ListNode> map; 
    private H_LinkedList<E> list;
    
    public H_LinkedHashSet() {
        this.map = new H_HashMap<>();
        this.list = new H_LinkedList<>();
    }
    
    @Override
    public boolean add(E e) {
        if (map.containsKey(e)) return false;
        list.add(e);
        map.put(e, list.getNode(list.size() - 1));
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!map.containsKey(o)) return false;
        map.remove(o).remove();
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
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
    public void clear() {
        this.map = new H_HashMap<>();
        this.list = new H_LinkedList<>();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedHashSetIterator();
    }
    
    public String toString() {
        return list.toString();
    }
    
    // Added independent class instead of returning LinkedListIterator to avoid 
    // direct use of Iterator.remove, which would make the map invalid.
    private class LinkedHashSetIterator implements Iterator<E> {
        private Iterator<E> iterator; 
        
        public LinkedHashSetIterator() {
            this.iterator = list.iterator();
        }
        public boolean hasNext() {
            return iterator.hasNext();
        }

        public E next() {
            return iterator.next();
        }
    }
            
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Set<Character> linkedSet = new H_LinkedHashSet<>();
        H_Set<Character> set = new H_HashSet<>();
        
        for (char c : "helloworld".toCharArray()) {
            linkedSet.add(c);
            set.add(c);
        }
        
        System.out.println(linkedSet);
        System.out.println(set);
        System.out.println(linkedSet.contains('o'));
        System.out.println(linkedSet.contains('f'));
        
    }

}
