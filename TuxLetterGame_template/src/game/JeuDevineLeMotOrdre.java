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

    Chronometre chrono;

    public JeuDevineLeMotOrdre() {
        super();
    }

    @Override
    protected void démarrePartie(Partie partie) {
        nbLettresRestantes = lettres.size();
        chrono = new Chronometre(60);   
        chrono.start();
        appliqueRegles(partie);
        }
    

    @Override
    protected boolean appliqueRegles(Partie partie) {
        boolean res = true;
        if(chrono.remainsTime()){
            if (tuxTrouveLettre()) {
                if (nbLettresRestantes == 0) {
                    chrono.stop();
                }
            }
        }else{
            res = false;
            
        }
        return res;
        
    }

    @Override
    protected void terminePartie(Partie partie) {
        if (chrono.getSeconds() == 0) {
            partie.setTemps((int) chrono.getTime());
        } else {
            partie.setTemps(chrono.getSeconds());
        }
        partie.setTrouve(nbLettresRestantes);
        lettres.clear();
    }

    private boolean tuxTrouveLettre() {
        boolean res = false;
        if (nbLettresRestantes == 0) {
            res = true;
        } else {

            if (collision(lettres.get(lettres.size() - nbLettresRestantes))) {
                env.removeObject(lettres.get(lettres.size() - nbLettresRestantes));
                res = true;
                nbLettresRestantes--;
            }
        }

        return res;
    }

}
