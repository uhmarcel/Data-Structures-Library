
package structures;

import java.util.Comparator;


public class H_RedBlackTree_TODO<E> extends H_BinarySearchTree<E> {

    public H_RedBlackTree_TODO() {
        super();
    }
    
    public H_RedBlackTree_TODO(Comparator<? super E> cmptr) {
        super(cmptr);
    }
    
    protected static class RBNode<E> extends TreeNode<E> {
        public static final boolean RED = false;
        public static final boolean BLACK = true;
        protected boolean color;
        
        public RBNode(E value, boolean color) {
            super(value);
            this.color = color;
        }
    }
}
