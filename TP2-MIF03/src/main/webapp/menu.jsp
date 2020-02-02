<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Billet" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.GestionBillets" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Menu</title>
</head>

<body>
<%
	int i = 0;
        session = request.getSession();

        if(request.getMethod().equals("POST")){
        }
	pageContext.setAttribute("listBillet", GestionBillets.getBillets());
%>

<h2>Hello <%= session.getAttribute("pseudo")%> dans le Menu!</h2>
<h3>nb billet : <%= GestionBillets.taille() %> !</h2>
</b>

<hr>

<c:forEach items="${listBillet}" var="item">
<h2> <a href="Billet/<%= i %>"> ${item.getTitre()}</a>  <br> </h2>
    ${item.getAuteur()}<br>
    ${item.getContenu()}<br>
    <hr>
    <% i++; %>
</c:forEach>


<p><a href="SaisieBillet">Saisir un nouveau billet</a></p>
<p><a href="Deco">Se déconnecter</a></p>

</body>
</html>
