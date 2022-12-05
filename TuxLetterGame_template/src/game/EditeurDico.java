/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author bouvi
 */
public class EditeurDico {
    private Document doc;
    
    public EditeurDico(Document doc){
        this.doc = doc;
    }
    
    public void lireDOM(String fichier) throws SAXException, ParserConfigurationException, FileNotFoundException, IOException{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        FileInputStream in = new FileInputStream(new File(fichier));
        doc = dbBuilder.parse(in, "UTF-8");
  }
    public void ecrireDOM(String fichier) throws TransformerException{
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //initialize StreamResult with File object to save to file
        StreamResult result = new StreamResult(fichier);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
    }
    
    public void ajouterMot(String mot, int niveau){
        Element nMot = doc.createElement("mot");
        nMot.setTextContent(mot);
        NodeList nNiveau = doc.getElementsByTagName("niveau");
        int i = 0;
        while(i < nNiveau.getLength()){
            Element eNiveau = (Element) nNiveau.item(i);
            if(Integer.parseInt(eNiveau.getAttribute("numero")) == niveau){
                nNiveau.item(i).appendChild(nMot);
                break;
            }
            i++;
        }
    }
}

