package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{

   
   private Double cout_estime ;
   
   public LabelStar(Node sommet, boolean marque, Arc père, Node dest) {
	   super(sommet,marque,père);
	   this.cout = Double.MAX_VALUE;
       this.cout_estime = sommet.getPoint().distanceTo(dest.getPoint());
   }
   
   
	
   public double getTotalCost() {
    return cout + cout_estime;
   }
      

}

