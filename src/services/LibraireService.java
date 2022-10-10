package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.Libraire;
import utils.DbConnection;
import utils.PwdEncrypt;

/**
 * 
 * @author houss
 *
 */
public class LibraireService {

	private transient  PreparedStatement preparedStatement;
    private transient ResultSet resultSet;
    private final transient Connection connexion;
    
    public LibraireService() {
		super();
		this.connexion = DbConnection.getInstance().getConnexion();
	}
    
    public List<Libraire> getAllLibraires(){
		
		List<Libraire> libraires = new ArrayList<>();
		try {
			preparedStatement = connexion.prepareStatement("select * from utilisateur inner join libraire on utilisateur.id = libraire.idUser where role = 2");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				libraires.add(new Libraire(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
						resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role"), resultSet.getInt("numeroBadge")));
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return libraires;
	}
    
    public boolean addLibraire(Libraire libraire) {
    	boolean isAdded = false;  
        String saltvalue = PwdEncrypt.getSaltvalue(30);  // Générer le salt
        String encryptedpassword = PwdEncrypt.generateSecurePassword(libraire.getPassword(), saltvalue);  //Générer un mot de passe crypté pour l'enregister a la base de données
        try{
        	preparedStatement = connexion.prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, libraire.getEmail());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("!!!L'email saisi existe déja!!!");
			}else {
				preparedStatement = connexion.prepareStatement("insert into utilisateur(nom, prenom, adresse, telephone, email, role, password, salt) values (?, ?, ?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, libraire.getNom());
				preparedStatement.setString(2, libraire.getPrenom());
				preparedStatement.setString(3, libraire.getAdresse());
				preparedStatement.setInt(4, libraire.getTelephone());
				preparedStatement.setString(5, libraire.getEmail());
				preparedStatement.setInt(6, 2);
				preparedStatement.setString(7, encryptedpassword);
				preparedStatement.setString(8, saltvalue);
				isAdded = preparedStatement.executeUpdate() > 0;
				if (isAdded) {
					preparedStatement = connexion.prepareStatement("insert into libraire(idUser) select id from utilisateur where email = ?");
					preparedStatement.setString(1, libraire.getEmail());
					preparedStatement.executeUpdate();
				}
			}
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return isAdded;
    }
    
    public boolean deleteLibraire(int id) {
    	boolean isDeleted = false;
		try {
			preparedStatement = connexion.prepareStatement("delete from libraire where idUser = ?");
			preparedStatement.setInt(1, id);
			if ( preparedStatement.executeUpdate() > 0 ) {
				preparedStatement = connexion.prepareStatement("delete from utilisateur where id = ?");
				preparedStatement.setInt(1, id);
				preparedStatement.executeUpdate();
				isDeleted = preparedStatement.executeUpdate() > 0;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return isDeleted;
    }
    
    public List<Libraire> findLibraireByNomPrenom(String nomPrenom) {
    	List<Libraire> libraires = new ArrayList<>();
		try {
			String sqlQuery = "select id,nom,prenom,adresse,telephone,email,numeroBadge,nom||' '||prenom as nomPrenom,prenom||' '||nom as prenomNom"
					+ " from utilisateur inner join libraire on utilisateur.id = libraire.idUser"
					+ " where role = 2 and (nom like ? or prenom like ? or nomPrenom like ? or prenomNom like ?)";
			preparedStatement = connexion.prepareStatement(sqlQuery);
			preparedStatement.setString(1, "%"+nomPrenom+"%");
			preparedStatement.setString(2, "%"+nomPrenom+"%");
			preparedStatement.setString(3, "%"+nomPrenom+"%");
			preparedStatement.setString(4, "%"+nomPrenom+"%");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				libraires.add(new Libraire(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
						resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role"), resultSet.getInt("numeroBadge")));
			}
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return libraires;
    }
    
    public Libraire findLibraireById(int id) {
		Libraire libraire = null;
		try {
			String sqlQuery = "select * from utilisateur inner join libraire on utilisateur.id = libraire.idUser where role = 2 and id = ?";
			preparedStatement = connexion.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				libraire = new Libraire(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),
						resultSet.getString("adresse"), resultSet.getInt("telephone"), resultSet.getInt("role"), resultSet.getInt("numeroBadge"));
			}
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return libraire;
	}
    
    public boolean updateLibraire(Libraire libraireMods) {
		
		boolean isModified = false;
		try {
			preparedStatement = connexion.prepareStatement("select * from utilisateur where email = ?");
			preparedStatement.setString(1, libraireMods.getEmail());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("!!!L'email saisi existe déja!!!");
			}else {
				preparedStatement = connexion.prepareStatement("update utilisateur set nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ? where id = ?");
				preparedStatement.setString(1, libraireMods.getNom());
				preparedStatement.setString(2, libraireMods.getPrenom());
				preparedStatement.setString(3, libraireMods.getAdresse());
				preparedStatement.setInt(4, libraireMods.getTelephone());
				preparedStatement.setString(5, libraireMods.getEmail());
				preparedStatement.setInt(6, libraireMods.getId());
				isModified = preparedStatement.executeUpdate() > 0 ;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isModified;
	}
}
