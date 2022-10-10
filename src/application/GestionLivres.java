package application;

import entities.Livre;

public interface GestionLivres {
	public void ajouterLivre(Livre livre);
	public void supprimerLivre(int isbn);
	public void modifierLivre(Livre livre);

}
