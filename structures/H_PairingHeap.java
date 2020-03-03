
package structures;

import interfaces.H_Heap;
import interfaces.H_List;
import java.util.Comparator;
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
    public void decreaseKey(E key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void merge(H_Heap<E> heap) {
        if (heap == null || !(heap instanceof H_PairingHeap))
            throw new IllegalArgumentException();
        
        H_PairingHeap<E> toMerge = (H_PairingHeap<E>) heap; // what if diff cmptr?
        size = size + toMerge.size;
        root = mergeTrees(root, toMerge.root);
    }

    @Override
    public boolean offer(E element) {
        if (element == null) throw new NullPointerException();
        H_PairingHeap<E> toAdd = new H_PairingHeap<>();
        toAdd.root = new PairingTree<>(element);
        toAdd.size = 1;
        this.merge(toAdd);
        return true;
    }

    @Override
    public E poll() {
        if (root == null) throw new IllegalStateException();
        E minimum = root.value;
        root = mergePairs(root.childs);
        size--;
        return minimum;
    }

    @Override
    public E peek() {
        if (root == null) throw new IllegalStateException();
        return root.value;
    }
    
    private void setHeap(H_PairingHeap<E> heap) {
        this.root = heap.root;
        this.size = heap.size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    private PairingTree<E> mergeTrees(PairingTree<E> A, PairingTree<E> B) {
        if (A == null) return B;
        if (B == null) return A;
        
        if (compare(A.value, B.value) < 0) {
            A.childs.add(B);
            return A;
        } else {
            B.childs.add(A);
            return B;
        }
    }
    
    private PairingTree<E> mergePairs(H_List<PairingTree<E>> trees) {
        if (trees.size() == 0)
            return null;
        if (trees.size() == 1)
            return trees.get(0);
        return mergeTrees(mergeTrees(trees.remove(0), trees.remove(0)), mergePairs(trees)); 
    }
    
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(root, buffer, "", "");
        return buffer.toString();
    }

    private void print(PairingTree<E> t, StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix).append(t.value.toString()).append('\n');
        for (int i = 0; i < t.childs.size(); i++) {
            if (i == t.childs.size() - 1) {
                print(t.childs.get(i), buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            } else { 
                print(t.childs.get(i), buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }
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
    
    private static class PairingTree<E> {
        private E value;
        private H_List<PairingTree<E>> childs;
        
        public PairingTree(E value) {
            this.value = value;
            this.childs = new H_LinkedList<>();
        }
    }
    
        
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Heap<Integer> H = new H_PairingHeap<>();
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            H.offer(r.nextInt(100));
            System.out.println(H.size());
            System.out.println(H);
        }
        
        while (!H.isEmpty()) {
            System.out.println("Removing -> " + H.poll() + " (" + H.size() + ")");
        }
    }

}
