
package structures;

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
    public boolean add(E element) {
        int prevSize = size;
        root = addRec(root, element);
        return prevSize != size;
    }
    
    protected TreeNode<E> addRec(TreeNode<E> t, E element) {
        TreeNode<E> r = super.addRec(t, element);
        refreshHeight(r);
        return r;
    }
    
    protected TreeNode<E> removeRec(TreeNode<E> t, E element) {
        TreeNode<E> r = super.removeRec(t, element);
        refreshHeight(r);
        return r;
    }
    
    protected TreeNode<E> replaceLeftmost(TreeNode<E> t, TreeNode<E> target) {
        TreeNode<E> r = super.replaceLeftmost(t, target);
        refreshHeight(r);
        return r;
    }
    
    private void refreshHeight(TreeNode<E> t) {
        if (t == null) return;
        AVLNode<E> curr = (AVLNode<E>) t;
        int leftHeight = height(t.left);
        int rightHeight = height(t.right);
        
        curr.height = Math.max(height(t.left), height(t.right)) + 1;
        
        if (Math.abs(leftHeight - rightHeight) > 1) { 
            //balance(t);
        }
    }
    
    @Override
    protected TreeNode<E> createNode(E element) {
        return new AVLNode<>(element);
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
        };
        
        public String toString() {
            return "(" + value.toString() + "," + height + ")";
        }
    }
   
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_AVLTree<Integer> t = new H_AVLTree<>();
        Random r = new Random();
        
        t.add(50);
        for (int i = 0; i < 30; i++) {
            t.add(r.nextInt(100));
        }
        
        System.out.println(t);
        
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
