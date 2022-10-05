package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.Livre;
import models.DbConnection;
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
	
	public Utilisateur(final String nom, final String prenom, final String email, final String password, final String adresse, final int telephone, final int role) {
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


	@Override
	public String toString() {
		return  "ID "+id+" ~~ Nom: "+ nom + " || Prenom: "+prenom+ " || Email: "+email+" || Adresse: "+adresse+ " || Téléphone: "+telephone;
	}

	
	@Override
	public List<Livre> consulterLivres() {
		List<Livre> livres = new ArrayList<>();
		PreparedStatement selectLivres;
		ResultSet resultSet;
		try {
			selectLivres = DbConnection.connect().prepareStatement("select * from livre");
			resultSet = selectLivres.executeQuery();
			Livre livre = new Livre();
			while(resultSet.next()) {
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
				livres.add(livre);
				System.out.println(livre.toString());
			}
			resultSet.close();
			selectLivres.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return livres;
	}

	@Override
	public Livre rechercherLivre(int reference) {
		Livre livre = new Livre();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			String sqlQuery = "select * from livre where id = ?";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			preparedStatement.setInt(1, reference);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return livre;
	}

	@Override
	public List<Livre> rechercherLivresByTitre(String titreARechercher) {
		List<Livre> livres = new ArrayList<>();
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			String sqlQuery = "select * from livre where titre like ?";
			preparedStatement = DbConnection.connect().prepareStatement(sqlQuery);
			final String search = "%"+titreARechercher+"%";
			preparedStatement.setString(1, search);
			resultSet = preparedStatement.executeQuery();
			Livre livre = new Livre();
			while(resultSet.next()) {
				livre.setId(resultSet.getInt("id"));
				livre.setTitre(resultSet.getString("titre"));
				livre.setCategorie(resultSet.getString("categorie"));
				livre.setAuteur(resultSet.getString("auteur"));
				livre.setAnnee(resultSet.getInt("annee"));
				livres.add(livre);
				System.out.println(livre.toString());
			}
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return livres;
	}

	
	
	
}
