
package structures.interfaces;


public interface H_Stack<E> {

    public boolean push(E elem);
    public E pop();
    public E peek();
    
    public int size();
    public boolean isEmpty();
}
