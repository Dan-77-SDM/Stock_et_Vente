package services;

import entities.Produit;
import repository.ProduitRepository;
import repository.LigneFactureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProduitService {

    private ProduitRepository produitRepository = new ProduitRepository();
    private LigneFactureRepository ligneRepo = new LigneFactureRepository();

    public void ajouterProduit(Produit p) {
        produitRepository.ajouterProduit(p);
    }

    public void modifierProduit(Produit p) {
        produitRepository.modifierProduit(p);
    }

    public void supprimerProduit(int id) {
        produitRepository.supprimerProduit(id);
    }

    public List<Produit> getToutesProduits() {
        return produitRepository.listerProduits();
    }

    public Produit trouverProduitParId(int id) {
        return produitRepository.trouverProduitParId(id);
    }

    /**
     * Récupère le top N produits les plus vendus.
     */
   public List<Produit> getTopProduitsVendus(int topN) {
    List<Map<String,Object>> rawTop = ligneRepo.topProduitsVendus(topN);
    List<Produit> topProduits = new ArrayList<>();

    for (Map<String,Object> map : rawTop) {
        Produit p = produitRepository.trouverProduitParId((Integer) map.get("id_produit"));
        p.setVentes((Integer) map.get("totalVentes"));
        topProduits.add(p);
    }

    return topProduits;
}


    /**
     * Somme totale des stocks pour le dashboard
     */
    public int getTotalStock() {
        return produitRepository.listerProduits().stream().mapToInt(Produit::getStock).sum();
    }

    /**
     * Nombre de produits en rupture de stock
     */
    public int getProduitsRuptureStock() {
        return (int) produitRepository.listerProduits().stream().filter(p -> p.getStock() <= 0).count();
    }
}
