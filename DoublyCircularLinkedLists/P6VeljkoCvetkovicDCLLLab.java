/***********************************************************************************************************************************************
 * Name: Veljko Cvetkovic
 * Period: 6
 * Name of the Lab: Doubly Circular Linked Lists (with a dummy header node)
 * Purpose of the Program: To provide a variety of methods for using doubly circular linked lists.
 * Due Date: 11/16/21
 * Date Submitted: 11/16/21
 *
 * What I learned:
 *  1. I learned that you have to reconnect the links of the nodes when you remove or add a new node into the list.
 *  2. I also learned that getting the first and last node with this kind of structure is very easy and has a big-oh O(1).
 *  3. The dummy header node has a value of null but is pointing to either itself in both directions (if there are no nodes)
 *  or to other nodes, which means that operations such as "head.getNext()" will never cause a NullPointerException.

 * How I feel about this lab: I thought this lab was interesting, and it made me realize the power of drawing diagrams to
 * figure out how to do a certain operation, and the idea behind it, before writing any code. I also think that it was interesting
 * to see the similarities and differences between these methods for other types of linked lists such as singly-linked and doubly-linked.
 *
 * What I wonder: I wonder where doubly circular linked lists are used in the real world, and what the advantage is
 * over other popular data structures.
 *
 * Student(s) who helped me (to what extent): I did this myself.
 *************************************************************************************************************************************************/

public class P6VeljkoCvetkovicDCLLLab{

    public static void main(String args[]){

        DCLL <String> list = new DCLL <String> ();

        list.addLast("Apple");
        list.addLast("Banana");
        list.addLast("Cucumber");
        list.add("Dumpling");
        list.add("Escargot");
        System.out.println(list);
        System.out.println("Size: " + list.size());

        Object obj = list.remove(3);
        System.out.println(list);

        System.out.println("Size: " +list.size());
        System.out.println("Removed "+ obj);

        System.out.print("Add at 3:   ");
        list.add(3,"Cheese");
        System.out.println(list);

        System.out.println("Get values at 1 and first: " + list.get(1)+" and " + list.getFirst());
        System.out.println("No change: " +list);

        System.out.println(list.removeLast() + " is now removed!");
        System.out.println(list);

        System.out.print("Add first:  ");
        list.addFirst("Anchovie");
        System.out.println(list);
        System.out.println("Size: " + list.size());

        System.out.print("Set the second:  ");
        list.set(2, "Bread");
        System.out.println(list);

    } // main
}

class DCLL <E>
{
    //*********************************************************************************************  DLL class

    private int size;
    //dummy node--very useful--simplifies the code
    private DLNode <E> head = new DLNode <E> ();

    /*
     pre: List is provided
     post: return size
    */
    public int size(){
        return size;
    }

    /*
     pre: List is provided
     post: appends obj to end of list; increases size.
     @return true
    */
    public boolean add(E obj){
        addLast(obj); // call to addLast
        return true;
    } // add

    /*
     pre: List is provided
     post: inserts obj at position index. Increments size.
    */
    public void add(int index, E obj){

        if(index < 1 || index > size + 1){ // index < 1 in order to preserve dummy header node
            throw new IndexOutOfBoundsException();
        }

        DLNode<E> temp = head;
        for(int i = 0;i < index-1; i++){ // go to index before insertion location
            temp = temp.getNext();
        }
        temp.getNext().setPrev(new DLNode<E>(obj, temp, temp.getNext()));
        temp.setNext(temp.getNext().getPrev());
        size++;
    } // add

    /*
     pre: List is provided
     post: returns the value at index.
    */
    public E get(int index){

        if(index < 1 || index > size){
            throw new IndexOutOfBoundsException();
        }

        DLNode<E> temp = head;
        for(int i = 0; i < index; i++)
            temp = temp.getNext();

        return temp.getValue();
    } // get

