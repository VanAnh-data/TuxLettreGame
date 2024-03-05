<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : paient_Pien.xsl
    Created on : 14 novembre 2023, 17:37
    Author     : trant
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                   xmlns:med="http://www.ujf-grenoble.fr/l3miage/medical">
    <!-- Cette page prend en entrée un fichier xml contenant les informations et les visites du petient. Produit une page html en sortie avec 
    les informations personnel dans un tableau et les informations de la visite dans autre tableau
    --> 
    <xsl:output method="html"/>
   
    <xsl:template match="/">
        <html>
            <head>
                <link href="../css/patient.css" rel="stylesheet" type="text/css"/>
                <title>fiche_patient.xsl</title>
            </head>
            <body>
                <h1>Bienvenue sur le site de votre cabinet médical</h1>
                <div class="BonjourPatient">
                    <xsl:text>Bonjour </xsl:text>
                    <xsl:value-of select="patient/prénom"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="patient/nom"/>
                </div>
                <!--on applique le template select sur le patient-->
                <xsl:apply-templates select="patient"/> 
            </body>
        </html>
    </xsl:template>
    <!--Template pour les renseignements personnels du patient-->
    <xsl:template match="patient">
        <div class="Renseignements">
            <h5>Renseignements</h5>
            <table>
                <tr>
                    <td>Nom:</td>
                    <td><xsl:value-of select="nom"/></td>
                </tr>
                <tr>
                    <td>Prénom:</td>
                    <td><xsl:value-of select="prénom"/></td>
                </tr>
                <tr>
                    <td>Sexe:</td>
                    <td><xsl:value-of select="sexe"/></td>
                </tr>
                <tr>
                    <td>Naissance:</td>
                    <td><xsl:value-of select="naissance"/></td>
                </tr>
                <tr>
                    <td>numéro de sécurité sociale:</td>
                    <td><xsl:value-of select="numéroSS"/></td>
                </tr>
                <tr>
                    <td>Adresse:</td>
                    <td>
                        <xsl:value-of select="adresse/rue"/>
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="adresse/codePostal"/>
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="adresse/ville"/>
                    </td>
                </tr>
            </table>
        </div>
        <!--On applique le template select sur les visites du patient-->
        <div class="Visites">
            <h2>Visites</h2>
            <table>
                <tr>
                    <th>Date</th>
                    <th>Intervenant</th>
                    <th>Actes</th>
                </tr>
                <!--On met les informations de visite dans le tableau-->
                <xsl:for-each select="visite">
                    <tr>
                        <td><xsl:value-of select="@date"/></td>
                        <td>
                            <xsl:value-of select="intervenant/nom"/>
                            <xsl:text> </xsl:text>
                            <xsl:value-of select="intervenant/prénom"/>
                        </td>
                        <td>
                            <xsl:for-each select="acte">
                                <xsl:value-of select="."/>
                                <br/>
                            </xsl:for-each>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
        </div>
    </xsl:template>

</xsl:stylesheet>


