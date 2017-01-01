package game;

import game.model.Puissance4;

/**
 * main class
 */
public class Game {
    public Game(){
        Puissance4 p = new Puissance4();
        p.afficherJeu(); //affichage du plateau de jeu sur la console
        p.playIA();
    }

    public static void main (String[] args){
        Game g = new Game();
    }
}
