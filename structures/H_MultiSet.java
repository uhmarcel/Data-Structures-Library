
package structures;


public interface H_MultiSet<E> extends H_Collection<E> {
    
    public int add(E object, int ocurrences);        
    public int remove(Object o, int ocurrences);
    public int getCount(E object);
    public int setCount(E object, int ocurrences);
    public H_Set<E> uniqueSet();
    
}
