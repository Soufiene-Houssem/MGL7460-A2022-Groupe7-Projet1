package gestionLibrairie;

//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
//import gestionLibrairie.Abonnes;
public class Menu {
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les param�tres sont correct
	 */
	public String authentification(String email, String password) {
		
		String role =null;
		try{
			Abonnes user = new Abonnes();
			PreparedStatement preparedStatement = (PreparedStatement) user.connect(Abonnes.DBNAME)
					.prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				//user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAddresse(resultSet.getString("adresse"));
				user.setPhone(resultSet.getString("telephone"));
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
		try (Scanner scan = new Scanner(System.in)) {
			int choix=scan.nextInt();
			if (choix==1) {
				try (Scanner sc = new Scanner(System.in)) {
					System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un utilisateur~~~~~~~~~~~~~~~~~\n");
					System.out.print("Entrez le nom: ");
					String nom = sc.next();	
					System.out.print("Entrez le prenom: ");
					String prenom = sc.next();	
					System.out.print("Entrez l'adresse: ");
					String adresse = sc.next();	
					System.out.print("Entrez le num�ro de t�l�phone: ");
					String telephone = sc.next();	
					System.out.print("Entrez l'email: ");
					String email = sc.next();
					System.out.print("Entrez le mot de passe: ");
					String password = sc.next();
					Abonnes admin= new Abonnes(nom,prenom,adresse,telephone,email,password);
					boolean result= admin.ajout(admin);
					if(result==true) {
						System.out.println("un utilisateur a ete ajoute avec success. Continuer: ");
						menuPrincipal();
					}
					else {
						System.out.println("l'ajout de l'utilisateur a echoue. Veuillez resseyer: ");
						menuPrincipal();
					}
				}
			}
			if(choix==2) {
				try (Scanner sc = new Scanner(System.in)) {
					System.out.println("\n~~~~~~~~~~~~~~~~~AUTHENTIFICATION~~~~~~~~~~~~~~~~~\n");
					System.out.println("SAISIR L'EMAIL:");
					String email=sc.next();
					System.out.println("SAISIR LE MOT DE PASSE:");
					String pwd=sc.next();
					Menu menu=new Menu();
					String role=menu.authentification(email,pwd);
					if (role.equals("normal")) {
						menuUtilisateurNormal();
					}
					if(role.equals("libraire")) {
						menuLibraire();
					}
					if (role.equals("admin")) {
						menuAdmin();
					}
				}
			}
			if (choix==3) {
				menuPrincipal();
			}
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
		menuPrincipal();
		//Utilisateur user = new Utilisateur();
		//user.rechercherLivresByTitre("programmer");
		//Admin admin = new Admin();
		//admin.supprimerLibraire(5);
		//admin.consulterLibraires();
		//admin.chercherLibraireById(1);

	}

}
