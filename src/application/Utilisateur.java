package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Livre;
import models.DbConnection;

public class Utilisateur {
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
	
	
	
	
	
}
