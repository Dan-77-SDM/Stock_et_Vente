package entities;

public class LigneFacture {
    private int id_ligne;
    private int quantite;
    private int prixUnitaire;
    private int id_produit;
    private int id_facture;

    // Getters & Setters
    public int getId_ligne() { return id_ligne; }
    public void setId_ligne(int id_ligne) { this.id_ligne = id_ligne; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public int getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(int prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public int getId_produit() { return id_produit; }
    public void setId_produit(int id_produit) { this.id_produit = id_produit; }

    public int getId_facture() { return id_facture; }
    public void setId_facture(int id_facture) { this.id_facture = id_facture; }
}
