package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H_AdjacencyList<Any> implements H_Graph<Any> {
    
    final private Map<Any, List<Any>> adjacencyList;
    final private Map<Any, Map<Any, Integer>> weightMap;
    
    public H_AdjacencyList() {
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
        adjacencyList.get(v).add(u);
        weightMap.get(v).put(u, w);
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
