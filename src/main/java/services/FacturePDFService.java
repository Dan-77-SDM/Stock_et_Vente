package services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import entities.Facture;
import entities.LigneFactureDTO;

import java.io.OutputStream;
import java.util.List;

public class FacturePDFService {

    // Génère la facture directement dans le flux (HttpServletResponse)
    public void genererFactureDansFlux(Facture facture, List<LigneFactureDTO> lignes, OutputStream outputStream) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // ===== Titre =====
            Font titreFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titre = new Paragraph("AXIA-Electronique", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Facture N° " + facture.getId_facture()));
            document.add(new Paragraph("Client : " + facture.getId_client()));
            document.add(new Paragraph("Date : " + facture.getDate()));
            document.add(new Paragraph(" "));

            // ===== Tableau produits =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("ID Produit");
            table.addCell("Désignation");
            table.addCell("Quantité");
            table.addCell("Prix Unitaire");
            table.addCell("Total Ligne");

            double totalFacture = 0;
            for (LigneFactureDTO lf : lignes) {
                table.addCell(String.valueOf(lf.getId_produit()));
                table.addCell(lf.getDesignation());
                table.addCell(String.valueOf(lf.getQuantite()));
                table.addCell(String.valueOf(lf.getPrixUnitaire()));
                table.addCell(String.valueOf(lf.getTotalLigne()));

                totalFacture += lf.getTotalLigne();
            }

            document.add(table);

            document.add(new Paragraph(" "));
            Font totalFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Paragraph totalParagraphe = new Paragraph("Total : " + totalFacture, totalFont);
            totalParagraphe.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraphe);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Merci de faire confiance à AXIA-Electronique"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
