/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;



/**
 *
 * @author trant
 */
public class JeuDevineLeMotOrdre extends Jeu {
    private int nbLettresRestantes;
    private Chronometre chrono;

    public JeuDevineLeMotOrdre() {
        super();
    }
    //la méthode tuxTrouveLettre() :renvoie true si la première lettre de la liste de lettres (restantes) du mot est en contact avec le personnage tux.
    public boolean tuxTrouveLettre() {
        Letter letter = listLetters.get(0); //get the first letter of the list
        //System.out.println(letter.getLetter());
        //System.out.println(collision(letter));
        return collision(letter);
        
    }

   
// La méthode démarrePartie(partie : Partie) : permet de démarrer une partie. Elle instancie un chronomètre 
//et initialise le nombre de lettres restantes à trouver.
    @Override
    public void démarrePartie(Partie partie) {
    
    int timeLimite = 0;
    switch (partie.getNiveau()) {
        case 1:
            timeLimite = 60;
            break;
        case 2:
            timeLimite = 45;
            break;
        case 3:
            timeLimite = 30;
            break;
        case 4:
            timeLimite = 20;
            break;
        case 5:
            timeLimite = 10;
            break;
        default:
            break;
    }
    chrono = new Chronometre(timeLimite);
    chrono.start();
    nbLettresRestantes = partie.getMot().length();
    
}
    
// la méthode appliqueRegles(partie : Partie) : permet de vérifier si le temps est écoulé 
//et si le joueur a trouvé une lettre, et de mettre à jour le nombre de lettres restantes à trouver.
// Si le temps est écoulé, la partie est terminée.
    @Override
    public void appliqueRegles(Partie partie) {
    // si le temps n'est pas écoulé et qu'il reste des lettres à trouver
    if(chrono.remainsTime() && nbLettresRestantes > 0) {
        // si tux trouve la lettre
        if(tuxTrouveLettre()) {
            nbLettresRestantes--;
            listLetters.remove(0); 
        }
    }
    else {
        if(chrono.getTime()<0){
            System.out.println("Temps écoulé");
            chrono.stop();
            partie.setArret(true);
        }
    }
    

   
}
// La méthode terminePartie(partie : Partie) : permet de terminer une partie. Elle arrête le chronomètre 
//et sauvegarde le temps de la partie et le pourcentage de lettres trouvées.
    @Override
    public void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTemps(chrono.getSeconds());
        partie.setTrouve(nbLettresRestantes); 
        System.out.println(partie.toString());
    }
    
    
}