/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import java.util.ArrayList;

/**
 *
 * @author bouvi
 */
public abstract class Jeu {
    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    ArrayList<Letter> lettres;
    Dico dico;
    
    public Jeu(){
 
    // Crée un nouvel environnement
    env = new Env();
    
    dico = new Dico("src/data/xml/dico.xml");
    dico.lireDictionnaireDOM();
    // Instancie une Room
    room = new Room();
    
    lettres = new ArrayList<Letter>();
    // Règle la camera
    env.setCameraXYZ(50, 60, 175);
    env.setCameraPitch(-20);
 
    // Désactive les contrôles par défaut
    env.setDefaultControl(false);
 
    // Instancie un profil par défaut
    profil = new Profil();
 
}
    
    public void execute() {
 
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        
        String mot = dico.getMotDepuisListeNiveaux(2);
        for(int i = 0; i < mot.length(); i++){
            double x = (double) Math.random()*(room.getWidth());
            double y = (double) Math.random()* room.getDepth();
            lettres.add(new Letter(mot.charAt(i),x,y));
        }
        
        
        
        joue(new Partie("21/11/2022", mot, 2));
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
    
    public void joue(Partie partie) {
 
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
 
        // Instancie un Tux
        tux = new Tux(env, room);
        env.addObject(tux);
        
        for(Letter lettre: lettres){
            env.addObject(lettre);
        }
        
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {
 
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }
 
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            // ... (sera complété plus tard) ...
            tux.déplace();
 
            // Ici, on applique les regles
            appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
 
    }  
    
    
    protected abstract void démarrePartie(Partie partie);
    
    
    protected abstract void appliqueRegles(Partie partie);
    
    
    protected abstract void terminePartie(Partie partie);
    
    
    
    protected double distance(Letter letter){
        return tux.distance(letter);
    }
    
    
    protected boolean collision(Letter letter){
        boolean res = false;
        if(distance(letter) < letter.getScale()+tux.getScale()){
            res = true;
        }
        
        return res;
    }
    
    
}
