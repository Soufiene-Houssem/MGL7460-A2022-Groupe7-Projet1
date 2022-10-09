package application;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import services.LibraireService;
import services.UtilisateurService;

/**
 * 
 * @author houss
 *
 */
public class Admin extends Libraire implements GestionUtilisateurs,GestionLibraires{
	
	private transient UtilisateurService userService;
	private transient LibraireService libraireService;
	
	public Admin(int id, String nom, String prenom, String email, String password, String adresse, int telephone,
			int role, int numeroBadge) {
		super(id, nom, prenom, email, password, adresse, telephone, role, numeroBadge);
	}
	
	public Admin(int id, String nom, String prenom, String email, String password, String adresse, int telephone,
			int role) {
		super(id, nom, prenom, email, password, adresse, telephone, role);
	}

	public Admin() {super();}
	
	@Override
	public void consulterUtilisateurs() {
		userService = new UtilisateurService();
		List<Utilisateur> users = userService.getAllUtilisateurs();
		if(users.isEmpty()) {
			System.out.println("Aucun utilisateur n'a été trouvé!");
		}else {
			for (Utilisateur user : users) {
				System.out.println(user.toString());
			}
		}
	}
	
	@Override
	public void ajouterUtilisateur() {
		userService = new UtilisateurService();
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un utilisateur~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le nom: ");
		String nom = scanner.next();	 // NOPMD by houss on 10/8/22 5:14 PM
		System.out.print("Entrez le prenom: ");
		String prenom = scanner.next();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez l'adresse: ");
		String adresse = scanner.next();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez le numéro de téléphone: ");
		int telephone = scanner.nextInt();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez l'email: ");
		String email = scanner.next(); // NOPMD by houss on 10/8/22 5:15 PM
		Pattern validEmailRegex = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = validEmailRegex.matcher(email);
		while(!matcher.find()) {
			System.out.print("!!Veuillez saisir un email valide: ");
			email = scanner.next();
			matcher = validEmailRegex.matcher(email);
		}
		System.out.print("Entrez le mot de passe: ");
		String password = scanner.next();		
		System.out.print("Veuillez confirmer le mot de passe: ");
		String passwordCheck = scanner.next();	
		if ( password.equals(passwordCheck) ) {
			Utilisateur user = new Utilisateur(nom, prenom, email, password, adresse, telephone, 1);
			if (userService.addUtilisateur(user)) {
				System.out.println("Utilisateur a été ajouté avec succes!");
			}else {
				System.out.println("Utilisateur n'a pas été ajouté!");
				ajouterUtilisateur();
			}
		}else {
			System.out.println("\n\nMot de passe n'a pas été confirmé!");
			ajouterUtilisateur();
		}
		scanner.close();
		
	}
	
	@Override
	public void supprimerUtilisateur(int id) {
		userService = new UtilisateurService();
		if (userService.deleteUtilisateur(id)) {
			System.out.println("Utilisateur supprimé avec succes!");
		}else {
			System.out.println("Utilisateur n'a pas été supprimé!");
		}
	}
	
