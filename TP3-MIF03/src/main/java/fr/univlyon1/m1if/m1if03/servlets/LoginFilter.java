package fr.univlyon1.m1if.m1if03.servlets;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        context.log("AuthenticationFilter initialisé.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session;

        if(req.getAttribute("pseudo") != null){
            chain.doFilter(request, response);
        }
        else if (req.getMethod().equals("POST"))
        {
            // On rappelle ici le doPost de Init en l'adaptant à notre filtre
            session = req.getSession(true);
            String pseudo = (String) session.getAttribute("pseudo");

            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/menuPrincipal.jsp");


            if(pseudo != null && !pseudo.equals("")) {
                String strGroupe = request.getParameter("nom_groupe");
                if(strGroupe != null && !strGroupe.equals("")) {
                    Groupe groupe;

                    if(strGroupe.equals("")) {
                        strGroupe = "all";
                    }

                    groupe = Groupe.getGroupe(strGroupe);

                    if(groupe == null) {
                        groupe = new Groupe(strGroupe, "description", pseudo);
                        groupe.persist();
			groupe.getParticipants().add(pseudo);
		    }

		    if(!groupe.isAParticipant(pseudo)) {
			//groupe.getParticipants().add(pseudo);
		    }

                    session.setAttribute("groupe", groupe);
                }

                dispatcher.forward(request, response);
            } else {
                pseudo = (String )request.getParameter("pseudo");
                String strGroupe = request.getParameter("groupe");


                if(pseudo != null && !pseudo.equals("")) {
                    session.setAttribute("pseudo", pseudo);

                    if(strGroupe.equals("")) {
                        strGroupe = "all";
                    }

                    Groupe groupe = Groupe.getGroupe(strGroupe);

                    if(groupe == null) {
                        groupe = new Groupe(strGroupe, "description", pseudo);
                        groupe.persist();
			groupe.getParticipants().add(pseudo);
		    } else {
			if(!groupe.isAParticipant(pseudo)) {
			    groupe.getParticipants().add(pseudo);
			}
		    }

                    session.setAttribute("groupe", groupe);

                    dispatcher.forward(request, response);
                } else {
                    res.sendRedirect("index.html");
                }

            }
        }
        else {
            session = req.getSession();
            session.invalidate();
            res.sendRedirect("index.html");
        }
    }

    @Override
    public void destroy() {
    }
}
