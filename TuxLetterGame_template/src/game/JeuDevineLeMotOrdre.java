/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author bouvi
 */
public class JeuDevineLeMotOrdre extends Jeu {
    int nbLettresRestantes;
    Chronometre chrono;
    
    public JeuDevineLeMotOrdre(){
        super();
    }

    @Override
    protected void démarrePartie(Partie partie) {
       nbLettresRestantes = lettres.size();
       chrono = new Chronometre(60000);
       while(nbLettresRestantes ==0 && chrono.remainsTime()){
           appliqueRegles(partie);
       }
       terminePartie(partie);
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        if(tuxTrouveLettre()){
            if(nbLettresRestantes == 0){
                chrono.stop();
                terminePartie(partie);
            }
        }
    }

    @Override
    protected void terminePartie(Partie partie) {
        if(chrono.getSeconds() == 0){
            partie.setTemps((int)chrono.getTime());
        }else{
            partie.setTemps(chrono.getSeconds());
        }
       partie.setTrouve(nbLettresRestantes);
    }
    
    
    private boolean tuxTrouveLettre(){
        boolean res = false;
        if(nbLettresRestantes == 0){
            res = true;
        }else{
            if(collision(lettres.get(lettres.size()-nbLettresRestantes + 1))){
            res = true;
            nbLettresRestantes--;
            }
        }
        
        
        
        return res;
    }
    
    
}
