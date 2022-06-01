/*********************************************************************************
 NAME: Veljko Cvetkovic
 PERIOD: 6
 DATE SUBMITTED: 01/11/22
 DUE DATE: n/a
 ASSIGNMENT: Maze Lab with a Queue
 PURPOSE OF THE LAB: To find and display the solution of a maze, in addition to
 displaying the number of steps it took to solve the maze if the user wishes, using a queue structure.
 MISTAKES:
 1. Using a boolean field in the point class to keep track of whether a point
 has been visited (which didn't work since a new point is created each time and therefore can't
 access the actual points visited status at a certain set of coordinates).
 2. Changing visited locations to a temporary character (this proved inefficient to revert when the exit was found).
 NEW CONCEPTS LEARNED: I learned how to use a queue to enqueue all possible choices at a given point, and how to
 traverse the queue to find the true path. I also learned how to "backtrack" to find the true path by going back
 in the chain of created points that made up the true path.
 HOW I FEEL ABOUT THIS LAB: I think I prefer the recursive solution to this lab, however, using a queue is also
 a nice way to do it. With a queue, there has to be an additional structure to keep track of a points visited
 status unless the maze is made up of Point objects initially, which would allow each one to store its own
 visited status. However, this would mean that each space in the maze would be a Point object which would
 be less memory efficient.
 CREDITS: n/a
 STUDENTS WHOM I HELPED: none
 */

import java.util.*;
import java.io.*;

public class P6VeljkoCvetkovicMazeLabQueues {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        char[][] retArr;
        Maze m;

        System.out.println();

        for (; ; ) {
            System.out.print("\nEnter the maze's filename (file extension not needed): ");

            retArr = buildCharArr(sc.next()); // transfer file to 2D array

            m = new Maze(retArr); // new maze with maze from file
            System.out.println("Maze: \n");
            m.display();

            System.out.println("\nWhat do you want to do (choose 1, 2, or 3):");
            System.out.println("   1: Mark only the correct path.");
            System.out.println("   2: Mark only the correct path, and display the count of STEPs.");
            System.out.println("   3: Exit");

            m.solve(sc.nextInt());

            m.display();
        } // for
    } // main

    /**
     * precondition: take in a filename (without .txt), called in main method
     * postcondition: return a char[][]
     */
    public static char[][] buildCharArr(String fileName) throws FileNotFoundException {

        Scanner reader = new Scanner(new File(fileName + ".txt"));
        char[][] mazeBoard = new char[reader.nextInt()][reader.nextInt()]; //grid built using bounds from file
        for (int i = 0; i < mazeBoard.length; i++) {

            String row = reader.next();
            mazeBoard[i] = row.toCharArray(); // process each line of the file
        }
        return mazeBoard;
    } // buildCharArr

    /**
     * precondition: none
     * postcondition: create a file with a new randomly generated maze (method not used)
     */
    private static void transfer2DGridToFile(char[][] m) throws FileNotFoundException {
        System.out.print("Enter the name of the output file? \nUse 'txt' as the file extension: ");
        Scanner input = new Scanner(System.in);
        String name = input.next();
        File outputFile = new File(name);
        if (outputFile.exists()) // doesn't override a file that already exists
        {
            System.out.println(name + "already exists");
            System.exit(1);
        }

        PrintWriter pw = new PrintWriter(outputFile);

        System.out.println("Enter the dimensions (row and column) of the random maze (separated the numbers with a space): ");
        pw.println(input.next() + " " + input.next());

        // transfer the 2D grid to the .txt text file
        for (char[] row : m) {
            pw.println(row);
        }
        pw.close();

    } // transfer2DGridToFile

}  // MazeDriver


class Maze {

