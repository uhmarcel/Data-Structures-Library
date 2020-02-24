package structures;

public interface H_List<E> {

    public E get(int i);
    public E set(int i, E e);
    public boolean add(E e);
    public boolean remove(Object o);
    public void add(int i, E e);
    public E remove(int i);
    public int size();
    public void clear();
    public boolean isEmpty();
    public boolean contains(Object o);
    public int indexOf(Object o);
    public Object[] toArray();
    public <T> T[] toArray(T[] ts);
    
}
    



