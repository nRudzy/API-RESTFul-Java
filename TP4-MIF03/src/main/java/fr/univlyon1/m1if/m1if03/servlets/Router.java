package fr.univlyon1.m1if.m1if03.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m1if.m1if03.servlets.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//@WebServlet(name = "Router", urlPatterns = "/*")
public class Router extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        //context.addServlet("groupes", "fr.univlyon1.m1if.m1if03.classes.Groupe");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }

    /**
     * Traitement du routage (commun pour toutes les méthodes HTTP).
     * Dispatche la requête aux contrôleurs de CU délégués.
     * Ces contrôleurs délégués sont des servlets et doivent avoir un nom enregistré dans le contexte applicatif (<code>@WebServlet(name="nom")</code>) correspondant à leur chemin dans l'URL.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;

        // On décompose l'URL
        String[] path = request.getRequestURI().split("/");
        // Les 2 premières parties sont serveur et chemin de l'application -> on s'intéresse à la suite
        if (path.length > 2) { // l'URL est complète
            dispatcher = request.getServletContext().getNamedDispatcher(path[2]);
            if (dispatcher != null) { // la servlet est référencée dans le contexte par son nom
                dispatcher.include(request, response);
            } else { // renvoi de fichiers statiques
                // cf. https://stackoverflow.com/questions/132052/servlet-for-serving-static-content
                dispatcher = getServletContext().getNamedDispatcher("default");

                HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
                    public String getServletPath() {
                        return "";
                    }
                };

                dispatcher.forward(wrapped, response);
            }
        } else { // Page d'accueil
            dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.forward(request, response);
        }
    }
}
