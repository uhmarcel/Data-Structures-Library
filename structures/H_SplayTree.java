
package structures;

import interfaces.H_Tree;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class H_SplayTree<E> extends H_BinarySearchTree<E> {
    
    public H_SplayTree() {
        super();
    }
    
    public H_SplayTree(Comparator<? super E> cmptr) {
        super(cmptr);
    }
    
    public boolean add(E element) {
        if (!super.add(element)) return false;
        root = splay(root, element);
        return true;
    }
    
    public boolean remove(Object obj) {
        if (!super.remove(obj)) return false;
        root = splay(root, (E) obj);
        return true;
    }
    
    public boolean contains(Object obj) {
        if (!super.contains(obj)) return false;
        root = splay(root, (E) obj);
        return true;
    }
    
    private TreeNode<E> splay(TreeNode<E> t, E key) {
        if (t == null || t.value == key) return t;
        
        if (compare(t.value, key) > 0) {
            if (t.left == null) return t;
            if (compare(t.left.value, key) >= 0) { // target at left-left
                t.left.left = splay(t.left.left, key);
                t = rotateRight(t);
                t = rotateRight(t);
            } else { // target at right-right
                t.left.right = splay(t.left.right, key);
                t.left = rotateLeft(t.left);
                t = rotateRight(t);
            }
            
        } else {
            if (t.right == null) return t;
            if (compare(t.right.value, key) < 0) { // target at right-right
                t.right.right = splay(t.right.right, key);
                t = rotateLeft(t);
                t = rotateLeft(t);
            } else { // target at right-left
                t.right.left = splay(t.right.left, key);
                t.right = rotateRight(t.right);
                t = rotateLeft(t);
            }
        } 
        return t;
    } 
    
    private TreeNode<E> rotateRight(TreeNode<E> t) {
        if (t.left == null) return t;
        TreeNode<E> r = t.left;
        t.left = r.right;
        r.right = t;
        return r;
    }
    
    private TreeNode<E> rotateLeft(TreeNode<E> t) {
        if (t.right == null) return t;
        TreeNode<E> r = t.right;
        t.right = r.left;
        r.left = t;
        return r;
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_Tree<Integer> t = new H_SplayTree<>();
        Random r = new Random();
        
        t.add(50);
        for (int i = 0; i < 30; i++) {
            int n = r.nextInt(100);
            t.add(n);
            System.out.println("Add " + n);
            System.out.println(t);
        }
        
        t.remove(7);
        System.out.println(t);
        System.out.println(t.contains(7));
        
        for (Iterator<Integer> iter = t.inorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        for (Iterator<Integer> iter = t.descendingIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
    }

}