    //Constants
    private final char WALL = 'W'; // represents bound
    private final char DOT = '.'; // possible step
    private final char START = 'S'; // starting position
    private final char EXIT = 'E'; // maze exit
    private final char STEP = '*'; // part of True path
    //fields
    private char[][] maze; // maze grid
    private boolean available[][]; // keep track of visited choices
    private int startRow, startCol;  // store where the start location is
    private boolean S_Exists = false, E_Exists = false;

    /**
     * Constructor
     * Initializes all the fields of a Maze object: maze, startRow, startCol; grid of visited points
     */
    public Maze(char[][] inCharArr) {

        maze = new char[inCharArr.length][inCharArr[0].length]; // new grid
        available = new boolean[inCharArr.length][inCharArr[0].length];

        for (int i = 0; i < inCharArr.length; i++) {
            for (int j = 0; j < inCharArr[0].length; j++) {
                maze[i][j] = inCharArr[i][j];
                available[i][j] = true;
                if (inCharArr[i][j] == START) {
                    startRow = i; // row containing START
                    startCol = j; // column containing START
                }
            }
        }

    }// Maze

    /**
     * precondition: maze is not null
     * postcondition: prints the maze
     */
    public void display() {

        if (maze == null)
            return;
        for (int a = 0; a < maze.length; a++) {
            for (int b = 0; b < maze[0].length; b++) {
                System.out.print(maze[a][b]);
            }
            System.out.println(""); // new line
        }
        System.out.println("");
    }  // display


    /**
     * precondition: 1 <= n <= 3
     * postcondition: calls method that user chose or exits based on user choice
     */
    public void solve(int n) {

        final int FIND_PATH = 1; // markTheCorrectPath
        final int FIND_PATH_AND_COUNT_PATH_LENGTH = 2; // markCorrectPathAndCountStars
        final int QUIT = 3; // exit

        if (n == FIND_PATH) {
            if (!markTheCorrectPath(startRow, startCol))
                System.out.println("No Path found!");
        } else if (n == FIND_PATH_AND_COUNT_PATH_LENGTH) {
            if (!markCorrectPathAndCountStars(startRow, startCol, 0))
                System.out.println("No Path found!");

        } else if (n == QUIT) {
            System.out.println("Goodbye!\n");
            System.exit(0);
        } else System.out.println("Invalid choice\n");
    }  // solve

    /**
     * precondition: method called in the solve method
     * postcondition: marks the solution to the maze
     */
    private boolean markTheCorrectPath(int r, int c) {

        Queue<Point> q = new LinkedList<Point>();

        q.add(new Point(r, c, null));
        while (!q.isEmpty()) {
            Point visited = q.remove();

            if (maze[visited.getX()][visited.getY()] == EXIT) {
                //backtrack and mark correct path
                while (maze[visited.getParent().getX()][visited.getParent().getY()] != START) {
                    visited = visited.getParent();
                    maze[visited.getX()][visited.getY()] = '*';
                }
                return true;
            }
            enqueue(q, visited);
        }
        return false;
    } // markTheCorrectPath

    /**
     * precondition: method called in the solve method
     * postcondition: marks the solution to the maze and displays the number of steps to get there (not including E or S as steps)
     */
    private boolean markCorrectPathAndCountStars(int r, int c, int count) {

        Queue<Point> q = new LinkedList<Point>();

        q.add(new Point(r, c, null));
        while (!q.isEmpty()) {
            Point visited = q.remove();

            if (maze[visited.getX()][visited.getY()] == EXIT) {
                // backtrack and mark correct path
                while (maze[visited.getParent().getX()][visited.getParent().getY()] != START) {
                    visited = visited.getParent();
                    maze[visited.getX()][visited.getY()] = '*';
                    count++;
                }
                System.out.println("\nCount of STEPs: " + count); // print count when exit is found
                return true;
            }
            enqueue(q, visited);
        }
        return false;
    } // markCorrectPathAndCountStars

