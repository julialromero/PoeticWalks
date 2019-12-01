package assignment3;
/* EE422C Assignment #3 submission by
 * Julia Romero
 * JLR5576
 */

import java.util.*;
import java.util.stream.Collectors;

public class Graph<T> {
    // Maps the value to the Vertex created with that value
    public Map<T, Vertex<T>> vertices;

    public Graph() {
        vertices = new LinkedHashMap<>();
    }

    /**
     * Fetches a vertex from vertices, adding it if it does not exist.
     *
     * @param value value of the vertex.
     * @return the existing or newly created vertex.
     */
    public Vertex<T> fetchAndAddVertex(T value) {
        Vertex<T> vertex = vertices.get(value);
        if (vertex == null) {
            vertex = new Vertex<>(value);
            vertices.put(value, vertex);
        }
        return vertex;
    }

    /**
     * Run depth first search to find Vertices that have the target in their adjacent Edges Map
     * Return value stored in bridges List
     *
     * @param root    the current word's Vertex object
     * @param visited the Set of all words visited so far
     * @param depth   keeps track of the depth of the recursion
     * @param target  target value the method is searching for
     * @param bridges List of Vertices that have the target value in its edges
     */
    public void findBridges(Vertex<T> root, Set<Vertex<T>> visited, int depth, T target, List<Vertex<T>> bridges) {
        if (visited.contains(root)) return;

        visited.add(root);
        if (depth < 2) {
            for (T adj : root.edges.keySet()) {
                findBridges(vertices.get(adj), visited, depth + 1, target, bridges);
            }
        }
        if (depth == 2 && root.edges.containsKey(target)) {
            bridges.add(root);
        }
    }
}
