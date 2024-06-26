package main;

import BEES_PACKAGE.*;
import NATURE_DESSIN_PACKAGE.DessinTuiles;
import Sources.Sources;
import graphs.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // variables concernant le GUI , Ecran ...
    public static final int LARGEUR_ECRAN = 950;
    public static final int HAUTEUR_ECRAN = 750;
    public static final int TAILLE_CELLULE = 25;

    // ça me servira de limite pour la lécture de mon fichier matrice_de_dessin.txt.
    public static final int nbMax_lignes_matrice_dessin = GamePanel.HAUTEUR_ECRAN / TAILLE_CELLULE;
    public static final int nbMax_colonnes_mat_dessin = GamePanel.LARGEUR_ECRAN / TAILLE_CELLULE;
    private static boolean frelon_mort = false;
    // les tuiles : arbres , eau , herbe .... ;
    public DessinTuiles tuiles = new DessinTuiles(this);
    /**************************************************************************************/

    /* Concernant les sources : */
    public static final int NOMBRE_DE_SOURCES = 25;
    static public int MAX_QUANTITE_pour_SOURCE = 12000;
    static public int MAX_QUALITE_pour_SOURCE = 10;
    static public Sources[] les_fleurs = new Sources[GamePanel.NOMBRE_DE_SOURCES];

    /**************************************************************************************/

    /* la ruche : */
    public static final int POSITION_X_DE_LA_RUCHE = GamePanel.LARGEUR_ECRAN - GamePanel.TAILLE_CELLULE * 4; // 850
    public static final int POSITION_Y_DE_LA_RUCHE = GamePanel.HAUTEUR_ECRAN - GamePanel.TAILLE_CELLULE * 26; // 100

    /**************************************************************************************/
    Image image;
    Graphics graphics;
    /**************************************************************************************/

    /* LES ABEILLES  */

    static public Frelon le_frelon;
    static public ArrayList<FrelonVoleurDeFrelon> les_fils_frelon;
    static public ArrayList<Banana> les_bananes;
    static public double[] qte_initiales_sources = new double[NOMBRE_DE_SOURCES];

    static public Bees[] eclaireuses_bees = new Eclaireuse_Bee[12];
    static public Bees[] employee_bees = new Employee_bee[GamePanel.NOMBRE_DE_SOURCES];
    static public Bees[] observatrice_bees = new Observatrice_bee[(int) (GamePanel.NOMBRE_DE_SOURCES / 2)];
    public CreateurDobjets LeCreateur = new CreateurDobjets();

    private Timer timer;
    private double quantite_de_pollen_initial = 0;

    public static boolean jeuPause = false;

    /**
     * ********************************** CONSTRUCTEUR
     * *****************************************
     **/
    public GamePanel() throws IOException {

        this.setPreferredSize(new Dimension(LARGEUR_ECRAN, HAUTEUR_ECRAN));

        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        setGameObjects();

        this.addKeyListener(le_frelon);

        quantite_de_pollen_initial = LeCreateur.quantite_de_pollen();
        timer = new Timer(50, this);
        timer.start();
    }

    /**
     * ***************************************************************************
     **/

    public void setGameObjects() {
        LeCreateur.set_objects();
    }

    public void paint(Graphics g) {
        // les deux lignes suivantes représente le " DOUBLE BUFFERING " ; en gros ça
        // nous aide à voir les image d'une maniére plus agréable , et éviter les
        // clignotements de trop !
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();

        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {

        // dessiner les tuiles .
        Graphics2D g2 = (Graphics2D) g;
        tuiles.draw(g);

        // dessiner ensuite les sources
        for (Sources source : les_fleurs) {
            if (source != null) {
                source.draw(g2);
            }
        }

        // dessiner les abeilles .
        for (Bees A_bee : eclaireuses_bees) {
            if (A_bee != null) {
                A_bee.draw(g2);
            }
        }
        for (Bees A_bee : employee_bees) {
            if (A_bee != null) {
                A_bee.draw(g2);
            }
        }

        for (Bees A_bee : observatrice_bees) {
            if (A_bee != null) {
                A_bee.draw(g2);
            }
        }

        g2.setColor(Color.GREEN);
        g2.drawOval(GamePanel.POSITION_X_DE_LA_RUCHE, GamePanel.POSITION_Y_DE_LA_RUCHE, 40, 30);

        if (jeuPause) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String message = "Jeu en pause";
            FontMetrics metrics = g.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g.drawString(message, x, y);
        }

        le_frelon.draw(g2);

        for (Banana b : GamePanel.les_bananes) {
            b.draw(g2);
        }
        for (FrelonVoleurDeFrelon ff : les_fils_frelon) {
            ff.draw(g2);
        }

        for (int i = 0; i < Frelon.vie_frelon; i++) {
            g2.drawImage(Frelon.coeur_image, i * GamePanel.TAILLE_CELLULE, 0, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE,
                    null);
        }
    }

    static public int how_much_in_home = 0;
    static public int how_much_gone = 0;

    static public boolean release_eclaireuses = true;
    static public boolean release_employees = false;
    static public boolean release_observatrices = false;
    public static int onetime = 1; // afin de affecter les sources au employées qu'une seule fois ; pas à chaque
                                   // update
    boolean do_it_again = true;
    public static ArrayList<Sources> valid_x = new ArrayList<>();

    public void poullen_finiOuuu(GameFrame ce_frame) {

        double quantite_de_pollen_Now = LeCreateur.quantite_de_pollen();

        if (quantite_de_pollen_Now <= quantite_de_pollen_initial * 30 / 100) {
            // fin du jeu

            int x = Frelon.munition;
            fin_du_jeu(x, ce_frame, "la quantité de pollen est épuisée");
        }
    }

    private void fin_du_jeu(int x, GameFrame ce_frame, String message) {
        GamePanel.reinitialiserLeJeu();
        timer.stop();
        ce_frame.dispose();
        new FinJeu(x, message);
    }

    private static void searchNewResources() {
        how_much_in_home = 0;

        release_eclaireuses = true;
        release_employees = false;
        release_observatrices = false;
        GamePanel.onetime = 1;

        for (Bees a : eclaireuses_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;
            a.dx = 5;
            a.dy = 5;
        }
        for (Bees a : employee_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;
            a.dx = 5;
            a.dy = 5;

            if (a.source_to_explore != null && a.source_to_explore.source_quantite < 0) {
                a.source_to_explore = null;
            }
        }
        for (Bees a : observatrice_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;
            a.source_to_explore = null;
            a.dx = 1;
            a.dy = 1;
        }

        Arrays.fill(Bees.infoBoxOfSources, null);
    }

    private static void reinitialiserLeJeu() {
        how_much_in_home = 0;

        release_eclaireuses = true;
        release_employees = false;
        release_observatrices = false;

        GamePanel.onetime = 1;
        GamePanel.frelon_mort = false;

        Frelon.vie_frelon = 5;
        Frelon.munition = 0;

        GamePanel.les_bananes.clear();
        GamePanel.les_fils_frelon.clear();

        for (Bees a : eclaireuses_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;

            a.dx = 1;
            a.dy = 1;
        }
        for (Bees a : employee_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;
            a.source_to_explore = null;

            a.dx = 1;
            a.dy = 1;
        }
        for (Bees a : observatrice_bees) {
            a.letMove = true;
            a.gotInfoNowWait = false;
            a.goingHome = false;
            a.information_gotten = false;
            a.isInHome = false;
            a.source_to_explore = null;
            a.dx = 1;
            a.dy = 1;
        }

        for (Sources c : les_fleurs) {
            if (c != null)
                c.surMoi.clear();
        }

        Arrays.fill(Bees.infoBoxOfSources, null);
    }

    public static int remaining_sources() {
        int somme = 0;
        for (Sources c : les_fleurs) {
            if (c != null) {
                somme += 1;
            }
        }
        return somme;
    }

    private void update() {

        if (GamePanel.le_frelon != null) {
            le_frelon.move();
        }

        for (int i = 0; i < GamePanel.les_fleurs.length; i++) {
            if (GamePanel.les_fleurs[i] != null
                    && GamePanel.les_fleurs[i].source_quantite <= ((double) 30 / 100) * GamePanel.qte_initiales_sources[i]) {
                try {
                    GamePanel.les_fleurs[i].image = ImageIO
                            .read(Objects.requireNonNull(getClass().getResourceAsStream("/Sources/flower_morte.png")));
                } catch (IOException e) {
                    throw new IllegalArgumentException(
                            "Accés à l'image impossible pour la source : " + GamePanel.les_fleurs[i].source_id, e);
                }
            }
        }

        for (int i = 0; i < GamePanel.les_fils_frelon.size(); i++) {
            GamePanel.les_fils_frelon.get(i).bee_move(1, 18);
            GamePanel.les_fils_frelon.get(i).voler();
            if (GamePanel.les_fils_frelon.get(i).attaquer(10)) {
                GamePanel.les_fils_frelon.remove(i);
                i--;

                Frelon.vie_frelon--;
                if (Frelon.vie_frelon <= 0) {
                    GamePanel.frelon_mort = true;
                }
            }
        }

        if (release_eclaireuses) {

            for (Bees ec_bee : eclaireuses_bees) {
                ec_bee.bee_move(1, 37);
                ec_bee.getSourceInformation(les_fleurs);
                GamePanel.how_much_in_home += ec_bee.getBackHome(17);
            }

            if (Bees.compter_nombre_de_source_decouvertes() == GamePanel.remaining_sources()) {
                for (Bees ec_bee : eclaireuses_bees) {
                    ec_bee.information_gotten = true;
                    ec_bee.letMove = false;

                    GamePanel.how_much_in_home += ec_bee.getBackHome(50);
                }
            }

            if (how_much_in_home == GamePanel.eclaireuses_bees.length) {
                how_much_in_home = 0;
                release_eclaireuses = false;
                release_employees = true;
            }
        }

        if (release_employees) {
            // LA DANSE DES ECLAIREUSES SE PASSES ICI .
            int i = 0;
            if (GamePanel.onetime == 1) {
                valid_x.clear();
                for (int k = 0; k < Bees.infoBoxOfSources.length; k++) {
                    if (Bees.infoBoxOfSources[k] != null) {
                        valid_x.add(Bees.infoBoxOfSources[k]);
                    }
                }

                for (Bees b_emp : employee_bees) {
                    // comme ça y'aura pas d'employée sans source ,
                    if (b_emp.source_to_explore == null) {
                        if (i < valid_x.size()) {
                            b_emp.source_to_explore = valid_x.get(i);
                            i++;
                        }
                    }

                }

                GamePanel.onetime++;
                Bees.reset_box();
            }

            for (Bees employee_bee : employee_bees) {
                if (employee_bee.source_to_explore != null) {
                    employee_bee.bee_move(1, 17);
                }
                // prend les info de ta source et scan le voisinage !
                employee_bee.getSourceInformation(les_fleurs);
                how_much_in_home += employee_bee.exploreVoisinage();
            }

            if (how_much_in_home == valid_x.size()) {
                how_much_in_home = 0;
                release_observatrices = true;
                release_employees = false;

                for (int k = 0; k < Bees.infoBoxOfSources.length; k++) {
                    if (Bees.infoBoxOfSources[k] != null && Bees.infoBoxOfSources[k].source_quantite <= 0)
                        Bees.infoBoxOfSources[k] = null;
                }

            }
        }

        if (release_observatrices) {
            // Elles observent les danses des employées à leur retour
            // et perçoivent avec un certain degré d’erreur la qualité estimée de chaque
            // source. Elles choisissent alors une source de nourriture

            if (do_it_again) {
                how_much_gone = 0;
                valid_x.clear();
                for (int k = 0; k < Bees.infoBoxOfSources.length; k++) {
                    if (Bees.infoBoxOfSources[k] != null) {
                        valid_x.add(Bees.infoBoxOfSources[k]);
                    }
                }

                //System.out.println("valid_x.size() = " + valid_x.size());

                for (Bees b_observ : observatrice_bees) {
                    b_observ.letMove = true;
                    b_observ.gotInfoNowWait = false;
                    b_observ.goingHome = false;
                    b_observ.information_gotten = false;
                    b_observ.isInHome = false;
                    b_observ.source_to_explore = null;

                    if (how_much_gone < valid_x.size()) {
                        b_observ.source_to_explore = valid_x.get(how_much_gone);
                        b_observ.source_to_explore.surMoi.clear();
                        how_much_gone++;
                    }
                }

                //System.out.println("\nhow much observatrice gone ?  = " + how_much_gone + "\n------------------\n");
                do_it_again = false;
            }

            for (Bees ab_ob : observatrice_bees) {
                if (ab_ob.source_to_explore != null)
                    ab_ob.bee_move(2, 17);
                ab_ob.getSourceInformation(les_fleurs);
                how_much_in_home += ab_ob.exploreVoisinage();
            }
            //System.out.println("how much in home ? = " + how_much_in_home);
            if (GamePanel.how_much_in_home == GamePanel.how_much_gone) {
                do_it_again = true;
                GamePanel.how_much_in_home = 0;

                GamePanel.les_bananes.clear();

                int cmpt = 0;
                for (int k = 0; k < Bees.infoBoxOfSources.length; k++) {
                    if (Bees.infoBoxOfSources[k] != null && Bees.infoBoxOfSources[k].source_quantite <= 0)
                        Bees.infoBoxOfSources[k] = null;

                    if (Bees.infoBoxOfSources[k] == null)
                        cmpt++;
                }
                // si on a plus de BONNES SOURCES !
                if (cmpt == Bees.infoBoxOfSources.length) {
                    searchNewResources();
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GamePanel.jeuPause = !GamePanel.jeuPause;
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!GamePanel.jeuPause) {
            // calcul_pollen();
            update();

            GameFrame frame_parent = (GameFrame) SwingUtilities.getWindowAncestor(this);
            poullen_finiOuuu(frame_parent);

            if (GamePanel.frelon_mort) {
                fin_du_jeu(Frelon.munition, frame_parent, "Frelon mort !");
            }

            repaint();
        }
    }
}


//    // bonus ...
// imlements Runnable ;
//    ArrayList<ArrayList<?>> listDesParcours = new ArrayList<>();
//
//    int V = 5; // Nombre de sommets
//    Graph graphe = new Graph(V);
//    graphe.ajouterArete(0, 1, 4);
//    graphe.ajouterArete(0, 2, 5);
//    graphe.ajouterArete(0, 3, 9);
//
//    graphe.ajouterArete(1, 2, 3);
//    graphe.ajouterArete(1, 3, 2);
//    graphe.ajouterArete(2, 3, 1);
//
//    public void run()
//    {
//        for(Bees b : eclaireuses_bees)
//        {
//            listDesParcours.add(((Eclaireuse_Bee) b).tirerAleatoirementUnParcours(graphe));
//        }
//    }
