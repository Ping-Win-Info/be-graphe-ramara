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
         return null;
    }

}
