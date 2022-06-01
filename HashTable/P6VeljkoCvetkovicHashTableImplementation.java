/*****************************************************************************************************************
 NAME: Veljko Cvetkovic
 PERIOD: 6
 DUE DATE: 2/27/22

 PURPOSE: To implement different ways of handling collisions when adding values to hash tables: linear probing, quadratic
 probing (added), relatively prime probing, and chaining.

 WHAT I LEARNED:
 1. I learned how switch statements work in Java
 2. I learned how to find the first relatively prime number of the given table size by using the gcf algorithm to
 find the gcf, and if the gcf of a number and the size is not equal to 1, then the two numbers are not relatively prime
 3. I also learned how to implement the different types of methods for handling collisions

 CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE): none

 ****************************************************************************************************************/

/***********************************************************************************
 Assignment:  This hashing program results in collisions.
 You are to implement three different collision schemes:
 linear probing, relative prime probing (use the first relatively prime
 number of the length of the hash table as the step increase), and
 chaining.  Then implement a search algorithm that is appropriate
 for each collision scheme.
 ***********************************************************************************/
import java.util.*;
import javax.swing.*;

public class P6VeljkoCvetkovicHashTableImplementation {

    public static void main(String[] args) {

        int arrayLength = Integer.parseInt(JOptionPane.showInputDialog("Hashing!\n"+ "Enter the size of the array:  ")); // enter 20

        int numItems = Integer.parseInt(JOptionPane.showInputDialog("Add n items:  ")); // enter 15

        int scheme = Integer.parseInt(JOptionPane.showInputDialog(
                "The Load Factor is " + (double)numItems/arrayLength +
                        "\nWhich collision scheme?\n" +
                        "1. Linear Probing\n" +
                        "2. Relatively Prime Probing\n" +
                        "3. Chaining\n" +
                        "4. Quadratic Probing"));
        Hashtable table = null;
        switch( scheme )
        {
            case 1:
                table = new HashtableLinearProbe(arrayLength);
                break;
            case 2: // rehash using the first relatively prime or arrayLength
                table = new HashtableRehash(arrayLength);
                break;
            case 3:
                table = new HashtableChaining(arrayLength);
                break;
            case 4:
                table = new HashtableQuadraticProbe(arrayLength);
                break;
            default:  System.exit(0);
        }
        for(int i = 0; i < numItems; i++)
            table.add("Item" + i);
        int itemNumber = Integer.parseInt(JOptionPane.showInputDialog(
                "Search for:  Item0" + " to "+ "Item"+(numItems-1)));
        while( itemNumber != -1 )
        {
            String key = "Item" + itemNumber;
            int index = table.indexOf(key);
            if( index >= 0) //found it
                System.out.println(key + " found  at index " + index);
            else
                System.out.println(key + " not found!");
            itemNumber = Integer.parseInt(JOptionPane.showInputDialog(
                    "Search for:  Item0" + " to "+ "Item"+(numItems-1)));
        }
        System.exit(0);
    }
}

interface Hashtable {

    void add(Object obj);
    int indexOf(Object obj);
}

class HashtableLinearProbe implements Hashtable {

    private Object[] array;

    /**
     * Constructor
     * Initializes the hash table with the provided size
     */
    public HashtableLinearProbe(int size){

        array = new Object[size];
    }

