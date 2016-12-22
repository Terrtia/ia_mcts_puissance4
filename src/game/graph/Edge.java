package game.graph;

import game.model.Puissance4;

import java.util.Random;

/**
 * Classe de création des arêtes pour les graphes
 *
 */

public class Edge {
	
   private int from;
   private int to;
   private int cost;
   private int uMinCost; //valeur du chemin de cout minimal pour le sommet u

    private int nbWin;
    private int nbSimulation;
    private Puissance4 state;
   
   /**
    * Constructeur
    * @param x - numéro du sommet de départ
    * @param y - numéro du sommet d'arrivée
    * @param cost - valeur
    */
   public Edge(int x, int y, int cost,Puissance4 newState) {
		this.from = x;
		this.to = y;
		this.cost = cost;
        state = new Puissance4(newState);
       nbWin = 0;
       nbSimulation = 0;
   }
   
   /****************************** GET FUNCTION *******************/
   
   /**
    * @return le numéro du sommet de départ
    */
   public int getFrom() {
	   return this.from;
   }
   
   /**
    * @return le numéro du sommet d'arrivée
    */
   public int getTo() {
	   return this.to;
   }
   
   /**
    * @return la valeur de l'arête
    */
   public int getCost() {
	   return this.cost;
   }
   
   /**
    * Change le sens de l'arrête entre 2 sommets
    */
   public void swapToFromZeroCost() {
	   
	   int u = this.from;
	   int v = this.to;
	   
	   this.from = v;
	   this.to = u;
	   this.cost = 0;
	   
   }
   
   public void setCost(int c) {
	   this.cost = c;
   }
   
   public int getMinCost() {
	   return this.uMinCost;
   }
   
   public void setMinCost(int mincost) {
	   this.uMinCost = mincost;
   }
   
   public int getVMinCost() {
	   return this.getTo();
   }

   public int simulation (){
       Random rand = new Random();
       while(state.getEnd() == Puissance4.End.NO){
           state.mouvement( rand.nextInt(7));
       }
       if(state.getEnd() == Puissance4.End.DRAW || state.getEnd() == Puissance4.End.P1) {
           return 0;
       }else{
           return 1;
       }
   }

   public void mAJ(int victoire){
       this.nbSimulation++;
       this.nbWin++;
       if(this.get)
   }
   
}
