package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_List;
import java.util.Iterator;

public class H_ArrayList<E> implements H_List<E> {
    
    private static final int DEFAULT_SIZE = 4;
    private Object[] array;
    private int size;

    public H_ArrayList() {
        this(DEFAULT_SIZE);
    }
    
    public H_ArrayList(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean contains(Object o) {
        return (this.indexOf(o) != -1);
    }

    public boolean add(E e) {
        if (size == array.length) grow();
        this.array[size] = e;
        size++;
        return true;
    }
    
    public boolean remove(Object o) {
        if (size < (array.length >> 1)) shrink();
        int i = this.indexOf(o);
        if (i == -1) 
            return false;
        this.remove(i);
        return true;
    }

    public void clear() {
        this.array = new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    public E get(int i) {
        return (E) this.array[i];
    }

    public E set(int i, E e) {
        E prev = (E) array[i];
        array[i] = e;
        return prev;
    }

    public void add(int i, E e) {
        if (size == array.length) grow();
        for (int j = size - 1; j >= i; j--) {
            array[j + 1] = array[j];
        }
        array[i] = e; 
        size++;
    }

    public E remove(int i) {
        if (size < (array.length >> 1)) shrink();
        E removed = (E) array[i];
        for (int j = i; j < size - 1; j++) {
            array[j] = array[j + 1];
        }
        size--;
        return removed;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o))
                return i;
        }
        return -1;
    }
    
    public Iterator<E> iterator() {
        return new ArrayListIterator<>();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(array[i]).append(", ");
        }
        if (size != 0) {
            sb.setLength(sb.length() - 2);
        } 
        sb.append(']');
        return sb.toString();
    }

    private void grow() {
        if (array.length > Integer.MAX_VALUE / 2) return;
        int doubleCapacity = array.length << 1;
        Object[] doubleArray = new Object[doubleCapacity];
        for (int i = 0; i < size; i++) {
            doubleArray[i] = array[i];
        }
        this.array = doubleArray;
    }
    
    private void shrink() {
        if (array.length <= DEFAULT_SIZE) return;
        int halfCapacity = array.length >> 1;
        Object[] halfArray = new Object[halfCapacity];
        for (int i = 0; i < size; i++) {
            halfArray[i] = array[i];
        }
        this.array = halfArray;
    }
    
    class ArrayListIterator<E> implements Iterator<E> {
        private int pointer;
        
        public ArrayListIterator() {
            this.pointer = 0;
        }
        
        public boolean hasNext() {
            return pointer < size;
        }

        public E next() {
            return (E) array[pointer++];
        }
    }

}
