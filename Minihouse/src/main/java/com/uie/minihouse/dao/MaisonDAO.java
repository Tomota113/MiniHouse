package com.uie.minihouse.dao;
import com.uie.minihouse.database.DBConnection;
import com.uie.minihouse.model.Maison;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaisonDAO {

    private Connection conn;

    public MaisonDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ── CREATE ──────────────────────────────────────────────────────────────
    public boolean ajouter(Maison m) {
        String sql = "INSERT INTO maison (nom, adresse, type, prix, surface, nb_pieces, statut) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getAdresse());
            ps.setString(3, m.getType());
            ps.setDouble(4, m.getPrix());
            ps.setDouble(5, m.getSurface());
            ps.setInt(6, m.getNbPieces());
            ps.setString(7, m.getStatut());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajouter : " + e.getMessage());
            return false;
        }
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<Maison> getTous() {
        List<Maison> liste = new ArrayList<>();
        String sql = "SELECT * FROM maison";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur getTous : " + e.getMessage());
        }
        return liste;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    public boolean modifier(Maison m) {
        String sql = "UPDATE maison SET nom=?, adresse=?, type=?, prix=?, surface=?, nb_pieces=?, statut=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getAdresse());
            ps.setString(3, m.getType());
            ps.setDouble(4, m.getPrix());
            ps.setDouble(5, m.getSurface());
            ps.setInt(6, m.getNbPieces());
            ps.setString(7, m.getStatut());
            ps.setInt(8, m.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modifier : " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public boolean supprimer(int id) {
        String sql = "DELETE FROM maison WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur supprimer : " + e.getMessage());
            return false;
        }
    }

    // ── SEARCH ───────────────────────────────────────────────────────────────
    public List<Maison> rechercher(String keyword) {
        List<Maison> liste = new ArrayList<>();
        String sql = "SELECT * FROM maison WHERE nom LIKE ? OR type LIKE ? OR adresse LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur rechercher : " + e.getMessage());
        }
        return liste;
    }

    // ── STATISTIQUES ─────────────────────────────────────────────────────────
    public int getNombreTotal() {
        String sql = "SELECT COUNT(*) FROM maison";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur getNombreTotal : " + e.getMessage());
        }
        return 0;
    }

    public int getNombreVendus() {
        String sql = "SELECT COUNT(*) FROM maison WHERE statut='Vendu'";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur getNombreVendus : " + e.getMessage());
        }
        return 0;
    }

    public double getPrixMoyen() {
        String sql = "SELECT AVG(prix) FROM maison";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Erreur getPrixMoyen : " + e.getMessage());
        }
        return 0.0;
    }

    // ── HELPER ───────────────────────────────────────────────────────────────
    private Maison mapper(ResultSet rs) throws SQLException {
        return new Maison(
            rs.getInt("id"),
            rs.getString("nom"),
            rs.getString("adresse"),
            rs.getString("type"),
            rs.getDouble("prix"),
            rs.getDouble("surface"),
            rs.getInt("nb_pieces"),
            rs.getString("statut")
        );
    }
}
