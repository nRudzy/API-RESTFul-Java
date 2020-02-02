package fr.univlyon1.m1if.m1if03.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import fr.univlyon1.m1if.m1if03.classes.Groupe;
import fr.univlyon1.m1if.m1if03.dto.GroupeDTO;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        if (req.getAttribute("pseudo") != null) {
            chain.doFilter(request, response);
        } else if (req.getMethod().equals("POST")) {
            // On rappelle ici le doPost de Init en l'adaptant à notre filtre
            String pseudo = request.getParameter("pseudo");
            String strGroupe = request.getParameter("groupe");

            if (pseudo != null && !pseudo.equals("")) {

                // Création du token
                String tokenWeb;
                try {
                    Algorithm algorithm = Algorithm.HMAC256("secret");
                    tokenWeb = JWT.create()
                            .withSubject(pseudo)
                            .withIssuer("auth0")
                            .sign(algorithm);
                    Cookie tokenCookie = new Cookie("tokenCookie", tokenWeb);
                    tokenCookie.setMaxAge(60 * 60 * 24);
                    res.addCookie(tokenCookie);

                } catch (JWTCreationException ignored) {
                }

                if (strGroupe.equals(""))
                    strGroupe = "all";

                Groupe groupe = Groupe.getGroupe(strGroupe);

                if (groupe == null) {
                    groupe = new Groupe(strGroupe, "description", pseudo);
                    groupe.persist();
                    groupe.getParticipants().add(pseudo);
                } else {
                    if (!groupe.isAParticipant(pseudo)) {
                        groupe.getParticipants().add(pseudo);
                    }
                }

                request.setAttribute("groupe", groupe);

                chain.doFilter(request, response);
            } else {
                Cookie[] cookies = req.getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("tokenCookie")) {

                            String pseudoReq = null;

                            ////Validation du token
                            try {
                                Algorithm algorithm = Algorithm.HMAC256("secret");
                                JWTVerifier verifier = JWT.require(algorithm)
                                        .withIssuer("auth0")
                                        .build();
                                DecodedJWT jwt = verifier.verify(cookie.getValue());
                                pseudoReq = jwt.getSubject();
                            } catch (JWTVerificationException exception) {
                                // Si event indésirable (jwt erroné), redirect index
                                RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/index.html");
                                dispatcher.forward(request, response);
                            }

                            if (pseudoReq != null) {
                                request.setAttribute("pseudo", pseudoReq);
                                req.setAttribute("pseudo", pseudoReq);

                                String strGroupeAdd = request.getParameter("groupe");

				if (strGroupeAdd == null || strGroupeAdd.equals(""))
				    strGroupeAdd = "all";

                                Groupe groupe = Groupe.getGroupe(strGroupeAdd);

                                if (groupe == null) {
                                    groupe = new Groupe(strGroupeAdd, "description", pseudoReq);
                                    groupe.persist();
                                    groupe.getParticipants().add(pseudoReq);
                                } else {
                                    if (!groupe.isAParticipant(pseudoReq)) {
                                        groupe.getParticipants().add(pseudoReq);
                                    }
                                }

                                //RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/menuPrincipal-vue");
                                //dispatcher.include(request, response);
				chain.doFilter(request, response);
                            }

                        }
                    }
                }
            }
        } else {
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