    /**
     * precondition: called in enqueue method
     * postcondition: returns true if the point with the provided coordinates is a valid move and has not been
     * visited; false otherwise
     */
    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] != WALL && available[x][y];
    }

    /**
     * precondition: queue and p are not null
     * postcondition: enqueue all valid points surrounding the provided point
     */
    private void enqueue(Queue<Point> queue, Point p) {

        int[][] options = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] op : options) {
            if (isValid(p.getX() + op[0], p.getY() + op[1])) {
                Point temp = new Point(p.getX() + op[0], p.getY() + op[1], p);
                queue.add(temp);
                available[temp.getX()][temp.getY()] = false;
            }
        }
    }
}

class Point {

    private int x; // x-coordinate
    private int y; // y-coordinate
    private Point parent; // previous Point

    public Point() {
        x = -1;
        y = -1;
        parent = null;
    }

    public Point(int x, int y, Point parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point getParent() {
        return this.parent;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

}

/* Program Output

Enter the maze's filename (file extension not needed): maze1
Maze:

WWWWWWWW
W....W.W
WW.WW..W
W....W.W
W.W.WW.E
S.W.WW.W
WW.....W
WWWWWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
1
WWWWWWWW
W....W.W
WW.WW..W
W***.W.W
W*W*WW*E
S*W*WW*W
WW.****W
WWWWWWWW


Enter the maze's filename (file extension not needed): maze1
Maze:

WWWWWWWW
W....W.W
WW.WW..W
W....W.W
W.W.WW.E
S.W.WW.W
WW.....W
WWWWWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
2

Count of STEPs: 13
WWWWWWWW
W....W.W
WW.WW..W
W***.W.W
W*W*WW*E
S*W*WW*W
WW.****W
WWWWWWWW


Enter the maze's filename (file extension not needed): maze2
Maze:

WWWSWWWWWW
W....W.W.W
WWWW.....W
W...W.WW.W
W.W....W.W
WEWWWWWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
1
WWWSWWWWWW
W..**W.W.W
WWWW**...W
W***W*WW.W
W*W***.W.W
WEWWWWWWWW


Enter the maze's filename (file extension not needed): maze2
Maze:

WWWSWWWWWW
W....W.W.W
WWWW.....W
W...W.WW.W
W.W....W.W
WEWWWWWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
2

Count of STEPs: 12
WWWSWWWWWW
W..**W.W.W
WWWW**...W
W***W*WW.W
W*W***.W.W
WEWWWWWWWW


Enter the maze's filename (file extension not needed): maze3
Maze:

..WW
W..S
E.WW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
1
..WW
W**S
E*WW


Enter the maze's filename (file extension not needed): maze3
Maze:

..WW
W..S
E.WW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
2

Count of STEPs: 3
..WW
W**S
E*WW


Enter the maze's filename (file extension not needed): maze5NoPath
Maze:

WWEWW
W...W
W.W.X
WWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
1
No Path found!
WWEWW
W...W
W.W.X
WWWWW


Enter the maze's filename (file extension not needed): maze5NoPath
Maze:

WWEWW
W...W
W.W.X
WWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
2
No Path found!
WWEWW
W...W
W.W.X
WWWWW


Enter the maze's filename (file extension not needed): maze6NoPath
Maze:

WWWWW
W...W
W.W.W
S.WWE
WWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
1
No Path found!
WWWWW
W...W
W.W.W
S.WWE
WWWWW


Enter the maze's filename (file extension not needed): maze6NoPath
Maze:

WWWWW
W...W
W.W.W
S.WWE
WWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
2
No Path found!
WWWWW
W...W
W.W.W
S.WWE
WWWWW


Enter the maze's filename (file extension not needed): maze1
Maze:

WWWWWWWW
W....W.W
WW.WW..W
W....W.W
W.W.WW.E
S.W.WW.W
WW.....W
WWWWWWWW


What do you want to do (choose 1, 2, or 3):
   1: Mark only the correct path.
   2: Mark only the correct path, and display the count of STEPs.
   3: Exit
3
Goodbye!


Process finished with exit code 0

 */