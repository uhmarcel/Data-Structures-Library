package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Map;
import io.github.uhmarcel.H_Set;
import io.github.uhmarcel.H_List;


public class H_Trie<E> {
    
    private final H_Map<E, H_Trie> children;
    private boolean isEnd;
    
    public H_Trie() {
        this.children = new H_HashMap<>();
        this.isEnd = false;
    }
    
    public void insert(E[] array) {
        H_Trie<E> t = this;
        for (E current : array) {
            if (!t.children.containsKey(current))
                t.children.put(current, new H_Trie<>());
            t = t.children.get(current);
        }
        t.isEnd = true;
    }
    
    public boolean remove(E[] array) {
        H_Trie<E> lastNonSingle = this;
        E toRemove = null;
        H_Trie<E> t = this;
        
        for (E current : array) {
            if (!t.children.containsKey(current)) 
                return false;
            if (t.children.size() > 1) {
                lastNonSingle = t;
                toRemove = current;
            }
            t = t.children.get(current);
        }
        
        if (!t.isEnd)
            return false;
        t.isEnd = false;
        
        if (t.children.isEmpty())
            lastNonSingle.children.remove(toRemove);
        return true;
    }
    
    public boolean search(E[] array) {
        H_Trie<E> t = this;
        for (E current : array) {
            if (!t.children.containsKey(current))
                return false;
            t = t.children.get(current);
        }
        return t.isEnd;
    }
    
    public H_Set<E> childSet() {
        return this.children.keySet();
    }
    
    public H_Trie<E> subTrie(E elem) {
        if (!this.children.containsKey(elem))
            return null;
        return this.children.get(elem);
    }
    
    public int size() {
        int size = (this.isEnd) ? 1 : 0;
        for (H_Trie child : this.children.values()) {
            size += child.size();
        }
        return size;
    }
    
    public int totalNodes() {
        int amount = this.children.size();
        for (H_Trie child : this.children.values()) {
            amount += child.totalNodes();
        }
        return amount;
    }
    
    public String toString() {
        H_List<H_List<E>> result = new H_ArrayList<>();
        generateElements(this, new H_ArrayList<>(), result);
        return result.toString();
    }
    
    private void generateElements(H_Trie<E> t, H_List<E> local, H_List<H_List<E>> result) {
        if (t.isEnd) {
            H_List<E> clone = new H_LinkedList<>();
            for (int i = 0; i < local.size(); i++) {
                clone.add(local.get(i));
            }
            result.add(clone);
        }
        
        for (E child : t.childSet()) {
            local.add(child);
            generateElements(t.subTrie(child), local, result);
            local.remove(local.size() - 1);
        }
    }
    
    //////////////////////////////////////////////
    // --------------- TEST ------------------- //
    //////////////////////////////////////////////
    
    public static void main(String[] mains) {
        H_Trie<Character> t = new H_Trie<>();
        t.insert(helper("pineapple"));
        t.insert(helper("pine"));
        t.insert(helper("apple"));
        t.insert(helper("applejuice"));
        t.insert(helper("pen"));
        t.insert(helper("pencil"));
        t.insert(helper("pentagon"));
        t.insert(helper("pentagonal"));
        
        System.out.println(t);
        System.out.println("Size: " + t.size());
        System.out.println("Total nodes: " + t.totalNodes());
        
        System.out.println("Search 'apple': " + t.search(helper("apple")));
        System.out.println("Search 'penta': " + t.search(helper("penta")));
        
        t.remove(helper("pentagon"));
        System.out.println(t);
        System.out.println("Size: " + t.size());
        System.out.println("Total nodes: " + t.totalNodes());
        
        t = t.subTrie('p');
        System.out.println(t);
        System.out.println("Size: " + t.size());
        System.out.println("Total nodes: " + t.totalNodes());
    }
    
    private static Character[] helper(String s) {
        Character[] result = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = s.charAt(i);
        }
        return result;
    }
    
}
