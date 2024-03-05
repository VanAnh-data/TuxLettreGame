/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author trant
 */
public class Dico extends DefaultHandler {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;
    private StringBuffer buffer;
    
    public Dico() {
    super();
    }

    //Ecrivez le constructeur de manière à ce qu'il initialise les ArrayList.
    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        this.listeNiveau1 = new ArrayList();
        this.listeNiveau2 = new ArrayList();
        this.listeNiveau3 = new ArrayList();
        this.listeNiveau4 = new ArrayList();
        this.listeNiveau5 = new ArrayList();
        lireDictionnaireDOM(cheminFichierDico);
    }

    /*Codez la méthode getMotDepuisListeNiveau(niveau : int) : String. Cette méthode permet de piocher aléatoirement un mot dans une liste où le niveau est donné en paramètre. Pour cela, il est judicieux de créer une autre méthode, getMotDepuisListe(list : ArrayList<String>) : String. La première méthode servira à sélectionner une liste selon un niveau fourni en paramètre, tandis que la deuxième extraira un mot au hasard d'une liste donnée. */
    public String getMotDepuisListeNiveau(int niveau) {
        niveau = vérifieNiveau(niveau);
        switch (niveau) {
            case 1:
                return getMotDepuisListe(listeNiveau1);
            case 2:
                return getMotDepuisListe(listeNiveau2);
            case 3:
                return getMotDepuisListe(listeNiveau3);
            case 4:
                return getMotDepuisListe(listeNiveau4);
            case 5:
                return getMotDepuisListe(listeNiveau5);
            default:
                return getMotDepuisListe(listeNiveau1);
        }

    }

    private String getMotDepuisListe(ArrayList<String> list) {
        //vérfie si la liste est vide
        if (list.size() == 0) {
            return "";
        }
        //pioche un mot au hasard

        int index = (int) (Math.random() * list.size());
        return list.get(index);

    }

    // Vérifier le niveau est de 1 à 5 , si non niveau est 1 
    private int vérifieNiveau(int niveau) {
        if (niveau <= 5 && niveau >= 1) {
            return niveau;
        }
        return 1;
    }

    // Ajouter un mot dans la liste correspond avec niveau 
    public void ajouteMotADico(int niveau, String mot) {
        //vérifie si le niveau est valide
        niveau = vérifieNiveau(niveau);
        //vérifie si le mot est valide
        boolean motValide = verifieMot(mot);
        //ajoute le mot à la liste correspondante
        if (motValide) {
            switch (niveau) {
                case 1:
                    listeNiveau1.add(mot);
                    break;
                case 2:
                    listeNiveau2.add(mot);
                    break;
                case 3:
                    listeNiveau3.add(mot);
                    break;
                case 4:
                    listeNiveau4.add(mot);
                    break;
                case 5:
                    listeNiveau5.add(mot);
                    break;
            }

        }
    }

    // véirfie si le mot est valide: un mot est valide s'il n'est pas null, sa longueur est supérieur à 3 et il n'existe pas dans les listes
    private boolean verifieMot(String mot) {
        if (mot == null || mot.length() == 0 || mot.length() < 3 || listeNiveau1.contains(mot) || listeNiveau2.contains(mot) || listeNiveau3.contains(mot) || listeNiveau4.contains(mot) || listeNiveau5.contains(mot)) {
            return false;
        }
        return true;
    }
    
    //Cette méthode permet de lire le fichier XML contenant les mots et de les ajouter au dictionnaire en utilisant un parseur DOM.
    public void lireDictionnaireDOM(String chemin) {
        try {
            //créer une instance de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(chemin);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("mot"); //récupère tous les noeuds "mot"
            for (int i = 0; i < nodes.getLength(); i++) { //parcours tous les noeuds "mot"
                Node node = nodes.item(i); //récupère le noeud courant
                String niveau = node.getAttributes().getNamedItem("niveau").getNodeValue();
      
                String mot = node.getTextContent(); //récupère le contenu du noeud
                ajouteMotADico(Integer.parseInt(niveau), mot); //ajoute le mot au dictionnaire

            }

        } catch (Exception e) {

        }
    }
    
        
        //------------------------SAX---------------------------------//
    //La méthode lireDictionnaireSAX(String chemin) : void. Cette méthode permet de lire le fichier XML contenant les mots et de les ajouter au dictionnaire. Pour cela, vous utiliserez un parseur SAX.
    public void lireDictionnaire(String chemin) throws SAXException, IOException {
            try {
                      // création d'une fabrique de parseurs SAX 
			SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
  
			// création d'un parseur SAX 
			SAXParser parseur = fabrique.newSAXParser(); 
  
			// lecture d'un fichier XML avec un DefaultHandler 
			File fichier = new File(chemin); 
			DefaultHandler gestionnaire = new DefaultHandler(); 
			parseur.parse(fichier, gestionnaire); 
            } catch(ParserConfigurationException pce){ 
			System.out.println("Erreur de configuration du parseur"); 
			System.out.println("Lors de l'appel à newSAXParser()"); 
		}catch(SAXException se){ 
			System.out.println("Erreur de parsing"); 
			System.out.println("Lors de l'appel à parse()"); 
		}catch(IOException ioe){ 
			System.out.println("Erreur d'entrée/sortie"); 
			System.out.println("Lors de l'appel à parse()"); 
		} 
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "dictionnaire":
                System.out.println("Début de lecture du fichier");
                break;
            case "mot":
                String niveau = attributes.getValue("niveau");
                String mot = attributes.getValue("mot");
                ajouteMotADico(Integer.parseInt(niveau), mot);
                break;
            default:
                System.out.println("Balise inconnue");
                break;
        }


    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "mot":
                System.out.println("Fin de lecture du mot");
                break;
            case "dictionnaire":
                System.out.println("Fin de lecture du fichier");
                break;
            default:
                System.out.println("Balise inconnue");
                break;
        }

    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String mot = new String(ch, start, length);
        System.out.println("Mot : " + mot);


    }

    @Override
    public void startDocument() throws SAXException {}

    @Override
    public void endDocument() throws SAXException {} 
    //------------------------------------------------------SAX-----------------------------//
}

