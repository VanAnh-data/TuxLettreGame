<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:dico="http://myGame/tux"> 
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                 <link rel="stylesheet" type="text/css" href="../css/dico.css"/>
                <title>Dictionnaire</title>
            </head>
            <body>
                <h1>Dictionnaire</h1>
                <!--on récupère le nombre de niveau pour pouvoir faire une boucle-->
                <xsl:variable name="niveau" select="count(//dico:mot/@niveau)"/>
                <!--pour chaque niveau on affiche un titre et on affiche les mots qui ont ce niveau-->
                <xsl:for-each select="//dico:mot"> 
                    <xsl:sort select="@niveau" order="ascending"/> <!--on les trie par niveau-->
                    <xsl:if test="not(@niveau = preceding-sibling::dico:mot/@niveau)"> <!--on vérifie que le niveau n'a pas déjà été affiché-->
                        <h4>Niveau <xsl:value-of select="@niveau"/></h4> <!--on affiche le niveau-->
                        <ul>
                            <xsl:for-each select="//dico:mot[@niveau = current()/@niveau]"> <!--on récupère tous les mots qui ont ce niveau-->
                                <xsl:sort select="." data-type="text" order="ascending"/> <!--on les trie par ordre alphabétique-->
                                <li>
                                    <xsl:value-of select="."/> <!--on affiche le mot-->
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
    

                


                
                

                
</xsl:stylesheet>