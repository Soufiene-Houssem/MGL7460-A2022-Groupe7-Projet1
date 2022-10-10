package models;

import java.util.List;

import entities.Livre;

public interface RechercheLivres {
	public List<Livre> consulterLivres();
	public Livre rechercherLivre(int reference);
	public List<Livre> rechercherLivresByTitre(String Titre);
}
