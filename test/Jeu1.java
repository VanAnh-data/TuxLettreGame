

import env3d.Env;
import game.Dico;
import game.Letter;
import game.Partie;
import game.Profil;
import game.mainRoom;
import game.Tux;
import java.util.ArrayList;
import java.util.Random;

public abstract class Jeu1 {

    private Env env;
    private mainRoom room;
    private Profil profil;
    private Tux tux;
    private Letter lettre;
    protected ArrayList<Letter> listLetters;
    private Dico dico;

    public Jeu1() {
        // instancier un dictionnaire
        dico = new Dico("dico.txt");
        dico.ajouteMotADico(1, "tard");
        dico.ajouteMotADico(2, "hhedf");

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room   
        room = new mainRoom();

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        //Desactive les controles par defaut
        env.setDefaultControl(false);
        // Instancie un profil par defaut
        profil = new Profil();

        listLetters = new ArrayList();

    }

    public void execute() {

        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue(new Partie("01/01/2021", "s", 4));

        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }

    public void joue(Partie partie) {

        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);

        // Instancie un Tux
        //Ajouter l'instance de Tux dans le jeu en utilisant la méthode addObject issue de la classe Env. La méthode addObject prend un EnvNode (donc votre tux) en paramètre. Ajouter ce code avant la boucle de jeu de la méthode Joue.
        tux = new Tux(env, room);
        //lettre = new Letter('a', 1, 1, 1);
        env.addObject(tux);
        
        String mot = partie.getMot();
        
        for (int i=0; i<mot.length(); i++){
            double x = Math.random()*100;
            double y = Math.random()*100;
            char c = mot.charAt(i);
            Letter letter = new Letter(c,x,y);
            listLetters.add(letter);
            env.addObject(letter);
        }
        

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1 || partie.isArret()) {
                finished = true;
            }
            else {

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();

            // Ici, on applique les regles
            appliqueRegles(partie);
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
    }

    protected abstract void démarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

    //la méthode distance(letter:Letter) : double de la classe Jeu renvoie la distance du personnage tux à la lettre.
    public double distance(Letter letter) {
        System.out.println(tux.distance(letter));
        return tux.distance(letter);
        
    }
//la méthode collision(letter:Letter) :renvoie true si le personnage tux est en collision avec la lettre passée en paramètre.

    public boolean collision(Letter letter) {
        boolean collision = false;
        if(distance(letter)<(tux.getScale() + letter.getScale())) {
            collision = true;
            env.removeObject(letter);
        }
        return collision;
    }

}
