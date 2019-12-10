
import java.util.Arrays;
import structures.Graph;


public class GraphTest {
    
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1, 5); 
        g.addEdge(0, 2, 3); 
        g.addEdge(1, 3, 6); 
        g.addEdge(1, 2, 2); 
        g.addEdge(2, 4, 4); 
        g.addEdge(2, 5, 2); 
        g.addEdge(2, 3, 7); 
        g.addEdge(3, 4, -1); 
        g.addEdge(4, 5, -2); 
            
//        Graph g = new Graph(9);
//        g.addEdgeUndirected(0, 1, 4); 
//        g.addEdgeUndirected(0, 7, 8); 
//        g.addEdgeUndirected(1, 2, 8); 
//        g.addEdgeUndirected(1, 7, 11); 
//        g.addEdgeUndirected(2, 3, 7); 
//        g.addEdgeUndirected(2, 8, 2); 
//        g.addEdgeUndirected(2, 5, 4); 
//        g.addEdgeUndirected(3, 4, 9); 
//        g.addEdgeUndirected(3, 5, 14); 
//        g.addEdgeUndirected(4, 5, 10); 
//        g.addEdgeUndirected(5, 6, 2); 
//        g.addEdgeUndirected(6, 7, 1); 
//        g.addEdgeUndirected(6, 8, 6); 
//        g.addEdgeUndirected(7, 8, 7); 
        
//        Graph g = new Graph(5);
//        g.addEdge(0, 1, -1);
//        g.addEdge(0, 2, 4);
//        g.addEdge(1, 2, 3);
//        g.addEdge(1, 3, 2);
//        g.addEdge(1, 4, 2);
//        g.addEdge(3, 2, 5);
//        g.addEdge(3, 1, 1);
//        g.addEdge(4, 3, -3);
        
//        Graph.printGraph(g);

        int startNode = 0;
        int[][] shortestPath;
        
        System.out.println("BFS: " + g.BFS(startNode));
        System.out.println("DFS: " + g.DFS(startNode));
        System.out.println("Topological: " + g.topologicalSort());
        
//        g.KruskarMST();
        System.out.println("PrimMST: " + Arrays.toString(g.PrimMST()));
        
        shortestPath = g.BellmanFordSPD(startNode);
        System.out.println("Bellman-Ford Shortest Path:");
        System.out.println("  Dist[v] ->" + Arrays.toString(shortestPath[Graph.DISTANCE_INDEX]));
        System.out.println("  Prev[v] ->" + Arrays.toString(shortestPath[Graph.PREVIOUS_INDEX]));
        
        shortestPath = g.DijkstraSPD(startNode);
        System.out.println("Dijkstra Shortest Path:");
        System.out.println("  Dist[v] ->" + Arrays.toString(shortestPath[Graph.DISTANCE_INDEX]));
        System.out.println("  Prev[v] ->" + Arrays.toString(shortestPath[Graph.PREVIOUS_INDEX]));
        
        shortestPath = g.directedAcyclicSPD(startNode);
        System.out.println("DAG Shortest Path:");
        System.out.println("  Dist[v] ->" + Arrays.toString(shortestPath[Graph.DISTANCE_INDEX]));
        System.out.println("  Prev[v] ->" + Arrays.toString(shortestPath[Graph.PREVIOUS_INDEX]));
        
        
//        g.MST();
        
    }
}
