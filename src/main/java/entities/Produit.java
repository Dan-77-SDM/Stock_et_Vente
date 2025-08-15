package entities;

public class Produit {
    private int id_produit;
    private String designation;
    private double prix_Unitaire;
    private int stock;
    
    // Relation avec le fournisseur (optionnel)
    private Integer id_fournisseur; // pour la BD
    private Fournisseur fournisseur; // pour Thymeleaf et la logique métier

    // Pour le dashboard : nombre de ventes
    private int ventes;

    // Constructeurs
    public Produit() {}

    public Produit(String designation, double prix_Unitaire, int stock, Integer id_fournisseur) {
        this.designation = designation;
        this.prix_Unitaire = prix_Unitaire;
        this.stock = stock;
        this.id_fournisseur = id_fournisseur;
    }

    public Produit(int id_produit, String designation, double prix_Unitaire, int stock, Integer id_fournisseur) {
        this.id_produit = id_produit;
        this.designation = designation;
        this.prix_Unitaire = prix_Unitaire;
        this.stock = stock;
        this.id_fournisseur = id_fournisseur;
    }

    // Getters et setters
    public int getId_produit() { return id_produit; }
    public void setId_produit(int id_produit) { this.id_produit = id_produit; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getPrix_Unitaire() { return prix_Unitaire; }
    public void setPrix_Unitaire(double prix_Unitaire) { this.prix_Unitaire = prix_Unitaire; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Integer getId_fournisseur() { return id_fournisseur; }
    public void setId_fournisseur(Integer id_fournisseur) { this.id_fournisseur = id_fournisseur; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    // Getter/Setter pour ventes
    public int getVentes() { return ventes; }
    public void setVentes(int ventes) { this.ventes = ventes; }
}
