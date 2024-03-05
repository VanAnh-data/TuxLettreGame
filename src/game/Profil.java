package game;



import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author trant
 */
public class Profil {

    public Document _doc; // DOM du profil courant (celui qui est chargé) : il contient toutes les informations du profil
    private String nom;
    private String avatar;
    private String dateNaissance;
    private ArrayList<Partie> parties;
    


    //constructeur deux paramètres : nom et date de naissance
    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.avatar = "Doty Happy";
        this.parties = new ArrayList<Partie>();
        
        //Instanciation de Factory de Parser DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //Parser le document XML
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Renvoyer le Document DOM
            _doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        // créer l'élément racine
        Element racine = _doc.createElement("profil");
        racine.setAttribute("xmlns", "http://myGame/tux");
        racine.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        racine.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/profil.xsd");
        _doc.appendChild(racine);
        // créer les éléments nom, dateNaissance et avatar
        Element nomEtl = _doc.createElement("nom");
        nomEtl.setTextContent(nom);
        racine.appendChild(nomEtl);
        Element dateNaissanceEtl = _doc.createElement("anniversaire");
        dateNaissanceEtl.setTextContent(dateNaissance);
        racine.appendChild(dateNaissanceEtl);
        Element avatarEtl = _doc.createElement("avatar");
        avatarEtl.setTextContent(avatar);
        racine.appendChild(avatarEtl);
        // créer l'élément parties
        Element partiesEtl = _doc.createElement("parties");
        racine.appendChild(partiesEtl);
        

    }
    // constructeur par défaut
    public Profil() {
        this.nom = "Tux";
        this.dateNaissance = "01/01/2023";
        this.avatar = "textures/doty_happy.png";
        this.parties = new ArrayList<Partie>();
        
       //Instanciation de Factory de Parser DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //Parser le document XML
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Renvoyer le Document DOM
            _doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }


    // Cree un DOM à partir d'un fichier XML
    Profil(String nomFichier) {
        _doc = fromXML(nomFichier);
        // récupérer les informations du profil
        this.nom = _doc.getElementsByTagName("nom").item(0).getTextContent();
        this.dateNaissance = _doc.getElementsByTagName("dateNaissance").item(0).getTextContent();
        this.avatar = _doc.getElementsByTagName("avatar").item(0).getTextContent();
        this.parties = new ArrayList<Partie>();
        NodeList partiesEtl = _doc.getElementsByTagName("partie");
        // parcourir la liste des parties et les ajouter à la liste des parties
        for (int i = 0; i < partiesEtl.getLength(); i++) {
            this.parties.add(new Partie((Element) partiesEtl.item(i)));
        }


    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.length() - 2, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.length() - 5, xmlDate.length() - 3);
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.length() - 6);
        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.length() - 4, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.length() - 7, profileDate.length() - 5);
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.length() - 8);

        return date;
}

    //Rajouter à la liste des parties une Partie
    public void ajoutePartie(Partie p) {
        this.parties.add(p);
        // ajouter la partie au DOM
        ajoutePartieDOM(p);
    }
    
    //La méthode sauvegarder(filename:String):void permet de sauvegarder le document DOM dans un fichier XML.
    public void sauvegarder(String filename) {
        toXML(filename);
    }
    
    // ajouter une partie au DOM
    public void ajoutePartieDOM(Partie p) {
        // récupérer l'élément parties
        Element partiesEtl = (Element) _doc.getElementsByTagName("parties").item(0);
        // créer l'élément partie
        Element partieEtl = _doc.createElement("partie");
        // ajouter l'attribut date
        partieEtl.setAttribute("date",p.getDate());
        // ajouter l'attribut trouvé
        partieEtl.setAttribute("trouvé", String.valueOf(p.getTrouvé())+"%");
        // ajouter l'élément temps
        Element tempsEtl = _doc.createElement("temps");
        tempsEtl.setTextContent(String.valueOf(p.getTemps()));
        partieEtl.appendChild(tempsEtl);
        // ajouter l'élément mot
        Element motEtl = _doc.createElement("mot");
        motEtl.setAttribute("niveau", String.valueOf(p.getNiveau()));
        motEtl.setTextContent(p.getMot());
        partieEtl.appendChild(motEtl);
        // ajouter l'élément partie à l'élément parties
        partiesEtl.appendChild(partieEtl);
    }
    
    // getNom() permet de récupérer le nom du profil
    public String getNom() {
        return nom;
    }
    
    // getAvatar() permet de récupérer l'avatar du profil
    public String getAvatar() {
        return avatar;
    }
    
    // charger(nomJoueur:String):void permet de charger un profil à partir un fichier XML
    public boolean charge(String nomJoueur) {
        // charger le DOM à partir du fichier XML
        _doc = fromXML("src/xml/profils/" + nomJoueur + ".xml"); // le fichier XML doit être dans le dossier xml
        // vérifier que le profil existe
        if (_doc == null) { //le fichier est existant mais vide ou inexistant
            System.out.println("Erreur : le profil n'existe pas");
            return false;
        }
        // récupérer les informations du profil
        this.nom = _doc.getElementsByTagName("nom").item(0).getTextContent();
        this.dateNaissance = xmlDateToProfileDate(_doc.getElementsByTagName("anniversaire").item(0).getTextContent());
        this.avatar = _doc.getElementsByTagName("avatar").item(0).getTextContent();
        this.parties = new ArrayList<>();
        NodeList partiesEtl = _doc.getElementsByTagName("partie");
        // parcourir la liste des parties et les ajouter à la liste des parties
        for (int i = 0; i < partiesEtl.getLength(); i++) {
            this.parties.add(new Partie((Element) partiesEtl.item(i)));
        }
        return true;
    }
    
    // récupérer une partie par hasard dans la liste des parties
    public Partie chargePartieExistante(){
        return parties.get((int) (Math.random() * parties.size()));
    }
    
   // vérifier si la liste des parties est vide
    public Boolean partiesVide(){
        return parties.isEmpty();
    }
    //-------------------------------------------------------------------------//
    
    //Transformer le DOM en HTML en utilisant XSLT (XSL Transformation) 
    public void toHTML(String nomFichier) {
        try {
            String html = XMLUtil.DocumentTransform.fromXSLTransformation("src/xslt/profil.xsl", fromXML("src/xml/profils/" + nomFichier + ".xml"));
            // utiliser la classe FileUtil pour écrire le résultat dans un fichier
            FileUtil.stringToFile(html,"src/html/" + nom + ".html");
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    }
