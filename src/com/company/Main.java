package com.company;

import java.awt.*;
import javax.swing.JFrame;

public class Main extends JFrame implements Commons{

    public Main() {
        this.setLayout(new GridLayout(1,1));
        initUI();
    }

    private void initUI() {
        Board board = new Board();
        add(board);
        setTitle("Main");
        pack(); //initial pack call to set the inserts
        Dimension defaultDimension = new Dimension(
                Commons.DEFAULT_WIDTH + getInsets().left + getInsets().right,
                Commons.DEFAULT_HEIGHT + getInsets().bottom + getInsets().top );

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(defaultDimension);
        setLocationRelativeTo(null);
        setResizable(true);
        pack();

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main window = new Main();
            window.setVisible(true);
        });
    }
}