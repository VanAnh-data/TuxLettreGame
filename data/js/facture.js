/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
var AMIVAL = 3.15;
var AISVAL = 2.65;
var DIVAL = 10.0;

var totalFacture = 0.0;


function afficherFacture(prenom, nom, actes)
{

    
    totalFacture = 0.0;
    var text = "<html>\n";
    text +=
            "    <head>\n\
            <title>Facture</title>\n\
            <link rel='stylesheet' type='text/css' href='css/mystyle.css'/>\n\
         </head>\n\
         <body>\n";
    
    text += "Facture pour " + prenom + " " + nom + "<br/>"; 


    //Trouver l'adresse du patient
    var xmlDoc = loadXMLDoc("../xml/cabinet.xml");
    var patients = xmlDoc.getElementsByTagName("patient");
    var i = 0;
    var found = false;

    while ((i < patients.length) && (!found)) {
        var patient = patients[i];
        var localNom = patient.getElementsByTagName("nom")[0].childNodes[0].nodeValue;
        var localPrenom = patient.getElementsByTagName("prénom")[0].childNodes[0].nodeValue;
        if ((nom === localNom) && (prenom === localPrenom)) {
            found = true;
        }
        else {
            i++;
        }
    }

    if (found) {
        text +="Adresse: ";
        // On rÃ©cupÃ¨re l'adresse du patient
        var adresse = patient.getElementsByTagName("adresse")[0];
        //adresse = ... Ã  complÃ©ter par une expression DOM
        text += adresseToText(adresse);
        text += "<br/>";

        var nSS = "0";
        //nss = rÃ©cupÃ©rer le numÃ©ro de sÃ©curitÃ© sociale grÃ¢ce Ã  une expression DOM
        nSS = patient.getElementsByTagName("numéro")[0];

        text += "Numéro de sécurité sociale: " + nSS + "\n";
    }
    text += "<br/>";



    // Tableau rÃ©capitulatif des Actes et de leur tarif
    text += "<table border='1'  bgcolor='#CCCCCC'>";
    text += "<tr>";
    text += "<td> Type </td> <td> Clé </td> <td> IntitulÃ© </td> <td> Coef </td> <td> Tarif </td>";
    text += "</tr>";

    var acteIds = actes.split(" ");
    for (var j = 0; j < acteIds.length; j++) {
        text += "<tr>";
        var acteId = acteIds[j];
        text += acteTable(acteId);
        text += "</tr>";
    }
    
     text += "<tr><td colspan='4'>Total</td><td>" + totalFacture + "</td></tr>\n";
     
     text +="</table>";
     
     
    text +=
            "    </body>\n\
    </html>\n";

    return text;
}

// Mise en forme d'un noeud adresse pour affichage en html
function adresseToText(adresse)
{
    
    var numero = adresse.getElementsByTagName("numéro")[0].childNodes[0].nodeValue;
    var rue = adresse.getElementsByTagName("rue")[0].childNodes[0].nodeValue;
    var ville = adresse.getElementsByTagName("ville")[0];
    var codePostal = adresse.getElementsByTagName("codePostal")[0].childNodes[0].nodeValue;
    
    
    var str="";  //"A completer..."
    str += numero+" rue "+rue+" "+ville+" "+codePostal;
    // Mise en forme de l'adresse du patient
    // A complÃ©ter

    return str;
}


function acteTable(acteId)
{
    var str = "";
    var xmlDoc = loadXMLDoc("../xml/actes.xml");
    
     
    var actesBloc; // pour récupérer le noeud "actes" contenant la liste des actes (en un bloc)
    actesBloc = xmlDoc.getElementsByTagName("actes")[0];
    // actes = rÃ©cupÃ©rer les actes de xmlDoc sous forme d'une liste ne contenant que les noeuds "acte"
    var actes = actesBloc.getElementsByTagName("acte");
    
    
    // ClÃ© de l'acte (3 lettres)
    var cle;
    
    // Coef de l'acte (nombre)
    var coef ;
    // Type id pour pouvoir rÃ©cupÃ©rer la chaÃ®ne de caractÃ¨res du type 
    //  dans les sous-Ã©lÃ©ments de types
    var typeId;
    
    var typesBloc = xmlDoc.getElementsByTagName("types"); // pour récupérer le noeud "types" contenant la liste des types (en un bloc)
    var j=0;
    var types = typesBloc.getElementsByTagName("type"); //Liste des différents noeud type exixtants
    var type_quelconq =types[j];
    
    // cherche le noeud "type", dont l'attribut id est égale à typeId, jusqu'à le trouver 
    while ((j < types.length) && ( type_quelconq.getAttributes('id')!== typeId )) {
        j++; // avance dans la Liste sans rien faire
    }
    
    // ChaÃ®ne de caractÃ¨re du type
    var type ; // puis la chaine de caractères de ce noeud type dans type
    // ...
    // IntitulÃ© de l'acte
    var intitule ;
    
    // Tarif = (lettre-clÃ©)xcoefficient (utiliser les constantes 
    // var AMIVAL = 3.15; var AISVAL = 2.65; et var DIVAL = 10.0;)
    // (cf  http://www.infirmiers.com/votre-carriere/ide-liberale/la-cotation-des-actes-ou-comment-utiliser-la-nomenclature.html) 
    
    
    if (cle === "AMI") {
            tarif = AMIVAL * coef;
    }
    else if (cle === "AIS") {
        tarif = AISVAL * coef;
    }
    else if (cle === "DIV") {
        tarif = DIVAL * coef;
    }
    else {
        tarif = 0.0;
    }
    
    
    var tarif = 0.0;

    // Trouver l'acte qui correspond
    var i = 0;
    var found = false;
    
    // A dÃ©-commenter dÃ¨s que actes aura le bon type...
    var acte = actes[i];
    while ((i < actes.length) && found!== true) {
        if( acte.getAttributes('id')!== acteId ){
            found = true;
        }
        i++;
        acte = actes[i];
    }

    if (found) {
        // A complÃ©ter
        cle = acte.getAttributes('clé');
        coef = acte.getAttributes('coef') ;
        typeId =acte.getAttributes('type') ; // récupère le type de l'acte et le met dans typeId 
        type = type_quelconq.childNodes[0].nodeValue; //quand le noeud "type", dont l'attribut id est égale à typeId, jusqu'à le trouver
        intitule = acte.childNodes[0].nodeValue;

    }

    // A modifier
    str += "<td>" + "???" + "</td>";
    str += "<td>" + "???" + "</td>";
    str += "<td>" + "???" + "</td>";
    str += "<td>" + "???" + "</td>";
    str += "<td>" + "???" + "</td>";
    totalFacture += tarif;

    return str;
}



// Fonction qui charge un document XML
function loadXMLDoc(docName)
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.open("GET", docName, true);
    xmlhttp.send();
    var xmlDoc = xmlhttp.responseXML;

    return xmlDoc;
}


