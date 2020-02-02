<%@ page import="java.util.List" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Groupe" %>
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
	Groupe groupe;
	if(session.getAttribute("indexGroupe") != null){
	    int indexGroupe = (int) session.getAttribute("indexGroupe");
	    groupe = Groupe.getGroupe(indexGroupe);
	} else {
	    groupe = (Groupe) session.getAttribute("groupe");
	}
	List<Billet> listBillet = GestionBillets.getBillets(groupe);
	pageContext.setAttribute("listBillet", listBillet);
	pageContext.setAttribute("participants", groupe.getParticipants());
%>

<h1>Hello <%= session.getAttribute("pseudo")%> dans le Menu du groupe <%= groupe.getNom() %>!</h2>
<h2>Nombre billet : <%= listBillet.size() %> !</h2>
<br/>
<h3> Liste des membres : </h3>
<c:forEach items="${participants}" var="item">
    ${item}, 
</c:forEach>
<br/>
<hr>

<c:forEach items="${listBillet}" var="item">
<h2> <a href="${pageContext.request.contextPath}/Billet/<%= i %>"> ${item.titre}</a>  <br> </h2>
    ${item.auteur}<br>
    ${item.contenu}<br>
    <hr>
    <% i++; %>
</c:forEach>


<p><a href="${pageContext.request.contextPath}/saisie.html">Saisir un nouveau billet</a></p>

<%--Menu qui affiche tous les groupeMenu qui affiche tous les groupes--%>
<p><a href="${pageContext.request.contextPath}/MenuPrincipal">Revenir au menu principal</a></p>
<p><a href="${pageContext.request.contextPath}/Deco">Se déconnecter</a></p>

</body>
</html>
