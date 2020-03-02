
package structures;

import interfaces.H_List;
import interfaces.H_Queue;
import interfaces.H_Stack;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class H_BinarySearchTree<E> implements H_Tree<E> {
    
    protected Comparator<? super E> comparator;
    protected TreeNode<E> root;
    protected int size;
    
    public H_BinarySearchTree() {
        this(null);
    }
    
    public H_BinarySearchTree(Comparator<? super E> cmptr) {
        this.comparator = cmptr;
        this.root = null;
        this.size = 0;
    }
    
    public boolean add(E element) {
        int prevSize = size;
        root = addRec(root, element);
        return prevSize != size;
    }
    
    protected TreeNode<E> addRec(TreeNode<E> t, E element) {
        if (t == null) {
            size++;
            return createNode(element);
        }
        if (compare(t.value, element) >= 0)
            t.left = addRec(t.left, element);
        else
            t.right = addRec(t.right, element);
        return t;
    }

    @Override
    public boolean remove(Object object) {
        if (root == null) return false;
        int prevSize = size;
        root = removeRec(root, (E) object);
        return (prevSize != size);
    }
    
    protected TreeNode<E> removeRec(TreeNode<E> t, E e) { 
        if (t == null) return null;
        if (t.value.equals(e)) {
            size--;
            if (t.left == null) return (t.right == null) ? null : t.right;
            if (t.right == null) return (t.left == null) ? null : t.left;
            t.right = replaceLeftmost(t.right, t);
            return t;
        }
        if (compare(t.value, e) >= 0) 
            t.left = removeRec(t.left, e);
        else 
            t.right = removeRec(t.right, e);
        return t;
    }

    @Override
    public boolean contains(Object object) {
        if (object == null) throw new NullPointerException();
        E element = (E) object;
        TreeNode<E> curr = root;
        while (curr != null) {
            if (curr.value.equals(element)) 
                return true;
            if (compare(curr.value, element) >= 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InorderIterator();
    }
    
    @Override
    public Iterator<E> inorderIterator() {
        return new InorderIterator();
    }
    
    @Override
    public Iterator<E> preorderIterator() {
        return new PreorderIterator();
    }
    
    @Override
    public Iterator<E> postorderIterator() {
        return new PostorderIterator();
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }
    
    protected int compare(E elem1, E elem2) {
        if (elem1 == null || elem2 == null)
            throw new NullPointerException();
        if (comparator == null) {
            Comparable<? super E> cmp = (Comparable<? super E>) elem1;
            return cmp.compareTo(elem2);
        } else {
            return comparator.compare(elem1, elem2);
        }
    }
    
    protected TreeNode<E> createNode(E elem) {
        return new TreeNode<>(elem);
    }
    
    public String toStringList() {
        H_List<E> list = new H_ArrayList<>();
        for (E element : this) {
            list.add(element);
        }
        return list.toString();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTreeRec(sb, root, "", "");
        return sb.toString();
    }
    
    private void printTreeRec(StringBuilder sb, TreeNode<E> t, String prefix, String child) {
        if (t == null) return;
        sb.append(prefix).append(t.toString()).append('\n');
        if (t.left != null || t.right != null) {
            printTreeRec(sb, t.right, child + (t.left != null ? "├── " : "└── "), child + (t.left != null ? "│   " : "    "));
            if (t.left != null) printTreeRec(sb, t.left, child + "└── ", child + "    ");
        }
    }
    
    protected TreeNode<E> replaceLeftmost(TreeNode<E> t, TreeNode<E> target) {
        if (t == null) return null;
        if (t.left == null) {
            target.value = t.value;
            return t.right;
        }
        t.left = replaceLeftmost(t.left, target);
        return t;
    }

    protected static class TreeNode<E> {        
        
        protected TreeNode<E> left;
        protected TreeNode<E> right;
        protected E value;
        
        public TreeNode(E value) {
            this.value = value;
        }
        
        public String toString() {
            return value.toString();
        }
        
    }
    
    public class DescendingIterator implements Iterator<E> {
        private final H_Queue<TreeNode<E>> queue;
        
        public DescendingIterator() {
            this.queue = new H_LinkedList<>();
            if (root != null) {
                queue.offer(root);
            } 
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public E next() {
            TreeNode<E> t = queue.poll();
            if (t.left != null) queue.offer(t.left);
            if (t.right != null) queue.offer(t.right);
            return t.value;
        }
    }
    
    public class InorderIterator implements Iterator<E> {
        private final H_Stack<TreeNode<E>> stack;
        
        public InorderIterator() {
            this.stack = new H_LinkedList<>();
            moveStack(root);
        }
        
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            TreeNode<E> next = stack.pop();
            moveStack(next.right);
            return next.value;
        }
        
        private void moveStack(TreeNode<E> t) {
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }
    } 
    
    public class PreorderIterator implements Iterator<E> {
        private final H_Stack<TreeNode<E>> stack;
        
        public PreorderIterator() {
            this.stack = new H_LinkedList<>();
            stack.push(root);
        }
        
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            TreeNode<E> t = stack.pop();
            if (t.right != null) stack.push(t.right);
            if (t.left != null) stack.push(t.left);
            return t.value;
        }
    } 

    public class PostorderIterator implements Iterator<E> {
        private final H_List list;
        private final Iterator<E> itr;
        
        public PostorderIterator() {
            this.list = new H_ArrayList<>();
            postorderTraversal(root);
            itr = list.iterator();
        }
        
        public boolean hasNext() {
            return itr.hasNext();
        }

        public E next() {
            return itr.next();
        }
        
        private void postorderTraversal(TreeNode<E> t) {
            if (t == null) return;
            postorderTraversal(t.left);
            postorderTraversal(t.right);
            list.add(t.value);
        }
    } 
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] args) {
        H_Tree<Integer> t = new H_BinarySearchTree<>();
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
