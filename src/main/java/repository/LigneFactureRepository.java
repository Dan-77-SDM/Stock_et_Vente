package repository;

import entities.LigneFacture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigneFactureRepository {

    public void ajouter(LigneFacture lf) {
        String sql = "INSERT INTO LigneFacture (quantite, prixUnitaire, id_produit, id_facture) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lf.getQuantite());
            stmt.setInt(2, lf.getPrixUnitaire());
            stmt.setInt(3, lf.getId_produit());
            stmt.setInt(4, lf.getId_facture());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifier(LigneFacture lf) {
        String sql = "UPDATE LigneFacture SET quantite=?, prixUnitaire=?, id_produit=?, id_facture=? WHERE id_ligne=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lf.getQuantite());
            stmt.setInt(2, lf.getPrixUnitaire());
            stmt.setInt(3, lf.getId_produit());
            stmt.setInt(4, lf.getId_facture());
            stmt.setInt(5, lf.getId_ligne());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM LigneFacture WHERE id_ligne=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LigneFacture trouverParId(int id) {
        String sql = "SELECT * FROM LigneFacture WHERE id_ligne=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LigneFacture lf = new LigneFacture();
                lf.setId_ligne(rs.getInt("id_ligne"));
                lf.setQuantite(rs.getInt("quantite"));
                lf.setPrixUnitaire(rs.getInt("prixUnitaire"));
                lf.setId_produit(rs.getInt("id_produit"));
                lf.setId_facture(rs.getInt("id_facture"));
                return lf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LigneFacture> lister() {
        List<LigneFacture> liste = new ArrayList<>();
        String sql = "SELECT * FROM LigneFacture";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LigneFacture lf = new LigneFacture();
                lf.setId_ligne(rs.getInt("id_ligne"));
                lf.setQuantite(rs.getInt("quantite"));
                lf.setPrixUnitaire(rs.getInt("prixUnitaire"));
                lf.setId_produit(rs.getInt("id_produit"));
                lf.setId_facture(rs.getInt("id_facture"));
                liste.add(lf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
