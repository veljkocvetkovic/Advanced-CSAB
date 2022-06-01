/****************************************************************************
 Name: Veljko Cvetkovic
 Lab Assignment: Heapsort
 Purpose of the program: To perform heapsort on a maxheap, and to create a random
 array of numbers, make a maxheap from it, and sort it.

 What I Learned (be as specific as possible):
 1. I learned how to implement heapsort
 2. I learned how to restore the order property of a max heap using heapDown
 3. I learned how to build a max heap in linear time by starting from the last
 non-leaf node and doing heapDown on each subtree.

 How I feel about this lab: I thought this lab was interesting because it was fun to
 try to implement heapsort in code after learning how to do it on paper.

 What I am wondering: Are heaps used in other sorting algorithms?

 The credits: who and/or what website(s) helped you (must state
 what information you got from the helper or website): none

 Students (names) you helped (to what extent, be specific): none
 ****************************************************************************/

import java.text.DecimalFormat;
public class P6VeljkoCvetkovicHeapsort {
    public static void main(String[] args) {

        //Part 1: Given a max heap, sort it. Do this part first.
        double heap[] = {-1,99,80,85,17,30,84,2,16,1};
        System.out.print("MaxHeap: ");
        display(heap);
        sort(heap);
        System.out.print("Sorted Heap: ");
        display(heap);
        System.out.println("Is Sorted: " + isSorted(heap));

        //Part 2:  Generate 10 random numbers, make a heap, sort it.
        /*
        int SIZE = 10;
        double[] heap = new double[SIZE + 1];
        heap = createRandom(heap);
        System.out.print("Random Array: ");
        display(heap);
        makeHeap(heap);
        System.out.print("MaxHeap: ");
        display(heap);
        sort(heap);
        System.out.print("Sorted Heap: ");
        display(heap);
        System.out.println("Is Sorted: " + isSorted(heap));
        */
    }

    //******* Part 1 ******************************************

    // precondition: takes in an array of doubles
    // postcondition: prints the contents of the heap
    public static void display(double[] array) {

        for(int k = 1; k < array.length; k++)
            System.out.print(array[k] + "    ");
        System.out.println("\n");
    }

    // precondition: array is a max heap
    // postcondition: sorts the array in ascending order
    public static void sort(double[] array) {

        int size = array.length-1;
        while(size > 1){
            swap(array, 1, size);
            size--;
            heapDown(array, 1, size);
        }

        if(array[1] > array[2]) //just an extra swap, if needed.
            swap(array, 1, 2);
    }

    // precondition: 0 < a < array.length && 0 < b < array.length
    // postcondition: swaps the elements at indices a and b
    public static void swap(double[] array, int a, int b) {

        double temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    // precondition: takes in an array, an index, and the size of the heap
    // postcondition: restores the order property of the max heap
    public static void heapDown(double[] array, int k, int size) {

        int left = 2 * k;
        int right = 2 * k + 1;

        if((left <= size && array[k] < array[left]) || (right <= size && array[k] < array[right])){ //node needs to be swapped
            if(left <= size && right > size) { //swap with left child
                swap(array, k, left);
                heapDown(array, left, size);
            }
            else if(right <= size && left > size) { //swap with right child
                swap(array, k, right);
                heapDown(array, right, size);
            }
            else if(array[left] > array[right]) { //swap with largest node
                swap(array, k, left);
                heapDown(array, left, size);
            }
            else {
                swap(array, k, right);
                heapDown(array, right, size);
            }
        }
    }

    // precondition: takes in an array of doubles
    // postcondition: returns true if the given array is sorted in increasing order; false otherwise
    public static boolean isSorted(double[] arr){
        for(int i = 1; i < arr.length-1; i++){
            if(arr[i] > arr[i+1])
                return false;
        }
        return true;
    }

    // ****** Part 2 *******************************************

    //Generate 100 random numbers between 1 and 100, formatted to 2 decimal places
    //postcondition: array[0] == -1, the rest of the array is random
    public static double[] createRandom(double[] array) {

        DecimalFormat d = new DecimalFormat("0.00");
        array[0] = -1;
        for(int i = 1; i < array.length; i++){
            array[i] = Double.parseDouble(d.format((Math.random() * 99) + 1));
        }
        return array;
    }

    //Turn the random array into a MAX heap
    //postcondition: array[0] == -1, the rest of the array is in heap-order
    private static void makeHeap(double[] array) {

        for(int i = array.length/2; i >= 1; i--) {
            heapDown(array, i, array.length-1);
        }
    }

} // HeapSort_shell

/* Program Output

//******* Part 1 ******************************************

MaxHeap: 99.0    80.0    85.0    17.0    30.0    84.0    2.0    16.0    1.0

Sorted Heap: 1.0    2.0    16.0    17.0    30.0    80.0    84.0    85.0    99.0

Is Sorted: true

Process finished with exit code 0

//******* Part 2 ******************************************

Random Array: 54.42    73.46    37.8    2.98    82.53    25.53    10.02    82.36    46.78    92.44

MaxHeap: 92.44    82.53    37.8    82.36    73.46    25.53    10.02    2.98    46.78    54.42

Sorted Heap: 2.98    10.02    25.53    37.8    46.78    54.42    73.46    82.36    82.53    92.44

Is Sorted: true

Process finished with exit code 0

*/