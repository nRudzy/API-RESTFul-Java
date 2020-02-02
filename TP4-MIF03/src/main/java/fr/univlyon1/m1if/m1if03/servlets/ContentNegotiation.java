package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ContentNegotiation")
public class ContentNegotiation implements Filter {
    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        String view = (String) req.getAttribute("view");
        // S'il y a un contenu à renvoyer
        if (view != null) {
            // TODO implémenter ici la négociation de contenus

            if (((HttpServletRequest) req).getHeader("Accept").startsWith("text/html")) {
                // Cas des JSP (nommées dans le web.xml)
                RequestDispatcher dispatcher = filterConfig.getServletContext().getNamedDispatcher(view + "-vue");
                HttpServletRequest wrapped = new HttpServletRequestWrapper((HttpServletRequest) req) {
                    public String getServletPath() {
                        return "";
                    }
                };
                dispatcher.forward(wrapped, resp);
            }
        } else {
            //RequestDispatcher dispatcher = filterConfig.getServletContext().getNamedDispatcher("/groupes/all");
            //dispatcher.forward(req, resp);

        }
    }
}
