# UE-INF1089M Conception D'Applications Web - CAW 2019-2020
## Préambule

##### Etudiants
* DE ALMEIDA Gabriel 11408459
* BLAIN Thomas 11601248

## Section TP2 & TP3
### TP2

##### Déploiement
* Le TP2 est disponible à l'adresse [http://192.168.75.16:8080/tp2/](http://192.168.75.16:8080/tp2/).

##### Fonctionnalités implémentées
* Interface de gestion des billets
    * Rajoutez à la classe Billet la possibilité de gérer des commentaires : **Fait**
    * Mettez en place l'affichage des commentaires en-dessous du contenu du billet : **Fait**
    * Mettez en place une variable globale de type *GestionBillets* : **Fait**
    * Mettez en place un mode d'appel de la page billet.jsp pour que l'utilisateur puisse choisir quel billet afficher : **Fait**
    * Ajoutez à la page un header HTTP Refresh de 5 secondes : **Fait**
    
* Déconnexion : **Fait**

* Ajout d'un menu : **Fait**

* Ajout de groupes : **Non fait au TP2 mais au TP3**
    

### TP3
##### Déploiement
* Le TP3 est disponible à l'adresse [https://192.168.75.16/api/v1/](https://192.168.75.16/api/v1/).

##### Fonctionnalités implémentées
* Rajout des fonctionnalités de groupe du TP2

* Pattern contexte : **Fait**

* Pattern MVC : **Fait en push-based**
    * Fichiers JSP déplacés dans le dossier WEB-INF inaccessible aux clients : **Fait**
    * Redirections internes : **Fait**
    
* Pattern chaîne de responsabilité
    * Authentification
        * Classe qui implémente javax.servlet.Filter : **Fait** 
        * Reprenez le contenu de la méthode de service de la servlet Init dans celle de votre filtre : **Fait** 
        * Décalez ce filtre dans le fichier de configuration de l'application : **Fait** 
       
    * Autorisation
        * Créez un second filtre qui vérifie que l'utilisateur est membre du groupe : **Fait** 

* Gestion du cache
    * Choix de l'utilisation des **en-têtes HTTP**.

## Section TP4
##### Déploiement
* Le TP4 est disponible à l'adresse [https://192.168.75.16/api/v2/](https://192.168.75.16/api/v2/).

##### Fonctionnalités implémentées
* Ressources
    * Modification des URLs : **Fait**
    * Représentations des ressources : **Fait**
    * Sémantique de HTTP : **Fait**

* Transactions sans état
    * Contexte des requêtes : **Fait**
    * Authentification Stateless (JAVA-JWT) : **Fait**
    
* Négociation de contenus
    * Regrouper types de vues dans WEB-INF : **Fait**
    * Mise en place des DTO : **Fait**
    * Opérations dans les contrôleurs : **Fait**
    * Filtre qui intercepte les requêtes **après** leur traitement par le contrôleur : **Fait**
    * Modifier les chemins dans le fichier *web.xml* rendre filtres opérationnels : **Fait**
    * Accéder au header Accept de la requête et rediriger vers la vue correspondante : **Fait**
    * Utilisation du NamedDispatcher : **Fait**
    * Déclaraton des JSP comme servlets : **Fait**

* Hypermédia
    * Ajout de liens : **Non fait**
    * Documentation de l'API : **Fait**

##### Liste des requêtes fonctionnelles
* Groupes
    * Récupérer tous les groupes
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Créer un groupe
        ```
        curl -k -X POST "https://192.168.75.16/api/v2/groupes?nom=nomGroupe&description=description" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Récupérer un groupe
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes/nomGroupe" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Modifier un groupe
        ```
        curl -k -X PUT "https://192.168.75.16/api/v2/groupes/nomGroupe?nom=nomGroupe1" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Supprimer un groupe
        ```
        curl -k -X DELETE "https://192.168.75.16/api/v2/groupes/nomGroupe" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
      
* Billets
    * Récupérer tous les billets d'un groupe
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes/billets" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Créer un billet
        ```
        curl -k -X POST "https://192.168.75.16/api/v2/groupes/nomGroupes/billets?titre=nomBillet&contenu=contenu" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Récupérer un billet
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes/nomGroupes/billets/0" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Modifier un billet
        ```
        curl -k -X PUT "https://192.168.75.16/api/v2/groupes/nomGroupes/billets/0?titre=nomBillet2&contenu=contenu2" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Supprimer un billet
        ```
        curl -k -X DELETE "https://192.168.75.16/api/v2/groupes/nomGroupe/billets/0" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
      
* Commentaires
    * Récupérer tous les commentaires d'un billet
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes/billets/0/commentaires/" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Créer un commentaire
        ```
        curl -k -X POST "https://192.168.75.16/api/v2/groupes/nomGroupes/0/commentaires/0?commentaire=com2" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
    * Récupérer un commentaire
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/groupes/nomGroupes/0/commentaires/0" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
      
* Utilisateurs
    * Se connecter, on doit juste rajouter l'argument pseudo dans l'url, renvoie une page d'erreur mais aussi le token
        ```
        curl -k -X GET "https://192.168.75.16/api/v2/*?pseudo=monPseudo" -H  "accept: application/json" -H  "Content-Type: application/json"
        ```
      
## Section TP5 & TP7
##### Déploiement
* Le TP5/TP7 est disponible à l'adresse [https://192.168.75.16/](https://192.168.75.16/) depuis un hébergement sur nginx.

##### Fonctionnalités implémentées
* Première application AJAX (*)
    * Récupération des données XML de fichier *time.jsp* et traitement direct en JavaScript dans le fichier *ajax.js*

* Reprise de votre application de blog
    * Mise en place d'une SPA complète avec différents items de menu et mécanisme de routage : **Fait**
    * Templating 
        * Créer des "mock objects" : **Fait**
        * Ajout de Mustache et créations de templates : **Fait**
        * Création d'un script déclencheur du moteur de templating et ajoute le résultat à la page : **Fait**
       
    * AJAX
        * Connexion et déconnexion : **Fait**
        * Récupération du token JWT : **Fait**
        * Récupération de la liste des groupes : **Fait**
        * Création, modification et récupération d'un groupe : **Fait**
        * Ajout et supression d'un utilisateur : **Fait**
        * Création et récupération d'un billet : **Fait**
    
    * Fetch API : **Fait**
        * *Toutes les fonctions de récupération de données sont **asynchrones***.
        
    * Finalisation de votre application
        * Ajout de feuille de style Bootstrap ainsi qu'une feuille de style personnelle.
        
*(\*) : L'horloge SVG est uniquement fonctionnelle sur l'URI [http://192.168.75.16:8080/tp5/client/](http://192.168.75.16:8080/tp5/client/) hébergée sur Tomcat.*

##### Description globale
###### VM utilisée
* Utilisation de la machine virtuelle numéro 13 : [https://192.168.75.13/api/v2/](https://192.168.75.13/api/v2/)

###### Performances sur Tomcat
* Temps de chargement de la page HTML initiale : 82ms
* Temps d'affichage de l'app shell : 21ms
* Temps d'affichage du chemin critique de rendu (CRP) : 54ms

###### Performances sur nginx
* Temps de chargement de la page HTML initiale : 197ms
* Temps d'affichage de l'app shell : 19ms
* Temps d'affichage du chemin critique de rendu (CRP) : 76ms