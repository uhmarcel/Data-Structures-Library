package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Heap;
import io.github.uhmarcel.H_PriorityQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class H_BinaryHeap<E> implements H_Heap<E> {
    
    // find-min:        O(1)
    // delete-min:      O(logn)
    // insert:          O(logn)
    // decrease-key:    O(logn)
    // merge:           O(n)
    
    private static final int DEFAULT_SIZE = 4;
    
    private Comparator<? super E> comparator;
    private int[] positions;
    private Element<E>[] heap;
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
        this.heap = new Element[capacity];
        this.size = 0;
    }
    
    @Override
    public ElementKey<E> insert(E element) {        
        if (size == heap.length) grow();
        Element<E> node = new Element(element, size);
        heap[size] = node;
        size++;
        percolateUp(size - 1);
        return node;
    }

    @Override
    public E deleteMin() {
        if (size == 0) return null;

        if (size < (heap.length >> 1)) shrink();
        E head = heap[0].value;
        heap[0].invalidateKey();
        size--;
        swap(0, size);
        percolateDown(0);
        return head;
    }

    @Override
    public E findMin() {
        if (size == 0) return null;
        return heap[0].value;
    }

    @Override 
    public void merge(H_Heap<E> heap) {
        if (!(heap instanceof H_BinaryHeap)) {
            throw new IllegalArgumentException();
        }

        H_BinaryHeap<E> other = (H_BinaryHeap<E>) heap;
        Element<E>[] elements = new Element[size + other.size];
        System.arraycopy(this.heap, 0, elements, 0, size);
        System.arraycopy(other.heap, 0, elements, size, other.size);
        for (int i = size; i < elements.length; i++) {
            elements[i].index = i;
        }
        
        H_BinaryHeap<E> merged = buildHeap(elements, comparator);
        this.heap = merged.heap;
        this.size = merged.size;
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        if (!(key instanceof Element)) {
            throw new IllegalArgumentException("Invalid key");
        }
        
        Element<E> node = (Element<E>) key;
        if (node.value == null) {
            throw new IllegalStateException("Element is no longer in the heap");
        }
        if (compare(heap[node.index].value, value) < 0) {
            throw new IllegalArgumentException("New value is larger than previous value");
        }

        heap[node.index].value = value;
        percolateUp(node.index);

        return node;
    }
    
    private static <T> H_BinaryHeap<T> buildHeap(Element<T>[] elements, Comparator<? super T> cmptr) {
        H_BinaryHeap<T> H = new H_BinaryHeap<>();
        H.heap = new Element[elements.length];
        H.size = elements.length;
        H.comparator = cmptr;
        System.arraycopy(elements, 0, H.heap, 0, elements.length);
        for (int i = H.size / 2 - 1; i >= 0; i--) {
            H.percolateDown(i);
        }
        return H;
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public int size() {
        return this.size;
    }

    private void percolateUp(int index) {
        E element = heap[index].value;
        int child = index; 
        while (child > 0) {
            int parent = (child - 1) / 2; 
            if (compare(element, heap[parent].value) >= 0) break;
            swap(child, parent);
            child = parent;
        }
    }
    
    private void percolateDown(int index) {
        E element = heap[index].value;
        int parent = index;
        while (parent < size) {
            int child = parent * 2 + 1;
            if (child < size-1 && compare(heap[child].value, heap[child + 1].value) > 0) {
                child++;
            }       
            if (child >= size || compare(element, heap[child].value) <= 0) {
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
        Element<E>[] doubleHeap = new Element[doubleCapacity];
        System.arraycopy(heap, 0, doubleHeap, 0, size);
        this.heap = doubleHeap;
    }
    
    private void shrink() {
        if (heap.length <= DEFAULT_SIZE) return;
        int halfCapacity = heap.length >> 1;
        Element<E>[] halfHeap = new Element[halfCapacity];
        System.arraycopy(heap, 0, halfHeap, 0, size);
        this.heap = halfHeap;
    }
    
    private void swap(int i, int j) {
        Element<E> temp = heap[i];
        heap[i] = heap[j];
        heap[i].index = i;
        heap[j] = temp;
        heap[j].index = j;
    }
    
    public String toString() {
        return Arrays.toString(heap);
    }
    
    public static class Element<E> implements ElementKey<E> {

        private int index;
        private E value;
        
        public Element(E value, int index) {
            this.value = value;
            this.index = index;
        }
        
        @Override
        public E getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value.toString();
        }
        
        private void invalidateKey() {
            this.value = null;
        }
        
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_PriorityQueue<Integer> H = new H_BinaryHeap<>((a,b) -> b - a);
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            H.offer(r.nextInt(100));
        }
        
        while (!H.isEmpty()) {
            System.out.print(H.poll() + ", ");
        }
        System.out.println();
        
        H_BinaryHeap<Integer> A = new H_BinaryHeap<>();
        A.insert(10);
        A.insert(30);
        A.insert(20);
        A.insert(40);
        
        H_BinaryHeap<Integer> B = new H_BinaryHeap<>();
        B.insert(80);
        B.insert(25);
        B.insert(35);
        B.insert(15);
        
        ElementKey<Integer> key = A.insert(70);
        
        System.out.println(A);
        System.out.println(B);
        A.merge(B);
        
        System.out.println(A);
        A.decreaseKey(key, 12);
        System.out.println(key);
        
        while (!A.isEmpty()) {
            System.out.print(A.poll() + ", ");
        }
        System.out.println();
    }

}
