/**
 * Name: Veljko Cvetkovic
 * Period: 6
 * Purpose of the Program: To perform different operations on polynomials.
 * What I learned:
 * 1. How to represent polynomials using Maps
 * 2. How to find the derivative, integral, and antiderivative of a polynomial
 * 3. Doing iterator.next() + 1 repeatedly changes the value of iterator.next()
 * even if it's stored in a variable.
 *
 * Credits: Discussed the basics of derivatives and integrals with my dad.
 */

import java.util.*;
import java.text.DecimalFormat;
public class P6VeljkoCvetkovicPolynomial {
    public static void main(String[] args) {

        TreeMapPolynomial poly1 = new TreeMapPolynomial();
        // add a new term to the polynomial. The first argument is the exponent and the
        // second argument is the coefficient of the term.
        poly1.makeTerm(1, -4);  // Polynomial: -4x^1
        poly1.makeTerm(3, 2);   // Polynomial becomes: 2*x^3 + -4x
        poly1.makeTerm(0, 2);   // Polynomial becomes: 2*x^3 + -4x + 2*x^0

        System.out.println("Poly1: " + poly1);
        double evaluateAt = 2.0;
        System.out.println("Poly1 evaluated at " + evaluateAt + ": " + poly1.evaluateAt(evaluateAt));

        TreeMapPolynomial poly2 = new TreeMapPolynomial();
        poly2.makeTerm(1, -5);
        poly2.makeTerm(4, 2);
        poly2.makeTerm(0, -3);
        poly2.makeTerm(2, 1);
        System.out.println("Poly2: " + poly2);
        System.out.println("Poly2 evaluated at " + 5 + ": " + poly2.evaluateAt(5));

        System.out.println("Poly1 + Poly2: " + poly1.add(poly2));
        System.out.println("Poly1 - Poly2: " + poly1.subtract(poly2));
        System.out.println("Poly1 * Poly2: " + poly1.multiply(poly2));
        System.out.println("Derivative of Poly1: " + poly1.differentiate());
        System.out.println("Derivative of Poly2: " + poly2.differentiate());
        System.out.println("Integrate Poly1 from 0 to 5: " + poly1.integrate(0, 5));
        System.out.println("Integrate Poly2 from 2 to 10: " + poly2.integrate(2, 10));
        System.out.println("Antiderivative of Poly1: " + poly1.antiDerivative());
        System.out.println("Antiderivative of Poly2: " + poly2.antiDerivative());

    } // main
} // P6VeljkoCvetkovicPolynomial


interface Polynomial {

    public Polynomial add(Polynomial b);
    public Polynomial subtract(Polynomial b);
    public Polynomial multiply(Polynomial b);
    public Polynomial differentiate();
    public double integrate(int start, int end);
    public Polynomial antiDerivative();
    public void makeTerm(int exponent, double coefficient);
    public double evaluateAt(double x);
    public Polynomial addAll();
    public Polynomial negate();
    public TreeMap<Integer, Double> getPolynomial();
}

class TreeMapPolynomial implements Polynomial {

    private TreeMap<Integer, Double> poly;

    /**
     * Constructor
     * Initializes a new TreeMap
     */
    public TreeMapPolynomial() {
        poly = new TreeMap<>();
    }

    /**
     * Constructor
     * Creates a TreeMapPolynomial object using the provided TreeMap
     */
    public TreeMapPolynomial(TreeMap<Integer, Double> map) {
        this.poly = map;
    }

    /**
     * precondition: exponent is an integer
     * postcondition: adds a new term to the polynomial
     */
    public void makeTerm(int exponent, double coefficient) {
        if(poly.containsKey(exponent)) {
            poly.put(exponent, poly.get(exponent) + coefficient);
        } else
            poly.put(exponent, coefficient);
    }

    /**
     * precondition: none
     * postcondition: evaluates the polynomial at x
     */
    public double evaluateAt(double x) {

        double result = 0;

        Iterator<Integer> it = poly.keySet().iterator();

        while(it.hasNext()) {
            int t = it.next();
            result += Math.pow(x, t) * poly.get(t);
        }

        return result;
    }

    /**
     * precondition: none
     * postcondition: copies the contents of a TreeMap to another TreeMap
     */
    public Polynomial addAll() {
        TreeMapPolynomial temp = new TreeMapPolynomial();
        Iterator<Integer> it = this.poly.keySet().iterator();

        while(it.hasNext()) {
            int next = it.next();
            temp.poly.put(next, this.poly.get(next));
        }
        return temp;
    }

    /**
     * precondition: none
     * postcondition: multiplies all terms in the polynomial by -1 (useful for subtraction)
     */
    public Polynomial negate() {
        TreeMap<Integer, Double> temp = new TreeMap<>();

        Iterator<Integer> it = this.poly.keySet().iterator();

        while(it.hasNext()) {
            int next = it.next();
            temp.put(next, this.poly.get(next) * -1);
        }
        return new TreeMapPolynomial(temp); // call to one-arg constructor
    }

