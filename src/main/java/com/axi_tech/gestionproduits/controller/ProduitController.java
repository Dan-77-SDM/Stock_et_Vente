package com.axi_tech.gestionproduits.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.axi_tech.gestionproduits.entities.Produit;
import com.axi_tech.gestionproduits.services.ProduitService;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    private ProduitService produitService;
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
 @GetMapping("/form")
 public String Formulaire(Model model){
        Produit produit = new Produit();
        model.addAttribute("produit", produit);
        return "formulaire";


 }
@PostMapping("/add")
public String ajouterProduit(Model model , Produit produit){
    produitService.EnregistrerProduit(produit);
    return "formulaire";
}
@GetMapping("/liste")
public String listeProduit(Model model ){
    List <Produit> produitList = produitService.findAll();
}


}
