package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.GestionBillets;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SaisieBillet", urlPatterns = "/SaisieBillet")
public class SaisieBilletServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/menu.jsp");
        Billet billet = new Billet();
        billet.setAuteur((String)session.getAttribute("pseudo"));
        billet.setTitre(request.getParameter("titre"));
        billet.setContenu(request.getParameter("contenu"));
	billet.persist();
        session.setAttribute("sBillet",billet);

        dispatcher.forward(request, response);

//        response.sendRedirect("menu.jsp");
        //request.getRequestDispatcher("menu.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("saisie.html");
    }
}
