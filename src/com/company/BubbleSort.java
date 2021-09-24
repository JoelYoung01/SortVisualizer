package com.company;

public class BubbleSort extends Sort {
    private int index;          //keeps track of current index of sort
    private int swappingIndex;  //keeps track of index of item being swapped back
    private int currentStage;   //variable for tracking state of pushing back
    private boolean spotFound;      //variable for tracking state of pushing back

    /* Includes Bar[] array from Sort */
    public BubbleSort(Bar[] arr) {
        super(arr); //use the Sort class' constructor
        //array = arr; occurring in superclass
        index = 1;  //start the count at the second item in the array
        swappingIndex = index; // set up the swapping index as well
        spotFound = false;
    }

    public boolean nextStep() {
        if (currentStage == 0 && sortComplete()) return false;   //if the sort is done, return false
        switch (currentStage) {
            //select bars
            case 0:
                Bar temp;
                array[swappingIndex].select();
                array[swappingIndex - 1].select();
                if (array[swappingIndex].getValue() < array[swappingIndex-1].getValue()) {
                    temp = array[swappingIndex - 1];
                    array[swappingIndex - 1] = array[swappingIndex];
                    array[swappingIndex] = temp;
                } else {
                    spotFound = true;  //if the bar is in the right place, set swapping index to zero so that the loop restarts
                }
                break;
            //deselect bars
            case 1:
                array[swappingIndex].deselect();
                array[swappingIndex - 1].deselect();
            //move one space back
                swappingIndex--;
                if (spotFound || swappingIndex < 1) {
                    index++;
                    swappingIndex = index;
                    spotFound = false;
                }
                break;
        }
        currentStage = (currentStage + 1) % 2;  //cycle the current stage counter
        return true;    //return true (a step was done)
    }

    /* What is going on here is the pointer is going through the array,
     * looking at the item at index i, and then pushing it back through
     * the already-sorted items until it is no longer smaller than its
     * previous item. */
    public void instaSort() {
        int newItemIndex;
        Bar temp;
        for (int i = 0; i < array.length; i++) {
            newItemIndex = i;
            while (newItemIndex > 0 && array[newItemIndex].getValue() < array[newItemIndex - 1].getValue()) {
                temp = array[newItemIndex - 1];
                array[newItemIndex - 1] = array[newItemIndex];
                array[newItemIndex] = temp;
                newItemIndex--;
            }
        }
    }
}
