
import structures.Graph;


public class GraphTest {
    
    public static void main(String[] args) {
            
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(1, 7);
        g.addEdge(6, 7);
        
        Graph.printGraph(g);
        
        int s = 0;
        System.out.println("BFS: " + g.BFS(s));
        System.out.println("DFS: " + g.DFS(s));
        System.out.println("Topological: " + g.topologicalSort());
        
    }
}
