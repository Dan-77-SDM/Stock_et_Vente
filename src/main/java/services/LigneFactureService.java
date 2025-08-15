package services;

import entities.LigneFacture;
import entities.Facture;
import entities.Produit;
import repository.LigneFactureRepository;
import repository.FactureRepository;
import repository.ProduitRepository;

import java.util.List;

public class LigneFactureService {

    private LigneFactureRepository repo = new LigneFactureRepository();
    private FactureRepository factureRepo = new FactureRepository();
    private ProduitRepository produitRepo = new ProduitRepository();

    public void ajouterLigne(LigneFacture lf) {
        // Ajouter la ligne de facture
        repo.ajouter(lf);

        // Mettre à jour le total de la facture
        Facture facture = factureRepo.trouverParId(lf.getId_facture());
        double totalLigne = lf.getQuantite() * lf.getPrixUnitaire();
        facture.setTotal(facture.getTotal() + totalLigne);
        factureRepo.modifier(facture);

        // Mettre à jour le stock du produit
        Produit produit = produitRepo.trouverProduitParId(lf.getId_produit());
        produit.setStock(produit.getStock() - lf.getQuantite());
        produitRepo.modifierProduit(produit);

        // Vérifier le seuil de rupture
        if (produit.getStock() < 10) {
            System.out.println("Attention : produit en rupture ou faible stock !");
        }
    }

    public void modifierLigne(LigneFacture lf) {
        repo.modifier(lf);
    }

    public void supprimerLigne(int id) {
        repo.supprimer(id);
    }

    public LigneFacture getLigne(int id) {
        return repo.trouverParId(id);
    }

    public List<LigneFacture> getToutesLignes() {
        return repo.lister();
    }

    // Méthode pour top produits vendus
    public List<java.util.Map<String,Object>> getTopProduitsVendus(int limit) {
        return repo.topProduitsVendus(limit);
    }
}
