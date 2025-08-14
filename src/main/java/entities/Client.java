package entities;

public class Client {
    private int id_client;
    private String nom;
    private String email;
    private int numero;

    public Client() {}

    public Client(int id_client, String nom, String email, int numero) {
        this.id_client = id_client;
        this.nom = nom;
        this.email = email;
        this.numero = numero;
    }

    public Client(String nom, String email, int numero) {
        this.nom = nom;
        this.email = email;
        this.numero = numero;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
