# 🎨 Color Addict - Projet Java Avancé

Ce projet est une implémentation du jeu de cartes **Color Addict** en Java, utilisant une architecture **MVC** avec JavaFX pour le frontend. Il a été réalisé dans le cadre d'un projet de Programmation Java Avancée.

## 👨‍💻 Équipe & Répartition

- **Anikati M’MADI** : Développement Backend
- **Triomphant Aldi Nzikou** : Développement Frontend

## 🧱 Architecture du Projet

### Backend - Partie Modèle

Les principales classes du modèle :

- `Couleur` : Enumération des couleurs de cartes.
- `Carte` : Classe abstraite de base.
  - `CarteCouleur` : Cartes avec couleur (hors multicolore).
  - `CarteJoker` : Cartes multicolores (Joker).
- `MainJoueur` : Représente la main du joueur (ArrayList).
- `PiocheJoueur` : Représente la pioche du joueur (Stack).
- `Joueur` : Nom, main et pioche.
- `Tas` : Cartes jouées sur la table.
- `ReglesJeu` : Contient la logique du jeu.
- `AppData` : Contient les données globales partagées entre backend et frontend.

### Frontend - Partie Vue (JavaFX + FXML)

Interfaces créées avec Scene Builder :

- `Accueil.fxml`
- `ChoixJeux.fxml`
- `LesJoueurs.fxml`
- `PartieAdeux.fxml`

> Les cartes sont affichées en texte (nom + couleur) au lieu d’images.

### Contrôleurs (Controller)

- `AccueilController`
- `ChoixJeuxController`
- `LesJoueurController`
- `PartieAdeuxController`

## 🧠 Fonctionnalités du Jeu

- **57 cartes** dont **8 jokers**.
- Répartition équilibrée des cartes selon le nombre de joueurs (2 à 6 joueurs).
- Prise en compte des règles spécifiques (joker jouable en premier, choix de couleur).
- Tour par tour avec gestion du tas, de la main et de la pioche.
- Condition de victoire : plus de cartes en main ou en pioche.

## 🔌 Intégration Frontend / Backend

- Utilisation d’une classe `AppData` pour centraliser les données.
- Mise en œuvre de **l’injection de dépendances** pour simplifier la communication entre FXML et les données du modèle.

## ⚠️ Limitations

- Le jeu contre l'ordinateur est partiellement implémenté.
- Affichage des cartes sous forme textuelle (pas d’images).
- Des ajustements manuels étaient nécessaires dans la console avant l’utilisation d'AppData.

## 🧰 Technologies utilisées

- Java 17
- JavaFX
- Scene Builder
- FXML
- MVC Architecture

## 🌐 Ressources & Aide

- **StackOverflow**, **OpenClassroom**, **Udemy**, **Codecademy**
- Discussions en TD avec les enseignants et camarades


## 📄 Licence

Projet réalisé dans le cadre d’un cours universitaire — usage éducatif uniquement.
