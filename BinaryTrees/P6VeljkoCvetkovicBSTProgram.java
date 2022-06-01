/*****************************************************************************************************************
 NAME: Veljko Cvetkovic
 PERIOD: 6
 DUE DATE: 1/17/22

 PURPOSE: To build a binary search tree, find its minimum and maximum values, search for a target in the
 tree (log n), display the tree sideways, and display the in order (smallest to largest) traversal of the tree.

 WHAT I LEARNED:
 1. I learned how to display a BST sideways by essentially displaying the in order traversal
 starting on the opposite side.
 2. I also learned how to search for a target and insert elements into a BST in O(log n).

 HOW I FEEL ABOUT THIS LAB: This lab was very interesting because many of the methods have nice recursive
 solutions and are much more efficient on BSTs than on other data structures due to the fact that the
 elements are sorted for binary search.

 CREDITS (BE SPECIFIC: FRIENDS, PEERS, ONLINE WEBSITE): none
 ****************************************************************************************************************/

import java.util.Scanner;
import java.util.*;

/****************************************************************
 Practice with a Binary Search Tree. Uses TreeNode.
 Prompt the user for an input string.  Build a Binary Search Tree
 using Comparables.  Display it as a sideways tree (take the code
 from the Tree Lab).  Prompt the user for a target and search the tree
 for it.  Display the tree's minimum and maximum values.  Print
 the data in order from smallest to largest.
 *****************************************************************/
public class P6VeljkoCvetkovicBSTProgram {

    public static void main(String[] args) {

        BinarySearchTree<String> t = new BinarySearchTree<>();

        // build the tree
        Scanner sc = new Scanner(System.in);
        System.out.print("Input string: ");
        String s = sc.next(); // "MAENIRAC";  "AMERICAN";   "AACEIMNR"
        for (int k = 0; k < s.length(); k++)
            t.insert("" + s.charAt(k));

        // get the root of the newly created BinarySearchTree
        TreeNode<String> root = t.getRoot();

        // call the display sideways method
        t.display();

        // test the find method
        sc = new Scanner(System.in);
        System.out.print("\nInput target: ");
        String target = sc.next(); //"I"

        boolean itemFound = t.find(target);
        if (itemFound)
            System.out.println("Found: " + target);
        else
            System.out.println(target + " not found.");

        // test the min and max methods
        System.out.println("Min = " + t.min());
        System.out.println("Max = " + t.max());

        // inorder traversal display the values from smallest to largest
        System.out.println("\nIn Order: ");
        t.smallToLarge();
    }
}

class BinarySearchTree<E extends Comparable> {

    private TreeNode<E> root;

    // default constructor
    public BinarySearchTree(){ root = null; }

    // return root
    public TreeNode<E> getRoot() {
        return root;
    }

    /****************************************************************
     Recursive algorithm to build a BST:  if the node is null, insert the
     new node.  Else, if the item is less, set the left node and recur to
     the left.  Else, if the item is greater, set the right node and recur
     to the right.
     *****************************************************************/

    /**
     * precondition: none
     * postcondition: inserts an element into the correct position in a BST
     */
    public TreeNode<E> insert(E s) {
        root = insert(root, s);
        return root;
    }

    // insert helper
    private TreeNode<E> insert(TreeNode<E> t, E s) {
        if (t == null) {
            t = new TreeNode<>(s);
            return t;
        }
        else if (t.getValue().compareTo(s) >= 0) {
            t.setLeft(insert(t.getLeft(), s));
        } else if (t.getValue().compareTo(s) < 0){
            t.setRight(insert(t.getRight(), s));
        }
        return t;
    } // insert

    /**
     * precondition: none
     * postcondition: display the BST sideways
     */
    public void display() {
        display(root, 0);
    }

    // display helper method and it is a private method
    private void display(TreeNode<E> t, int level) {
        if(t != null){
            display(t.getRight(), level + 1);

            //print spaces
            for(int i = 0; i < level; i++)
                System.out.print("      ");

            System.out.println(t.getValue());
            display(t.getLeft(), level + 1);
        }
    }

    /***************************************************************
     Iterative algorithm:  create a temporary pointer p at the root.
     While p is not null, if the p's value equals the target, return true.
     If the target is less than the p's value, go left, otherwise go right.
     If the target is not found, return false.

     Find the target. Recursive algorithm:  If the tree is empty,
     return false.  If the target is less than the current node
     value, return the left subtree.  If the target is greater, return
     the right subtree.  Otherwise, return true.
     . ****************************************************************/

    /**
     * precondition: take in an element
     * postcondition: return true if x is found in the BST, false otherwise
     */
    public boolean find(E x) {
        return find(root, x);
    }

