/***********************************************************************
 Name:
 Period:
 Date:
 What I Learned:
 Credit (person who helped me):
 Student(s) whom I helped (to what extent):
 ************************************************************************/

import java.util.*;
public class P6VeljkoCvetkovicPostfixEvaluation {
    public static void main(String[] args) throws IllegalArgumentException {

        System.out.println("Enter a valid postfix expression (single digits only),");
        System.out.println("such as 35*1+");
        Scanner keyboard = new Scanner(System.in);
        String s = keyboard.next();
        while(!s.equals("-1")) {

            System.out.println(s + "  --->  " + eval(s) + "\n");
            // // System.out.println((s = "354*+7*") + " = " + eval(s) + "\n");
            // // System.out.println((s = "82-") + " = " + eval(s) + "\n");
            // // System.out.println((s = "82/") + " = " + eval(s) + "\n");
            s = keyboard.next();
        }
    }
    public static int eval(String x) {

        Stack<Integer> stack = new Stack<Integer>();

        for(int i = 0; i < x.length(); i++){
            char c = x.charAt(i);

            if(Character.isDigit(c)){
                stack.push(c - '0');
            }
            else {
                stack.push(eval(stack.pop(), stack.pop(), c));
            }
        }
        return stack.pop();
    }
    public static int eval(int a, int b, char ch) throws IllegalArgumentException {

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
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

}
