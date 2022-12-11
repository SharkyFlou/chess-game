# Rapport A31-Chessgame; FLU HOOGLAND

## 1ère séance :

Début d'un diagramme, résumant les intéractions entre les classes:

- Une interface `Pièce` contient les fonctions de déplacement, d'attaque, etc...
- Utilisation du Template Method : chaque pièce (cavalier, pion, roi, reinne, fou, tour) implémente cette classe, et redefinit les fonctions de mouvement et d'attaque
- Nous utilisons le patron de structure "Facade" en rajoutant une classe "GameFacade" qui est lancée par le main, et gère fait le lien entre la vue, et le modèle

## 2ème séance :

- On passe de IntelliJ à Visual Studio Code, car nous sommmes plus habitués à utiliser VS code qu'IntelliJ
- Rajout de View : `DisplayBoard` et `Terminal`
- Utilisation de Factory Method pour pouvoir créer des pièces depuis Board plus facilement
- Rajout de l'observeur `BoardObserver`, qui observe `Board`, et `DisplayBoard` l'implémente pour se mettre à jour automatiquement lors de deplacement de pièces

## 3e séance :

- Changements faits au PUML :
  - La classe Board doit être divisée en plusieurs sub-classes. L'idée est de dépourvoir le Board d'autant de responsabilités
  - Pièce n'est pas censée gérer ses mouvements, c'est une des subclasses de Board
  - Les relations entre classes ont été modifiées, le PUML a été nettoyé
- Définition de toutes les classes par Model, View et Controller

## 4e seance :

Passage au codage, modifications du PUML

- Création de `Board`, `Interactions` (qui gère le mouvement) et `Manager` (qui gère les victoires, règles et le "backend" du chess)
- On enlève le FactoryMethod, vu que chaque pièce peut être créée avec les attributs propres à chacune (pas de problème auquel répondre)
- Création du squelette de chaque classe décrite dans le PUML.
- Création de la view `DisplayBoard` qui affiche déjà un échiquier.
- Inititalisaton du `Board`, création des pièces aux bon endroits.
- Début calcul mouvement des pièces.

## 5e seance :

Modification du PUML :

- Ajout de la classe `Mover` qui remplace `Interactions`, responsable de calculer les déplacements et attaques réelement possible, et de renvoyer un tableau de tableau de booléan correspondant (permet de respecter le modèle MVC)
- Calcul des mouvements théorique des pièces défini dans chaque pièce
- Ajout `FirstMovement` ...

## 6e seance :

- Ajout `ScoreObserver`
- Début codage de `Mover`

## 7e seance :

- Rapport.md plus beau
- Calcul de chaque type de movement fini, tout mouvement (de théorique à pratique) est fait dans `Mover` (ex: calcul de la trajectoire d'un Bishop)
- Nettoyage du code + commentaires
