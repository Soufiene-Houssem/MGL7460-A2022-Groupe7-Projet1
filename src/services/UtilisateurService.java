package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.Utilisateur;
import utils.PwdEncrypt;
import utils.DbConnection;

/**
 * 
 * @author houss
 *
 */
public class UtilisateurService {
	    private transient  PreparedStatement preparedStatement;
	    private transient ResultSet resultSet;
	    private final transient Connection connexion;
	    
	    public UtilisateurService() {
			super();
			this.connexion = DbConnection.getInstance().getConnexion();
		}
	    
	    public List<Utilisateur> getAllUtilisateurs(){
			
			List<Utilisateur> users = new ArrayList<>();
			try {
				preparedStatement = connexion.prepareStatement("select * from utilisateur where role = 1");
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					users.add(new Utilisateur(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
							resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role")));
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			return users;
		}
	    
	    public boolean addUtilisateur(Utilisateur user) {
	    	boolean isAdded = false;  
	        String saltvalue = PwdEncrypt.getSaltvalue(30);  // Générer le salt
	        String encryptedpassword = PwdEncrypt.generateSecurePassword(user.getPassword(), saltvalue);  //Générer un mot de passe crypté pour l'enregister a la base de données
	        try{
	        	preparedStatement = connexion.prepareStatement("select * from utilisateur where email = ?");
				preparedStatement.setString(1, user.getEmail());
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					System.out.println("!!!L'email saisi existe déja!!!");
				}else {
					preparedStatement = connexion.prepareStatement("insert into utilisateur(nom, prenom, adresse, telephone, email, role, password, salt) values (?, ?, ?, ?, ?, ?, ?, ?)");
					preparedStatement.setString(1, user.getNom());
					preparedStatement.setString(2, user.getPrenom());
					preparedStatement.setString(3, user.getAdresse());
					preparedStatement.setInt(4, user.getTelephone());
					preparedStatement.setString(5, user.getEmail());
					preparedStatement.setInt(6, 1);
					preparedStatement.setString(7, encryptedpassword);
					preparedStatement.setString(8, saltvalue);
					isAdded = preparedStatement.executeUpdate() > 0;
				}
				
			} catch ( SQLException e) {
				System.out.println(e.getMessage());
			}
			return isAdded;
	    }
	    
	    public boolean deleteUtilisateur(int id) {
	    	boolean isDeleted = false;
			try {
				preparedStatement = connexion.prepareStatement("delete from utilisateur where id = ? and role = 1");
				preparedStatement.setInt(1, id);
				isDeleted = preparedStatement.executeUpdate() > 0;
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			return isDeleted;
	    }
	    
	    public List<Utilisateur> findUtilisateurByNomPrenom(String nomPrenom) {
	    	List<Utilisateur> users = new ArrayList<>();
			try {
				String sqlQuery = "select id,nom,prenom,adresse,telephone,email,role,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
						+ " from utilisateur"
						+ " where role = 1 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
				preparedStatement = connexion.prepareStatement(sqlQuery);
				preparedStatement.setString(1, "%"+nomPrenom+"%");
				preparedStatement.setString(2, "%"+nomPrenom+"%");
				preparedStatement.setString(3, "%"+nomPrenom+"%");
				preparedStatement.setString(4, "%"+nomPrenom+"%");
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					users.add(new Utilisateur(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
							resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role")));
				}
			} catch ( SQLException e) {
				System.out.println(e.getMessage());
			}
			return users;
	    }
	    
	    public Utilisateur findUtilisateurById(int id) {
			Utilisateur user = null;
			try {
				String sqlQuery = "select * from utilisateur where role = 1 and id = ?";
				preparedStatement = connexion.prepareStatement(sqlQuery);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					user = new Utilisateur(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
							resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role"));
				}
			} catch ( SQLException e) {
				System.out.println(e.getMessage());
			}
			return user;
		}
	    
	    public boolean updateUtilisateur(Utilisateur userModifications) {
			
			boolean isModified = false; // NOPMD by houss on 10/10/22 8:20 PM
			try {
				preparedStatement = connexion.prepareStatement("update utilisateur set nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? where id = ?");
				preparedStatement.setString(1, userModifications.getNom());
				preparedStatement.setString(2, userModifications.getPrenom());
				preparedStatement.setString(3, userModifications.getAdresse());
				preparedStatement.setInt(4, userModifications.getTelephone());
				preparedStatement.setString(5, userModifications.getEmail());
				preparedStatement.setInt(6, userModifications.getId());
				isModified = preparedStatement.executeUpdate() > 0 ;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return isModified;
		}
}
