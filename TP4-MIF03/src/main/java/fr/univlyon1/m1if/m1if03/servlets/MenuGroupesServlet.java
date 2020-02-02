package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.GroupLayout.Group;

import fr.univlyon1.m1if.m1if03.classes.Groupe;
import fr.univlyon1.m1if.m1if03.classes.Billet;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.univlyon1.m1if.m1if03.dto.GroupeDTO;

import java.util.Enumeration;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

// Affiche un menu avec la liste des billets pour un groupe

@WebServlet(name = "groupes")//, urlPatterns = "/groupes/*")
public class MenuGroupesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();

    public static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);

	String pseudo = (String) request.getAttribute("pseudo");

	if (pseudo == null || pseudo.equals(""))
	    pseudo = "anon";


        if (segments.length == 3) {
            String nom = request.getParameter("nom");
            String description = request.getParameter("description");
            Groupe groupe = Groupe.getGroupe(nom);
            if (groupe == null) {
                groupe = new Groupe(nom, description, pseudo);
                groupe.persist();
            }

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String strGroupeJson = this.gson.toJson(groupe);
            out.print(strGroupeJson);

            out.flush();
        } else {
            //out.print("erreur d'url");

            // gerer les billets
            RequestDispatcher dispatcher = request.getServletContext().getNamedDispatcher(segments[4]);
            dispatcher.forward(request, response);
        }
    }

    protected String[] parseUri(String uriStr) {
        String[] res = null;
        try {
            URI uri = new URI(uriStr);
            res = uri.getPath().split("/");
        } catch (URISyntaxException ignored) {
        }
        return res;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);


        // on a un groupeId dans l'url || TODO: peut etre trouver un meileur moyen de verifier
        if (segments.length == 4) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String strGroupe = segments[3];

            String groupeJsonString = this.gson.toJson(Groupe.getGroupe(strGroupe));

            out.print(groupeJsonString);
            out.flush();

            // on a pas de groupeId dans l'url
        } else if (segments.length == 3) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String groupesJsonString = this.gson.toJson(Groupe.getGroupes());
            out.print(groupesJsonString);
            out.flush();
        } else {
            // gerer les billets
            RequestDispatcher dispatcher = request.getServletContext().getNamedDispatcher(segments[4]);
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriStr = req.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        String strGroupe = segments[3];
        Groupe groupe = Groupe.getGroupe(strGroupe);

        if (segments.length == 4) {
            String nom = req.getParameter("nom");
            String description = req.getParameter("description");

            groupe.setNom(nom);
            groupe.setDescription(description);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            GroupeDTO groupeDTO = new GroupeDTO(groupe);
            String groupeJsonString = this.gson.toJson(groupeDTO);
            out.print(groupeJsonString);
            out.flush();
        } else {
            // gerer les billets
            RequestDispatcher dispatcher = req.getServletContext().getNamedDispatcher(segments[4]);
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriStr = req.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        if (segments.length == 4) {
            String strGroupe = segments[3];
            Groupe groupe = Groupe.getGroupe(strGroupe);

            Groupe.getGroupes().remove(groupe);
        } else {
            // gerer les billets
            RequestDispatcher dispatcher = req.getServletContext().getNamedDispatcher(segments[4]);
            dispatcher.forward(req, resp);
        }
    }
}

