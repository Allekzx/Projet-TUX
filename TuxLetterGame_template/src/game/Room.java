package game;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author bouvi
 */
public class Room {
    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureSouth;
    private String textureTop;
    
    
    
    public Room(){
        this.textureBottom = "textures/skybox/interstellar/bottom.png";
        this.textureEast = "textures/skybox/interstellar/east.png";
        this.textureNorth = "textures/skybox/interstellar/north.png";
        this.textureWest = "textures/skybox/interstellar/west.png";
        this.depth = 100;
        this.width = 100;
        this.height = 60;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }
    
}
