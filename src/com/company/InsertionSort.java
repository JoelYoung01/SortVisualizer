package com.company;

public class InsertionSort extends Sort {
    private int index;          //keeps track of current index of sort
    private int swappingIndex;  //keeps track of index of item being swapped back
    private int currentStage;   //variable for tracking state of pushing back

    /* Includes Bar[] array from Sort */
    public InsertionSort(Bar[] arr) {
        super(arr); //use the Sort class' constructor
        //array = arr; occurring in superclass
        index = 1;  //start the count at the second item in the array
        currentStage = 0; //set current stage to the first stage
    }

    public boolean nextStep() {
        if (index == array.length) return false;   //if the sort is done, return false
        switch (currentStage) {
            case 0:
                //find and select bars
                swappingIndex = index;
                while (swappingIndex > 0 && array[swappingIndex-1].getValue() > array[index].getValue()) {
                    swappingIndex--;
                }
                array[index].select();
                array[swappingIndex].select();

                break;

            case 1:
                //shift array forward to make space
                Bar temp = array[index];
                for (int i = index; i > swappingIndex; i--) {
                    array[i] = array[i-1];
                }
                array[swappingIndex] = temp;
                array[swappingIndex].deselect();
                array[swappingIndex+1].deselect();
                index++;
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
        int swapIndex;
        Bar temp;
        for (int i = 1; i < array.length; i++) {
            swapIndex = i;
            while (swapIndex > 0 && array[swapIndex].getValue() < array[i].getValue()) {
                temp = array[swapIndex - 1];
                array[swapIndex - 1] = array[swapIndex];
                array[swapIndex] = temp;
                swapIndex--;
            }

        }
    }
}
