/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author bouvi
 */
public class Letter extends EnvNode {

    private char letter;

    public Letter(char letter, double x, double y) {

        this.letter = letter;
        setScale(5.0);
        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(y); // positionnement au milieu de la profondeur de la room

        if (this.letter == ' ') {
            setTexture("models/letter/cube.png");
        } else {
            setTexture("models/letter/" + this.letter + ".png");
        }
        setModel("models/letter/cube.obj");

    }
}
