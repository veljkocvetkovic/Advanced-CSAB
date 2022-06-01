//*********************************************************************************************************************************
// Name: Veljko Cvetkovic
// Period: 6
// Date: 3/17/22
// What I learned: I learned how to traverse over Sets and Maps using an Iterator and for-each loops, and
// how to remap System.out.println statements to a file. I also learned how to use a switch statement and how
// to reverse a map.
// How I feel about this lab: I think this lab was particularly interesting because the output
// was sent to a file for part of the program, and then back to the console. Additionally, I think
// it was good practice for the Map methods.
// What I wonder: I wonder what the internal representation of a HashMap/TreeMap is.
//***********************************************************************************************************************************

import java.io.*;
import java.util.*;

public class P6VeljkoCvetkovicDictionary {

    private static PrintStream ps;

    public static void main(String[] args) throws Exception {
        /***************************************************
         PART I
         **************************************************/
        try {
            // funnel all System.out.print() results to the output file "Pd6EdmundLauDictionaryOutputI.txt");
            ps = new PrintStream(new FileOutputStream("Pd6VeljkoCvetkovicDictionaryOutputI.txt"));
            System.setOut(ps);
        } catch (Exception e) {
            System.exit(1);
        } //catch


        Map<String, Set<String>> eng2spn = new TreeMap<String, Set<String>>();
        Scanner infile = new Scanner(new File("spanglish.txt"));
        while (infile.hasNext()) {
            add(eng2spn, infile.next(), infile.next());
        }

        infile.close();

        System.out.println("ENGLISH TO SPANISH");
        display(eng2spn);

        Map<String, Set<String>> spn2eng = reverse(eng2spn);
        System.out.println("\nSPANISH TO ENGLISH");
        display(spn2eng);

        ps.close(); // close the output file


        /***************************************************
         Part II
         **************************************************/

        // The two maps are still in the memory. Part II can interact with the user and add
        // new word(s) to both maps
        // For this part of the program, display all outputs onto the console. See sample outputs below.
        // After the user is done, write the two maps to a text file.


        // Write your Part II code here
        // Menu options: translate from (1) English to Spanish
        //                              (2) Spanish to English
        //                              (3) Add a new translation: (a) from English->Spanish (b) from Spanish->English
        //                              (4) Exit

        // send the newly edited maps to a text file: Pd4EdmundLauDictionaryOutputII.txt

        // To reset the System.out to the console
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out))); // reset output str

        while (true) {

            System.out.println("\nWhat would you like to do today? Select a number:" +
                    "\n1) Search dictionary" +
                    "\n2) Add translation" +
                    "\n3) Exit");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.");
                    if (sc.nextInt() == 1) {
                        System.out.println("You have selected the English to Spanish dictionary. What word would you like to search for (type in all lowercase)?");
                        String nxt = sc.next();

                        if (eng2spn.containsKey(nxt))
                            System.out.println(eng2spn.get(nxt));
                        else
                            System.out.println("Sorry, this word is currently not in the dictionary.");
                    } else {
                        System.out.println("You have selected the Spanish to English dictionary. What word would you like to search for (type in all lowercase)?");
                        String nxt = sc.next();

                        if (spn2eng.containsKey(nxt))
                            System.out.println(spn2eng.get(nxt));
                        else
                            System.out.println("Sorry, this word is currently not in the dictionary.");

                    }
                    break;
                case 2:
                    //add translation
                    System.out.println("What English word would you like to add?");
                    String engWord = sc.next();
                    System.out.println("What is the Spanish translation?");
                    String spanWord = sc.next();

                    add(eng2spn, engWord, spanWord);
                    add(spn2eng, spanWord, engWord);

                    break;
                case 3:
                    ps = new PrintStream(new FileOutputStream("Pd6VeljkoCvetkovicDictionaryOutputII.txt")); // send newly edited maps to text file before exit
                    System.setOut(ps);
                    System.out.println("ENGLISH TO SPANISH");
                    display(eng2spn);
                    System.out.println("\nSPANISH TO ENGLISH");
                    display(spn2eng);
                    System.exit(0);
            }
        }

    } // main

    // Note: must explain how your method works
    // The method iterates through the Map using an Iterator and prints out
    // the Set at each key with the corresponding values (words) using the
    // Set toString method.
    // Postcondition: display the contents of a dictionary on the screen
    public static void display(Map<String, Set<String>> m) {
        Iterator<String> it = m.keySet().iterator();

        while (it.hasNext()) {
            String t = it.next();
            System.out.println(t + " " + m.get(t));
        }

    } // display

    // Note: must explain how your method works
    // The method checks if the provided word is already in the Map (meaning that a TreeSet already exists)
    // and if it isn't it creates a new TreeSet at that key and then adds it to the TreeSet. If the word
    // is already an existing key, it simply adds the word to the TreeSet.
    // postcondition: insert a new pair to the English to Spanish Dictionary
    public static void add(Map<String, Set<String>> engToSpnDictionary, String word, String translation) {
        if (!engToSpnDictionary.containsKey(word))
            engToSpnDictionary.put(word, new TreeSet());

        engToSpnDictionary.get(word).add(translation); // add to TreeSet at given key

    } // add

    // Note: must explain how your method works
    // The method iterates through the provided Map using an Iterator, and then adds each value in
    // the Set at the given key to the temporary Map as keys, and makes the previous keys the values.
    // Finally, the method returns the temporary Set.
    // postcondition: returns a Spanish to English dictionary
    public static Map<String, Set<String>> reverse(Map<String, Set<String>> engToSpnDictionary) {
        Map<String, Set<String>> map = new TreeMap();

        Iterator<String> iter = engToSpnDictionary.keySet().iterator();
        while (iter.hasNext()) {
            String t = iter.next();
            for (String s : engToSpnDictionary.get(t)) {
                add(map, s, t); // add each value as a key
            }
        }
        return map;
    } // reverse

}// Dictionary2022

