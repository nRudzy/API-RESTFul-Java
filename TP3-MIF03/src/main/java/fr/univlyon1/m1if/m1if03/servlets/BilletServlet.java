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

@WebServlet(name = "Billet", urlPatterns = "/Billet")
public class BilletServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static long getLastModifiedMillis () {
        //Using hard coded value, in real scenario there should be for example
        // last modified date of this servlet or of the underlying resource
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2017, 1, 8,
                10, 30, 20),
                ZoneId.of("GMT"));
        return zdt.toInstant().toEpochMilli();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/menuGroupe.jsp");
	String titre = request.getParameter("titre");
	String contenu = request.getParameter("contenu");

	String commentaire = request.getParameter("commentaire");

	if(titre != null && !titre.equals("")){
	    Billet billet = new Billet();
	    billet.setAuteur((String)session.getAttribute("pseudo"));
	    billet.setTitre(request.getParameter("titre"));
	    billet.setContenu(request.getParameter("contenu"));
	    billet.setGroupe((Groupe) session.getAttribute("groupe"));
	    billet.persist();
	    session.setAttribute("sBillet", billet);
	} else if(commentaire != null && !commentaire.equals("")) {
	    Billet billet = (Billet) session.getAttribute("sBillet");
	    billet.addCommentaire(commentaire);
	}

        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("saisie.html");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/billet.jsp");

        long lastModifiedFromBrowser = request.getDateHeader("If-Modified-Since");
        long lastModifiedFromServer = getLastModifiedMillis();

        if (lastModifiedFromBrowser != -1 &&
                lastModifiedFromServer <= lastModifiedFromBrowser) {
            //setting 304 and returning with empty body
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        response.addDateHeader("Last-Modified", lastModifiedFromServer);

        int indexBillet = Integer.parseInt(request.getPathInfo().substring(1));
	Groupe groupe = (Groupe) session.getAttribute("groupe");
	Billet billet = GestionBillets.getBillet(groupe, indexBillet);

        session.setAttribute("billet", billet);

        dispatcher.forward(request, response);
    }
}

