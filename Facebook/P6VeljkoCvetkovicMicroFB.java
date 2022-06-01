/****************************
 * Name: Veljko Cvetkovic
 * Period: 6
 * Name of the Lab: Micro Facebook
 * Purpose of the Program: To simulate a Facebook-like program where users can be created
 * and where they can add or remove friends, and view friends of a person.
 * The program works everywhere...I think.
 *
 * What I learned:
 * 1. How to use multiple HashMaps to keep track of users and their friendship status for fast and efficient access.
 * 2. How to use the default command in a switch statement to run if a user doesn't enter one of the specified options.
 * 3. How to use the split method to separate the command name from the name of the user.
 * 4. How to write data to a file and then read from it an execute commands accordingly.
 * 5. How to make a functional, user-friendly program to simulate a real-world application of the coding
 * skills I learned this year.
 *
 *
 * How I feel about this lab: I feel very good about this lab, I thought it was very interesting, particularly the extensions
 * because it was interesting to see how much time all the commands took. I also think it was a good learning experience to
 * implement certain data structures to store information about and keep track of created users.
 *
 * What I wonder: I wonder how the actual version of Facebook is implemented, specifically which data structures
 * are used and how efficient they are in completing actions like create user and add friend. I also wonder
 * how Facebook is able to handle the large amount of users constantly being created and friendships being established.
 ***************************/

import java.util.*;
import java.io.*;
public class P6VeljkoCvetkovicMicroFB {

    private static HashMap<String, Person> nameToIndex = new HashMap<>(); // map name to Person object
    private static HashMap<String, Boolean> areConnected = new HashMap<>(); // map two name key to friendship status

    /**
     * precondition: called in main method
     * postcondition: create a new Person object and add them to the map of users
     */
    public static void pCommand(String name) {
        nameToIndex.put(name, new Person(name)); // making the same person again replaces the current person
    }

    /**
     * precondition: name != name2; name and name2 map to initialized Person objects
     * postcondition: users with names "name" and "name2" are added to each others friends lists; friendship status is set to true
     *
     * If user enters the name of a Person that doesn't exist, the program will
     * let them know that one of the names does not exist (in the map of created Person objects)
     */
    public static void fCommand(String name, String name2) throws NullPointerException {
        if(!name.equals(name2)) { // person cannot add themselves as a friend
            nameToIndex.get(name).getFriends().addFirst(nameToIndex.get(name2));
            nameToIndex.get(name2).getFriends().addFirst(nameToIndex.get(name));

            String twoPKey = generateTwoPKey(name, name2);
            areConnected.put(twoPKey, true);
        }
    }

    /**
     * precondition: called in main method
     * postcondition: remove the friendship between users with names "name" and "name2"
     *
     * If user enters the name of a Person that doesn't exist, the program will
     * let them know that one of the names does not exist (in the map of created Person objects)
     */
    public static void uCommand(String name, String name2) throws NullPointerException {
        nameToIndex.get(name).getFriends().remove(nameToIndex.get(name2));
        nameToIndex.get(name2).getFriends().remove(nameToIndex.get(name));

        String twoPKey = generateTwoPKey(name, name2);
        areConnected.put(twoPKey, false);
    }

    /**
     * precondition: called in main method
     * postcondition: print out the friends of a user with name "name"
     */
    public static void lCommand(String name) throws NullPointerException {
        System.out.print("\tFriends:");
        for(Person p: nameToIndex.get(name).getFriends()) {
            System.out.print(" " + p.getName());
        }
        System.out.println();
    }

    /**
     * precondition: called in main method
     * postcondition: print true if two people are friends; false otherwise
     */
    public static void qCommand(String name, String name2) {
        String twoPKey = generateTwoPKey(name, name2);

        if(areConnected.containsKey(twoPKey)) { // check if key exists
            if(areConnected.get(twoPKey))
                System.out.println("\tYes, they are friends");
        }
        else
            System.out.println("\tNo, they are not friends");
    }

    /**
     * precondition: called in main method
     * postcondition: gracefully end the program
     */
    public static void xCommand() {
        System.exit(0);
    }

    /**
     * precondition: private helper to generate the two-person key for the friendship status map
     * postcondition: return a String with the two-person key
     */
    private static String generateTwoPKey(String name, String name2) {
        String twoPKey = "";
        if(name.compareTo(name2) <= 0)
            twoPKey = name + "*" + name2;
        else if(name.compareTo(name2) > 0)
            twoPKey = name2 + "*" + name;

        return twoPKey;
    }

