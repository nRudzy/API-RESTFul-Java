package fr.univlyon1.m1if.m1if03.dto;

import fr.univlyon1.m1if.m1if03.classes.Billet;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.io.Serializable;
import java.util.ArrayList;

public class BilletDTO implements Serializable {

    private String titre, contenu, auteur;
    private Groupe groupe;
    private ArrayList<String> commentaires;

    public BilletDTO(){
        this.titre = "Rien";
        this.contenu = "Vide";
        this.auteur = "Personne";
        this.commentaires = new ArrayList<>();
        this.groupe = Groupe.getGroupe("all");
    }

    public BilletDTO(Billet billet) {
        this.titre = billet.getTitre();
        this.contenu = billet.getContenu();
        this.auteur = billet.getAuteur();
        this.commentaires = billet.getCommentaires();
        this.groupe = billet.getGroupe();
    }
}
