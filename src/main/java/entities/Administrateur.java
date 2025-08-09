package entities;

public class Administrateur {
    private int id;
    private String nom;
    private String email;
    private String nom_utilisateur;
    private String mot_de_passe;

    // Constructeurs
    public Administrateur() {
    }

    public Administrateur(String nom, String email, String nomUtilisateur, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.nom_utilisateur = nomUtilisateur;
        this.mot_de_passe = motDePasse;
    }

    public Administrateur(int id, String nom, String email, String nomUtilisateur, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.nom_utilisateur = nomUtilisateur;
        this.mot_de_passe = motDePasse;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nom_utilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nom_utilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return mot_de_passe;
    }

    public void setMotDePasse(String motDePasse) {
        this.mot_de_passe = motDePasse;
    }
}
