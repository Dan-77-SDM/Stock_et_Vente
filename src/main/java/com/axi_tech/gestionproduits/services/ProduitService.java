package com.axi_tech.gestionproduits.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.axi_tech.gestionproduits.entities.Produit;
import com.axi_tech.gestionproduits.repository.ProduitRepository;

@Service
public class ProduitService {
private ProduitRepository produitRepository;

public ProduitService(ProduitRepository produitRepository) {
this.produitRepository = produitRepository;
}
public void EnregistrerProduit(Produit produit) {
    produitRepository.save(produit);
}
public void SupprimerProduit(Long id) {
    produitRepository.deleteById(id);   
}
public Produit TrouverProduitParId(Long id) {
    return produitRepository.findById(id).orElse(null);     
}
public List<Produit> findAll(){
    return produitRepository.findAll();
}
}
