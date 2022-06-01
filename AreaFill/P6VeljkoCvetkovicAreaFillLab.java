/***********************************************************************************************************************************************
 * Name: Veljko Cvetkovic
 * Period: 6
 * Name of the Lab: Area Fill
 * Purpose of the Program: To replace the area of a grid containing a certain character with a new character.
 * Due Date: Sunday, September 26th 2021
 * Date Submitted: Sunday, September 26th 2021
 * What I learned: 
 * 1. How to use backtracking to make elegant and efficient solutions to recursive problems.
 * 2. How to read chars from the console with a Scanner object.
 * How I feel about this lab: This lab was very interesting and I think I learned a lot from it.
 * 
 * What I wonder: I wonder how this lab could be done if the original grid couldn't be changed.
 *
 * Student(s) who helped me (to what extent): n/a
 * Student(s) whom I helped (to what extent): n/a
 *************************************************************************************************************************************************/

   
import java.util.Scanner;
import java.io.*;
public class P6VeljkoCvetkovicAreaFillLab{

   public static char[][] grid = null;
   
   public static void main(String[] args){
   
      Scanner sc = new Scanner(System.in);
      System.out.print("Filename: ");
      String filename = sc.next();
      
      try{
         grid = read(filename + ".txt");
      }catch(FileNotFoundException e){
         System.out.println("This file doesn't exist");
         System.exit(1);
      }
      
      char userChoice;
      do{
         display(grid);
         System.out.print("\nEnter ROW COL to fill from: ");
         int row = sc.nextInt();
         int col = sc.nextInt(); 
         System.out.print("Enter a fill character: ");
         char fillChar = sc.next().charAt(0);
         fill(grid, row, col, grid[row][col], fillChar);
         display(grid);
         System.out.println("\nPress any key to continue or q to quit");
         userChoice = sc.next().charAt(0);
      }while(userChoice != 'q');
      sc.close();
   }
   
   public static char[][] read(String filename)throws FileNotFoundException{
   
      Scanner sc = new Scanner(new File(filename));
      char[][] board = new char[sc.nextInt()][sc.nextInt()];
      for(int i = 0; i < board.length; i++){
         String row = sc.next();
         board[i] = row.toCharArray();
      }
      return board;
   }
   
   public static void display(char[][] g){
              
      for(int i = 0; i < g.length; i++){
         for(int j = 0; j < g[i].length; j++){
            System.out.print(g[i][j]);
         }
         System.out.println();
      }
   }
   
   /**
   * pre: method called in main method 
   * post: modifies char[][] g and replaces characters of the 
   * index indicated on the grid with a *
   * @param g
   * @param r
   * @param c
   * @param ch
   * @param fillChar - char user wants to fill with
   */

   public static void fill(char[][] g, int r, int c, char ch, char fillChar){ //recursive method
       
      if(r >= g.length || c >= g[0].length || r < 0 || c < 0 || ch == fillChar)
         return; //check if out of bounds
      
      else if(g[r][c] == ch){ //check if character at current position should be filled
      
         g[r][c] = fillChar;
            
         //Up
         fill(g, r+1, c, ch, fillChar);
         //Down
         fill(g, r-1, c, ch, fillChar);
         //Left
         fill(g, r, c-1, ch, fillChar); 
         //Right
         fill(g, r, c+1, ch, fillChar); 
      }
      
   }// fill
}

/* Program Output

Filename: area
xxxx............................
...xx...........................
..xxxxxxxxxxxx..................
..x.........xxxxxxx.............
..x...........0000xxxx..........
..xxxxxxxxxxxx0..000............
..xxxxxxxxx...0...00.....0000000
..........xx.......0000000000000
.....xxxxxxxxx........0.........
....xx.................00000....
....xx.....................00...
.....xxxxxxxxxxxxxxxxxx....00...
......................xx...00...
.......................xxxx00000
............................0000

Enter ROW COL to fill from: 0 0
Enter a fill character: ^
^^^^............................
...^^...........................
..^^^^^^^^^^^^..................
..^.........^^^^^^^.............
..^...........0000^^^^..........
..^^^^^^^^^^^^0..000............
..^^^^^^^^^...0...00.....0000000
..........^^.......0000000000000
.....^^^^^^^^^........0.........
....^^.................00000....
....^^.....................00...
.....^^^^^^^^^^^^^^^^^^....00...
......................^^...00...
.......................^^^^00000
............................0000

Press any key to continue or q to quit
c
^^^^............................
...^^...........................
..^^^^^^^^^^^^..................
..^.........^^^^^^^.............
..^...........0000^^^^..........
..^^^^^^^^^^^^0..000............
..^^^^^^^^^...0...00.....0000000
..........^^.......0000000000000
.....^^^^^^^^^........0.........
....^^.................00000....
....^^.....................00...
.....^^^^^^^^^^^^^^^^^^....00...
......................^^...00...
.......................^^^^00000
............................0000

Enter ROW COL to fill from: 4 14
Enter a fill character: p
^^^^............................
...^^...........................
..^^^^^^^^^^^^..................
..^.........^^^^^^^.............
..^...........pppp^^^^..........
..^^^^^^^^^^^^p..ppp............
..^^^^^^^^^...p...pp.....ppppppp
..........^^.......ppppppppppppp
.....^^^^^^^^^........p.........
....^^.................00000....
....^^.....................00...
.....^^^^^^^^^^^^^^^^^^....00...
......................^^...00...
.......................^^^^00000
............................0000

Press any key to continue or q to quit
q

 ----jGRASP: operation complete.

Filename: area2
..........00
...0....0000
...000000000
0000.....000
............
..#########.
..#...#####.
......#####.
...00000....

Enter ROW COL to fill from: 1 2
Enter a fill character: %
%%%%%%%%%%00
%%%0%%%%0000
%%%000000000
0000.....000
............
..#########.
..#...#####.
......#####.
...00000....

Press any key to continue or q to quit
c
%%%%%%%%%%00
%%%0%%%%0000
%%%000000000
0000.....000
............
..#########.
..#...#####.
......#####.
...00000....

Enter ROW COL to fill from: 5 3
Enter a fill character: >
%%%%%%%%%%00
%%%0%%%%0000
%%%000000000
0000.....000
............
..>>>>>>>>>.
..>...>>>>>.
......>>>>>.
...00000....

Press any key to continue or q to quit
q

 ----jGRASP: operation complete.

Filename: area3
+++
@+@
@+@
@@@

Enter ROW COL to fill from: 3 2
Enter a fill character: r
+++
r+r
r+r
rrr

Press any key to continue or q to quit
x
+++
r+r
r+r
rrr

Enter ROW COL to fill from: 2 1
Enter a fill character: ?
???
r?r
r?r
rrr

Press any key to continue or q to quit
q

 ----jGRASP: operation complete.

*/