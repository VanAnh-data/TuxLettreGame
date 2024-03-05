<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:act='http://www.ujf-grenoble.fr/l3miage/actes'
                xmlns:med="http://www.ujf-grenoble.fr/l3miage/medical"> 
    
     <!-- *****Ce fichier de transformation XSLT permet d'extraire toutes les informations relatives à un seul patient pour les mettres dans un doc .xml ****-->
    
    <xsl:output method="xml"/>
    <xsl:param name="destinedName" >Pien</xsl:param> <!-- ici le patient choisi a pour nom "Pien"-->
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    <xsl:template match="/">
        <!-- pour le patient telque son nom est $destinedName, appliquer la template med:patient -->
        <xsl:apply-templates select="med:cabinet/med:patients/med:patient[./med:nom=$destinedName]"/>
    </xsl:template>
    <!-- Cette pour le patient en question, construit le doc .xml correspondant -->
    <xsl:template match="med:patient[./med:nom=$destinedName]">
        <patient>
            <nom><xsl:value-of select="med:nom/text()"/></nom>
            <prénom><xsl:value-of select="med:prénom/text()"/> </prénom>
            <sexe><xsl:value-of select="med:sexe/text()"/></sexe>
            <naissance><xsl:value-of select="med:naissance/text()"/></naissance>
            <numéroSS><xsl:value-of select="med:numéro/text()"/></numéroSS>
            <adresse>
                <rue><xsl:value-of select="med:adresse/med:rue/text()"/></rue>
                <codePostal><xsl:value-of select="med:adresse/med:codePostal/text()"/></codePostal>
                <ville><xsl:value-of select="med:adresse/med:ville/text()"/></ville>
            </adresse>
           <!-- Cette template pour chaque visite du patient, applique la template  med:visite -->
           <!-- <xsl:apply-templates select= "med:visite"/> -->
           <!-- affiche les visites du patient par ordre de date croissant -->     
            <xsl:apply-templates select= "med:visite">
                        <xsl:sort select="@date" order= "ascendent"/> 
            </xsl:apply-templates>
        </patient>
    </xsl:template>

    <!-- créer la variable ici pour accéder à l'élément au dessus de visite, car dans visite ne comprend que ce qui est, dessous-->
    <xsl:variable name="tabInf" select="med:cabinet/med:infirmiers/med:infirmier"/>
    
    <!-- La template med:visite pour un élément viste, écrit les balises et sous élément correspondants-->
    <xsl:template match="med:visite">
        <xsl:element name="visite">
                    <xsl:attribute name="date">
                         <xsl:value-of select="@date"/>  <!-- les visites doivent ètre triée par date !!!! -->
                    </xsl:attribute>
                    <xsl:element name="intervenant">
                        <xsl:variable name="interv_id" select="@intervenant"/>
                        <xsl:element name="nom">
                            <xsl:value-of select="$tabInf[@id=$interv_id]/med:nom/text()"/>
                        </xsl:element>
                        <xsl:element name="prénom">
                            <xsl:value-of select="$tabInf[@id=$interv_id]/med:prénom/text()"/>
                        </xsl:element>
                    </xsl:element>
                    <!-- Cette template pour chaque acte d'une visite, applique la template  med:acte--> 
                    <xsl:apply-templates select= "med:acte"/> 
        </xsl:element>
    </xsl:template>

    <!-- La template med:acte pour un élément acte, écrit les balises sous élément correspondants-->
    <xsl:template match="med:acte">
            <xsl:variable name="Id_act" select="@id"/>
            <xsl:element name="acte">
                <xsl:value-of select="$actes/act:actes/act:acte[@id=$Id_act]/text()"/> 
            </xsl:element>
    </xsl:template>
</xsl:stylesheet>