	@Override
	public void modifierUtilisateur(int id) {
		userService = new UtilisateurService();
		Utilisateur user = userService.findUtilisateurById(id);
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n\t\t~~~~~~~~~~~~~~~Modifier un utilisateur~~~~~~~~~~~~~~~");
		System.out.print("~ Nom: "+user.getNom());
		System.out.print("\tEntrez le nouveau nom: ");
		String nom = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:14 PM
		user.setNom(nom);
		System.out.print("~ Prenom: "+user.getPrenom());
		System.out.print("\tEntrez le nouveau prenom: ");
		String prenom = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:15 PM
		user.setPrenom(prenom);
		System.out.print("~ Adresse: "+user.getAdresse());
		System.out.print("\tEntrez la nouvelle adresse: ");
		String adresse = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:15 PM
		user.setAdresse(adresse);
		System.out.print("~ Téléphone: "+user.getTelephone());
		System.out.print("\tEntrez le nouveau numéro de téléphone: ");
		int telephone = scanner.nextInt();	 // NOPMD by houss on 10/8/22 5:15 PM
		user.setTelephone(telephone);
		System.out.println("1- Confirmer \t2- Annuler");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2};
		int choix = getChoix(scanner, choixPossibles);
		if (choix == 1) { // NOPMD by houss on 10/9/22 1:34 PM
			if (userService.updateUtilisateur(user)) {
				System.out.println("Utilisateur a été modifié avec succes!");
			}else {
				System.out.println("Utilisateur n'a pas été modifié!");
			}
		}
		
	}
	
	@Override
	public void rechercherUtilisateurById(int id) {
		userService = new UtilisateurService();
		Utilisateur user = userService.findUtilisateurById(id);
		if(user != null) {
			System.out.println(user.toString());
		}else {
			System.out.println("Aucun utilisateur n'a été trouvé!");
		}
	}
	
	@Override
	public void rechercherUtilisateurByNomPrenom(String nomPrenom) {
		userService = new UtilisateurService();
		List<Utilisateur> users = userService.findUtilisateurByNomPrenom(nomPrenom);
		if(users.isEmpty()) {
			System.out.println("Aucun utilisateur n'a été trouvé!");
		}else {
			for (Utilisateur user : users) {
				System.out.println(user.toString());
			}
		}
	}
	
	
	/****************Méthodes de gestion des Libraires**********************/
	
	@Override
	public void consulterLibraires() {
		libraireService = new LibraireService();
		List<Libraire> libraires = libraireService.getAllLibraires();
		if(libraires.isEmpty()) {
			System.out.println("Aucun libraire n'a été trouvé!");
		}else {
			for (Libraire libraire : libraires) {
				System.out.println(libraire.toString());
			}
		}
	}
	
	@Override
	public void supprimerLibraire(int id) {
		libraireService = new LibraireService();
		if (libraireService.deleteLibraire(id)) {
			System.out.println("Libraire supprimé avec succes!");
		}else {
			System.out.println("Libraire n'a pas été supprimé!");
		}
	}
	
	@Override
	public boolean ajouterLibraire() {
		libraireService = new LibraireService();
		boolean isAdded = false; // NOPMD by houss on 10/8/22 6:25 PM
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un utilisateur~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le nom: ");
		String nom = scanner.next();	 // NOPMD by houss on 10/8/22 5:14 PM
		System.out.print("Entrez le prenom: ");
		String prenom = scanner.next();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez l'adresse: ");
		String adresse = scanner.next();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez le numéro de téléphone: ");
		int telephone = scanner.nextInt();	 // NOPMD by houss on 10/8/22 5:15 PM
		System.out.print("Entrez l'email: ");
		String email = scanner.next(); // NOPMD by houss on 10/8/22 5:15 PM
		Pattern validEmailRegex = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = validEmailRegex.matcher(email);
		while(!matcher.find()) {
			System.out.print("!!Veuillez saisir un email valide: ");
			email = scanner.next();
			matcher = validEmailRegex.matcher(email);
		}
		System.out.print("Entrez le mot de passe: ");
		String password = scanner.next();		
		System.out.print("Veuillez confirmer le mot de passe: ");
		String passwordCheck = scanner.next();	
		if ( password.equals(passwordCheck) ) {
			Libraire libraire = new Libraire(nom, prenom, email, password, adresse, telephone, 2);
			if (libraireService.addLibraire(libraire)) {
				System.out.println("Libraire a été ajouté avec succes!");
			}else {
				System.out.println("Libraire n'a pas été ajouté!");
				ajouterLibraire();
				
			}
		}else {
			System.out.println("Mot de passe n'a pas été confirmé!");
			ajouterLibraire();
		}
		scanner.close();
		return isAdded;
	}
	
	@Override
	public void modifierLibraire(int id) {
		LibraireService service = new LibraireService();
		Libraire libraire = service.findLibraireById(id);
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:03 PM
		System.out.println("\n\t\t~~~~~~~~~~~~~~~Modifier un utilisateur~~~~~~~~~~~~~~~");
		System.out.print("~ Nom: "+libraire.getNom());
		System.out.print("\tEntrez le nouveau nom: ");
		String nom = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:14 PM
		libraire.setNom(nom);
		System.out.print("~ Prenom: "+libraire.getPrenom());
		System.out.print("\tEntrez le nouveau prenom: ");
		String prenom = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:15 PM
		libraire.setPrenom(prenom);
		System.out.print("~ Adresse: "+libraire.getAdresse());
		System.out.print("\tEntrez la nouvelle adresse: ");
		String adresse = scanner.nextLine();	 // NOPMD by houss on 10/8/22 5:15 PM
		libraire.setAdresse(adresse);
		System.out.print("~ Téléphone: "+libraire.getTelephone());
		System.out.print("\tEntrez le nouveau numéro de téléphone: ");
		int telephone = scanner.nextInt();	 // NOPMD by houss on 10/8/22 5:15 PM
		libraire.setTelephone(telephone);
		System.out.println("1- Confirmer \t2- Annuler");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2};
		int choix = getChoix(scanner, choixPossibles);
		if (choix == 1) { // NOPMD by houss on 10/9/22 1:34 PM
		libraireService = new LibraireService();
			if (libraireService.updateLibraire(libraire)) {
				System.out.println("Libraire a été modifié avec succes!");
			}else {
				System.out.println("Libraire n'a pas été modifié!");
			}
		}
	}
	
	@Override
	public void rechercherLibraireById(int id) {
		libraireService = new LibraireService();
		Libraire libraire = libraireService.findLibraireById(id);
		if(libraire != null) {
			System.out.println(libraire.toString());
		}else {
			System.out.println("Aucun libraire n'a été trouvé!");
		}
	}
	
	@Override
	public void rechercherLibraireByNomPrenom(String nomPrenom) {
		libraireService = new LibraireService();
		List<Libraire> libraires = libraireService.findLibraireByNomPrenom(nomPrenom);
		if(libraires.isEmpty()) {
			System.out.println("Aucun libraire n'a été trouvé!");
		}else {
			for (Libraire libraire : libraires) {
				System.out.println(libraire.toString());
			}
		}
	}
	
	
	
	
}
