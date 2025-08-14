package repository;

import entities.Fournisseur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurRepository {

    // CREATE
    public void ajouterFournisseur(Fournisseur fournisseur) {
        String sql = "INSERT INTO Fournisseur (nom, Telephone, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getTelephone());
            stmt.setString(3, fournisseur.getEmail());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        fournisseur.setId_fournisseur(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Fournisseur> listerFournisseurs() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM Fournisseur";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fournisseur f = new Fournisseur();
                f.setId_fournisseur(rs.getInt("id_fournisseur"));
                f.setNom(rs.getString("nom"));
                f.setTelephone(rs.getString("Telephone"));
                f.setEmail(rs.getString("email"));
                fournisseurs.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }

    // READ BY ID
    public Fournisseur trouverFournisseurParId(int id) {
        String sql = "SELECT * FROM Fournisseur WHERE id_fournisseur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Fournisseur f = new Fournisseur();
                    f.setId_fournisseur(rs.getInt("id_fournisseur"));
                    f.setNom(rs.getString("nom"));
                    f.setTelephone(rs.getString("Telephone"));
                    f.setEmail(rs.getString("email"));
                    return f;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public void modifierFournisseur(Fournisseur fournisseur) {
        String sql = "UPDATE Fournisseur SET nom=?, Telephone=?, email=? WHERE id_fournisseur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getTelephone());
            stmt.setString(3, fournisseur.getEmail());
            stmt.setInt(4, fournisseur.getId_fournisseur());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void supprimerFournisseur(int id) {
        String sql = "DELETE FROM Fournisseur WHERE id_fournisseur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
