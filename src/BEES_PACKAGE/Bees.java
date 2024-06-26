package BEES_PACKAGE;
//import graphs.Graph;
//import java.util.ArrayList;
//import java.util.List;

import Sources.Sources;
import main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Bees {

    public BufferedImage image;
    public int bee_xpos, bee_ypos;
    public Sources source_to_explore;
    public int dx = 1, dy = 1;
    public boolean isInHome = false;
    public int bee_id;
    public boolean information_gotten = false;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE); // pour m'aider à
    // détecter les
    // collisions .

    static public Sources[] infoBoxOfSources = new Sources[GamePanel.NOMBRE_DE_SOURCES];
    public boolean letMove = true;
    public int stop_Labeille = 0;
    public boolean gotInfoNowWait = false;
    public boolean goingHome = false;

    public double ventre = 0;

    Random rand = new Random();

    public abstract void getSourceInformation(Sources[] src);

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, bee_xpos, bee_ypos, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE, null);
    }

    public void bee_move(int temps, int vitesse) {
        if (letMove) {
            int target_x_position = this.source_to_explore.source_xpos;
            int target_y_position = this.source_to_explore.source_ypos;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse * temps) / distance);
            bee_ypos += (int) ((dy * vitesse * temps) / distance);

            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            source_to_explore.solidArea.setLocation(target_x_position, target_y_position);

            if (this.solidArea.intersects(source_to_explore.solidArea)) {
                this.information_gotten = true;
                this.letMove = false;
            }
        }
    }

    public void abeille_suce_et_attend(int tempsDattente,double qteAsucer) {
        this.information_gotten = false;
        this.gotInfoNowWait = true;
        this.letMove = false; // pour bloquer les abeilles ( t.important );

        this.ventre += qteAsucer;
        this.stop_Labeille++;

        if (this.stop_Labeille > tempsDattente) {
            this.stop_Labeille = 0;
            this.information_gotten = true;
        }
    }


    public int compteur_retour_ruche = 0;

    public int exploreVoisinage() {

        if (this.information_gotten) {
            this.stop_Labeille = 0;

            int[] X_directions = { +0, +0, -1, -1, +0, +0, +0, +0, +0, +1, +1, +1, +0, +0, -1, -1};
            int[] Y_directions = { -1, -1, +0, +0, +1, +1, +1, +1, +1, +0, +0, +0, -1, -1, +0, +0};

            if (compteur_retour_ruche >= X_directions.length) {
                return this.getBackHome(50);
            } else {
                bee_xpos += GamePanel.TAILLE_CELLULE * X_directions[compteur_retour_ruche];
                bee_ypos += GamePanel.TAILLE_CELLULE * Y_directions[compteur_retour_ruche];
                compteur_retour_ruche++;
            }
        }

        return 0;
    }

    public int getBackHome(int vitesse) {
        if (!isInHome && information_gotten) {

            this.goingHome = true;

            int target_x_position = GamePanel.POSITION_X_DE_LA_RUCHE + GamePanel.TAILLE_CELLULE * 2;
            int target_y_position = GamePanel.POSITION_Y_DE_LA_RUCHE - GamePanel.TAILLE_CELLULE * 3;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse) / distance);
            bee_ypos += (int) ((dy * vitesse) / distance);

            if(this instanceof Employee_bee)
            {
               ((Employee_bee) this).release_a_banana();
            }

            if (this.bee_ypos <= GamePanel.POSITION_Y_DE_LA_RUCHE
                    && this.bee_xpos >= GamePanel.POSITION_X_DE_LA_RUCHE) {
                this.isInHome = true;
                this.information_gotten = false;
                this.compteur_retour_ruche = 0;
                return 1;
            }
        }
        return 0;
    }

    public static int compter_nombre_de_source_decouvertes() {
        int cpt = 0;
        for (int i = 0; i < Bees.infoBoxOfSources.length; i++) {
            if (Bees.infoBoxOfSources[i] != null)
                cpt++;
        }
        return cpt;
    }

    public static void reset_box() {
        Sources tempo = new Sources(0, 0, 0, 9999999, 0);
        for (int i = 0; i < Bees.infoBoxOfSources.length; i++) {
            infoBoxOfSources[i] = tempo;
        }
    }


    public String toString()
    {
        return "bee source_id = "+bee_id ;
    }
}



                                    /*  bonus */
//List<Integer> parcours_abeille = new ArrayList<>();
//
//
//
//// les employées vont chaqu'une parcouir ce " parcours prédifinie par les éclaireuses "
//public List<Integer> parcourir_et_modifier(Graph gr)
//{
//    // pour échanger deux aretes par deux autres ; il faut qu'elles soient non-adjacentes les unes des autres ;
//    // si j'ai un parcours comme ça : ABCD
//    // AB et BC ; BC et CD sont des aretes adjacentes
//    // donc il faut prendre AB,CD les remplacés par AC,BD .
//
//    for(int sommet = 0; sommet<parcours_abeille.size()-3;sommet++)
//    {
//        int A = parcours_abeille.get(sommet);
//        int B = parcours_abeille.get(sommet+1);
//        int C = parcours_abeille.get(sommet+2);
//        int D = parcours_abeille.get(sommet+3);
//
//        int poidsAB = gr.getPoidsArete(A,B);
//        int poidsCD = gr.getPoidsArete(C,D);
//
//        int poids_avant = this.poids_dun_parcours(this.parcours_abeille,gr);
//
//        int poidsAC = gr.getPoidsArete(A,C);
//        int poidsBD = gr.getPoidsArete(B,D);
//
//        int poids_apres = poids_avant -(poidsAB+poidsCD) + (poidsAC+poidsBD);
//
//        if(poids_apres < poids_avant)
//        {
//            this.parcours_abeille.set(sommet+1,C);
//            this.parcours_abeille.set(sommet+2,A);
//        }
//    }
//    return  this.parcours_abeille;
//}
//public int poids_dun_parcours(List<Integer> parcours,Graph gr)
//{
//    int s = 0 ;
//    for(int i = 0 ; i < parcours.size() - 1;i++)
//    {
//        s+= gr.getPoidsArete(parcours.get(i),parcours.get(i+1));
//    }
//    s+= gr.getPoidsArete(parcours.getLast(),parcours.getFirst());
//
//    return s;
//}