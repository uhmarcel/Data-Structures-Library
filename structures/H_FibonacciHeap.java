
package structures;

import java.util.Comparator;
import java.util.Random;
import structures.interfaces.H_Heap;
import structures.interfaces.H_Map;
import structures.interfaces.H_Set;


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
        if (minimum == null) throw new IllegalStateException();
        E minValue = minimum.value;
        minimum.invalidateKey();
        size = size - 1;
        
        if (size == 0) {
            rootList = minimum = null;
            return minValue;
        }
        
        if (minimum == rootList) {
            if (rootList.next != minimum) {
                rootList = rootList.next;
            } else {
                rootList = minimum.child;
                rootList.parent = null;
            }
        }
        
        if (rootList != minimum.child) 
            rootList.link(minimum.child);
        minimum.unlink();
        
        combinePairs();
        return minValue;
    }

    @Override
    public E findMin() {
        if (minimum == null) throw new IllegalStateException();
        return minimum.value;
    }

    @Override
    public void merge(H_Heap<E> heap) {
        if (heap == null || !(heap instanceof H_FibonacciHeap)) 
            throw new IllegalArgumentException();
        
        H_FibonacciHeap<E> other = (H_FibonacciHeap<E>) heap; 
        if (other.rootList == null) return;
        if (rootList == null) { 
            rootList = other.rootList;
            minimum = other.minimum;
        } else {
            rootList.link(other.rootList);
            if (compare(minimum.value, other.minimum.value) > 0) {
                minimum = other.minimum;
            }
        }
        size = size + other.size;
    }
    
    private void tempPrintList(FibTree<E> t) {
        FibTree<E> curr = t;
        do {
            System.out.print(t.value + ", ");
            t = t.next;
        } while (curr != t);
        System.out.println();
        
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        if (key == null || value == null) 
            throw new NullPointerException();
        if (!(key instanceof FibTree)) 
            throw new IllegalArgumentException();
        
        FibTree<E> node = (FibTree<E>) key;
        
        if (node.value == null || compare(node.value, value) < 0) 
            throw new IllegalArgumentException();
        node.value = value;
        
        if (node.parent != null && compare(node.value, node.parent.value) < 0) {
            FibTree<E> parent = node.parent;
            if (node.next != node) 
                parent.child = node.next;
            else 
                parent.child = null;
            node.unlink();
            rootList.link(node);
            node.parent = null;
            node.marked = false;
            
            if (parent.marked == true) {
                node = parent;
                // ... continue later
            }
        }
        return node;
    }
    
    private void combinePairs() {
        if (rootList == null) return;
        // Get log2(size) for array length, as java array initialization is O(n)
        int n = (int) Math.ceil(Math.log(size) / Math.log(2)) + 1; 
        FibTree<E>[] array = new FibTree[n]; 
        FibTree<E> current = rootList;
        minimum = current;
        
        while (current.parent == null && current != array[current.degree]) {
            FibTree<E> next = current.next;
            next.parent = null;
            while (array[current.degree] != null) {
                current = joinPair(current, array[current.degree]);
                array[current.degree - 1] = null;
            }
            
            if (compare(minimum.value, current.value) > 0) {
                minimum = current;
            }
            
            array[current.degree] = current;
            current.marked = false;
            rootList = current;            
            current = next;
        }
    }
    
    private FibTree<E> joinPair(FibTree<E> A, FibTree<E> B) {
        if (compare(A.value, B.value) < 0) {
            A.addChild(B);
            return A;
        } else {
            B.addChild(A);
            return B;
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
    
    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public String toString() {
        FibTree<E> wrapper = new FibTree(null);
        wrapper.child = rootList;
        StringBuilder sb = new StringBuilder();
        print(wrapper, sb, "", "");
        return sb.toString();
    }
    
    public void printTree(FibTree<E> t) {
        StringBuilder sb = new StringBuilder();
        print(t, sb, "", "");
        System.out.println(sb.toString());
    }
    
    private void print(FibTree<E> t, StringBuilder sb, String prefix, String childPrefix) {
        if (t == null) return;
        sb.append(prefix).append((t.value == null) ? "" : t).append('\n');
        FibTree<E> head = t.child;
        FibTree<E> curr = head;
        if (head == null) return;
        do {
            if (curr.next != head)
                print(curr, sb, childPrefix + "├── ", childPrefix + "│   ");
            else 
                print(curr, sb, childPrefix + "└── ", childPrefix + "    ");
            curr = curr.next;
        } while (curr != head); 
    }
    
    public static class FibTree<E> implements ElementKey<E> {

        private FibTree<E> parent;
        private FibTree<E> child;
        private FibTree<E> next;
        private FibTree<E> prev;
        private boolean marked;
        public int degree;
        private E value;
        
        public FibTree(E value) {
            this.value = value;
            this.next = this;
            this.prev = this;
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
        
        public void addChild(FibTree<E> node) {
            if (node == null) return;
            node.unlink();
            if (child == null)
                child = node;
            else
                child.link(node);
            node.parent = this;
            degree++;
        }
        
        @Override
        public E getValue() {
            return value;
        }
        
        public void invalidateKey() {
            value = null;
        }
        
        @Override
        public String toString() {
            return "(" + value + ", "+ degree + ")\t\t\t (parent: " + p(parent) + ", next: " + p(next) + ", prev: " + p(prev) + ", child: " + p(child) +")";
        }
        
        private static String p(FibTree t) {
            return (t == null) ? "null" : ((t.value == null) ? "WTF" : t.value.toString());
        }
        
        
    }
    
                
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        for (int j = 0; j < 1000; j++) {
        H_FibonacciHeap<Integer> H = new H_FibonacciHeap<>();
        Random r = new Random();
//        System.out.println("insert 50");
        ElementKey<Integer> key = H.insert(50);
        for (int i = 0; i < 100; i++) {
            int x = r.nextInt(100);
//            System.out.println(H.size() + " -> " + x);
            H.offer(x);
//            System.out.println(H);
        }
        
//        H.combinePairs();
        H.poll();
//        System.out.println(H);
        
        
//        FibTree<Integer> t = H.rootList;
//        for (int i = 0; i < 12; i++) {
//            System.out.print(t.value + ", ");
//            t = t.prev;
//        }
        
//        System.out.println(H);
//        System.out.println("decrease key");
//        H.decreaseKey(key, 5);
//        System.out.println(H);
//        
        while (!H.isEmpty()) {
            H_Set<Integer> degrees = new H_HashSet<>();
            FibTree<Integer> curr = H.rootList;
            do {
                if (degrees.contains(curr.degree))
                    throw new IllegalStateException("Repeated degree");
                degrees.add(curr.degree);
                curr = curr.next;
            } while (curr != H.rootList);
            Integer x = H.poll();
            if (x == null) throw new NullPointerException();
//            System.out.println("Removing -> " + x + " (" + H.size() + ")");

        }
        }
    }

}
