package game.ia.utc;

import game.model.Puissance4;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe de création des arêtes pour les graphes
 */

public class Node {

    private Node pere;
    private ArrayList<Node> filsListe;
    private int nbWin;
    private int nbSimulation;
    private Puissance4 state;
    private boolean ia ;
    private int mouvement;

   

   public Node(Puissance4 newState) {
       state = new Puissance4(newState);
       nbWin = 0;
       nbSimulation = 0;
       ia = false;
       filsListe = new ArrayList<>();
   }

    public Node(int newMouvement, Node newPere, Puissance4 newState) {
        pere = newPere;
        state = new Puissance4(newState);
        nbWin = 0;
        nbSimulation = 0;
        ia = !pere.isIa();
        this.mouvement = newMouvement;
        filsListe = new ArrayList<>();
    }

    public Node selection() {
        this.state.end();
        int filsPossible =0;
        if(this.state.getEnd()!= Puissance4.End.NO) {
            return this;
        }else{
            for(int i = 0; i < 7 ; i++){
                if(state.mouvementValide(i)){
                    filsPossible++;
                }
            }
            if(this.filsListe.isEmpty()||this.filsListe.size() < filsPossible){
                return this;
            }else{
                double max =this.getFils(0).getBeta();
                int selection = 0;
                for(int i = 0; i < filsListe.size();i++){
                    if(this.getFils(i).getBeta()>max){
                        max = this.getFils(i).getBeta();
                        selection = i;
                    }
                }
                return getFils(selection).selection();
            }
        }
    }

    public Node developpe() {
        this.state.end();
        if(this.state.getEnd()== Puissance4.End.NO){

            ArrayList<Integer> moveList = new ArrayList<>();
            for (int i = 0; i < 7; i ++){
                moveList.add(i);
            }
            for(Node e : filsListe){
                moveList.remove((Object)e.getMouvement());
            }

            Random rand = new Random();
            int move = moveList.get(rand.nextInt(moveList.size()));

            Puissance4 copie = new Puissance4(this.state);

            while(!copie.mouvementValide(move)){
                moveList.remove((Object)move);
                move = moveList.get(rand.nextInt(moveList.size()));
            }

            copie.mouvement(move);

            Node newFils = new Node(move,this,copie);
            this.filsListe.add(newFils);
            return newFils;
        }else{
            return this;
        }
    }

   public int simulation (){
       Random rand = new Random();
       int move;
       Puissance4 copie = new Puissance4(state);
       copie.end();
       while(copie.getEnd() == Puissance4.End.NO){
           move = rand.nextInt(7);
           if(copie.mouvementValide(move)) {
               copie.mouvement(move);
               copie.end();
           }
       }
       if(copie.getEnd() == Puissance4.End.P2 ) {
           return 1;
       }else{
           return 0;
       }
   }

    public int simulationGagnante (){
        Random rand = new Random();
        int move;
        int i =0;
        Puissance4 copie = new Puissance4(state);
        Puissance4 copie2 = new Puissance4(copie) ;
        copie.end();
        copie2.end();

        while (copie.getEnd() == Puissance4.End.NO) {
            while (copie2.getEnd() == Puissance4.End.NO && i < 7) {
                copie2 = new Puissance4(copie);
                if (copie2.mouvementValide(i)) {
                    copie2.mouvement(i);
                    copie2.end();
                }
                i++;
            }

            if(copie2.getEnd() == Puissance4.End.NO){
                move = rand.nextInt(7);
                while (!copie.mouvementValide(move)) {
                    move = rand.nextInt(7);
                }
                copie.mouvement(move);
                copie.end();
            } else {
                move = i - 1;
                copie.mouvement(move);
            }
            i = 0;
            copie.end();
        }
        copie.end();
        if(copie.getEnd() == Puissance4.End.P2) {
            return 1;
        }else{
            return 0;
        }
    }

   public void maj(int victoire){
       this.nbSimulation++;
       this.nbWin = this.nbWin + victoire;
       if(this.getPere()!= null) {
           getPere().maj(victoire);
       }
   }

    /**
     * @return numero de la colonne ou jouer le coup
     */
    public int getBestMovement() {
        double max = -1;
        int bestMovement = 9;
        for(Node fils : filsListe){
            //System.out.println("FILS "+fils.getMouvement()+" NB SIM = "+fils.nbSimulation);
            if ((double)fils.nbWin/fils.nbSimulation > max){
                max = (double)fils.nbWin/fils.nbSimulation;
                bestMovement = fils.getMouvement();
            }
        }
        System.out.println("NB Simulation = "+nbSimulation+"\nProba de victoire = "+ max);
        return bestMovement;
    }

    public Node getPere() {
        return pere;
    }

    public int getNbSimulation(){
        return nbSimulation;
    }

    /**
     * Calcul le beta du noeud
     */
    public double getBeta(){
        double beta;
        double mu;
        if(getPere() == null){
            beta = 1;
        }else {
            mu =  (double)(nbWin / nbSimulation);
            if (isIa()) {
                mu = -(mu);
            }
            beta = mu + (Math.sqrt(2) * Math.sqrt(Math.log(getPere().getNbSimulation()) / Math.log(getNbSimulation())));
        }
            return beta;
    }

    public boolean isIa() {
        return ia;
    }

    public int getMouvement() {
        return mouvement;
    }

    private Node getFils(int i) {
        return filsListe.get(i);
    }
}
