## Rendre extensible le système des actions

Cette tâche se décompose naturellement en plusieur étapes:

1. Rationaliser l'implémentation des actions pour en ajouter
   en minimisant les convention de code qui ne peuvent pas être imposées
   par le compilateur, suggestions et internationalisation comprises.
2. Minimiser les dépendences au projets dataprep pour éviter de tirer
   tout le code juste pour ajouter une action
3. Créer un archetype maven pour permettre aux utilisateurs de disposer
   rapidement d'une structure de base complète avec les tests et se
   concentrer sur ce qui l'intéresse vraiment, à savoir : son métier.


### Rationaliser l'implémentation des actions

L'implementation des actions pose plusieurs problèmes pour un utilisateur
non familier avec le code de dataprep :

1. Il doit jouer avec plusieurs convention de code comme:
    * le nom du composant, potentiellement dangeureux pour tout
      l'environnement Spring en cas d'erreur
    * les appel aux implementations de base des méthodes d'ActionMetadata
    * la définition d'une action par une classe abstraite et non une
      interface, même si un adapteur serait le bienvenu (ActionAdapteur
      par exemple)
2. Les actions ont plusieurs interfaces historiques et maintenant inutiles : 
   ColumnAction, CellAction et RowAction
3. Les suggestions ont aussi besoin d'un lifting (utilisation de constantes
   en interface par ex)
...


Il s'agit donc de simplifier drastiquement le code nécessaire à une action et
de réduire les effets de bords.

Lié au ticket [TDP-823] sur la classe ScopeCategory.

Il y a un problème de gestion du cycle de vie des actions au niveau d'ActionMetadata,
c'est dangeureux avec un implem externe.

> actionContext.setActionStatus(ActionContext.ActionStatus.CANCELED);


#### Internationalisation
Il faut aussi rationnaliser l'internationalisation des paramètres et des actions
afin de ne pas être obligés de passer par le composant dataprep MessagesBundle
qui ne peut utiliser qu'un seul fichier de traductions.

Pour cela il faut utiliser correctement les abstractions Spring org.springframework.context.MessageSource
et de org.springframework.context.HierarchicalMessageSource qui permettrait
de construire correctement notre internationalisation a partir de fichiers
hierarchisés entre les différents modules.

Comment internationnaliser correctement les modules:

 - laisser l'utilisateur fournir son resource bundle
 - fournir une gestion par défaut dans un adapteur
 - autodétecter les bundle dans le casspath et les ajouter => risques de conflits


### Minimiser les dépendances

#### Séparer backend-common en trois couches:

- une couche agnostique du projet (guava-like) : common
- une couche contenant la base métier : dataprep-base
- le reste quon laisserait dans backend-common

##### Common
Cette partie, sorte de Guava spéciale dataprep, contiendrait:

* les outils de manipulation de dates : DateManipulator
* les outils d'internationalisation : I18N et MessagesBundle
* les outils de manipulation de paramètres : le package org.talend.dataprep.parameters
* les utilitaires du package org.talend.dataprep.util à l'exception du SortAndOrderHelper.

##### Base (ou Core comme la bibliothèque springframework)
Cette partie, dédiée à la base métier de dataprep contiendrait :

* Les outils de représentation et manipulation de dataset avec :
    - leur statistiques (et les packages date et number)
    - leur stockage (location, locator...)
    - les outils de sérialization en json
    - Flag et FlagNames dans diff
* Les classes Type et TypeUtils
* Les classes UserData et UserGroup car utilisés par DatasetService
* Le package de manipulation d'exceptions
* Le package d'utilitaires HTTP (org.talend.dataprep.http)
* le package de manipulations de schemas contenant : 
    - les outils de manipulation XLS
    - les outils de manipulation HTML
    - les outils de manipulation CSV
    - 
* Le package de gestion de la sécurité

### Créer un archétype maven

Sans doute la partie la plus simple, il suffit de créer un projet d'action
et d'en générer un archétype à l'aide de la commande 

> mvn archetype:generate

Cf [documentation du plugin archetypes]




[documentation du plugin archetypes]: https://maven.apache.org/guides/introduction/introduction-to-archetypes.html "Introduction to archetypes"
[TDP-823]: https://jira.talendforge.org/browse/TDP-823 "JIRA: remove ScopeCategory"
