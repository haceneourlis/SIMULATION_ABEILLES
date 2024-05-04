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
        tuile = new Tuile[20];

        matrice_de_dessin = new int[GamePanel.nbMax_lignes_matrice_dessin][GamePanel.nbMax_colonnes_mat_dessin];

        // charger les images ...
        getTuilesImages();
        lire_matrice_de_dessin();
    }

    public void getTuilesImages() throws IOException {
        try {
            tuile[0] = getImage("/Tuiles/herbe.png");
            tuile[1] = getImage("/Tuiles/water.png");
            tuile[2] = getImage("/Tuiles/ruche.png");
            tuile[3] = getImage("/Tuiles/earth.png");
            tuile[4] = getImage("/Tuiles/tree.png");
            tuile[5] = getImage("/Tuiles/mur.png");
            tuile[6] = getImage("/Tuiles/cabane.png");
            tuile[7] = getImage("/Tuiles/pass.png");
            tuile[8] = getImage("/Tuiles/tree1.png");
            tuile[9] = getImage("/Tuiles/eau_terre_bottom.png");
            tuile[10] = getImage("/Tuiles/eau_terre_top.png");
            tuile[11] = getImage("/Tuiles/eau_terre_cornerBOTTOMdroite.png");
            tuile[12] = getImage("/Tuiles/eau_terre_cornerBOTTOMgauche.png");
            tuile[13] = getImage("/Tuiles/eau_terre_cornerTOPdroite.png");
            tuile[14] = getImage("/Tuiles/eau_terre_cornerTOPgauche.png");
            tuile[15] = getImage("/Tuiles/eau_terre_droite.png");
            tuile[16] = getImage("/Tuiles/eau_terre_gauche.png");
        } catch (IOException e) {
            throw new IOException("impossible de charger les images des tuiles", e);
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
        String filePath = "./NATURE_DESSIN_PACKAGE/matrice_de_dessin.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < GamePanel.nbMax_lignes_matrice_dessin) {
                String[] values = line.trim().split("\\s+");

                for (int col = 0; col < values.length && col < GamePanel.nbMax_colonnes_mat_dessin; col++) {
                    matrice_de_dessin[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            throw new IOException("impossible de lire le fichier : matrice_de_dessin.txt ", e);
        }
    }

    public void draw(Graphics g) {
        int x = 0;
        int y = 0;
        int row = 0;
        int col = 0;
        while (row < GamePanel.nbMax_lignes_matrice_dessin && col < GamePanel.nbMax_colonnes_mat_dessin) {
            int tileNum = matrice_de_dessin[row][col];
            g.drawImage(tuile[tileNum].image, x, y, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE, null);
            col++;
            x += GamePanel.TAILLE_CELLULE;
            if (col == GamePanel.nbMax_colonnes_mat_dessin) {
                col = 0;
                x = 0;
                row++;
                y += GamePanel.TAILLE_CELLULE;
            }
        }
    }
}
