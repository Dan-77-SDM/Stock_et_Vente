package entities;

import java.sql.Date;

public class Facture {
    private int id_facture;
    private Date date;
    private double total; // correspond à DECIMAL(10,2)
    private Integer id_client; // nullable

    // Getters et Setters
    public int getId_facture() { return id_facture; }
    public void setId_facture(int id_facture) { this.id_facture = id_facture; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Integer getId_client() { return id_client; }
    public void setId_client(Integer id_client) { this.id_client = id_client; }
}
