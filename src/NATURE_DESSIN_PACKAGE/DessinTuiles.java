package NATURE_DESSIN_PACKAGE;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class DessinTuiles {

    GamePanel gp;
    Tuile[] tuile;
    public static int[][] matrice_de_dessin;

    public DessinTuiles(GamePanel gp) throws IOException {
        this.gp = gp;
        tuile = new Tuile[10];

        matrice_de_dessin = new int[GamePanel.maxScreenRow][GamePanel.maxScreenCol];

        // charger les images ...
        getTuilesImages();
        lire_matrice_de_dessin();
    }

    public void getTuilesImages() throws IOException {
        try {
            tuile[0] = getImage("/Tuiles/grass01.png");
            tuile[1] = getImage("/Tuiles/water00.png");
            tuile[2] = getImage("/Tuiles/r.png");
            tuile[3] = getImage("/Tuiles/earth.png");
            tuile[4] = getImage("/Tuiles/tree.png");
            tuile[5] = getImage("/Tuiles/wall.png");
        } catch (IOException e) {
            throw new IOException("impossible de getter les images des tuiles", e);
        }
    }

    private Tuile getImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        if (image == null) {
            throw new IllegalArgumentException("getting image is impossible pour l'image : " + path);
        }
        return new Tuile(image);
    }

    private void lire_matrice_de_dessin() throws IOException {
        String filePath = "src/NATURE_DESSIN_PACKAGE/map.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < GamePanel.maxScreenRow) {
                String[] values = line.trim().split("\\s+");

                for (int col = 0; col < values.length && col < GamePanel.maxScreenCol; col++) {
                    matrice_de_dessin[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            throw new IOException("impossible de lire le fichier : map.txt ", e);
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
