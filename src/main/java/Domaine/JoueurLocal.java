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
public class JoueurLocal implements Joueur {

    final private ArrayList<Navire> navires;
    private ArrayList<Coup> listeCoups;
    private int naviresCoules = 0;

    public JoueurLocal() {
        this.navires = new ArrayList();
        this.listeCoups = new ArrayList();
    }

    @Override
    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne) {
        Case cases = new Case(positionX, positionY);
        Navire navire = new Navire(longueur, cases, tourne);
        this.navires.add(navire);
    }

    @Override
    public Coup envoyerCoup(Coup coup) {
        coup.resultat = Controleur.Partie.Resultat.MANQUE;

        for (Navire nav : navires) {
            for (Case cas : nav.getCases()) {
                if (cas.equals(coup)) {
                    coup.resultat = nav.toucher(cas);
                    if (coup.resultat == Controleur.Partie.Resultat.COULE) {
                        naviresCoules++;
                    }
                    break;
                }
            }
            if (coup.resultat != Controleur.Partie.Resultat.MANQUE) {
                break;
            }
        }
        if (naviresCoules == navires.size()) {
            coup.resultat = Controleur.Partie.Resultat.TERMINE;
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
}
