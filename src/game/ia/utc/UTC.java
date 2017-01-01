package game.ia.utc;

import game.ia.IA;
import game.model.Puissance4;

/**
 * Algo UCT
 */
public class UTC implements IA {
    private Node root;

    public UTC(Puissance4 stateRoot){
        this.root = new Node(stateRoot);
    }

    /**
     * Recherche du meilleur coup à jouer
     * @return
     */
    public int getMouvement(){
        long start = System.currentTimeMillis();
        long time = 0;
        Node traiter,developpe;
        int sim;
       while (time < 5000){
           //etape 1 Selection
            traiter = root.selection();
           //etape 2 Devellopement
            developpe = traiter.developpe();
           //etape 3 Simulation
            sim = developpe.simulationGagnante();
           //etape 4 Propager
           developpe.maj(sim);

           time = System.currentTimeMillis() - start;
       }
       return root.getBestMovement();
    }

    public void setRoot(Puissance4 root) {
        this.root = new Node(root);
    }
}
