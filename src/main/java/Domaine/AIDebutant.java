/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import Controleur.Partie.Resultat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Frederic.Pitel
 */
public class AIDebutant implements Joueur {

    final private List<Navire> navires;
    final private List<Coup> listeCoups;
    private int naviresCoules = 0;

    public AIDebutant() {
        navires = new ArrayList();
        listeCoups = new ArrayList();
        int[] longueurs = {5, 4, 3, 3, 2};
        boolean collision;
        Navire nav;

        for (int i = 0; i < longueurs.length; i++) {
            Case cases = new Case();
            Boolean tourne = false;
            collision = true;

            while (collision) {
                int positionX = ThreadLocalRandom.current().nextInt(0, 10);
                int positionY = ThreadLocalRandom.current().nextInt(0, 10);
                int randTourne = ThreadLocalRandom.current().nextInt(0, 2);

                if (randTourne == 0) {
                    tourne = true;
                }

                if (positionX > longueurs[i] && positionY > longueurs[i]) {
                    if (!tourne) {
                        positionY -= (positionY - longueurs[i]);
                    } else {
                        positionX -= (positionX - longueurs[i]);
                    }
                } else if (positionX > longueurs[i] && tourne) {
                    positionX -= (positionX - longueurs[i]);
                } else if (positionY > longueurs[i] && !tourne) {
                    positionY -= (positionY - longueurs[i]);
                }
                cases.setCoord(positionX, positionY);
                nav = new Navire(longueurs[i], cases, tourne);
                assert (nav.getCases().size() == longueurs[i]);
                collision = verifierCollision(nav);
                if (!collision) {
                    navires.add(nav);
                }
            }
        }
        assert (navires.size() == 5);
    }

    public AIDebutant(List<Navire> navires, List<Coup> listeCoups, int naviresCoules) {
        this.listeCoups = listeCoups;
        this.navires = navires;
        this.naviresCoules = naviresCoules;
    }

    private boolean verifierCollision(Navire nav) {
        for (Navire navire : navires) {
            for (Case cas : navire.getCases()) {
                for (Case caseNav : nav.getCases()) {
                    if (cas.equals(caseNav)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne) {

    }

    @Override
    public Coup envoyerCoup(Coup coup) {
        coup.setResultat(Controleur.Partie.Resultat.MANQUE);

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
        Coup coup;

        do {
            coup = new Coup(ThreadLocalRandom.current().nextInt(0, 10), ThreadLocalRandom.current().nextInt(0, 10));
        } while (listeCoups.contains(coup));

        return coup;
    }

    @Override
    public void sauvegarderCoup(Coup coup) {
        listeCoups.add(coup);
    }

    protected Coup getCoupSauvegarder(Coup coupRecherche) {
        int index;
        index = listeCoups.indexOf(coupRecherche);
        if (index == -1) {
            return null;
        }
        return listeCoups.get(index);
    }

    @Override
    public List<Navire> getListNavire() {
        return navires;
    }

    @Override
    public List<Coup> getListCoup() {
        return listeCoups;
    }

    @Override
    public int getNaviresCoules() {
        return naviresCoules;
    }
}
