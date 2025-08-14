package entities;

import java.sql.Date;

public class Facture {
    private int id_facture;
    private Date date; // java.sql.Date pour correspondre au type SQL
    private int total;
    private Integer id_client; // Nullable, donc on utilise Integer

    public Facture() {
    }

    public Facture(int id_facture, Date date, int total, Integer id_client) {
        this.id_facture = id_facture;
        this.date = date;
        this.total = total;
        this.id_client = id_client;
    }

    // Getters et Setters
    public int getId_facture() {
        return id_facture;
    }

    public void setId_facture(int id_facture) {
        this.id_facture = id_facture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id_facture=" + id_facture +
                ", date=" + date +
                ", total=" + total +
                ", id_client=" + id_client +
                '}';
    }
}
