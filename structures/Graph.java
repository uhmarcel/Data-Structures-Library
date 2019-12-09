package structures;


import java.util.Arrays;
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
    
    public void addEdgeUndirectedChar(char u, char v) {
        addEdgeUndirected(u - 'A', v - 'A');
    }
    
    public int getVertexAmount() {
        return this.vertices;
    }
    
    public List<Integer> getAdjacent(int vertex) {
        return this.adjacencyList[vertex];
    }
        
    public List<Integer> BFS(int s) {
        boolean[] visited = new boolean[this.vertices];
        Queue<Integer> Q = new LinkedList();
        Q.add(s);
        
        List<Integer> result = new LinkedList();
                
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
        return result;
    }
    
    public List<Integer> DFS(int s) {
        boolean[] visited = new boolean[this.vertices];
        Stack<Integer> S = new Stack();
        S.push(s);
        
        List<Integer> result = new LinkedList();
                
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
        return result;
    }
    
    
    
    ////// Testbench //////
    
    
    
    public void sort() {
        for (List<Integer> L: this.adjacencyList) {
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
