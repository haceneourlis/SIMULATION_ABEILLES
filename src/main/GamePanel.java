package main;

import BEES_PACKAGE.*;
import NATURE_DESSIN_PACKAGE.DessinTuiles;
import Sources.Sources;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // variables concernant le GUI , screen ...
    public static final int SCREEN_WIDTH = 950;
    public static final int SCREEN_HEIGHT = 750;
    public static final int UNIT_SIZE = 25;

    // ça me servira de limite pour la lécture de mon fichier map.txt.
    public static final int maxScreenRow = GamePanel.SCREEN_HEIGHT / UNIT_SIZE;
    public static final int maxScreenCol = GamePanel.SCREEN_WIDTH / UNIT_SIZE;
    private static boolean gameState = true;

    // les tuiles : arbres , eau , herbe .... ;
    public DessinTuiles tuiles = new DessinTuiles(this);

    /**************************************************************************************/

    /* Concernant les sources : */
    public static final int NUMBER_OF_SOURCES = 10;
    // public static final int NUMBER_OF_SOURCES =1;

    static public int SOURCES_MAXIMUM_QUANTITY = 4000;
    static public int SOURCES_MAXIMUM_QUALITY = 10;
    static public Sources[] les_fleurs = new Sources[GamePanel.NUMBER_OF_SOURCES];
    static public ArrayList<Sources> deadSources = new ArrayList<>();

    /**************************************************************************************/

    /* la ruche : */
    public static final int POSITION_X_DE_LA_RUCHE = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * 4; // 850
    public static final int POSITION_Y_DE_LA_RUCHE = GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE * 26; // 100
    // static final int GAME_UNITS =
    // (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

    /**************************************************************************************/
    Image image;
    Graphics graphics;
    Random random = new Random();
    /**************************************************************************************/

    /* LES ABEILLES ET LEUR CREATION */

    static public Frelon le_frelon;
    static public ArrayList<Banana> les_bananes ;
    static public Bees[] eclaireuses_bees = new Eclaireuse_Bee[30];
    static public Bees[] employee_bees = new Employee_bee[GamePanel.NUMBER_OF_SOURCES];
    static public Bees[] observatrice_bees = new Observatrice_bee[(int) (GamePanel.NUMBER_OF_SOURCES / 2)];
    public CreateurDobjets LeCreateur = new CreateurDobjets();

    private Timer timer;
    private double quantite_de_pollen_initial = 0;

    public static boolean jeuPause = false;

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();


        setGameObjects();

        this.addKeyListener(le_frelon);


        quantite_de_pollen_initial = LeCreateur.quantite_de_pollen();
        timer = new Timer(50, this);
        timer.start();
    }

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

        for(Banana b : GamePanel.les_bananes)
        {
            b.draw(g2);
        }
        // g.setColor(Color.BLUE);
        // for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
        // g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        // g.drawLine(UNIT_SIZE * i, 0, UNIT_SIZE * i, SCREEN_HEIGHT);
        // }
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
            GamePanel.gameState = !GamePanel.gameState;
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (GamePanel.gameState) {
            // calcul_pollen();
            update();

            GameFrame frame_parent = (GameFrame) SwingUtilities.getWindowAncestor(this);
            poullen_finiOuuu(frame_parent);

            repaint();
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

    private void update() {
        le_frelon.move();
        if (release_eclaireuses) {

            for (Bees ec_bee : eclaireuses_bees) {
                ec_bee.bee_move(1, 17);
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
                System.out.println("ahahahahah");
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

                for (Bees b_emp : employee_bees) {
                    if (b_emp.source_to_explore != null) {
                        System.out.println(b_emp + " is assigned to : " + b_emp.source_to_explore);
                    }
                }

                GamePanel.onetime++;
                Bees.reset_box();
            }

            for (Bees employee_bee : employee_bees) {
                // THEFUCK !
                // you say : for ?.... a.source_to_explore = null ; and then you say this ???

                // System.out.println("what the hell");
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
                    if (Bees.infoBoxOfSources[k] != null && Bees.infoBoxOfSources[k].quantity <= 0)
                        Bees.infoBoxOfSources[k] = null;
                }
                for (Sources c : Bees.infoBoxOfSources) {
                    System.out.println(c);
                    System.out.println("-------------------------------------------------");
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

                System.out.println("ZZZZZ\n valid_x.size() = " + valid_x.size());

                for (Bees b_observ : observatrice_bees) {
                    b_observ.letMove = true;
                    b_observ.gotInfoNowWait = false;
                    b_observ.goingHome = false;
                    b_observ.information_gotten = false;
                    b_observ.isInHome = false;

                    if (how_much_gone < valid_x.size()) {
                        b_observ.source_to_explore = valid_x.get(how_much_gone);
                        b_observ.source_to_explore.surMoi.clear();
                        how_much_gone++;
                    }

                }

                do_it_again = false;
            }

            for (Bees ab_ob : observatrice_bees) {
                if (ab_ob.source_to_explore != null)
                    ab_ob.bee_move(2, 17);
                ab_ob.getSourceInformation(les_fleurs);
                how_much_in_home += ab_ob.exploreVoisinage();
                // System.out.println("how much in home (ab observa) : " + how_much_in_home);
            }
            if (GamePanel.how_much_in_home == GamePanel.how_much_gone) {
                do_it_again = true;
                GamePanel.how_much_in_home = 0;

                int cmpt = 0;
                for (int k = 0; k < Bees.infoBoxOfSources.length; k++) {
                    if (Bees.infoBoxOfSources[k] != null && Bees.infoBoxOfSources[k].quantity <= 0)
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

    public void poullen_finiOuuu(GameFrame ce_frame) {

        double quantite_de_pollen_Now = LeCreateur.quantite_de_pollen();

        if (quantite_de_pollen_Now <= quantite_de_pollen_initial * 30 / 100) {
            // fin du jeu

            GamePanel.reinitialiserLeJeu();
            timer.stop();
            ce_frame.dispose();
            new FinJeu();
        }
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

            if (a.source_to_explore != null && a.source_to_explore.quantity < 0) {
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

    public void calcul_pollen() {
        int somme = 0;
        for (Sources s : les_fleurs) {
            if (s != null)
                somme += (int) s.quantity;
        }

        System.out.println("somme = " + somme);
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
}
