import java.util.*;
import java.io.*;

    class PersonVertex {

        private final String name;
        private ArrayList<PersonVertex> adjacencies;

        public PersonVertex(String v) {
            name = v;
            adjacencies = new ArrayList<PersonVertex>();
        }

        public String getName() {
            return this.name;
        }

        public ArrayList<PersonVertex> getAdjacencies() {
            return this.adjacencies;
        }

        public void addAdjacent(PersonVertex v) {
            adjacencies.add(v);
        }
    }

    public class P6VeljkoCvetkovicMicroFBAdjList {

        private static ArrayList<PersonVertex> vertices = new ArrayList<>();
        private static Map<String, Integer> nameToIndex = new HashMap<String, Integer>();
        private static HashMap<String, Boolean> areConnected = new HashMap<>();

        public static void pCommand(String name) {
            nameToIndex.put(name, vertices.size()); // making the same person again replaces the current person
            vertices.add(new PersonVertex(name));
        }

        public static void fCommand(String name, String name2) throws NullPointerException {
            if(!name.equals(name2)) { // person cannot add themselves as a friend
                vertices.get(nameToIndex.get(name)).addAdjacent(vertices.get(nameToIndex.get(name2)));
                vertices.get(nameToIndex.get(name2)).addAdjacent(vertices.get(nameToIndex.get(name)));

                String twoPKey = generateTwoPKey(name, name2);
                areConnected.put(twoPKey, true);
            }
        }

        public static void uCommand(String name, String name2) throws NullPointerException {
            for(int i = 0; i < vertices.get(nameToIndex.get(name)).getAdjacencies().size(); i++) {
                if(vertices.get(nameToIndex.get(name)).getAdjacencies().get(i) == vertices.get(nameToIndex.get(name2))) {
                    vertices.get(nameToIndex.get(name)).getAdjacencies().remove(i);
                    break;
                }
            }

            for(int i = 0; i < vertices.get(nameToIndex.get(name2)).getAdjacencies().size(); i++) {
                if(vertices.get(nameToIndex.get(name2)).getAdjacencies().get(i) == vertices.get(nameToIndex.get(name))) {
                    vertices.get(nameToIndex.get(name2)).getAdjacencies().remove(i);
                    break;
                }
            }

            String twoPKey = generateTwoPKey(name, name2);
            areConnected.put(twoPKey, false);
        }

        public static void lCommand(String name) throws NullPointerException {
            System.out.print("\tFriends:");
            for(PersonVertex v: vertices.get(nameToIndex.get(name)).getAdjacencies()) {
                System.out.print(" " + v.getName());
            }
            System.out.println();
        }

        public static void qCommand(String name, String name2) {
            String twoPKey = generateTwoPKey(name, name2);

            if(areConnected.containsKey(twoPKey)) { // check if key exists
                if(areConnected.get(twoPKey))
                    System.out.println("\tYes, they are friends");
            }
            else
                System.out.println("\tNo, they are not friends");
        }

        public static void xCommand() {
            System.exit(0);
        }

        private static String generateTwoPKey(String name, String name2) {
            String twoPKey = "";
            if(name.compareTo(name2) <= 0)
                twoPKey = name + "*" + name2;
            else if(name.compareTo(name2) > 0)
                twoPKey = name2 + "*" + name;

            return twoPKey;
        }

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