    // helper method of find
    private boolean find(TreeNode<E> t, E x) {

        if(t == null)
            return false;

        if(t.getValue().compareTo(x) > 0)
            return find(t.getLeft(), x);
        else if(t.getValue().compareTo(x) < 0)
            return find(t.getRight(), x);
        else
            return true;
    }

    /***************************************************************
     starting at the root, return the min value in the BST.
     Use iteration.   Hint:  look at several BSTs. Where are
     the min values always located?
     ***************************************************************/

    /**
     * precondition: none
     * postcondition: returns the min element in the BST
     */
    public E min(){ // you might need to write a helper method for min
        return min(root);
    }

    // min helper
    private E min(TreeNode<E> t){

        if(t != null){
            while(t.getLeft() != null)
                t = t.getLeft();

            return t.getValue();
        }
        return null;
    }

    /*****************************************************************
     starting at the root, return the max value in the BST.
     Use recursion!
     *****************************************************************/

    /**
     * precondition: none
     * postcondition: returns the max element in the BST
     */
    public E max(){ // you might need to write a helper method for max
        return max(root);
    }

    // max helper
    private E max(TreeNode<E> t){
        if(t == null){
            return null;
        }
        else if(t.getRight() == null){
            return t.getValue();
        }
        else
            return max(t.getRight());
    }

    /**
     * precondition: none
     * postcondition: prints the BST in order
     */
    public void smallToLarge(){  // need a helper method as well?
        inOrder(root);
    }

    // small to large helper
    private void inOrder(TreeNode<E> root){
        if(root != null){
            inOrder(root.getLeft());
            System.out.print(root.getValue() + " ");
            inOrder(root.getRight());
        }
    }


    //classwork test

    boolean isChild(TreeNode<E> t, TreeNode<E> r){
        return t!= null && t.getLeft() == r || t.getRight() == r;
    }

    boolean isDescendant(TreeNode<E> first, TreeNode<E> second){
        if(first == null)
            return false;
        else if(isChild(first, second)){
            return true;
        }
        else
            return isDescendant(first.getLeft(), second) || isDescendant(first.getRight(), second);
    }

    public TreeNode<E> insertBST(TreeNode<E> t, E value){
        if(t == null)
            t = new TreeNode<E>(value);
        else if(value.compareTo(t.getValue()) <= 0)
            t.setLeft(insertBST(t.getLeft(), value));
        else
            t.setRight(insertBST(t.getRight(), value));

        return t;
    }

    public TreeNode<E> removeBST(TreeNode<E> t, E value){
        if(t == null)
            return null;
        else if(t.getValue().compareTo(value) > 0)
            t.setLeft(removeBST(t.getLeft(), value));
        else if(t.getValue().compareTo(value) < 0)
            t.setRight(removeBST(t.getRight(), value));
        else{
            if(t.getRight() == null)
                return t.getLeft();
            else if(t.getLeft() == null)
                return t.getRight();
            else{
                //t.setValue(getMin(t.getRight()).getValue());
                t.setRight(removeBST(t.getRight(), t.getValue()));
            }
        }
        return t;
    }

    public void preOrder(TreeNode<E> t){
        if(t != null){
            System.out.println(t.getValue());
            preOrder(t.getLeft());
            preOrder(t.getRight());
        }
    }

    public void postOrder(TreeNode<E> t){
        if(t != null){
            postOrder(t.getLeft());
            postOrder(t.getRight());
            System.out.println(t.getValue());
        }
    }

    public void inOrder2(TreeNode<E> t){
        if(t != null){
            inOrder2(t.getLeft());
            System.out.println(t.getValue());
            inOrder2(t.getRight());
        }
    }

    public void levelOrder(TreeNode<E> t){
        if(t != null){
            Queue<TreeNode<E>> queue = new LinkedList<>();

            queue.add(t);
            while(!queue.isEmpty()){
                TreeNode<E> temp = queue.remove();
                System.out.println(temp.getValue());

                if(temp.getLeft() != null)
                    queue.add(temp.getLeft());
                if(temp.getRight() != null)
                    queue.add(temp.getRight());
            }
        }
    }

    public E minNodeBT(TreeNode<E> t){
        if(t == null)
            return null;
        else{
            E value = t.getValue();
            E lValue = t.getLeft().getValue();
            E rValue = t.getRight().getValue();

            if(lValue.compareTo(value) < 0)
                value = lValue;
            if(rValue.compareTo(value) < 0)
                value = rValue;

            return value;
        }
    }

    public TreeNode<E> minNodeBST(TreeNode<E> t){
        if(t == null)
            return null;
        else if(t.getLeft() == null)
            return t;
        else
            return minNodeBST(t.getLeft());
    }

    public TreeNode<E> maxNodeBST(TreeNode<E> t){
        if(t == null)
            return null;
        else if(t.getRight() == null)
            return t;
        else
            return maxNodeBST(t.getRight());
    }

