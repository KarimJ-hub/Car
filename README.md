# Car

# FTP Server in Java

## Description
Ce projet implémente un serveur FTP basique en Java qui prend en charge certaines commandes FTP fondamentales. Il permet aux clients FTP de se connecter, s'authentifier et interagir avec le système de fichiers du serveur. Le serveur répond aux commandes suivantes :

- **USER** : Spécifie le nom d'utilisateur.
- **PASS** : Permet l'authentification en fournissant un mot de passe.
- **QUIT** : Termine la session client.
- **EPSV** : Active le mode passif étendu et ouvre une connexion de données.
- **RETR [nom de fichier]** : Télécharge un fichier du serveur.
- **CWD [répertoire]** : Change le répertoire de travail actuel.
- **LIST** : Liste le contenu du répertoire de travail courant.

## Fonctionnalités
1. **Authentification** :
   - Le serveur demande un nom d'utilisateur et un mot de passe.
   - Par défaut, les identifiants valides sont :
     - Nom d'utilisateur : `miage`
     - Mot de passe : `car`

2. **Mode Passif Étendu (EPSV)** :
   - Permet de gérer une connexion de données séparée pour le transfert de fichiers ou l'envoi de listes de répertoires.

3. **Liste des fichiers (LIST)** :
   - Le serveur fournit une liste des fichiers et répertoires dans le répertoire courant.

4. **Changement de répertoire (CWD)** :
   - Permet aux clients de naviguer entre différents répertoires du serveur.

5. **Gestion des erreurs** :
   - Réponses appropriées aux commandes non reconnues ou à l'accès à des fichiers/répertoires inexistants.

## Utilisation
1. **Exécution du serveur** :
   - Compilez et exécutez le fichier `FTPServer.java` avec une JVM compatible.

2. **Connexion au serveur** :
   - Utilisez un client FTP pour vous connecter à `localhost` sur le port `2121`.

3. **Commandes prises en charge** :
   - `USER [nom utilisateur]` : Fournir un nom d'utilisateur.
   - `PASS [mot de passe]` : Fournir un mot de passe.
   - `EPSV` : Activer le mode passif étendu.
   - `RETR [nom de fichier]` : Télécharger un fichier.
   - `CWD [répertoire]` : Changer de répertoire.
   - `LIST` : Lister les fichiers et répertoires du répertoire courant.
   - `QUIT` : Terminer la connexion.
