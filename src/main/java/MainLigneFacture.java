import java.util.List;
import java.util.Scanner;

import entities.LigneFacture;
import services.LigneFactureService;

public class MainLigneFacture {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LigneFactureService ligneService = new LigneFactureService();

        while (true) {
            System.out.println("\n=== GESTION DES LIGNES DE FACTURE ===");
            System.out.println("1. Ajouter une ligne");
            System.out.println("2. Modifier une ligne");
            System.out.println("3. Supprimer une ligne");
            System.out.println("4. Lister les lignes");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choix;
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un chiffre (0-4).");
                continue;
            }

            try {
                switch (choix) {
                    case 1:
                        System.out.print("Quantité : ");
                        int quantite = Integer.parseInt(scanner.nextLine());

                        System.out.print("Prix unitaire : ");
                        int prixUnitaire = Integer.parseInt(scanner.nextLine());

                        System.out.print("ID Produit : ");
                        int idProduit = Integer.parseInt(scanner.nextLine());

                        System.out.print("ID Facture : ");
                        int idFacture = Integer.parseInt(scanner.nextLine());

                        LigneFacture nouvelleLigne = new LigneFacture();
                        nouvelleLigne.setQuantite(quantite);
                        nouvelleLigne.setPrixUnitaire(prixUnitaire);
                        nouvelleLigne.setId_produit(idProduit);
                        nouvelleLigne.setId_facture(idFacture);

                        ligneService.ajouterLigne(nouvelleLigne);
                        System.out.println("Ligne ajoutée avec succès !");
                        break;

                    case 2:
                        System.out.print("ID de la ligne à modifier : ");
                        int idModif = Integer.parseInt(scanner.nextLine());

                        LigneFacture ligneModif = ligneService.getLigne(idModif);
                        if (ligneModif == null) {
                            System.out.println("Ligne introuvable !");
                            break;
                        }

                        System.out.print("Nouvelle quantité (" + ligneModif.getQuantite() + ") : ");
                        String newQuantStr = scanner.nextLine();
                        if (!newQuantStr.isEmpty()) ligneModif.setQuantite(Integer.parseInt(newQuantStr));

                        System.out.print("Nouveau prix unitaire (" + ligneModif.getPrixUnitaire() + ") : ");
                        String newPrixStr = scanner.nextLine();
                        if (!newPrixStr.isEmpty()) ligneModif.setPrixUnitaire(Integer.parseInt(newPrixStr));

                        System.out.print("Nouvel ID Produit (" + ligneModif.getId_produit() + ") : ");
                        String newIdProdStr = scanner.nextLine();
                        if (!newIdProdStr.isEmpty()) ligneModif.setId_produit(Integer.parseInt(newIdProdStr));

                        System.out.print("Nouvel ID Facture (" + ligneModif.getId_facture() + ") : ");
                        String newIdFactStr = scanner.nextLine();
                        if (!newIdFactStr.isEmpty()) ligneModif.setId_facture(Integer.parseInt(newIdFactStr));

                        ligneService.modifierLigne(ligneModif);
                        System.out.println("Ligne modifiée !");
                        break;

                    case 3:
                        System.out.print("ID de la ligne à supprimer : ");
                        int idSuppr = Integer.parseInt(scanner.nextLine());
                        ligneService.supprimerLigne(idSuppr);
                        System.out.println("Ligne supprimée !");
                        break;

                    case 4:
                        List<LigneFacture> lignes = ligneService.getToutesLignes();
                        System.out.println("\n=== Liste des lignes de facture ===");
                        for (LigneFacture l : lignes) {
                            System.out.println("ID: " + l.getId_ligne() +
                                               ", Quantité: " + l.getQuantite() +
                                               ", Prix unitaire: " + l.getPrixUnitaire() +
                                               ", ID Produit: " + l.getId_produit() +
                                               ", ID Facture: " + l.getId_facture());
                        }
                        break;

                    case 0:
                        System.out.println("Au revoir !");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Choix non reconnu.");
                }
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }
}
