package application;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author houss
 *
 */
public class Menus {
	
	public static void menuUtilisateur(Utilisateur user, Scanner scanner) {
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		user.consulterLivres();
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Déconnecter ");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2, 3};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				int isbn = scanner.nextInt();
				user.rechercherLivreByIsbn(isbn);
				break;
			case 2:
				System.out.print("Titre: ");
				String titre = scanner.next();
				user.rechercherLivreByTitre(titre);
				break;
			case 3:
				LibrairieApp.deconnexion();
				break;
			default:
				break;
		}
	}
	
	public static void menuLibraire(Libraire user, Scanner scanner) {
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		user.consulterLivres();
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Ajouter un livre ");
		System.out.println("4- Supprimer un livre ");
		System.out.println("5- Modifier un livre ");
		System.out.println("6- Déconnecter ");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				int isbn = scanner.nextInt();
				user.rechercherLivreByIsbn(isbn);
				break;
			case 2:
				System.out.print("Titre: ");
				String titre = scanner.next();
				user.rechercherLivreByTitre(titre);
				break;
			case 3:
				user.ajouterLivre();
				menuLibraire(user, scanner);
				break;
			case 4:
				System.out.print("Veuillez saisir l'isbn du livre à supprimer: ");
				int isbnToDelete = scanner.nextInt();
				user.supprimerLivre(isbnToDelete);
				break;
			case 5:
				System.out.print("Veuillez saisir l'isbn du livre à modifier: ");
				int isbnToUpdate = scanner.nextInt();
				user.modifierLivre(isbnToUpdate);
				menuLibraire(user, scanner);
				break;
			case 6:
				LibrairieApp.deconnexion();
				break;
			default:
				break;
		}
	}
	
	public static void menuAdmin(Admin user, Scanner scanner) {
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		System.out.println("1- Gestion livres");
		System.out.println("2- Gestion utilisateurs");
		System.out.println("3- Gestion Libraires");
		System.out.println("4- Deconnecter");
		System.out.print("\nVeuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix) {
			case 1:
				menuGestionLivres(scanner, user);
				break;
			case 2:
				menuGestionUtilisateurs(scanner, user);
				break;
			case 3:
				menuGestionLibraires(scanner, user);
			case 4:
				LibrairieApp.deconnexion();
			default:
				break;
		}
		
	}
	
	public static void menuGestionLivres(Scanner scanner, Admin user) {
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Ajouter un livre ");
		System.out.println("4- Supprimer un livre ");
		System.out.println("5- Modifier un livre ");
		System.out.println("6- Retourner au menu principal ");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				int isbn = scanner.nextInt();
				user.rechercherLivreByIsbn(isbn);
				break;
			case 2:
				System.out.print("Titre: ");
				String titre = scanner.next();
				user.rechercherLivreByTitre(titre);
				break;
			case 3:
				user.ajouterLivre();
				menuLibraire(user, scanner);
				break;
			case 4:
				System.out.print("Veuillez saisir l'isbn du livre à supprimer: ");
				int isbnToDelete = scanner.nextInt();
				user.supprimerLivre(isbnToDelete);
				break;
			case 5:
				System.out.print("Veuillez saisir l'isbn du livre à modifier: ");
				int isbnToUpdate = scanner.nextInt();
				user.modifierLivre(isbnToUpdate);
				menuLibraire(user, scanner);
				break;
			case 6:
				menuAdmin(user,scanner);
				break;
			default:
				break;
		}
	}
	
	public static void menuGestionUtilisateurs(Scanner scanner, Admin user) {
		user.consulterUtilisateurs();
		System.out.println("1- rechercher utilisateur par id ");
		System.out.println("2- Rechercher utilisateur par nom et/ou prenom ");
		System.out.println("3- Ajouter un utilisateur ");
		System.out.println("4- Supprimer un utilisateur ");
		System.out.println("5- Modifier un utilisateur ");
		System.out.println("6- Retourner au menu principal ");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix){
			case 1:
				System.out.print("~ Id: ");
				int id = scanner.nextInt();
				user.rechercherUtilisateurById(id);
				break;
			case 2:
				System.out.print("~ Nom et/ou prénom: ");
				String nomPrenom = scanner.nextLine();
				user.rechercherUtilisateurByNomPrenom(nomPrenom);
				break;
			case 3:
				user.ajouterUtilisateur();
				menuGestionUtilisateurs(scanner, user);
				break;
			case 4:
				System.out.print("Veuillez saisir l'id de l'utilisateur à supprimer: ");
				int idToDelete = scanner.nextInt();
				user.supprimerUtilisateur(idToDelete);
				break;
			case 5:
				System.out.print("Veuillez saisir l'id de l'utilisateur à modifier: ");
				int idToUpdate = scanner.nextInt();
				user.modifierUtilisateur(idToUpdate);
				menuGestionUtilisateurs(scanner, user);
				break;
			case 6:
				menuAdmin(user,scanner);
				break;
			default:
				break;
		}
	}
	
	
	
	
	/*TO COMPLETE*/
	public static void menuGestionLibraires(Scanner scanner, Admin user) {
		user.consulterUtilisateurs();
		System.out.println("1- rechercher libraire par id ");
		System.out.println("2- Rechercher libraire par nom et/ou prenom ");
		System.out.println("3- Ajouter un libraire ");
		System.out.println("4- Supprimer un libraire ");
		System.out.println("5- Modifier un libraire ");
		System.out.println("6- Retourner au menu principal ");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6};
		int choix = getChoix(scanner, choixPossibles);
		switch(choix){
			case 1:
				System.out.print("~ Id: ");
				int id = scanner.nextInt();
				user.rechercherLibraireById(id);
				break;
			case 2:
				System.out.print("~ Nom et/ou prénom: ");
				String nomPrenom = scanner.nextLine();
				user.rechercherLibraireByNomPrenom(nomPrenom);
				break;
			case 3:
				user.ajouterLibraire();
				menuGestionLibraires(scanner, user);
				break;
			case 4:
				System.out.print("Veuillez saisir l'id du libraire à supprimer: ");
				int idToDelete = scanner.nextInt();
				user.supprimerLibraire(idToDelete);
				break;
			case 5:
				System.out.print("Veuillez saisir l'id du libraire à modifier: ");
				int idToUpdate = scanner.nextInt();
				user.modifierLibraire(idToUpdate);
				menuGestionLibraires(scanner, user);
				break;
			case 6:
				menuAdmin(user,scanner);
				break;
			default:
				break;
		}
	}

	
	public static int getChoix(Scanner scanner, int... choixPossibles) {
		String choixString = scanner.next();
		int choix;
		if (choixString.matches("\\d+")) {
			if(Arrays.stream(choixPossibles).anyMatch(x -> x == Integer.parseInt(choixString))) {
				choix = Integer.parseInt(choixString);
			}else {
				System.out.print("Choix invalide!! Veuillez saisir un choix valide (ou entrez 3 pour quitter): ");
				choix = getChoix(scanner, choixPossibles);	
			}
		}else {
			System.out.print("Choix invalide!! Veuillez saisir un choix valide (ou entrez 3 pour quitter): ");
			choix = getChoix(scanner, choixPossibles);	
		}
		return choix;
	}
	
}
