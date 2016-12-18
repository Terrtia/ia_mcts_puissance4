package graou.graph;

/**
 * Classe de création des arêtes pour les graphes
 *
 */

public class Edge {
	
   private int from;
   private int to;
   private int cost;
   private int uMinCost; //valeur du chemin de cout minimal pour le sommet u
   
   /**
    * Constructeur
    * @param x - numéro du sommet de départ
    * @param y - numéro du sommet d'arrivée
    * @param cost - valeur
    */
   public Edge(int x, int y, int cost) {
		this.from = x;
		this.to = y;
		this.cost = cost;
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
   
}
