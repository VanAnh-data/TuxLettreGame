/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import env3d.advanced.EnvNode;

/**
 *
 * @author trant
 */
public class Letter extends EnvNode {

    private char letter;

    public Letter(char l, double x, double y) {
        this.letter = l;
        setX(x);
        setY(getScale()*1.1+2);
        setZ(y);
        setScale(3.5);
        
        
        setModel("models/letter/cube.obj");
        if (l != ' ' ){
        setTexture("models/letter/" + l + ".png");
        }
        else {
            setTexture("models/letter/cube.png");
        }
    }

    public char getLetter() {
        return letter;
    }
    

    
    
    

    
}

