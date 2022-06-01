// Name: Veljko Cvetkovic
// Date: 3/12/22
// What I learned: I learned how to iterate over a Map and how to reverse a Map
// How I feel about this lab: I thought this lab was interesting because it used an ArrayList as the value type of the Map
// What I wonder: I wonder if there are any other types of maps. To solve this problem without maps, I would use
// an array of LinkedLists for fast adding and removing.
// Credits: none

import java.util.*;

public class P6VeljkoCvetkovicActingSchool {

    public static void main(String[] args) {

        // Map initializer. Does it look like the one for 1D array? 2D array?
        Map<String, String> doubleBraceMap =
                new HashMap<String, String>() {
                    {
                        put("key1", "value1");
                        put("key1", "value2");
                    }
                };

        System.out.println(doubleBraceMap + "\n");
        Map<String, String> sGrades = new TreeMap<String, String>(); // HashMap

        sGrades.put("Jack Nicholson", "A-");
        sGrades.put("Humphrey Bogart", "A+");
        sGrades.put("Audrey Hepburn", "A");
        sGrades.put("Meryl Streep", "A-");
        sGrades.put("Jimmy Stewart", "A");

        // What you need to do:
        // 1. display initial data. Use an iterator instead of using the built-in toString method of HashMap

        Iterator<String> it = sGrades.keySet().iterator();

        while (it.hasNext()) {
            Object t = it.next();
            System.out.print(t + " ");
            System.out.println("(" + sGrades.get(t) + ")");
        }
        System.out.println();

        // 2. reverse the map--use TreeMap

        Map<String, ArrayList<String>> temp = reverseMap(sGrades);

        // 3. display the reversed map

        Iterator<String> it2 = temp.keySet().iterator();

        while (it2.hasNext()) {
            Object t = it2.next();
            System.out.print(t + ": ");
            System.out.println(temp.get(t));
        }

    } // main

    public static Map<String, ArrayList<String>> reverseMap(Map<String, String> map) {
        Map<String, ArrayList<String>> temp = new TreeMap<>();

        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()) {
            String t = it.next();

            if (temp.get(map.get(t)) == null) { // if the key does not exist in the map
                ArrayList<String> ch = new ArrayList<>();
                ch.add(t);
                temp.put(map.get(t), ch);
            } else
                temp.get(map.get(t)).add(t); // add to ArrayList in map
        }
        return temp;
    }

} // ActingSchool_shell

/* Program Output

Audrey Hepburn (A)
Humphrey Bogart (A+)
Jack Nicholson (A-)
Jimmy Stewart (A)
Meryl Streep (A-)

A: [Audrey Hepburn, Jimmy Stewart]
A+: [Humphrey Bogart]
A-: [Jack Nicholson, Meryl Streep]

Process finished with exit code 0

 */