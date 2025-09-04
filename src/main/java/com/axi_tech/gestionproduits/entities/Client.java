package com.axi_tech.gestionproduits.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Client {
 
    private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String nom;
    private String email;
    @OneToMany(mappedBy = "client", cascade =CascadeType.ALL)
    private List<Commande> commandes;

    // Getters and Setters
}
