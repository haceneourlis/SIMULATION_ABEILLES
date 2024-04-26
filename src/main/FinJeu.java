package main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class FinJeu extends JFrame implements ActionListener {

    FinJeu() {
        this.setTitle("THEM BEEEEZ");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel label_Text = new JLabel("GAME OVER");
        panel.add(label_Text);

        JButton jb = new JButton("Rejouer");
        jb.setFocusable(false);
        jb.addActionListener(this);
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
                new GameFrame();
            }
        }
    }
}
