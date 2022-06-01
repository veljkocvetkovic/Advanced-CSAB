/***********************************************************************
 Name: Veljko Cvetkovic
 Period: 6
 Date: 12/16/21

 What I Learned: I learned how to convert a char to an integer by subtracting '0' from a char which gets the decimal
 value of an ASCII character (when the character is a single digit number from 0-9). I also learned how to use stacks
 and the stack methods to maintain the values from the expression and to perform the correct operations in the correct order.

 Credit (person who helped me): I did this myself.
 Student(s) whom I helped (to what extent): none
 ************************************************************************/

import java.util.*;
public class P6VeljkoCvetkovicInfixToPostfix {

    public static void main(String[] args) {

        System.out.println("Enter an infix expression such as 1+2*3 or (1+2)*3 (single digits); or '-1' to quit");
        Scanner keyboard = new Scanner(System.in); //(3*(4+5)-2)/5
        String s = keyboard.next();
        while (!s.equals ("-1")){
            String postfixExp = trans(s);
            System.out.println (s + " --> " + postfixExp + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");

            /*
            System.out.println("\n----------! Test Data !----------");
            System.out.println ("3+4*5" + " --> " + (postfixExp = trans("3+4*5")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            System.out.println ("3*4+5" + " --> " + (postfixExp = trans("3*4+5")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            System.out.println ("(4+5)-5*3" + " --> " + (postfixExp = trans("(4+5)-5*3")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            System.out.println ("(3+4)*(5+6)" + " --> " + (postfixExp = trans("(3+4)*(5+6)")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            System.out.println ("(3*(4+5)-2)/5" + " --> " + (postfixExp = trans("(3*(4+5)-2)/5")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            System.out.println ("8+1*2-9/3" + " --> " + (postfixExp = trans("8+1*2-9/3")) + " --> " + PostfixEvaluation.eval (postfixExp) + "\n");
            */

            s = keyboard.next();
        }
    }  // end of main

    /*
     pre: x contains only digits from 0-9 and +-/*
     post: Convert infix expression to postfix and return postfix expression
    */
    public static String trans(String x) {

        Stack<Character> stack = new Stack<>();
        String post = "";

        for(int i = 0; i < x.length(); i++){
            char t = x.charAt(i);

            if(Character.isDigit(t)){
                post += t;
            }
            else if(t == ')'){
                while(!stack.isEmpty()){
                    char c = stack.pop();
                    if(c == '('){
                        break;
                    }
                    post += c;
                }
            }
            else{
                if(t == '('){
                    stack.push(t);
                }
                else if(isOperator(t)){
                    if(!stack.isEmpty() && isOperator(stack.peek())){
                        while(!stack.isEmpty() && stack.peek() != '(' && !isLower(t, stack.peek())){
                            post += stack.pop();
                        }
                    }
                    stack.push(t);
                }
            }
        }
        while(!stack.isEmpty()){
            post += stack.pop();
        }

        return post;
    }  // end of trans

    /*
     pre: Called with two characters
     post: returns true if c2 is of lower precedence than c1, false otherwise
    */
    public static boolean isLower(char c1, char c2){
        return (c1 == '*' || c1 == '/') && (c2 == '+' || c2 == '-');
    }

    /*
     pre: A character is provided
     post: returns true if ch is one of the four operators (+, -, *, /), false otherwise
    */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

}  // end of InfixToPostfix


class PostfixEvaluation {

    public static void main(String[] args) {

        System.out.println("Enter a valid postfix expression such as 35*1+ (single digits only), or '-1' to quit");
        Scanner keyboard = new Scanner(System.in);
        String s = keyboard.next();
        while(!s.equals("-1")) {

            System.out.println(s + "  --->  " + eval(s) + "\n");

            /*
            System.out.println("\n----------! Test Data !----------");
            System.out.println((s = "345*+") + " --> " + eval(s) + "\n");
            System.out.println((s = "34*5+") + " --> " + eval(s) + "\n");
            System.out.println((s = "45+53*-") + " --> " + eval(s) + "\n");
            System.out.println((s = "34+56+*") + " --> " + eval(s) + "\n");
            System.out.println((s = "345+*2-5/") + " --> " + eval(s) + "\n");
            System.out.println((s = "812*+93/-") + " --> " + eval(s) + "\n");
            System.out.println((s = "354*+7*") + " = " + eval(s) + "\n");
            System.out.println((s = "82-") + " = " + eval(s) + "\n");
            System.out.println((s = "82/") + " = " + eval(s) + "\n");
             */

            s = keyboard.next();
        }
    }

    /*
     pre: A valid postfix expression is provided
     post: returns the numerical result of the postfix expression
    */
    public static int eval(String x) {

        Stack<Integer> stack = new Stack<Integer>();

        for(int i = 0; i < x.length(); i++){
            char c = x.charAt(i);

            if(Character.isDigit(c)){
                stack.push(c - '0'); // convert char to int
            }
            else {
                stack.push(eval(stack.pop(), stack.pop(), c));
            }
        }
        return stack.pop();
    }

    /*
     pre: Two integers and an operator are provided
     post: returns the result of the operation; IllegalArgumentException is thrown
     if ch is not a valid operator (is not one of the four: +, -, *, /)
    */
    public static int eval(int a, int b, char ch) {

        if (ch == '+')
            return b + a;
        else if (ch == '-')
            return b - a;
        else if (ch == '*')
            return b * a;
        else if (ch == '/')
            return b / a;
        else
            throw new IllegalArgumentException();
    }

    /*
     pre: A character is provided
     post: returns true if ch is one of the four operators (+, -, *, /), false otherwise
    */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

} // end of PostfixEvaluation


/* Program Output

Enter an infix expression such as 1+2*3 or (1+2)*3 (single digits); or '-1' to quit
3+4*5
3+4*5 --> 345*+ --> 23

3*4+5
3*4+5 --> 34*5+ --> 17

(4+5)-5*3
(4+5)-5*3 --> 45+53*- --> -6

(3+4)*(5+6)
(3+4)*(5+6) --> 34+56+* --> 77

(3*(4+5)-2)/5
(3*(4+5)-2)/5 --> 345+*2-5/ --> 5

8+1*2-9/3
8+1*2-9/3 --> 812*+93/- --> 7

-1

Process finished with exit code 0

 */