/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author trant
 */


public class Tux extends EnvNode {

    private final Env env;
    private final mainRoom room;

    public Tux(Env env, mainRoom room) {
        this.env = env;
        this.room = room;
        setScale(5.0);
        setX(room.getWidth() / 2);
        setY(getScale() * 1.1); 
        setZ(room.getDepth() / 2);
        setModel("models/tux/tux.obj");
        setTexture("models/tux/tux_special.png");
    }
    
    // Tux déplace normal avec la collision de la Room
    public void déplace() { //TODO : mettre des position "à demie tourné" pour le tux
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            this.setRotateY(180);
            if (!testeRoomCollision(this.getX(), (this.getZ() - 1.0 - getScale()))){
                this.setZ(this.getZ() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
            //bas
            this.setRotateY(0);
            if (!testeRoomCollision(this.getX(), (this.getZ() + 1.0 + getScale()))){
                this.setZ(this.getZ() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            this.setRotateY(270);
            if (!testeRoomCollision((this.getX()-1.0 - getScale()), this.getZ())){
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite
            this.setRotateY(90);
            if (!testeRoomCollision((this.getX()+1.0 + getScale()), this.getZ())){
                this.setX(this.getX() + 1.0);
            }
        }
    }
    
     //Test collision du Tux avec Room
    private Boolean testeRoomCollision(double x, double z){
         return (x < 0 || x > room.getWidth() || z < 0 || z > room.getDepth());
    }
    
    // Test collision du Tux avec une lettre
    private Boolean testeLetterCollision(double x, double z, Letter letter) {
        return (x < letter.getX() + letter.getScale() && x > letter.getX() - letter.getScale() && z < letter.getZ() + letter.getScale() && z > letter.getZ() - letter.getScale());

    
    }
     //Test collision du Tux avec une list des lettres
    public boolean collisionMots(double x, double z,ArrayList<Letter> letters) {
        for (Letter letter : letters) {
            if (testeLetterCollision(x, z, letter)) {
                return true;
            }
        }

        return false;
    }
    
    //Tux déplace avec les collisions des lettres et la Room
    public void déplace(ArrayList<Letter> letters){
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            this.setRotateY(180);
            //test si la position est dans la room et teste si la position est en collision avec une lettre
            if (!testeRoomCollision(this.getX(), (this.getZ() - 1.0 - getScale())) && !collisionMots(this.getX(), (this.getZ() - 1.0 - getScale()), letters)){
                this.setZ(this.getZ() - 1.0);
            }

        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
            //bas
            this.setRotateY(0);
            if (!testeRoomCollision(this.getX(), (this.getZ() + 1.0 + getScale())) && !collisionMots(this.getX(), (this.getZ() + 1.0 + getScale()), letters)){
                this.setZ(this.getZ() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            this.setRotateY(270);
            if (!testeRoomCollision((this.getX()-1.0 - getScale()), this.getZ()) && !collisionMots((this.getX()-1.0 - getScale()), this.getZ(), letters)){
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite
            this.setRotateY(90);
            if (!testeRoomCollision((this.getX()+1.0 + getScale()), this.getZ()) && !collisionMots((this.getX()+1.0 + getScale()), this.getZ(), letters)){
                this.setX(this.getX() + 1.0);
            }
        }
    }    
    
    
    
}

