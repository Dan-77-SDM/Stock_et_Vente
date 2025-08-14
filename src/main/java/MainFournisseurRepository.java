import entities.Fournisseur;
import services.FournisseurService;

public class MainFournisseurRepository {

    public static void main(String[] args) {

        FournisseurService fournisseurService = new FournisseurService();

        // Création d'un fournisseur de test
        Fournisseur f = new Fournisseur();
        f.setNom("ACME Fournitures");
        f.setTelephone("699123456");
        f.setEmail("contact@acme.com");

        try {
            fournisseurService.ajouterFournisseur(f);
            System.out.println("✅ Fournisseur ajouté avec succès ! ID=" + f.getId_fournisseur());
        } catch (Exception e) {
            System.out.println("❌ L'ajout du fournisseur a échoué.");
            e.printStackTrace();
        }
    }
}
