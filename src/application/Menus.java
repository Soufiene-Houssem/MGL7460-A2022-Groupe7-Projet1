package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author houss
 *
 */
public class Menus { // NOPMD by houss on 10/12/22 9:35 PM
	
	public static void menuUtilisateur(Utilisateur user, Scanner scanner) {
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		user.consulterLivres();
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Rechercher livre par auteur ");
		System.out.println("4- Déconnecter ");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2, 3, 4};
		int choix = getChoix(scanner, choixPossibles);
		int returnChoix;
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				try {
					int isbn = scanner.nextInt();
					user.rechercherLivreByIsbn(isbn);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'isbn contient que des chiffre!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuUtilisateur(user, scanner);
				}
				break;
			case 2:
				System.out.print("Titre: ");
				scanner.nextLine();
				String titre = scanner.nextLine();
				user.rechercherLivreByTitre(titre);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuUtilisateur(user, scanner);
				}
				break;
			case 3:
				System.out.print("Auteur: ");
				scanner.nextLine();
				String auteur = scanner.nextLine();
				user.rechercherLivreByAuteur(auteur);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuUtilisateur(user, scanner);
				}
				break;
			case 4:
				LibrairieApp.deconnexion();
				break;
			default:
				break;
		}
	}
	
	public static void menuLibraire(Libraire user, Scanner scanner) { // NOPMD by houss on 10/12/22 9:35 PM
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		user.consulterLivres();
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Rechercher livre par auteur ");
		System.out.println("4- Ajouter un livre ");
		System.out.println("5- Supprimer un livre ");
		System.out.println("6- Modifier un livre ");
		System.out.println("7- Déconnecter ");
		System.out.print("Veuillez saisir votre choix: ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6, 7};
		int choix = getChoix(scanner, choixPossibles);
		int returnChoix;
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				try {
					int isbn = scanner.nextInt();
					user.rechercherLivreByIsbn(isbn);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'isbn contient que des chiffre!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 2:
				System.out.print("Titre: ");
				scanner.nextLine();
				String titre = scanner.nextLine();
				user.rechercherLivreByTitre(titre);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 3:
				System.out.print("Auteur: ");
				scanner.nextLine();
				String auteur = scanner.nextLine();
				user.rechercherLivreByAuteur(auteur);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 4:
				user.ajouterLivre();
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 5:
				System.out.print("Veuillez saisir l'isbn du livre à supprimer: ");
				try {
					int isbnToDelete = scanner.nextInt();
					user.supprimerLivre(isbnToDelete);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'isbn contient que des chiffre!");
					scanner.nextLine();
				}
				
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 6:
				System.out.print("Veuillez saisir l'isbn du livre à modifier: ");
				try {
					int isbnToUpdate = scanner.nextInt();
					scanner.nextLine();
					user.modifierLivre(isbnToUpdate);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'isbn contient que des chiffre!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuLibraire(user, scanner);
				}
				break;
			case 7:
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
				break;
			case 4:
				LibrairieApp.deconnexion();
				break;
			default:
				break;
		}
		
	}
	
	public static void menuGestionLivres(Scanner scanner, Admin user) { // NOPMD by houss on 10/12/22 9:35 PM
		user.consulterLivres();
		System.out.println("1- Rechercher livre par isbn ");
		System.out.println("2- Rechercher livre par titre ");
		System.out.println("3- Rechercher livre par auteur ");
		System.out.println("4- Ajouter un livre ");
		System.out.println("5- Supprimer un livre ");
		System.out.println("6- Modifier un livre ");
		System.out.println("7- Retourner au menu principal ");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6, 7};
		int choix = getChoix(scanner, choixPossibles);
		int returnChoix;
		switch(choix){
			case 1:
				System.out.print("Isbn: ");
				try {
					int isbn = scanner.nextInt();
					user.rechercherLivreByIsbn(isbn);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'isbn contient que des chiffre!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 2:
				System.out.print("Titre: ");
				scanner.nextLine();
				String titre = scanner.nextLine();
				user.rechercherLivreByTitre(titre);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 3:
				System.out.print("Auteur: ");
				scanner.nextLine();
				String auteur = scanner.nextLine();
				user.rechercherLivreByAuteur(auteur);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 4:
				user.ajouterLivre();
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 5:
				System.out.print("Veuillez saisir l'isbn du livre à supprimer: ");
				try {
					int isbnToDelete = scanner.nextInt();
					user.supprimerLivre(isbnToDelete);
				} catch(InputMismatchException  | NullPointerException ex) {
					System.out.println("Attention! l'isbn est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 6:
				System.out.print("Veuillez saisir l'isbn du livre à modifier: ");
				try {
					int isbnToUpdate = scanner.nextInt();
					user.modifierLivre(isbnToUpdate);
				} catch(InputMismatchException  | NullPointerException ex) {
					System.out.println("Attention! l'isbn est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLivres(scanner, user);
				}
				break;
			case 7:
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
		int returnChoix;
		switch(choix){
			case 1:
				System.out.print("~ Id: ");
				try {
					int id = scanner.nextInt();
					user.rechercherUtilisateurById(id);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'id contient que des chiffres!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionUtilisateurs(scanner, user);
				}
				break;
			case 2:
				System.out.print("~ Nom et/ou prénom: ");
				scanner.nextLine();
				String nomPrenom = scanner.nextLine();
				user.rechercherUtilisateurByNomPrenom(nomPrenom);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionUtilisateurs(scanner, user);
				}
				break;
			case 3:
				user.ajouterUtilisateur();
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionUtilisateurs(scanner, user);
				}
				break;
			case 4:
				System.out.print("Veuillez saisir l'id de l'utilisateur à supprimer: ");
				try {
					int idToDelete = scanner.nextInt();
					user.supprimerUtilisateur(idToDelete);
				} catch(InputMismatchException  | NullPointerException ex) {
					System.out.println("Attention! l'id est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionUtilisateurs(scanner, user);
				}
				break;
			case 5:
				System.out.print("Veuillez saisir l'id de l'utilisateur à modifier: ");
				try {
					int idToUpdate = scanner.nextInt();
					user.modifierUtilisateur(idToUpdate);
				} catch(InputMismatchException  | NullPointerException ex) {
					System.out.println("Attention! l'id est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionUtilisateurs(scanner, user);
				}
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
		user.consulterLibraires();
		System.out.println("1- rechercher libraire par id ");
		System.out.println("2- Rechercher libraire par nom et/ou prenom ");
		System.out.println("3- Ajouter un libraire ");
		System.out.println("4- Supprimer un libraire ");
		System.out.println("5- Modifier un libraire ");
		System.out.println("6- Retourner au menu principal ");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		int [] choixPossibles = {1, 2, 3, 4, 5, 6};
		int choix = getChoix(scanner, choixPossibles);
		int returnChoix;
		switch(choix){
			case 1:
				System.out.print("~ Id: ");
				try {
					int id = scanner.nextInt();
					user.rechercherLibraireById(id);
				} catch(InputMismatchException ex) {
					System.out.println("Attention! l'id contient que des chiffres!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLibraires(scanner, user);
				}
				break;
			case 2:
				System.out.print("~ Nom et/ou prénom: ");
				scanner.nextLine();
				String nomPrenom = scanner.nextLine();
				user.rechercherLibraireByNomPrenom(nomPrenom);
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLibraires(scanner, user);
				}
				break;
			case 3:
				user.ajouterLibraire();
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLibraires(scanner, user);
				}
				break;
			case 4:
				System.out.print("Veuillez saisir l'id du libraire à supprimer: ");
				try {
					int idToDelete = scanner.nextInt();
					user.supprimerLibraire(idToDelete);
				} catch(InputMismatchException  | NullPointerException ex) {
					System.out.println("Attention! l'id est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLibraires(scanner, user);
				}
				break;
			case 5:
				System.out.print("Veuillez saisir l'id du libraire à modifier: ");
				try {
					int idToUpdate = scanner.nextInt();
					user.modifierLibraire(idToUpdate);
				} catch(InputMismatchException | NullPointerException ex) {
					System.out.println("Attention! l'id est incorrect!");
					scanner.nextLine();
				}
				returnChoix = returnMenu(scanner, choixPossibles );
				if( returnChoix==1 ) { // NOPMD by houss on 10/12/22 9:28 PM
					menuGestionLibraires(scanner, user);
				}
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
	
	public static int returnMenu(Scanner scanner, int... choixPossibles) { // NOPMD by houss on 10/12/22 9:28 PM
		System.out.println("1- retourner au menu");
		System.out.print("Entrez votre choix: ");
		choixPossibles = new int[]{ 1 };
		return getChoix(scanner, choixPossibles );
	}
	
}
