package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "Init", urlPatterns = "/Init")
public class Init extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletConfig config;

    public void init(ServletConfig _config) throws ServletException {
	this.config = _config;
	ServletContext sc = config.getServletContext();
	HashMap<String,Groupe> groupes = new HashMap<>();
	for (int i = 0; i < Groupe.getGroupes().size() ; i++) {
		groupes.put(Integer.toString(i),Groupe.getGroupe(i));
	}
	sc.setAttribute("groupes",groupes);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession(true);
	String pseudo = (String) session.getAttribute("pseudo");
	super.init(config);
	getServletContext().setAttribute("pseudo",pseudo);
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/menuPrincipal.jsp");


	// check si l'user est deja connecte
        //if(pseudo != null && !pseudo.equals("")) {
	    //String strGroupe = request.getParameter("nom_groupe");
	    //if(strGroupe != null && !strGroupe.equals("")) {
		//Groupe groupe;

		//if(strGroupe.equals("")) {
		    //strGroupe = "all";
		//}

		//groupe = Groupe.getGroupe(strGroupe);
		
		//if(groupe == null) {
		    //groupe = new Groupe(strGroupe, "description", pseudo);
		    //groupe.persist();
		//}

		//session.setAttribute("groupe", groupe);
	    //}

	    //dispatcher.forward(request, response);
////	    response.sendRedirect("menuPrincipal.jsp");
	//} else {
	    //pseudo = request.getParameter("pseudo");
	    //String strGroupe = request.getParameter("groupe");
	    

	    ////check si les parametres POST sont correctes
	    //if(pseudo != null && !pseudo.equals("")) {
		//session.setAttribute("pseudo", pseudo);

		//Groupe groupe;

		//if(strGroupe.equals("")) {
		    //strGroupe = "all";
		//}

		//groupe = Groupe.getGroupe(strGroupe);

		//if(groupe == null) {
		    //groupe = new Groupe(strGroupe, "description", pseudo);
		    //groupe.persist();
		//}

		//session.setAttribute("groupe", groupe);

			//dispatcher.forward(request, response);
////			response.sendRedirect("menuGroupe.jsp");
	    //} else {
		//response.sendRedirect("index.html");
	    //}
	    
	//}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
		response.sendRedirect("index.html");
    }
}
