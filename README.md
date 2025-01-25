# LinguaCrypt : Clone numérique de CodeNames™

## Présentation du Projet

Développé avec mon groupe lors de la CodingWeek 2025 à TELECOM Nancy, LinguaCrypt est une implémentation numérique du jeu de société CodeNames™, réalisée en Java/JavaFX.

## Fonctionnalités Principales

- Paramétrage personnalisable de la partie (taille de grille, thèmes de mots, nombre de joueurs)
- Respect strict des règles originales de CodeNames™
- Sauvegarde et chargement de parties
- Création et gestion de listes de mots personnalisées
- Mécanisme de consultation de la carte des agents secrets
- Minuteur intégré avec mode "blitz" optionnel
- Mode de jeu avec images
- Mode solo avec indices pré-programmés
- Suivi des statistiques des joueurs et des parties

## Fonctionnalités Avancées

- Mode coopératif à deux joueurs
- Mode solo contre une IA avec différents niveaux de difficulté

## Aspects Techniques

- Développé en Java 17 et JavaFX
- Architecture modulaire
- Gestion de version avec Git
- Processus de build automatisé avec Gradle

## Méthodologie de Projet

Développement itératif et incrémental, avec une version fonctionnelle livrée à la fin de chaque journée de la CodingWeek.

## Installation et Exécution

### Prérequis
- Java 17
- JavaFX SDK

### Lancement de l'Application
```bash
java --module-path CHEMIN_VERS_JAVAFX_LIB --add-modules javafx.base,javafx.controls,javafx.fxml -jar linguacrypt.jar
```

Si vous voulez générer une nouvelle archive jar avec IntelliJ par exemple, assurez-vous que le dossier `resources` est bien placé à la racine de l'archive.