    public E maxNodeBT(TreeNode<E> t){
        if(t == null)
            return null;
        else{
            E value = t.getValue();
            E lValue = t.getLeft().getValue();
            E rValue = t.getRight().getValue();

            if(lValue.compareTo(value) > 0)
                value = lValue;
            if(rValue.compareTo(value) > 0)
                value = rValue;

            return value;
        }
    }

    public int countNode(TreeNode<E> t){
        if(t == null)
            return 0;
        else
            return 1 + countNode(t.getLeft()) + countNode(t.getRight());
    }

    public int sumOfAllNodes(TreeNode<E> t){
        if(t == null)
            return 0;
        else
            return (int)t.getValue() + sumOfAllNodes(t.getLeft()) + sumOfAllNodes(t.getRight());
    }

    public int countLeaf(TreeNode<E> t){
        if(t == null)
            return 0;
        else if(t.getLeft() == null && t.getRight() == null)
            return 1;
        else
            return countLeaf(t.getLeft()) + countLeaf(t.getRight());
    }

    public int sumLN(TreeNode<E> t){
        if(t == null)
            return 0;
        else if(t.getLeft() == null && t.getRight() == null)
            return (int)t.getValue();
        else
            return sumLN(t.getLeft()) + sumLN(t.getRight());
    }

    public boolean isLeaf(TreeNode<E> t){
        return t != null && t.getRight() == null && t.getLeft() == null;
    }

    public boolean isBST(TreeNode<E> t){
        if(t == null)
            return false;
        if(t.getLeft() != null && t.getLeft().getValue().compareTo(t.getValue()) > 0)
            return false;
        if(t.getRight() != null && t.getRight().getValue().compareTo(t.getValue()) < 0)
            return false;
        if(!isBST(t.getLeft()) || !isBST(t.getRight()))
            return false;

        return true;
    }

    public boolean sameShape(TreeNode<E> t, TreeNode<E> t2){
        if(t == null && t2 == null)
            return true;
        else if(t == null || t2 == null)
            return false;
        else
            return sameShape(t.getLeft(), t2.getLeft()) && sameShape(t.getRight(), t2.getRight());
    }

    public boolean equal(TreeNode<E> t, TreeNode<E> t2){
        if(t == null && t2 == null)
            return true;
        if(t != null && t2 != null)
            return t.getValue().equals(t2.getValue()) && equal(t.getLeft(), t2.getLeft()) && equal(t.getRight(), t2.getRight());

        return false;
    }

    public TreeNode<E> clone(TreeNode<E> t){
        if(t == null)
            return new TreeNode<E>(null);
        else
            return new TreeNode<E>(t.getValue(), clone(t.getLeft()), clone(t.getRight()));
    }

    public TreeNode<E> mirror(TreeNode<E> t){
        if(t == null)
            return new TreeNode<E>(null);
        else
            return new TreeNode<E>(t.getValue(), mirror(t.getRight()), mirror(t.getLeft()));
    }

    public boolean isChild2(TreeNode<E> parent, TreeNode<E> child){
        return parent != null && parent.getLeft() == child || parent.getRight() == child;
    }

    public boolean isDescendant2(TreeNode<E> t, TreeNode<E> t2){
        if(t != null){
            if(isChild(t, t2) || isDescendant2(t.getLeft(), t2) || isDescendant2(t.getRight(), t2)){
                return true;
            }
        }
        return false;
    }

}  // BinarySearchTree

/* TreeNode class for the AP Exams */

class TreeNode<E> {
    private E value;
    private TreeNode left, right;

    public TreeNode(E initValue) {
        value = initValue;
        left = null;
        right = null;
    }

    public TreeNode(E initValue, TreeNode<E> initLeft, TreeNode<E> initRight) {
        value = initValue;
        left = initLeft;
        right = initRight;
    }

    public E getValue() {
        return value;
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setValue(E theNewValue) {
        value = theNewValue;
    }

    public void setLeft(TreeNode<E> theNewLeft) {
        left = theNewLeft;
    }

    public void setRight(TreeNode<E> theNewRight) {
        right = theNewRight;
    }

}

/* Program Output

Input string: AMERICAN
            R
                  N
      M
                  I
            E
                  C
A
      A

Input target: X
X not found.
Min = A
Max = R

In Order:
A A C E I M N R


Input string: MAENIRAC
            R
      N
M
                  I
            E
                  C
      A
            A

Input target: I
Found: I
Min = A
Max = R

In Order:
A A C E I M N R


Input string: AACEIMNR
                                    R
                              N
                        M
                  I
            E
      C
A
      A

Input target: R
Found: R
Min = A
Max = R

In Order:
A A C E I M N R


Input string: COMPUTER
                  U
                        T
                              R
            P
      O
            M
                  E
C

Input target: U
Found: U
Min = C
Max = U

In Order:
C E M O P R T U

Process finished with exit code 0

*/