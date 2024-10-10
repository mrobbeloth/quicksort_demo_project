package edu.demo.cs;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.compare;

public class Quicksort<T> {
    private ArrayList<T> data;
    private final Random rand;
    private final Comparator<T> c;
    private boolean verbose = true;

    public ArrayList<T> getData() {
        return data;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public int getTotal_swaps() {
        return total_swaps;
    }

    private int total_swaps;

    public Quicksort() {
        this.data = new ArrayList<>();
        this.rand = new Random();
        this.c = (Comparator<T>) Comparator.comparingInt((T o) -> o.hashCode());
    }

    public Quicksort(ArrayList<T> data, Comparator<T> c) {
        this.data = data;
        rand = new Random();
        this.c = c;
    }

    private <T> T[] swap(T[] a, int first, int second) {
        T swap = a[second];
        a[second] = a[first];
        a[first] = swap;
        if (verbose) printList(a);
        total_swaps++;
        return a;
    }

    private <T> T[] swap(T[] a, Integer first, Integer second) {
        T swap = a[second];
        a[second] = a[first];
        a[first] = swap;
        if (verbose) printList(a);
        total_swaps++;
        return a;
    }

    public <T> void printList(T[] a) {
        for(T i : a) {
            System.out.print(i + " " );
        }
        System.out.println();
    }

    void printList() {
        for(T i : data) {
            System.out.print(i + " " );
        }
        System.out.println();
    }

    public T[] sort(T[] a) {
        return sort(a, 0, a.length);
    }

    private T[] sort(T[] a, int low, int high) {
        if (high <= 1) return a;
        T pivot = a[low + rand.nextInt(high)];
        if (verbose) System.out.println("Pivot is " + pivot);
        int lesser = low-1, equal = low, greater = low+high;
// a[i..lesser]<x, a[lesser+1..greater-1]??x, a[greater..i+n-1]>x
        while (equal < greater) {
            if (verbose) System.out.println("Compare " + a[equal] + " with " + pivot);
            int comp = c.compare(a[equal], pivot);
            if (comp < 0) {
                swap(a, equal++, ++lesser); // move to beginning of array
            } else if (comp > 0) {
                swap(a, equal, --greater); // move to end of array
            } else {
                equal++; // keep in the middle
            }
        }
// a[i..lesser]<x, a[lesser+1..greater-1]=x, a[greater..i+n-1]>x
        sort(a, low, lesser-low+1); // sort left sublist
        sort(a, greater, high-(greater-low)); // sort right sublist
        return a;
    }

    public static void main(String[] args) {

        // Let's test the Quicksort class using integers
        ArrayList<Integer> myList = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            myList.add(r.nextInt(100));
        }

        Quicksort<Integer> q = new Quicksort<>(myList, Integer::compareTo);
        q.setVerbose(false);
        q.printList(myList.toArray());
        long start = System.nanoTime();
        Integer[] nums = q.sort(myList.toArray(new Integer[0]));
        long end = System.nanoTime();
        q.printList(nums);
        System.out.println("Total swaps: " + q.getTotal_swaps());
        System.out.println("Time taken: " + (end - start) + " ns");
        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");

        ArrayList<String> words = new ArrayList<>(100000);
        for(int i = 0; i < 10; i++) {
            char[] word = new char[r.nextInt(8)+3]; // 3 to 10 character words
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + r.nextInt(26));
            }
            words.add(new String(word));
        }

        // Let's test the Quicksort class using made up words
        Quicksort<String> q2 = new Quicksort<>(words, String::compareTo);
        q2.setVerbose(false);
        q2.printList();
        start = System.nanoTime();
        String[] wordArray = q2.sort(words.toArray(new String[0]));
        end = System.nanoTime();
        q2.printList(wordArray);
        System.out.println("Total swaps: " + q2.getTotal_swaps());
        System.out.println("Time taken: " + (end - start) + " ns");
        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
    }
}
