package controller;

import entities.Client;
import services.ClientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utility.ThymeleafConfig;

import java.io.IOException;

@WebServlet("/clients/*")
public class ClientController extends HttpServlet {

    private ClientService clientService;

    @Override
    public void init() {
        clientService = new ClientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo(); // /editer/1, /supprimer/1, ou null

        if (path == null || path.equals("/")) {
            // Page clients : tableau + formulaire vide
            request.setAttribute("clients", clientService.listerClients());
            request.setAttribute("client", new Client());
            ThymeleafConfig.render(request, response, "client");
        } else if (path.startsWith("/editer/")) {
            // Pré-remplir le formulaire pour modification
            int id = Integer.parseInt(path.substring("/editer/".length()));
            Client client = clientService.trouverClientParId(id);
            request.setAttribute("clients", clientService.listerClients());
            request.setAttribute("client", client);
            ThymeleafConfig.render(request, response, "client");
        } else if (path.startsWith("/supprimer/")) {
            int id = Integer.parseInt(path.substring("/supprimer/".length()));
            clientService.supprimerClient(id);
            response.sendRedirect(request.getContextPath() + "/clients");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    
   @Override

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        String idStr = request.getParameter("id_client");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String numeroStr = request.getParameter("numero");

        System.out.println("Paramètres reçus : id_client=" + idStr + ", nom=" + nom + ", email=" + email + ", numero=" + numeroStr);

        int numero = 0; // valeur par défaut
        if (numeroStr != null && !numeroStr.isEmpty()) {
            try {
                numero = Integer.parseInt(numeroStr);
            } catch (NumberFormatException nfe) {
                System.out.println("Numero invalide : " + numeroStr + ", utilisation de 0 par défaut");
            }
        }

        Client client = new Client();
        client.setNom(nom);
        client.setEmail(email);
        client.setNumero(numero);

        String message = "";

        if (idStr != null && !idStr.isEmpty() && !idStr.equals("0")) {
            // Modification
            client.setId_client(Integer.parseInt(idStr));
            clientService.modifierClient(client);
            message = "Client modifié avec succès !";
        } else {
            // Ajout
            clientService.ajouterClient(client);
            message = "Client ajouté avec succès !";
        }

        // Recharger la page clients avec message
        request.setAttribute("clients", clientService.listerClients());
        request.setAttribute("client", new Client()); // formulaire vide
        request.setAttribute("message", message);

        ThymeleafConfig.render(request, response, "client");

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("clients", clientService.listerClients());
        request.setAttribute("client", new Client());
        request.setAttribute("message", "Erreur serveur : " + e.getMessage());
        ThymeleafConfig.render(request, response, "client");
    }
}


  }