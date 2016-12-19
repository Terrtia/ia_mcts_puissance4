package game;

import game.model.Puissance4;

/**
 * Created by brice on 19/12/16.
 */
public class Game {
    public Game(){
        Puissance4 p = new Puissance4();
        p.afficherJeu();
        p.play();
    }

    public static void main (String[] args){
        Game g = new Game();
    }
}
