package com.company;
import java.util.Arrays;
import java.util.Random;
/*
* This class is the random sort, also known as Bogo Sort.
* This sort is O( (n+1)! ) so it is super duper slow
*/
public class Randomize extends Sort {
    Random r;   //random variable used in selecting array items
    int count;  //count variable to keep track of which stage of the animation we are in
    int index1; //first index to be swapped
    int index2; //second index to be swapped
    public Randomize(Bar[] arr) {
        super(arr);         //call the Sort() constructor to initialize the array
        r = new Random();   //initialize the random variable
        count = 0;          //set count to zero
        index1 = 0;         //set first swap index to 0
        index2 = 0;         //set second swap index to 0
    }
    /*each call to this method steps the process once. it is not hard to do with the Bogo Sort*/
    public boolean nextStep() {
        //if branch to determine the stage of the sort, only three possibilities:
        if (count%3 == 0) {     //first branch, selects the two bars to swap
            if (sortComplete()) return false;  //check if the sort is already done, if so return now
            index1 = r.nextInt(array.length);       //chooses random index in the array
            do index2 = r.nextInt(array.length); while (index2 == index1); //chooses another random index that isnt the first one

            //select both bars being messed with
            array[index1].select();
            array[index2].select();
        } else if (count%3 == 1) {  //next branch, which swaps the two selected bars
            Bar temp = array[index2];
            array[index2] = array[index1];
            array[index1] = temp;
        } else {                //final branch, which simply deselects the two bars.
            array[index1].deselect();
            array[index2].deselect();
        }

        //increment the count
        count++;
        return true;
    }

    /*Ultimately it just picks two random elements and swaps them.
    * over and over.
    * until its sorted. */
    public void instaSort() {
        int ind1;
        int ind2;
        Bar temp;
        do {
            ind1 = r.nextInt(array.length);
            do {
                ind2 = r.nextInt(array.length);
            } while (ind1 == ind2);
            temp = array[ind1];
            array[ind1] = array[ind2];
            array[ind2] = temp;
        } while (!sortComplete());
    }
}
