package game.model;

import java.util.Scanner;

/**
 * Created by brice on 19/12/16.
 */
public class Puissance4 {
    private int grille[][]  ;
    private boolean p1 ;

    public Puissance4(){
        grille = new int [6][7];
        p1 = true ;

        for(int i =0 ; i < 6 ; i++){
            for (int j = 0 ; j < 7 ; j++){
                grille[i][j] = 0;
            }
        }

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
            afficherJeu();
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
        while(!end()){
            tour();
        }
    }

    public boolean end(){
        int ligne = 0;
        int col = 0;
        int sum = 0;
        int val;

        boolean end = false;

        while(ligne < 6 && !end){
            while(col < 6 && !end){
               if(grille[ligne][col] != 0) {
                   val = grille[ligne][col];
                   if(suite(ligne,col,val) == 4){
                       end = true;
                   }
               }
               col++;
            }
            ligne++;
        }
        return end;
    }

    public int suite(int ligne,int col,int val){
        int max = 1;
        for(int i =  col - 1; i < col +2 ; i++ ){
            if(i >= 0 && i <= 6) {
                if (grille[ligne+1][i] == val) {
                    if (1 + suite(ligne+1, i, val) > max)
                        max = 1 + suite(ligne+1, i, val);
                }
            }
        }
        if(col+1 < 7) {
            if (grille[ligne][col+1] == val) {
                if (1+ suite(ligne, col+1, val) > max)
                    max = 1 + suite(ligne , col+1, val);
            }
        }
        return max;
    }
}
