package main;

import BEES_PACKAGE.Bees;
import BEES_PACKAGE.Employee_bee;
import BEES_PACKAGE.Observatrice_bee;
import NATURE_DESSIN_PACKAGE.DessinTuiles;
import Sources.Sources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

    // variables concernant le GUI , screen ...
    public static final int SCREEN_WIDTH = 950;
    public static final int SCREEN_HEIGHT = 750;
    public static final int UNIT_SIZE = 25;

    // ça me servira de limite pour la lécture de mon fichier map.txt.
    public static final int maxScreenRow = GamePanel.SCREEN_HEIGHT / UNIT_SIZE; // récuperer le nombres de lignes
                                                                                // (représentation matrice de notre
                                                                                // interface graphique )
    public static final int maxScreenCol = GamePanel.SCREEN_WIDTH / UNIT_SIZE; // récuperer le nombres de colonnes
                                                                               // (représentation matrice de notre
                                                                               // interface graphique )

    // les tuiles : arbres , eau , herbe .... ;
    public DessinTuiles tuiles = new DessinTuiles(this);

    /**************************************************************************************/

    /* Concernant les sources : */
    public static final int NUMBER_OF_SOURCES = 10;
    // public static final int NUMBER_OF_SOURCES =1;

    static public int SOURCES_MAXIMUM_QUANTITY = 99999;
    static public int SOURCES_MAXIMUM_QUALITY = 10;
    static public Sources[] les_fleurs = new Sources[GamePanel.NUMBER_OF_SOURCES];

    /**************************************************************************************/

    /* la ruche : */
    public static final int POSITION_X_DE_LA_RUCHE = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * 4; // 850
    public static final int POSITION_Y_DE_LA_RUCHE = GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE * 26; // 100
    // static final int GAME_UNITS =
    // (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

    /**************************************************************************************/
    Image image;
    Graphics graphics;

    /**************************************************************************************/

    /* LES ABEILLES ET LEUR CREATION */
    static public Bees[] eclaireuses_bees = new Bees[30];
    // static public Bees[] eclaireuses_bees = new Bees[1];

    static public Bees[] employee_bees = new Bees[GamePanel.NUMBER_OF_SOURCES];
    static public Bees[] observatrice_bees = new Observatrice_bee[(int) (GamePanel.NUMBER_OF_SOURCES / 3)];
    public CreateurDobjets LeCreateur = new CreateurDobjets();

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        setGameObjects();

        new Timer(60, this).start();

    }

    public void display_sourceINFOS() {
        for (int i = 0; i < GamePanel.les_fleurs.length; i++) {
            if (les_fleurs[i] != null)
                System.out.println(
                        "the source :" + les_fleurs[i].source_id + "is at " + les_fleurs[i].quantity + " quantity");
        }
    }

    public void setGameObjects() {
        LeCreateur.set_objects();
    }

    public void paint(Graphics g) {
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

        g.setColor(Color.BLUE);

        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            g.drawLine(UNIT_SIZE * i, 0, UNIT_SIZE * i, SCREEN_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    static public int how_much_in_home = 0;
    static public boolean release_eclaireuses = true;
    static public boolean release_employees = false;
    static public boolean release_observatrices = false;
    int onetime = 1; // afin de affecter les sources au employées qu'une seule fois ; pas à chaque
                     // update();

    private void update() {
        if (release_eclaireuses) {
            for (Bees eclaireuses_bee : eclaireuses_bees) {
                if (eclaireuses_bee != null) {
                    eclaireuses_bee.bee_move(3, 5);
                    eclaireuses_bee.getSourceInformation(les_fleurs);
                    GamePanel.how_much_in_home += eclaireuses_bee.getBackHome(9);
                }
            }
        }
        // si toutes les eclaireuses sont de retour à la ruche , on fait BOUGER/JOUER
        // les employées .
        if (!release_employees && Bees.compter_nombre_de_source_decouvertes() == GamePanel.les_fleurs.length) {
            release_eclaireuses = false;
            if (how_much_in_home == GamePanel.eclaireuses_bees.length) {
                how_much_in_home = 0;
                release_employees = true;
            } else {
                for (Bees eclaireuses_bee : eclaireuses_bees) {
                    // if (eclaireuses_bee != null) {
                    // si l'abeille est déjà arrivé à la ruche , il ne faut pas appeler la méthode
                    // getbackHome();
                    eclaireuses_bee.information_gotten = true;
                    GamePanel.how_much_in_home += eclaireuses_bee.getBackHome(9);
                    // }
                }
            }
        }

        if (release_employees) {

            // LA DANSE DES ECLAIREUSES SE PASSES ICI .
            int i = 0;
            if (onetime == 1) {
                for (Bees employee_bee : employee_bees) {
                    if (Bees.infoBoxOfSources[i] != null) {
                        employee_bee.source_to_explore = Bees.infoBoxOfSources[i];
                    }
                    i++;
                }
                onetime++;
                Bees.reset_box();
            }

            for (Bees employee_bee : employee_bees) {
                if (employee_bee != null) {
                    // System.out.println("what the hell");
                    employee_bee.bee_move(2, 3);
                    // prend les info de ta source et scan le voisinage !
                    employee_bee.getSourceInformation(les_fleurs);
                    Employee_bee temp = (Employee_bee) employee_bee;
                    how_much_in_home += temp.exploreVoisinage();
                    if (onetime == 2 && how_much_in_home == GamePanel.employee_bees.length) {
                        onetime++;
                        release_observatrices = true;
                        // int cmpt = 0;
                        System.out.println("****-'-'-'-'-'-'-'-'-'-'-'-'-'-'-'-'-'-'-'****");
                        display_sourceINFOS();
                    }
                }
            }
        }

        if (release_observatrices) {
            // Elles observent les danses des employées à leur retour
            // et perçoivent avec un certain degré d’erreur la qualité estimée de chaque
            // source. Elles choisissent alors une source de nourriture
            if (onetime == 3) {
                int i = 0;
                for (Bees abeille_observatrice : observatrice_bees) {
                    if (abeille_observatrice != null && Bees.infoBoxOfSources[i] != null) {
                        abeille_observatrice.source_to_explore = Bees.infoBoxOfSources[i];
                    }
                    i++;
                }
                onetime++;
            }

            for (Bees abeille_observatrice : observatrice_bees) {
                if (abeille_observatrice != null) {
                    abeille_observatrice.bee_move(2, 3);
                }
            }
        }
    }

}
