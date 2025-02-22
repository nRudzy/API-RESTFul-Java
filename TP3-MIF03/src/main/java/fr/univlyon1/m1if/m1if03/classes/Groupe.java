package fr.univlyon1.m1if.m1if03.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Groupe implements Serializable {
    private String nom, description;
    private String proprietaire;
    private List<String> participants;
    private GestionBillets gBillets;

    private static List<Groupe> groupes = new ArrayList<>();;

    public Groupe() {
        this.nom = "";
        this.description = "";
        this.proprietaire = "";
        this.participants = new ArrayList<String>();
        gBillets = new GestionBillets();
    }

    public Groupe(String nom, String description, String proprietaire) {
        this.nom = nom;
        this.description = description;
        this.proprietaire = proprietaire;
        this.participants = new ArrayList<String>();
	//participants.add(proprietaire);
        gBillets = new GestionBillets();
    }
    
    public void persist() {
        groupes.add(this);
    }

    public static Groupe getGroupe(int i) {
	return groupes.get(i);
    }

    public static Groupe getGroupe(String nom) {
	Groupe groupe = null;

        for(Groupe g: groupes) {
            if(g.nom.equals(nom)) {
		groupe = g;
		break;
            }
        }

	return groupe;
    }

    public boolean isAParticipant(String userPseudo) {
	boolean res = false;

        for(String pseudo: participants) {
            if(pseudo.equals(userPseudo)) {
		res = true;
		break;
            }
        }

	return res;
    }

    public static List<Groupe> getGroupes() {
        return groupes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String _nom) {
        this.nom = _nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String _proprietaire) {
        this.proprietaire = _proprietaire;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object obj) {
	if(obj == null)
	    return false;

    	return nom.equals(((Groupe)obj).getNom());
    }
}

