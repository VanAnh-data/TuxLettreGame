
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author trant
 */


public class Partie {
        private String date;
        private int niveau;
        private String mot;
        private int temps;
        private boolean arret;
        private int trouvé; // pourcentage de lettres trouvées

        public Partie(String date,String mot, int niveau) {
            this.date = date;
            this.niveau = niveau;
            this.mot = mot;
            this.arret=false;
        }
        // setTrouve() permet de calculer le pourcentage de lettres trouvées en fonction du nombre de lettres restantes à trouver
        public void setTrouve(int nbLettresRestantes) {
            this.trouvé = 100-nbLettresRestantes*100 / mot.length();
        }
        public void setTemps(int temps) {
            this.temps = temps;
        } 
        public int getNiveau() {
            return niveau;
        }
        @Override
        public String toString() {
            return "Date : " + date + "\nNiveau : " + niveau + "\nMot : " + mot + "\nTemps : " + temps + "\nPourcentage trouvé : " + trouvé + "%";
        }
        public String getMot() {
            return mot;
        }

        public void setArret(boolean arret) {
            this.arret = arret;
        }

        public boolean isArret() {
            return arret;
        }

        // Partie(Element partieEtl): constructeur qui permet de créer une partie à partir d'un élément XML
        public Partie(Element partieEtl) {

            this.date = partieEtl.getAttribute("date");
            this.niveau = Integer.parseInt(partieEtl.getElementsByTagName("mot").item(0).getAttributes().getNamedItem("niveau").getNodeValue());
            this.mot = partieEtl.getElementsByTagName("mot").item(0).getTextContent();
            this.temps = Integer.parseInt(partieEtl.getElementsByTagName("temps").item(0).getTextContent());
            arret = false;
        }
        public String getDate() {
            return date;
        }

        public int getTemps() {
            return temps;
        }


        public int getTrouvé() {
            return trouvé;
        }

         //La méthode getPartie(doc:Document):Element Cette méthode crée le bloc XML représentant une partie à partir du paramètre doc
        //(pour créer les éléments du XML) et renvoie ce bloc en tant que Element.
        public Element getPartie(Document doc) {
            Element partieEtl = doc.createElement("partie");
            partieEtl.setAttribute("date", date);
            Element motEtl = doc.createElement("mot");
            motEtl.setAttribute("niveau", String.valueOf(niveau));
            motEtl.setTextContent(mot);
            partieEtl.appendChild(motEtl);
            Element tempsEtl = doc.createElement("temps");
            tempsEtl.setTextContent(String.valueOf(temps));
            partieEtl.appendChild(tempsEtl);
            return partieEtl;
        }

}
