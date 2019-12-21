package structures;

import java.util.List;


public abstract class Graph<Any> {
    
    public abstract int numVertices();
    public abstract int numEdges();
    
    public abstract void addVertex(Any v);
    public abstract void removeVertex(Any v);
    public abstract void addEdge(Any v, Any u, int w);
    public abstract void removeEdge(Any v, Any u, int w);
    
    public abstract List<Any> getVertices();
    public abstract List<Any> getAdjacent(Any v);

}
