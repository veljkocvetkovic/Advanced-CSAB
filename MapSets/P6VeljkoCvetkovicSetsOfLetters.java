// Name: Veljko Cvetkovic
// Date: 3/8/22

import java.util.*;
import java.io.*;

public class P6VeljkoCvetkovicSetsOfLetters {

    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "declarationLast.txt";
        fillTheSets(fileName);
    }

    //precondition: none
    //postcondition: prints sets containing all the lowercase, uppercase, and other characters in the file
    public static void fillTheSets(String fn) throws FileNotFoundException {

        Scanner infile = new Scanner(new File(fn));

        Set<Character> commonLower = new TreeSet();
        Set<Character> commonUpper = new TreeSet();
        Set<Character> commonOther = new TreeSet();

        while (infile.hasNext()) {

            Set<Character> lower = new TreeSet();
            Set<Character> upper = new TreeSet();
            Set<Character> other = new TreeSet();

            String line = infile.nextLine();

            for (int i = 0; i < line.length(); i++) {
                char let = line.charAt(i);
                if ((int) let >= 97 && (int) let <= 122) { // ASCII values for lowercase letters
                    lower.add(let);
                } else if ((int) let >= 65 && (int) let <= 90) { // ASCII values for uppercase letters
                    upper.add(let);
                } else
                    other.add(let);
            }

            System.out.println("\n" + line);
            System.out.println("Lower Case: " + lower);
            System.out.println("Upper Case: " + upper);
            System.out.println("Other: " + other);

            // addAll method rewritten
            if (commonLower.isEmpty())
                addAll(commonLower, lower);
            if (commonUpper.isEmpty())
                addAll(commonUpper, upper);
            if (commonOther.isEmpty())
                addAll(commonOther, other);

            // find intersection between each set
            commonLower = intersection(commonLower, lower);
            commonUpper = intersection(commonUpper, upper);
            commonOther = intersection(commonOther, other);

        }
        System.out.println("\nCommon Lower Case: " + commonLower);
        System.out.println("Common Upper Case: " + commonUpper);
        System.out.println("Common Other: " + commonOther);
    }

    //precondition: none
    //postcondition: returns the set containing the common elements between s and t
    public static Set<Character> intersection(Set<Character> s, Set<Character> t) {
        Set<Character> temp = new TreeSet<>();

        Iterator<Character> iter = s.iterator();

        while (iter.hasNext()) {
            Character value = iter.next();
            if (t.contains(value))
                temp.add(value);
        }
        return temp;
    }

    //precondition: none
    //postcondition: adds all of Set t to Set s
    public static void addAll(Set<Character> s, Set<Character> t) {
        for (Character ch : t) {
            s.add(ch);
        }
    }
}

/***********************************
 We, therefore, the Representatives of the united States of
 Lower Case: [a, d, e, f, h, i, n, o, p, r, s, t, u, v]
 Upper Case: [R, S, W]
 Other: [ , ,]

 America, in General Congress, Assembled, appealing to the
 Lower Case: [a, b, c, d, e, g, h, i, l, m, n, o, p, r, s, t]
 Upper Case: [A, C, G]
 Other: [ , ,]

 Supreme Judge of the world for the rectitude of our intentions,
 Lower Case: [c, d, e, f, g, h, i, l, m, n, o, p, r, s, t, u, w]
 Upper Case: [J, S]
 Other: [ , ,]

 do, in the Name, and by the Authority of the good People of
 Lower Case: [a, b, d, e, f, g, h, i, l, m, n, o, p, r, t, u, y]
 Upper Case: [A, N, P]
 Other: [ , ,]

 these Colonies, solemnly publish and declare, That these United
 Lower Case: [a, b, c, d, e, h, i, l, m, n, o, p, r, s, t, u, y]
 Upper Case: [C, T, U]
 Other: [ , ,]

 Colonies are, and of Right ought to be Free and Independent
 Lower Case: [a, b, d, e, f, g, h, i, l, n, o, p, r, s, t, u]
 Upper Case: [C, F, I, R]
 Other: [ , ,]

 States; that they are Absolved from all Allegiance to the
 Lower Case: [a, b, c, d, e, f, g, h, i, l, m, n, o, r, s, t, v, y]
 Upper Case: [A, S]
 Other: [ , ;]

 British Crown, and that all political connection between them
 Lower Case: [a, b, c, d, e, h, i, l, m, n, o, p, r, s, t, w]
 Upper Case: [B, C]
 Other: [ , ,]

 and the State of Great Britain, is and ought to be totally
 Lower Case: [a, b, d, e, f, g, h, i, l, n, o, r, s, t, u, y]
 Upper Case: [B, G, S]
 Other: [ , ,]

 dissolved; and that as Free and Independent States, they have
 Lower Case: [a, d, e, h, i, l, n, o, p, r, s, t, v, y]
 Upper Case: [F, I, S]
 Other: [ , ,, ;]

 full Power to levy War, conclude Peace, contract Alliances,
 Lower Case: [a, c, d, e, f, i, l, n, o, r, s, t, u, v, w, y]
 Upper Case: [A, P, W]
 Other: [ , ,]

 establish Commerce, and to do all other Acts and Things which
 Lower Case: [a, b, c, d, e, g, h, i, l, m, n, o, r, s, t, w]
 Upper Case: [A, C, T]
 Other: [ , ,]

 Independent States may of right do. And for the support of this
 Lower Case: [a, d, e, f, g, h, i, m, n, o, p, r, s, t, u, y]
 Upper Case: [A, I, S]
 Other: [ , .]

 Declaration, with a firm reliance on the protection of divine
 Lower Case: [a, c, d, e, f, h, i, l, m, n, o, p, r, t, v, w]
 Upper Case: [D]
 Other: [ , ,]

 Providence, we mutually pledge to each other our Lives, our
 Lower Case: [a, c, d, e, g, h, i, l, m, n, o, p, r, s, t, u, v, w, y]
 Upper Case: [L, P]
 Other: [ , ,]

 Fortunes and our sacred Honor.
 Lower Case: [a, c, d, e, n, o, r, s, t, u]
 Upper Case: [F, H]
 Other: [ , .]

 Common Lower Case: [d, e, n, o, r, t]
 Common Upper Case: []
 Common Other: [ ]

 Process finished with exit code 0

 ************************************/