package game.model;

import java.util.Scanner;

import static game.model.Puissance4.Mouvement.*;

/**
 * Created by brice on 19/12/16.
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

        for(int i =0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                grille[i][j] = 0;
            }
        }
        end = End.NO;
    }

    public Puissance4(Puissance4 copie){
        this.p1 = copie.p1;
        for(int i =0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                grille[i][j] = copie.grille[i][j];
            }
        }
        end = copie.getEnd();
    }

    public void afficherJeu(){
        System.out.println(this.toString());
    }

    public void tour(){
        int ligne=0;
        int col;
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir la colonne :");
        try {
            col = Integer.parseInt(sc.nextLine());
        }catch(Exception e ){
            col = 9;
        }
        mouvement(col);
    }

    public void mouvement(int col) {
        int ligne = 0;
        if(col >= 0 && col <= 6){
            while (ligne < 6 && grille[ligne][col] != 0) {
                ligne++;
            }
        }
        if(ligne < 6 && col >= 0 && col <= 6) {
            if (p1) {
                grille[ligne][col] = 1;
            } else {
                grille[ligne][col] = 2;
            }
            p1 = !p1;
        }else{
            System.out.println("\ncoup impossible rejouez\n");
            tour();
        }

    }

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

    public void play(){
        while(getEnd()==End.NO){
            tour();
            afficherJeu();
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

    public void end(){
        int ligne = 0;
        int col;
        int val;

        boolean draw = true;

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

        while(ligne < 6 && getEnd()==End.NO) {
            col = 0;
            while (col < 7 && getEnd() == End.NO) {
                if (grille[ligne][col] != 0) {
                    val = grille[ligne][col];
                    if (suite(ligne, col, val) == 4) {
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
        max = 1+ Math.max(suiteMouvement(ligne+1,col-1,val,BASGAUCHE),
                Math.max(suiteMouvement(ligne+1,col+1,val,BASDROITE),
                Math.max(suiteMouvement(ligne,col+1,val,DROITE),suiteMouvement(ligne+1,col,val,BAS))));

       // System.out.println("\nval : "+val+"\nmax : "+max);
        return  max;
    }

    public int suiteMouvement(int ligne,int col,int val,Mouvement mouv){
        int max = 0 ;
        if (ligne >= 0 && ligne <= 5 && col >= 0 && col <= 6) {
            if (grille[ligne][col] == val) {
                max = 1;
                switch (mouv) {
                    case DROITE:
                        if (col + 1 >= 0 && col + 1 <= 6)
                            max = 1 + suiteMouvement(ligne, col + 1, val, mouv);
                        break;
                    case BAS:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5) {
                            max = 1 + suiteMouvement(ligne + 1, col, val, mouv);
                        }
                        break;
                    case BASGAUCHE:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5 && col - 1 >= 0 && col - 1 <= 6) {
                            max = 1 + suiteMouvement(ligne + 1, col - 1, val, mouv);
                        }
                        break;
                    case BASDROITE:
                        if (ligne + 1 >= 0 && ligne + 1 <= 5 && col + 1 >= 0 && col + 1 <= 6) {
                            max = 1 + suiteMouvement(ligne + 1, col + 1, val, mouv);
                        }
                        break;
                }
            }
        }
        return max;
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
}
