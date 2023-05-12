package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import java.util.Random; // Pour sélectionner des sommets aléatoires

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
    //TODO: virer les cas impossible (graphe non connexe par ex)
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
     if (scenario == 0) {
        //Pour voir les distances:
        int comparaison_dist = Float.compare(path_b.getLength(), path_d.getLength());
        if (comparaison_dist == 0) {//Si ils sont égaux -> même distance, alors le test est validé
            return 1;
        } else {
            return 0;
        }
     } else if (scenario==2){ 
        int comparaison_temps = Double.compare(path_b.getMinimumTravelTime(), path_d.getMinimumTravelTime());
        if (comparaison_temps == 0) {
            return 1;
        } else {
        return 0;
        }
     } return -1; // Si jamais il ne passe pas dans mes ifs, pour que l'environnement ne me cri plus dessus
    }

    public static int batterieTest() {
        //TODO : Il faut que j'appelle plusieurs fois, dans des scénarios différents et avec une map

        return -1;
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        // Les chemins ici sont adaptés pour l'architecture de mon ordinateur personnel. Pour les tester, il faut changer les chemins par les tiens.
        final String mapName = "//mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

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
        final PathReader pathReader = new BinaryPathReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        		

        // Read the path.
        final Path path = pathReader.readPath(graph);
        

        // Draw the path.
        drawing.drawPath(path);
    }

}
