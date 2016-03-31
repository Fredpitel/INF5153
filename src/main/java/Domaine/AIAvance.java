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
public class AIAvance implements Joueur{
    final private ArrayList<Navire> navires;
    final private ArrayList<Coup> listeCoups;
    private int naviresCoules = 0;
    
    public AIAvance(){
        navires = new ArrayList();
        listeCoups = new ArrayList();
    }
    
    @Override
    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne){
    }
    @Override
    public Coup envoyerCoup(Coup coup){
        return null;
    }
    
    @Override
    public Coup demanderCoup(){
        return null;
    }
    
    @Override
    public void sauvegarderCoup(Coup coup){
        listeCoups.add(coup);        
    }
}