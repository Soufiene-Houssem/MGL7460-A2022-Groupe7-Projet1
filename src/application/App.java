package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import models.DbConnection;
import models.PwdEncrypt;

public class App {

	public static void main(String[] args) {
		final Scanner scanner = new Scanner(System.in);
		Utilisateur user;
		System.out.println("~~~~~~~~~~~~~~~~~~Librairie~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~                                       ~~~");
		System.out.println("~~~         1- S'authentifier             ~~~");
		System.out.println("~~~         2- Créer un compte            ~~~");
		System.out.println("~~~         3- Quitter                    ~~~");
		System.out.println("~~~                                       ~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
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
				System.out.println("~~~~~~~~~~~~~~~~~~Authentification~~~~~~~~~~~~~~~~~~");
				do {
					System.out.print("~ Email: ");
					final String email = scanner.next();
					System.out.print("~ Password: ");
					final String password = scanner.next();
					user = authentification(email, password);
				}
				while (user == null);
				switch (user.getRole()) {
					case 1:
						menuUtilisateur();
						break;
					case 2:
						menuLibraire();
						break;
					case 3:
						menuAdmin();
						break;
					default:
						scanner.close();
						break;
				}
				scanner.close();
				break;
			case 2:
				System.out.println("This is register page");
				scanner.close();
				break;
			case 3:
				System.exit(0);
				scanner.close();
				break;
			default:
				scanner.close();
				break;
		}
		scanner.close();
	}
	
	
	public static void menuUtilisateur() {
		System.out.println("This is the user's page");
	}
	
	public static void menuLibraire() {
		System.out.println("This is the libraire's page");
	}
	
	public static void menuAdmin() {
		System.out.println("This is the admin's page");
	}
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les paramètres sont correct
	 */
	public static Utilisateur authentification(final String email,final String password) {
		
		Utilisateur user = null;
		final Connection connxion = DbConnection.connect();		
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		try{
			preparedStatement = (PreparedStatement) connxion.prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				if (PwdEncrypt.verifyUserPassword(password, resultSet.getString("password"), resultSet.getString("salt"))) {
					user = new Utilisateur();
					user.setId(resultSet.getInt("id"));
					user.setNom(resultSet.getString("nom"));
					user.setPrenom(resultSet.getString("prenom"));
					user.setAdresse(resultSet.getString("adresse"));
					user.setTelephone(resultSet.getInt("telephone"));
					user.setEmail(resultSet.getString("email"));
					user.setPassword(resultSet.getString("password"));
					user.setRole(resultSet.getInt("role"));
				}else {
					System.out.println("Mot de passe incorrect!");
				}
			}else {
				System.out.println("L'email saisi ne correspond à aucun compte!");
			}
			preparedStatement.close();
			connxion.close();
		} catch ( SQLException e ) {
			System.out.println(e.getMessage());
		}

		return user;
		
	}

}

