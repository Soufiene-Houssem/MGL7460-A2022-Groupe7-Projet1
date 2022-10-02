package application;

public class Libraire extends Utilisateur {
	
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

	
}
