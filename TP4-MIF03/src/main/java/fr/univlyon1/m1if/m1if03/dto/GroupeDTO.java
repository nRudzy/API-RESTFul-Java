package fr.univlyon1.m1if.m1if03.dto;

import fr.univlyon1.m1if.m1if03.classes.GestionBillets;
import fr.univlyon1.m1if.m1if03.classes.Groupe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupeDTO implements Serializable {
    private String nom, description;
    private String proprietaire;
    private List<String> participants;
    private List<Groupe> groupes = new ArrayList<>();;

    public GroupeDTO() {
        this.nom = "";
        this.description = "";
        this.proprietaire = "";
        this.participants = new ArrayList<String>();
    }

    public GroupeDTO(Groupe groupe){
        this.nom = groupe.getNom();
        this.description = groupe.getDescription();
        this.proprietaire = groupe.getProprietaire();
        this.participants = groupe.getParticipants();
        this.groupes = Groupe.getGroupes();
    }
}
