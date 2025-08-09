import java.sql.Connection;
import java.sql.SQLException;

import repository.DatabaseConnection;

public class MainDatabaseconnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
    