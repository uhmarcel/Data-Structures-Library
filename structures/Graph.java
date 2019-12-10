package structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class Graph {
    
    public class Node implements Comparable<Node> {        
        public int u;
        public int w;
        
        public Node(int u, int w) {
            this.u = u;
            this.w = w;
        }

        @Override
        public int compareTo(Node t) {
            return this.w - t.w;
        }
        
        @Override
        public String toString() {
            return Integer.toString(u) + "/" + w;
        }
    }
        
    public static final int DISTANCE_INDEX = 0;
    public static final int PREVIOUS_INDEX = 1;
    
    private int vertices;
    private List<Node>[] adjacencyList;

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
        this.adjacencyList[v].add(new Node(u,w));
    }
    
    public void addEdgeUndirected(int v, int u) {
        addEdge(u, v);
        addEdge(v, u);
    }
    
    public void addEdgeUndirected(int v, int u, int w) {
        addEdge(u, v, w);
        addEdge(v, u, w);
    }
    
    public int getVertexAmount() {
        return this.vertices;
    }
    
    public List<Node> getAdjacent(int vertex) {
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
            
            for (Node edge : this.adjacencyList[v]) {
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
            
            for (Node edge : this.adjacencyList[v]) {
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
        
        for (Node edge : this.adjacencyList[v]) {
            if (!visited[edge.u]) {
                topologicalSortHelper(edge.u, visited, result);
            }
        }
        
        result.addFirst(v);
    }
    
    
    // Doesn't work - Requires map utils
    public Graph KruskarMST() {
        
//        class Edge implements Comparable<Edge> {
//            public int v, u, w;
//            public Edge(int _v, int _u, int _w) { v = _v; u = _u; w = _w; }
//            public int compareTo(Edge e) { return this.w - e.w; }
//        }
//        
//        PriorityQueue<Edge> H = new PriorityQueue();
//        
//        for (int v = 0; v < vertices; v++) {
//            for (Edge n : this.adjacencyList[v]) {
//                H.add(new Edge(v, n.u, n.w));
//            }
//        }
//        
//        System.out.println(H);
//        
//        Graph G = new Graph(vertices);
//        
//        while (!H.isEmpty()) {
//            Edge e = H.remove();
//            if (G.getAdjacent(e.u).isEmpty()) {
//                G.addEdge(e.v, e.u, e.w);
//            }
//        }
//        
//        printGraph(G);
        return null;
    }
    
    // Works, but sketchy implementation.
    public int[] PrimMST() {     
        int[] prev = new int[vertices];
        boolean[] found = new boolean[vertices]; 
        Node[] nodes = new Node[vertices];
        
        for (int v = 0; v < vertices; v++) {
            nodes[v] = new Node(v, Integer.MAX_VALUE);
        }
        nodes[0].w = 0;
        
        PriorityQueue<Node> H = new PriorityQueue();
        for (int v = 0; v < vertices; v++) {
            H.add(new Node(nodes[v].u, nodes[v].w));
        }
        
        for (int i = 0; i < vertices; i++) {
            if (H.isEmpty()) {
                break;
            }
            
            Node v = H.remove();
            while (found[v.u]) {
                v = H.remove();
            } 
            found[v.u] = true;
            
            for (Node e : adjacencyList[v.u]) {
                if (!found[e.u]) {
                    if (nodes[e.u].w > e.w) {
                        nodes[e.u].w = e.w;
                        prev[e.u] = v.u;
                        H.add(new Node(nodes[e.u].u, nodes[e.u].w));    
                    }
                }
            }
        }
        return prev;  
    }
    
    public int[][] BellmanFordSPD(int s) {
        int[] dist = new int[vertices];
        int[] prev = new int[vertices];
        
        for (int v = 0; v < vertices; v++) {
            dist[v] = Integer.MAX_VALUE;
            prev[v] = -1;
        }
    
        dist[s] = 0;
        for (int i = 0; i < vertices - 1; i++) {
            for (int v = 0; v < vertices; v++) {
                for (Node e : adjacencyList[v]) {
                    if (dist[e.u] > dist[v] + e.w) {
                        dist[e.u] = dist[v] + e.w;
                        prev[e.u] = v;
                    }
                }
            }
        }
        return new int[][] {dist, prev};
    }
    
    public int[][] DijkstraSPD(int s) {
        int[] dist = new int[vertices];
        int[] prev = new int[vertices];
        boolean[] done  = new boolean[vertices];
        
        for (int v = 0; v < vertices; v++) {
            dist[v] = Integer.MAX_VALUE;
            prev[v] = -1;
            done[v] = false;
        }
        
        dist[s] = 0;
        
        PriorityQueue<Node> H = new PriorityQueue();
        for (int v = 0; v < vertices; v++) {
            H.add(new Node(v, dist[v]));
        }
        
        for (int i = 0; i < vertices; i++) {
            Node V = H.remove();
            while (done[V.u]) {
                V = H.remove();
            }
            
            for (Node E : adjacencyList[V.u]) {
                if (!done[E.u]) {
                    if (dist[E.u] > dist[V.u] + E.w) {
                        dist[E.u] = dist[V.u] + E.w;
                        prev[E.u] = V.u;
                        H.add(new Node(E.u, dist[E.u]));
                    }
                }
            }
        }
        
        return new int[][] {dist, prev};  
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
            for (Node edge : g.getAdjacent(v)) {
                System.out.print(edge.u + ", ");
            }
            System.out.println();
        }
    }
    
}
