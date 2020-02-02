package fr.univlyon1.m1if.m1if03.classes;

import java.util.ArrayList;
import java.util.List;

public class GestionBillets {
    private static List<Billet> billets = new ArrayList<>();;
    public GestionBillets() {
    }

    public static void add(Billet billet) {
        billets.add(billet);
    }

    public static Billet getBillet(int i) {
        return billets.get(i);
    }

    public static Billet getLastBillet(int i) {
        if (billets.size() > 0)
            return getBillet(billets.size() -1);
        throw new IndexOutOfBoundsException("Erreur dans l'appel à la fonction getLastBillet");
    }

    public static List<Billet> getBillets() {
	return billets;
    }
    public static int taille() {
	    return billets.size();
    }
}
