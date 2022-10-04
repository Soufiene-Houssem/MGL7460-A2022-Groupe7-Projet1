package application;

import java.util.Scanner;

public class App {
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les param�tres sont correct
	 */
	public boolean authentification(String email, String password) {
		
		String role =null;
		try{
			Utilisateur user = new Utilisateur();
			PreparedStatement preparedStatement = (PreparedStatement) DbConnection.connect()
					.prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setRole(resultSet.getString("role"));
				String saltvalue = resultSet.getString("salt");
				
				if (PwdEncrypt.verifyUserPassword(password, user.getPassword(), saltvalue)) {
					System.out.println("Connect�! mot de passe correct!");
					role = user.getRole();
					
				}else {
					System.out.println("Mot de passe incorrect!");
				}
			}
			
			preparedStatement.close();
		} catch ( Exception e ) {
			System.out.println("l'�mail ou le mot de passe est incorrect!");
		}
		return role;
		
	}
	//Menu Principal
	
	public static void menuPrincipal(){
		System.out.println("^^^^^^GESTION D'UNE LIBRAIRIE^^^^^^");
		System.out.println("1.Creation de compte ");
		System.out.println("2.Connection ");
		System.out.println("3.Sortir ");
		System.out.println("--------------------");
		System.out.println("LES NUMEROS REPRESENTENT LES CHOIX: ");
		Scanner scan= new Scanner(System.in);
		choix=scan.nextInt();
		if (choix=1) {
			Admin admin= new Admin()
			admin.ajouterUtilisateur();
		}
		if(choix=2) {
			Scanner scan=new Scanner(System.in);
			System.out.println("SAISIR L'EMAIL:");
			String email=scan.next();
			System.out.println("SAISIR LE MOT DE PASSE:");
			String pwd=scan.next();
			String role=authentification(email,pwd);
			if (role.equals("normal")) {
				this.menuUtilisateurNormal();
			}
			if(role.equals("libraire")) {
				this.menuLibraire();
			}
			if (role.equals("admin")) {
				this.menuAdmin();
			}
		}
		if (choix=3) {
			this.menuPrincipal();
		}
		
	}
	//Menu pour l'utilisateur normal
	
	public static void menuUtilisateurNormal() {
		
	}
	//Menu pour le libraire
	
	public static void menuLibraire() {
		
	}
	//Menu pour l'admin
	
	public static void menuAdmin() {
		
	}
	
	public static void main(String[] args) {
		Utilisateur user = new Utilisateur();
		user.rechercherLivresByTitre("programmer");
		//Admin admin = new Admin();
		//admin.supprimerLibraire(5);
		//admin.consulterLibraires();
		//admin.chercherLibraireById(1);

	}

}

