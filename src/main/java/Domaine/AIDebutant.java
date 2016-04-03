/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Frederic.Pitel
 */
public class AIDebutant implements Joueur{
    final private ArrayList<Navire> navires;
    final private ArrayList<Coup> listeCoups;
    private int naviresCoules = 0;
    
    public AIDebutant(){
        navires = new ArrayList();
        listeCoups = new ArrayList();
        int[] longueurs = {5, 4, 3, 3, 2};
        boolean collision;
        Navire nav;
        
        for(int i = 0; i < longueurs.length; i++){
            Case cases = new Case();
            Boolean tourne = false;
            collision = true;
            
            while(collision){
                int positionX = ThreadLocalRandom.current().nextInt(0, 10);
                int positionY = ThreadLocalRandom.current().nextInt(0, 10);
                int randTourne = ThreadLocalRandom.current().nextInt(0, 2);
                
                if(randTourne == 0){
                    tourne = true;
                }
                
                if(positionX > longueurs[i] && positionY > longueurs[i]){
                    if(!tourne){
                        positionY -= (positionY - longueurs[i]);
                    } else {
                        positionX -= (positionX - longueurs[i]);
                    }
                } else if(positionX > longueurs[i] && tourne){
                    positionX -= (positionX - longueurs[i]);
                } else if(positionY > longueurs[i] && !tourne){
                    positionY -= (positionY - longueurs[i]);
                }
                cases.setCoord(positionX, positionY);
                nav = new Navire(longueurs[i] ,cases , tourne);
                assert(nav.getCases().size() == longueurs[i]);
                collision = verifierCollision(nav);
                if(!collision){
                    navires.add(nav);
                }
            }
        }
        assert(navires.size() == 5);
    }
    
    private boolean verifierCollision(Navire nav){
        for(Navire navire : navires){
            for(Case cas : navire.getCases())
                for(Case caseNav : nav.getCases()){
                    if(cas.equals(caseNav)){
                        return true;
                    }
                }
        }
        return false;
    }
    
    @Override
    public void placerNavire(int positionX, int positionY, int longueur, boolean tourne){
        
    }
    
    @Override
    public Coup envoyerCoup(Coup coup){
        coup.resultat = Controleur.Partie.Resultat.MANQUE;
        
        for(Navire nav : navires){
            for(Case cas : nav.getCases()){
                if(cas.equals(coup)){
                    coup.resultat = nav.toucher(cas);
                    if(coup.resultat == Controleur.Partie.Resultat.COULE){
                        naviresCoules++;
                    }
                    break;
                }
            }
            if(coup.resultat != Controleur.Partie.Resultat.MANQUE){
                break;
            }
        }
        if(naviresCoules == navires.size()){
            coup.resultat = Controleur.Partie.Resultat.TERMINE;
        }
        
        return coup;
    }
    
    @Override
    public Coup demanderCoup(){
        Coup coup;
        
        do{
            coup = new Coup(ThreadLocalRandom.current().nextInt(0, 10), ThreadLocalRandom.current().nextInt(0, 10));
        } while(listeCoups.contains(coup));
        
        return coup;
    }

    @Override
    public void sauvegarderCoup(Coup coup){
        listeCoups.add(coup);        
    }
}
