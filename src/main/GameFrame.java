package main;

import javax.swing.*;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("BEEEEZ");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
