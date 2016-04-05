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
public abstract interface Joueur {

    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne);

    public Coup envoyerCoup(Coup coup);

    public Coup demanderCoup();

    public void sauvegarderCoup(Coup coup);
}
