package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import fr.univlyon1.m1if.m1if03.classes.Groupe;

public class BilletFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("BilletFilter initialisé.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);

        String pseudo = (String) session.getAttribute("pseudo");
        Groupe groupe = (Groupe) session.getAttribute("groupe");
        int indexGroupe = (int) session.getAttribute("indexGroupe");

        if (groupe.isAParticipant(pseudo)) {
            //res.sendRedirect("Init");
            chain.doFilter(request, response);
        } else {
            //TODO: fix pour que ce soit pas un lien hardcode
            res.sendRedirect("/tp3/Groupe/" + indexGroupe);
        }

    }

    @Override
    public void destroy() {

    }
}
