package io.github.uhmarcel.structures;

import java.util.Arrays;
import java.util.function.BiFunction;


public class H_BinaryIndexedTree<E> {
    
    private final BiFunction<E,E,E> function;
    private final BiFunction<E,E,E> inverse;
    private final E[] values;
    private final E[] tree;
    private final E zeroValue;
    
    public H_BinaryIndexedTree(E[] array, BiFunction<E,E,E> f, BiFunction<E,E,E> r) {
        this(array, f, r, null);
    }
    
    public H_BinaryIndexedTree(E[] array, BiFunction<E,E,E> f, BiFunction<E,E,E> r, E zeroValue) {
        this.tree = (E[]) new Object[array.length + 1];
        this.values = (E[]) new Object[array.length];
        this.function = f;
        this.inverse = r;
        this.zeroValue = zeroValue;
        
        if (zeroValue != null) {
            for (int i = 0; i < array.length; i++) {
                values[i] = zeroValue;
                tree[i+1] = zeroValue;
            }
        }
        
        
        for (int i = 0; i < array.length; i++) {
            update(i, array[i]);
        }
    }
    
    public E update(int index, E elem) {
        E delta = inverse.apply(elem, values[index]);
        for (int i = index + 1; i < tree.length; i += i & -i) {
            tree[i] = function.apply(tree[i], delta);
        }
        E prev = values[index];
        values[index] = elem;
        return prev;
    }
    
   
    public E getRange(int i, int j) {
        return inverse.apply(accumulate(j), accumulate(i - 1));
    }
    
    private E accumulate(int index) {
        E result = zeroValue;
        for (int i = index + 1; i > 0; i -= i & -i) {
            result = function.apply(result, tree[i]);
        }
        return result;
    }
       
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    private void print() {
        System.out.println(Arrays.toString(values) + " -> " + Arrays.toString(tree));
    }
    
    public static void main(String[] mains) {
        H_BinaryIndexedTree<Integer> t = new H_BinaryIndexedTree<>(
                new Integer[] {1, 1, 1, 1, 1, 1},
                (a, b) -> a + b, 
                (a, b) -> a - b,
                0);

        System.out.println(t.getRange(0, 4));
        System.out.println(t.getRange(0, 0));
        System.out.println(t.getRange(0, 3));
        System.out.println(t.getRange(2, 3));
        
        t.update(2, 10);
    
        System.out.println(t.getRange(0, 4));
        System.out.println(t.getRange(0, 0));
        System.out.println(t.getRange(0, 3));
        System.out.println(t.getRange(2, 3));
        
        H_BinaryIndexedTree<String> s = new H_BinaryIndexedTree<>(
                new String[] {"hi", "how", "are", "you", "today"},
                (a, b) -> a + b, 
                (a, b) -> a.replace(b, ""),
                "");
                
        System.out.println(s.getRange(0, 4));
        System.out.println(s.getRange(0, 0));
        System.out.println(s.getRange(0, 3));
        System.out.println(s.getRange(2, 3));
    }
        
}
