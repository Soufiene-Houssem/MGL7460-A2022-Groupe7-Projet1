package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.DbConnection;
import models.GestionLibraires;
import models.GestionUtilisateurs;
import models.PwdEncrypt;

/**
 * 
 * @author houss
 *
 */
public class Admin extends Libraire implements GestionUtilisateurs,GestionLibraires{
	
	public Admin(String nom, String prenom, String email, String password, String adresse, int telephone, int role) {
		super(nom, prenom, email, password, adresse, telephone, role);
	}
	
	public Admin() {super();}
	
	
	/*************************************Méthodes de gestion des Utilisateurs*************************************/
	
	/**
	 * Méthode pour récupérer tout les utilisateurs
	 */
	public List<Utilisateur> consulterUtilisateurs(){
		
		List<Utilisateur> users = new ArrayList<>();
		try {
			PreparedStatement selectUtilisateurs = DbConnection.connect().prepareStatement("select * from utilisateur where role = 1");
			ResultSet resultSet = selectUtilisateurs.executeQuery();
			while(resultSet.next()) {
				Utilisateur user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				users.add(user);
				System.out.println(user.toString());
			}
			selectUtilisateurs.close();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Aucun utilisateur n'a été trouvé!");
		}
		return users;
	}
	
	
	
	/**
	 * Méthode d'ajout d'un utilisateur
	 */
	public boolean ajouterUtilisateur() {
		
		boolean isAdded = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un utilisateur~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le nom: ");
		String nom = sc.next();	
		System.out.print("Entrez le prenom: ");
		String prenom = sc.next();	
		System.out.print("Entrez l'adresse: ");
		String adresse = sc.next();	
		System.out.print("Entrez le numéro de téléphone: ");
		int telephone = sc.nextInt();	
		System.out.print("Entrez l'email: ");
		String email = sc.next();
		try{
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("!!!L'email saisi existe déja!!!");
				preparedStatement.close();
				ajouterUtilisateur();
			}else {
				System.out.print("Entrez le mot de passe: ");
				String password = sc.next();		
				System.out.print("Veuillez confirmer le mot de passe: ");
				String passwordCheck = sc.next();	
				if ( password.equals(passwordCheck) ) {
					
			        /* Générer le Salt. */  
			        String saltvalue = PwdEncrypt.getSaltvalue(30);  
			        
			        /* Générer un mot de passe crypté pour l'enregister a la base de données */
			        String encryptedpassword = PwdEncrypt.generateSecurePassword(password, saltvalue);  
			  
			        try{
						preparedStatement = DbConnection.connect()
								.prepareStatement("insert into utilisateur(nom, prenom, adresse, telephone, email, role, password, salt) values (?, ?, ?, ?, ?, ?, ?, ?)");
						preparedStatement.setString(1, nom);
						preparedStatement.setString(2, prenom);
						preparedStatement.setString(3, adresse);
						preparedStatement.setInt(4, telephone);
						preparedStatement.setString(5, email);
						preparedStatement.setInt(6, 1);
						preparedStatement.setString(7, encryptedpassword);
						preparedStatement.setString(8, saltvalue);
						boolean result = preparedStatement.executeUpdate() > 0;
						System.out.println(result);
						preparedStatement.close();
						isAdded = true;
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}
			preparedStatement.close();
		} catch ( Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		return isAdded;
	}
	
	
	/**
	 * @param id
	 * @return true si l'utilisateur d'id saisi est supprimé
	 */
	public boolean supprimerUtilisateur(int id) {
		
		boolean isDeleted = false;
		List<Utilisateur> users = new ArrayList<>();
		try {
			users = consulterUtilisateurs();
			for (int i=0; i<users.size(); i++){
				if(users.get(i).getId()==id) {
					PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("delete from utilisateur where id = ?");
					preparedStatement.setInt(1, id);
					if ( preparedStatement.executeUpdate() > 0 ) {
						users.remove(users.get(i));
						System.out.println("Utilisateur supprimé");
						isDeleted = true;
					}else {
						System.out.println("Erreur! Utilisateur n'a pas été supprimé!");
					}
					preparedStatement.close();
					break;
				}
			};
			
		} catch(Exception e) {
			users = null;
			e.printStackTrace();
		}
		return isDeleted;
	}

	
	/**
	 * @param nomPrenom
	 * @return liste des utilisateurs qui ont le nom et prenom qui contienent chaine saisie
	 */
	public List<Utilisateur> chercherUtilisateurByNomPrenom(String nomPrenom) {
		List<Utilisateur> users = new ArrayList<>();
		try {
			String sqlQuery = "select id,nom,prenom,adresse,telephone,email,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
					+ " from utilisateur"
					+ " where role = 1 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			nomPrenom = "%"+nomPrenom+"%";
			preparedStatement.setString(1, nomPrenom);
			preparedStatement.setString(2, nomPrenom);
			preparedStatement.setString(3, nomPrenom);
			preparedStatement.setString(4, nomPrenom);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Utilisateur user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				users.add(user);
				System.out.println(user.toString());
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * @param nomPrenom
	 * @return liste des utilisateurs qui ont le nom et prenom qui contienent chaine saisie
	 */
	public Utilisateur chercherUtilisateurById(int id) {
		Utilisateur user = new Utilisateur();
		try {
			String sqlQuery = "select * from utilisateur where role = 1 and id = ?";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	
	/**
	 * Méthode pour la modification d'un utilisateur
	 */
	public boolean modifierUtilisateur(Utilisateur userModifications) {
		
		boolean isModified = false;
		try {
			PreparedStatement selectUtilisateurAModifier = DbConnection.connect().prepareStatement("select * from utilisateur where id =?");
			selectUtilisateurAModifier.setInt(1, userModifications.getId());
			ResultSet resultSet = selectUtilisateurAModifier.executeQuery();
			if (resultSet.next()) {
				Utilisateur user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				if(!user.getEmail().equals(userModifications.getEmail())) {
					try {
						PreparedStatement checkEmailExists = DbConnection.connect()
								.prepareStatement("select * from utilisateur where email = ?");
						checkEmailExists.setString(1, userModifications.getEmail());
						ResultSet result = checkEmailExists.executeQuery();
						if(result.next()) {
							System.out.println("!!!L'email saisi existe déja!!!");
							selectUtilisateurAModifier.close();
						}else {
							PreparedStatement updateUtilisateur = DbConnection.connect()
									.prepareStatement("update utilisateur set nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? where id = ?");
							updateUtilisateur.setString(1, userModifications.getNom());
							updateUtilisateur.setString(2, userModifications.getPrenom());
							updateUtilisateur.setString(3, userModifications.getAdresse());
							updateUtilisateur.setInt(4, userModifications.getTelephone());
							updateUtilisateur.setString(5, userModifications.getEmail());
							isModified = updateUtilisateur.executeUpdate() > 0 ;
							updateUtilisateur.close();
						}
						checkEmailExists.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			selectUtilisateurAModifier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isModified;
	}
	
	/*************************************Méthodes de gestion des Libraires*************************************/
	
	
	public List<Libraire> consulterLibraires(){
			
			List<Libraire> libraires = new ArrayList<>();
			try {
				String sqlQuery = "select * from utilisateur inner join libraire on utilisateur.id = libraire.id_utilisateur where role = 2";
				PreparedStatement selectLibraires = DbConnection.connect().prepareStatement(sqlQuery);
				ResultSet resultSet = selectLibraires.executeQuery();
				while(resultSet.next()) {
					Libraire libraire = new Libraire();
					libraire.setId(resultSet.getInt("id"));
					libraire.setNom(resultSet.getString("nom"));
					libraire.setPrenom(resultSet.getString("prenom"));
					libraire.setAdresse(resultSet.getString("adresse"));
					libraire.setTelephone(resultSet.getInt("telephone"));
					libraire.setEmail(resultSet.getString("email"));
					libraire.setNumeroBadge(resultSet.getInt("numeroBadge"));
					libraires.add(libraire);
					System.out.println(libraire.toString());
				}
				selectLibraires.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Aucun utilisateur n'a été trouvé!");
			}
			return libraires;
		}
	
	
	/**
	 * Méthode d'ajout d'un utilisateur
	 */
	public boolean ajouterLibraire() {
		
		boolean isAdded = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un libraire~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le nom: ");
		String nom = sc.next();	
		System.out.print("Entrez le prenom: ");
		String prenom = sc.next();	
		System.out.print("Entrez l'adresse: ");
		String adresse = sc.next();	
		System.out.print("Entrez le numéro de téléphone: ");
		int telephone = sc.nextInt();	
		System.out.print("Entrez l'email: ");
		String email = sc.next();
		try{
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("!!!L'email saisi existe déja!!!");
				preparedStatement.close();
				ajouterUtilisateur();
			}else {
				System.out.print("Entrez le mot de passe: ");
				String password = sc.next();		
				System.out.print("Veuillez confirmer le mot de passe: ");
				String passwordCheck = sc.next();	
				if ( password.equals(passwordCheck) ) {
					
			        /* Générer le Salt. */  
			        String saltvalue = PwdEncrypt.getSaltvalue(30);  
			        
			        /* Générer un mot de passe crypté pour l'enregister a la base de données */
			        String encryptedpassword = PwdEncrypt.generateSecurePassword(password, saltvalue);  
			  
			        try{
						preparedStatement = DbConnection.connect()
								.prepareStatement("insert into utilisateur(nom, prenom, adresse, telephone, email, role, password, salt) values (?, ?, ?, ?, ?, ?, ?, ?)");
						preparedStatement.setString(1, nom);
						preparedStatement.setString(2, prenom);
						preparedStatement.setString(3, adresse);
						preparedStatement.setInt(4, telephone);
						preparedStatement.setString(5, email);
						preparedStatement.setInt(6, 2);
						preparedStatement.setString(7, encryptedpassword);
						preparedStatement.setString(8, saltvalue);
						boolean result = preparedStatement.executeUpdate() > 0;
						if (result) {
							preparedStatement = DbConnection.connect()
									.prepareStatement("insert into libraire(id_utilisateur) select id from utilisateur where email = ?");
							preparedStatement.setString(1, email);
							preparedStatement.executeUpdate();
						}
						preparedStatement.close();
						isAdded = true;
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}
			preparedStatement.close();
		} catch ( Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		return isAdded;
	}
	
	
	/**
	 * @param id
	 * @return true si le libraire d'id saisi est supprimé
	 */
	public boolean supprimerLibraire(int id) {
		
		boolean isDeleted = false;
		List<Libraire> libaires = new ArrayList<>();
		try {
			libaires = consulterLibraires();
			for (int i=0; i<libaires.size(); i++){
				if(libaires.get(i).getId()==id) {
					String sqlQuery = "delete from libraire where id_utilisateur = ?";
					PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
					preparedStatement.setInt(1, id);
					if ( preparedStatement.executeUpdate() > 0 ) {
						preparedStatement = DbConnection.connect().prepareStatement("delete from utilisateur where id = ?");
						preparedStatement.setInt(1, id);
						preparedStatement.executeUpdate();
						libaires.remove(libaires.get(i));
						System.out.println("Libraire supprimé");
						isDeleted = true;
					}else {
						System.out.println("Erreur! Libraire n'a pas été supprimé!");
					}
					preparedStatement.close();
					break;
				}
			};
			
		} catch(Exception e) {
			libaires = null;
			System.out.println(e.getMessage());
		}
		return isDeleted;
	}
	
	/**
	 * @param nomPrenom
	 * @return liste des libraires qui ont le nom et prenom qui contienent chaine saisie
	 */
	public List<Libraire> chercherLibraireByNomPrenom(String nomPrenom) {
		List<Libraire> libraires = new ArrayList<>();
		try {
			String sqlQuery = "select id,nom,prenom,adresse,telephone,email,numeroBadge,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
					+ " from utilisateur inner join libraire on utilisateur.id = libraire.id_utilisateur"
					+ " where role = 2 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			nomPrenom = "%"+nomPrenom+"%";
			preparedStatement.setString(1, nomPrenom);
			preparedStatement.setString(2, nomPrenom);
			preparedStatement.setString(3, nomPrenom);
			preparedStatement.setString(4, nomPrenom);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				while(resultSet.next()) {
					Libraire libraire = new Libraire();
					libraire.setId(resultSet.getInt("id"));
					libraire.setNom(resultSet.getString("nom"));
					libraire.setPrenom(resultSet.getString("prenom"));
					libraire.setAdresse(resultSet.getString("adresse"));
					libraire.setTelephone(resultSet.getInt("telephone"));
					libraire.setEmail(resultSet.getString("email"));
					libraire.setNumeroBadge(resultSet.getInt("numeroBadge"));
					libraires.add(libraire);
					System.out.println(libraire.toString());
				}
			}else {
				System.out.println("Il n'y a aucun libraire avec ce nom ou prenom!");
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return libraires;
	}
	
	/**
	 * @param nomPrenom
	 * @return liste des libraire qui ont le nom et prenom qui contienent chaine saisie
	 */
	public Libraire chercherLibraireById(int id) {
		Libraire libraire = new Libraire();
		try {
			String sqlQuery = "select * from utilisateur inner join libraire on utilisateur.id = libraire.id_utilisateur where role = 2 and id = ?";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				libraire.setId(resultSet.getInt("id"));
				libraire.setNom(resultSet.getString("nom"));
				libraire.setPrenom(resultSet.getString("prenom"));
				libraire.setAdresse(resultSet.getString("adresse"));
				libraire.setTelephone(resultSet.getInt("telephone"));
				libraire.setEmail(resultSet.getString("email"));
				libraire.setNumeroBadge(resultSet.getInt("numeroBadge"));
				System.out.println(libraire.toString());
			}else {
				System.out.println("Il n'y a aucun libraire avec cet id");
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return libraire;
	}
	
	/**
	 * Méthode pour la modification d'un libraire
	 */
	public boolean modifierLibraire(Libraire libraireModifications) {
		
		boolean isModified = false;
		try {
			PreparedStatement selectLibraireAModifier = DbConnection.connect().prepareStatement("select * from utilisateur where id =?");
			selectLibraireAModifier.setInt(1, libraireModifications.getId());
			ResultSet resultSet = selectLibraireAModifier.executeQuery();
			if (resultSet.next()) {
				Utilisateur user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				if(!user.getEmail().equals(libraireModifications.getEmail())) {
					try {
						PreparedStatement checkEmailExists = DbConnection.connect()
								.prepareStatement("select * from utilisateur where email = ?");
						checkEmailExists.setString(1, libraireModifications.getEmail());
						ResultSet result = checkEmailExists.executeQuery();
						if(result.next()) {
							System.out.println("!!!L'email saisi existe déja!!!");
							selectLibraireAModifier.close();
						}else {
							PreparedStatement updateLibraire = DbConnection.connect()
									.prepareStatement("update utilisateur set nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? where id = ?");
							updateLibraire.setString(1, libraireModifications.getNom());
							updateLibraire.setString(2, libraireModifications.getPrenom());
							updateLibraire.setString(3, libraireModifications.getAdresse());
							updateLibraire.setInt(4, libraireModifications.getTelephone());
							updateLibraire.setString(5, libraireModifications.getEmail());
							isModified = updateLibraire.executeUpdate() > 0 ;
							updateLibraire.close();
						}
						checkEmailExists.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			selectLibraireAModifier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isModified;
	}
	
}
