package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{

   
   private Double cout_estime ;
   
   public LabelStar(Node sommet, boolean marque, Arc père, Node dest,int mode, int max_speed) { //maxspeed en km/h
	   super(sommet,marque,père);
	   this.cout = Double.MAX_VALUE;
      double distance_estime;
      double vitesse_ms ;
      if (mode==0) { //On est en temps
         distance_estime=sommet.getPoint().distanceTo(dest.getPoint());
         
         //v = d/t => t = d/v, d en mètre, v en m/s
         vitesse_ms=max_speed/3.6;
         this.cout_estime=distance_estime/vitesse_ms; 

      } else {
         this.cout_estime = sommet.getPoint().distanceTo(dest.getPoint());
      }
       
   }
   
   
	
   public double getTotalCost() {
    return cout + cout_estime;
   }
      

}

