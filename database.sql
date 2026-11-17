-- 🏢 Script de création de la base de données MiniHouse
-- Étudiant : Ibrahim Modibo Tomota (L2 IA)

-- 1. Création de la base de données
CREATE DATABASE IF NOT EXISTS immobilier;
USE immobilier;

-- 2. Création de la table 'maison'
-- Cette table stocke tous les types de biens (Maisons, Appartements, Terrains)
CREATE TABLE IF NOT EXISTS maison (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL, -- Maison, Appartement, Terrain
    prix DOUBLE NOT NULL,
    surface DOUBLE NOT NULL,
    nb_pieces INT NOT NULL,
    statut VARCHAR(50) NOT NULL -- Disponible, Loué, Vendu
);

-- 3. Insertion de quelques données d'exemple (Optionnel)
-- Permet de tester l'application immédiatement après l'importation du script
INSERT INTO maison (nom, adresse, type, prix, surface, nb_pieces, statut) VALUES
('Villa des Palmiers', 'Quartier du Fleuve, Bamako', 'Maison', 125000000, 450, 6, 'Disponible'),
('Appartement Horizon', 'ACI 2000, Immeuble Sky', 'Appartement', 45000000, 110, 3, 'Loué'),
('Terrain Titré Kalaban', 'Kalaban Coro, Près du goudron', 'Terrain', 15000000, 300, 0, 'Disponible'),
('Résidence Modibo', 'Sébénikoro, Rue 12', 'Maison', 85000000, 200, 4, 'Vendu');
