/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Domaine.Case;
import Domaine.Coup;
import Domaine.Joueur;
import Domaine.Navire;
import java.util.List;

/**
 *
 * @author Patrick Blanchette
 */
public class IteratorJoueur {
    List<Coup> listCoup;
    int indexCoup = 0;
    List<Navire> listNavire;
    int indexNavire = 0;

    public IteratorJoueur(Joueur joueur) {
        this.listCoup = joueur.getListCoup();
        this.listNavire = joueur.getListNavire();
    }
    
    public Coup prochainCoup(){
        if (indexCoup >= listCoup.size()){
            return null;
        }
        Coup coup = listCoup.get(indexCoup);
        indexCoup++;
        return coup;
    }
    
    public boolean prochainNavire(){
        if (indexNavire >= listNavire.size()){
            return false;
        }
        indexNavire++;
        return true;
    }
    
    public Case getPositionNavire(){
        return listNavire.get(indexNavire).getCases().get(0);
    }
    
    public boolean getOrientationNavire(){
        return listNavire.get(indexNavire).getTourne();
    }
    
    public void debut(){
        indexCoup = 0;
        indexNavire = 0;
    }
}
