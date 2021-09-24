package com.company;

public abstract class Sort {
    Bar[] array;    //a pointer to the array being used for this sort
    public Sort(Bar[] arr) {
        array = arr;    //assign the address to the pointer being used for the sort
    }

    /* when called, the method steps in the sort process.
    * If the sort is complete, it returns false (cannot step) */
    public abstract boolean nextStep();

    /* returns the current Bar array as a String*/
    public String toString() {
        String s = "[" + array[0];
        for (int i = 1; i < array.length; i++) {
            s += ", " + array[i];
        }
        return s + "]";
    }

    /* This method checks to see if the array is sorted. It does this by
    * checking each bar value to the index of a for loop, which increments by 1 */
    public boolean sortComplete() {
        for (int i = 0; i < array.length; i++) {
            if(array[i].getValue() != i+1) {
                return false;   //if a bar is found out of place, exits the method and returns false
            }
        }
        return true;    //if completes the loop, method returns true (successfully sorted)
    }

    /* This method deep copies two arrays, makes code cleaner. I deep copy arrays all the time in the sort methods.
    * Copies arr1 ONTO arr2. */
    protected void deepCopy(final Object[] arr1, int sourceStart, int sourceEnd, Object[] arr2, int destinationStart) {
        if(sourceEnd < sourceStart || destinationStart < 0 || sourceEnd-sourceStart > arr2.length - destinationStart) {
            //this is thrown whenever the indices of the arrays don't match up, go check your parameters
            throw new IndexOutOfBoundsException("The indices of the arrays don't match up, go check your parameters");
        }
        for(int i = 0; i < sourceEnd-sourceStart; i++) {
            arr2[i+destinationStart] = arr1[i+sourceStart];
        }
    }
    /* Same thing as previous, with the starts of both arrays at index pos 0 */
    protected void deepCopy(final Object[] arr1, Object[] arr2) {
        deepCopy(arr1, 0, arr1.length, arr2, 0);
    }

    /* This method uses the desired sort strategy to sort the array all at once. */
    public abstract void instaSort();
}