    /**
     * precondition: private helper to create P file (for Extension I)
     * postcondition: creates "num" random names and adds them to a file; prints to console if user chooses
     */
    private static void createFile(int num, boolean choice, String fileName) throws FileNotFoundException {
        File output = new File(fileName);
        PrintWriter pw = new PrintWriter(output);

        for(int i = 0; i < num; i++) {
            String name = "";
            for(int j = 0; j < 5; j++) {
                char random = (char)((int)(Math.random() * 26) + 'a'); // convert random int to ascii character
                name += Character.toString(random);
            }
            pw.println("P " + name); // add name to file
        }
        pw.close();

        Scanner reader = new Scanner(new File(fileName)); // Scanner for newly created file

        while(reader.hasNext()) {
            String lineInput = reader.nextLine();
            if(choice) // if user chose to print out the names
                System.out.println(lineInput);

            String[] t = lineInput.split(" ");
            pCommand(t[1]); // skip over P command character
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);

        System.out.println("Help Page");
        System.out.print("P <name> - Create a person with the specified name" +
                "\nF <name1> <name2> - Record that the two specified people are friends" +
                "\nU <name1> <name2> - Record that the two specified people are no longer friends" +
                "\nL <name> - Print out the friends of the specified person" +
                "\nQ <name1> <name2> - Check whether two people are friends" +
                "\nX - terminate the program\n");

        System.out.println("\nWould you like to randomly generate names? Enter Y/N");
        String text = input.next();

        if(text.equals("Y")) {
            System.out.print("Enter the number of names you would like to generate: ");
            int n = input.nextInt();
            System.out.println("Would you like to print the randomly generated names? Y/N");
            String s = input.next();
            boolean flag = s.equals("Y");
            createFile(n, flag, "P6VeljkoCvetkovicPerson.txt");
        }

        while(true) {
            System.out.print("Enter Command: ");
            char choice = input.next().charAt(0);
            switch(choice) {
                case 'P':
                    pCommand(input.next());
                    break;
                case 'F':
                    try {
                        fCommand(input.next(), input.next());
                    } catch(NullPointerException e) { // if user attempts to friend people that don't exist
                        System.out.println("User Not Found");
                    }
                    break;
                case 'U':
                    try {
                        uCommand(input.next(), input.next());
                    } catch(NullPointerException e) { // if user attempts to unfriend people that don't exist
                        System.out.println("User Not Found");
                    }
                    break;
                case 'L':
                    try {
                        lCommand(input.next());
                    } catch(NullPointerException e) { // if user enters a name that doesn't correspond to existing Person
                        System.out.println("User Not Found");
                    }
                    break;
                case 'Q':
                    qCommand(input.next(), input.next());
                    break;
                case 'X':
                    xCommand();
                default: // if user choice is not a valid command, or they enter input incorrectly
                    System.out.println("Invalid Choice. Enter a new choice. ");
                    break;
            }
        }
    }
}

class Person {

    private String name;
    private LinkedList<Person> friends;

    /**
     * Constructor
     * creates a new Person object
     */
    public Person(String name) {
        friends = new LinkedList<>();
        this.name = name;
    }

    /**
     * precondition: accessor
     * postcondition: returns the name of the Person
     */
    public String getName() {
        return name;
    }

    /**
     * precondition: accessor
     * postcondition: returns the LinkedList of Person objects with the Person's friends
     */
    public LinkedList<Person> getFriends() {
        return friends;
    }
}

/* Program Output

Help Page
P <name> - Create a person with the specified name
F <name1> <name2> - Record that the two specified people are friends
U <name1> <name2> - Record that the two specified people are no longer friends
L <name> - Print out the friends of the specified person
Q <name1> <name2> - Check whether two people are friends
X - terminate the program

Would you like to randomly generate names? Enter Y/N
Y
Enter the number of names you would like to generate: 10
Would you like to print the randomly generated names? Y/N
Y
P danja
P gggwo
P hhfmo
P okeqx
P ttode
P jmuxp
P amhcl
P ceeqe
P gvnxc
P vwxgw

Enter Command: F danja gggwo
Enter Command: Q danja gggwo
	Yes, they are friends
Enter Command: P Sam
Enter Command: P Liza
Enter Command: P Mark
Enter Command: P Amy
Enter Command: F Liza Amy
Enter Command: F Liza Mark
Enter Command: F Amy Sam
Enter Command: L Amy
	Friends: Sam Liza
Enter Command: L Sam
	Friends: Amy
Enter Command: U Liza Amy
Enter Command: L Amy
	Friends: Sam
Enter Command: Q Liza Mark
	Yes, they are friends
Enter Command: P Edmund
Enter Command: P Esther
Enter Command: F Edmund Mark
Enter Command: F Mark Esther
Enter Command: L Mark
	Friends: Esther Edmund Liza
Enter Command: X

Process finished with exit code 0

 */