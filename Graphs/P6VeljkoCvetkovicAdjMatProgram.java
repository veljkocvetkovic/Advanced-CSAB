// Name: Veljko Cvetkovic
// Date: 04/19/22

import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

interface AdjacencyMatrix {
    void addEdge(int source, int target);

    void removeEdge(int source, int target);

    boolean isEdge(int from, int to);

    String toString(); //returns the grid as a String

    int edgeCount();

    List<Integer> getNeighbors(int source);

    List<String> getReachables(String from);  //Warshall extension
}

interface Warshall { //User-friendly functionality

    boolean isEdge(String from, String to);

    Map<String, Integer> getVertices();

    void readNames(String fileName) throws FileNotFoundException;

    void readGrid(String fileName) throws FileNotFoundException;

    void displayVertices();

    void allPairsReachability();  // Warshall's Algorithm
}

interface Floyd {

    int getCost(int from, int to);

    int getCost(String from, String to);

    void allPairsWeighted();
}

class AdjMat implements AdjacencyMatrix, Warshall { //, Floyd

    private int[][] grid = null;   //adjacency matrix representation
    private Map<String, Integer> vertices = null;   // name maps to index (for Warshall & Floyd)
    /*for Warshall's Extension*/ ArrayList<String> nameList = null;  //reverses the map, index-->name

    public AdjMat(int size) {
        grid = new int[size][size];
        vertices = new TreeMap<>();
    }

    public void addEdge(int source, int target) {
        grid[source][target] = 1;
    }

    public void removeEdge(int source, int target) {
        grid[source][target] = 0;
    }

    //returns true if a direct path from -> to exists; false otherwise
    public boolean isEdge(int from, int to) {
        return grid[from][to] == 1;
    }

    //returns the grid as a String
    public String toString() {
        String t = "";
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                t += grid[r][c] + "  ";
            }
            t += "\n";
        }
        return t;
    }

    public int edgeCount() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1)
                    count++;
            }
        }
        return count;
    }

    //returns the list of neighboring vertices
    public List<Integer> getNeighbors(int source) {

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < grid[source].length; i++) {
            if (grid[source][i] == 1)
                list.add(i);
        }
        return list;
    }

    public boolean isEdge(String from, String to) {
        if(!vertices.containsKey(from) || !vertices.containsKey(to))
            return false;

        return isEdge(vertices.get(from), vertices.get(to));
    }

    public Map<String, Integer> getVertices() {
        return this.vertices;
    }

    //fills the map with names from the text file
    public void readNames(String fileName) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(fileName));
        int size = reader.nextInt();

        for (int i = 0; i < size; i++) {
            vertices.put(reader.next(), i);
        }
    }

    //copies the grid from the text file into the grid field
    public void readGrid(String fileName) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(fileName));
        reader.nextInt(); // skip size

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = Integer.parseInt(reader.next());
            }
        }
    }

    //prints the vertices in the graph
    public void displayVertices() {
        for (String t : vertices.keySet()) {
            System.out.println(vertices.get(t) + "-" + t);
        }
        System.out.println();
    }

    //creates a reachability matrix using Warshall's algorithm
    public void allPairsReachability() {
        int size = grid.length;

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (grid[i][k] + grid[k][j] == 2)
                        grid[i][j] = 1;
                }
            }
        }
    }  // Warshall's Algorithm

    //returns the list of all reachable cities from String from
    public List<String> getReachables(String from) {

        if(!vertices.containsKey(from))
            return null;

        nameList = new ArrayList<>();
        ArrayList<String> reach = new ArrayList<>();

        for(String t: vertices.keySet()) { // reverse the map
            nameList.add(vertices.get(t), t);
        }

        for(int i = 0; i < grid.length; i++) {
            if(grid[vertices.get(from)][i] == 1)
                reach.add(nameList.get(i));
        }
        return reach;
    }

} // AdjMat

public class P6VeljkoCvetkovicAdjMatProgram {
    public static void main(String[] args) {

    }
}

/* Program output - Warshall's Algorithm

Warshall's Algorithm! Enter file of names: cities
Enter file of the matrix: citymatrix
Adjacency Matrix
0  0  0  0  0  0  0  1
0  0  0  1  0  0  0  0
0  0  0  0  0  1  0  1
0  0  0  0  0  1  0  1
1  0  0  0  0  0  0  0
0  1  0  1  0  0  0  0
0  0  0  0  0  1  1  0
1  0  0  0  1  0  0  0

Number of Edges: 13

0-Pendleton
1-Pensacola
2-Peoria
3-Phoenix
4-Pierre
5-Pittsburgh
6-Princeton
7-Pueblo

Reachability Matrix
1  0  0  0  1  0  0  1
1  1  0  1  1  1  0  1
1  1  0  1  1  1  0  1
1  1  0  1  1  1  0  1
1  0  0  0  1  0  0  1
1  1  0  1  1  1  0  1
1  1  0  1  1  1  1  1
1  0  0  0  1  0  0  1

Number of Edges: 40

Is it reachable?  Enter name of start city (-1 to exit): Pittsburgh
Enter name of end city: Peoria
false

Is it reachable?  Enter name of start city (-1 to exit): Pittsburgh
Enter name of end city: Phoenix
true

Is it reachable?  Enter name of start city (-1 to exit): -1

================== EXTENSION ==================
List of every city's reachable cities:
Pendleton--> [Pendleton, Pierre, Pueblo]
Pensacola--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
Peoria--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
Phoenix--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
Pierre--> [Pendleton, Pierre, Pueblo]
Pittsburgh--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
Princeton--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Princeton, Pueblo]
Pueblo--> [Pendleton, Pierre, Pueblo]

List the reachable cities from: Pendleton
[Pendleton, Pierre, Pueblo]

List the reachable cities from: Pierre
[Pendleton, Pierre, Pueblo]

List the reachable cities from: Princeton
[Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Princeton, Pueblo]

List the reachable cities from: -1

Process finished with exit code 0

 */