/*****************************************************************************************************************
 NAME: Veljko Cvetkovic
 PERIOD: 6
 DUE DATE: 12/05/21
 ASSIGNMENT: Stack Text Editor Program

 PURPOSE: To remove characters based on certain escape characters entered by a user in a line of input
 and to print the contents of the stack containing the user's input.

 LEARNED:
 - How to print a stack using an iterator.
 - How to add and remove elements from a stack object.
 - How to check if a stack is empty.

 CREDITS: I did this individually.

 ****************************************************************************************************************/
import java.util.*;
public class P6VeljkoCvetkovicStackTextEditor {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Scanner choice = new Scanner(System.in);
        String again;
        do {
            System.out.print("Enter a line of text: ");
            String input = sc.nextLine();
            editText(input);
            System.out.print("\nAgain (y/n)? ");
            again = choice.next();
        } while (!again.equals("n"));
    }//main

    //pre:  s is not null
    //post: edits a String according to certain characters it contains and prints the resulted string
    public static void editText(String s) {

        Stack<Character> text = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            text.push(c);

            if (c == '-' && !text.isEmpty()) {
                text.pop();
                if (!text.isEmpty())
                    text.pop();
            } else if (c == '$') {
                while (!text.isEmpty()) {
                    text.pop();
                }
            }
        }
        printStack(text);
    }//editText

    //pre:  none
    //post: prints the Stack in a nicer format, ex. abc instead of [a, b, c]
    public static void printStack(Stack<Character> s) {

        Iterator<Character> iter = s.iterator();

        System.out.print("\nHere is the line you entered: ");
        while (iter.hasNext()) {
            System.out.print(iter.next());
        }

    }//printStack
}

/* Program Output

Enter a line of text: Ca-noe$Ra3-fx-t

Here is the line you entered: Raft
Again (y/n)? y
Enter a line of text: AP$$-Compp-utee-r Sic--cei--ience

Here is the line you entered: Computer Science
Again (y/n)? y
Enter a line of text: He$He was astg-- tall ae-s a$ 6 foot,- 3 inchre-- treeu-

Here is the line you entered:  6 foot 3 inch tree
Again (y/n)? y
Enter a line of text: bone matrix and pivot joint$

Here is the line you entered:
Again (y/n)? y
Enter a line of text: dey$daybsah---reakk-s be-ell

Here is the line you entered: daybreaks bell
Again (y/n)? n

Process finished with exit code 0
*/