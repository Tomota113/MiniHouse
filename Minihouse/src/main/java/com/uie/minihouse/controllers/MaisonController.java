package com.uie.minihouse.controllers;
import com.uie.minihouse.dao.MaisonDAO;
import com.uie.minihouse.model.Maison;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaisonController implements Initializable {

    // ── Formulaire ────────────────────────────────────────────────────────────
    @FXML private TextField      tfId;
    @FXML private TextField      tfNom;
    @FXML private TextField      tfAdresse;
    @FXML private ComboBox<String> cbType;
    @FXML private TextField      tfPrix;
    @FXML private TextField      tfSurface;
    @FXML private Spinner<Integer> spNbPieces;
    @FXML private ComboBox<String> cbStatut;

    // ── Recherche ─────────────────────────────────────────────────────────────
    @FXML private TextField      tfRecherche;

    // ── Tableau ───────────────────────────────────────────────────────────────
    @FXML private TableView<Maison>         tableView;
    @FXML private TableColumn<Maison, Integer> colId;
    @FXML private TableColumn<Maison, String>  colNom;
    @FXML private TableColumn<Maison, String>  colAdresse;
    @FXML private TableColumn<Maison, String>  colType;
    @FXML private TableColumn<Maison, Double>  colPrix;
    @FXML private TableColumn<Maison, Double>  colSurface;
    @FXML private TableColumn<Maison, Integer> colPieces;
    @FXML private TableColumn<Maison, String>  colStatut;

    // ── Statistiques ─────────────────────────────────────────────────────────
    @FXML private Label lblTotal;
    @FXML private Label lblVendus;
    @FXML private Label lblMoyen;

    private MaisonDAO dao;
    private ObservableList<Maison> data;

    // ══════════════════════════════════════════════════════════════════════════
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao  = new MaisonDAO();
        data = FXCollections.observableArrayList();

        // Remplir les ComboBox
        cbType.setItems(FXCollections.observableArrayList("Maison", "Appartement", "Terrain"));
        cbType.getSelectionModel().selectFirst();

        cbStatut.setItems(FXCollections.observableArrayList("Disponible", "Loué", "Vendu"));
        cbStatut.getSelectionModel().selectFirst();

        // Configurer les colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colSurface.setCellValueFactory(new PropertyValueFactory<>("surface"));
        colPieces.setCellValueFactory(new PropertyValueFactory<>("nbPieces"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Sélection dans le tableau → remplir le formulaire
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, selected) -> {
                if (selected != null) remplirFormulaire(selected);
            }
        );

        tableView.setItems(data);
        chargerTous();
    }

    // ── AJOUTER ───────────────────────────────────────────────────────────────
    @FXML
    private void handleAjouter() {
        if (!validerFormulaire()) return;
        Maison m = lireFormulaire();
        if (dao.ajouter(m)) {
            showInfo("Succès", "Bien ajouté avec succès !");
            reinitialiserFormulaire();
            chargerTous();
        } else {
            showErreur("Erreur lors de l'ajout.");
        }
    }

    // ── MODIFIER ──────────────────────────────────────────────────────────────
    @FXML
    private void handleModifier() {
        if (tfId.getText().isEmpty()) {
            showErreur("Veuillez sélectionner un bien dans le tableau.");
            return;
        }
        if (!validerFormulaire()) return;
        Maison m = lireFormulaire();
        m.setId(Integer.parseInt(tfId.getText()));
        if (dao.modifier(m)) {
            showInfo("Succès", "Bien modifié avec succès !");
            reinitialiserFormulaire();
            chargerTous();
        } else {
            showErreur("Erreur lors de la modification.");
        }
    }

    // ── SUPPRIMER ─────────────────────────────────────────────────────────────
    @FXML
    private void handleSupprimer() {
        Maison selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showErreur("Veuillez sélectionner un bien à supprimer.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le bien ?");
        confirm.setContentText("Voulez-vous vraiment supprimer : " + selected.getNom() + " ?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (dao.supprimer(selected.getId())) {
                showInfo("Succès", "Bien supprimé.");
                reinitialiserFormulaire();
                chargerTous();
            } else {
                showErreur("Erreur lors de la suppression.");
            }
        }
    }

    // ── RECHERCHER ────────────────────────────────────────────────────────────
    @FXML
    private void handleRechercher() {
        String kw = tfRecherche.getText().trim();
        if (kw.isEmpty()) { chargerTous(); return; }
        List<Maison> resultats = dao.rechercher(kw);
        data.setAll(resultats);
        mettreAJourStats();
    }

    // ── TOUT AFFICHER ─────────────────────────────────────────────────────────
    @FXML
    private void handleToutAfficher() {
        tfRecherche.clear();
        chargerTous();
    }

    // ── RÉINITIALISER ─────────────────────────────────────────────────────────
    @FXML
    private void handleReinitialiser() {
        reinitialiserFormulaire();
        tableView.getSelectionModel().clearSelection();
    }

    // ══════════════════════════════════════════════════════════════════════════
    // MÉTHODES PRIVÉES
    // ══════════════════════════════════════════════════════════════════════════

    private void chargerTous() {
        data.setAll(dao.getTous());
        mettreAJourStats();
    }

    private void mettreAJourStats() {
        lblTotal.setText("Total : " + dao.getNombreTotal());
        lblVendus.setText("Vendus : " + dao.getNombreVendus());
        double moy = dao.getPrixMoyen();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
        lblMoyen.setText("Prix moyen : " + nf.format(moy) + " FCFA");
    }

    private Maison lireFormulaire() {
        return new Maison(
            tfNom.getText().trim(),
            tfAdresse.getText().trim(),
            cbType.getValue(),
            Double.parseDouble(tfPrix.getText().trim()),
            Double.parseDouble(tfSurface.getText().trim()),
            spNbPieces.getValue(),
            cbStatut.getValue()
        );
    }

    private void remplirFormulaire(Maison m) {
        tfId.setText(String.valueOf(m.getId()));
        tfNom.setText(m.getNom());
        tfAdresse.setText(m.getAdresse());
        cbType.setValue(m.getType());
        tfPrix.setText(String.valueOf(m.getPrix()));
        tfSurface.setText(String.valueOf(m.getSurface()));
        spNbPieces.getValueFactory().setValue(m.getNbPieces());
        cbStatut.setValue(m.getStatut());
    }

    private void reinitialiserFormulaire() {
        tfId.clear();
        tfNom.clear();
        tfAdresse.clear();
        cbType.getSelectionModel().selectFirst();
        tfPrix.clear();
        tfSurface.clear();
        spNbPieces.getValueFactory().setValue(3);
        cbStatut.getSelectionModel().selectFirst();
    }

    private boolean validerFormulaire() {
        StringBuilder erreurs = new StringBuilder();
        if (tfNom.getText().trim().isEmpty())     erreurs.append("- Le nom est obligatoire.\n");
        if (tfAdresse.getText().trim().isEmpty())  erreurs.append("- L'adresse est obligatoire.\n");
        if (cbType.getValue() == null)             erreurs.append("- Le type est obligatoire.\n");
        try { Double.parseDouble(tfPrix.getText().trim()); }
        catch (NumberFormatException e)            { erreurs.append("- Le prix doit être un nombre.\n"); }
        try { Double.parseDouble(tfSurface.getText().trim()); }
        catch (NumberFormatException e)            { erreurs.append("- La surface doit être un nombre.\n"); }
        if (cbStatut.getValue() == null)           erreurs.append("- Le statut est obligatoire.\n");

        if (erreurs.length() > 0) {
            showErreur(erreurs.toString());
            return false;
        }
        return true;
    }

    private void showInfo(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showErreur(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Problème détecté");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
