// Name: Veljko Cvetkovic
// Date: 05/01/22

import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList/AdjacencyList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/* Graphs 3: Adjacency List
 */
interface VertexInterface {

    String toString(); // Don't use commas in the list.  Example: "C [C D]"

    String getName();

    ArrayList<Vertex> getAdjacencies();

    void addAdjacent(Vertex v);
}

class Vertex implements VertexInterface {

    private final String name;
    private ArrayList<Vertex> adjacencies;

    public Vertex(String v) {
        name = v;
        adjacencies = new ArrayList<Vertex>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Vertex> getAdjacencies() {
        return this.adjacencies;
    }

    public void addAdjacent(Vertex v) {
        adjacencies.add(v);
    }

    public String toString() {
        String t = this.name + " [";
        for (Vertex v : adjacencies) {
            t += v.getName();
            if(!adjacencies.get(adjacencies.size()-1).equals(v))
                t += " ";
        }
        t += "]";
        return t;
    }

}

interface AdjListInterface {

    List<Vertex> getVertices();

    Vertex getVertex(int i);

    Vertex getVertex(String vertexName);

    Map<String, Integer> getVertexMap();

    void addVertex(String v);

    void addEdge(String source, String target);

    String toString();  //returns all vertices with their edges (omit commas)
}


/* Graphs 4: DFS and BFS
 */
interface DFS_BFS {

    List<Vertex> depthFirstSearch(String name);

    List<Vertex> depthFirstSearch(Vertex v);

    List<Vertex> breadthFirstSearch(String name);

    List<Vertex> breadthFirstSearch(Vertex v);

    /*  three extra credit methods */
    List<Vertex> depthFirstRecur(String name);

    List<Vertex> depthFirstRecur(Vertex v);

    void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable);
}

/* Graphs 5: Edgelist with Cities
 */
interface EdgeListWithCities {

    void graphFromEdgeListData(String fileName) throws FileNotFoundException;

    int edgeCount();

    int vertexCount(); //count the vertices in the object

    boolean isReachable(String source, String target);

    boolean isConnected();
}


public class P6VeljkoCvetkovicAdjList implements AdjListInterface, DFS_BFS { //, EdgeListWithCities

    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();

    public List<Vertex> getVertices() {
        return this.vertices;
    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    public Vertex getVertex(String vertexName) {
        return vertices.get(nameToIndex.get(vertexName));
    }

    public Map<String, Integer> getVertexMap() {
        return nameToIndex;
    }

    public void addVertex(String v) {
        if (!nameToIndex.containsKey(v)) {
            nameToIndex.put(v, vertices.size());
            vertices.add(new Vertex(v));
        }
    }

    public void addEdge(String source, String target) {
        if (!nameToIndex.containsKey(source))
            addVertex(source);
        else if (!nameToIndex.containsKey(target)) {
            addVertex(target);
            vertices.get(nameToIndex.get(source)).addAdjacent(vertices.get(nameToIndex.get(target)));
        } else
            vertices.get(nameToIndex.get(source)).addAdjacent(vertices.get(nameToIndex.get(target)));
    }

    public String toString() { //returns all vertices with their edges (omit commas)
        String t = "";
        for (Vertex v : vertices) {
            t += v.toString() + "\n";
        }
        return t;
    }

    public List<Vertex> depthFirstSearch(String name) {
        if(!nameToIndex.containsKey(name))
            return null;

        return depthFirstSearch(vertices.get(nameToIndex.get(name)));
    }

    public List<Vertex> depthFirstSearch(Vertex v) {
        Stack<Vertex> stack = new Stack<>();
        ArrayList<Vertex> list = new ArrayList<>();

        stack.push(v);

        while(!stack.isEmpty()) {
            Vertex x = stack.pop();

            if(!list.contains(x)) {
                list.add(x);

                for(Vertex t: x.getAdjacencies()){
                    if(!list.contains(t))
                        stack.push(t);
                }
            }
        }
        return list;
    }

    public List<Vertex> breadthFirstSearch(String name) {
        if(!nameToIndex.containsKey(name))
            return null;

        return breadthFirstSearch(vertices.get(nameToIndex.get(name)));
    }

    public List<Vertex> breadthFirstSearch(Vertex v) {
        Queue<Vertex> queue = new LinkedList<>();
        ArrayList<Vertex> list = new ArrayList<>();

        queue.add(v);

        while(!queue.isEmpty()) {
            Vertex temp = queue.remove();

            if(!list.contains(temp)) {
                list.add(temp);

                for(Vertex t: temp.getAdjacencies()) {
                    if(!list.contains(t))
                        queue.add(t);
                }
            }
        }
        return list;
    }

    /*  three extra credit methods, recursive version  */
    public List<Vertex> depthFirstRecur(String name) {
        if(!nameToIndex.containsKey(name))
            return null;

        return depthFirstRecur(vertices.get(nameToIndex.get(name)));
    }

    public List<Vertex> depthFirstRecur(Vertex v) {
        ArrayList<Vertex> list = new ArrayList<>();
        depthFirstRecurHelper(v, list);
        return list;
    }

    public void depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable) {
        reachable.add(v);
        for(Vertex x: v.getAdjacencies()) {
            if(!reachable.contains(x))
                depthFirstRecurHelper(x, reachable);
        }
    }

    public List<Vertex> topologicalSort(String name) {
        return topologicalSort(vertices.get(nameToIndex.get(name)));
    }

    public List<Vertex> topologicalSort(Vertex v) {
        ArrayList<Vertex> list = new ArrayList<>();
        Stack<Vertex> stack = new Stack<>();
        ArrayList<Vertex> order = new ArrayList<>(); //necessary for correct order
        topologicalSortHelper(v, list, stack);

        while(!stack.isEmpty())
            order.add(stack.pop());

        return order;
    }

    public void topologicalSortHelper(Vertex u, ArrayList<Vertex> visited, Stack<Vertex> stack) {
        visited.add(u);
        for(Vertex x: u.getAdjacencies()) {
            if(!visited.contains(x))
                topologicalSortHelper(x, visited, stack);
        }
        stack.push(u);
    }
}

/* Program Output - DFS/BFS/TS

Edge List Representation!
A [C]
B [A]
C [C D]
D [C A]


Depth First Search
A ---> [A [C], C [C D], D [C A]]
B ---> [B [A], A [C], C [C D], D [C A]]
C ---> [C [C D], D [C A], A [C]]
D ---> [D [C A], A [C], C [C D]]

Breadth First Search
A ---> [A [C], C [C D], D [C A]]
B ---> [B [A], A [C], C [C D], D [C A]]
C ---> [C [C D], D [C A], A [C]]
D ---> [D [C A], C [C D], A [C]]

D ---> [D [C A], A [C], C [C D]]
D ---> [D [C A], C [C D], A [C]]

Depth First Search (Recursive)
A ---> [A [C], C [C D], D [C A]]
B ---> [B [A], A [C], C [C D], D [C A]]
C ---> [C [C D], D [C A], A [C]]
D ---> [D [C A], C [C D], A [C]]

Topological Sort
A ---> [A [C], C [C D], D [C A]]
B ---> [B [A], A [C], C [C D], D [C A]]
C ---> [C [C D], D [C A], A [C]]
D ---> [D [C A], A [C], C [C D]]

Process finished with exit code 0

 */