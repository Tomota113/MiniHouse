# 🏠 Rapport de Projet : Application MiniHouse
**Étudiant :** Ibrahim Modibo Tomota  
**Classe :** Licence 2 – Intelligence Artificielle (2025-2026)  
**Matière :** Programmation Java & Interface Graphique (JavaFX)

---

## 🌟 Introduction
Dans le cadre de ma deuxième année en Licence IA, j'ai conçu et développé **MiniHouse**, une application de gestion immobilière complète. Ce projet m'a permis de transformer des concepts théoriques en une solution logicielle concrète, capable de gérer un catalogue de biens (maisons, appartements, terrains) de manière fluide et professionnelle.

L'objectif était double : offrir une expérience utilisateur (UX) simple et intuitive tout en garantissant une structure technique robuste basée sur des standards industriels.

---

## 🏗️ Architecture du Système

Pour ce projet, j'ai opté pour l'architecture **MVC (Modèle-Vue-Contrôleur)**. Ce choix n'est pas anodin : il permet de séparer proprement la logique métier, les données et l'affichage, facilitant ainsi la maintenance et l'évolution future de l'application.

### 1. Le Modèle (`Maison.java`)
C'est le cœur de l'application. J'y ai défini l'objet "Maison" avec tous les attributs demandés (ID, Nom, Adresse, Type, Prix, Surface, etc.). C'est une classe simple et efficace (POJO) qui sert de structure de données à travers tout le projet.

### 2. La Couche DAO (`MaisonDAO.java`)
Pour la gestion de la base de données, j'ai implémenté le pattern **Data Access Object (DAO)**. 
- J'ai utilisé **JDBC** pour communiquer avec MySQL.
- Toutes les requêtes sont sécurisées via des `PreparedStatement` pour éviter les failles de sécurité (injections SQL).
- Les calculs de statistiques (prix moyen, total, vendus) sont effectués directement au niveau SQL pour optimiser les performances.

### 3. Le Contrôleur (`MaisonController.java`)
C'est le "cerveau" de l'interface. Il gère les événements (clics sur les boutons, sélection dans le tableau) et fait le pont entre l'utilisateur et la base de données. J'y ai intégré une logique de validation stricte : l'application refuse d'ajouter un bien si les informations sont incomplètes ou erronées.

### 4. La Vue (`maison.fxml`)
Conçue avec **Scene Builder**, la vue utilise un fichier FXML pour définir l'interface. J'ai apporté un soin particulier au design en utilisant des conteneurs (`GridPane`, `VBox`, `HBox`) pour que l'interface reste harmonieuse.

---

## 🚀 Fonctionnalités Clés

### 🔹 Gestion Complète (CRUD)
L'application permet d'ajouter, de modifier, de supprimer et de lister les biens. J'ai ajouté une fonctionnalité de confort : lorsqu'on clique sur une ligne du tableau, le formulaire se remplit automatiquement, ce qui rend la modification extrêmement rapide.

### 🔹 Recherche Intelligente
L'utilisateur peut rechercher un bien en tapant simplement un mot-clé. Le système scanne simultanément les noms, les types et les adresses pour trouver les correspondances, rendant la navigation dans un grand catalogue très aisée.

### 🔹 Tableau de Bord (Statistiques)
En bas de l'écran, un bandeau dynamique affiche en temps réel :
- Le volume total du parc immobilier.
- Le succès commercial (nombre de biens vendus).
- La valeur financière (prix moyen du catalogue).

---

## 🎨 Design et Expérience Utilisateur
En tant qu'étudiant en IA, je sais que l'utilité d'un outil dépend de sa simplicité. J'ai donc appliqué un style visuel moderne :
- **Codes Couleurs** : Vert pour l'ajout, Bleu pour la modification, Rouge pour la suppression.
- **Typographie** : Utilisation de polices claires et de labels en gras pour une lecture rapide.
- **Feedback** : Chaque action importante déclenche une petite boîte de dialogue (Succès, Erreur ou Confirmation) pour que l'utilisateur ne soit jamais perdu.

---

## 🛠️ Fiche Technique
- **Langage :** Java 17+
- **Framework UI :** JavaFX
- **Base de données :** MySQL 8.0
- **Connecteur :** JDBC (MySQL Connector/J)
- **Gestionnaire de dépendances :** Maven (pom.xml)

---

## 💡 Conclusion et Perspectives
Ce projet **MiniHouse** a été une étape clé dans mon apprentissage. Il m'a permis de maîtriser l'intégration d'une base de données dans une application graphique et de comprendre l'importance d'un code bien structuré.

Pour la suite, j'envisage d'ajouter des fonctionnalités d'IA, comme un module de prédiction de prix basé sur la surface et la localisation, pour transformer cet outil de gestion en un véritable outil d'aide à la décision.

---
**Ibrahim Modibo Tomota**  
*L2 IA - 2026*
