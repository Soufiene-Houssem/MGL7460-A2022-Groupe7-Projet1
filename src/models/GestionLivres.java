package models;

import entities.Livre;

public interface GestionLivres {
	public boolean ajouterLivre();
	public boolean supprimerLivre(int reference);
	public boolean modifierLivre(Livre livre);

}
