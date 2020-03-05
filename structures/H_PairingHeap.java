
package structures;

import structures.interfaces.H_Heap;
import structures.interfaces.H_PriorityQueue;
import structures.interfaces.H_Queue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class H_PairingHeap<E> implements H_Heap<E> {
    
    private Comparator<? super E> comparator;
    private PairingTree<E> root;
    private int size;
    
    public H_PairingHeap() {}
    public H_PairingHeap(Comparator<? super E> cmptr) {
        this.comparator = cmptr;
    } 

    
    @Override
    public ElementKey<E> insert(E element) {
        if (element == null) throw new NullPointerException();
         
        PairingTree<E> node = new PairingTree<>(element);
        root = PairingTree.merge(root, node, comparator);
        size = size + 1;
        return node;
    }

    @Override
    public E deleteMin() {
        if (root == null) throw new IllegalStateException();
        
        E minimum = root.value;
        root = PairingTree.mergePairs(root.leftmostChild, comparator);
        size = size - 1;
        return minimum;
    }

    @Override
    public E findMin() {
        if (root == null) throw new IllegalStateException();
        return root.value;
    }

    @Override
    public void merge(H_Heap<E> heap) {        
        if (heap == null || !(heap instanceof H_PairingHeap)) 
            throw new IllegalArgumentException();
        
        H_PairingHeap<E> other = (H_PairingHeap<E>) heap; 
        size = size + other.size;
        root = PairingTree.merge(root, other.root, comparator);
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        if (key == null || value == null) 
            throw new NullPointerException();
        if (!(key instanceof PairingTree)) 
            throw new IllegalArgumentException();
        
        PairingTree<E> node = (PairingTree<E>) key;
        if (PairingTree.compare(node.value, value, comparator) < 0) 
            throw new IllegalArgumentException();
        node.value = value;
        
        PairingTree<E> parent = node.getParent();
        if (parent != null && PairingTree.compare(parent.value, node.value, comparator) > 0) {
            node.unlink();
            root = PairingTree.merge(root, node, comparator);
        }
        return node;
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        print(root, sb, "", "");
        return sb.toString();
    }

    private void print(PairingTree<E> t, StringBuilder sb, String prefix, String childPrefix) {
        if (t == null) return;
        sb.append(prefix).append(t.value.toString()).append('\n');
        PairingTree<E> curr = t.leftmostChild;
        while (curr != null) {
            if (curr.nextSibling != null)
                print(curr, sb, childPrefix + "├── ", childPrefix + "│   ");
            else 
                print(curr, sb, childPrefix + "└── ", childPrefix + "    ");
            curr = curr.nextSibling;
        }
    }
    
    private static class PairingTree<E> implements ElementKey<E> {
        
        private E value;
        private PairingTree<E> nextSibling;
        private PairingTree<E> leftmostChild;
        private PairingTree<E> previous;
        
        public PairingTree(E value) {
            this.value = value;
        }
        
        @Override
        public E getValue() {
            return value;
        }
        
        public static <T> PairingTree<T> merge(PairingTree<T> A, PairingTree<T> B, Comparator<? super T> cmptr) {
            if (A == null) return B;
            if (B == null) return A;
            
            if (compare(A.value, B.value, cmptr) < 0) {
                B.unlink();
                A.link(B);
                return A;
            } else {
                A.unlink();
                B.link(A);
                return B;
            }
        } 
        
        public static <T> PairingTree<T> mergePairs(PairingTree<T> list, Comparator<? super T> cmptr) {
            if (list == null) return null;
            if (list.nextSibling == null) return list;
            
            PairingTree<T> firstPair = merge(list, list.nextSibling, cmptr);
            return merge(firstPair, mergePairs(firstPair.nextSibling, cmptr), cmptr);
        }
        
        private void unlink() {
            if (previous != null) {
                if (previous.nextSibling == this)
                    previous.nextSibling = nextSibling;
                else 
                    previous.leftmostChild = nextSibling;
            }
            
            if (nextSibling != null) {
                nextSibling.previous = previous;
            }
            this.previous = null;
            this.nextSibling = null;
        }
        
        private void link(PairingTree<E> node) {
            if (node == null) throw new NullPointerException();
            if (leftmostChild != null) {
               node.nextSibling = leftmostChild;
               leftmostChild.previous = node;
            }
            leftmostChild = node;
            node.previous = this;
        }

        private static <T> int compare(T elem1, T elem2, Comparator<? super T> cmptr) {
            if (elem1 == null || elem2 == null)
                throw new NullPointerException();
            if (cmptr == null) {
                Comparable<? super T> cmp = (Comparable<? super T>) elem1;
                return cmp.compareTo(elem2);
            } else {
                return cmptr.compare(elem1, elem2);
            }
        }
        
        private PairingTree<E> getParent() {
            if (previous == null) return null;
            
            PairingTree<E> leftmost = this;
            while (leftmost != null && leftmost.previous.nextSibling == leftmost) {
                leftmost = leftmost.previous;
            }
            return (leftmost == null) ? null : leftmost.previous;
        }

    }
    
        
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Heap<Integer> H = new H_PairingHeap<>();
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            int x = r.nextInt(100);
            H.offer(x);
            System.out.println(H.size() + " -> " + x);
            System.out.println(H);
        }
        System.out.println("insert 50");
        ElementKey<Integer> key = H.insert(50);
        System.out.println(H);
        System.out.println("poll");
        H.poll();
        System.out.println(H);
        System.out.println("decrease key");
        H.decreaseKey(key, 22);
        System.out.println(H);
        
        while (!H.isEmpty()) {
            System.out.println("Removing -> " + H.poll() + " (" + H.size() + ")");
            System.out.println(H);
        }
    }

}
