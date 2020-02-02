package fr.univlyon1.m1if.m1if03.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Billet implements Serializable {
    private String titre, contenu, auteur;

    private Groupe groupe;

    private ArrayList<String> commentaires;

    public Billet() {
        this.titre = "Rien";
        this.contenu = "Vide";
        this.auteur = "Personne";
        this.commentaires = new ArrayList<>();
        this.groupe = Groupe.getGroupe("all");
    }

    public Billet(String titre, String contenu, String auteur, String groupe) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.commentaires = new ArrayList<>();
        this.groupe = Groupe.getGroupe(groupe);
    }

    public Billet(String titre, String contenu, String auteur, Groupe groupe) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.commentaires = new ArrayList<>();
        this.groupe = groupe;
    }

    public void persist() {
	GestionBillets.add(this);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public ArrayList<String> getCommentaires() {
        return commentaires;
    }

    public void addCommentaire(String commentaire) {
        if(commentaire != null) {
            this.commentaires.add(commentaire);
        }
    }

    /**
     * @return Groupe return the groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

}
