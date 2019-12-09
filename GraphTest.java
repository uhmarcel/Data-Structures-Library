
import java.util.Arrays;
import structures.Graph;


public class GraphTest {
    
    public static void main(String[] args) {
            
        Graph g = new Graph(8);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 1);
        g.addEdge(0, 3, 1);
        g.addEdge(2, 4, 3);
        g.addEdge(3, 5, 2);
        g.addEdge(4, 5, 2);
        g.addEdge(5, 6, 5);
        g.addEdge(1, 7, 5);
        g.addEdge(6, 7, 1);
        
        Graph.printGraph(g);
        
        int s = 0;
        System.out.println("BFS: " + g.BFS(s));
        System.out.println("DFS: " + g.DFS(s));
        System.out.println("Topological: " + g.topologicalSort());
        
        int[][] sp = g.BellmanFordSPD(s);
        System.out.println("Bellman-Ford Shortest Path:");
        System.out.println("  Dist[v] ->" + Arrays.toString(sp[Graph.DISTANCE_INDEX]));
        System.out.println("  Prev[v] ->" + Arrays.toString(sp[Graph.PREVIOUS_INDEX]));
        
        
//        g.MST();
        
    }
}
