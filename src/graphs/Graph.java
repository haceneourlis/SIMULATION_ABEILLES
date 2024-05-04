//package graphs;
//
//import java.util.*;
//
//// Classe pour représenter un graphe en utilisant une liste d'adjacence avec des poids
//public class Graph {
//    public int nb_sommets; // Nombre de sommets
//    public LinkedList<Arete>[] listeAdjacence; // Liste d'adjacence
//
//    // Classe pour représenter une arête avec un poids
//    public static class Arete {
//        int destination;
//        int poids;
//
//        Arete(int dest, int poids) {
//            this.destination = dest;
//            this.poids = poids;
//        }
//    }
//
//    // Constructeur
//    public Graph(int V) {
//        this.nb_sommets = V;
//        // Créer un tableau de listes d'adjacence
//        listeAdjacence = new LinkedList[V];
//        // Initialiser chaque liste d'adjacence comme étant vide
//        for (int i = 0; i < V; i++) {
//            listeAdjacence[i] = new LinkedList<>();
//        }
//    }
//
//    // Fonction pour ajouter une arête au graphe
//    public void ajouterArete(int src, int dest, int poids) {
//        listeAdjacence[src].add(new Arete(dest, poids));
//        listeAdjacence[dest].add(new Arete(src, poids));
//    }
//
//    // Fonction pour afficher le graphe
//    public void afficherGraphe() {
//        for (int v = 0; v < nb_sommets; v++) {
//            System.out.println("Liste d'adjacence du sommet " + v);
//            for (Arete arete : listeAdjacence[v]) {
//                System.out.println(" -> " + arete.destination + " (poids : " + arete.poids + ")");
//            }
//            System.out.println();
//        }
//    }
//
//    public int getPoidsArete(int src,int dest)
//    {
//        int i = 0 ;
//        while(i<listeAdjacence[src].size())
//        {
//            if(listeAdjacence[src].get(i).destination == dest)
//            {
//                return listeAdjacence[src].get(i).poids;
//            }
//            i++;
//        }
//        return -99;
//    }
//
//}