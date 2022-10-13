package application;

import java.util.List;

import entities.Livre;
import services.LivreService;

/**
 * 
 * @author houss
 *
 */
public class Utilisateur implements ConsulterLivres{
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String adresse;
	private int telephone;
	private int role;
	private transient LivreService service;
	
	
	public Utilisateur(int id, final String nom, final String prenom, final String email, final String password, final String adresse, final int telephone, final int role) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.telephone = telephone;
		this.role = role;
	}
	
	
	
	public Utilisateur(int id, String nom, String prenom, String email, String adresse, int telephone, int role) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.adresse = adresse;
		this.telephone = telephone;
		this.role = role;
	}
	
	public Utilisateur(String nom, String prenom, String email, String password, String adresse, int telephone, int role) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.telephone = telephone;
		this.role = role;
	}



	public Utilisateur() { super(); }


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}	
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return  "ID "+id+" ~~ Nom: "+ nom + " || Prenom: "+prenom+ " || Email: "+email+" || Adresse: "+adresse+ " || Téléphone: "+telephone;
	}
	
	@Override
	public void consulterLivres() {
		service = new LivreService();
		List<Livre> livres = service.getAllLivres();
		if(livres.isEmpty()) {
			System.out.println("Aucun livre n'a été trouvé!");
		}else {
			System.out.println("\n\t\t~~~~~~~~~~~~~~~Liste des livres~~~~~~~~~~~~~~~");
			for (Livre livre : livres) {
				System.out.println(livre.toString());
			}
			System.out.println("\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
	
	@Override
	public void rechercherLivreByIsbn(int isbn) {
		service = new LivreService();
		Livre livre = service.findLivreByIsbn(isbn);
		if(livre != null) {
			System.out.println(livre.toString());
		}else {
			System.out.println("Aucun livre n'a été trouvé!");
		}
		
	}
	
	@Override
	public void rechercherLivreByTitre(String title) {
		service = new LivreService();
		List<Livre> livres = service.findLivreByTitle(title);
		if(livres.isEmpty()) {
			System.out.println("Aucun livre n'a été trouvé!");
		}else {
			for (Livre livre : livres) {
				System.out.println(livre.toString());
			}
		}
		
	}
	
	@Override
	public void rechercherLivreByAuteur(String auteur) {
		service = new LivreService();
		List<Livre> livres = service.findLivreByAuthor(auteur);
		if(livres.isEmpty()) {
			System.out.println("Aucun livre n'a été trouvé!");
		}else {
			for (Livre livre : livres) {
				System.out.println(livre.toString());
			}
		}
		
	}
	
	
}
