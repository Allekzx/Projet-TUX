/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author bouvi
 */
public class Partie {
    private String date;
    private String mot;
    private int niveau;
    private int trouvé;
    private int temps;
    
    
    public Partie(String date, String mot, int niveau){
        this.date = date;
        this.niveau = niveau;
        this.mot = mot;
    }
    
    public Partie(Element partieElt){
        this.date = partieElt.getAttribute("date");
        
        this.temps = Integer.parseInt(partieElt.getElementsByTagName("temps").item(0).getTextContent());
        
        this.niveau = Integer.parseInt(partieElt.getElementsByTagName("niveau").item(0).getAttributes().item(0).getTextContent());
        
        this.mot = partieElt.getElementsByTagName("mot").item(0).getTextContent();
        
        if(partieElt.hasAttribute("trouvé")){
            this.trouvé =Integer.parseInt(partieElt.getAttribute("trouvé"));
        }else{
            this.trouvé = 100;
        }
        
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getTrouvé(){
        return trouvé;
    }
    
    public String getMot(){
        return mot;
    }
    
    public void setTrouve(int nbLettresRestantes){
        trouvé = (int)((mot.length()-nbLettresRestantes)/mot.length())*100;
    }
    public void setTemps(int temps){
        this.temps = temps;
    }
    
    public int getNiveau(){
        return 0;
    }
    
    
    public int getTemps(){
        return this.temps;
    }
    
    @Override
    public String toString(){
        String res = "";
        res += "Partie terminée :\n";
        
        if(temps == 0){
            res += "Malheurement vous avez mit plus de 60 secondes a trouver le mot";
            res += "\n Vous avez trouvé "+trouvé+"% du mot, votre partie est sauvegardée et vous pourrez réessayer le meme mot";
        }else{
            res += "Vous avez trouvé "+ trouvé +"% du mot : " + mot;
            res += " en "+ temps + " secondes";
        }
        return res;
    }
    
    public Element getPartie(Document doc){
        Element res = doc.createElement("partie");
        res.setAttribute("date", this.date);
      
        if(trouvé != 100){
            res.setAttribute("trouvé", ""+trouvé);
        }
        
        Element temps = doc.createElement("temps");
        temps.setTextContent(""+this.temps);
        
        Element niveau = doc.createElement("niveau");
        niveau.setAttribute("numero", ""+this.niveau);
        
        Element mot = doc.createElement("mot");
        mot.setTextContent(this.mot);
        
        niveau.appendChild(mot);
        res.appendChild(temps);
        res.appendChild(niveau);
        
        return res;
        
    }
    
}
