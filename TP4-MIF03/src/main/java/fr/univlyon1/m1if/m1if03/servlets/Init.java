package fr.univlyon1.m1if.m1if03.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import fr.univlyon1.m1if.m1if03.dto.BilletDTO;

import java.io.PrintWriter;

@WebServlet(name = "Init")
public class Init extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletConfig config;
    private Gson gson = new Gson();

    public void init(ServletConfig _config) throws ServletException {
        this.config = _config;
        ServletContext sc = config.getServletContext();
        HashMap<String, Groupe> groupes = new HashMap<>();
        for (int i = 0; i < Groupe.getGroupes().size(); i++) {
            groupes.put(Integer.toString(i), Groupe.getGroupe(i));
        }
        sc.setAttribute("groupes", groupes);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        if (pseudo == null) {
            pseudo = (String) request.getAttribute("pseudo");
        }
        super.init(config);
        getServletContext().setAttribute("pseudo", pseudo);
        request.setAttribute("pseudo", pseudo);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/menuPrincipal-vue");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Billet billet = new Billet("mytitre", "lorem ipsum", "jojo", new Groupe("all", "la description", "jojo"));
        BilletDTO billetDTO = new BilletDTO(billet);
        String billetJsonString = this.gson.toJson(billetDTO);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(billetJsonString);
        out.flush();
    }
}
