package application;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import services.UtilisateurService;
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
					user = authentification(scanner);
				}
				while (user == null);
				switch (((Utilisateur) user).getRole()) {
					case 1:
						Menus.menuUtilisateur((Utilisateur)user, scanner);
						break;
					case 2:
						Menus.menuLibraire((Libraire) user, scanner);
						break;
					case 3:
						Menus.menuAdmin((Admin) user, scanner);
						break;
					default:
						break;
				}
				break;
			case 2:
				inscription(scanner);
				authentification(scanner);
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
	
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les paramètres sont correct
	 * @throws SQLException 
	 */
	public static Object authentification(Scanner scanner) {
		Object user = null;  // NOPMD by houss on 10/8/22 6:39 PM
		System.out.print("~ Email: ");
		final String email = scanner.next();
		System.out.print("~ Password: ");
		final String password = scanner.next();
		try (Connection connexion =  DbConnection.getInstance().getConnexion()){
			PreparedStatement preparedStatement = connexion.prepareStatement("select * from utilisateur where email = ?"); // NOPMD by houss on 10/8/22 5:05 PM
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery(); // NOPMD by houss on 10/8/22 5:05 PM
			if(resultSet.next() && PwdEncrypt.verifyUserPassword(password, resultSet.getString("password"), resultSet.getString("salt"))) {
				int role = resultSet.getInt("role");
				String pwd = resultSet.getString("password"); // NOPMD by houss on 10/8/22 11:58 PM
				switch (role) {
					case 1:
						user = new Utilisateur(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role);
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
					case 2:
						user = new Libraire(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role);
						resultSet.close();
						preparedStatement.close();
						return user; // NOPMD by houss on 10/8/22 5:06 PM
					case 3: 
						user = new Admin(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
								pwd, resultSet.getString("adresse"), resultSet.getInt("telephone"), role);
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
	
	public static void inscription(Scanner scanner) {
		UtilisateurService userService = new UtilisateurService(); // NOPMD by houss on 10/9/22 3:19 PM
		System.out.println("\n\t\t~~~~~~~~~~~~~~~Inscription~~~~~~~~~~~~~~~");
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
			}else {
				System.out.println("Utilisateur n'a pas été ajouté!");
				inscription(scanner);
			}
		}else {
			System.out.println("\n\nMot de passe n'a pas été confirmé!");
			inscription(scanner);
		}
		
	}
	

}
