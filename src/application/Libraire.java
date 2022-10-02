package application;


import entities.Livre;
import models.GestionLivres;

public class Libraire extends Utilisateur implements GestionLivres{
	
	private int numeroBadge;
	
	public Libraire(String nom, String prenom, String email, String password,
			String adresse, int telephone, int role) {
		super(nom, prenom, email, password, adresse, telephone, role);
	}
	
	public Libraire () {};
	
	public int getNumeroBadge() {
		return numeroBadge;
	}



	public void setNumeroBadge(int numeroBadge) {
		this.numeroBadge = numeroBadge;
	}
	
	
	
	@Override
	public String toString() {
		return  "ID "+getId()+" ~~ Nom: "+ getNom() + " || Prenom: "+getPrenom()+ " || Badge: "+numeroBadge+ " || Email: "+getEmail()+" || Adresse: "+getAdresse()+ " || Téléphone: "+getTelephone();
	}

	
	/*********************************Gestion livres*********************************/
	
	@Override
	public boolean ajouterLivre() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimerLivre(int reference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifierLivre(Livre livre) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

	
}
