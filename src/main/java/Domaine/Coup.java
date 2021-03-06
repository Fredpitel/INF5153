/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import Controleur.Partie.Resultat;

/**
 *
 * @author Frederic.Pitel
 */
public class Coup {

    private int x;
    private int y;
    private Resultat resultat;

    public Coup(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coup(int x, int y, Resultat res) {
        this.x = x;
        this.y = y;
        resultat = res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coup other = (Coup) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Resultat getResultat() {
        return this.resultat;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }
}
