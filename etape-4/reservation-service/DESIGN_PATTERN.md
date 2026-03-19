# Design Pattern — State

Le pattern State a été choisi pour gérer le cycle de vie d'une réservation (CONFIRMED → CANCELLED ou COMPLETED).

Sans ce pattern, la logique de transition aurait été gérée avec des if/else sur le status dans le service, ce qui devient vite illisible quand on ajoute des règles métier à chaque transition.

Chaque état encapsule son propre comportement et interdit les transitions invalides (on ne peut pas annuler une réservation déjà complétée par exemple).

C'est un pattern comportemental du GoF, bien adapté aux entités qui ont un cycle de vie avec des états finis.
