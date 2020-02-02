//Bilblioth�que de fonctions AJAX permettant l'envoi de requ�tes au serveur de mani�re asynchrone 
//et le traitement (ajout au corpos du document appelant) de r�ponses en XML conformes � la structure d�crite dans l'�nonc� du devoir.

//--------------------Fonctions principales---------------------

//fonction principale, qui envoie la  requ�te au serveur de fa�on asynchrone et positionne la fonction qui va traiter les donn�es
function loadXMLAsynchroneously(method, request, parameters, id) {
    //initialisation de l'objet XMLXhttpRequest
    var xhr = initRequete();

    //sp�cification de la fonction de traitement � appeler au retour serveur (car chargement asynchrone)
    var XMLDoc = null;
    xhr.onreadystatechange = function () {
        getXMLDocument(xhr, XMLDoc, id);
    };

    //envoi de la requ�te de chargement du fichier XML au serveur
    //le dernier param�tre est true ; le chargement du fichier se fera en asynchrone
    xhr.open(method, request, true);
    //encodage des param�tres dans la requ�te, si la m�thode est post
    if (parameters && (method === "post" || method === "POST"))
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(parameters);
}

//autre fonction principale, plus simple, qui envoie la requ�te au serveur de fa�on asynchrone et ne se pr�occupe pas de la r�ponse
//remarque : l'utilisation de cette fonction n'est pas n�cessaire pour r�aliser le devoir.
function sendRequestAsynchroneously(method, request, parameters, idDiv) {
    //initialisation de l'objet XMLXhttpRequest
    var xhr = initRequete();

    //envoi de la requ�te de chargement du fichier XML au serveur
    //le dernier param�tre est true ; le chargement du fichier se fera en asynchrone
    xhr.open(method, request, true);
    //encodage des param�tres dans la requ�te, si la m�thode est post
    if (parameters && (method === "post" || method === "POST"))
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 || xhr.status === 0) {
                document.getElementById(idDiv).innerHTML = xhr.responseText;
            }
        }
    };

    xhr.send(parameters);

}

//--------------------------fonctions secondaires---------------------------

// fonction pour charger un document xml
function loadXMLDoc(filename) {
    var xhttp = initRequete();
    xhttp.open("GET", filename, false);
    try {
        xhttp.responseType = "msxml-document"
    } catch (err) {
    } // Helping IE11
    xhttp.send("");
    return xhttp.responseXML;
}


//fonction appel�e lors de la r�ception de la r�ponse, si la fonction principale loadXMLAsynchroneously() a �t� utilis�e.
function getXMLDocument(xhr, XMLDoc, id) {
    // teste si la r�ponse est disponible
    if (xhr.readyState === 4) {
        // teste si la r�ponse est arriv�e et contient des donn�es (code HTTP = 200 : OK)
        if (xhr.status === 200) {
            // teste si la r�ponse est arriv�e en XML ou en texte (peut arriver pour certaines configurations d'Apache)
            if (xhr.responseXML != null) {
                XMLDoc = xhr.responseXML;
            } else if (xhr.responseText != null) {
                //si la r�ponse est en texte, transformation en XML (voir fonction fa�ade plus bas)
                XMLDoc = parseFromString(xhr.responseText);
            }
            //D�commentez la ligne suivante pour voir le contenu XML obtenu (ne marche qu'avec FF)
            //alert((new XMLSerializer()).serializeToString(XMLDoc));

            //appel de la fonction de traitement qui va ajouter les donn�es au corps de la page (� �crire)
            traiteXML(XMLDoc, id);

            //teste si le code de statut est autre que le code renvoy� en cas d'absence de nouveaux messages.
            //Remarque : le code 1223 provient d'un bug avec IE : http://trac.dojotoolkit.org/ticket/2418
        } else if (xhr.status >= 400 && xhr.status !== 1223) {
            alert("Un probl�me est survenu avec la requ�te : ");
        }
    }
}

