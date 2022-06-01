/*
Name: Veljko Cvetkovic
What I learned:
1. I learned how to join two sets together
2. I learned what subsets and supersets are
3. I also learned how to find the differences between two sets
*/

import java.util.*;
public class P6VeljkoCvetkovicMySetHomework {

    public static void main(String[] args) {

        Object[] list1 = {-1,99,80,85,17,30,84,2,16,1};
        Object[] list2= {-1, 17, 10, 63, 2};
        Object[] list3 = {-1, 80, 1, 84, 30};
        Object[] list4 = {-1,99,80,85};

        // use the above lists to create four MySet objects: s1, s2, s3, s4
        // Note: MySet extends HashSet which implements Set
        MySet<Object> s1 = new MySet <>();
        MySet s2 = new MySet();
        MySet s3 = new MySet();
        MySet s4 = new MySet();

        for(Object x: list1){
            s1.add(x); // calling add method in Set
        }
        for(Object x: list2) {
            s2.add(x);
        }
        for(Object x: list3) {
            s3.add(x);
        }
        for(Object x: list4) {
            s4.add(x);
        }
        System.out.println("s1: " + s1 + "\ns2: " + s2 + "\ns3: " + s3 + "\ns4: " + s4);

        //should print -1, 99, 80, 85, 17, 30, 84, 2, 16, 1, 10, 63
        System.out.println("\nUnion of s and s2: " + s1.union(s2));

        //should print -1, 17, 2
        System.out.println("\nIntersection of s and s2: " + s1.intersect(s2));

        //should print 99, 80, 85, 30, 84,16,1
        System.out.println("\nDifference between s and s2: " + s1.difference(s2));

        //should print false
        System.out.println("\nIs s a subset of s3? " + s1.subset(s3));

        //should print true
        System.out.println("\nIs s a superset of s4? " + s1.superset(s4));
    } // main
}  // MySetDriver

// MySet is an extension of Set. It has union, intersect, difference, subset, superset
// methods defined in it.
class MySet<E> extends HashSet {

    //private Set<E> set;

    //Default Constructor
    public MySet() {}

    //preconditions: none
    //postconditions: returns the combined Set of the calling Set and s
    public Set<E> union (Set<E> s) {

        Iterator<E> iter = this.iterator();
        Set<E> temp = new HashSet<>();

        while(iter.hasNext()){
            temp.add(iter.next());
        }

        for(E value: s)
            temp.add(value);

        return temp;
    }

    //preconditions: none
    //postconditions: returns a Set of common elements between two sets
    public Set<E> intersect (Set<E> s) {

        Set<E> temp = new HashSet<>();

        Iterator<E> iter = this.iterator();

        while(iter.hasNext()){
            E value = iter.next();
            if(s.contains(value))
                temp.add(value);
        }
        return temp;
    }

    //preconditions: none
    //postconditions: returns the differences between two sets as a Set
    public Set<E> difference (Set<E> s) {

        Set<E> temp = new HashSet<>();

        Iterator<E> iter = this.iterator();

        while(iter.hasNext()){
            E value = iter.next();
            if(!s.contains(value))
                temp.add(value);
        }
        return temp;
    }

    //preconditions: none
    //postconditions: returns true if the calling Set is a subset of s; false otherwise
    public boolean subset(Set<E> s) {

        Iterator<E> iter = this.iterator();

        if(this.containsAll(s))
            return false; // return false if sets are equal

        while(iter.hasNext()){
            if(!s.contains(iter.next()))
                return false;

        }
        return true;
    }

    //preconditions: none
    //postconditions: returns true if the calling Set is a superset of s; false otherwise
    public boolean superset(Set<E> s) {

        for (E e : s) {
            if (!this.contains(e))
                return false;
        }
        return true;
    }

} // MySet

/*

 s1: [-1, 80, 16, 17, 1, 2, 99, 84, 85, 30]
 s2: [-1, 17, 2, 10, 63]
 s3: [-1, 80, 1, 84, 30]
 s4: [-1, 80, 99, 85]

 Union of s1 and s2: [-1, 80, 16, 17, 1, 2, 99, 84, 85, 10, 30, 63]
 Intersection of s1 and s2: [-1, 17, 2]
 Difference between s1 and s2: [80, 16, 1, 99, 84, 85, 30]
 Is s1 a subset of s3? false
 Is s1 a superset of s4? true

Process finished with exit code 0

*/