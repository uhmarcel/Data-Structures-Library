package structures.interfaces;

public interface H_Collection<E> extends Iterable<E> {

    public boolean add(E e);
    
    public boolean remove(Object o);
    
    public boolean contains(Object o);
    
    public int size();
    
    public void clear();
    
    public default boolean isEmpty() {
        return this.size() == 0;
    }
    
    public default Object[] toArray() {
        Object[] clone = new Object[this.size()];
        int index = 0;
        for (E element : this) 
            clone[index++] = element;
        return clone;
    }

    public default <T> T[] toArray(T[] ts) {
        int index = 0;
        for (E element : this) 
            ts[index++] = (T) element;
        return ts;
    }
    
}
