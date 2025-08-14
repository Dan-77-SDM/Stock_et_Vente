package repository;

import entities.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitRepository {

    // Ajouter un produit
    public void ajouterProduit(Produit p) {
        String sql = "INSERT INTO Produit(designation, prix_Unitaire, stock, id_fournisseur) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getDesignation());
            stmt.setDouble(2, p.getPrix_Unitaire());
            stmt.setInt(3, p.getStock());

            if (p.getId_fournisseur() != null) {
                stmt.setInt(4, p.getId_fournisseur());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId_produit(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un produit
    public void modifierProduit(Produit p) {
        String sql = "UPDATE Produit SET designation=?, prix_Unitaire=?, stock=?, id_fournisseur=? WHERE id_produit=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getDesignation());
            stmt.setDouble(2, p.getPrix_Unitaire());
            stmt.setInt(3, p.getStock());

            if (p.getId_fournisseur() != null) {
                stmt.setInt(4, p.getId_fournisseur());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, p.getId_produit());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un produit
    public void supprimerProduit(int id) {
        String sql = "DELETE FROM Produit WHERE id_produit=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lister tous les produits
    public List<Produit> listerProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM Produit";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produit p = new Produit();
                p.setId_produit(rs.getInt("id_produit"));
                p.setDesignation(rs.getString("designation"));
                p.setPrix_Unitaire(rs.getDouble("prix_Unitaire"));
                p.setStock(rs.getInt("stock"));

                int idFourn = rs.getInt("id_fournisseur");
                if (rs.wasNull()) {
                    p.setId_fournisseur(null);
                } else {
                    p.setId_fournisseur(idFourn);
                }

                produits.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    // Trouver un produit par ID
    public Produit trouverProduitParId(int id) {
        String sql = "SELECT * FROM Produit WHERE id_produit=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setDesignation(rs.getString("designation"));
                    p.setPrix_Unitaire(rs.getDouble("prix_Unitaire"));
                    p.setStock(rs.getInt("stock"));

                    int idFourn = rs.getInt("id_fournisseur");
                    if (rs.wasNull()) {
                        p.setId_fournisseur(null);
                    } else {
                        p.setId_fournisseur(idFourn);
                    }

                    return p;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