/********************
 INPUT:
 holiday
 fiesta
 holiday
 vacaciones
 party
 fiesta
 celebration
 fiesta
 <etc.>
 ***********************************
 OUTPUT:
 ENGLISH TO SPANISH
 banana [banana]
 celebration [fiesta]
 computer [computadora, ordenador]
 double [doblar, doble, duplicar]
 father [padre]
 feast [fiesta]
 good [bueno]
 hand [mano]
 hello [hola]
 holiday [fiesta, vacaciones]
 party [fiesta]
 plaza [plaza]
 priest [padre]
 program [programa, programar]
 sleep [dormir]
 son [hijo]
 sun [sol]
 vacation [vacaciones]

 SPANISH TO ENGLISH
 banana [banana]
 bueno [good]
 computadora [computer]
 doblar [double]
 doble [double]
 dormir [sleep]
 duplicar [double]
 fiesta [celebration, feast, holiday, party]
 hijo [son]
 hola [hello]
 mano [hand]
 ordenador [computer]
 padre [father, priest]
 plaza [plaza]
 programa [program]
 programar [program]
 sol [sun]
 vacaciones [holiday, vacation]
 **********************/

/***
 CONSOLE OUTPUT FOR PART II:

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 1
 You have selected the English to Spanish dictionary. What word would you like to search for (type in all lowercase)?
 father
 [padre]

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 2
 You have selected the Spanish to English dictionary. What word would you like to search for (type in all lowercase)?
 fiesta
 [celebration, feast, holiday, party]

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 1
 You have selected the English to Spanish dictionary. What word would you like to search for (type in all lowercase)?
 beautiful
 Sorry, this word is currently not in the dictionary.

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 2
 What English word would you like to add?
 beautiful
 What is the Spanish translation?
 bella

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 2
 You have selected the Spanish to English dictionary. What word would you like to search for (type in all lowercase)?
 bella
 [beautiful]

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 2
 What English word would you like to add?
 hand
 What is the Spanish translation?
 manecilla

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 2
 You have selected the Spanish to English dictionary. What word would you like to search for (type in all lowercase)?
 manecilla
 [hand]

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 2
 What English word would you like to add?
 tree
 What is the Spanish translation?
 arbol

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 1
 Choose 1 to translate from English to Spanish or 2 to translate from Spanish to English.
 1
 You have selected the English to Spanish dictionary. What word would you like to search for (type in all lowercase)?
 tree
 [arbol]

 What would you like to do today? Select a number:
 1) Search dictionary
 2) Add translation
 3) Exit
 3

 Process finished with exit code 0
 ********************************************
 NEW DICTIONARY OUTPUT:

 ENGLISH TO SPANISH
 banana [banana]
 beautiful [bella]
 celebration [fiesta]
 computer [computadora, ordenador]
 double [doblar, doble, duplicar]
 father [padre]
 feast [fiesta]
 good [bueno]
 hand [manecilla, mano]
 hello [hola]
 holiday [fiesta, vacaciones]
 party [fiesta]
 plaza [plaza]
 priest [padre]
 program [programa, programar]
 sleep [dormir]
 son [hijo]
 sun [sol]
 tree [arbol]
 vacation [vacaciones]

 SPANISH TO ENGLISH
 arbol [tree]
 banana [banana]
 bella [beautiful]
 bueno [good]
 computadora [computer]
 doblar [double]
 doble [double]
 dormir [sleep]
 duplicar [double]
 fiesta [celebration, feast, holiday, party]
 hijo [son]
 hola [hello]
 manecilla [hand]
 mano [hand]
 ordenador [computer]
 padre [father, priest]
 plaza [plaza]
 programa [program]
 programar [program]
 sol [sun]
 vacaciones [holiday, vacation]
 ***/