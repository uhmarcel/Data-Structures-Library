package structures;

import java.util.Iterator;

public class H_HashSet<E> implements H_Set<E> {
    
    private static final int DEFAULT_CAPACITY = 16;
    private H_List<E>[] array;
    private int size;
    
    public H_HashSet() {
        this.array = new H_LinkedList[DEFAULT_CAPACITY];
        this.size = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = new H_LinkedList<>();
        }
    }

    @Override
    public boolean add(E e) {
        if (this.contains(e)) return false;
        if (loadFactor() > 0.75) grow();
        int index = e.hashCode() % array.length;
        if (!array[index].remove(e)) size++; 
        array[index].add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!this.contains(o)) return false;
        if (loadFactor() < 0.25) shrink();
        int index = o.hashCode() % array.length;
        array[index].remove(o);
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        int index = o.hashCode() % array.length;
        return array[index].contains(o);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public void clear() {
        this.array = new H_LinkedList[DEFAULT_CAPACITY];
        this.size = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = new H_LinkedList<>();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator();
    }
    
    public String toString() {
        H_List<E> list = new H_ArrayList<>();
        for (E elem : this) 
            list.add(elem);
        return list.toString();
    }
    
    private double loadFactor() {
        return (double) size / (double) array.length;
    }
    
    private void grow() {
        int n = array.length;
        if (n == Integer.MAX_VALUE) return;
        H_List<E>[] prevArray = this.array;
        int capacity = (n < Integer.MAX_VALUE / 2) ? n * 2 : Integer.MAX_VALUE;
        this.array = new H_LinkedList[capacity];
        this.size = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = new H_LinkedList<>();
        }
        rehash(prevArray);
    }
    
    private void shrink() {
        int n = array.length;
        if (n <= DEFAULT_CAPACITY) return;
        H_List<E>[] prevArray = this.array;
        int capacity = Math.max(n / 2, DEFAULT_CAPACITY);
        this.array = new H_LinkedList[capacity];
        this.size = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = new H_LinkedList<>();
        }
        rehash(prevArray);
    }
    
    private void rehash(H_List<E>[] prevArray) {
        for (int i = 0; i < prevArray.length; i++) {
            for (E elem : prevArray[i]) {
                this.add(elem);
            }
        }
    }

    class HashSetIterator implements Iterator<E> {
        private int pointer;
        private Iterator<E> listIter;
        
        public HashSetIterator() {
            this.pointer = 1;
            this.listIter = array[0].iterator();
        }
        
        public boolean hasNext() {
            while(!listIter.hasNext() && pointer < array.length) {
                listIter = array[pointer].iterator();
                pointer++;
            }
            return listIter.hasNext();
        }

        public E next() {
            return listIter.next();
        }
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_HashSet<Integer> set = new H_HashSet<>();
        for (int i = 0; i < 32; i++) {
            set.add(i);
            set.add(i);
        }
        System.out.println(set);
        
        for (int elem : set) 
            System.out.print(elem + " ");
        
        System.out.println(set.contains(6));
        System.out.println(set.contains(32));
        
    }

}
