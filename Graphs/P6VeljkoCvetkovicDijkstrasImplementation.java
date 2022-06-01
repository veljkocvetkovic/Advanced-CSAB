/*
 * Name: Veljko Cvetkovic
 * Period: 6
 * What I learned:
 * 1. I learned how to implement Dijkstra's Algorithm
 * 2. Why the wVertex and Edge classes are necessary to implement it
 * 3. How to get the path after executing Dijkstra's Algorithm
 */

import java.io.*;
import java.util.*;

//***********************************************************  Edge Class
// need to have this class for adjacency list representation of a weighted graph
class Edge {

    private final wVertex target;
    private final double weight;

    public Edge(wVertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }

    // add accessors and modifiers below
    public wVertex getTargetVertex() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

} // end of Edge


// each wVertex has a name, a list of adjacent weighted edges
class wVertex implements Comparable<wVertex> {

    private final String name;
    private ArrayList<Edge> adjacencies;
    private double minDistance = Double.POSITIVE_INFINITY;  // this field is needed in order to implement Dijkstra's algorithm

    private wVertex previous;  //for building the actual shortest path resulted from Dijkstra's algorithm

    public wVertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    // Accessors and modifiers of wVertex
    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double m) {
        minDistance = m;
    }

    public wVertex getPrevious() {
        return previous;
    }

    public void setPrevious(wVertex v) {
        previous = v;
    }

    public ArrayList<Edge> getAdjacencies() {
        return adjacencies;
    }

    // implement this method
    public int compareTo(wVertex other) {
        if (this.getMinDistance() < other.getMinDistance())
            return -1;

        else if (this.getMinDistance() > other.getMinDistance())
            return 1;

        return 0;
    }

    // toString returns the name of the wVertex, not all the attributes of a wVertex; same as getName() ... Sorry.
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
/*****************/
}  // end of wVertex

public class P6VeljkoCvetkovicDijkstrasImplementation {

    private ArrayList<wVertex> vertices = new ArrayList<wVertex>();
    private Map<String, Integer> nameToIndex = new HashMap<String, Integer>();


    public List<wVertex> getVertices() {
        return vertices;
    }

    public wVertex getVertex(int i) {
        return vertices.get(i);
    }

    public wVertex getVertex(String vertexName) {
        return vertices.get(nameToIndex.get(vertexName));
    }

    public void addVertex(String v) {
        if (!nameToIndex.containsKey(v))  //don't add the same vertex twice
        {
            vertices.add(new wVertex(v));
            nameToIndex.put(v, vertices.size() - 1);
        }
    }

    public void addEdge(String source, String target, double weight) {
        getVertex(source).getAdjacencies().add(new Edge(getVertex(target), weight));
    }

    public void minimumWeightPath(String vertexName) {
        minimumWeightPath(getVertex(vertexName));
    }

    // implement Dijkstra's algorithm here
    private void minimumWeightPath(wVertex source) {

        // Step 1: set the min distance from source to infinity - minDistance already set to infinity in wVertex class

        // set the min distance to itself to 0
        source.setMinDistance(0);

        // create a priority queue
        PriorityQueue<wVertex> vertexQueue = new PriorityQueue<wVertex>();

        // enqueue the source wVertex
        vertexQueue.add(source);

        // start processing the priority queue.
        while (!vertexQueue.isEmpty()) {
            wVertex u = vertexQueue.remove();
            for (Edge e : u.getAdjacencies()) {
                double oldDist = e.getTargetVertex().getMinDistance();
                double newDist = u.getMinDistance() + e.getWeight();
                if (newDist < oldDist) {
                    e.getTargetVertex().setMinDistance(newDist);
                    e.getTargetVertex().setPrevious(u);

                    if (oldDist == Double.POSITIVE_INFINITY)
                        vertexQueue.add(e.getTargetVertex());
                }
            }
        } // while

    }  // end of private minimumWeightPath

