/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game;

/**
 *
 * @author bouvi
 */
public class TestDico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Dico dico = new Dico("src/data/xml/dico.xml");
       
       dico.lireDictionnaireDOM();
       
       System.out.println("Mot de la liste 1: "+dico.getMotDepuisListeNiveaux(1));
       System.out.println("Mot de la liste 2: "+dico.getMotDepuisListeNiveaux(2));
       System.out.println("Mot de la liste 3: "+dico.getMotDepuisListeNiveaux(3));
       System.out.println("Mot de la liste 4: "+dico.getMotDepuisListeNiveaux(4));
       System.out.println("Mot de la liste 5: "+dico.getMotDepuisListeNiveaux(5));
       System.out.println("Mot de la liste 5: "+dico.getMotDepuisListeNiveaux(5));
       System.out.println("Mot de la liste 5: "+dico.getMotDepuisListeNiveaux(5));
       System.out.println("Mot de la liste 9: "+dico.getMotDepuisListeNiveaux(9));
    }
    
}
