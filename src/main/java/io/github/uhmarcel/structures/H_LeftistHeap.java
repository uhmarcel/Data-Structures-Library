package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Heap;
import java.util.Comparator;
import java.util.Random;


public class H_LeftistHeap<E> implements H_Heap<E> {

    // find-min:        O(1)
    // delete-min:      O(logn)
    // insert:          O(logn)
    // decrease-key:    O(logn)
    // merge:           O(logn)

    private Comparator<? super E> comparator;
    private LeftistTree<E> root;
    private int size;
    
    public H_LeftistHeap() {}
    public H_LeftistHeap(Comparator<? super E> cmptr) {
        this.comparator = cmptr;
    } 

    @Override
    public ElementKey<E> insert(E element) {
        if (element == null) throw new NullPointerException();
        LeftistTree<E> node = new LeftistTree<>(element);
        root = LeftistTree.merge(root, node, comparator);
        size = size + 1;
        return node;
    }

    @Override
    public E deleteMin() {
        if (root == null) throw new IllegalStateException();
        E value = root.value;
        root.invalidateKey();
        root = LeftistTree.merge(root.left, root.right, comparator);
        if (root != null) root.parent = null;
        size = size - 1;
        return value;
    }

    @Override
    public E findMin() {
        if (root == null) throw new IllegalStateException();
        return root.value;
    }

    @Override
    public void merge(H_Heap<E> heap) {
        if (heap == null || !(heap instanceof H_LeftistHeap)) 
            throw new IllegalArgumentException();
        
        H_LeftistHeap<E> other = (H_LeftistHeap<E>) heap; 
        size = size + other.size;
        root = LeftistTree.merge(root, other.root, comparator);
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        if (key == null || value == null) 
            throw new NullPointerException();
        if (!(key instanceof LeftistTree)) 
            throw new IllegalArgumentException();
        
        LeftistTree<E> node = (LeftistTree<E>) key;
        
        if (node.value == null || LeftistTree.compare(node.value, value, comparator) < 0) 
            throw new IllegalArgumentException();
        
        node.value = value;
        
        if (node.parent != null && LeftistTree.compare(node.parent.value, node.value, comparator) > 0) {
            if (node.parent.left == node) 
                node.parent.left = null;
            else 
                node.parent.right = null;
            
            // Update all distances upwards - O(logn)
            LeftistTree<E> curr = node.parent;
            while (curr != null) {
                int prevDistance = curr.distance;
                curr.distance = Math.min(LeftistTree.dist(curr.left), LeftistTree.dist(curr.right)) + 1;
                if (prevDistance == curr.distance) break;
            }
            root = LeftistTree.merge(root, node, comparator);    
        }
        return node;
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
        StringBuilder sb = new StringBuilder(50);
        print(root, sb, "", "");
        return sb.toString();
    }

    private void print(LeftistTree<E> t, StringBuilder sb, String c, String cp) {
        if (t == null) return;
        sb.append(c).append(t.toString()).append('\n');
        if (t.left != null || t.right != null) {
            print(t.right, sb, cp + (t.left != null ? "├── " : "└── "), cp + (t.left != null ? "│   " : "    "));
            if (t.left != null) print(t.left, sb, cp + "└── ", cp + "    ");
        }
    }
    
    public static class LeftistTree<E> implements ElementKey<E> {
        
        private E value;
        private LeftistTree<E> left;
        private LeftistTree<E> right;
        private LeftistTree<E> parent; 
        private int distance;
        
        // Added parent pointer to support efficient decrease-key operation
        // at the cost of an additional constant time and space factor in the
        // heap.
        
        public LeftistTree(E element) {
            this.value = element;
        }

        @Override
        public E getValue() {
            return value;
        }
        
        private void invalidateKey() {
            this.value = null;
        }
        
        private void swapChildren() {
            LeftistTree<E> temp = left;
            left = right;
            right = temp;
        }
        
        private static <T> LeftistTree<T> merge(LeftistTree<T> A, LeftistTree<T> B, Comparator<? super T> cmptr) {
            if (A == null) return B;
            if (B == null) return A;
            
            LeftistTree<T> root;
            if (compare(A.value, B.value, cmptr) < 0) {
                root = A;
                root.right = merge(A.right, B, cmptr);
            } else {
                root = B;
                root.right = merge(A, B.right, cmptr);
            }
            root.right.parent = root;
            
            if (dist(root.left) < dist(root.right)) 
                root.swapChildren();
            
            root.distance = Math.min(dist(root.left), dist(root.right)) + 1;
            return root;
        }
        
        private static <T> int dist(LeftistTree<T> node) {
            if (node == null) return -1;
            return node.distance;
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
        
        @Override
        public String toString() {
            // The commented alernative below allows to see each node's distance 
            // and parent on the tree diagram for debugging purposes.
            return value.toString();
            // return "(" + value.toString() + ", " + distance + ")" + " [parent: " + (parent != null ? parent.value : "null") + "]";
        }
        
    }
    
        
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Heap<Integer> H = new H_LeftistHeap<>();
        Random r = new Random();
        for (int i = 0; i < 15; i++) {
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
