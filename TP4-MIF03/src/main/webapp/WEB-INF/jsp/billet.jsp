<%@ page import="fr.univlyon1.m1if.m1if03.classes.Billet" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.GestionBillets" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Groupe" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Billet</title>
    <meta http-equiv="refresh" content="5" />
</head>

<body>
<%
        session = request.getSession();
	Groupe groupe = (Groupe) session.getAttribute("groupe");
	int indexGroupe = (int) session.getAttribute("indexGroupe");
	Billet b = (Billet) session.getAttribute("billet"); //GestionBillets.getBillet(groupe, indexBillet);

        if(request.getMethod().equals("POST")){
            String commentaire = request.getParameter("commentaire");
            b.addCommentaire(commentaire);
        }
        pageContext.setAttribute("billet", b);
	pageContext.setAttribute("indexGroupe", indexGroupe);
%>

<h2>Hello <%= session.getAttribute("pseudo")%> !</h2>
<%--<h2>Index du billet : <%= indexBillet %> !</h2>--%>
<p>Titre: ${billet.titre} </p>
<div class="contenu">
    <c:out value="${billet.contenu}"/>
</div>
<hr>

<ul>
<c:forEach items="${billet.commentaires}" var="item">
    <li>${item}</li>
</c:forEach>
</ul>

<hr>
<form method="post" action="">
    <p>

        <label for="commentaire">Commentaire :</label>
        <input type="text" id="commentaire" name="commentaire">
        <input type="submit" value="Envoyer">

    </p>
</form>

<p><a href="${pageContext.request.contextPath}/SaisieBillet">Saisir un nouveau billet</a></p>
<p><a href="${pageContext.request.contextPath}/Deco">Se déconnecter</a></p>
<p><a href="${pageContext.request.contextPath}/Groupe/<%= indexGroupe %>">Menu</a></p>

</body>
</html>
