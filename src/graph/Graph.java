package graou.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

/**
 * Classe de création des graphes
 *
 */

public class Graph implements Cloneable
{
   private ArrayList<Edge>[] adj;
   private final int V;
   private int coutMinSommetFinal;
   int E;
@SuppressWarnings("unchecked")
/**
 * Constructeur - Initialise V au nombre de sommets N
 * 				- Crée un tableau de listes d'arêtes (une pour chaque sommet) : adj
 * 				- Initialise E à 0
 * @param N - nombre de sommets (numérotés)
 */
   public Graph(int N)
	 {
		this.V = N;
		this.E = 0;
		adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v = 0; v < N; v++)
		  adj[v] = new ArrayList<Edge>();
	 }

/**
* @return le nombre de sommets V
*/
   public int vertices()
	 {
		return V;
	 }
   
   /**
    * Ajoute l'arête e dans le graphe
    * @param e - nouvelle arête
    */
   public void addEdge(Edge e)
	 {
		int v = e.getFrom();
		int w = e.getTo();
		adj[v].add(e);
		adj[w].add(e);
	 }
   
   /**
    * @param v - numéro du sommet
    * @return la liste des arêtes du sommet numéro v
    */
   public Iterable<Edge> adj(int v)
	 {
		return adj[v];
	 }      

   /**
    * @param v - numéro du sommet
    * @return la liste des arêtes sortantes du sommet numéro v
    */
   public Iterable<Edge> next(int v)
	 {
		ArrayList<Edge> n = new ArrayList<Edge>();
		for (Edge e: adj(v))
		  if (e.getTo() != v)
			n.add(e);
		return n;
	 }
   
   /**
    * @param v - numéro du sommet
    * @return la liste des arêtes entrantes du sommet numéro v
    */
   public Iterable<Edge> previous(int v)
	 {
		ArrayList<Edge> n = new ArrayList<Edge>();
		for (Edge e: adj(v)){
		  if (e.getFrom() != v){
			  n.add(e);
		  }
		}
		return n;
	 }
   
   /**
    * @return la liste de toutes les arêtes sortantes de tous les sommets
    */
   public Iterable<Edge> edges()
	 {
		ArrayList<Edge> list = new ArrayList<Edge>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj(v)) {
                if (e.getTo() != v)
                    list.add(e);
            }
        return list;
    }
   
   /**
    * Transforme le graphe en fichier .dot
    * @param s - nom du fichier résultat
    */
   public void writeFile(String s)
	 {
		try
		  {			 
			 PrintWriter writer = new PrintWriter(s, "UTF-8");
			 writer.println("digraph G{");
			 for (Edge e: edges())
			   writer.println(e.getFrom() + "->" + e.getTo() + "[label=\"" + e.getCost() + "\"];");
			 writer.println("}");
			 writer.close();
		  }
		catch (IOException e)
		  {
		  }						
	 }

   public void swapEdge(ArrayList<Integer> sommets, int s) {
	   
	   int deb = s;
	   //Remise dans l'ordre croissant des sommets
	   Collections.reverse(sommets);
	   
	   for(Integer sommet : sommets){
		   Edge e = this.getEdgeFromTo(deb, sommet);
		   e.swapToFromZeroCost();
		   
		   deb = sommet;
	   } 
	   
	   int last = this.V - 1;
	   this.getEdgeFromTo(deb, last).swapToFromZeroCost();
   }
   
   public Edge getEdgeFromTo(int from, int to){
	   Edge res = null;
	
	   Iterable<Edge> next = this.next(from);
	   for(Edge e : next){
		   if(e.getTo() == to) {
			   
			   res = e;
		   }
	   }
	   return res;
   }
   
   public int getMinCost(int sommet) {
	   int res = 10000;
	   Iterable<Edge> n = this.adj(sommet);
	   for(Edge e : n){
			  res = e.getMinCost();
		   } 
	   return res;
   }
   
   public void setMinCost(int sommet, int minCost) {
	   Iterable<Edge> n = this.next(sommet);
	   for(Edge e : n){
		  e.setMinCost(minCost);
	   }
	   if(sommet == this.V - 1){
		   this.coutMinSommetFinal = minCost;
	   }
   }
  
   
   public void calcMinCost(int depart) {
	   int sommetCourant;
	   int coutMin = 10000;
	   
	   this.setMinCost(depart, 0);// le sommet de depart, coutMin = 0
	   
	   for(int i=depart + 1; i<V; i++){
		   coutMin = 10000;
		   
		   sommetCourant = i;
		   
		   Iterable<Edge> previous = this.previous(sommetCourant);
		   
		   for(Edge e : previous){
			   int c = e.getMinCost() + e.getCost();
			   if(c < coutMin){
				   coutMin = c;
			   }
		   }
		   
		   this.setMinCost(sommetCourant, coutMin);
	   }
   }
   
   public void diffCost(int depart) {
	   int sommetCourant;
	   int minCostU;
	   int minCostV;
	   int newCost;
	   
	   for(int i=depart + 1; i<V; i++){
		   sommetCourant = i;
		   minCostU = this.getMinCost(sommetCourant);
		   
		   Iterable<Edge> next = this.next(sommetCourant);
		   
		   for(Edge e : next){
			  
			  minCostV = this.getMinCost(e.getTo());
			  if(e.getTo() == this.V - 1){
				  minCostV = this.coutMinSommetFinal;
			  }
			  
			  newCost = (minCostU - minCostV) + e.getCost();
			  
			  if(newCost > 0){
				  e.setCost(newCost);
			  } else {
				  e.setCost(0);
			  }
		   }   
	   }
   }

public boolean estCheminSommetFinal(int i) {
	boolean res = false;
	Iterable<Edge> n = this.next(i);
	for(Edge e : n){
		if(e.getTo() == this.vertices() - 1){
			res = true;
		}
	}
	return res;
}

public ArrayList<Integer> getDerniersSommets() {
	ArrayList<Integer> dernierSommets = new ArrayList<Integer>();
	
	for(int i=1; i<this.vertices() - 1; i++){
		Iterable<Edge> n = this.next(i);
		for(Edge e : n){		
			if(e.getTo() == this.vertices() - 1){
				if(!dernierSommets.contains(i)){
					dernierSommets.add(i);
				}
			}
		}	
	}
	
	return dernierSommets;
}
   
}
