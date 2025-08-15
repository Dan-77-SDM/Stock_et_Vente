package entities;

public class LigneFactureDTO {
    private int id_produit;
    private String designation;  // nom du produit
    private int quantite;
    private int prixUnitaire;
    private int totalLigne;

    // Constructeur
    public LigneFactureDTO(int id_produit, String designation, int quantite, int prixUnitaire) {
        this.id_produit = id_produit;
        this.designation = designation;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.totalLigne = quantite * prixUnitaire;
    }

    // Getters
    public int getId_produit() { return id_produit; }
    public String getDesignation() { return designation; }
    public int getQuantite() { return quantite; }
    public int getPrixUnitaire() { return prixUnitaire; }
    public int getTotalLigne() { return totalLigne; }
}
