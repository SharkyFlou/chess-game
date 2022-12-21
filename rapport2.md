# Rapport persos n2

## note 1

Dans `Board`, la fonction destroyPiece, prends un bouléen supplémentaire, pour savoir si la fonction est appelé suite à un déplacement de pièce, ou réelement pour manger une pièce (appel différent)

Dans `DisplayBoard` BoxLayout principal et PanelLow, en un simple BorderLayout

Création de `CheckChecker` ayant une référence vers le `Board` qui permet de vérifier si il y a un échec, un pat, ou un échec et mat.

Création de l'interface `LockedObserver` permetant au `DisplayBoard` de pouvoir affiché en orange les pièces bloquées, et le roi si il est en échec.

Modification de `DisplayBoard` qui observe le `CheckChecker` afin de pouvoir affiché les pièces qui sont bloqués lorsque le roi est mit en échec.

Modification de `SuperVisor` qui entre chaque tour vérifie grâce au `CheckChecker` si le prochain jour est en échec, en pat, ou en échec et mat.

Modification de `Mover`, ajout d'un lien vers le `CheckChecker` pour filtrer les mouvements ne laissant pas en échec le roi -> on ne peut donc plus manger les rois maintenant (pratique).

Modification de `Board`, la fonction de déplacement prend un booléen supplémentaire afin de savoir si elle est appelé pour faire des calcul depuis `CheckChecker`, ou bien pour réelement déplacer la pièce (afin de ne pas initialiser les premiers mouvements du roi, de la tour ou du pion).

Supression fichier "!".


