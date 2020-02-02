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

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

@WebServlet(name = "commentaires", urlPatterns = "/groupes/*/billets/*/commentaire/*")
public class CommentaireServlet extends HttpServlet {
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
        } catch (URISyntaxException ignored) {
        }
        return res;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commentaire = request.getParameter("commentaire");

        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        // TODO: verifier les inputs

        String strGroupe = segments[3];
        Groupe groupe = Groupe.getGroupe(strGroupe);

        int indexBillet = Integer.parseInt(segments[5]);
        Billet billet = GestionBillets.getBillet(groupe, indexBillet);
        billet.addCommentaire(commentaire);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BilletDTO billetDTO = new BilletDTO(billet);
        String billetJsonString = this.gson.toJson(billetDTO);
        out.print(billetJsonString);

        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriStr = request.getRequestURI();
        String[] segments = this.parseUri(uriStr);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String strGroupe = segments[3];
        Groupe groupe = Groupe.getGroupe(strGroupe);

        int indexBillet = Integer.parseInt(segments[5]);
        Billet billet = GestionBillets.getBillet(groupe, indexBillet);

        // on a pas de billetId dans l'url || TODO: peut etre trouver un meileur moyen de verifier
        if (segments.length == 7) {
            List<String> commentaires = billet.getCommentaires();
            String commentairesJsonString = this.gson.toJson(commentaires);
            out.print(commentairesJsonString);

            // on a un billetId dans l'url
        } else if (segments.length == 8) {
            int indexCommentaires = Integer.parseInt(segments[7]);
            String commentaires = billet.getCommentaires().get(indexCommentaires);
            String commentaireJsonString = this.gson.toJson(commentaires);
            out.print(commentaireJsonString);
        }

        out.flush();
    }
}


