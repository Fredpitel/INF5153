/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Domaine.AIDebutant;
import Domaine.Joueur;
import Domaine.AIAvance;
import Domaine.JoueurLocal;
import Domaine.Case;
import Domaine.Coup;
import XML.SaveLoadXML;

/**
 *
 * @author Frederic.Pitel
 */

public class Partie {

    public static final int NOM_MAX = 20;

    public enum Difficulte {

        DEBUTANT, AVANCE
    }

    public enum Resultat {

        TOUCHE, COULE, MANQUE, TERMINE
    }

    private String nomUtilisateur;
    private Difficulte difficulte;
    private static Joueur joueurLocal;
    private static Joueur joueurAutre;

    public void creerPartie() {
        joueurLocal = new JoueurLocal();
        switch (this.difficulte) {
            case DEBUTANT:
                joueurAutre = new AIDebutant();
                break;
            case AVANCE:
                joueurAutre = new AIAvance();
                break;
        }
    }

    public Partie(Difficulte diff) {
        this.difficulte = diff;
    }

    public void setNomUtilisateur(String nom) {
        this.nomUtilisateur = nom;
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne) {
        joueurLocal.placerNavire(positionX, positionY, longueur, tourne);
    }

    public Resultat envoyerCoup(int x, int y) {
        Coup coup = new Coup(x, y);

        coup = joueurAutre.envoyerCoup(coup);
        joueurLocal.sauvegarderCoup(coup);
        return coup.getResultat();
    }

    public Coup demanderCoup() {
        Coup coup = joueurAutre.demanderCoup();
        coup = joueurLocal.envoyerCoup(coup);
        joueurAutre.sauvegarderCoup(coup);
        return coup;
    }
    
    public boolean sauvegarderPartie(){
        SaveLoadXML saveloadXML = new SaveLoadXML();
        return saveloadXML.sauvegarderPartie(joueurLocal, joueurAutre, difficulte);
    }
    
    public boolean chargerPartie(){
        SaveLoadXML saveloadXML = new SaveLoadXML();
        difficulte = saveloadXML.chargerDifficulte();
        joueurLocal = saveloadXML.chargerJoueurLocal();
        joueurAutre = saveloadXML.chargerJoueurAdversaire(difficulte);
        return joueurLocal != null && joueurAutre != null && difficulte != null;
    }
}
