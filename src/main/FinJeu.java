package main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class FinJeu extends JFrame implements ActionListener {

    FinJeu(int score) {
        this.setTitle("THEM BEEEEZ");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label_Text = new JLabel("GAME OVER");
        label_Text.setFont(new Font("Arial", Font.BOLD, 36));
        label_Text.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label_Text);
        panel.add(Box.createVerticalStrut(100));

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoreLabel);
        panel.add(Box.createVerticalStrut(100));

        JLabel pollenStatus = new JLabel("Pollen épuisé");
        pollenStatus.setFont(new Font("Arial", Font.PLAIN, 24));
        pollenStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pollenStatus);

        panel.add(Box.createVerticalStrut(100));

        JButton jb = new JButton("Rejouer");
        jb.setFocusable(false);
        jb.addActionListener(this);
        jb.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(jb);

        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Rejouer")) {
                this.dispose();
                try {
                    new GameFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
