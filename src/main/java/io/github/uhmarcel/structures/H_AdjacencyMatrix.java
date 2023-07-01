package io.github.uhmarcel.structures;

import io.github.uhmarcel.H_Graph;
import java.util.Collection;

public class H_AdjacencyMatrix<Any> implements H_Graph<Any> {
    
    @Override
    public int sizeVertex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int sizeEdges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addVertex(Object v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEdge(Object v, Object u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEdge(Object v, Object u, int w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeVertex(Object v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeEdge(Object v, Object u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getEdgeWeight(Object v, Object u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection getVertices() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection getAdjacent(Object v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
