/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author trant
 */
public class mainRoom {
    private int depth;
    private int width;
    private int height;
    private String pion;
    private String textureBottom;
    private String textureTop;
    private String textureNorth;
    private String textureSouth;
    private String textureEast;
    private String textureWest;
    
    public mainRoom (){
        
    
        this.depth=100;
        this.width=100;
        this.height=60;
        this.textureBottom="textures/floor/woodFloor1.png";
        this.textureEast="textures/skybox/city/east.png";
        this.textureNorth="textures/skybox/city/south.png";
        this.textureWest="textures/skybox/city/west.png";
        
    }
    
     //Réaliser un parseur DOM permettant la mise en place de la Room.
    public mainRoom(String nomFichier){
        //Instanciation de Factory de Parser DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //Parser le document XML
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Renvoyer le Document DOM
            Document doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = fromXML(nomFichier);
        Element racine = doc.getDocumentElement();
        //Récupération des dimensions
        Element dimensions =(Element) racine.getElementsByTagName("dimensions").item(0);
        //Récupération de la hauteur
        this.height=Integer.parseInt(dimensions.getElementsByTagName("height").item(0).getTextContent());
        //Récupération de la largeur
        this.width=Integer.parseInt(dimensions.getElementsByTagName("width").item(0).getTextContent());
        //Récupération de la profondeur
        this.depth=Integer.parseInt(dimensions.getElementsByTagName("depth").item(0).getTextContent());
        //Récupération du mapping
        Element mapping =(Element) racine.getElementsByTagName("mapping").item(0);
        ////Récupération de la textures du bas
        this.textureBottom=mapping.getElementsByTagName("textureBottom").item(0).getTextContent();
        //Récupération de la texture de l'est
        this.textureEast=mapping.getElementsByTagName("textureEast").item(0).getTextContent();
        //Récupération de la texture de l'ouest
        this.textureWest=mapping.getElementsByTagName("textureWest").item(0).getTextContent();
        //Récupération de la texture du nord
        this.textureNorth=mapping.getElementsByTagName("textureNorth").item(0).getTextContent();
        //Récupération du pion
        this.pion=racine.getElementsByTagName("pion").item(0).getTextContent();

    }
    // Cree un DOM à partir d'un fichier XML
    private Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public int getDepth() {
        return depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }
    
    
    
}
