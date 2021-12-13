/*********************************************************************************
 NAME: Veljko Cvetkovic
 PERIOD: 6
 DATE SUBMITTED: 10/15/21
 DUE DATE: 10/16/21
 ASSIGNMENT: Maze Lab

 PURPOSE OF THE LAB: To find and display the solution of a maze, in addition to
 displaying the number of steps it took to solve the maze if the user wishes.

 MISTAKES:
 1. Calling the markTheCorrectPath method in the markCorrectPathAndCountSteps method.
 2. Changing the character 'S' at the start position to a * when solving the maze.

 NEW CONCEPTS LEARNED: I learned how to OR recursive calls and use backtracking to process
 them as they come back, in addition to how to find the shortest path in a maze. I also learned
 that writing an initialization statement inside a try-catch block will result in a compiler
 error because the data field or structure may not be initialized, unless a runtime exception is
 thrown in the catch block which will ensure that the code segment will not proceed with an uninitialized
 field or data structure.

 HOW I FEEL ABOUT THIS LAB: I thought this lab was interesting, but there is probably a much faster way to reach the
 end of the maze using more efficient algorithms. I have heard of Dijkstra's Algorithm and breadth-first search, but
 I do not know exactly how they work and how much more efficient they would be.

 CREDITS: n/a
 STUDENTS WHOM I HELPED: n/a
 */

import javax.swing.*;
import java.util.*;
import java.io.*;
public class P6VeljkoCvetkovicMazeLab{

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        char[][] retArr;
        Maze m;

        System.out.println();

        for (;;)
        {
            System.out.print("\nEnter the maze's filename (file extension not needed): ");

            retArr = buildCharArr(sc.next()); // transfer file to 2D array

            m = new Maze(retArr); // new maze with maze from file
            System.out.println ("Maze: \n");
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
     precondition: take in a filename (without .txt), called in main method
     postcondition: return a char[][]
     */
    public static char[][] buildCharArr(String fileName) throws FileNotFoundException {

        Scanner reader = new Scanner(new File(fileName + ".txt"));
        char[][] mazeBoard = new char[reader.nextInt()][reader.nextInt()]; //grid built using bounds from file
        for(int i = 0; i < mazeBoard.length; i++){

            String row = reader.next();
            mazeBoard[i] = row.toCharArray(); // process each line of the file
        }
        return mazeBoard;
    } // buildCharArr

    /**
     precondition: none
     postcondition: create a file with a new randomly generated maze (method not used)
     */
    private static void transfer2DGridToFile (char [][] m) throws FileNotFoundException
    {
        System.out.print ("Enter the name of the output file? \nUse 'txt' as the file extension: ");
        Scanner input = new Scanner (System.in);
        String name = input.next();
        File outputFile = new File (name);
        if (outputFile.exists()) // doesn't override a file that already exists
        {
            System.out.println (name + "already exists");
            System.exit(1);
        }

        PrintWriter pw = new PrintWriter (outputFile);

        System.out.println ("Enter the dimensions (row and column) of the random maze (separated the numbers with a space): ");
        pw.println(input.next() + " " + input.next());

        // transfer the 2D grid to the .txt text file
        for (char [] row : m)
        {
            pw.println (row);
        }
        pw.close();

    } // transfer2DGridToFile

}  // MazeDriver


class Maze{

    //Constants
    private final char WALL = 'W'; // represents bound
    private final char DOT = '.'; // possible step
    private final char START = 'S'; // starting position
    private final char EXIT = 'E'; // maze exit
    private final char STEP = '*'; // part of True path
    //fields
    private char[][] maze; // maze grid
    private int startRow, startCol;  // store where the start location is
    private boolean S_Exists = false, E_Exists = false;

    /**
     * Constructor
     * Initializes all the fields of a Maze object: maze, startRow, startCol
     */
    public Maze(char[][] inCharArr){

        maze = new char[inCharArr.length][inCharArr[0].length]; // new grid

        for (int i = 0; i < inCharArr.length; i++) {
            for (int j = 0; j < inCharArr[0].length; j++) {
                maze[i][j] = inCharArr[i][j];
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
    public void display(){

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
    public void solve(int n){

        final int FIND_PATH = 1; // markTheCorrectPath
        final int FIND_PATH_AND_COUNT_PATH_LENGTH = 2; // markCorrectPathAndCountStars
        final int QUIT = 3; // exit

        if(n == FIND_PATH)
        {
            if (!markTheCorrectPath(startRow, startCol))
                System.out.println ("No Path found!");
        }
        else if(n == FIND_PATH_AND_COUNT_PATH_LENGTH)
        {
            if (!markCorrectPathAndCountStars(startRow, startCol, 0))
                System.out.println ("No Path found!");

        }
        else if (n == QUIT)
        {
            System.out.println("Goodbye!\n");
            System.exit (0);
        }

        else System.out.println("Invalid choice\n");
    }  // solve

    /**
     precondition: method called in the solve method
     postcondition: marks the solution to the maze
     */
    /*  Recur until you find E, then mark the True path */
    private boolean markTheCorrectPath(int r, int c){

        if(r >= maze.length || r < 0 || c >= maze[0].length || c < 0 || maze[r][c] == WALL)
            return false; // check if out of bounds

        if(maze[r][c] == EXIT)
            return true; // reached the exit

        if(maze[r][c] == DOT || maze[r][c] == START){
            if (maze[r][c] == DOT)
                maze[r][c] = 'o'; // change only dots to temporary character 'o'

            // recursive calls OR'd
            if (markTheCorrectPath(r + 1, c) || markTheCorrectPath(r - 1, c) || markTheCorrectPath(r, c + 1) || markTheCorrectPath(r, c - 1)){
                if (maze[r][c] != START) {
                    maze[r][c] = STEP; // change temporary character to * if part of true path
                }
                return true; // true path exists
            }
            else if(maze[r][c] != START)
                maze[r][c] = DOT; // character changed back to dot if not part of true path
        }
        return false;
    } // markTheCorrectPath

    /**
     precondition: method called in the solve method
     postcondition: marks the solution to the maze and displays the number of steps to get there (not including E or S as steps)
     */
    private boolean markCorrectPathAndCountStars(int r, int c, int count){

        if(r >= maze.length || r < 0 || c >= maze[0].length || c < 0 || maze[r][c] == WALL)
            return false; // check if out of bounds

        if(maze[r][c] == EXIT){
            System.out.println("\nCount of STEPs: " + count); // print count when exit is found
            return true; // reached the exit
        }

        if(maze[r][c] == DOT || maze[r][c] == START){
            if(maze[r][c] == DOT) {
                maze[r][c] = 'o'; // change only dots to temporary character 'o'
                count++; // increase step count
            }

            // recursive calls OR'd
            if(markCorrectPathAndCountStars(r + 1, c, count) ||
                    markCorrectPathAndCountStars(r - 1, c, count) ||
                    markCorrectPathAndCountStars(r, c + 1, count) ||
                    markCorrectPathAndCountStars(r, c - 1, count))
            {
                if(maze[r][c] != START){
                    maze[r][c] = STEP; // change temporary character to * if part of true path
                }
                return true; // true path exists
            }
            else if(maze[r][c] != START){
                maze[r][c] = DOT; // character changed back to dot if not part of true path
            }
        }
        return false;
    } // markCorrectPathAndCountStars

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
