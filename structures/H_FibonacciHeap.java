
package structures;

import java.util.Comparator;
import java.util.Random;
import structures.interfaces.H_Heap;


public class H_FibonacciHeap<E> implements H_Heap<E> {
    
    private Comparator<? super E> comparator;
    private FibTree<E> rootList;
    private FibTree<E> minimum;
    private int size;
    
    public H_FibonacciHeap() {}
    public H_FibonacciHeap(Comparator<? super E> cmptr) {
        this.comparator = cmptr;
    } 

    @Override
    public ElementKey<E> insert(E element) {
        if (element == null) throw new NullPointerException();
        FibTree<E> node = new FibTree<>(element);
        if (minimum == null || compare(node.value, minimum.value) < 0) {
            minimum = node;
        }
        node.link(rootList);
        rootList = node;
        size = size + 1;
        return node;
    }

    @Override
    public E deleteMin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E findMin() {
        if (minimum == null) throw new IllegalStateException();
        return minimum.value;
    }

    @Override
    public void merge(H_Heap<E> heap) {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public int size() {
        return this.size;
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
    
    public static class FibTree<E> implements ElementKey<E> {

        private FibTree<E> parent;
        private FibTree<E> child;
        private FibTree<E> next;
        private FibTree<E> prev;
        private boolean marked;
        private E value;
        
        public FibTree(E value) {
            this.value = value;
            this.next = this;
            this.prev = this;
        }
        
        @Override
        public E getValue() {
            return value;
        }
        
        public void link(FibTree<E> node) {
            if (node == null) return;
            FibTree<E> temp = this.next;
            node.parent = parent;
            this.next = node;
            node.prev.next = temp;
            temp.prev = node.prev;
            node.prev = this;
        }
        
        public void unlink() {
            prev.next = next;
            next.prev = prev;
            next = this;
            prev = this;
            parent = null;
        }
        
    }
    
                
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_FibonacciHeap<Integer> H = new H_FibonacciHeap<>();
        Random r = new Random();
        System.out.println("insert 50");
        ElementKey<Integer> key = H.insert(50);
        for (int i = 0; i < 4; i++) {
            int x = r.nextInt(100);
            System.out.println(H.size() + " -> " + x);
            H.offer(x);
            System.out.println(H.findMin());
//            System.out.println(H);
        }
        
        FibTree<Integer> t = H.rootList;
        for (int i = 0; i < 12; i++) {
            System.out.print(t.value + ", ");
            t = t.prev;
        }
        
//        System.out.println(H);
//        System.out.println("decrease key");
//        H.decreaseKey(key, 5);
//        System.out.println(H);
//        
//        while (!H.isEmpty()) {
//            System.out.println("Removing -> " + H.poll() + " (" + H.size() + ")");
//            System.out.println(H);
//        }
    }

}
