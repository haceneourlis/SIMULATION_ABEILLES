
Pour compiler ce programme :

1. Placez-vous dans le répertoire : src
2. 
   - Si les fichiers .class n'ont pas encore été supprimés,
      Tapez : java main.BeesGame
   - Sinon :
      Tapez : cd main ; rm *.class ; cd ../BEES_PACKAGE ; rm *.class ; cd ../Sources ; rm *.class ; cd ../NATURE_DESSIN_PACKAGE ; rm *.class ; cd ..
      Ensuite : javac -d . ./main/BeesGame.java
      Enfin : java main.BeesGame