    /**
     * precondition: none
     * postcondition: returns the TreeMap of the current instance of Polynomial
     */
    public TreeMap<Integer, Double> getPolynomial() {
        return this.poly;
    }

    /**
     * precondition: none
     * postcondition: Adds two polynomials together
     */
    public Polynomial add(Polynomial b) {

        TreeMapPolynomial sum = (TreeMapPolynomial) b.addAll();

        Iterator<Integer> it = this.poly.keySet().iterator();

        while(it.hasNext()) {
            int next = it.next();

            if(sum.poly.containsKey(next)) {
                sum.poly.put(next, sum.poly.get(next) + this.poly.get(next));
            }
            else
                sum.poly.put(next, this.poly.get(next));
        }
        return sum;
    }

    /**
     * precondition: none
     * postcondition: subtracts two polynomials
     */
    public Polynomial subtract(Polynomial b) {
        return this.add(b.negate());
    }

    /**
     * precondition: none
     * postcondition: multiplies two polynomials together
     */
    public Polynomial multiply(Polynomial b) {
        TreeMap<Integer, Double> temp = new TreeMap<>();

        for(Integer i : this.poly.keySet()){
            for(Integer j : b.getPolynomial().keySet()){
                if(temp.containsKey(i+j)) {
                    temp.put(i+j, this.poly.get(i) * b.getPolynomial().get(j) + temp.get(i+j));
                } else
                    temp.put(i+j, this.poly.get(i) * b.getPolynomial().get(j));
            }
        }
        return new TreeMapPolynomial(temp);
    }

    /**
     * precondition: none
     * postcondition: returns the derivative of the given polynomial
     */
    public Polynomial differentiate() {
        TreeMap<Integer, Double> temp = new TreeMap<>();

        Iterator<Integer> it = this.poly.keySet().iterator();

        while(it.hasNext()) {
            int next = it.next();
            if(temp.containsKey(next-1))
                temp.put(next-1, this.poly.get(next) * next + temp.get(next-1));
            else
                temp.put(next-1, this.poly.get(next) * next);
        }
        return new TreeMapPolynomial(temp);
    }

    /**
     * precondition: none
     * postcondition: returns the definite integral of the polynomial from start to end
     */
    public double integrate(int start, int end) {
        DecimalFormat d = new DecimalFormat("0.00");

        return Double.parseDouble(d.format(this.antiDerivative().evaluateAt(end) - this.antiDerivative().evaluateAt(start)));
    }

    /**
     * precondition: none
     * postcondition: returns the anti derivative of the polynomial
     */
    public Polynomial antiDerivative() {

        DecimalFormat d = new DecimalFormat("0.00");

        TreeMap<Integer, Double> temp = new TreeMap<>();

        Iterator<Integer> it = this.poly.keySet().iterator();

        while(it.hasNext()) {
            int next = it.next();
            int inc = next + 1;

            if(temp.containsKey(inc))
                temp.put(inc, Double.parseDouble(d.format(this.poly.get(next) / inc + temp.get(inc))));
            else
                temp.put(inc, Double.parseDouble(d.format(this.poly.get(next) / inc)));

        }
        return new TreeMapPolynomial(temp);
    }

    public String toString() {

        String t = "";
        Iterator<Integer> it = poly.keySet().iterator();

        while(it.hasNext()) {
            int i = it.next();
            if(poly.get(i) == 0)
                continue;
            if(i == 0)
                t = poly.get(i) + " + " + t; // add to front of string
            else if(i == 1) {
                if(poly.get(i) == 1)
                    t = "x" + " + " + t;
                else
                    t = poly.get(i) + "x" + " + " + t;
            }
            else {
                if(poly.get(i) == 1)
                    t = "x^" + i + " + " + t;
                else if(poly.get(i) == -1)
                    t = "-x^" + i + " + " + t;
                else
                    t = poly.get(i) + "x^" + i + " + " + t;
            }
        }

        t = t.substring(0, t.length()-3); //remove extra + at the end

        return t;
    }

} // TreeMapPolynomial

/* Program Output

Poly1: 2.0x^3 + -4.0x + 2.0
Poly1 evaluated at 2.0: 10.0
Poly2: 2.0x^4 + x^2 + -5.0x + -3.0
Poly2 evaluated at 5: 1247.0
Poly1 + Poly2: 2.0x^4 + 2.0x^3 + x^2 + -9.0x + -1.0
Poly1 - Poly2: -2.0x^4 + 2.0x^3 + -x^2 + x + 5.0
Poly1 * Poly2: 4.0x^7 + -6.0x^5 + -6.0x^4 + -10.0x^3 + 22.0x^2 + 2.0x + -6.0
Derivative of Poly1: 6.0x^2 + -4.0
Derivative of Poly2: 8.0x^3 + 2.0x + -5.0
Integrate Poly1 from 0 to 5: 272.5
Integrate Poly2 from 2 to 10: 40050.56
Antiderivative of Poly1: 0.5x^4 + -2.0x^2 + 2.0x
Antiderivative of Poly2: 0.4x^5 + 0.33x^3 + -2.5x^2 + -3.0x

Process finished with exit code 0

 */