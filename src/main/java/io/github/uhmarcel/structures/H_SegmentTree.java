package io.github.uhmarcel.structures;

import java.util.function.BiFunction;


public class H_SegmentTree<E> {

    private final BiFunction<E,E,E> function;
    private final E[] tree;
    private final int size;
    
    public H_SegmentTree(E[] array, BiFunction<E,E,E> function) {
        this.tree = (E[]) new Object[array.length << 1];
        this.size = array.length;
        this.function = safeFunction(function);
        
        for (int i = 0; i < size; i++) {
            update(i, array[i]);
        }
    }
    
    public void update(int i, E value) {
        if (i < 0 || i >= size) throw new IllegalArgumentException();
        tree[i + size] = value;
        for (int j = i + size; j > 0; j = j >> 1) {
            j -= j & 1; // If odd decrease by one
            tree[j >> 1] = function.apply(tree[j], tree[j + 1]);
        }
    }
    
    public E getRange(int i, int j) {
        int left = size + i, right = size + j + 1;
        E val = null;
        
        while (left < right) {
            if (left % 2 == 1)
                val = function.apply(val, tree[left++]);
            if (right % 2 == 1)
                val = function.apply(val, tree[--right]);
            left = left / 2;
            right = right / 2;
        }
        return val;
    }
    
    private BiFunction<E,E,E> safeFunction(BiFunction<E,E,E> f) {
        return (a, b) -> {
            if (a == null) return b;
            if (b == null) return a;
            return f.apply(a, b);
        };
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_SegmentTree<Integer> t = new H_SegmentTree<>(new Integer[]{1,1,1,1,1,1,1,1}, (a, b) -> Math.max(a, b));
        
        System.out.println(t.getRange(0, 1));
        System.out.println(t.getRange(2, 2));
        System.out.println(t.getRange(0, 2));
        System.out.println(t.getRange(2, 4));
        System.out.println(t.getRange(3, 5));
        System.out.println(t.getRange(5, 7));
        System.out.println(t.getRange(0, 6));
        System.out.println(t.getRange(0, 7));
        System.out.println();
        
        t.update(4,5);
        
        System.out.println(t.getRange(0, 4));
        System.out.println(t.getRange(2, 4));
        System.out.println(t.getRange(3, 5));
        System.out.println(t.getRange(5, 7));
        System.out.println(t.getRange(0, 6));
        System.out.println(t.getRange(0, 7));
        
        H_SegmentTree<String> s = new H_SegmentTree<>(
                new String[] {"I", "am", "a", "test", "of", "this", "stuff", "right", "here"},
                (a, b) -> a + " " + b);
        
        System.out.println(s.getRange(0, 1));
        System.out.println(s.getRange(2, 2));
        System.out.println(s.getRange(0, 4));
        System.out.println(s.getRange(2, 4));
        System.out.println(s.getRange(3, 5));
        System.out.println(s.getRange(5, 7));
        System.out.println(s.getRange(1, 6));
        System.out.println(s.getRange(0, 7));
        System.out.println(s.getRange(0, 8));
    }
}


