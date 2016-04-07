/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import Controleur.Partie.Resultat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frederic.Pitel
 */
public class JoueurLocal implements Joueur {

    final private List<Navire> navires;
    private final List<Coup> listeCoups;
    private int naviresCoules = 0;

    public JoueurLocal() {
        this.navires = new ArrayList();
        this.listeCoups = new ArrayList();
    }
    
    public JoueurLocal(List<Navire> navires, List<Coup> listeCoups, int naviresCoules) {
        this.listeCoups = listeCoups;
        this.navires = navires;
        this.naviresCoules = naviresCoules;
    }

    @Override
    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne) {
        Case cases = new Case(positionX, positionY);
        Navire navire = new Navire(longueur, cases, tourne);
        this.navires.add(navire);
    }

    @Override
    public Coup envoyerCoup(Coup coup) {
        coup.setResultat(Resultat.MANQUE);

        for (Navire nav : navires) {
            for (Case cas : nav.getCases()) {
                if (cas.equals(coup)) {
                    coup.setResultat(nav.toucher(cas));
                    if (coup.getResultat() == Resultat.COULE) {
                        naviresCoules++;
                    }
                    break;
                }
            }
            if (coup.getResultat() != Resultat.MANQUE) {
                break;
            }
        }
        if (naviresCoules == navires.size()) {
            coup.setResultat(Resultat.TERMINE);
        }

        return coup;
    }

    @Override
    public Coup demanderCoup() {
        return null;
    }

    @Override
    public void sauvegarderCoup(Coup coup) {
        listeCoups.add(coup);
    }
    
    @Override
    public List<Navire> getListNavire(){
        return navires;
    }
    
    @Override
    public List<Coup> getListCoup(){
        return listeCoups;
    }
}
