<%@ page import="fr.univlyon1.m1if.m1if03.classes.Groupe" %>
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
	pageContext.setAttribute("listGroupes", Groupe.getGroupes());
%>

<h2>Hello <%= request.getAttribute("pseudo")%> dans le menu principal !</h2>

<form method="post" action="Init">
    <p>
        <label>
            Creer un nouveau groupe :
            <input type="text" name="nom_groupe">
        </label>
        <input type="submit" value="Connexion">
    </p>
</form>

<hr>

<c:forEach items="${listGroupes}" var="item">
<h2>
    <a href="groupes/${item.nom}">
            ${item.nom}
    </a>
    <br/>
</h2>
    <hr>
    <% i++; %>
</c:forEach>


<p><a href="${pageContext.request.contextPath}/Deco">Se déconnecter</a></p>

</body>
</html>
