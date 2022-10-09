package application;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Scanner;

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
	public boolean ajouterUtilisateur() {
		userService = new UtilisateurService();
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
		System.out.print("Entrez le mot de passe: ");
		String password = scanner.next();		
		System.out.print("Veuillez confirmer le mot de passe: ");
		String passwordCheck = scanner.next();	
		if ( password.equals(passwordCheck) ) {
			Utilisateur user = new Utilisateur(nom, prenom, email, password, adresse, telephone, 1);
			if (userService.addUtilisateur(user)) {
				System.out.println("Utilisateur a été ajouté avec succes!");
				isAdded = userService.addUtilisateur(user);
			}else {
				System.out.println("Utilisateur n'a pas été ajouté!");
			}
		}else {
			System.out.println("Mot de passe n'a pas été confirmé!");
		}
		scanner.close();
		return isAdded;
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
	public void modifierUtilisateur(Utilisateur user) {
		userService = new UtilisateurService();
		if (userService.updateUtilisateur(user)) {
			System.out.println("Utilisateur a été modifié avec succes!");
		}else {
			System.out.println("Utilisateur n'a pas été modifié!");
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
	public void ajouterLibraire(Libraire libraire) {
		libraireService = new LibraireService();
		if (libraireService.addLibraire(libraire)) {
			System.out.println("Libraire a été modifié avec succes!");
		}else {
			System.out.println("Libraire n'a pas été modifié!");
		}
	}
	
	@Override
	public void modifierLibraire(Libraire libraire) {
		libraireService = new LibraireService();
		if (libraireService.updateLibraire(libraire)) {
			System.out.println("Libraire a été modifié avec succes!");
		}else {
			System.out.println("Libraire n'a pas été modifié!");
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
