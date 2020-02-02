package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;
import fr.univlyon1.m1if.m1if03.classes.GestionBillets;
import fr.univlyon1.m1if.m1if03.classes.Billet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.univlyon1.m1if.m1if03.dto.BilletDTO;
import fr.univlyon1.m1if.m1if03.dto.GroupeDTO;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

@WebServlet(name = "billets", urlPatterns = "/groupes/*/billets/*")
public class BilletServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();

    private static long getLastModifiedMillis() {
        //Using hard coded value, in real scenario there should be for example
        // last modified date of this servlet or of the underlying resource
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2017, 1, 8,
                10, 30, 20),
                ZoneId.of("GMT"));
        return zdt.toInstant().toEpochMilli();
    }

    protected String[] parseUri(String uriStr) {
        String[] res = null;
        try {
            URI uri = new URI(uriStr);
            res = uri.getPath().split("/");
        } catch (URISyntaxException e) {
        }
        return res;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String contenu = request.getParameter("contenu");

        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);


        // TODO: verifier les inputs

        if (segments.length == 5) {
	    String auteur = (String) request.getAttribute("pseudo");

	    //if (auteur == null || auteur.equals(""))
		//auteur = "anon";

            Billet billet = new Billet();
            billet.setAuteur(auteur);

            if (titre != null && !titre.equals(""))
                billet.setTitre(titre);

            if (titre != null && !titre.equals(""))
                billet.setContenu(contenu);

            String strGroupe = segments[3];
            Groupe groupe = Groupe.getGroupe(strGroupe);

            if (groupe == null) {
                groupe = new Groupe(strGroupe, "description", auteur);
                groupe.persist();
            }

            billet.setGroupe(groupe);

            billet.persist();

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            BilletDTO billetDTO = new BilletDTO(billet);
            String billetJsonString = this.gson.toJson(billetDTO);
            out.print(billetJsonString);

            out.flush();
        } else {
            RequestDispatcher dispatcher = request.getServletContext().getNamedDispatcher(segments[6]);
            dispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //out.print(segments.length);
        //String segmentJsonString = this.gson.toJson(segments);
        //out.print(segmentJsonString);
        //out.print("");

        String strGroupe = segments[3];
        Groupe groupe = Groupe.getGroupe(strGroupe);

        //String groupeJsonString = this.gson.toJson(groupe);
        //out.print(groupeJsonString);

        //out.print("");

        // on a pas de billetId dans l'url || TODO: peut etre trouver un meileur moyen de verifier
        if (segments.length == 5) {
            List<Billet> billets = GestionBillets.getBillets(groupe);
            String billestJsonString = this.gson.toJson(billets);
            out.print(billestJsonString);

            // on a un billetId dans l'url
        } else if (segments.length == 6) {
            int indexBillet = Integer.parseInt(segments[5]);
            Billet billet = GestionBillets.getBillet(groupe, indexBillet);
            BilletDTO billetDTO = new BilletDTO(billet);
            String billestJsonString = this.gson.toJson(billetDTO);
            out.print(billestJsonString);
        } else {
            RequestDispatcher dispatcher = request.getServletContext().getNamedDispatcher(segments[6]);
            dispatcher.forward(request, response);
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriStr = req.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        String strGroupe = segments[3];
        Groupe groupe = Groupe.getGroupe(strGroupe);

        int indexBillet = Integer.parseInt(segments[5]);
        Billet billet = GestionBillets.getBillet(groupe, indexBillet);

        if (segments.length == 6) {
            String titre = req.getParameter("titre");
            String contenu = req.getParameter("contenu");
            String newStrGroupe = req.getParameter("groupe");

            if (titre != null && !titre.equals(""))
                billet.setTitre(titre);

            if (contenu != null && !contenu.equals(""))
                billet.setContenu(contenu);

            if (newStrGroupe != null && !newStrGroupe.equals("")) {
                Groupe newGroupe = Groupe.getGroupe(newStrGroupe);
                billet.setGroupe(newGroupe);
            }

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

        if (segments.length == 6) {
            String strGroupe = segments[3];
            Groupe groupe = Groupe.getGroupe(strGroupe);

            int indexBillet = Integer.parseInt(segments[5]);
            Billet billet = GestionBillets.getBillet(groupe, indexBillet);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String groupeJsonString = this.gson.toJson(groupe);
            out.print(groupeJsonString);

            String billetJsonString = this.gson.toJson(billet);
            out.print(billetJsonString);

            out.flush();

            GestionBillets.getBillets().remove(billet);
        } else {
            // gerer les billets
            RequestDispatcher dispatcher = req.getServletContext().getNamedDispatcher(segments[6]);
            dispatcher.forward(req, resp);
        }
    }

}

