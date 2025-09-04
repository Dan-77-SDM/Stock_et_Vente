package com.axi_tech.gestionproduits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axi_tech.gestionproduits.entities.Produit;

public interface ProduitRepository  extends JpaRepository<Produit, Long> {
public Produit findByNom(String nom);
}
