/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;



/**
 *
 * @author bouvi
 */
public class Dico {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;

    private String cheminFichierDico;

    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();

    }

    public String getMotDepuisListeNiveaux(int niveau) {
        String s = "";

        switch (verifieNiveau(niveau)) {
            case 1:
                s = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                s = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                s = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                s = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                s = getMotDepuisListe(listeNiveau5);
                break;
            default:
                break;
        }
        return s;
    }

    private int verifieNiveau(int niveau) {
        int res = niveau;

        if (niveau > 5) {
            res = 5;
        }
        if (niveau < 1) {
            res = 1;
        }
        return res;
    }

    private String getMotDepuisListe(ArrayList<String> list) {
        if (list.isEmpty()) {
            return "vide";
        } else {
            int nombreAleatoire = (int) (Math.random() * (list.size()));
            return list.get(nombreAleatoire);
        }

    }

    public void ajoutMotDico(int niveau, String mot) {
        switch (verifieNiveau(niveau)) {
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
        }
    }

    public void lireDictionnaireDOM() {
        try {
            
      
           
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            FileInputStream in = new FileInputStream(new File(this.cheminFichierDico));
            Document doc = dbBuilder.parse(in, "UTF-8");
            

            NodeList nListeNiveau = doc.getElementsByTagName("niveau");
            int niveau = 1;
            for (int i = 0; i < nListeNiveau.getLength(); i++) {
                Element tempListeNiveau = (Element) nListeNiveau.item(i);
                NodeList nListeMot = tempListeNiveau.getElementsByTagName("mot");
                for (int j = 0; j < nListeMot.getLength(); j++) {
                    Node mot = nListeMot.item(j);
                    Element elt = (Element) mot;
                    this.ajoutMotDico(niveau, elt.getTextContent());
                }
                niveau++;
            }

        } catch (Exception e) {
            e.toString();
        }

    }
}
