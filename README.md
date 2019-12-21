# Cuber

## Introduction
Cuber est une application Uber-like qui permet aux utilisateurs de rechercher des chauffeurs pour aller à une destination, en guise de replacement aux taxis.

## Cahier des charges
Un mini cahier des charges est inclu en format PDF ici : https://github.com/projetc22019/AHOKCuber/blob/master/documents/Cahier%20des%20charges.pdf.  
Ce cahier contient la totalité de la spécification du projet.    
Il est recommandé de lire le cahier en entier avant de continuer !  

## [Diagrammes et documents](documents)
Les diagrammes d'utilisation sont ici : 
* [Diagramme de recherche de nouveau trajet](documents/diagramme%20de%20recherche%20-%20nouveau%20trajet.png)
* [Diagramme de recherche de trajet existant (covoiturage)](documents/diagramme%20de%20recherche%20-%20partage%20de%20trajet.png)
* [Diagramme de base de données](documents/database.png)
* [Document d'écrans et de composants de l'application](documents/ecrans-composants.pdf)   
* [Diagramme de requête de trajet](documents/diagramme%20de%20requete%20de%20trajet.png)  

## [Design et Wireframes](documents/sketching)
Nous utilions Mockflow pour créer les sketchs de notre application. Nous avons décidé d'implémenter Bootstrap pour sa facilité et son design ergonomique. Nous mettons à jour le design régulièrement.  

* [Voir design final](documents/wireframes.pdf)

## Étapes de travail
- [x] Phase 1: écrire cahier des charges pour idée générale sur le projet. 

- [x] Phase 2: dessiner diagrammes d'utilistation et de base de données.

- [x] Phase 3: écrire les user stories, afin de définir en détails les fonctionnalités à fournir.

- [x] Phase 4: dessiner les sketchs du premier livrable de l'application. [Voir design final](documents/wireframes.pdf)

- [x] Phase 5: rechercher en détails les points techniques de chaque story: que faut-il utiliser, et comment (par exemple, intégrer OAuth pour que les utilisateurs puissent créer des comptes) ? Que faire niveau serveur ?  

- [x] Phase 6: pour chaque itération, répartir les tâches dans l'équipe.

- [x] Phase 7: pour chaque itération, créer les sketchs et les confirmer par l'équipe.  

- [ ] Phase 8: chaque développeur teste les fonctionnalités développées par l'autre (normalement il faut un équipe de contrôle de qualité, mais nous ne sommes que deux !)

- [ ] Phase 9: nous agréons de compléter une itération avec toutes les user stories complétées, ou bien quelques unes annulées ou quelques unes reportées pour la flexibilité.

- [ ] Phase 10: nous continuous ainsi de suite avec chaque itération, et à la fin de chaque itération nous recherchons si jamais on de nouvelles idées pour ajouter de nouvelles stories pour des versions futures.

## Les itérations 
Nous avons défini en gros dans notre cahier des charges (que nous tenons à préciser est une version courte d'un réel cahier des charges) que nous allons développer une version de base et une autre avec covoiturage.  

Afin de traduire cela en travail scrum, nous avons rédigé 24 User Storie [ici](UserStories.md).

Suite à cela, nous les avons répartis en fonction d'importance. Nous avons conclu avoir 5 itérations dont 4 livrables.  

### Itération 1 : la gestion d'utilisateurs
Cette itération est non livrable.  

[Les détails sont dans ce fichier](iterations/1.md).

### Itération 2 : le coeur de l'application 
Cette itération est livrable. Elle consiste des fonctionnalités de base permettant le minimum d'une application similaire à Uber.

[Les détails sont dans ce fichier](iterations/2.md).

### Itération 3 : les statistiques, c'est important !
Cette itération est livrable. Elle consiste de fonctionnalités permettant aux utilisateurs de consulter leurs trajets complétés.  

[Les détails sont dans ce fichier](iterations/3.md).

### Itération 4 : le covoiturage
Cette itération est livrable. Elle ajoute la deuxième fonctionnalité principale dans notre cahier des charges.  

[Les détails sont dans ce fichier](iterations/4.md).

### Itération 5 : plus de contrôle
Cette itération est livrable. Elle ajoute plus de contrôle pour les utilisateurs difficles à satisfaire, comme par exemple un client qui souhaite refuser un certain conducteur.  

[Les détails sont dans ce fichier](iterations/5.md).  
