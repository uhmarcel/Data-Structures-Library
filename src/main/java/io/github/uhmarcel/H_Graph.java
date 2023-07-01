package io.github.uhmarcel;
import java.util.Collection;

public interface H_Graph<Any> {
   
    public int sizeVertex();
    public int sizeEdges();
    public void addVertex(Any v);
    public void addEdge(Any v, Any u);
    public void addEdge(Any v, Any u, int w);
    public void removeVertex(Any v);
    public void removeEdge(Any v, Any u);
    public int getEdgeWeight(Any v, Any u);
    public Collection<Any> getVertices();
    public Collection<Any> getAdjacent(Any v);

}
