package io.github.uhmarcel.structures;

import java.util.Comparator;
import java.util.Random;
import io.github.uhmarcel.H_Heap;


public class H_BinomialHeap<E> implements H_Heap<E> {
    
    // find-min:        O(1)
    // delete-min:      O(logn)
    // insert:          O(1)
    // decrease-key:    O(logn)
    // merge:           O(logn)
    
    private Comparator<? super E> comparator;
    private BinomialTree<E> rootNodes;
    private BinomialTree<E> minRoot;
    private int size;
    
    public H_BinomialHeap() {}
    public H_BinomialHeap(Comparator<? super E> cmptr) {
        this.comparator = cmptr;
    } 
    
    @Override
    public ElementKey<E> insert(E element) {
        if (element == null) throw new NullPointerException();
        BinomialTree<E> node = new BinomialTree<>(element);
        size = size + 1;
        merge(node);
        return node;
    }
    
    @Override
    public E deleteMin() {
        if (minRoot == null) throw new IllegalStateException();
        E minimum = minRoot.value;
        
        if (minRoot == rootNodes) // To avoid unlinking the root
            rootNodes = rootNodes.sibling;
        minRoot.unlink();
        
        size = size - 1;
        merge(minRoot.child);
        return minimum;
    }

    @Override
    public E findMin() {
        if (rootNodes == null) throw new IllegalStateException();
        return minRoot.value;
    }

    @Override
    public void merge(H_Heap<E> heap) {
        if (heap == null || !(heap instanceof H_PairingHeap)) 
            throw new IllegalArgumentException();
        
        H_BinomialHeap<E> other = (H_BinomialHeap<E>) heap; 
        size = size + other.size;
        merge(other.rootNodes);
    }
    
    private void merge(BinomialTree<E> node) {
        rootNodes = BinomialTree.concatenateTrees(rootNodes, node);
        rootNodes = BinomialTree.combinePairs(rootNodes, comparator);
        minRoot = BinomialTree.findMinimum(rootNodes, comparator);
    }

