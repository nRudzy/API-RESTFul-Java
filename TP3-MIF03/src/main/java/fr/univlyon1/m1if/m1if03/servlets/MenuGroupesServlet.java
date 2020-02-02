package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.io.IOException;

// Affiche un menu avec la liste des billets pour un groupe

@WebServlet(name = "MenuGroupe", urlPatterns = "/Groupe/*")
public class MenuGroupesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static String getLastBitFromUrl(final String url){
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/menuGroupe.jsp");
        dispatcher.forward(request, response);
//	    response.sendRedirect("menuGroupe.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("saisie.html");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/menuGroupe.jsp");

        String path = request.getPathInfo();
//        String idStr = path.substring(path.lastIndexOf('/') + 1);
        int indexGroupe = Integer.parseInt(getLastBitFromUrl(path));
	Groupe groupe = Groupe.getGroupe(indexGroupe);

        session.setAttribute("groupe", groupe);
        session.setAttribute("indexGroupe", indexGroupe);
//        response.sendRedirect("../menuGroupe.jsp");
        dispatcher.forward(request, response);
        //request.getRequestDispatcher("billet.jsp").forward(request, response);
    }
}

