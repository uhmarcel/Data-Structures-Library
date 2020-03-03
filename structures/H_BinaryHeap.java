package structures;

import interfaces.H_Heap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class H_BinaryHeap<E> implements H_Heap<E> {
    
    private static final int DEFAULT_SIZE = 4;
    
    private Comparator<? super E> comparator;
    private E[] heap;
    private int size;
    
    public H_BinaryHeap() {
        this(DEFAULT_SIZE, null);
    }
    
    public H_BinaryHeap(int capacity) {
        this(capacity, null);
    }
    
    public H_BinaryHeap(Comparator<? super E> cmptr) {
        this(DEFAULT_SIZE, cmptr);
    }
    
    public H_BinaryHeap(int capacity, Comparator<? super E> cmptr) {
        this.comparator = cmptr;
        this.heap = (E[]) new Object[capacity];
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
        E head = heap[0];
        size--;
        swap(0, size);
        percolateDown(0);
        return head;
    }
    
    public E peek() {
        return heap[0];
    }
    
    public boolean add(E element) {
        return offer(element);
    }
    
    public E remove() {
        return poll();
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return (this.size == 0);
    }

    private void percolateUp(int index) {
        E element = heap[index];
        int child = index; 
        while (child > 0) {
            int parent = (child - 1) / 2; 
            if (compare(element, heap[parent]) >= 0) break;
            swap(child, parent);
            child = parent;
        }
    }
    
    private void percolateDown(int index) {
        E element = heap[index];
        int parent = index;
        while (parent < size) {
            int child = parent * 2 + 1;
            if (child < size-1 && compare(heap[child], heap[child + 1]) > 0) {
                child++;
            }       
            if (child >= size || compare(element, heap[child]) <= 0) {
                break;
            }
            swap(child, parent);
            parent = child;
        }
    }
    
    private int compare(E elem1, E elem2) {
        if (elem1 == null || elem2 == null)
            throw new NullPointerException();
        if (comparator == null) {
            Comparable<? super E> cmp = (Comparable<? super E>) elem1;
            return cmp.compareTo(elem2);
        } else {
            return comparator.compare(elem1, elem2);
        }
    }
    
    private void grow() {
        if (heap.length > Integer.MAX_VALUE / 2) return;
        int doubleCapacity = heap.length << 1;
        E[] doubleHeap = (E[]) new Object[doubleCapacity];
        for (int i = 0; i < size; i++) {
            doubleHeap[i] = heap[i];
        }
        this.heap = doubleHeap;
    }
    
    private void shrink() {
        if (heap.length <= DEFAULT_SIZE) return;
        int halfCapacity = heap.length >> 1;
        E[] halfHeap = (E[]) new Object[halfCapacity];
        for (int i = 0; i < size; i++) {
            halfHeap[i] = heap[i];
        }
        this.heap = halfHeap;
    }
    
    private void swap(int i, int j) {
        E temp = heap[i];
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
        H_Heap<Integer> H = new H_BinaryHeap<>((a,b) -> b - a);
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            H.offer(r.nextInt(100));
        }
        
        while (!H.isEmpty()) {
            System.out.print(H.poll() + ", ");
        }
    }

    @Override
    public void decreaseKey(E key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void merge(H_Heap<E> heap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
