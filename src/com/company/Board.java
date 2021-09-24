package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//todo maybe move all static methods to their own class?
public class Board extends JPanel implements Commons, ActionListener, ChangeListener {
    private Timer timer;                //timer for animation control
    private boolean inGame;             //boolean for controlling pause
    private JSlider delay;              //jSlider representing the update delay
    private JTextField barCount;        //text field for the number of bars
    private JLabel status;              //JLabel that indicates the status of the sort
    private int speed;                  //int representing animation speed
    private int size;                   //int counting the current number of bars
    private Bar[] array;                //array of bars to be sorted
    private sortTypes currentSort;      //enum variable to keep track of current sort
    private Sort Sorter;                //variable to keep track of the current sort being used
    private enum sortTypes {        //enum variables representing a sort
        //goal : random, bubble, selection, insertion, quick, heap, merge
        RANDOMIZE,
        BUBBLE,
        SELECTION_SORT,
        INSERTION_SORT,
        QUICK_SORT,
        HEAP_SORT,
        MERGE_SORT
    }
    private String[] sortNames = {"Randomize", "Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Heap Sort", "Merge Sort"};

    public Board() { this(DEFAULT_PERIOD, DEFAULT_SIZE); }  //default constructor
    public Board(int speed, int size) {
        this.speed = speed;     //animation speed, is actually a delay amount
        this.size = size;       //size of array to be sorted
        timer = new Timer();    //timer to control speed
        setFocusable(true);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));   //set minimum window size, this doesn't really work
        currentSort = sortTypes.RANDOMIZE;  //default sort type is random

        //TODO make the interface look nicer, not just jumbled together
        //animation speed slider component
        delay = new JSlider(JSlider.HORIZONTAL,0, 1000, DEFAULT_PERIOD);
        delay.addChangeListener(this);
        delay.setMajorTickSpacing(250);
        delay.setPaintTicks(true);
        delay.setPaintLabels(true);
        add(delay);

        //bar amount component
        barCount = new JTextField("" + size, 10);
        barCount.addActionListener(this);
        JLabel barCountLabel = new JLabel("Bar Count:");
        add(barCountLabel);
        add(barCount);

        //add the status text area
        //todo make this work
        status = new JLabel("test");

        //pause and play button
        JButton pauseButton = new JButton("Play / Pause");
        pauseButton.addActionListener(e -> {
            inGame = !inGame;
            if(inGame) {    //branch that fires when being unpaused
                this.actionPerformed(e);    //fires the update for the bar count box
                inGame = true;
            } else {        //branch that fires when being paused
                this.actionPerformed(e);    //fires the update for the bar count box
            }
        });
        add(pauseButton);

        //restart animation button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            inGame = false;
            this.actionPerformed(e);    //fires the update for the bar count box
            initBoard();
        });
        add(restartButton);

        //sort selection option
        String[] sortTypesAvailable = sortNames;
        JComboBox<String> sortSelection = new JComboBox<>(sortTypesAvailable);
        sortSelection.addActionListener(e -> {
            switch ("" + sortSelection.getSelectedItem()) {
                case "Randomize": currentSort = sortTypes.RANDOMIZE; break;
                case "Bubble Sort": currentSort = sortTypes.BUBBLE; break;
                case "Selection Sort": currentSort = sortTypes.SELECTION_SORT; break;
                case "Insertion Sort": currentSort = sortTypes.INSERTION_SORT; break;
                case "Quick Sort": currentSort = sortTypes.QUICK_SORT; break;
                case "Heap Sort": currentSort = sortTypes.HEAP_SORT; break;
                case "Merge Sort": currentSort = sortTypes.MERGE_SORT; break;
                default: System.out.println("on ho no something went wrong"); break;
            }
            this.actionPerformed(e);    //fires the update for the bar count box
            initBoard();
        });
        add(sortSelection);

        initBoard();
    }

    public void initBoard() {
        inGame = false;         //makes sure no drawing happens
        array = new Bar[size];  //creates new Bar array to be sorted
        initTimer();

        //populate and shuffle the Bar Array
        for(int i = 0; i < array.length; i++) {
            array[i] = new Bar(i+1);
        }
        shuffle(array);
        initSort();
    }
    public void initTimer() {   //separate method to initiate timer so it can be called separately
        if (timer != null) {
            timer.cancel();     //stops any current timer, starts a new one
            timer.purge();
        }
        timer = new Timer();    //I hope the old timer object is garbage collected...
        timer.scheduleAtFixedRate(new ScheduleTask(), 0, speed);

    }

    public void initSort() {
        switch (currentSort) {
            case RANDOMIZE: Sorter = new Randomize(array); break;
            case BUBBLE: Sorter = new BubbleSort(array); break;
            case SELECTION_SORT: Sorter = new SelectionSort(array); break;
            case INSERTION_SORT: Sorter = new InsertionSort(array); break;
            case QUICK_SORT: Sorter = new QuickSort(array); break;
            case HEAP_SORT: Sorter = new HeapSort(array); break;
            //case MERGE_SORT: Sorter = new MergeSort(array); Sorter.nextStep();
            default: System.out.println("could not assign " + currentSort + " to sorter type");
        }
    }

    @Override
    public void paintComponent(Graphics g) {    //method is the one being called constantly with the timer
        super.paintComponent(g);
        updateObjects(g);
    }

    public void stateChanged(ChangeEvent e) {
        //animation speed update
        if(!delay.getValueIsAdjusting()) {    //if done adjusting, run this block
            if (delay.getValue() > 0) speed = delay.getValue(); //grab the slider value
            else speed = 1; //if not greater than zero, set speed to 1 (only happens if value is zero, can't have timer delay of 0)
            initTimer();    //restart the sort with the new information
        }
    }

    public void actionPerformed(ActionEvent e) {
        //this actionPerformed method used for BAR COUNT and not all the jComponents in the window
        int newSize;
        try {   //try to grab the stuff in the text box, if fails, set to default size
            newSize = Integer.parseInt(barCount.getText());
        } catch (Exception ex) {
            newSize = size;
        }
        if (newSize > getWidth()) {
            throwError("Nope", newSize + " is too large, defaulting to " + getWidth());
            newSize = getWidth();   //maximum size is the width of the window
        }
        else if (newSize < 1) { //minimum size is 1
            throwError("Nope", newSize + " is too small, defaulting to 1");
            newSize = 1;
        }
        if (newSize != size) {  //if the size variable is to be updated, run this block
            size = newSize;         //set new size value
            initBoard();            //restart board
        }
    }

    //method called at regular intervals, to draw all components and update them
    private void updateObjects(Graphics g) {
        if(inGame) {
            if(!Sorter.nextStep()) endSort(g);
        }
        drawArray(g);
    }

    //method to draw the array (separate method for containment)
    private void drawArray(Graphics g) {
        //set all the variables
        int width = (int) (getWidth() / (double) array.length); //the width of all the individual bars
        int extras = 0;                                         //variable for expanding the bars dynamically
        if (getWidth() % array.length != 0) {   //if the window width isn't a clean factor run this block
            extras = getWidth() % array.length;     //extras is assigned the remainder
        }
        int height;
        int x = 0;                  //x start position is left of the screen, at x = 0
        int startYPos = 60;         //y start position is 60 pixels from the top of the window
        //todo get rid of the weird angle change when its completely sorted
        int maxYPos = (getHeight()-startYPos);
        int count = 0;              //counter for tracking the enhanced for loop
        int supplement;             //variable for expanding the width of the bars
        for (Bar bar : array) {     //runs through the entire bar array
            if (bar != null && bar.getValue() != -1) {      //if the bar value exists, run block
                //max height / ( total items / item value )
                height = (int) (maxYPos / ((double) array.length / (double) bar.getValue()));
                if (count < extras) supplement = 1;         //if count is less than the total remainder, add one to the width
                else supplement = 0;                        //if its not less, do nothing
                bar.draw(g, array.length, width+supplement, height, x, startYPos);  //draw the bar with the new information

                //increment the counter variables
                x = x + width + supplement; //increment the x counter to the new x position
                count++;
            }
        }

        //draw the borderline at the top of the bar area
        g.setColor(Color.black);
        g.fillRect(0, startYPos-3, getWidth(), 3);
    }

    public void endSort(Graphics g) {
        inGame = false;     //stop the sort
        if(Sorter.sortComplete()) {
            for (Bar item : array) { //loop to set all bars in the array to green
                item.setComplete();
            }
        } else {
            for (Bar item : array) { //loop to set all bars in the array to red
                item.select();
            }
        }
        drawArray(g);   //draw the array again
    }

    /* fisher-yates shuffle method (modern style), directly shuffles the array */
    public static void shuffle(Object[] arr) {
        if (arr == null) {  //makes sure the array isn't empty
            return;
        }

        //initialize variables used by shuffle
        Random r = new Random();
        int size = arr.length;
        Object[] results = new Object[size];
        int temp;

        /*Shuffle the items in the array, works like putting all items into hat and randomly drawing them out.
         * Every time an item is drawn from the hat, it is removed from the items to be selected.
         * Time efficiency of 0(n) */
        for(int i = 0; i < size; i++) {
            temp = r.nextInt(size - i ) + i;
            results[i] = arr[temp];
            arr[temp] = arr[i];
        }
        System.arraycopy(results, 0, arr, 0, size); //update the original array
    }

    private void throwError(String title, String message) {
        JFrame temp = new JFrame();
        JOptionPane.showMessageDialog(temp, message, title, JOptionPane.WARNING_MESSAGE);
        temp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //class for the timer iteration
    private class ScheduleTask extends TimerTask {
        //this method runs once every delay period on the timer created
        @Override
        public void run() {
            repaint(); // causes the canvas to repaint every timer fire
        }
    }
}
