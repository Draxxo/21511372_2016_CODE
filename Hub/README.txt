DISCUTION FB ENTRE ALEXIS & LUCAS

ALEXIS :
un server centralis� sur lequel tous les fichier resources tel que notre liste de joueur est stock�...
mais pas que...
sur lequel on stoke un fichier qui permet de savoir quels ports ont utiliseent actuellement
du coup...
si un client ce co au server et ce fait bouler il peut recup le dernier port cr�er et lincrement� pour en cr�er un nouveau...
eu coup un client r�cup�re d'abord la liste des ports utilis�, essaye de ce co � tous les server
si il n'y en a aucun de libre il en cr�er un nouveau et l'ajoute � la liste
ainsi si un server ce retrouve � 0 joueur on le kill
imagine on pourra lancer autant de server qu'on veut....
autant de client qu'on veut
du coup le server central aura tjrs le m�me port fixe lui
et si �a liste est vide on le kill aussi
comme �a on bouffe pas les ports pour rien
Voil� �a veut dire deux server, server pour jouer, server pour save les diff�rentes donn�es
�a y est tu me prends pour un fou ? ^^"

LUCAS
alors attend : il y aurait donc un serveur qui serait la uniquement pour pouvoir jouer (comme actuellement) 
+ un autre (le serveur principal) qui se chargera de synchroniser les donn�es (donn�es qui sont principalement 
le fichier contenant les listes de joueur)
et en plus le serveur principal donne au client le port et l'ip d'un serveur de jeu