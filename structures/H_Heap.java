package structures;

import java.util.Arrays;
import java.util.Random;

public class H_Heap<E extends Comparable<E>> implements H_Queue<E> {
    
    private static final int DEFAULT_SIZE = 4;
    
    private Object[] heap;
    private int size;
    
    public H_Heap() {
        this(DEFAULT_SIZE);
    }
    
    public H_Heap(int capacity) {
        this.heap = new Object[capacity];
        this.size = 0;
    }
    
    public boolean offer(E element) {
        if (size == heap.length) grow();
        heap[size] = element;
        size++;
        percolateUp(size - 1);
        return true;
    }
    
    public E poll() {
        if (size < (heap.length >> 1)) shrink();
        E head = (E) heap[0];
        size--;
        swap(0, size);
        percolateDown(0);
        return head;
    }
    
    public E peek() {
        return (E) heap[0];
    }
    
    public boolean add(E element) {
        return offer(element);
    }
    
    public E remove() {
        return poll();
    }

    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return (this.size == 0);
    }

    private void percolateUp(int index) {
        Comparable<? super E> key = (Comparable<? super E>) heap[index];
        int child = index; 
        while (child > 0) {
            int parent = (child - 1) / 2; 
            if (key.compareTo((E) heap[parent]) >= 0) break;
            swap(child, parent);
            child = parent;
        }
    }
    
    private void percolateDown(int index) {
        Comparable<? super E> key = (Comparable<? super E>) heap[index];
        int parent = index;
        while (parent < size) {
            int child = parent * 2 + 1;
            if (child < size-1 && ((E) heap[child]).compareTo((E) heap[child + 1]) > 0) {
                child++;
            }       
            if (child >= size || key.compareTo((E) heap[child]) <= 0) {
                break;
            }
            swap(child, parent);
            parent = child;
        }
    }
    
    private void grow() {
        if (heap.length > Integer.MAX_VALUE / 2) return;
        int doubleCapacity = heap.length << 1;
        Object[] doubleHeap = new Object[doubleCapacity];
        for (int i = 0; i < size; i++) {
            doubleHeap[i] = heap[i];
        }
        this.heap = doubleHeap;
    }
    
    private void shrink() {
        if (heap.length <= DEFAULT_SIZE) return;
        int halfCapacity = heap.length >> 1;
        Object[] halfHeap = new Object[halfCapacity];
        for (int i = 0; i < size; i++) {
            halfHeap[i] = heap[i];
        }
        this.heap = halfHeap;
    }
    
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public String toString() {
        return Arrays.toString(heap);
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Heap<Integer> H = new H_Heap<>();
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            H.add(r.nextInt(10000));
        }
        
        while (!H.isEmpty()) {
            System.out.print(H.poll() + ", ");
        }
    }


}