    /*
     pre: List is provided
     post: replaces obj at position index.
    */
    public void set(int index, E obj){

        if(index < 1 || index > size){
            throw new IndexOutOfBoundsException();
        }

        DLNode<E> temp = head;
        for(int i = 0; i < index; i++){
            temp = temp.getNext();
        }
        temp.setValue(obj);
    } // set

    /*
     pre: List is provided
     post: removes the node from position index; decrements size.
     @return the object at position index.
     */
    public E remove(int index){

        if(index < 1 || index > size){ //don't allow for the dummy header node to be removed
            throw new IndexOutOfBoundsException();
        }

        DLNode<E> temp = head;
        for(int i = 0; i < index;i++){
            temp = temp.getNext();
        }
        E value = temp.getValue();

        // reconnect links
        temp.getPrev().setNext(temp.getNext());
        temp.getNext().setPrev(temp.getPrev());
        size--;

        return value;
    } // remove


    /*
     pre: List is provided
     post: inserts obj at front of list; increases size.
    */
    public void addFirst(E obj){

        head.getNext().setPrev(new DLNode<E>(obj, head, head.getNext()));
        head.setNext(head.getNext().getPrev());
        size++;
    } // addFirst


    /*
     pre: List is provided
     post: appends obj to end of list; increases size.
    */
    public void addLast(E obj){

        head.getPrev().setNext(new DLNode<E>(obj, head.getPrev(), head));
        head.getPrev().getNext().setPrev(head.getPrev());
        head.setPrev(head.getPrev().getNext());
        size++;
    } // addLast


    /*
     pre: List is provided
     post: return first value
    */
    public E getFirst(){
        return head.getNext().getValue();
    } // getFirst


    /*
     pre: List is provided
     post: return last value
    */
    public E getLast(){
        return head.getPrev().getValue();
    } // getLast


    /*
     pre: List is provided
     post: remove first value and return that value
    */
    public E removeFirst(){
        return remove(1);
    } // removeFirst


    /*
     pre: List is provided
     post: remove last value and return that value
    */
    public E removeLast(){

        E value = head.getPrev().getValue();
        head.getPrev().getPrev().setNext(head);
        head.setPrev(head.getPrev().getPrev());
        size--;

        return value;
    } // removeLast


    /*
     pre: List is provided
     post: print out the list provided
    */
    public String toString(){

        DLNode<E> temp = head.getNext();
        String t = "[";
        while(temp != head){
            t += temp.getValue();
            temp = temp.getNext();
            if(temp != head){
                t += ", ";
            }
        }
        t += "]";
        return t;
    } // toString
}  // DCLL


class DLNode <E>
{
    private E value;
    private DLNode prev;
    private DLNode next;
    public DLNode(E arg, DLNode <E> p, DLNode <E> n)
    {
        value=arg;
        prev=p;
        next=n;
    }
    public DLNode()
    {
        value=null;
        next=this;
        prev=this;
    }
    public void setValue(E arg)
    {
        value=arg;
    }
    public void setNext(DLNode <E> arg)
    {
        next=arg;
    }
    public void setPrev(DLNode <E> arg)
    {
        prev=arg;
    }
    public DLNode <E> getNext()
    {
        return next;
    }
    public DLNode <E> getPrev()
    {
        return prev;
    }
    public E getValue()
    {
        return value;
    }
}  // DLNode


/* Program Output

[Apple, Banana, Cucumber, Dumpling, Escargot]
Size: 5
[Apple, Banana, Dumpling, Escargot]
Size: 4
Removed Cucumber
Add at 3:   [Apple, Banana, Cheese, Dumpling, Escargot]
Get values at 1 and first: Apple and Apple
No change: [Apple, Banana, Cheese, Dumpling, Escargot]
Escargot is now removed!
[Apple, Banana, Cheese, Dumpling]
Add first:  [Anchovie, Apple, Banana, Cheese, Dumpling]
Size: 5
Set the second:  [Anchovie, Bread, Banana, Cheese, Dumpling]

Process finished with exit code 0

*/