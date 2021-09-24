package com.company;

public class QuickSort extends Sort {
    private int count;      //variable for tracking the current stage of the sort
    private int currIndex;  //current index of the sort, linear relation to the count variable
    private int minIndex;   //current index of the next smallest item
    public QuickSort(Bar[] arr) {
        super(arr); //use the Sort class' constructor
        count = 0;  //set the count of the sort to 0
    }
    public boolean nextStep() {
        if (count%3 == 0 && sortComplete()) return false;   //if the sort is done, return false, only checks once every five counts
        currIndex = count/3;
        if (count%3 == 0) {     //find minimum value and select it
            minIndex = currIndex;       //set the min index to the current index
            for(int i = currIndex+1; i < array.length; i++) {   //find and set the min value of the array, starting at the index
                if (array[minIndex].compareTo(array[i]) > 0) minIndex = i;  //if value is lower set the new minIndex
            }
            array[currIndex].select();  //select the two bars being swapped
            array[minIndex].select();
        } else if (count%3 == 1) {  //swap the min value with the current index (div two)
            Bar temp = array[currIndex];
            array[currIndex] = array[minIndex];
            array[minIndex] = temp;
        } else {    //deselect the two items swapped
            array[currIndex].deselect();
            array[minIndex].deselect();
        }
        count++;    //inc the count
        return true;    //return true (a step was done)
    }

    /* Ultimately what happens is it goes through the entire array index by index, and at
    * each step swaps the current item at the index with the lowest value bar
    * that comes after the index, effectively sorting the array progressively
    * as it moves through the array. */
    public void instaSort() {
        partition(0, 1, array.length);
    }
    public void partition(int pivot, int start, int end) {
        /* Input:   pivot - index of pivot element in array
                    start - start index of partition section, inclusive
                    end - end index of partition section, exclusive
                    */
        int Lo = end, Hi = start;
        boolean hiStuck = false, loStuck = false;
        while (Hi >= Lo) {
            //update lo counter
            if(!loStuck && array[Lo].getValue() > array[pivot].getValue()) loStuck = true;
            else Lo++;

            //update hi counter
            if(!hiStuck && array[Hi].getValue() < array[pivot].getValue()) hiStuck = true;
            else Hi--;

            //swap if necessary
            if (hiStuck && loStuck) {
                Bar temp = array[Hi];
                array[Hi] = array[Lo];
                array[Lo] = temp;
            }
        }
    }
}
