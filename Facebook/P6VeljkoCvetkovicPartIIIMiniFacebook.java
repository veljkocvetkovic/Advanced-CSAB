import java.util.*;
import java.io.*;

public class P6VeljkoCvetkovicPartIIIMiniFacebook {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of people you would like to generate: ");
        int num = Integer.parseInt(input.next());

        for (int k = 0; k < 2; k++) {

            if(k == 0)
                System.out.println("HashMap Implementation: \n");
            else
                System.out.println("Graph Implementation: \n");

            // P
            PrintWriter pw = new PrintWriter(new File("P6VeljkoCvetkovicNameOnly.txt"));
            for (int i = 0; i < num; i++) {
                String name = "";
                for (int j = 0; j < 5; j++) {
                    char random = (char) ((int) (Math.random() * 26) + 'a');
                    name += Character.toString(random);
                }
                pw.println("P " + name);
            }
            pw.close();

            //timing
            long initTime = System.currentTimeMillis();
            Scanner reader = new Scanner(new File("P6VeljkoCvetkovicNameOnly.txt"));
            while (reader.hasNext()) {
                String[] t = reader.nextLine().split(" ");
                String name = t[1];
                if(k == 0)
                    P6VeljkoCvetkovicMicroFB.pCommand(name);
                else
                    P6VeljkoCvetkovicMicroFBAdjList.pCommand(name);
            }
            long finalTime = System.currentTimeMillis();
            System.out.println("P time: " + (finalTime - initTime) + " Milliseconds");

            // F
            reader = new Scanner(new File("P6VeljkoCvetkovicNameOnly.txt"));
            pw = new PrintWriter(new File("P6VeljkoCvetkovicFriendOnly.txt"));
            while (reader.hasNext()) {
                String[] n = reader.nextLine().split(" ");
                String name = n[1];
                Scanner lineReader = new Scanner(new File("P6VeljkoCvetkovicNameOnly.txt"));
                while (lineReader.hasNext()) {
                    String[] f = lineReader.nextLine().split(" ");
                    String friend = f[1];
                    pw.println("F " + name + " " + friend);
                }
            }
            pw.close();

            //timing
            initTime = System.currentTimeMillis();
            reader = new Scanner(new File("P6VeljkoCvetkovicFriendOnly.txt"));
            while (reader.hasNext()) {
                String[] t = reader.nextLine().split(" ");
                String name = t[1];
                String friend = t[2];
                if(k == 0)
                    P6VeljkoCvetkovicMicroFB.fCommand(name, friend);
                else
                    P6VeljkoCvetkovicMicroFBAdjList.fCommand(name, friend);
            }
            finalTime = System.currentTimeMillis();
            System.out.println("F time: " + (finalTime - initTime) + " Milliseconds");

            // U
            reader = new Scanner(new File("P6VeljkoCvetkovicNameOnly.txt"));
            pw = new PrintWriter(new File("P6VeljkoCvetkovicUnFriendOnly.txt"));
            while (reader.hasNext()) {
                String[] n = reader.nextLine().split(" ");
                String name = n[1];
                Scanner lineReader = new Scanner(new File("P6VeljkoCvetkovicNameOnly.txt"));
                while (lineReader.hasNext()) {
                    String[] f = lineReader.nextLine().split(" ");
                    String friend = f[1];
                    pw.println("U " + name + " " + friend);
                }
            }
            pw.close();

            //timing
            initTime = System.currentTimeMillis();
            reader = new Scanner(new File("P6VeljkoCvetkovicUnFriendOnly.txt"));
            while (reader.hasNext()) {
                String[] t = reader.nextLine().split(" ");
                String name = t[1];
                String friend = t[2];
                if(k == 0)
                    P6VeljkoCvetkovicMicroFB.uCommand(name, friend);
                else
                    P6VeljkoCvetkovicMicroFBAdjList.uCommand(name, friend);
            }
            finalTime = System.currentTimeMillis();
            System.out.println("U time: " + (finalTime - initTime) + " Milliseconds\n");
        }
        P6VeljkoCvetkovicMicroFB.main(null); // let user continue to enter commands
    }
}

/* Program Output

Enter the number of people you would like to generate:
500
HashMap Implementation:

P time: 13 Milliseconds
F time: 205 Milliseconds
U time: 414 Milliseconds

Graph Implementation:

P time: 5 Milliseconds
F time: 177 Milliseconds
U time: 424 Milliseconds

Help Page
P <name> - Create a person with the specified name
F <name1> <name2> - Record that the two specified people are friends
U <name1> <name2> - Record that the two specified people are no longer friends
L <name> - Print out the friends of the specified person
Q <name1> <name2> - Check whether two people are friends
X - terminate the program

Would you like to randomly generate names? Enter Y/N
N
Enter Command: X

Process finished with exit code 0

 */