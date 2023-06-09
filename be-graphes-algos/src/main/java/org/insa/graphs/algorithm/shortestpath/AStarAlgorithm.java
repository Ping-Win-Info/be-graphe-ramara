package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    
    protected void init(){
        int mode;
        int i =0;
        int max_speed=graph.getGraphInformation().getMaximumSpeed();
        if (data.getMode()==Mode.TIME) {
            mode=0;
        } else {
            mode=1;
        }

         for (Node node : graph.getNodes()) {//On parcours tout les sommets du graphe
            labels[i]=new LabelStar(node, false, null,data.getDestination(),mode,max_speed);//NB: on les mets tous avec un cout infini ( par défault )
            i++;
         }

    }
}
