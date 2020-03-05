
package structures;

import structures.interfaces.H_Tree;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class H_AVLTree<E> extends H_BinarySearchTree<E> {
    
    public H_AVLTree() {
        super();
    }
    
    public H_AVLTree(Comparator<? super E> cmptr) {
        super(cmptr);
    }
    
    @Override
    protected TreeNode<E> createNode(E element) {
        return new AVLNode<>(element);
    }
    
    @Override
    public boolean add(E element) {
        int prevSize = size;
        root = addRec(root, element);
        return prevSize != size;
    }
    
    @Override
    protected TreeNode<E> addRec(TreeNode<E> t, E element) {
        t = super.addRec(t, element);
        return checkBalance(t);
    }
    
    @Override
    protected TreeNode<E> removeRec(TreeNode<E> t, E element) {
        t = super.removeRec(t, element);
        return checkBalance(t);
    }
    
    @Override
    protected TreeNode<E> replaceLeftmost(TreeNode<E> t, TreeNode<E> target) {
        t = super.replaceLeftmost(t, target);
        return checkBalance(t);
    }
    
    private TreeNode<E> checkBalance(TreeNode<E> t) {
        if (t == null) return null;
        refreshHeight((AVLNode<E>) t);
        int balanceFactor = balanceFactor(t);
        if (balanceFactor > 1) {
            if (balanceFactor(t.right) < 0) 
                t.right = rotateRight(t.right);
            t = rotateLeft(t);
        } else if (balanceFactor < -1) {
            if (balanceFactor(t.left) > 0)
                t.left = rotateLeft(t.left);
            t = rotateRight(t);
        } 
        return t;
    }
    
    private int refreshHeight(AVLNode<E> t) {
        if (t == null) return 0;
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t.height;
    }
    
    private int balanceFactor(TreeNode<E> t) {
        if (t == null) return 0;
        return height(t.right) - height(t.left);
    }
    
    private TreeNode<E> rotateRight(TreeNode<E> t) {
        TreeNode<E> r = t.left;
        t.left = r.right;
        r.right = t;
        refreshHeight((AVLNode<E>) t);
        refreshHeight((AVLNode<E>) r);
        return r;
    }
    
    private TreeNode<E> rotateLeft(TreeNode<E> t) {
        TreeNode<E> r = t.right;
        t.right = r.left;
        r.left = t;
        refreshHeight((AVLNode<E>) t);
        refreshHeight((AVLNode<E>) r);
        return r;
    }
    
    private int height(TreeNode<E> t) {
        if (t == null) return 0;
        return ((AVLNode<E>) t).height;
    }
    
    protected static class AVLNode<E> extends TreeNode<E> {
        protected int height;
        
        public AVLNode(E value) {
            super(value);
            height = 1;
        }
    }
   
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_Tree<Integer> avl = new H_AVLTree<>();
        H_Tree<Integer> bst = new H_BinarySearchTree<>();
        Random r = new Random();
        
        avl.add(50);
        bst.add(50);
        for (int i = 0; i < 30; i++) {
            int next = r.nextInt(100);
            avl.add(next);
            bst.add(next);
        }
        
        
        System.out.println(avl);
        System.out.println(bst);
        
        avl.remove(7);
        bst.remove(7);
        System.out.println(avl);
        System.out.println(bst);
        System.out.println(avl.contains(7));
        System.out.println(bst.contains(7));
        
        for (Iterator<Integer> iter = avl.inorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        for (Iterator<Integer> iter = bst.inorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        H_Tree<Integer> t = new H_AVLTree<>();
        for (int i = 1; i <= 5; i++) {
            t.add(i);
        }
        System.out.println(t);
        
        for (Iterator<Integer> iter = t.inorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        for (Iterator<Integer> iter = t.preorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        for (Iterator<Integer> iter = t.postorderIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
        for (Iterator<Integer> iter = t.descendingIterator(); iter.hasNext();) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        
    }
}
