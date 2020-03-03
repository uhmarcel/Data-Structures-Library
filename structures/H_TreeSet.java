
package structures;

import interfaces.H_SortedSet;
import java.util.Comparator;
import java.util.Iterator;


public class H_TreeSet<E> extends H_AVLTree<E> implements H_SortedSet<E> {

    public Comparator<? super E> comparator() {
        return super.comparator;
    }

    @Override
    public E first() {
        if (root == null) throw new IllegalStateException();
        TreeNode<E> t = root;
        while (t.left != null)
            t = t.left;
        return t.value;
    }

    @Override
    public E last() {
        if (root == null) throw new IllegalStateException();
        TreeNode<E> t = root;
        while (t.right != null)
            t = t.right;
        return t.value;
    }

    @Override
    public H_SortedSet<E> headSet(E targetElement) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public H_SortedSet<E> tailSet(E targetElement) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
