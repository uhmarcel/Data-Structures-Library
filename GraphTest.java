
import java.util.Arrays;
import structures.Graph;


public class GraphTest {
    
    public static void main(String[] args) {
            
        Graph g = new Graph(9);
        g.addEdgeUndirected(0, 1, 4); 
        g.addEdgeUndirected(0, 7, 8); 
        g.addEdgeUndirected(1, 2, 8); 
        g.addEdgeUndirected(1, 7, 11); 
        g.addEdgeUndirected(2, 3, 7); 
        g.addEdgeUndirected(2, 8, 2); 
        g.addEdgeUndirected(2, 5, 4); 
        g.addEdgeUndirected(3, 4, 9); 
        g.addEdgeUndirected(3, 5, 14); 
        g.addEdgeUndirected(4, 5, 10); 
        g.addEdgeUndirected(5, 6, 2); 
        g.addEdgeUndirected(6, 7, 1); 
        g.addEdgeUndirected(6, 8, 6); 
        g.addEdgeUndirected(7, 8, 7); 
        
//        Graph g = new Graph(5);
//        g.addEdge(0, 1, -1);
//        g.addEdge(0, 2, 4);
//        g.addEdge(1, 2, 3);
//        g.addEdge(1, 3, 2);
//        g.addEdge(1, 4, 2);
//        g.addEdge(3, 2, 5);
//        g.addEdge(3, 1, 1);
//        g.addEdge(4, 3, -3);
        
        Graph.printGraph(g);
        
        int s = 0;
        System.out.println("BFS: " + g.BFS(s));
        System.out.println("DFS: " + g.DFS(s));
        System.out.println("Topological: " + g.topologicalSort());
        
        int[][] sp = g.BellmanFordSPD(s);
        System.out.println("Bellman-Ford Shortest Path:");
        System.out.println("  Dist[v] ->" + Arrays.toString(sp[Graph.DISTANCE_INDEX]));
        System.out.println("  Prev[v] ->" + Arrays.toString(sp[Graph.PREVIOUS_INDEX]));
        
//        g.KruskarMST();
        System.out.println("PrimMST: " + Arrays.toString(g.PrimMST()));
        
//        g.MST();
        
    }
}
