package application;


import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;
import entities.Livre;
import services.LivreService;

/**
 * 
 * @author houss
 *
 */
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
	
	public Libraire(String nom, String prenom, String email, String password, String adresse, int telephone, int role) {
		super(nom, prenom, email, password, adresse, telephone, role);
	}
	
	public Libraire(int id,String nom, String prenom, String email, String password, String adresse, int telephone, int role) {
		super(id, nom, prenom, email, password, adresse, telephone, role);
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
	public void ajouterLivre() {
		service = new LivreService();
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un livre~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le isbn: ");
		int isbn = scanner.nextInt();	
		scanner.nextLine();
		System.out.print("Entrez le titre: ");
		String title = scanner.nextLine();	 
		System.out.print("Entrez l'auteur: ");
		String author = scanner.nextLine();
		System.out.print("Entrez la categorie: ");
		String category = scanner.nextLine();
		System.out.print("Entrez l'année: ");
		int year = scanner.nextInt();
		scanner.nextLine();
		Livre livre = new Livre(isbn, title, author, year, category); // NOPMD by houss on 10/9/22 2:33 PM
		System.out.println("1- Confirmer \t2- Annuler");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2};
		int choix = getChoix(scanner, choixPossibles);
		if (choix == 1) { // NOPMD by houss on 10/9/22 1:34 PM
			if(service.addLivre(livre)) {
				System.out.println("Livre ajouté avec succes!");
			}else {
				System.out.println("Livre n'a pas été ajouté!");
				ajouterLivre();
			}
		}
	}
	
	@Override
	public void supprimerLivre(int isbn) {
		service = new LivreService();
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("1- Confirmer \t2- Annuler");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2};
		int choix = getChoix(scanner, choixPossibles);
		if (choix == 1) { // NOPMD by houss on 10/9/22 1:34 PM
			if(service.deleteLivre(isbn)) {
				System.out.println("Livre supprimé avec succes!");
			}else {
				System.out.println("Livre n'a pas été supprimé!");
			}
		}
		
		
		
		
	}
	
	@Override
	public void modifierLivre(int isbn) {
		service = new LivreService();
		Livre livre = service.findLivreByIsbn(isbn);
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n~~~~~~~~~~~~~~~~~Modifier un livre~~~~~~~~~~~~~~~~~\n");
		System.out.print("~ Titre: "+livre.getTitle());
		System.out.print("\tEntrez le nouveau titre: ");
		String title = scanner.nextLine();
		livre.setTitle(title);
		System.out.print("~ Auteur: "+livre.getAuthor());
		System.out.print("\tEntrez le nouvel auteur: ");
		String author = scanner.nextLine();
		livre.setAuthor(author);
		System.out.print("~ Categorie: "+livre.getCategory());
		System.out.print("\tEntrez la nouvelle categorie: ");
		String category = scanner.nextLine();
		livre.setCategory(category);
		System.out.print("~ Année: "+livre.getYear());
		System.out.print("\tEntrez l'année: ");
		int year = scanner.nextInt();
		livre.setYear(year);
		scanner.nextLine();
		System.out.println("1- Confirmer \t2- Annuler");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2};
		int choix = getChoix(scanner, choixPossibles);
		if (choix == 1) { // NOPMD by houss on 10/9/22 1:34 PM
			if(service.updateLivre(livre)) {
				System.out.println("Livre modifié avec succes!");
			}else {
				System.out.println("Livre n'a pas été modifié!");
			}
		}
	}

	public static int getChoix(Scanner scanner, int... choixPossibles) {
		String choixString = scanner.next();
		int choix;
		if (choixString.matches("\\d+")) {
			if(Arrays.stream(choixPossibles).anyMatch(x -> x == Integer.parseInt(choixString))) {
				choix = Integer.parseInt(choixString);
			}else {
				System.out.print("Choix invalide!! Veuillez saisir un choix valide: ");
				choix = getChoix(scanner, choixPossibles);	
			}
		}else {
			System.out.print("Choix invalide!! Veuillez saisir un choix valide: ");
			choix = getChoix(scanner, choixPossibles);	
		}
		return choix;
	}
	
	

	
}
