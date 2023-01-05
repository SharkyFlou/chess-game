# Rapport persos n2

## note 1

Dans `Board`, la fonction destroyPiece, prends un bouléen supplémentaire, pour savoir si la fonction est appelé suite à un déplacement de pièce, ou réelement pour manger une pièce (appel différent)

Dans `DisplayBoard` BoxLayout principal et PanelLow, en un simple BorderLayout

Création de `CheckChecker` dans le package `model` ayant une référence vers le `Board` qui permet de vérifier si il y a un échec, un pat, ou un échec et mat.

Création de l'interface `LockedObserver` dans le package `model` permetant au `DisplayBoard` de pouvoir affiché en orange les pièces bloquées, et le roi si il est en échec.

Modification de `DisplayBoard` qui observe le `CheckChecker` afin de pouvoir affiché les pièces qui sont bloqués lorsque le roi est mit en échec.

Modification de `Supervisor` qui entre chaque tour vérifie grâce au `CheckChecker` si le prochain jour est en échec, en pat, ou en échec et mat.

Modification de `Mover`, ajout d'un lien vers le `CheckChecker` pour filtrer les mouvements ne laissant pas en échec le roi -> on ne peut donc plus manger les rois maintenant (pratique).

Modification de `Board`, la fonction de déplacement prend un booléen supplémentaire afin de savoir si elle est appelé pour faire des calcul depuis `CheckChecker`, ou bien pour réelement déplacer la pièce (afin de ne pas initialiser les premiers mouvements du roi, de la tour ou du pion).

Supression fichier "!".

## Ajout de la possibilité de roquer :

Modification de `Mover`, ajout du preview de la destination final du roi pour les roques, uniquement si les roques sont possible (sans vérifier les echecs intérimédiaire pour l'instant)

Modification de `Board`, qui lorsque déplace une pièce, vérifie si c'est un roi, si oui, regarde si la distance parcouru en X est supérieur de 1, si oui c'est un roque et alors il déplace la tour convenu par la suite.

Modification de `CheckChecker`, qui vérifie si le roi est en echec avant ou pendant le déplacement du roque, et autorise ou non le déplacement.

## Ajout de la prise en passant :

Modification de `Board` : rajout d'un tableau de 2 int, retenant si la dernière pièce ayant bougé est un pion, et si c'est le cas, ses coordonnées, sinon écrase les anciennes par -1.

Modification de `Mover` qui quand calcul l'attaque d'un pion, regarde dans `Board` si il y est alors possible de faire une prise en passant.

Modification de `Supervisor` qui vérifie si l'attaque se dirige vers une case oui, si oui c'est une prise en passant ! Dans ce cas il va manger et prendre les points de la case soit celle du dessus soit du dessous.

Pas besoin de mettre le système d'echec à jour !

## Ajout de la promotion :

Création de `PromotionWindow`, la fenêtre qui s'affiche au moment où une Promotion possible est vérifiée.

Ceci est fait par `Supervisor`, qui, dès qu'un(e) mouvement/attaque est réalisé(e), vérifie et instancie cette Promotion.

Au moment du choix sur `PromotionWindow`(pion devient reine, fou, tour ou chevalier),

## Ajout écran de début :

Modification du `Main`, et création de `Initialiser` dans le package `controller`, les instanciation se feront desormai dans cette classe. Le `Main` execute la fonction `LaunchStartMenu` de `Initialiser` pour lancer le jeu.

Création de `StartScreen` dans le package `view`. Cette classe est une interface swing possédant des champs pour remplir le nom du joueur blanc et noir, et un bouton "Jouer".
Le boutton lorsque cliqué appelle la fonction `LaunchGame` de la classe `Initialiser` avec en paramètre le nom des joueurs.

Modification de `DisplayBoard`, ajout de deux String en attribut : le nom de chaque joueur pour l'affiche des tours.

Modification de `LabelScore`, ajout d'un String pour l'affichage du score au nom du joueur.

`Initialiser` instancie alors les les autres classe comme faisait précédement le `Main`.

La partie peut commencer !

## Ajout écran de fin :

En passant en paramètre les infos sur chaque joueur depuis `Supervisor`, `EndScreen` permet d'afficher les scores et noms de chaque joueur après un checkmate.
`Supervisor` instancie cette classe au moment ou le Checkmate est vérifié ; le joueur gagnant et perdant sont affichés.

`EndScreen` permet aussi de relancer le jeu.
L'idée est d'appeler `Initialiser`, la classe qui relancera le jeu.
