package services;

import entities.Produit;
import repository.ProduitRepository;
import java.util.List;

public class ProduitService {

    private ProduitRepository produitRepository;

    public ProduitService() {
        this.produitRepository = new ProduitRepository();
    }

    public void ajouterProduit(Produit p) {
        produitRepository.ajouterProduit(p);
    }

    public void modifierProduit(Produit p) {
        produitRepository.modifierProduit(p);
    }

    public void supprimerProduit(int id) {
        produitRepository.supprimerProduit(id);
    }

    public List<Produit> listerProduits() {
        return produitRepository.listerProduits();
    }

    public Produit trouverProduitParId(int id) {
        return produitRepository.trouverProduitParId(id);
    }
}
