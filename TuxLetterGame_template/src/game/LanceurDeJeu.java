/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author bouvi
 */
public class LanceurDeJeu {
    public static void main(String args[]) throws ParserConfigurationException, TransformerException {
        // Declare un Jeu
        Jeu jeu = null;
        try {
            //Instancie un nouveau jeu
            jeu = new JeuDevineLeMotOrdre();
        } catch (SAXException ex) {
            Logger.getLogger(LanceurDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LanceurDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Execute le jeu
        jeu.execute();
    }
}
