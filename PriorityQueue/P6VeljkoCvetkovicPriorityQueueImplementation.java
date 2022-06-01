/**************************************************************************************
 Name: Veljko Cvetkovic
 Date: 2/8/22
 What I learned:

 1. I learned that when you want to remove an element from a heap, you have to first swap it
 with the last element in the heap and then heapDown (bubble down) the node until it reaches
 its proper position.
 2. I learned that to add an element you have to add it to the end of the heap to maintain
 the CBT property and then heapUp (bubble up) the node to the proper position.
 3. I learned that the index of the last non-leaf node is numItems / 2
 4. I learned that the indices of the children of a particular node with index n are 2n and 2n + 1.

 How I feel about this lab: I thought this lab was very interesting and also helpful in realizing
 and understanding the concept of a heap and how to perform its operations.

 I am wondering (the what-if moment): Does the heap memory section implement a priority queue
 or another data structure?

 Credits: none
 ***************************************************************************************/

import java.util.*;
public class P6VeljkoCvetkovicPriorityQueueImplementation<E extends Comparable<E>> {

    private static final int DEFAULT_CAPACITY = 1024;
    private Comparable[] items;// use a 1-D array instead of ArrayList
    private int numItems;// number of elements in items

    public static void main(String[] args) {

        // Create a HeapPriorQueue_shell object to test all the methods in this class
        P6VeljkoCvetkovicPriorityQueueImplementation<Integer> pq = new P6VeljkoCvetkovicPriorityQueueImplementation<>();

        //make heap
        pq.add(32);
        pq.add(20);
        pq.add(12);
        pq.add(5);
        pq.add(52);
        System.out.println(pq);

        System.out.println("Remove: " + pq.remove());
        System.out.println(pq);
        System.out.println("Remove: " + pq.remove());
        System.out.println(pq);
        System.out.println("Add 5:");
        pq.add(5);
        System.out.println(pq);
        System.out.println("Add 99:");
        pq.add(99);
        System.out.println(pq);
        System.out.println("Add 23:");
        pq.add(23);
        System.out.println(pq);
        System.out.println("Peek: " + pq.peek());
        System.out.println("Remove: " + pq.remove());
        System.out.println(pq);
        System.out.println("Peek: " + pq.peek());
        System.out.println(pq);

    }

    public P6VeljkoCvetkovicPriorityQueueImplementation() {

        items = new Comparable[DEFAULT_CAPACITY];
        items[0] = 0;
        numItems = 0;
    }

    public P6VeljkoCvetkovicPriorityQueueImplementation(int initialCapacity) {

        items = new Comparable[initialCapacity+1]; // +1 to account for items[0] = 0
        items[0] = 0;
        numItems = 0;
    }

    // precondition: none
    // postcondition: returns true if empty; false otherwise
    public boolean isEmpty() {
        return numItems == 0;
    }

    // precondition: none
    // postcondition: returns the minimum element
    public E peek() {
        return (E) items[1];
    }

    // precondition: none
    // postcondition: removes and returns the min element
    public E remove() {

        E value = (E) items[1]; //store root element
        reheapDown(1);
        return value;
    }

    // precondition: none
    // postcondition: adds element obj in the correct position
    public boolean add(E obj) {

        if (numItems + 1 >= items.length)
            doubleCapacity();

        items[++numItems] = obj;
        reheapUp();

        return true;
    } // add

    // precondition: none
    // postcondition: prints out the priority queue
    public String toString() {

        String t = "Contents of PQ: ";
        for (int i = 1; i < numItems; i++) {
            t += items[i] + " ";
        }
        t += items[numItems];
        return t;
    }

    // precondition: index == 1; private method for remove
    // postcondition: bubbles down an element until it reaches its correct position
    private void reheapDown(int index) {

        //remove root element
        items[index] = items[numItems];
        numItems--;

        while (index < numItems) {
            //find smallest child node and swap
            if (index * 2 < items.length && items[index * 2] != null && items[index * 2].compareTo(items[index * 2 + 1]) < 0) {
                swap(index * 2, index); //swap
                index = index * 2;
            } else if(index * 2 + 1 < items.length && items[index * 2 + 1] != null && items[index * 2].compareTo(items[index * 2 + 1]) > 0){
                swap(index * 2 + 1, index); //swap
                index = index * 2 + 1;
            }
            else
                return; //node is in correct position
        }
    }

    // precondition: private method for add
    // postcondition: bubbles up an element until it reaches its correct position
    private void reheapUp() {

        int index = numItems/2;
        int childIndex = numItems;
        while (index >= 0) {
            if(items[childIndex].compareTo(items[index]) < 0){
                swap(index, childIndex); //swap
                childIndex = index;
                index = index / 2;
            }
            else
                return; //node is in correct position
        }
    }

    // precondition: 1 <= i <= numItems && 1 <= j <= numItems
    // postcondition: swaps the elements at index i and index j in the array
    private void swap(int i, int j){

        E temp = (E)items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    // precondition: none
    // postcondition: creates a new array with double the capacity
    private void doubleCapacity() {

        Comparable temp[] = new Comparable[items.length * 2];
        for (int i = 0; i < items.length; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

}  //HeapPriorityQueue_shell

/* Sample Output

Contents of PQ: 5 12 20 32 52
Remove: 5
Contents of PQ: 12 32 20 52
Remove: 12
Contents of PQ: 20 32 52
Add 5:
Contents of PQ: 5 20 52 32
Add 99:
Contents of PQ: 5 20 52 32 99
Add 23:
Contents of PQ: 5 20 23 32 99 52
Peek: 5
Remove: 5
Contents of PQ: 20 32 23 52 99
Peek: 20
Contents of PQ: 20 32 23 52 99

Process finished with exit code 0

*/