package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);

    }
    
    protected Label NewLabel(Node node, boolean marque, Arc père,ShortestPathData data) {
    	return new Label(node,marque,père);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	 final ShortestPathData data = getInputData();
         ShortestPathSolution solution = null;
         Graph graph= data.getGraph();
         Label[] labels = new Label[graph.size()];

         //Initialisation
         //On a besoin d'init les labels de tout le monde avant de mettre des choses dedans
         int i =0;
         for (Node node : graph.getNodes()) {//On parcours tout les sommets du graphe
            labels[i]=new Label(node, false, null);//et on les mets tous avec un cout infini ( par défault )
            i++;
         }

         //coût de chemin à l'origine = 0
         labels[data.getOrigin().getId()].setCout(0);

         //coût du chemin aux autres sommets = +infini : C'est fait avant

         //Structure pour trouver le  sommet du plus petit cout(noté dans le Label) : file de priorité = Tas
         BinaryHeap<Label> queue = new BinaryHeap<Label>();
         //On met le premier node dans le tas car c'est le seul qu'on connait au début
         queue.insert(labels[data.getOrigin().getId()]);

         //Initialisation faite ! 


         //Itération 
         boolean trouver = false;

         while (!queue.isEmpty() && !trouver) { //tant qu'il y'a des éléments dans la pile (=non marqué)
            Label X = queue.findMin(); //NB : à la première itération ça sera le sommet origine
            X.setMarque(true); //On le marque à vrai
            queue.remove(X);; // et on le retire
            
            trouver = (data.getDestination()==X.getSommet()); //on arrete la boucle une fois qu'on a trouver notre node d'arrivée


            //pour tous les successeurs de X
            for (Arc successeur : X.getSommet().getSuccessors()) { //NB : les successeurs d'un node sont des arcs à qui on peut accéder au cout avec data.getCost
                //On va chercher le successeur dans notre liste de label
                Label Y = labels[successeur.getDestination().getId()];
                if (Y.isMarque() == false) {//si not Mark(y)

                    //Cost(y) <- min (cost(y), cost(x) + W(x,y))
                    Y.setCout(Math.min(Y.getCost(), X.getCost() + data.getCost(successeur)));
                    
                    //if mis à jour
                    if (Y.getCost() > X.getCost() + data.getCost(successeur)) {
                        queue.insert(Y);
                        //Father(y) <- x ( sous entendu l'arc de x à y donc successeur)
                        Y.setPère(successeur);
                    }
                }
            }
         }

        
        //Je copie-colle la création de la solution de Bellman-ford. avec PredecessorsArcs = labels(i).getPère

        // Destination has no predecessor, the solution is infeasible...
        if (labels[data.getDestination().getId()].getPère() == null) { 
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[data.getDestination().getId()].getPère();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getPère();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }
         
 }


