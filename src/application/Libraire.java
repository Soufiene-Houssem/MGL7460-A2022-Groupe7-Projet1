package application;


import entities.Livre;
import services.LivreService;

public class Libraire extends Utilisateur implements GestionLivres{
	
	private int numeroBadge;
	private transient LivreService service;
	
	public Libraire(int id, String nom, String prenom, String email, String password, String adresse, int telephone,
			int role, int numeroBadge) {
		super(id, nom, prenom, email, password, adresse, telephone, role);
		this.numeroBadge = numeroBadge;
	}
	
	public Libraire(int id, String nom, String prenom, String email, String adresse, int telephone,
			int role, int numeroBadge) {
		super(id, nom, prenom, email, adresse, telephone, role);
		this.numeroBadge = numeroBadge;
	}

	public Libraire () { super(); }
	
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

	@Override
	public void ajouterLivre(Livre livre) {
		service = new LivreService();
		if(service.addLivre(livre)) {
			System.out.println("Livre ajouté avec succes!");
		}else {
			System.out.println("Livre n'a pas été ajouté!");
		}
	}
	
	@Override
	public void supprimerLivre(int isbn) {
		service = new LivreService();
		if(service.deleteLivre(isbn)) {
			System.out.println("Livre supprimé avec succes!");
		}else {
			System.out.println("Livre n'a pas été supprimé!");
		}
		
	}
	
	@Override
	public void modifierLivre(Livre livre) {
		service = new LivreService();
		if(service.updateLivre(livre)) {
			System.out.println("Livre modifié avec succes!");
		}else {
			System.out.println("Livre n'a pas été modifié!");
		}
		
	}


	
	

	
}