    // returns the shortest path from source to vertexName
    public List<wVertex> getShortestPathTo(String vertexName) {
        if (!nameToIndex.containsKey(vertexName))
            return null;

        return getShortestPathTo(vertices.get(nameToIndex.get(vertexName)));
    }

    //version without using a stack
    /*public List<wVertex> getShortestPathTo(wVertex v) {
        ArrayList<wVertex> list = new ArrayList<>();

        while (v != null && v.getMinDistance() > 0) {
            list.add(0, v);
            v = v.getPrevious();
        }

        list.add(0, v);
        return list;
    }*/

    public List<wVertex> getShortestPathTo(wVertex v) {
        ArrayList<wVertex> list = new ArrayList<>();
        Stack<wVertex> stack = new Stack<>();

        while (v != null && v.getMinDistance() > 0) {
            stack.push(v);
            v = v.getPrevious();
        }
        stack.push(v); //push destination vertex once distance is 0

        while(!stack.isEmpty()) {
            list.add(stack.pop());
        }

        return list;
    }

    //**************************************   main   *********************************************/
    public static void main(String[] args) throws FileNotFoundException {
        /* four vertex graph */
        P6VeljkoCvetkovicDijkstrasImplementation graph = new P6VeljkoCvetkovicDijkstrasImplementation();

        // Do we really need the next four addVertex() statements to create the graph?
        // Think ...
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        // sketch the DAG on a piece of paper first so that you know what kind of graph you are
        // dealing with
        graph.addEdge("A", "B", 9);
        graph.addEdge("A", "C", 3);
        graph.addEdge("C", "B", 5);
        graph.addEdge("C", "D", 2);
        graph.addEdge("D", "B", 1);

        Scanner key = new Scanner(System.in);

        System.out.print("Enter start: ");
        String source = key.next();

        // find the minimum path from "source"
        graph.minimumWeightPath(source);

        // print out the minimum path cost from "source" to other vertices
        for (wVertex v : graph.getVertices()) {
            System.out.println("\nDistance to " + v + ": " + v.getMinDistance());
            if(v.getMinDistance() == Double.POSITIVE_INFINITY)
                System.out.println("Shortest Path from " + source + " to " + v + ": NO PATH");
            else
                System.out.println("Shortest Path from " + source + " to " + v + ": " + graph.getShortestPathTo(v));
        }

    }  // end of main
} // end of GraphAdjListWeighted_shell

/******************************************* Program Output

 Enter start: A

 Distance to A: 0.0
 Shortest Path from A to A: [A]

 Distance to B: 6.0
 Shortest Path from A to B: [A, C, D, B]

 Distance to C: 3.0
 Shortest Path from A to C: [A, C]

 Distance to D: 5.0
 Shortest Path from A to D: [A, C, D]

 Process finished with exit code 0

 -----------------------------------

 Enter start: B

 Distance to A: Infinity
 Shortest Path from B to A: NO PATH

 Distance to B: 0.0
 Shortest Path from B to B: [B]

 Distance to C: Infinity
 Shortest Path from B to C: NO PATH

 Distance to D: Infinity
 Shortest Path from B to D: NO PATH

 Process finished with exit code 0

 -----------------------------------

 Enter start: C

 Distance to A: Infinity
 Shortest Path from C to A: NO PATH

 Distance to B: 3.0
 Shortest Path from C to B: [C, D, B]

 Distance to C: 0.0
 Shortest Path from C to C: [C]

 Distance to D: 2.0
 Shortest Path from C to D: [C, D]

 Process finished with exit code 0

 -----------------------------------

 Enter start: D

 Distance to A: Infinity
 Shortest Path from D to A: NO PATH

 Distance to B: 1.0
 Shortest Path from D to B: [D, B]

 Distance to C: Infinity
 Shortest Path from D to C: NO PATH

 Distance to D: 0.0
 Shortest Path from D to D: [D]

 Process finished with exit code 0

 **********************************************************/