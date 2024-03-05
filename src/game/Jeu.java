/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import env3d.Env;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    private final Env env;
    private Tux tux;
    private final mainRoom mainRoom;
    private final mainRoom menuRoom;
    private Letter letter;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText; //text (affichage des texte du jeu)
    protected ArrayList<Letter> listLetters; 
    protected mainRoom Room;
   
    
    
    
    
    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une mainRoom
        // On peut inicialiser une Room à partir un plateau (sans URL)
        //room = new mainRoom("src/xml/plateau.xml");
        
        // ou inicialiser une Room par défaut
        mainRoom = new mainRoom();
        
        
        

        // Instancie une autre Room pour les menus
        menuRoom = new mainRoom();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil();
        
        // Dictionnaire
        dico = new Dico("src/xml/dico.xml");
        
        //instanciation du conteneur de lettre
        listLetters = new ArrayList<>();
        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        // On demande la date de naissance du joueur et on l'affiche
        menuText.addText("Quelle est votre date de naissance ? " , "dateNaissance", 200, 300);
        // On demande le niveau de difficulté et on l'affiche
        menuText.addText("Choisissez un niveau de difficulté : ", "niveau", 200, 300);
        // On demande la date du jour et on l'affiche
        menuText.addText("Quel est votre jour de naissance ?(1-31) " , "jour", 200, 300);
        // On demande le mois et on l'affiche
        menuText.addText("Quel est votre mois de naissance ?(1-12) " , "mois", 200, 300);
        // On demande l'année et on l'affiche
        menuText.addText("Quelle est votre année de naissance ?(1900-2010) " , "année", 200, 300);
        menuText.addText("Mot à chercher", "mot", 200, 300);
         // demande si le joueur veut sauvegarder son historique de parties sous la forme HTML
        menuText.addText("Vous voulez voir votre profil HTML ? ", "sauvegardeHTML", 200, 300);
        menuText.addText("Ajouter un mot dans notre dico ?", "addDico", 200, 200);
       
        //addText:word
        menuText.addText("Quel mot voulez vous?", "word", 250, 280);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("4.Ajouter un mot dans le dictionnaire ?", "addWord", 250, 210);
        menuText.addText("Il n'existe pas le nom  ", "nom", 250, 210);
        menuText.addText("","resultat", 200,300);
        menuText.addText("Vous n'avez aucune partie existe", "erreur", 200, 300);
        //addText:room
        menuText.addText("Quelle room voulez vous jouer ?\n1. Cave \n2. Défault \n3. Evening \n4. Fantasy \n5. Snow \n6. Sunny \n", "room", 250, 240);
        
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {
        
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }


    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }
    
    //Demander la date de naissance du joueur et la renvoie sous la forme d'une chaîne de caractères
    private String getDateNaissance() {
        String dateNaissance = "";
        menuText.getText("jour").display();
        // vérifie si date est valide (entre 1 et 31)
        int jour = Integer.parseInt(menuText.getText("jour").lire(true));
        while (jour < 1 || jour > 31) {
            menuText.getText("jour").clean();
            menuText.getText("jour").display();
            jour = Integer.parseInt(menuText.getText("jour").lire(true));
        }
        
        menuText.getText("jour").clean();
        if (jour<10){
            dateNaissance += "0";
        }
        // on ajoute date à la chaîne de caractères dateNaissance
        dateNaissance += jour + "/";
        menuText.getText("mois").display();
        // vérifie si mois est valide (entre 1 et 12)
        int mois = Integer.parseInt(menuText.getText("mois").lire(true));
        while (mois < 1 || mois > 12) {
            menuText.getText("mois").clean();
            menuText.getText("mois").display();
            mois = Integer.parseInt(menuText.getText("mois").lire(true));
        }
        menuText.getText("mois").clean();
        // on ajoute mois à la chaîne de caractères dateNaissance
        if (mois<10){
            dateNaissance += "0";
        }
        dateNaissance += mois + "/";
        menuText.getText("année").display();
        // Vérifie si année est valide (entre 1900 et 2010)
        int année = Integer.parseInt(menuText.getText("année").lire(true));
        while (année < 1900 || année > 2010) {
            menuText.getText("année").clean();
            menuText.getText("année").display();
            année = Integer.parseInt(menuText.getText("année").lire(true));
        }
        menuText.getText("année").clean();
        //on ajoute année à la chaîne de caractères dateNaissance
        dateNaissance += année;
        menuText.getText("dateNaissance").clean();
        return dateNaissance;
        
        
    }
    // Afficher un mot en 2.5 secondes pour le joueur connaisse le mot à deviner
    private void afficheMot(String mot){
        menuText.getText("mot").display();
        menuText.getText("mot").modifyTextAndDisplay("Mot à chercher:  " + mot);
        env.advanceOneFrame();
        // attendre quelques secondes
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("Erreur : impossible d'attendre 3 secondes");
        }
        menuText.getText("mot").clean();
    }
    
    //Demander quel mot le joueur veut ajouter dans le dictionnaire
    private String getWord() {
        String word = "";
        //un mot doit contenir au moins 3 lettres
        menuText.getText("word").display();
        word = menuText.getText("word").lire(true);
        while (word.length() < 3) {
            menuText.getText("word").clean();
            menuText.getText("word").display();
            word = menuText.getText("word").lire(true);
        }
        menuText.getText("word").clean();
        return word;
    }
    
    
    //Demande le niveau de difficulté du joueur et le renvoie sous la forme d'un entier
    private int getNiveau() {
        int niveau;
        menuText.getText("niveau").display();
        niveau = Integer.parseInt(menuText.getText("niveau").lire(true));
        // gérer les exceptions
        try {
            while (niveau < 1 || niveau > 5) {
                menuText.getText("niveau").clean();
                menuText.getText("niveau").display();
                niveau = Integer.parseInt(menuText.getText("niveau").lire(true));
            }
        } catch (NumberFormatException e) {
            niveau = 1;
        }
        menuText.getText("niveau").clean();
        return niveau;
    }
    
    //Demande si le joueur veut sauvegarder son historique de parties sous la forme HTML
    private boolean sauvegardeHTML() {
        // affiche la question
        menuText.getText("sauvegardeHTML").display();
        // vérifie si oui ou non en tapant O ou N ou o ou n
        char choix = menuText.getText("sauvegardeHTML").lire(true).charAt(0);
        while (choix != 'O' && choix != 'N' && choix != 'o' && choix != 'n') {
            menuText.getText("sauvegardeHTML").clean();
            menuText.getText("sauvegardeHTML").display();
            choix = menuText.getText("sauvegardeHTML").lire(true).charAt(0);
        }
        menuText.getText("sauvegardeHTML").clean();
        // si oui, sauvegarde le profil sous la forme HTML
        if (choix == 'O' || choix == 'o') {
            return true;
        }
        return false;
       
    }
    
    // Affiche fichier HTML du profil du joueur si le joueur le souhaite
    private void afficheHTML() {
        // affiche le fichier HTML
        BrowserUtil.launch("src/html/" + profil.getNom() + ".html");
    }
    
    // Affiche le résultat de la partie après avoir joué une partie (gagné ou perdu) et le temps de jeu en secondes 
    private void afficheResultat(Partie partie) {
        String resultat = "";
        // affiche le résultat de la partie
        if (partie.getTrouvé() < 100) {
            resultat = "Vous avez perdu !";
        } else {
            resultat = "Vous avez gagné !";
        }
        resultat += "\nLe mot à deviner était : " + partie.getMot();
            //temps de jeu
        resultat += "\nTemps de jeu : " + partie.getTemps() + " secondes";
        menuText.getText("resultat").display();
        menuText.getText("resultat").modifyTextAndDisplay(resultat);
        env.advanceOneFrame();
        // attendre 20 secondes avant de commencer la partie (pour que le joueur connaisse le mot à deviner)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Erreur : impossible d'attendre 3 secondes");
        }
        menuText.getText("resultat").clean();
    }
    
    //Demander quelle room le joueur veut jouer, on a 6 rooms
    private int getRoom() {
        int room;
        menuText.getText("room").display();
        room = Integer.parseInt(menuText.getText("room").lire(true));
        // gérer les exceptions
        try {
            while (room < 1 || room > 6) {
                menuText.getText("room").clean();
                menuText.getText("room").display();
                room = Integer.parseInt(menuText.getText("room").lire(true));
            }
        } catch (NumberFormatException e) {
            room = 1;
        }
        menuText.getText("room").clean();
        return room;
    }

    // choisir une room en suivant le getRoom()
    private void chooseRoom(int room) {
        switch (room) {
            case 1:
                Room = new mainRoom("src/xml/plateaux/plateau_cave.xml");
                env.setRoom(Room);
                break;
            case 2:
                Room = new mainRoom("src/xml/plateaux/plateau_default.xml");
                env.setRoom(Room);
                break;
            case 3:
                Room = new mainRoom("src/xml/plateaux/plateau_evening.xml");
                env.setRoom(Room);
                break;
            case 4:
                Room = new mainRoom("src/xml/plateaux/plateau_fantasy.xml");
                env.setRoom(Room);
                break;
            case 5:
                Room = new mainRoom("src/xml/plateaux/plateau_snow.xml");
                env.setRoom(Room);
                break;
            case 6:
                Room = new mainRoom("src/xml/plateaux/plateau_sunny.xml");
                env.setRoom(Room);
                break;
            default:
                break;
        }
    }
    

    
    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);
           
            
            // 
            LocalDate today1 = LocalDate.now(); // récupère la date du jour
            String today = today1.toString();
            String mot;
            int niveau;
            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    //
                    
                    // demande un niveau de difficulté
                    niveau = getNiveau();
                    // charge un mot depuis le dico
                    mot = dico.getMotDepuisListeNiveau(niveau);
                    //afficheMot(mot);
                    // crée une nouvelle partie
                    partie = new Partie(today, mot, niveau);
                    int room = getRoom();
                    // Choisir une Room de jouer
                    chooseRoom(room);
                    afficheMot(mot); // afficher le mot à deviner en quelque seconds
                    // joue
                    joue(partie);
                    // On affiche le résultat 
                    afficheResultat(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    // ajoute la partie au profil
                    profil.ajoutePartie(partie);
                    // enregistre le profil
                    try {
                        profil.sauvegarder("src/xml/profils/" + profil.getNom() + ".xml");
                    } catch (Exception e) {
                        System.out.println("Erreur : impposible de sauvegarder le profil");
                    }
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    //charger une partie existante depuis le profil
                    if (profil.partiesVide()){
                        menuText.getText("erreur").display();
                        env.advanceOneFrame();
                    try {
                            Thread.sleep(3000);
                    }catch (InterruptedException e) {
                            System.out.println("Erreur : impossible d'attendre 3 secondes");
                        }
                    menuText.getText("erreur").clean();
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    }
                    else {
                    
                    partie = profil.chargePartieExistante(); //XXXXXXXXX
                    
                    
                    // Recupère le mot de la partie existante
                    mot = partie.getMot();
                    // affiche mot à deviner
                    //afficheMot(mot);
                    niveau = partie.getNiveau();
                    // créer une vouvelle partie
                    partie = new Partie(today,mot,niveau);
                    // ..........
                    // joue
                    int room1 = getRoom();
                    // chooseRoom
                    chooseRoom(room1);
                    
                    afficheMot(mot);
                    joue(partie);
                    afficheResultat(partie);
                    
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    // ajouter la partie au profil
                    profil.ajoutePartie(partie);
                    // entregistrer le profil
                    try {
                        profil.sauvegarder("src/xml/profils/" + profil.getNom() + ".xml");
                    } catch (Exception e) {
                        System.out.println("Erreur : impposible de sauvegarder le profil");
                    }
                    }
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    //demande si le joueur veut sauvegarder son historique de parties sous la forme HTML
                    
                    boolean sauvegarde = sauvegardeHTML();
                    if (sauvegarde) {
                        // créer un fichier HTML
                        File file = new File("src/html/" + profil.getNom() + ".html");
                        try {
                            file.createNewFile();
                        } catch (Exception e) {
                            System.out.println("Erreur : impossible de créer un fichier HTML");
                        }

                        // sauvegarde le profil sous la forme HTML
                        try {
                        profil.toHTML(profil.getNom());
                        afficheHTML();
                        }
                        catch (Exception e){
                            System.out.println("Erreur : impossible de créer un fichier HTML");
                        }
                    }
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal(){

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
        menuText.getText("addWord").display();       
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("addWord").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {
                    // Si le joueur n'existe pas dans le dossier , une message va etre afficher
                    menuText.getText("nom").display();
                    env.advanceOneFrame();
                    try {
                            Thread.sleep(3000);
                    }catch (InterruptedException e) {
                            System.out.println("Erreur : impossible d'attendre 3 secondes");
                        }
                    menuText.getText("nom").clean();
                    choix = MENU_VAL.MENU_CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // demande le date de naissance du nouveau joueur
                String dateNaissance = getDateNaissance();
     
                System.out.println(dateNaissance);

                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur, dateNaissance);
                // Créer un fichier XML avec le profil
                File file = new File("src/xml/profils/" + nomJoueur + ".xml");
                try {
                    file.createNewFile();
                 
                } catch (IOException e){
                    System.out.println("Erreur : impossible de créer un fichier XML");
                }
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
                break;
            // -------------------------------------
            // Touche 4 : Ajouter un mot dans dictionnaire
            // -------------------------------------
            case Keyboard.KEY_4:
                String word = getWord();
                int niveau = getNiveau();
                dico.ajouteMotADico(niveau, word);
                
                choix = MENU_VAL.MENU_CONTINUE;
                
        }
        return choix;
    }

    public void joue(Partie partie) {
        
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);

        //letter = new Letter('a', 10, 10);
        //env.addObject(letter);
        
        String mot = partie.getMot();
        
        for (int i=0; i<mot.length(); i++){
            
            double x = Math.random()*95+2;
            double y = Math.random()*95+2;
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
            
            //Mettre quatre cubes sans texture dans la room
            //Tux ne peut pas passer à travers ces cubes
            Letter l1 = new Letter(' ', 60, 60);
            Letter l2 = new Letter(' ', 50, 90);
            Letter l3 = new Letter(' ', 77, 44);
            Letter l4 = new Letter(' ', 10, 10);
            //metter les lettres dans la room
            env.addObject(l1);
            env.addObject(l2);
            env.addObject(l3);
            env.addObject(l4);
            //mettre les lettres dans la nouvelle liste
            ArrayList<Letter> listBox = new ArrayList<>();
            listBox.add(l1);
            listBox.add(l2);
            listBox.add(l3);
            listBox.add(l4);
            
             // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace(listBox);
            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
           }
        }
        //vider liste des lettrers
        listLetters.clear();
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);

    }
    public ArrayList<Letter> getLettres() {
        return listLetters;
    }
    

    protected abstract void démarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);


//la méthode distance(letter:Letter) : double de la classe Jeu renvoie la distance du personnage tux à la lettre.
    public double distance(Letter letter) {
        //System.out.println(tux.distance(letter));
        return tux.distance(letter);
        
    }
//la méthode collision(letter:Letter) :renvoie true si le personnage tux est en collision avec la lettre passée en paramètre.

    public boolean collision(Letter letter) {
        boolean collision = false;
        if(distance(letter)<(tux.getScale() + letter.getScale())/2) {
            collision = true;
            env.removeObject(letter);
        }
        return collision;
    }
    
    
    
}