//----------------------Fonctions fa�ades----------------------------
//permettent de masquer les diff�rences entre les navigateurs
//remarque : ces fonctions ont uniquement �t� test�es avec FF et IE7

//fonction fa�ade qui teste le type de navigateur et renvoie un objet capable de se charger de l'envoi de la requ�te.
function initRequete() {
    var xhr = null;
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xhr;
}

//fonction fa�ade qui re�oit une cha�ne de caract�res et la parse en XML pour renvoyer un objet DOM.
//remarque : le XML doit �tre bien form�, sans quoi l'erreur de parsing bloque l'ex�cution du script.
function parseFromString(docTXT) {
    // code for IE
    if (window.ActiveXObject) {
        var XMLDoc = new ActiveXObject("Microsoft.XMLDOM");
        XMLDoc.async = "false";
        XMLDoc.loadXML(docTXT);
    }
    // code for Mozilla, Firefox, Opera, etc.
    else {
        var parser = new DOMParser();
        var XMLDoc = parser.parseFromString(docTXT, "text/xml");
    }
    return XMLDoc;
}

//fonction fa�ade qui renvoie le texte contenu dans un �l�ment XML
function getXMLTextContent(source) {
    if (source.textContent != null) {
        //Gecko
        return source.textContent;
    } else {
        //IE
        return source.text;
    }
}

function traiteXML(file, id) {
    var rawFile = initRequete();
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4) {
            if (rawFile.status === 200 || rawFile.status === 0) {
                var allText = rawFile.responseText;
                alert(allText);
                document.getElementById(id).innerHTML = allText;
            }
        }
    };
    rawFile.send(null);
}

//-------------------------- FONCTIONS RAJOUTEES ---------------------------

// Fonction ouverture fichier xml asynchrone + appel fonction build & print horloge svg
function runAfficheHorlogeSVG(methode, nomFichier) {
    // xhttp pour ouvrir notre fichier XML time.jsp
    var xhttp = initRequete();
    xhttp.onreadystatechange = function () {
        // 4 : Requete finie et statut http 200 c'est ok
        if (this.readyState === 4 && this.status === 200) {
            // on appelle notre horloge
            afficheHorlogeSVG(this);
        }
    };
    xhttp.open(methode, nomFichier, true);
    xhttp.send();
}

// Fonction de récup des données et SVG horloge
function afficheHorlogeSVG(xml) {
    let xmlDoc = xml.responseXML;
    let h = xmlDoc.getElementsByTagName('h')[0];
    let m = xmlDoc.getElementsByTagName('m')[0];
    let s = xmlDoc.getElementsByTagName('s')[0];
    let heures = h.childNodes[0].nodeValue;
    let minutes = m.childNodes[0].nodeValue;
    let secondes = s.childNodes[0].nodeValue;

    let hands = [];
    hands.push(document.querySelector('#secondhand > *'));
    hands.push(document.querySelector('#minutehand > *'));
    hands.push(document.querySelector('#hourhand > *'));

    let cx = 100;
    let cy = 100;

    function shifter(val) {
        return [val, cx, cy].join(' ');
    }

    let hoursAngle = 360 * heures / 12 + minutes / 2;
    let minuteAngle = 360 * minutes / 60;
    let secAngle = 360 * secondes / 60;

    // Permet la rotation en continu des aiguilles
    // remplace le siteTimeout(...)
    hands[0].setAttribute('from', shifter(secAngle));
    hands[0].setAttribute('to', shifter(secAngle + 360));
    hands[1].setAttribute('from', shifter(minuteAngle));
    hands[1].setAttribute('to', shifter(minuteAngle + 360));
    hands[2].setAttribute('from', shifter(hoursAngle));
    hands[2].setAttribute('to', shifter(hoursAngle + 360));

    // Affichage des 12 traits horaires
    for (let i = 1; i <= 12; i++) {
        let el = document.createElementNS('http://www.w3.org/2000/svg', 'line');
        el.setAttribute('x1', '100');
        el.setAttribute('y1', '30');
        el.setAttribute('x2', '100');
        el.setAttribute('y2', '40');
        el.setAttribute('transform', 'rotate(' + (i * 360 / 12) + ' 100 100)');
        el.setAttribute('style', 'stroke: #20b7af;');
        document.querySelector('svg').appendChild(el);
    }
}

