package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    
    private int vertices;
    private List<Integer>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new List[vertices];
        
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList();
        }
    }
    
    public void addEdge(int u, int v) {
        this.adjacencyList[u].add(v);
    }
    
    public void addEdgeUndirected(int u, int v) {
        addEdge(u, v);
        addEdge(v, u);
    }
    
    public int getVertexAmount() {
        return this.vertices;
    }
    
    public List<Integer> getAdjacent(int vertex) {
        return this.adjacencyList[vertex];
    }
    
    public List<Integer> BFS(int s) {
        boolean[] visited = new boolean[this.vertices];
        List<Integer> result = new LinkedList();
        
        for (int v = 0; v < this.vertices; v++) {
            BFS_helper((v + s) % this.vertices, visited, result);
        }
        return result;
    }
        
    public void BFS_helper(int s, boolean[] visited, List<Integer> result) {
        Queue<Integer> Q = new LinkedList();
        Q.add(s);
                
        while (!Q.isEmpty()) {
            int v = Q.remove();
            
            if (!visited[v]) {
                visited[v] = true;
                result.add(v);
            }
            
            for (int u : this.adjacencyList[v]) {
                if (!visited[u]) {
                    Q.add(u);
                }
            }
        }
    }
    
    public List<Integer> DFS(int s) {
        boolean[] visited = new boolean[this.vertices];
        List<Integer> result = new LinkedList();
        
        for (int v = 0; v < this.vertices; v++) {
            DFS_helper((v + s) % this.vertices, visited, result);
        }
        return result;
    }
    
    private void DFS_helper(int s, boolean[] visited, List<Integer> result) {
        Stack<Integer> S = new Stack();
        S.push(s);
                
        while (!S.isEmpty()) {
            int v = S.pop();
            
            if (!visited[v]) {
                visited[v] = true;
                result.add(v);
            }
            
            for (int u : this.adjacencyList[v]) {
                if (!visited[u]) {
                    S.push(u);
                }
            }
        }
    }
    
    public List<Integer> topologicalSort() {
        boolean[] visited = new boolean [this.vertices];
        LinkedList<Integer> result = new LinkedList();
        
        for (int v = 0; v < this.vertices; v++) {
            if (!visited[v]) {
                topologicalSortHelper(v, visited, result);
            }
        }
        return result;
    }
    
    private void topologicalSortHelper(int v, boolean[] visited, LinkedList<Integer> result) {
        visited[v] = true;
        
        for (int u : this.adjacencyList[v]) {
            if (!visited[u]) {
                topologicalSortHelper(u, visited, result);
            }
        }
        
        result.addFirst(v);
    }
    
    
    
    ////// Testbench //////
    
    
    
    public void sort() {
        for (List<Integer> L : this.adjacencyList) {
            L.sort(null);
        }
    }
    
    public static void printGraph(Graph g) {
        int size = g.getVertexAmount();
        
        for (int v = 0; v < size; v++) {
            Iterator<Integer> iter = g.getAdjacent(v).iterator();
            
            System.out.print(v + " -> ");
            while (iter.hasNext()) {
                int u = iter.next();
                System.out.print(u + ", ");
            }
            System.out.println();
        }
        
    }
    
}
