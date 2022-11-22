/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author bouvi
 */
public class Tux extends EnvNode {
    private Env env;
    private Room room;
    public Tux(Env env, Room room){
        this.env = env;
        this.room = room;
        
        setScale(4.0);
        setX( this.room.getWidth()/2 );// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ( this.room.getDepth()/2 ); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj"); 
    }
    
    public void déplace(){
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
       // Haut
            if(!testeRoomCollision(this.getX(), this.getZ()-1.0)){
                this.setRotateY(180);
                this.setZ(this.getZ() - 1.0);
            }
   
       }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            if(!testeRoomCollision(this.getX()-1.0, this.getZ())){
                this.setRotateY(270);
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
            // Bas
            if(!testeRoomCollision(this.getX(), this.getZ()+1.0)){
                this.setRotateY(0);
                this.setZ(this.getZ() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Gauche
            if(!testeRoomCollision(this.getX()+1.0, this.getZ())){
                this.setRotateY(90);
                this.setX(this.getX() + 1.0);
            }
        }
    }
    
    public boolean testeRoomCollision(double x, double z){
        boolean collision = false;
        
        if((z + getScale() == room.getDepth()) || (z-getScale() == 0) || (x + getScale() == room.getWidth()) || (x-getScale() == 0) ){
            collision = true;
        }
        
        return collision;
    }
    
    
}
