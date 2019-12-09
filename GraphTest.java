
import java.util.Arrays;
import structures.Graph;


public class GraphTest {
    
    public static void main(String[] args) {
        
                
        Graph g = new Graph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 4);
        
        g.sort();
        
        Graph.printGraph(g);
        
        int s = 3;
//        System.out.println("BFS: " + Arrays.toString(g.BFS(s)) );
//        System.out.println("DFS: " + Arrays.toString(g.DFS(s)) );
        
    }
}
