<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:med="http://www.ujf-grenoble.fr/l3miage/medical"
                xmlns:act="http://www.ujf-grenoble.fr/l3miage/actes">
  
    <xsl:output method="html"/>
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    <xsl:param name="infirmierId" select="'001'"/>
    
    <!-- *****Ce fichier de transformation XSLT permet d'afficher la listes des patients qu'un Infirmier devra Osculter ****-->
  
    <xsl:template match="/">
        <html>
            <head>
                <link href="../css/cabinet.css" rel="stylesheet" type="text/css"/>
                <script type="text/javascript">
                    function openFacture(prenom, nom, actes) {
                    var width  = 500;
                    var height = 300;
                    if(window.innerWidth) {
                    var left = (window.innerWidth-width)/2;
                    var top = (window.innerHeight-height)/2;
                    }
                    else {
                    var left = (document.body.clientWidth-width)/2;
                    var top = (document.body.clientHeight-height)/2;
                    }
                   
                    var factureWindow = window.open('','facture','menubar=yes, scrollbars=yes, top='+top+', left='+left+', width='+width+', height='+height+'');
                    factureText = "Facture pour : " + prenom + " " + nom;
                    factureWindow.document.write(factureText);
                    }
                </script>  
            <title>Page de l'infirmière</title>
            </head>
            <body>
                <div id="BonjourInf">
                    <h1>Bonjour <xsl:value-of select="//med:infirmier[@id=$infirmierId]/med:prénom"/>,</h1>
                    <p>Aujourd'hui, vous avez <xsl:value-of select="count(//med:visite[@intervenant=$infirmierId])"/> patients</p>
                </div>
                <div id="patientInfo">
                    <!--on applique le template select sur le patient qui a pour id $infirmierId-->
                    <xsl:apply-templates select="//med:patient[med:visite/@intervenant=$infirmierId]"/>
                </div>
                
            </body>
        </html>
    </xsl:template>
    <!-- la template med:patient affiche pour un patient ses donneés et la liste des soins, puis un button pour consulter la facture -->
    <xsl:template match="med:patient">
        <div class="infoPatient">
            Patient :<br/>
            Nom : <xsl:value-of select="med:prénom/text()"/>
            <br/>
            Prénom : <xsl:value-of select="med:nom/text()"/>
            <br/>
            Adresse:
            <xsl:value-of select="med:adresse/med:rue"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="med:adresse/med:ville"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="med:adresse/med:codePostal"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="med:adresse/med:pays"/> 
            <br/>
            <xsl:text> </xsl:text>
        </div>    
        <div class="soin">
            Liste des soins:<br/>
            <xsl:for-each select="med:visite/med:acte">
                <xsl:variable name="idActe" select="@id"/>
                Acte : <xsl:value-of select="$actes//act:acte[@id = $idActe]"/>
                <br/>
            </xsl:for-each>
        </div>
        <input type="button" value="Facture">
            <xsl:variable name="idActe" select="med:visite/med:acte/@id" />   
            <xsl:attribute name="onclick">
                openFacture('<xsl:value-of select="med:prénom/text()"/>',
                '<xsl:value-of select="med:nom/text()"/>',
                '<xsl:value-of select="med:acte"/>')
            </xsl:attribute>
        </input>
</xsl:template>
</xsl:stylesheet>

