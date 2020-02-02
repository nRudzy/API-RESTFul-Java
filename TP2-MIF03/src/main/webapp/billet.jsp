<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Billet" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.GestionBillets" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Billet</title>
</head>

<body>
<%
        List<String> commentaires = new ArrayList<String>();
        session = request.getSession();
	int indexBillet = (int) session.getAttribute("indexBillet");
	Billet b = GestionBillets.getBillet(indexBillet);
        if(request.getMethod().equals("POST")){
            String commentaire = request.getParameter("commentaire");
            commentaires.add(commentaire);
        }
        pageContext.setAttribute("listcom",commentaires);
        pageContext.setAttribute("billet",b);
%>

<h2>Hello <%= session.getAttribute("pseudo")%> !</h2>
<h2>Index du billet : <%= indexBillet %> !</h2>
<div class="contenu">
    <c:out value="${billet.contenu}"/>
</div>
<hr>
</b>

<c:forEach items="${listcom}" var="item">
    ${item}<br>
</c:forEach>
<hr>
<form method="post" action="billet.jsp">
    <p>
        Commentaire :
        <input type="text" id="commentaire" name="commentaire">
        <input type="submit" value="Envoyer">

    </p>
</form>
<p><a href="SaisieBillet">Saisir un nouveau billet</a></p>
<p><a href="Deco">Se déconnecter</a></p>
<p><a href="menu.jsp">Menu</a></p>

</body>
</html>
