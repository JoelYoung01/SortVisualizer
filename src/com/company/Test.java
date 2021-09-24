package com.company;

public class Test {
    public static void main(String[] args) {
        int size = 10;
        Bar[] array = new Bar[size];
        for(int i = 0; i < size; i++) {
            array[i] = new Bar(i+1);
        }
        Board.shuffle(array);
        System.out.print("Start Array: ");
        if(size < 100) printArray(array);
        else System.out.println(size + " items");

        Sort sort = new MergeSort(array);
        long startTime = System.currentTimeMillis();
        sort.instaSort();
        long endTime = System.currentTimeMillis();

        System.out.print("End Array:   ");
        if(size < 100) printArray(array);
        else System.out.println(size + " items");

        long elapsedTime = endTime - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        elapsedTime %= 1000;
        long elapsedMinutes = elapsedSeconds / 60;
        elapsedSeconds %= 60;
        System.out.println("\nElapsed Time: " + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds, " + elapsedTime + " milliseconds.");
    }

    private static void printArray(Bar[] arr) {
        System.out.print("[");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length-1) System.out.print(", ");
        }
        System.out.println("]");
    }
}