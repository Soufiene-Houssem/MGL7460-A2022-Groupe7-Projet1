package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Livre;
import models.DbConnection;
import models.PwdEncrypt;
import models.RechercheLivres;

public class Utilisateur implements RechercheLivres {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String adresse;
	private int telephone;
	private int role;
	
	public Utilisateur(String nom, String prenom, String email, String password, String adresse, int telephone, int role) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.telephone = telephone;
		this.role = role;
	}
	
	public Utilisateur () {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}	
	
	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<Livre> findAllLivres(){
		List<Livre> livres = new ArrayList<Livre>();
		try {
			PreparedStatement preparedStatement = DbConnection.connect()
					.prepareStatement("select * from Livre");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Livre livre = new Livre();
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAnnee(resultSet.getInt("annee"));
				livres.add(livre);
				System.out.println(livre.toString());
			}
			preparedStatement.close();
			
		} catch(Exception e) {
			livres = null;
			e.printStackTrace();
			System.out.println("Aucun livre n'a été trouvé!");
		}
		return livres;
	}

	@Override
	public String toString() {
		return  "ID "+id+" ~~ Nom: "+ nom + " || Prenom: "+prenom+ " || Email: "+email+" || Adresse: "+adresse+ " || Téléphone: "+telephone;
	}

	
	@Override
	public List<Livre> consulterLivres() {
		List<Livre> livres = new ArrayList<>();
		try {
			PreparedStatement selectLivres = DbConnection.connect().prepareStatement("select * from livre");
			ResultSet resultSet = selectLivres.executeQuery();
			while(resultSet.next()) {
				Livre livre = new Livre();
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
				livres.add(livre);
				System.out.println(livre.toString());
			}
			selectLivres.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return livres;
	}

	@Override
	public Livre rechercherLivre(int reference) {
		Livre livre = new Livre();
		try {
			String sqlQuery = "select * from livre where and id = ?";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, reference);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return livre;
	}

	@Override
	public List<Livre> rechercherLivresByTitre(String titreARechercher) {
		List<Livre> livres = new ArrayList<>();
		try {
			String sqlQuery = "select * from livre where titre like ?)";
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			titreARechercher = "%"+titreARechercher+"%";
			preparedStatement.setString(1, titreARechercher);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Livre livre = new Livre();
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
				livres.add(livre);
				System.out.println(livre.toString());
			}
			preparedStatement.close();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return livres;
	}
	
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return true si les paramètres sont correct
	 */
	public boolean authentification(String email, String password) {
		
		boolean isConnected = false;
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
				String saltvalue = resultSet.getString("salt");
				if (PwdEncrypt.verifyUserPassword(password, user.getPassword(), saltvalue)) {
					System.out.println("Connecté! mot de passe correct!");
					isConnected = true;
					
				}else {
					System.out.println("Mot de passe incorrect!");
				}
			}
			
			preparedStatement.close();
		} catch ( Exception e ) {
			System.out.println("l'émail ou le mot de passe est incorrect!");
		}
		return isConnected;
		
	}
	
	
	
	
}
