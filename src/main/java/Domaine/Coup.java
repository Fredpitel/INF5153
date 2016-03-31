/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

/**
 *
 * @author Frederic.Pitel
 */
public class Coup {
    int x;
    int y;
    Controleur.Partie.Resultat resultat;
    
    public Coup(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Coup (int x, int y, Controleur.Partie.Resultat res){
        this.x = x;
        this.y = y;
        resultat = res;
    }
    
    public boolean equals(Coup coup){
        return(this.x == coup.x && this.y == coup.y);
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public Controleur.Partie.Resultat getResultat(){
        return this.resultat;
    }
}
