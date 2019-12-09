package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    
    public class WeightedPair {
        public int u;
        public int w;
        public WeightedPair(int u, int w) {
            this.u = u;
            this.w = w;
        }
    }
    
    private int vertices;
    private List<WeightedPair>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new List[vertices];
        
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList();
        }
    }
    
    public void addEdge(int v, int u) {
        this.addEdge(v, u, 1);
    }
    
    public void addEdge(int v, int u, int w) {
        this.adjacencyList[v].add(new WeightedPair(u,w));
    }
    
    public void addEdgeUndirected(int v, int u) {
        addEdge(u, v);
        addEdge(v, u);
    }
    
    public int getVertexAmount() {
        return this.vertices;
    }
    
    public List<WeightedPair> getAdjacent(int vertex) {
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
            
            for (WeightedPair edge : this.adjacencyList[v]) {
                if (!visited[edge.u]) {
                    Q.add(edge.u);
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
            
            for (WeightedPair edge : this.adjacencyList[v]) {
                if (!visited[edge.u]) {
                    S.push(edge.u);
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
        
        for (WeightedPair edge : this.adjacencyList[v]) {
            if (!visited[edge.u]) {
                topologicalSortHelper(edge.u, visited, result);
            }
        }
        
        result.addFirst(v);
    }
    
    
    
    ////// Testbench //////
    
    
    
//    public void sort() {
//        for (List<Integer> L : this.adjacencyList) {
//            L.sort(null);
//        }
//    }
    
    public static void printGraph(Graph g) {
        int size = g.getVertexAmount();
        
        for (int v = 0; v < size; v++) {
            for (WeightedPair edge : g.getAdjacent(v)) {
                System.out.print(edge.u + ", ");
            }
            System.out.println();
        }
    }
    
}
