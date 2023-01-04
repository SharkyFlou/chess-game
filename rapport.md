# Rapport A31-Chessgame; FLU HOOGLAND

## 1ère séance :

Début d'un diagramme, résumant les intéractions entre les classes:

- Une interface `Pièce` contient les fonctions de déplacement, d'attaque, etc...
- Utilisation du Template Method : chaque pièce (cavalier, pion, roi, reinne, fou, tour) implémente cette classe, et redefinit les fonctions de mouvement et d'attaque.
- Nous utilisons le patron de structure "Facade" en rajoutant une classe "GameFacade" qui est lancée par le main, et gère fait le lien entre la vue, et le modèle.

## 2ème séance :

- On passe de IntelliJ à Visual Studio Code, car nous sommmes plus habitués à utiliser VS code qu'IntelliJ.
- Rajout de View : `DisplayBoard` et `Terminal`.
- Utilisation de Factory Method pour pouvoir créer des pièces depuis Board plus facilement.
- Rajout de l'observeur `BoardObserver`, qui observe `Board`, et `DisplayBoard` l'implémente pour se mettre à jour automatiquement lors de deplacement de pièces.

## 3ème séance :

- La classe `Board` doit être divisée en plusieurs sub-classes. L'idée est de dépourvoir le `Board` d'autant de responsabilités, car Board gère aussi les mouvement des pièces, et stock les prévisualisations des pièces en plus des pièces elles même.
- Pièce n'est pas censée gérer ses mouvements, c'est une des subclasses de `Board`.
- Les relations entre classes ont été modifiées, le PUML a été nettoyé.
- Définition de toutes les classes par Model, View et Controller.

## 4ème séance :

- Création de `Board`, `Interactions` (qui gère les mouvements, ce n'est plus Board (vu a la [3e séance](#3e-séance))) et `Manager` (qui gère les victoires, les pats et le "backend" du chess).
- On enlève le Factory Method, vu que chaque pièce peut être créée avec les attributs propres à chacune (pas de problème auquel répondre).
- Création du squelette de chaque classe décrite dans le PUML.
- Création de la view `DisplayBoard` qui affiche déjà un échiquier.
- Inititalisaton du `Board`, création des pièces aux bon endroits.
- Début calcul mouvement des pièces.

## 5ème séance :

- Ajout de la classe `Mover` qui remplace `Interactions` (vu a la [4e séance](#4e-séance)), responsable de calculer les déplacements et attaques réelement possible, et de renvoyer un tableau de tableau de booléan correspondant (permet de respecter le modèle MVC).
- Calcul des mouvements théorique des pièces défini dans chaque pièce.
- Ajout de l''interface `FirstMovement` dont hérite `Pawn`, `Rook` et `King`, qui permet de savoir si les pièces ont déjà bougé, afin de calculer les mouvements possibles.

## 6ème séance :

- Ajout `ScoreObserver` qui observe `Manager` (vu a la [4e séance](#4e-séance)).
- Début codage de `Mover`, passe des mouvement théorique des pièces aux mouvement réelement possible sur le plateau.
- Choix : tout les commentaires seront desormai en français.

## 7ème séance :

- `Rapport.md` plus beau 😎.
- Ajout de `PanelScore` qui implémente `ScoreObserver` et étends JPanel; `DisplayBoard` possède deux `PanelScore`, un pour chaque équipe.
- Calcul de chaque type de movement fini, tout mouvement (de théorique à pratique) est fait dans `Mover` (ex: calcul de la trajectoire d'un Bishop).
- Nettoyage du code + commentaires.
- Suppression de la vue `Terminal` inutile créé lors de la [2ème séance](#2ème-séance)).

## 8ème séance :

- Développement de l'interface "Promotion" : création de `PromotionWindow`, qui affiche les pièces possibles en situation de promotion.
- Utilisation de `PromotionObserver`, qui décompose une fonction de `BoardObserver` dédiée aux promotions.
- Modification de `Supervisor`: après avoir check si la pièce peut être promoted, elle attend la réponse de `PromotionWindow` pour modifier le pion en question.
- Modification PUML
