/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

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
    
    public void setTrouve(int nbLettresRestantes){
        trouvé = (int)((mot.length()-nbLettresRestantes)/mot.length())*100;
    }
    public void setTemps(int temps){
        this.temps = temps;
    }
    
    public int getNiveau(){
        return 0;
    }
    
    @Override
    public String toString(){
        return "";
    }
    
}
