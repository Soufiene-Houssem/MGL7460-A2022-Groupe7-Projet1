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
	
	public Admin(final String nom, final String prenom, final String email, final String password, final String adresse, final int telephone, final int role) {
		super(nom, prenom, email, password, adresse, telephone, role);
	}
	
	public Admin() {super();}
	
	
	/*************Méthodes de gestion des Utilisateurs*******************/
	
	/**
	 * Méthode pour récupérer tout les utilisateurs
	 */
	@Override
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
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Aucun utilisateur n'a été trouvé!");
		}
		return users;
	}
	
	
	
	/**
	 * Méthode d'ajout d'un utilisateur
	 */
	@Override
	public boolean ajouterUtilisateur() {
		
		boolean isAdded = false;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n~~~~~~~~~~~~~~~~~Ajouter un utilisateur~~~~~~~~~~~~~~~~~\n");
		System.out.print("Entrez le nom: ");
		final String nom = sc.next();	
		System.out.print("Entrez le prenom: ");
		final String prenom = sc.next();	
		System.out.print("Entrez l'adresse: ");
		final String adresse = sc.next();	
		System.out.print("Entrez le numéro de téléphone: ");
		final int telephone = sc.nextInt();	
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
					} catch ( SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		return isAdded;
	}
	
	
	/**
	 * @param id
	 * @return true si l'utilisateur d'id saisi est supprimé
	 */
	@Override
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
			
		} catch(SQLException e) {
			users = null;
			System.out.println(e.getMessage());
		}
		return isDeleted;
	}

	
	/**
	 * @param nomPrenom
	 * @return liste des utilisateurs qui ont le nom et prenom qui contienent chaine saisie
	 */
	@Override
	public List<Utilisateur> chercherUtilisateurByNomPrenom(String nomPrenom) {
		List<Utilisateur> users = new ArrayList<>();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			String sqlQuery = "select id,nom,prenom,adresse,telephone,email,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
					+ " from utilisateur"
					+ " where role = 1 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setString(1, "%"+nomPrenom+"%");
			preparedStatement.setString(2, "%"+nomPrenom+"%");
			preparedStatement.setString(3, "%"+nomPrenom+"%");
			preparedStatement.setString(4, "%"+nomPrenom+"%");
			resultSet = preparedStatement.executeQuery();
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
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
	
	/**
	 * @param nomPrenom
	 * @return liste des utilisateurs qui ont le nom et prenom qui contienent chaine saisie
	 */
	@Override
	public Utilisateur chercherUtilisateurById(int id) {
		Utilisateur user = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			final String sqlQuery = "select * from utilisateur where role = 1 and id = ?";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	
	
	/**
	 * Méthode pour la modification d'un utilisateur
	 */
	@Override
	public boolean modifierUtilisateur(Utilisateur userModifications) {
		
		boolean isModified = false;
		PreparedStatement selectUtilisateurAModifier;
		ResultSet resultSet;
		try {
			selectUtilisateurAModifier = DbConnection.connect().prepareStatement("select * from utilisateur where id =?");
			selectUtilisateurAModifier.setInt(1, userModifications.getId());
			resultSet = selectUtilisateurAModifier.executeQuery();
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
						System.out.println(e.getMessage());
					}
				}
			}
			resultSet.close();
			selectUtilisateurAModifier.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isModified;
	}
	
	/****************Méthodes de gestion des Libraires**********************/
	
	
	@Override
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
			} catch(SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("Aucun utilisateur n'a été trouvé!");
			}
			return libraires;
		}
	
	
	/**
	 * Méthode d'ajout d'un utilisateur
	 */
	@Override
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
					} catch ( SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		sc.close();
		return isAdded;
	}
	
	
	/**
	 * @param id
	 * @return true si le libraire d'id saisi est supprimé
	 */
	@Override
	public boolean supprimerLibraire(int id) {
		
		boolean isDeleted = false;
		List<Libraire> libraires;
		PreparedStatement preparedStatement;
		try {
			libraires = consulterLibraires();
			for (Libraire libraire : libraires) {
				if(libraire.getId()==id) {
					String sqlQuery = "delete from libraire where id_utilisateur = ?";
					preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
					preparedStatement.setInt(1, id);
					if ( preparedStatement.executeUpdate() > 0 ) {
						preparedStatement = DbConnection.connect().prepareStatement("delete from utilisateur where id = ?");
						preparedStatement.setInt(1, id);
						preparedStatement.executeUpdate();
						libraires.remove(libraire);
						System.out.println("Libraire supprimé");
						isDeleted = true;
					}else {
						System.out.println("Erreur! Libraire n'a pas été supprimé!");
					}
					preparedStatement.close();
					break;
				}
			}
			
		} catch(SQLException e) {
			libraires = null;
			System.out.println(e.getMessage());
		}
		return isDeleted;
	}
	
	/**
	 * @param nomPrenom
	 * @return libraires qui ont le nom et prenom qui contienent chaine saisie
	 */
	@Override
	public List<Libraire> chercherLibraireByNomPrenom(String nomPrenom) {
		List<Libraire> libraires = new ArrayList<>();
		Libraire libraire = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			final String sqlQuery = "select id,nom,prenom,adresse,telephone,email,numeroBadge,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
					+ " from utilisateur inner join libraire on utilisateur.id = libraire.id_utilisateur"
					+ " where role = 2 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setString(1, "%"+nomPrenom+"%");
			preparedStatement.setString(2, "%"+nomPrenom+"%");
			preparedStatement.setString(3, "%"+nomPrenom+"%");
			preparedStatement.setString(4, "%"+nomPrenom+"%");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				libraire = new Libraire();
				while(resultSet.next()) {
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
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return libraires;
	}
	
	/**
	 * @param nomPrenom
	 * @return liste des libraire qui ont le nom et prenom qui contienent chaine saisie
	 */
	@Override
	public Libraire chercherLibraireById(int id) {
		Libraire libraire = new Libraire();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			final String sqlQuery = "select * from utilisateur inner join libraire on utilisateur.id = libraire.id_utilisateur where role = 2 and id = ?";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
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
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return libraire;
	}
	
	/**
	 * Méthode pour la modification d'un libraire
	 */
	public boolean modifierLibraire(Libraire libraireModifications) {
		
		boolean isModified = false;
		PreparedStatement selectLibraireAModifier, checkEmailExists, updateLibraire;
		Utilisateur user = null;
		ResultSet resultSet, result;
		try {
			selectLibraireAModifier = DbConnection.connect().prepareStatement("select * from utilisateur where id =?");
			selectLibraireAModifier.setInt(1, libraireModifications.getId());
			resultSet = selectLibraireAModifier.executeQuery();
			if (resultSet.next()) {
				user = new Utilisateur();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setAdresse(resultSet.getString("adresse"));
				user.setTelephone(resultSet.getInt("telephone"));
				user.setEmail(resultSet.getString("email"));
				if(!user.getEmail().equals(libraireModifications.getEmail())) {
					try {
						checkEmailExists = DbConnection.connect()
								.prepareStatement("select * from utilisateur where email = ?");
						checkEmailExists.setString(1, libraireModifications.getEmail());
						result = checkEmailExists.executeQuery();
						if(result.next()) {
							System.out.println("!!!L'email saisi existe déja!!!");
							selectLibraireAModifier.close();
						}else {
							updateLibraire = DbConnection.connect()
									.prepareStatement("update utilisateur set nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? where id = ?");
							updateLibraire.setString(1, libraireModifications.getNom());
							updateLibraire.setString(2, libraireModifications.getPrenom());
							updateLibraire.setString(3, libraireModifications.getAdresse());
							updateLibraire.setInt(4, libraireModifications.getTelephone());
							updateLibraire.setString(5, libraireModifications.getEmail());
							isModified = updateLibraire.executeUpdate() > 0 ;
							updateLibraire.close();
						}
						result.close();
						checkEmailExists.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			resultSet.close();
			selectLibraireAModifier.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isModified;
	}
	
}
