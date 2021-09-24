package com.company;

import java.awt.*;

public class Bar implements Commons{
    private int value;          //value representing the bar
    private boolean selected;   //boolean representing if this bar is being selected
    private enum BarStates { SELECTED, DESELECTED, COMPLETED }
    private BarStates state;
    public Bar(int value) { //constructor used to set value
        this.value = value;
        state = BarStates.DESELECTED;
    }

    public int getValue() { return value; } //function returns the bar's value
    public void draw(Graphics g, int totalBars, int width, int height, int x, int y) {
        switch(state) { //switch case statement to determine the color of the bar
            case SELECTED: g.setColor(Color.red);  //if selected, the color is red
                break;
            case DESELECTED: g.setColor(Color.blue); //if deselected, color is blue
                break;
            case COMPLETED: g.setColor(new Color(76,187,23)); //if completed, turn color green
                break;
            default: g.setColor(Color.blue);    //default case; make the bar blue
        }
        g.fillRect(x, y, width, height);    //draw bar
        int MAX_UNBORDERED = 100;           //maximum total bars before the black border is removed from the bar
        if (totalBars < MAX_UNBORDERED) {   //if not too many bars, run block
            g.setColor(Color.black);            //set pen to black
            g.drawRect(x, y, width, height);    //draw a border
        }
    }
    public void select() {  //method to set the selected state to true
        state = BarStates.SELECTED;
    }
    public void deselect() {    //method to set the selected state to false
        state = BarStates.DESELECTED;
    }
    public void setComplete() { //method to set the selected state to completed
        state = BarStates.COMPLETED;
    }
    public int compareTo(Bar b) {
        if (value > b.getValue()) return 1;
        else if (value < b.getValue()) return -1;
        else return 0;
    }
    public String toString() {
        return "" + value;
    }
}
