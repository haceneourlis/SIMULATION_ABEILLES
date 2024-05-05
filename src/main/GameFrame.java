package main;

import javax.swing.*;
import java.io.IOException;

public class GameFrame extends JFrame {

    GamePanel panel;

    public GameFrame() throws IOException {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Simulation");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
