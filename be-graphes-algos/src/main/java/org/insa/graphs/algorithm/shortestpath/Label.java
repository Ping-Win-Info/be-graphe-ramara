package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{

   private boolean marque;
   private Node sommet;
   protected double cout;
   private Arc père ;
   
   public Label(Node sommet, boolean marque, Arc père) {
	   this.sommet=sommet;
	   this.marque=marque;
	   this.père=père;
	   this.cout = Double.MAX_VALUE;
   }
  
   
	
   public boolean isMarque() {
		return this.marque;
	}

	public void setMarque(boolean marque) {
		this.marque = marque;
	}
   
   public Node getSommet() {
	   return sommet;
   }
   
   public void setSommet(Node sommet) {
	   this.sommet=sommet;
   }
   
   public Arc getPère() {
	   return père;
   }
   
   public void setPère(Arc père) {
	   this.père=père;
   }
   
   public double getCost() {
	   return cout ;
   }
   
   public void setCout(double cout ) {
	   this.cout=cout;
   }
   
   
    



   @Override
   //Comparer 2 couts 
   public int compareTo(Label autre) {
      
      return Double.valueOf(this.getCost()-autre.getCost()).intValue(); 
      
      

   }
}
