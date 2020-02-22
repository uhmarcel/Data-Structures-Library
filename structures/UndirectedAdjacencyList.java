package structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndirectedAdjacencyList<Any> implements Graph<Any> {
    
    final private Map<Any, List<Any>> adjacencyList;
    final private Map<Any, Map<Any, Integer>> weightMap;
    
    public UndirectedAdjacencyList() {
        this.adjacencyList = new HashMap<>();
        this.weightMap = new HashMap<>();
    }
    
    @Override
    public int sizeVertex() {
        return adjacencyList.size();
    }

    @Override
    public int sizeEdges() {
        int count = 0;
        for (List<Any> list : adjacencyList.values()) {
            count += list.size();
        }
        return count;
    }

    @Override
    public void addVertex(Any v) {
        adjacencyList.put(v, new ArrayList<>());
        weightMap.put(v, new HashMap<>());
    }

    @Override
    public void removeVertex(Any v) {
        adjacencyList.remove(v);
        weightMap.remove(v);
    }
    
    @Override
    public void addEdge(Any v, Any u) {
        this.addEdge(v, u, 1);
    }

    @Override
    public void addEdge(Any v, Any u, int w) {
        if (!adjacencyList.containsKey(v)) addVertex(v);
        if (!adjacencyList.containsKey(u)) addVertex(u);
        adjacencyList.get(v).add(u);
        adjacencyList.get(u).add(v);
        weightMap.get(v).put(u, w);
        weightMap.get(u).put(v, w);
    }

    @Override
    public void removeEdge(Any v, Any u) {
        if (!adjacencyList.containsKey(v)) return;
        adjacencyList.get(v).remove(u);
        weightMap.get(v).remove(u);
    }
    
    @Override
    public int getEdgeWeight(Any v, Any u) {
        return weightMap.get(v).get(u);
    }

    @Override
    public Collection<Any> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public Collection<Any> getAdjacent(Any v) {
        return adjacencyList.get(v);
    }
        
}
