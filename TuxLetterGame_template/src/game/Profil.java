/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import game.XMLUtil.DocumentTransform;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author bouvi
 */
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Profil {

    private String nom;
    private String avatar;
    private String dateNaissance;
    private ArrayList<Partie> parties;
    public Document _doc;

    public Profil() {

    }

    public Profil(String nomJoueur, String dateNaissance) throws ParserConfigurationException {
        this.nom = nomJoueur;
        this.dateNaissance = dateNaissance;
        parties = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        _doc = db.newDocument();
    }

    Profil(String nomFichier) {
        _doc = fromXML(nomFichier);

        Element profil = (Element) _doc.getElementsByTagName("profil").item(0);
        this.nom = profil.getElementsByTagName("nom").item(0).getTextContent();
        this.avatar = profil.getElementsByTagName("avatar").item(0).getTextContent();
        this.dateNaissance = xmlDateToProfileDate(profil.getElementsByTagName("anniversaire").item(0).getTextContent());

        parties = new ArrayList<>();

        NodeList nParties = _doc.getElementsByTagName("partie");
        for (int i = 0; i < nParties.getLength(); i++) {
            Element nPartie = (Element) nParties.item(i);
            Partie p = new Partie(nPartie);
            parties.add(p);

        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDate(String date) {
        this.dateNaissance = date;
    }

    public void setParties(ArrayList<Partie> parties) {
        this.parties = parties;
    }

    public String getNom() {
        return nom;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDate() {
        return dateNaissance;
    }

    public ArrayList<Partie> getParties() {
        return parties;
    }

    public void ajoutePartie(Partie partie) {
        parties.add(partie);
    }

    public boolean charge(String nomJoueur) {
        return (nomJoueur.equals(this.nom));
    }

    private Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    private static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)

    private static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    // Sauvegarde un DOM en XML

    private void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sauvegarder(String nomFichier) throws TransformerException {
        if (fromXML(nomFichier) == null) {
            Element root = _doc.createElement("profil");
            _doc.setXmlVersion("1.0");

            root.setAttribute("xmlns", "http://myGame/tux");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/profil.xsd");

            Element nNom = _doc.createElement("nom");
            nNom.setTextContent(nom);

            root.appendChild(nNom);

            Element nAvatar = _doc.createElement("avatar");
            nAvatar.setTextContent(avatar);

            root.appendChild(nAvatar);

            Element nAnniversaire = _doc.createElement("anniversaire");
            nAnniversaire.setTextContent(profileDateToXmlDate(dateNaissance));

            root.appendChild(nAnniversaire);

            Element nParties = _doc.createElement("parties");

            for (Partie p : parties) {
                Element nPartie = p.getPartie(_doc);
                nParties.appendChild(nPartie);
            }
            root.appendChild(nParties);
            _doc.appendChild(root);
            
            toXML(nomFichier);
        }else{
            System.out.println("Nombre de partie : "+parties.size());
            Element parties = (Element) _doc.getElementsByTagName("parties").item(0);
            
            Partie p = this.parties.get(this.parties.size()-1);
            Element partie = _doc.createElement("partie");
            partie.setAttribute("date", ""+LocalDate.now());
            if (p.getTrouvé() != 100){
                partie.setAttribute("trouvé", ""+p.getTrouvé());
            }
            
            Element temps = (Element) _doc.createElement("temps");
            temps.setTextContent(""+p.getTemps());
            
            partie.appendChild(temps);
            
            Element niveau = (Element) _doc.createElement("niveau");
            niveau.setAttribute("numero", ""+p.getNiveau());
            
            Element mot = (Element) _doc.createElement("mot");
            mot.setTextContent(p.getMot());
            
            niveau.appendChild(mot);
            
            partie.appendChild(niveau);
                  
            parties.appendChild(partie);
            
            toXML(nomFichier);
        }

    }

    @Override
    public String toString() {
        String res = "Bonjour  : " + nom + "\n";

        res += "Voici les parties jouées de ce profil : ";

        for (Partie partie : parties) {
            res += "\n" + partie.toString();
        }
        return res;
    }

    public Partie chargePartie() {
        Partie res = null;
        int i = 0;
        while (i < parties.size() && parties.get(i).getTrouvé() == 100) {
            i++;
        }
        if (i < parties.size()) {
            res = parties.get(i);
            supprimerPartie(res);
        }
        return res;
    }

    private void supprimerPartie(Partie partie) {
        NodeList parties = _doc.getElementsByTagName("partie");
        int i = 0;
        while (i < parties.getLength()){
            Element nPartie = (Element) parties.item(i);
            if (partie.getMot().equals(nPartie.getElementsByTagName("mot").item(0).getTextContent())) {
                _doc.removeChild(nPartie);
                i = parties.getLength();
            }
        }
    }
    public void afficheProfil(String xslStreamSource){
        String res = "";
        try {   
            res = DocumentTransform.fromXSLTransformation(xslStreamSource, _doc);
            FileUtil.stringToFile(res, "src/data/html/profil-"+this.nom+".html");
            File htmlFile = new File("src/data/html/profil-"+this.nom+".html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        }catch(Exception e){
            e.toString();
        }
    }

    /* Todo list : 
        - afficher tous les profils classés par score
     */
}
