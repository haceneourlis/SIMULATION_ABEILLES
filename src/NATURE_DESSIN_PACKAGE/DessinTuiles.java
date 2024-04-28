package NATURE_DESSIN_PACKAGE;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class DessinTuiles {

    GamePanel gp;
    Tuile[] tuile;
    public static int[][] matrice_de_dessin;

    public DessinTuiles(GamePanel gp) {
        this.gp = gp;
        tuile = new Tuile[10];

        matrice_de_dessin = new int[GamePanel.maxScreenRow][GamePanel.maxScreenCol];

        // charger les images ...
        getTuilesImages();
        lire_matrice_de_dessin();
    }

    public void getTuilesImages() {
        try {
            tuile[0] = new Tuile();
            tuile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/grass01.png")));

            tuile[1] = new Tuile();
            tuile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/water00.png")));

            tuile[2] = new Tuile();
            tuile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/ruche.png")));

            tuile[3] = new Tuile();
            tuile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/earth.png")));

            tuile[4] = new Tuile();
            tuile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/tree.png")));

            tuile[5] = new Tuile();
            tuile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/wall.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // quelle bonne idée monsieur jean-luc !
    public void lire_matrice_de_dessin() {
        String filePath = "src/NATURE_DESSIN_PACKAGE/map.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < GamePanel.maxScreenRow) {
                String[] values = line.trim().split("\\s+");

                for (int col = 0; col < values.length && col < GamePanel.SCREEN_HEIGHT; col++) {
                    matrice_de_dessin[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {

        int x = 0;
        int y = 0;
        int row = 0;
        int col = 0;
        while (row < GamePanel.maxScreenRow && col < GamePanel.maxScreenCol) {
            int tileNum = matrice_de_dessin[row][col];
            g.drawImage(tuile[tileNum].image, x, y, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
            col++;
            x += GamePanel.UNIT_SIZE;
            if (col == GamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += GamePanel.UNIT_SIZE;
            }
        }
    }
}
