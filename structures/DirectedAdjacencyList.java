package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DirectedAdjacencyList<T> extends Graph<T> {
    
    class Neighbor<T> {
        public T vertex;
        public int weight;
        public Neighbor(T v, int w) {
            this.vertex = v;
            this.weight = w;
        }
    }
    
    private Map<T, List<Neighbor<T>>> adjacencyList;
    
    public DirectedAdjacencyList() {
        adjacencyList = new HashMap<>();
    }
    
    public int numVertices() {
        return adjacencyList.size();
    }

    public int numEdges() {
        int edges = 0;
        for (List<Neighbor<T>> adj : adjacencyList.values()) {
            edges += adj.size();
        }
        return edges;
    }

    public void addVertex(T v) {
        adjacencyList.put(v, new ArrayList<>());
    }

    public void removeVertex(T v) {
        adjacencyList.remove(v);
    }

    public void addEdge(T v, T u, int w) {
        if (!adjacencyList.containsKey(v)) addVertex(v);
        if (!adjacencyList.containsKey(u)) addVertex(u);
        adjacencyList.get(v).add(u);
        
    }

    public void removeEdge(T v, T u, int w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> getVertices() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> getAdjacent(T v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

}
