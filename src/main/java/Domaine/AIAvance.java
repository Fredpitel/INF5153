/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import Controleur.Partie.Resultat;
import java.util.List;

/**
 *
 * @author Frederic.Pitel
 */
public class AIAvance extends AIDebutant {

    private Coup dernierNavireTouche;

    public AIAvance() {
        dernierNavireTouche = null;
    }

    public AIAvance(List<Navire> navires, List<Coup> listeCoups, int naviresCoules, Coup dernierNavireTouche) {
        super(navires, listeCoups, naviresCoules);
        this.dernierNavireTouche = dernierNavireTouche;
    }

    public Coup getDernierNavireTouche() {
        return dernierNavireTouche;
    }

    @Override
    public Coup demanderCoup() {
        Coup coupSuivant;
        int tempIndex;
        if (dernierNavireTouche != null) {
            tempIndex = dernierNavireTouche.getX() + 1;
            coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
            if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) != null && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                    || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE)) {
                do {
                    tempIndex++;
                    coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
                    if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex < 10 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));

                tempIndex = dernierNavireTouche.getX();
                do {
                    tempIndex--;
                    coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
                    if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex >= 0 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));
            }
            tempIndex = dernierNavireTouche.getX() - 1;
            coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
            if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) != null && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                    || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE)) {
                do {
                    tempIndex--;
                    coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
                    if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex >= 0 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));

                tempIndex = dernierNavireTouche.getX();
                do {
                    tempIndex++;
                    coupSuivant = new Coup(tempIndex, dernierNavireTouche.getY());
                    if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex < 10 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));
            }
            tempIndex = dernierNavireTouche.getY() + 1;
            coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
            if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) != null && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                    || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE)) {
                do {
                    tempIndex++;
                    coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
                    if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex < 10 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));

                tempIndex = dernierNavireTouche.getY();
                do {
                    tempIndex--;
                    coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
                    if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex >= 0 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));
            }
            tempIndex = dernierNavireTouche.getY() - 1;
            coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
            if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) != null && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                    || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE)) {
                do {
                    tempIndex--;
                    coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
                    if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex >= 0 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));

                tempIndex = dernierNavireTouche.getY();
                do {
                    tempIndex++;
                    coupSuivant = new Coup(dernierNavireTouche.getX(), tempIndex);
                    if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
                        return coupSuivant;
                    }
                } while (tempIndex < 10 && (this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.TOUCHE
                        || this.getCoupSauvegarder(coupSuivant).getResultat() == Resultat.COULE));
            }
            return obtenirCoupAdjacent(dernierNavireTouche);
        }
        return super.demanderCoup();
    }

    @Override
    public void sauvegarderCoup(Coup coup) {
        if (coup.getResultat() == Controleur.Partie.Resultat.TOUCHE) {
            dernierNavireTouche = coup;
        } else if (coup.getResultat() == Controleur.Partie.Resultat.COULE) {
            dernierNavireTouche = null;
        }
        super.sauvegarderCoup(coup);
    }

    private Coup obtenirCoupAdjacent(Coup coupPrecedant) {
        int tempIndex;
        Coup coupSuivant;
        tempIndex = coupPrecedant.getX() + 1;
        coupSuivant = new Coup(tempIndex, coupPrecedant.getY());
        if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getX() - 1;
        coupSuivant = new Coup(tempIndex, coupPrecedant.getY());
        if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getY() + 1;
        coupSuivant = new Coup(coupPrecedant.getX(), tempIndex);
        if (tempIndex < 10 && this.getCoupSauvegarder(coupSuivant) == null) {
            return coupSuivant;
        }
        tempIndex = coupPrecedant.getY() - 1;
        coupSuivant = new Coup(coupPrecedant.getX(), tempIndex);
        if (tempIndex >= 0 && this.getCoupSauvegarder(coupSuivant) == null) {
            return coupSuivant;
        }
        return null;
    }
}
