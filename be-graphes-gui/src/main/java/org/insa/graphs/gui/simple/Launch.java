package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import java.util.Random; // Pour sélectionner des sommets aléatoires
import java.lang.*;

import javax.lang.model.util.Elements.Origin;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.gui.drawing.overlays.PathOverlay;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }


    //Fonction qui test un scénario, renvoie 1 si la version dijkstra == la version Bellman

    //scenario  0 = shortest path, all roads \\ scénario 2 = fastest path, all road
    
    public static int test(Graph graph,int scenario){
     
     //On veut choisir 2 nodes aléatoires dans le graphe et faire Dijkstra et comparer avec Bellman-ford
     Random random = new Random();
     int randomOrigin = random.nextInt(graph.size());
     int randomDestination = random.nextInt(graph.size());
     //Maintenant qu'on a nos 2 nodes, on veut appeller dijkstra et Bellman-ford ( surement doRun)
     //Il me faut les Node, pas juste le int
     Node origin = graph.get(randomOrigin);
     Node dest = graph.get(randomDestination);
     ArcInspector arcins = ArcInspectorFactory.getAllFilters().get(scenario); 
        
     
     //Je veut lancer les algos, mais on a besoin de la data
     ShortestPathData data = new ShortestPathData(graph, origin, dest, arcins); 
     DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
     BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
     //je crée mes espaces solutions
     //ShortestPathSolution sol_d = dijkstra.run();
     //ShortestPathSolution sol_b = bellman.run();
     Path path_d = dijkstra.run().getPath();
     Path path_b = bellman.run().getPath();
     //Il reste plus que à comparer ce qu'on veut 
     if (path_b != null && path_d != null) {
            
        
        if (scenario == 0) {
            //Pour voir les distances:
            //System.out.println(path_b.getLength());
            //System.out.println(path_d.getLength());
            //System.out.println(path_b);
            //System.out.println(path_d);
            
            boolean comparaison_dist = comparer_float(path_b.getLength(), path_d.getLength());
            if (comparaison_dist) {//Si ils sont égaux -> même distance, alors le test est validé
                return 1;
            } else {
                return 0;
            }
        } else if (scenario==2){ 
            boolean comparaison_temps = comparer_float((float)(path_b.getMinimumTravelTime()), (float)(path_d.getMinimumTravelTime()));
            if (comparaison_temps) {
                return 1;
            } else {
                //System.out.println("Cette origine "+ randomOrigin);
                //System.out.println("combine à cette destination " + randomDestination + "ne marche pas");
            return 0;
            }
        }
     } return 1; // Si jamais il n'y a pas de path, alors le test est compté comme réussi
    }
    public static boolean comparer_float(float un, float deux) {
        //Je ne dois pas comparer comme ça, je dois mettre une incertitude
        if (Math.abs(deux-un) <= 0.2) {
            return true;
        } else {
            System.out.println("else " + deux);
            System.out.println(un);
            return false;
        }
        
    }
    public static void batterieTest(Graph graph, int nb_test) {
        
        int test_dist_reussi = 0;
        int test_temps_reussi = 0;
        for (int i=0; i< nb_test/2; i++) {
            //System.out.println(test(graph,0));
            test_dist_reussi += test(graph,0);
        }
        for (int j=0; j< nb_test/2; j++) {
            //System.out.println("for2");
            test_temps_reussi += test(graph,2);
        }

        System.out.println("Il y'a eu " + test_dist_reussi + " test de distance reussi sur " + nb_test/2 );
        System.out.println("Il y'a eu " + test_temps_reussi + " test de temps reussi sur " + nb_test/2 );
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        // Les chemins ici sont adaptés pour l'architecture de mon ordinateur personnel. Pour les tester, il faut changer les chemins par les tiens.
        final String mapName = "//mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        //final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // Read the graph.
        final Graph graph = reader.read();
        
        // Create the drawing:
        final Drawing drawing = createDrawing();

        // Draw the graph on the drawing.
        drawing.drawGraph(graph);

        // Create a PathReader.
        //final PathReader pathReader = new BinaryPathReader(
        //		new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        		

        // Read the path.
        //final Path path = pathReader.readPath(graph);
        

        // Draw the path.
        //drawing.drawPath(path);
        System.out.println("Mes tests");
        batterieTest(graph, 2500);
    }

}