// ************************** REQ POST *********************************


// la reponse du login contient un set-cookie qui est gere automatiquement par le navigateur
// si on inclue dans le xhrfield "withCredentials: true"
// le navigateur envoie automatiquement le cookie dans la requete
function postLogin(url) {
    let pseudo = {
        "pseudo": $('#form_pseudo').val()
    };
    let strigifyPseudo = JSON.stringify(pseudo);
    let parsedPseudo = JSON.parse(strigifyPseudo);

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'POST',
        data: JSON.stringify(pseudo),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            window.location.replace('#groupes');
            $('#userConnected').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>").val(parsedPseudo.pseudo);
            $('#userConnected2').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>");
            $('#userConnected3').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>");
            $('#userConnected4').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>");
            $('#userConnected5').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>");
            $('#userConnected6').html("Utilisateur connecté : " + parsedPseudo.pseudo + "<hr>");
            fetchGroupes('https://192.168.75.13/api/v2/groupes');
            fetchUtilisateurs('https://192.168.75.13/api/v2/users');
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function postGroupe(url) {
    let nomGroupe = {
        "nom": $('#form_groupe').val()
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'POST',
        data: JSON.stringify(nomGroupe),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            window.location.replace('#groupe');
            $('#montrerGroupe').val($('#form_groupe').val()).html("<h3><b>Groupe : " + "<span contenteditable='true'>" + $('#montrerGroupe').val() + " </span></b></h3>");

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function putNomGroupe(url) {
    let nomGroupe = {
        "nom": $('#montrerGroupe span').text().trim()
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'PUT',
        data: JSON.stringify(nomGroupe),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            let newNomGroupe = $('#montrerGroupe span').text().trim();
            $('#titreBillet').empty();
            $('#descriptionBillet').empty();
            $('#montrerGroupe').val(newNomGroupe).html("<h3><b>Groupe : " + "<span contenteditable='true'>" + $('#montrerGroupe').val() + " </span></b></h3>");
            window.location.replace('#groupe');

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function supprimerGroupe(url) {

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'DELETE',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            $('#groupesList').empty();
            fetchGroupes('https://192.168.75.13/api/v2/groupes');
            $('#form_groupe').val('');
            window.location.replace('#groupes');


        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function postBillet(url) {
    let createBillet = {
        "titre": $('#titreBillet').val(),
        "contenu": $('#descriptionBillet').val(),
        "auteur": $('#userConnected').val()
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'POST',
        data: JSON.stringify(createBillet),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            $('#montrerBillet').val($('#titreBillet').val());
            $('#montrerBilletsDuGroupe').empty();
            let groupeSelected = $('#montrerGroupe').val();
            fetchBilletsGroupe('https://192.168.75.13/api/v2/groupes/' + groupeSelected + '/billets');
            $('#titreBillet').val('');
            $('#descriptionBillet').val('');

            window.location.replace('#groupe');

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function supprimerBillet(url) {

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'DELETE',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            $('#montrerBilletsDuGroupe').empty();
            let groupeSelected = $('#montrerGroupe').val();
            let billetSelected = $('#montrerBillet').val();
            fetchBilletsGroupe('https://192.168.75.13/api/v2/groupes/' + groupeSelected + '/billets/' + billetSelected);
            window.location.replace('#groupe');

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function modifierBillet(url) {
    let billetAEffacer = {
        "titre": $('#titreAModifier span').text().trim(),
        "contenu": $('#contenuAModifier span').text().trim(),
        "auteur": $('#userConnected').text().trim()
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'PUT',
        data: JSON.stringify(billetAEffacer),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {

            window.location.replace('#groupe');

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function postCommentaire(url) {
    let commentaire = {
        "auteur": $('#userConnected').val(),
        "texte": $('#commentaireTexte').val(),
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'POST',
        data: JSON.stringify(commentaire),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            $('#montrerBillet').empty();
            let groupeSelected = $('#montrerGroupe').val();
            let billetSelected = $('#montrerBillet').val();
            fetchBillet('https://192.168.75.13/api/v2/groupes/' + groupeSelected + '/billets/' + billetSelected);
            $('#commentaireTexte').val('');
            window.location.replace('#billet');
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function supprimerCommentaire(url) {

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'DELETE',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (data) {
            $('#montrerBillet').empty();
            $('#montrerCommentairesDuBillet').empty();
            let groupeSelected = $('#montrerGroupe').val();
            let billetSelected = $('#montrerBillet').val();
            fetchBillet('https://192.168.75.13/api/v2/groupes/' + groupeSelected + '/billets/' + billetSelected);
            $('#commentaireTexte').val('');
            window.location.replace('#billet');
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function modifierCommentaire(url, id) {
    var commentaireAMofifier = {
        "auteur": $('#userConnected').val(),
        "texte": $('#com' + id).text()
    };

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'PUT',
        data: JSON.stringify(commentaireAMofifier),
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {

        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function postDeconnecter(url) {

    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'POST',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (result, status, xhr) {
            $('#form_pseudo').val('');
            window.location.replace('#index');
            window.location.reload();
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

function displayMustacheTemplate(json,idTemplate,idHtml){
    let template = $('#'+idTemplate).html();
    Mustache.parse(template);
    let rendu = Mustache.render(template,json);
    $('#'+idHtml).append(rendu);
}


// Ensemble des requêtes GET qui ont été remplacées par les fetch à la fin du TP

// getter de json groupe REMPLACEE PAR FETCHGROUPES
function getGroupesJson(url) {
    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'GET',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (data) {
            data.forEach(elmnt =>
                // Ne pas toucher ce bijou
                $('#groupesList').append("<li><form action='#groupe'>" +
                    "<button class='btn btnGroupe btnAjax' onclick='getValue()' value=" + elmnt.split('/').slice(-1)[0] + ">" +
                    elmnt.split('/').slice(-1)[0] + "</button></form></li>")
            );
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

// getter de json billets d'un groupe remplacée par fetchBillet
function getUnBilletDunGroupe(url) {
    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'GET',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (data) {
            $('#montrerBillet').append("<br>" +
                "<p><b>Titre : </b>" + data.titre + "</p>" +
                "<p><b>Contenu : </b>" + data.contenu + "</p>" +
                "<p><b>Auteur : </b>" + data.auteur.split('/').slice(-1)[0] + " </p>" +
                "<p><b>Commentaires : </b>" + data.commentaires + "</p>" + "<hr>");
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

// getter de json groupe utilisé par un fetch maintenant
function getUsersJson(url) {
    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'GET',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (data) {
            data.forEach(elmnt =>
                $('#usersList').append("<li>" + elmnt.split('/').slice(-1)[0] + "</li>")
            );
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

// getter de json billets d'un groupe
function getBilletsDeGroupe(url) {
    $.ajax({
        url: url,
        xhrFields: {
            withCredentials: true // active l'envoie automatique du cookie
        },
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        type: 'GET',
        datatype: 'application/json',
        contentType: 'application/json',
        success: function (data) {
            data.forEach(elmnt =>
                $('#montrerBilletsDuGroupe').append("<li><form action='#billet'>" +
                    "<button class='btn btnBillets btnAjax' onclick='getValue()' value=" + elmnt.split('/').slice(-1)[0] + ">" +
                    elmnt.split('/').slice(-1)[0] + "</button></form></li>")
            );
        },
        complete: function (xhr, status) {
            console.log(status);
            console.log(xhr);
        }
    });
}

