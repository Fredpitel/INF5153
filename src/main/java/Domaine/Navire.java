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
public class Navire {

    private int longueur;
    private ArrayList<Case> cases;
    private int casesTouchees = 0;
    public boolean tourne;

    public Navire() {

    }

    public Navire(int longueur, Case cases, boolean tourne) {
        this.longueur = longueur;
        this.tourne = tourne;
        this.cases = new ArrayList();

        if (!tourne) {
            for (int i = 0; i < longueur; i++) {
                Case caseBateau = new Case(cases.getX(), cases.getY() + i);
                this.cases.add(caseBateau);
            }
        } else {
            for (int i = 0; i < longueur; i++) {
                Case caseBateau = new Case(cases.getX() + i, cases.getY());
                this.cases.add(caseBateau);
            }
        }
    }

    public ArrayList<Case> getCases() {
        return this.cases;
    }

    public Controleur.Partie.Resultat toucher(Case cas) {
        casesTouchees++;
        if (casesTouchees == longueur) {
            return Controleur.Partie.Resultat.COULE;
        } else {
            return Controleur.Partie.Resultat.TOUCHE;
        }
    }
}
