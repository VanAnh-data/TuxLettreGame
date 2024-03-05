<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tux="http://myGame/tux">
  <!-- Feuille de style XSLT pour afficher le profil du joueur -->
  <xsl:output method="html"/>
  <!-- Template principal -->
  <xsl:template match="/">
    <html>
      <head>
          <!-- Inclusion de la feuille de style CSS -->
         <link rel="stylesheet" href="../css/profil.css"/>
        <title>Profil du joueur</title>
      </head>
      <body>
        <h1>Profil du joueur</h1>
        <p>Nom: <xsl:value-of select="//tux:nom"/></p>
        <img src="../../textures/doty_happy.png" alt="Avatar" width="100"/>
        <p>Avatar: <xsl:value-of select="//tux:avatar"/></p>
        <p>Anniversaire: <xsl:value-of select="//tux:anniversaire"/></p>
        <h2>Parties</h2>
        <table border="1">
          <tr>
            <th>Date</th>
            <th>Temps</th>
            <th>Niveau</th>
            <th>Mot</th>
            
          </tr>
          <!-- Boucle sur les parties -->
          <xsl:for-each select="//tux:partie">
            <tr>
              <td><xsl:value-of select="@date"/></td>
              <td><xsl:value-of select="tux:temps"/></td>
              <td><xsl:value-of select="tux:mot/@niveau"/></td>
              <td><xsl:value-of select="tux:mot"/></td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
  
</xsl:stylesheet>