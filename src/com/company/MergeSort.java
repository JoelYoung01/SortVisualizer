package com.company;
import java.lang.Math;

public class MergeSort extends Sort implements Runnable {
    Thread myThread;
    boolean noBreak;
    boolean pause;
    boolean started;
    public MergeSort(Bar[] arr) {
        super(arr);
        noBreak = false;
        started = false;
        pause = true;

        myThread = new Thread(this, "mergeThread");
    }

    @Override
    public boolean nextStep() {
        if (!started) { myThread.start(); started = true; }
        pause = false;
        myThread.notifyAll();
        return false;
    }

    @Override
    public void run() {
        System.out.println("runno");
        mergesort(0, array.length);
    }

    //todo figure out how to give these methods control of the monitor? so it can stop the thread?
    public void mergesort(int start, int stop) {
        if (start+1 == stop) return;
        System.out.print("New Array: " );
        for (int i = start; i < stop; i++) System.out.print(array[i] + ", ");
        System.out.println();

        int middleIndex = (stop - start) / 2 + start;
        mergesort(start, middleIndex);
        mergesort(middleIndex, stop);

        merge(start, middleIndex, stop);
    }
    public void merge(int count1, int count2, int end2) {
        while (count1 < count2 && count2 < end2) {
            array[count1].select();
            array[count2].select();
            pause();
            if(array[count1].compareTo(array[count2]) > 0) {
                /* If this is the case, the bar at count2 index must be inserted where count1 is
                and the count1 array must be shifted back to fill the spot where count2 used to be. */
                Bar temp = array[count2];
                for(int i = 0; i < count2 - count1; i++) array[i+count1+1] = array[i+count1];
                array[count1] = temp;

                pause();
                array[count1].deselect();
                count1++;
                count2++;
            } else {
                //if this is the case, count1 is already in the right place, no placing needed
                pause();
                array[count1].deselect();
                count1++;
            }
        }
        while (count1 < count2) {
            array[count1].select();
            pause();
            array[count1].deselect();
            count1++;
        }
        while (count2 < end2) {
            array[count2].select();
            pause();
            array[count2].deselect();
            count2++;
        }
    }

    public void pause() {
        System.out.println("paused");
        try {
            myThread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*  These two methods are recursion sort, they basically divide and conquer. there are no pause points that are bypassed when the nextStep method is called.*/
    @Override
    public void instaSort() {
        Bar[] temp = mergesortQuick(array);  //call the mergesort method, starts the process
        deepCopy(temp, array);          //copies the result to the final array
    }

    /*
    * The goal here is to sort sections of the array at a time, by first doing lower level and
    * slowly increasing the size of merging pieces until you are just merging the two halves,
    * and finally you are done.
    */
    public Bar[] mergesortQuick(Bar[] arr) {
        int size = 2;
        int index1 = 0, index2 = index1 + size-1, maxIndex;
        while (size < arr.length) {
            //debug purpose, deleteme
            int i1 = index1, i2 = index2;
            maxIndex = Math.min(index1 + size - 1, array.length-1);
            // merge two halves together
            for (int i = index1; i < index1+size; i++) {
                if (index1 == index2 || index2 > maxIndex) break;
                if (array[index1].getValue() < array[index2].getValue()) index1++;
                else {
                    Bar temp = array[index2];
                    if (index2 - index1 >= 0)   // Shift unsorted left half of array to the right by one element
                        System.arraycopy(array, index1, array, index1 + 1, index2 - index1);
                    array[index1] = temp;
                    index1++;
                    index2++;
                }
            }
            // debug, print current iteration info
            System.out.println("    " + this + " i1: " + i1 + ", i2: " + i2);

            // update current sort section
            index1 += size;
            index2 += size;
            if (index2 >= array.length) {   // if the end of array is reached, restart with a larger sort interval
                size *= 2;
                index1 = 0;
                index2 = index1 + size-1;
            }
        }
        return array;
    }
}
