/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author bouvi
 */
public class LanceurDeJeu {
    public static void main(String args[]) {
        // Declare un Jeu
        Jeu jeu;
        //Instancie un nouveau jeu
        jeu = new JeuDevineLeMotOrdre();
        //Execute le jeu
        jeu.execute();
    }
}