    @Override
    public ElementKey<E> decreaseKey(ElementKey<E> key, E value) {
        if (key == null || value == null) 
            throw new NullPointerException();
        if (!(key instanceof BinomialTree)) 
            throw new IllegalArgumentException();
        
        BinomialTree<E> node = (BinomialTree<E>) key;
        
        if (node.value == null || BinomialTree.compare(node.value, value, comparator) < 0) 
            throw new IllegalArgumentException();
        node.value = value;
        
        BinomialTree<E> parent = node.getParent();
        while (parent != null && BinomialTree.compare(parent.value, value, comparator) > 0) {
            BinomialTree.swapReferences(parent, node);
            if (parent == rootNodes) // rootNodes might have been swapped
                rootNodes = node;    // fix by changing the pointer to keep first.
            parent = node.getParent();
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
        BinomialTree<E> wrapper = new BinomialTree();
        wrapper.child = rootNodes;
        StringBuilder sb = new StringBuilder();
        print(wrapper, sb, "", "");
        return sb.toString();
    }
    
    private void print(BinomialTree<E> t, StringBuilder sb, String prefix, String childPrefix) {
        if (t == null) return;
        sb.append(prefix).append((t.value == null) ? "" : t).append('\n');
        BinomialTree<E> curr = t.child;
        while (curr != null) {
            if (curr.sibling != null)
                print(curr, sb, childPrefix + "├── ", childPrefix + "│   ");
            else 
                print(curr, sb, childPrefix + "└── ", childPrefix + "    ");
            curr = curr.sibling;
        }
    }

    public static class BinomialTree<E> implements ElementKey<E> {

        private BinomialTree<E> previous;  
        private BinomialTree<E> sibling; 
        private BinomialTree<E> child;
        private int degree;
        private E value;
        
        public BinomialTree() {}
        public BinomialTree(E value) {
            this.value = value;
        }
        
        @Override
        public E getValue() {
            return value;
        }
        
        public void invalidateKey() {
            value = null;
        }
        
        public static <T> BinomialTree<T> concatenateTrees(BinomialTree<T> A, BinomialTree<T> B) {
            if (A == null) return B;
            if (B == null) return A;
            
            BinomialTree<T> head = new BinomialTree<>(); 
            BinomialTree<T> curr = head;
            
            while (A != null && B != null) {
                if (A.degree < B.degree) {
                    curr.sibling = A;
                    A = A.sibling;
                } else {
                    curr.sibling = B;
                    B = B.sibling;
                }
                curr = curr.sibling;
            }
            curr.sibling = (A != null) ? A : B;
            return head.sibling;
        }
        
        public static <T> BinomialTree<T> combinePairs(BinomialTree<T> node, Comparator<? super T> cmptr) {
            if (node == null) return null;
            
            BinomialTree<T> head = new BinomialTree<>();
            BinomialTree<T> curr = head;
            BinomialTree<T> last = node;
            
            node = node.sibling;
            last.unlink();
            
            while (node != null) {
                if (last.degree != node.degree || (node.sibling != null && last.degree == node.sibling.degree)) {
                    curr.sibling = last;
                    last.previous = curr;
                    last = node;
                    node = node.sibling;
                    last.unlink();
                    curr = curr.sibling;
                    continue;
                }               
                
                BinomialTree<T> t = node;
                node = node.sibling;
                t.unlink();
                
                if (compare(last.value, t.value, cmptr) < 0) {
                    last.addChild(t);
                } else {
                    t.addChild(last);
                    last = t;
                } 
            }
            
            curr.sibling = last;
            last.previous = curr;
            head.sibling.previous = null;
            return head.sibling; 
        }
        
        private static <T> BinomialTree<T> findMinimum(BinomialTree<T> node, Comparator<? super T> cmptr) {
            if (node == null) return null;
            BinomialTree<T> minRef = node;
            BinomialTree<T> curr = node;
            while (curr != null) {
                if (compare(minRef.value, curr.value, cmptr) > 0) 
                    minRef = curr;
                curr = curr.sibling;
            }
            return minRef;
        }
        
        private void addChild(BinomialTree<E> node) {
            if (node == null) return;
            node.unlink();
            if (child != null) {
                child.previous = node;
                node.sibling = child;
            }
            child = node;
            node.previous = this;
            degree++;
        }
        
        private void unlink() {
            if (previous != null) {
                if (previous.sibling == this)
                    previous.sibling = sibling;
                else 
                    previous.child = sibling;
            }
            
            if (sibling != null) {
                sibling.previous = previous;
            }
            this.previous = null;
            this.sibling = null;
        }
        
        private BinomialTree<E> getParent() {
            if (previous == null) return null;
            
            BinomialTree<E> leftmost = this;
            while (leftmost != null && leftmost.previous.sibling == leftmost) {
                leftmost = leftmost.previous;
            }
            return (leftmost == null) ? null : leftmost.previous;
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
        
        private static <T> void swapReferences(BinomialTree<T> A, BinomialTree<T> B) {
            BinomialTree<T> tempPrevious = A.previous;
            BinomialTree<T> tempSibling = A.sibling;
            BinomialTree<T> tempChild = A.child;
            
            if (B.sibling != null) B.sibling.previous = A;
            A.sibling = B.sibling;
            if (B.child != null) B.child.previous = A;
            A.child = B.child;
            System.out.println(A + " " + A.child);
            if (B.previous != null && B.previous != A) {
                if (B.previous.sibling == B) B.previous.sibling = A;
                else B.previous.child = A;
            }
            A.previous = (B.previous == A) ? B : B.previous;
            
            if (tempSibling != null) tempSibling.previous = B;
            B.sibling = tempSibling;
            if (tempChild != null) tempChild.previous = B;
            B.child = (tempChild == B) ? A : tempChild;
            if (tempPrevious != null) {
                if (tempPrevious.sibling == A) tempPrevious.sibling = B;
                else tempPrevious.child = B;
            }
            B.previous = tempPrevious;
        }
        
        @Override
        public String toString() {
            //return value  + " (prev: " + (previous != null ? previous.value : "null") + ")";
            return value.toString();
        }
    
    }
    
            
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Heap<Integer> H = new H_BinomialHeap<>();
        Random r = new Random();
        System.out.println("insert 50");
        ElementKey<Integer> key = H.insert(50);
        for (int i = 0; i < 15; i++) {
            int x = r.nextInt(100);
            System.out.println(H.size() + " -> " + x);
            H.offer(x);
            System.out.println(H);
        }
        System.out.println(H);
        System.out.println("decrease key");
        H.decreaseKey(key, 5);
        System.out.println(H);
        
        while (!H.isEmpty()) {
            System.out.println("Removing -> " + H.poll() + " (" + H.size() + ")");
            System.out.println(H);
        }
    }
}