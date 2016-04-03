/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import java.util.ArrayList;

/**
 *
 * @author Frederic.Pitel
 */
public class AIAvance extends AIDebutant{
    private ArrayList<Coup> navireEnemiTouche;
    
    @Override
    public Coup demanderCoup(){
        Coup coupSuivant;
        if (!navireEnemiTouche.isEmpty()){
            if (navireEnemiTouche.size() == 1){
                return obtenirCoupAdjacent(navireEnemiTouche.get(0));
            }
            coupSuivant = obtenirCoupAdjacent(navireEnemiTouche.get(navireEnemiTouche.size() - 1));
            if (coupSuivant == null){
                coupSuivant = obtenirCoupAdjacent(navireEnemiTouche.get(0));
            }
            return coupSuivant;
        }
        return super.demanderCoup();
    }

    
    
    @Override
    public void sauvegarderCoup(Coup coup){
        if (coup.getResultat() == Controleur.Partie.Resultat.TOUCHE){
            navireEnemiTouche.add(coup);
        } else if (coup.getResultat() == Controleur.Partie.Resultat.COULE){
            navireEnemiTouche.clear();
        }
        super.sauvegarderCoup(coup);
    }
    
    private Coup obtenirCoupAdjacent(Coup coupPrecedant) {
        int tempIndex;
        Coup coupSuivant;
        tempIndex = coupPrecedant.getX() + 1;
        coupSuivant = new Coup(tempIndex, coupPrecedant.getY());
        if (tempIndex < 10 && !listeCoups.contains(coupSuivant)) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getX() - 1;
        coupSuivant = new Coup(tempIndex, coupPrecedant.getY());
        if (tempIndex >= 0 && !listeCoups.contains(coupSuivant)) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getY() + 1;
        coupSuivant = new Coup(coupPrecedant.getX(), tempIndex);
        if (tempIndex < 10 && !listeCoups.contains(coupSuivant)) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getY() - 1;
        coupSuivant = new Coup(coupPrecedant.getX(), tempIndex);
        if (tempIndex >= 0 && !listeCoups.contains(coupSuivant)) {
            return coupSuivant;
        }
        return null;
    }
}