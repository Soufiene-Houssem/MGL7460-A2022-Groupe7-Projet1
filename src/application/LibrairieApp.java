package application;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import utils.DbConnection;
import utils.PwdEncrypt;

public class LibrairieApp {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // NOPMD by houss on 10/8/22 5:05 PM
		Object user;
		System.out.println("\t\t~~~~~~~~~~~~~~~~~~Librairie~~~~~~~~~~~~~~~~~~");
		System.out.println("\t\t~~~                                       ~~~");
		System.out.println("\t\t~~~         1- S'authentifier             ~~~");
		System.out.println("\t\t~~~         2- Créer un compte            ~~~");
		System.out.println("\t\t~~~         3- Quitter                    ~~~");
		System.out.println("\t\t~~~                                       ~~~");
		System.out.println("\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
		String choixString = scanner.next();
		int choix;
		while(!choixString.matches("\\d+") || Integer.parseInt(choixString) != 1 && Integer.parseInt(choixString) != 2 && Integer.parseInt(choixString) != 3 ) {
			System.out.print("Choix invalide!! Veuillez saisir un choix valide (ou entrez 3 pour quitter): ");
			choixString = scanner.next();
		}
		choix = Integer.parseInt(choixString);
		switch(choix){
			case 1:
				System.out.println("\n\t\t~~~~~~~~~~~~~~~Authentification~~~~~~~~~~~~~~~");
				do {
					System.out.print("~ Email: ");
					final String email = scanner.next();
					System.out.print("~ Password: ");
					final String password = scanner.next();
					user = authentification(email, password);
				}
				while (user == null);
				switch (((Utilisateur) user).getRole()) {
					case 1:
						menuUtilisateur((Utilisateur) user, scanner);
						break;
					case 2:
						menuLibraire((Libraire) user, scanner);
						break;
					case 3:
						menuAdmin((Admin) user, scanner);
						break;
					default:
						break;
				}
				break;
			case 2:
				System.out.println("This is register page");
				break;
			case 3:
				System.out.println("Au revoir :)");
				System.exit(0);
				break;
			default:
				break;
		}
		scanner.close();
	}
	
	
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
				deconnexion();
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
		System.out.print("Veuillez saisir votre choix (Chiffre): ");
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
				
			case 4:
				
			case 5:
				
			case 6:
				deconnexion();
				break;
			default:
				break;
		}
	}
	
	public static void menuAdmin(Admin user, Scanner scanner) {
		System.out.println("\n\nBienvenue "+user.getNom()+" "+user.getPrenom());
		System.out.println("This is the admin's page");
		user.consulterLivres();
		boolean isAdded = user.ajouterUtilisateur();
		while(!isAdded) {
			isAdded = user.ajouterUtilisateur();
		}
	}
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les paramètres sont correct
	 * @throws SQLException 
	 */
	public static Object authentification(final String email,final String password) {
		
		Object user = null;  // NOPMD by houss on 10/8/22 6:39 PM
		try (Connection connexion =  DbConnection.getInstance().getConnexion()){
			PreparedStatement preparedStatement = connexion.prepareStatement("select * from utilisateur where email = ?"); // NOPMD by houss on 10/8/22 5:05 PM
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery(); // NOPMD by houss on 10/8/22 5:05 PM
			if(resultSet.next() && PwdEncrypt.verifyUserPassword(password, resultSet.getString("password"), resultSet.getString("salt"))) {
				int role = resultSet.getInt("role");
				String pwd = resultSet.getString("password");
				switch (role) {
					case 1:
						user = new Utilisateur(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role);
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
					case 2:
						user = new Libraire(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role, resultSet.getInt("numeroBadge"));
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
					case 3: 
						user = new Admin(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role, 0);
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
					default:
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
				}
				
			}
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e ) {
			System.out.println(e.getMessage());
			System.out.println("L'email ou mot de passe est incorrecte!");
		}

		return user;
		
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
	
	public static void deconnexion() {
		System.out.println("Vous êtes déconnecté! Au revoir :)");
		String[] args = {};
		main(args);
	}

}
