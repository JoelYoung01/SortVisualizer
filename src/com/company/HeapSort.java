package com.company;

public class HeapSort extends Sort {
    private int count;      //variable for tracking the current stage of the sort
    private int currIndex;  //current index of the sort, linear relation to the count variable
    private int minIndex;   //current index of the next smallest item
    public HeapSort(Bar[] arr) {
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
        int min;
        Bar temp;
        for(int i = 0; i < array.length; i++) {
            min = i;
            for (int j = i+1; j < array.length; j++) {
                if(array[min].compareTo(array[j]) > 0) min = j;
            }
            if (min != i) {
                temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }
}
