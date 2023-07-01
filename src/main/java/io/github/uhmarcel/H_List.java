package io.github.uhmarcel;

public interface H_List<E> extends H_Collection<E> {

    public E get(int i);
    public E set(int i, E e);
    public void add(int i, E e);
    public E remove(int i);
    public int indexOf(Object o);
   
}
    



