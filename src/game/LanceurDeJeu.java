/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;


import env3d.Env;

/**
 *
 * @author trant
 */

public class LanceurDeJeu {
    
    public static void main(String args[]) {
        
        
        Jeu jeuTux;
        
        //Instancie un nouveau jeu
        jeuTux = new JeuDevineLeMotOrdre();
        
        //Execute le jeu
        jeuTux.execute();
        
        
            
    }


}