    /**
     * precondition: the table is not full
     * postcondition: adds the provided object to the hash table at the correct index
     */
    public void add(Object obj) {

        int code = obj.hashCode();
        int index = Math.abs(code % array.length);
        if (array[index] == null) { //empty
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
        else { //collision

            System.out.println(obj + "\t" + code + "\tCollision at "+ index);
            index = linearProbe(index);
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
    }

    /**
     * precondition: index is the location of the collision
     * postcondition: returns the next available index for the item to be inserted through linear probing
     */
    public int linearProbe(int index) {

        int tempIndex = (index + 1) % array.length;

        while(tempIndex != index) { // after n iterations
            if(array[tempIndex] == null)
                return tempIndex;

            tempIndex = (tempIndex + 1) % array.length;
        }
        return -1; // array is full
    }

    /**
     * precondition: called in main method
     * postcondition: returns the index of the Object obj in the hash table
     */
    public int indexOf(Object obj) {

        int index = Math.abs(obj.hashCode() % array.length);
        while(array[index] != null) {

            if(array[index].equals(obj)) { //found it
                return index;
            }
            else { //search for it in a linear probe manner
                index = (index + 1) % array.length;
                System.out.println("Looking at index " + index);
            }
        } // while
        return -1; // not found
    } // indexOf
} // HashtableLinearProbe


class HashtableQuadraticProbe implements Hashtable {

    private Object[] array;

    /**
     * Constructor
     * Initializes the hash table with the provided size
     */
    public HashtableQuadraticProbe(int size){

        array = new Object[size];
    }

    /**
     * precondition: the table is not full
     * postcondition: inserts obj into the hash table at the correct index
     */
    public void add(Object obj) {

        int code = obj.hashCode();
        int index = Math.abs(code % array.length);
        if (array[index] == null) { //empty
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
        else { //collision

            System.out.println(obj + "\t" + code + "\tCollision at "+ index);
            index = quadraticProbe(index);
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
    }

    /**
     * precondition: index is the location of the collision
     * postcondition: returns the next available index for the item to be inserted through quadratic probing
     */
    public int quadraticProbe(int index) {

        int count = 1;
        int tempIndex = (index + (int)Math.pow(count++, 2)) % array.length;

        while(tempIndex != index) { // after n iterations
            if(array[tempIndex] == null)
                return tempIndex;

            tempIndex = (index + (int)Math.pow(count++, 2)) % array.length;
        }
        return -1; //array is full
    }

    /**
     * precondition: called in main method
     * postcondition: returns the index of Object obj
     */
    public int indexOf(Object obj) {

        int index = Math.abs(obj.hashCode() % array.length);
        int initialIndex = index;
        int count = 1;
        while(array[index] != null) {

            if(array[index].equals(obj)) { //found it
                return index;
            }
            else { //search for it in a quadratic probe manner
                index = (initialIndex + (int)Math.pow(count++, 2)) % array.length;
                System.out.println("Looking at index " + index);
            }
        } // while
        return -1; // not found
    } // indexOf

} // HashtableQuadraticProbe


class HashtableRehash implements Hashtable {

    private Object[] array;
    private int constant = 2;

    /**
     * Constructor
     * Initializes the hash table; finds a constant that is relatively prime to the size of the array
     */
    public HashtableRehash(int size) {
        array = new Object[size];

        if(size % 2 == 0) {
            for(int i = 3; i < size; i++) {
                if(gcf(i, size) == 1) {
                    constant = i;
                    break;
                }
            }
        }
    }

    /**
     * precondition: the table is not full
     * postcondition: inserts obj into the hash table at the correct index
     */
    public void add(Object obj) {

        int code = obj.hashCode();
        int index = Math.abs(code % array.length);
        if(array[index] == null) { //empty
            //insert it
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
        else { //collision

            System.out.println(obj + "\t" + code + "\tCollision at "+ index);
            index = rehash(index);
            array[index] = obj;
            System.out.println(obj + "\t" + code + "\t" + index);
        }
    }

    /**
     * precondition: index is the location of the collision
     * postcondition: returns the next available index for the item to be inserted through relatively prime probing
     */
    public int rehash(int index) {

        int count = 1;
        int tempIndex = (index + count++ * constant) % array.length;

        while(tempIndex != index) { // after n iterations
            if(array[tempIndex] == null)
                return tempIndex;

            tempIndex = (index + count++ * constant) % array.length;
        }
        return -1;
    }

    /**
     * precondition: called in main method
     * postcondition: returns the index of Object obj
     */
    public int indexOf(Object obj) {

        int index = Math.abs(obj.hashCode() % array.length);
        while(array[index] != null) {

            if(array[index].equals(obj)) { //found it
                return index;
            }
            else { //search for it in a rehashing manner
                index = (index + constant) % array.length;
                System.out.println("Looking at index " + index);
            }
        }
        return -1; //not found
    }// index of

    /**
     * precondition: called in constructor
     * postcondition: returns the greatest common factor of x and y
     */
    private int gcf(int x, int y) {
        if(x == 0)
            return y;

        return gcf(y%x, x);
    }

} // HashTableRehash


class HashtableChaining implements Hashtable {

    private LinkedList[] array;

    /**
     * Constructor
     * Instantiates the hash table and the LinkedLists
     */
    public HashtableChaining(int size) {

        array = new LinkedList[size];

        for(int i = 0; i < size; i++)
            array[i] = new LinkedList();

    }

    /**
     * precondition: the table is not full
     * postcondition: inserts the provided object into the hash table at the correct index
     */
    public void add(Object obj) {

        int code = obj.hashCode();
        int index = Math.abs(code % array.length);
        array[index].addFirst(obj);
        System.out.println(obj + "\t" + code + " " + " at " +index + ": "+ array[index]);
    }

    /**
     * precondition: called in main method
     * postcondition: returns the index of the Object obj
     */
    public int indexOf(Object obj) {

        int index = Math.abs(obj.hashCode() % array.length);
        if(!array[index].isEmpty()) {

            if(array[index].getFirst().equals(obj)) { //found it
                return index;
            }
            else { //search for it in a chaining manner
                Iterator iter = array[index].iterator();
                while(iter.hasNext()) {
                    if (iter.next().equals(obj))
                        return index;
                }
            }
        }
        return -1; //not found
    } // indexOf

} // HashtableChaining

/* Program Output

--- Testing Parameters --
size = 20, numItems = 15

--Linear Probing--

Item0	70973277	17
Item1	70973278	18
Item2	70973279	19
Item3	70973280	0
Item4	70973281	1
Item5	70973282	2
Item6	70973283	3
Item7	70973284	4
Item8	70973285	5
Item9	70973286	6
Item10	-2094795630	10
Item11	-2094795629	9
Item12	-2094795628	8
Item13	-2094795627	7
Item14	-2094795626	Collision at 6
Item14	-2094795626	11
Item4 found  at index 1
Item6 found  at index 3
Looking at index 7
Looking at index 8
Looking at index 9
Looking at index 10
Looking at index 11
Item14 found  at index 11
Item99 not found!


--Quadratic Probing--

Item0	70973277	17
Item1	70973278	18
Item2	70973279	19
Item3	70973280	0
Item4	70973281	1
Item5	70973282	2
Item6	70973283	3
Item7	70973284	4
Item8	70973285	5
Item9	70973286	6
Item10	-2094795630	10
Item11	-2094795629	9
Item12	-2094795628	8
Item13	-2094795627	7
Item14	-2094795626	Collision at 6
Item14	-2094795626	15
Item4 found  at index 1
Item6 found  at index 3
Looking at index 7
Looking at index 10
Looking at index 15
Item14 found  at index 15
Item99 not found!


--Relatively Prime Probing--

Item0	70973277	17
Item1	70973278	18
Item2	70973279	19
Item3	70973280	0
Item4	70973281	1
Item5	70973282	2
Item6	70973283	3
Item7	70973284	4
Item8	70973285	5
Item9	70973286	6
Item10	-2094795630	10
Item11	-2094795629	9
Item12	-2094795628	8
Item13	-2094795627	7
Item14	-2094795626	Collision at 6
Item14	-2094795626	12
Item4 found  at index 1
Item6 found  at index 3
Looking at index 9
Looking at index 12
Item14 found  at index 12
Item99 not found!


--Chaining--

Item0	70973277  at 17: [Item0]
Item1	70973278  at 18: [Item1]
Item2	70973279  at 19: [Item2]
Item3	70973280  at 0: [Item3]
Item4	70973281  at 1: [Item4]
Item5	70973282  at 2: [Item5]
Item6	70973283  at 3: [Item6]
Item7	70973284  at 4: [Item7]
Item8	70973285  at 5: [Item8]
Item9	70973286  at 6: [Item9]
Item10	-2094795630  at 10: [Item10]
Item11	-2094795629  at 9: [Item11]
Item12	-2094795628  at 8: [Item12]
Item13	-2094795627  at 7: [Item13]
Item14	-2094795626  at 6: [Item14, Item9]
Item4 found  at index 1
Item6 found  at index 3
Item14 found  at index 6
Item99 not found!

Process finished with exit code 0

 */