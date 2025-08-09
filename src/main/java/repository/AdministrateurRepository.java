package repository;

import entities.Administrateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministrateurRepository {

    // 🔹 Enregistre un administrateur dans la base
   public boolean save(Administrateur admin) {
    System.out.println("Tentative d'enregistrement : " + admin.getEmail());
    String sql = "INSERT INTO Administrateur (nom, email, nom_utilisateur, mot_de_passe) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, admin.getNom());
        stmt.setString(2, admin.getEmail());
        stmt.setString(3, admin.getNomUtilisateur());
        stmt.setString(4, admin.getMotDePasse());

        int rowsInserted = stmt.executeUpdate();
        System.out.println("Nombre de lignes insérées : " + rowsInserted);
        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // 🔹 Récupère un administrateur via son email
    public Administrateur findByEmail(String email) {
        String sql = "SELECT * FROM Administrateur WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Administrateur(// Crée un nouvel objet Administrateur avec les données récupérées
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Aucun administrateur trouvé
    }

    // 🔹 Récupère un administrateur via son email et mot de passe

    public Administrateur findByEmailAndPassword(String email, String motDePasse) {
        String sql = "SELECT * FROM Administrateur WHERE email = ? AND mot_de_passe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();// Ouvre une connexion à la base de données
             PreparedStatement stmt = conn.prepareStatement(sql)) {// Prépare la requête de sélection

            stmt.setString(1, email);// Définit le paramètre email
            stmt.setString(2, motDePasse);// Définit le paramètre mot de passe

            try (ResultSet rs = stmt.executeQuery()) {// Exécute la requête de sélection
                // Vérifie si un résultat a été trouvé
                if (rs.next()) {
                    return new Administrateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;//  Aucun administrateur trouvé
    }
}
