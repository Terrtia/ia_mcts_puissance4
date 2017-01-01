package game.model;

import game.ia.utc.UTC;

import java.util.Scanner;

import static game.model.Puissance4.Mouvement.*;

/**
 * Classe
 */
public class Puissance4 {
    private int grille[][]  ;
    private boolean p1 ;
    enum Mouvement {DROITE,BAS,BASGAUCHE,BASDROITE};
    public enum End {NO,DRAW,P1,P2};
    private End  end;

    public Puissance4(){
        grille = new int [6][7];
        p1 = false ;

        //creation de la grille de jeu
        for(int i =0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                grille[i][j] = 0;
            }
        }
        end = End.NO;
    }

    /**
     * Constructeur de copie
     * @param copie, Puissance4 à copier
     */
    public Puissance4(Puissance4 copie){
        this.p1 = copie.p1;
        grille = new int[6][7];
        for(int i =0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                grille[i][j] = copie.grille[i][j];
            }
        }


        end = copie.getEnd();
    }

    /**
     * Gestion du tour du joueur humain
     */
    public void tour(){
        int ligne=0;
        int col;
        afficherJeu(); //affichage du plateau de jeu sur la console

        //recuperation du coup du joueur
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir la colonne :");
        try {
            col = Integer.parseInt(sc.nextLine());
        }catch(Exception e ){
            col = 9;
        }

        mouvement(col);
        afficherJeu(); //affichage du plateau de jeu sur la console
    }

    /**
     * Verifie si un mouvement est valide (si la colonne est pleine ou non)
     * @param move, numero
     * @return true si valide, false sinon
     */
    public boolean mouvementValide(int move) {
        if(this.grille[5][move]==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Placer les coups dans la grille
     * @param col, numero de la colonne ou jouer
     */
    public void mouvement(int col) {
        int ligne = 0;
        if(col >= 0 && col <= 6){ //placer le coup sur la bonne ligne
            while (ligne < 6 && grille[ligne][col] != 0) {
                ligne++;
            }
        }
        if(ligne < 6 && col >= 0 && col <= 6) {
            if (p1) { //Humain
                grille[ligne][col] = 1;
            } else {  //IA
                grille[ligne][col] = 2;
            }
            p1 = !p1;
        }else{ //gestion des coup invalide
            System.out.println("\ncoup impossible rejouez\n " + col );
            tour();
        }

    }

    /**
     * Gestion partie 2 Joueurs Humain
     */
    public void play(){
        while(getEnd()==End.NO){
            tour();
            end();
        }
        if(this.getEnd() == End.P1){
            System.out.println("P1 WIN");
        }else if(this.getEnd() == End.P2){
            System.out.println("P2 WIN");
        }else{
            System.out.println("DRAW");
        }
    }

    /**
     * Gestion d'une partie contre une IA
     */
    public void playIA(){
        UTC utc = new UTC(this);

        while(getEnd()==End.NO){
            if(!p1){ //tour du joueur humain
                tour();
            }else{   //tour de l'IA
                utc.setRoot(this);
                this.mouvement(utc.getMouvement());
            }
            end();
        }

        // Gestion de l'affichage en cas de fin de la partie
        if(this.getEnd() == End.P1){
            System.out.println("P1 WIN");
        }else if(this.getEnd() == End.P2){
            System.out.println("IA WIN");
            afficherJeu();
        }else{
            System.out.println("DRAW");
        }
    }

    /**
     * Verification si la partie est termine, met à jour l'attribut end
     */
    public void end(){
        int ligne = 0;
        int col;
        int val;

        boolean draw = true;

        //gestion egalite
        for(int i = 0; i < 6 ;i++){
            for(int j = 0; j < 7;j++){
                if(grille[i][j] == 0) {
                    draw = false;
                }
            }
        }

        if(draw) {
            setEnd(End.DRAW);
        }

        //verification victoire p1, p2
        while(ligne < 6 && getEnd()==End.NO) {
            col = 0;
            while (col < 7 && getEnd() == End.NO) {
                if (grille[ligne][col] != 0) {
                    val = grille[ligne][col];
                    if (suite(ligne, col, val) == 4) { //il y a une suite de 4 symboles(1 ou 2)
                        if (p1) {
                            setEnd(End.P1);
                        } else {
                            setEnd(End.P2);
                        }
                    }
                }
                col++;
            }
            ligne++;
        }
    }

    public int suite(int ligne,int col,int val){
        int max;
        max = 1+ Math.max(suite(ligne+1,col-1,val,BASGAUCHE),
                Math.max(suite(ligne+1,col+1,val,BASDROITE),
                Math.max(suite(ligne,col+1,val,DROITE), suite(ligne+1,col,val,BAS))));
        return  max;
    }

    public int suite(int ligne, int col, int val, Mouvement mouv){
        int max = 0 ;
        if (ligne >= 0 && ligne <= 5 && col >= 0 && col <= 6) {
            if (grille[ligne][col] == val) {
                max = 1;
                switch (mouv) {
                    case DROITE:
                        if (col + 1 >= 0 && col + 1 <= 6)
                            max = 1 + suite(ligne, col + 1, val, mouv);
                        break;
                    case BAS:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5) {
                            max = 1 + suite(ligne + 1, col, val, mouv);
                        }
                        break;
                    case BASGAUCHE:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5 && col - 1 >= 0 && col - 1 <= 6) {
                            max = 1 + suite(ligne + 1, col - 1, val, mouv);
                        }
                        break;
                    case BASDROITE:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5 && col + 1 >= 0 && col + 1 <= 6) {
                            max = 1 + suite(ligne + 1, col + 1, val, mouv);
                        }
                        break;
                }
            }
        }
        return max;
    }

    /**
     * @return String du plateau de jeu
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("        0   1   2   3   4   5   6    \n\n");
        for(int i =0 ; i < 6 ; i++){
            sb.append(i +"     | ");
            for (int j = 0 ; j < 7 ; j++){
                switch(grille[i][j]){
                    case 0:
                        sb.append("  | ");
                        break;
                    case 1:
                        sb.append("x | ");
                        break;
                    case 2:
                        sb.append("o | ");
                        break;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void afficherJeu(){
        System.out.println(this.toString());
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End newEnd) {
        end = newEnd;
    }

    public boolean isP1(){
        return p1;
    }
    public void setP1(boolean p1) {
        this.p1 = p1;
    }
}
