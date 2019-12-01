package assignment3;
/* EE422C Assignment #3 submission by
 * Julia Romero
 * JLR5576
 */
import java.util.*;

// a Vertex has a Map of adjacent Vertices to their Weights
// and a Set of the Vertices feeding into it
public class Vertex<T> {
    public T value;
    public Map<T, Integer> edges;
    public Set<T> inEdges;

    public Vertex(T value){
        this.value = value;
        this.edges = new HashMap<>();
        this.inEdges = new HashSet<>();
    }

    public void incrementWeight(T value) {
        int newWeight = 1;
        if (edges.containsKey(value)) {
            newWeight += edges.get(value);
        }
        edges.put(value, newWeight);
    }

}
